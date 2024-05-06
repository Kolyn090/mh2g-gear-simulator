package src;

import src.Component.Equip;
import src.Component.Jewel;
import src.Component.JewelBox;
import src.Component.Suit;

import java.util.*;

// calculate the scores of all suit
// take out n best suits
// add jewels to them

public class Mosaicist {

    private final JewelBox[] jewelBoxes;
    private final MosaicistHelper helper;

    public Mosaicist(int numOfSuits, Set<Suit> suits, Map<String, Integer> skillRequirements, Set<Jewel> usableJewels) {
        jewelBoxes = new JewelBox[numOfSuits];
        Calculator calculator = new Calculator(skillRequirements);
        calculator.calculateScores(suits);
        helper = new MosaicistHelper(usableJewels, skillRequirements);
        Suit[] mosaicSuit = mosaic(calculator.getNBestSuits(suits, numOfSuits));

        for (int i = 0; i < jewelBoxes.length; i++) {
            for (Equip equip : mosaicSuit[i].equipIterator()) {
                System.out.println(equip.detailedInfo());
            }
            Map<String, Integer> nsp = getNeededSkillPoints(mosaicSuit[i]);
            System.out.println("Before: "+nsp);
            JewelBox jewelBox = jewelBoxes[i];
            System.out.println(jewelBox);
            for (Jewel[] a : jewelBox.getBox()) {
                for (Jewel b : a) {
                    for (String s : nsp.keySet()) {
                        if (b.getPositiveEffect()[0].equals(s)) {
                            nsp.put(s, nsp.get(s) - Integer.parseInt(b.getPositiveEffect()[1]));
                        } else if (b.getNegativeEffect() != null && b.getNegativeEffect()[0].equals(s)) {
                            nsp.put(s, nsp.get(s) - Integer.parseInt(b.getNegativeEffect()[1]));
                        }
                    }
                }
            }
            System.out.println("After: "+nsp);
        }
    }

    private Suit[] mosaic(MaxPQ<Suit> suitMaxPQ) {
        Suit[] suits = new Suit[jewelBoxes.length];
        for (int i = 0; i < jewelBoxes.length; i++) {
            Suit currentSuit = suitMaxPQ.delMax();
            JewelBox jewelBox = assignJewelBox(currentSuit);
            suits[i] = currentSuit;
            Map<String, Integer> neededSP = getNeededSkillPoints(currentSuit);
            int[] subtractedSlot = new int[5];

            while (helper.hasUnfulfilled(neededSP) && !helper.allSlotsOccupied(currentSuit, neededSP, subtractedSlot)) {
                for (String skill : neededSP.keySet()) {
                    int requiredPoint = helper.getSkillRequirements().get(skill);
                    if (requiredPoint < 0 && neededSP.get(skill) < 0) {
                        helper.helpMosaicNegativeJewel(neededSP, currentSuit, subtractedSlot, skill, jewelBox);
                    } else if (requiredPoint > 0 && neededSP.get(skill) > 0){
                        helper.helpMosaicPositiveJewel(neededSP, currentSuit, subtractedSlot, skill, jewelBox);
                    }
                }
            }

            jewelBoxes[i] = jewelBox;
        }
        return suits;
    }

    private JewelBox assignJewelBox(Suit suit) {
        int[] slots = new int[5];
        int j = 0;
        for (Equip equip : suit.equipIterator()) {
            slots[j] = equip.getSlot();
            j++;
        }
        return new JewelBox(slots);
    }

    private Map<String, Integer> getNeededSkillPoints(Suit suit) {
        Map<String, Integer> skillRequirements = helper.getSkillRequirements();
        Map<String, Integer> alreadyHave = new HashMap<>();
        for (Equip equip : suit.equipIterator()) {
            for (String s : equip.getSkillTable().keySet()) {
                if (!alreadyHave.containsKey(s)) {
                    alreadyHave.put(s, equip.getSkillTable().get(s));
                } else {
                    alreadyHave.put(s, alreadyHave.get(s) + equip.getSkillTable().get(s));
                }
            }
        }
        Map<String, Integer> stillNeeded = new HashMap<>();
        for (String a : alreadyHave.keySet()) {
            for (String s : skillRequirements.keySet()) {
                if (a.equals(s)) {
                    int point = skillRequirements.get(a) - alreadyHave.get(a);
                    if (skillRequirements.get(a) > 0) {
                        if (point < 0) {
                            point = 0;
                        }
                    } else if (skillRequirements.get(a) < 0) {
                        if (point > 0) {
                            point = 0;
                        }
                    }
                    stillNeeded.put(a, point);
                }
            }
        }
        for (String s : skillRequirements.keySet()) {
            if (!alreadyHave.containsKey(s)) {
                stillNeeded.put(s, skillRequirements.get(s));
            }
        }
        return stillNeeded;
    }
}