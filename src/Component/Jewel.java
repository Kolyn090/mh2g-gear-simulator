//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Component;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public final class Jewel {
    private final String name;
    private final String[] positiveEffect = new String[2];
    private String[] negativeEffect;
    private final int slot;
    private final Hashtable<String, Integer> materialTable = new Hashtable();
    private final String rank;

    public Jewel(String input) {
        String[] tuple = input.split(" ");
        this.name = tuple[0];
        String pos = tuple[1];
        int midPoint = 0;

        for(int i = 0; i < pos.length(); ++i) {
            if (pos.charAt(i) == '+') {
                midPoint = i;
                break;
            }
        }

        this.positiveEffect[0] = pos.substring(0, midPoint);
        this.positiveEffect[1] = pos.substring(midPoint);
        int i;
        if (tuple.length == 6) {
            this.negativeEffect = new String[2];
            String neg = tuple[2];
            midPoint = 0;

            for(i = 0; i < neg.length(); ++i) {
                if (neg.charAt(i) == '-') {
                    midPoint = i;
                    break;
                }
            }

            this.negativeEffect[0] = neg.substring(0, midPoint);
            this.negativeEffect[1] = neg.substring(midPoint);
        }

        this.rank = tuple[tuple.length - 1];
        String[] materials = tuple[tuple.length - 2].split(":");
        this.materialTable.put(materials[0], 1);

        for(i = 1; i < materials.length; ++i) {
            String[] m = materials[i].split("\\*");
            String material = m[0];
            String number = m[1];
            number = number.replaceAll("\\+", "");
            this.materialTable.put(material, Integer.parseInt(number));
        }

        this.slot = this.countSlots(tuple[tuple.length - 3]);
    }

    public String getName() {
        return this.name;
    }

    public String[] getPositiveEffect() {
        return this.positiveEffect;
    }

    public String[] getNegativeEffect() {
        return this.negativeEffect;
    }

    public int getSlot() {
        return this.slot;
    }

    public Hashtable<String, Integer> getMaterialTable() {
        return this.materialTable;
    }

    public String getRank() {
        return this.rank;
    }

    public String toString() {
        return this.name;
    }

    public String detailedInfo() {
        String var10000 = this.name;
        return "Name: " + var10000 + "\n" + Arrays.toString(this.positiveEffect) + "\n" + Arrays.toString(this.negativeEffect) + "\nSlot: " + this.slot + "\nMaterials: " + this.materialTable + "\nRank: " + this.rank;
    }

    private int countSlots(String slot) {
        int count = 0;

        for(int i = 0; i < slot.length(); ++i) {
            if (slot.charAt(i) == 'O') {
                ++count;
            }
        }

        return count;
    }

    public int calculateScore(Map<String, Integer> skillRequirements) {
        int score = 0;
        Iterator var3 = skillRequirements.keySet().iterator();

        while(true) {
            while(var3.hasNext()) {
                String skillName = (String)var3.next();
                if (skillName.equals(this.positiveEffect[0]) && (Integer)skillRequirements.get(skillName) > 0) {
                    score += 10 * Integer.parseInt(this.positiveEffect[1]);
                } else if (this.negativeEffect != null && skillName.equals(this.negativeEffect[0]) && (Integer)skillRequirements.get(skillName) < 0) {
                    score -= 10 * Integer.parseInt(this.negativeEffect[1]);
                }
            }

            return score;
        }
    }
}
