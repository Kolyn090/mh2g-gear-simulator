//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src;

// Request occupation
// Request rarity range
// Request weapon slots
// Put requested skill codes to the convertor and get skills

// first, fill up the weapon slot
// second, assemble

import src.Component.Equip;
import src.Component.Jewel;
import src.Component.Skill;
import src.Component.Suit;

import java.util.*;

public class Assembler {

    private final Jewel[] weaponJewels; // the len will be the max num of slots
    private final Set<Skill> selectedSkills;
    private final Map<String, Integer> skillRequirements;
    private final Set<Equip>[] flittedEquips;
    private final Set<Jewel> usableJewels;
    private final Set<Suit> suits;

    public Assembler(int occupation, int weaponSlots, int[] rarityRange, Set<Integer> requestedSkillCode) {
        weaponJewels = new Jewel[weaponSlots];
        RequestConvertor rc = new RequestConvertor(requestedSkillCode);
        selectedSkills = rc.getOutputSkills();
        System.out.println(selectedSkills);
        EquipReader equipReader = new EquipReader(occupation, rarityRange, selectedSkills);
        skillRequirements = new HashMap<>();
        for (Skill skill : selectedSkills) {
            skillRequirements.put(skill.getType(), skill.getPoint());
        }
        flittedEquips = equipReader.getFlittedEquips();
        JewelReader jr = new JewelReader(selectedSkills);
        usableJewels = jr.getUsableJewels();
        suits = new HashSet<>();
        fillWeaponSlots();
        assemble();
    }

    private void fillWeaponSlots() {
        // 1st priority: Fill the skill with negative requested points
        // 2nd priority: Fill the skill with the highest requested points
        // 3rd priority: Fill the skill with lower positive requested points
        // Update the skill requirements
        int position = 0;
        int weaponSlots = weaponJewels.length;
        for (Skill skill: selectedSkills) {
            if (skill.getPoint() < 0) {
                for (Jewel jewel : usableJewels) {
                    if (jewel.getNegativeEffect() != null && jewel.getNegativeEffect()[0].equals(skill.getType())) {
                        if (jewel.getSlot() < weaponSlots) {
                            System.out.println("weapon jewel: "+jewel);
                            weaponJewels[position] = jewel;
                            weaponSlots -= jewel.getSlot();
                            position++;
                        }
                    }
                }
            }
        }

        MaxPQ<Skill> skillMaxPQ = new MaxPQ<>(new Comparator<Skill>() {
            @Override
            public int compare(Skill o1, Skill o2) {
                return o1.getPoint() - o2.getPoint();
            }
        });
        for (Skill skill : selectedSkills) {
            if (skill.getPoint() > 0) {
                skillMaxPQ.insert(skill);
            }
        }
        // The processing order of jewel, can be improved
        MaxPQ<Jewel> jewelMaxPQ = new MaxPQ<>(new Comparator<Jewel>() {
            @Override
            public int compare(Jewel o1, Jewel o2) {
                return o1.getSlot() - o2.getSlot();
            }
        });
        for (Jewel jewel : usableJewels) {
            jewelMaxPQ.insert(jewel);
        }
        for (Skill skill : skillMaxPQ) {
            for (Jewel jewel : jewelMaxPQ) {
                if (jewel.getPositiveEffect()[0].equals(skill.getType())) {
                    if (jewel.getSlot() <= weaponSlots) {
                        weaponJewels[position] = jewel;
                        weaponSlots -= jewel.getSlot();
                        position++;
                    }
                }
            }
        }

        for (Jewel jewel : weaponJewels) {
            if (jewel == null) {
                continue;
            }
            String[] effectPoint;
            if (skillRequirements.containsKey(jewel.getPositiveEffect()[0])) {
                effectPoint = jewel.getPositiveEffect();
            } else {
                effectPoint = jewel.getNegativeEffect();
            }
            skillRequirements.put(effectPoint[0], skillRequirements.get(effectPoint[0])-Integer.parseInt(effectPoint[1]));
        }
    }

    private void assemble() {
        Set<Equip> helmet = flittedEquips[0];
        Set<Equip> plate = flittedEquips[1];
        Set<Equip> gauntlet = flittedEquips[2];
        Set<Equip> waist = flittedEquips[3];
        Set<Equip> leggings = flittedEquips[4];

        // 4^5, at most 1024 times
        for (Equip he : helmet) {
            for (Equip pl : plate) {
                for (Equip ga : gauntlet) {
                    for (Equip wa : waist) {
                        for (Equip le : leggings) {
                            suits.add(new Suit(new Suit.SuitBuilder(he, pl, ga, wa, le)));
                        }
                    }
                }
            }
        }
    }

    public Map<String, Integer> getSkillRequirements() {
        return skillRequirements;
    }

    public Jewel[] getWeaponJewels() {
        return weaponJewels;
    }

    public Set<Suit> getSuits() {
        return suits;
    }

    public Set<Jewel> getUsableJewels() {
        return usableJewels;
    }

    public boolean collectionFail() {
        for (Set<Equip> flittedEquip : flittedEquips) {
            if (flittedEquip.size() == 0) {
                return true;
            }
        }
        return false;
    }
}