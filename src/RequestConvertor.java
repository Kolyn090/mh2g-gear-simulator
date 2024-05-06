package src;

import src.Component.Skill;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RequestConvertor {

    private final Skill[] allSkills = new Skill[212];
    private final Set<Skill> outputSkills = new HashSet<>();

    // Given a serial of skill codes, convert them into a reasonable serial

    public RequestConvertor(Set<Integer> skillCodes) {
        readAllSkills();
        for (int code : skillCodes) {
            outputSkills.add(allSkills[code-1]);
        }
        convert();
    }

    // If two skills have the same type, use the one with higher point
    public void convert() {
        Set<Integer> removedSkills = new HashSet<>();
        for (Skill i : outputSkills) {
            for (Skill j : outputSkills) {
                if (i.getCode() != j.getCode() && i.getType().equals(j.getType())) {
                    if (i.getPoint() > j.getPoint()) {
                        removedSkills.add(j.getCode());
                    } else {
                        removedSkills.add(i.getCode());
                    }
                }
            }
        }
        for (int k : removedSkills) {
            outputSkills.removeIf(m -> m.getCode() == k);
        }
    }

    private void readAllSkills() {
        int c = 0;

        try {
            ArrayList<String> lines = FileReader.readFile("lib/技.txt");
            for (String line : lines) {
                allSkills[c] = new Skill(line);
                c++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Set<Skill> getOutputSkills() {
        return outputSkills;
    }
}