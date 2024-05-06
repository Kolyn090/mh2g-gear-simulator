package src;

import src.Component.Jewel;
import src.Component.Skill;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class JewelReader {

    private final Set<Jewel> usableJewels = new HashSet<>();

    public JewelReader(Set<Skill> selectedSkills) {
        fill(selectedSkills);
    }

    private void fill(Set<Skill> selectedSkills) {
        Set<Jewel> allJewels = new HashSet<>();
        try {
            ArrayList<String> lines = FileReader.readFile("lib/玉.txt");
            for (String line : lines) {
                allJewels.add(new Jewel(line));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (Jewel jewel : allJewels) {
            for (Skill skill : selectedSkills) {
                if (skill.getType().equals(jewel.getPositiveEffect()[0])) {
                    if (skill.getPoint() > 0) { // a match
                        usableJewels.add(jewel);
                    }
                } else if (jewel.getNegativeEffect()!=null && skill.getType().equals(jewel.getNegativeEffect()[0])) {
                    if (skill.getPoint() < 0) { // a match
                        usableJewels.add(jewel);
                    }
                }
            }
        }
    }

    public Set<Jewel> getUsableJewels() {
        return usableJewels;
    }
}