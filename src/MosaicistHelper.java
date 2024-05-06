package src;

import src.Component.Equip;
import src.Component.Jewel;
import src.Component.JewelBox;
import src.Component.Suit;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class MosaicistHelper {

    private final Map<String, Integer> skillRequirements;
    private final Set<Jewel> usableJewels;

    public MosaicistHelper(Set<Jewel> usableJewels, Map<String, Integer> skillRequirements) {
        this.usableJewels = usableJewels;
        this.skillRequirements = skillRequirements;
    }

    public void helpMosaicNegativeJewel(Map<String, Integer> neededSP,
                                        Suit currentSuit,
                                        int[] subtractedSlot,
                                        String skill,
                                        JewelBox jewelBox) {
        Jewel negJewel = findNegativeTargetJewel(neededSP, currentSuit, subtractedSlot, skill);
        int position = 0;
        for (Equip equip : currentSuit.equipIterator()) {
            if (negJewel != null && equip.getSlot() - subtractedSlot[position] >= negJewel.getSlot()) {
                jewelBox.addJewel2Box(negJewel, position);
                String skillName = negJewel.getNegativeEffect()[0];
                int skillPoint = Integer.parseInt(negJewel.getNegativeEffect()[1]);
                int newPoint = neededSP.get(skillName) - skillPoint;
                if (newPoint > 0) {
                    newPoint = 0;
                }
                neededSP.put(skillName, newPoint);
                subtractedSlot[position] = subtractedSlot[position] + negJewel.getSlot();
                break;
            }
            position++;
        }
    }

    public void helpMosaicPositiveJewel(Map<String, Integer> neededSP,
                                        Suit currentSuit,
                                        int[] subtractedSlot,
                                        String skill,
                                        JewelBox jewelBox) {
        Jewel posJewel = findPositiveTargetJewel(neededSP, currentSuit, subtractedSlot, skill);
        int position = 0;
        for (Equip equip : currentSuit.equipIterator()) {
            if (posJewel != null && equip.getSlot() - subtractedSlot[position] >= posJewel.getSlot()) {
                jewelBox.addJewel2Box(posJewel, position);
                String skillName = posJewel.getPositiveEffect()[0];
                int skillPoint = Integer.parseInt(posJewel.getPositiveEffect()[1]);
                int newPoint = neededSP.get(skillName) - skillPoint;
                if (newPoint < 0) {
                    newPoint = 0;
                }
                neededSP.put(skillName, newPoint);
                subtractedSlot[position] = subtractedSlot[position] + posJewel.getSlot();
                break;
            }
            position++;
        }
    }

    private Jewel findPositiveTargetJewel(Map<String, Integer> neededSP,
                                          Suit suit,
                                          int[] subtractedSlots,
                                          String currSkillName) {
        MaxPQ<Jewel> jewelMaxPQ = new MaxPQ<>(new Comparator<Jewel>() {
            @Override
            public int compare(Jewel o1, Jewel o2) {
                return o1.calculateScore(neededSP) - o2.calculateScore(neededSP);
            }
        });
        for (Jewel jewel : usableJewels) {
            if (jewel.getPositiveEffect()[0].equals(currSkillName)) {
                jewelMaxPQ.insert(jewel);
            }
        }
        if (jewelMaxPQ.isEmpty()) {
            setAllNeeded2Zero(neededSP, currSkillName);
        }
        Jewel recordedJewel = null;
        while (!jewelMaxPQ.isEmpty()) {
            Jewel maxJewel = jewelMaxPQ.delMax();
            for (String type : neededSP.keySet()) {
                if (neededSP.get(type) > 0) { // positive skill needed
                    String skillName = maxJewel.getPositiveEffect()[0];
                    int skillPoint = Integer.parseInt(maxJewel.getPositiveEffect()[1]);
                    if (skillName.equals(type)) {
                        if (skillPoint <= neededSP.get(type) && isSlotForThisJewel(suit, subtractedSlots, maxJewel.getSlot())) {
                            return maxJewel;
                        } else {
                            recordedJewel = maxJewel;
                        }
                    }
                }
            }
        }
        return recordedJewel;
    }

    private Jewel findNegativeTargetJewel(Map<String, Integer> neededSP,
                                          Suit suit,
                                          int[] subtractedSlots,
                                          String currSkillName) {
        MaxPQ<Jewel> jewelMinPQ = new MaxPQ<>(new Comparator<Jewel>() {
            @Override
            public int compare(Jewel o1, Jewel o2) {
                return o1.calculateScore(neededSP) - o2.calculateScore(neededSP);
            }
        });
        for (Jewel jewel : usableJewels) {
            if (jewel.getNegativeEffect() != null && jewel.getNegativeEffect()[0].equals(currSkillName)) {
                jewelMinPQ.insert(jewel);
            }
        }
        if (jewelMinPQ.isEmpty()) {
            setAllNeeded2Zero(neededSP, currSkillName);
        }
        Jewel recordedJewel = null;
        while (!jewelMinPQ.isEmpty()) {
            Jewel minJewel = jewelMinPQ.delMax();
            for (String type : neededSP.keySet()) {
                if (neededSP.get(type) < 0) {
                    String skillName = minJewel.getNegativeEffect()[0];
                    int skillPoint = Integer.parseInt(minJewel.getNegativeEffect()[1]);
                    if (skillName.equals(type)) {
                        if (skillPoint >= neededSP.get(type) && isSlotForThisJewel(suit, subtractedSlots, minJewel.getSlot())) {
                            return minJewel;
                        } else {
                            recordedJewel = minJewel;
                        }
                    }
                }
            }
        }
        return recordedJewel;
    }

    private void setAllNeeded2Zero(Map<String, Integer> neededSP, String skillName) {
        neededSP.put(skillName, 0);
    }

    private boolean isSlotForThisJewel(Suit suit,
                                       int[] subtractedSlots,
                                       int desiredSlot) {
        for (int i = 0; i < suit.equipIterator().length; i++) {
            if (suit.equipIterator()[i].getSlot() - subtractedSlots[i] >= desiredSlot) {
                return true;
            }
        }
        return false;
    }

    public boolean hasUnfulfilled(Map<String, Integer> neededSP) {
        boolean result = false;
        for (String s : neededSP.keySet()) {
            if (neededSP.get(s) != 0) {
                result = true;
            }
        }
        return result;
    }

    public boolean allSlotsOccupied(Suit suit, Map<String, Integer> neededSP, int[] subtractedSlots) {
        for (int i = 0; i < suit.equipIterator().length; i++) {
            for (Jewel jewel : usableJewels) {
                if (suit.equipIterator()[i].getSlot() - subtractedSlots[i] >= jewel.getSlot()) {
                    for (String skill : neededSP.keySet()) {
                        if (neededSP.get(skill) > 0) {
                            if (jewel.getPositiveEffect()[0].equals(skill)) {
                                return false;
                            }
                        } else {
                            if (jewel.getNegativeEffect()!=null && jewel.getNegativeEffect()[0].equals(skill)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public Map<String, Integer> getSkillRequirements() {
        return skillRequirements;
    }
}