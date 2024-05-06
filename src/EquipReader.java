package src;

import src.Component.Equip;
import src.Component.Skill;

import java.util.*;
import java.io.File;

public class EquipReader {

    // 0: helmet
    // 1: plate
    // 2: gauntlet
    // 3: waist
    // 4: leggings
    private final int equipLimitation = 4; // limitation of number of equips for each part
    private final Set<Equip>[] candidateEquips = new Set[5];
    private final Set<Equip>[] flittedEquips = new Set[5];
    private final String occupation;
    private final int lowBound, highBound;
    private final Set<Skill> selectedSkills;

    public EquipReader(int occupationInt, int[] rarityRange,
                       Set<Skill> selectedSkills) {
        for (int i = 0; i < 5; i++) {
            candidateEquips[i] = new HashSet<>();
            flittedEquips[i] = new HashSet<>();
        }
        if (occupationInt == 0) {
            occupation = "剣";
        } else {
            occupation = "ガ";
        }
        lowBound = rarityRange[0];
        highBound = rarityRange[1];
        this.selectedSkills = selectedSkills;

        roughRead();
        flit();
    }

    private void roughRead() {
        String[] parts = new String[] {
                "lib/頭防.txt",
                "lib/胴体.txt",
                "lib/手防.txt",
                "lib/腰防.txt",
                "lib/足防.txt"
        };

        for (int i = 0; i < parts.length; i++) {
            readFile(parts[i], i);
        }
    }

    private void readFile(String fname, int position) {
        Set<Equip> equips = candidateEquips[position];

        try {
            ArrayList<String> lines = FileReader.readFile(fname);
            for (String line : lines) {
                Equip newEquip = new Equip(line);
                if (isCorrectOccupation(newEquip) &&
                        isInRange(newEquip) &&
                        isSkillWanted(newEquip)) {
                    equips.add(newEquip);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean isCorrectOccupation(Equip equip) {
        if (equip.getType().length() > 1) {
            return true;
        } else return equip.getType().equals(occupation);
    }

    private boolean isInRange(Equip equip) {
        return equip.getRarity() >= lowBound && equip.getRarity() <= highBound;
    }

    // do not add equip with opposite points
    private boolean isSkillWanted(Equip equip) {
        Hashtable<String, Integer> skillTable = equip.getSkillTable();
        for (String e : skillTable.keySet()) {
            for (Skill skill : selectedSkills) {
                if (skill.getType().equals(e)) {
                    if (skillTable.get(e) > 0 && skill.getPoint() > 0 ||
                            skillTable.get(e) < 0 && skill.getPoint() < 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void flit() {
        for (int i = 0; i < candidateEquips.length; i++) {
            MaxPQ<Equip> equipMaxPQ = new MaxPQ<>(new Comparator<Equip>() {
                @Override
                public int compare(Equip o1, Equip o2) {
                    return o1.getScore() - o2.getScore();
                }
            });
            for (Equip equip : candidateEquips[i]) {
                equip.setScore(calculateScore(equip));
                equipMaxPQ.insert(equip);
            }
            for (int j = 0; j < equipLimitation; j++) {
                if (!equipMaxPQ.isEmpty()) {
                    flittedEquips[i].add(equipMaxPQ.delMax());
                }
            }
        }
    }

    public int calculateScore(Equip equip) {
        // slot * 20
        // positive point * 10
        // negative point * -20
        int score = equip.getSlot()*15;

        for (Skill selected : selectedSkills) {
            for (String skill : equip.getSkillTable().keySet()) {
                if (selected.getType().equals(skill)) {
                    // Check the positivity is matches the requested
                    if (selected.getPoint() > 0 && equip.getSkillTable().get(skill) > 0 ||
                            selected.getPoint() < 0 && equip.getSkillTable().get(skill) < 0) {
                        score += equip.getSkillTable().get(skill) * 10;
                    } else {
                        score += equip.getSkillTable().get(skill) * 20;
                    }
                }
            }
        }

        return score;
    }

    public Set<Equip>[] getFlittedEquips() {
        return flittedEquips;
    }
}