package src;

import src.Component.Equip;
import src.Component.Suit;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Calculator {

    private final Map<String, Integer> skillRequirements;

    public Calculator(Map<String, Integer> skillRequirements) {
        this.skillRequirements = skillRequirements;
    }

    // For every point uncompleted, minus 20
    public void calculateScores(Set<Suit> scoredSuits) {
        for (Suit suit : scoredSuits) {
            int score = 0;
            Map<String, Integer> skillInSuit = new HashMap<>();
            for (Equip equip : suit.equipIterator()) {
                score += equip.getScore();
                calculateSkillPointInSuit(skillInSuit, equip);
            }

            // calculate skill point score
            for (String reqSkill : skillRequirements.keySet()) {
                for (String skill : skillInSuit.keySet()) {
                    if (skill.equals(reqSkill)) {
                        if (skillRequirements.get(reqSkill) > 0) {
                            int point = skillRequirements.get(reqSkill) - skillInSuit.get(skill);
                            if (point < 0) {point = 0;}
                            score -= point*20;
                        } else {
                            int point = skillRequirements.get(reqSkill) - skillInSuit.get(skill);
                            if (point < 0) {point = 0;}
                            score += point*20;
                        }
                    }
                }
                if (!skillInSuit.containsKey(reqSkill)) {
                    if (skillRequirements.get(reqSkill) > 0) {
                        score -= skillRequirements.get(reqSkill) * 20;
                    } else {
                        score += skillRequirements.get(reqSkill) * 20;
                    }
                }
            }
            suit.setScore(score);
        }
    }

    private void calculateSkillPointInSuit(Map<String, Integer> skillInSuit, Equip equip) {
        for (String equSkill : equip.getSkillTable().keySet()) {
            for (String reqSkill : skillRequirements.keySet()) {
                if (equSkill.equals(reqSkill)) {
                    if (skillRequirements.get(reqSkill) > 0) {
                        if (!skillInSuit.containsKey(reqSkill)) {
                            skillInSuit.put(reqSkill, equip.getSkillTable().get(reqSkill));
                        } else {
                            int newPoint = skillInSuit.get(reqSkill) + equip.getSkillTable().get(reqSkill);
                            if (newPoint < 0) {
                                newPoint = 0;
                            }
                            skillInSuit.put(reqSkill, newPoint);
                        }
                    } else if (skillRequirements.get(reqSkill) < 0) {
                        if (!skillInSuit.containsKey(reqSkill)) {
                            skillInSuit.put(reqSkill, equip.getSkillTable().get(reqSkill));
                        } else {
                            int newPoint = skillInSuit.get(reqSkill) - equip.getSkillTable().get(reqSkill);
                            if (newPoint > 0) {
                                newPoint = 0;
                            }
                            skillInSuit.put(reqSkill, newPoint);
                        }
                    }
                }
            }
        }
    }

    public MaxPQ<Suit> getNBestSuits(Set<Suit> scoredSuits, int num) {
        MaxPQ<Suit> suitMaxPQ = new MaxPQ<>(new Comparator<Suit>() {
            @Override
            public int compare(Suit o1, Suit o2) {
                return o1.getScore() - o2.getScore();
            }
        });
        for (Suit suit : scoredSuits) {
            suitMaxPQ.insert(suit);
        }
        MaxPQ<Suit> result = new MaxPQ<>(new Comparator<Suit>() {
            @Override
            public int compare(Suit o1, Suit o2) {
                return o1.getScore() - o2.getScore();
            }
        });

        while (result.size() < num && suitMaxPQ.size() >= 1) {
            result.insert(suitMaxPQ.delMax());
        }
        return result;
    }
}