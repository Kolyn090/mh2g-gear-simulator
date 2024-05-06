//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Component;

import java.util.Hashtable;

public final class Equip {
    private final String name;
    private final int rarity;
    private final String type;
    private final int price;
    private final int[] res;
    private final int slot;
    private final Hashtable<String, Integer> skillTable;
    private final Hashtable<String, Integer> materialTable;
    private int score = 0;

    public Equip(String input) {
        String[] tuple = input.split(" ");
        this.name = tuple[0];
        this.rarity = Integer.parseInt(tuple[1]);
        this.type = tuple[2];
        this.price = Integer.parseInt(tuple[3].replaceAll("z", ""));
        this.res = new int[]{Integer.parseInt(tuple[4]), Integer.parseInt(tuple[5]), Integer.parseInt(tuple[6]), Integer.parseInt(tuple[7]), Integer.parseInt(tuple[8])};
        this.skillTable = this.splitSkills(tuple[10]);
        this.materialTable = this.splitMaterials(tuple[11]);
        this.slot = this.countSlots(tuple[9]);
    }

    public String getName() {
        return this.name;
    }

    public int getRarity() {
        return this.rarity;
    }

    public String getType() {
        return this.type;
    }

    public int getPrice() {
        return this.price;
    }

    public int getSlot() {
        return this.slot;
    }

    public int getFire() {
        return this.res[0];
    }

    public int getWater() {
        return this.res[1];
    }

    public int getLight() {
        return this.res[2];
    }

    public int getIce() {
        return this.res[3];
    }

    public int getDragon() {
        return this.res[4];
    }

    public Hashtable<String, Integer> getSkillTable() {
        return this.skillTable;
    }

    public Hashtable<String, Integer> getMaterialTable() {
        return this.materialTable;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int other) {
        this.score = other;
    }

    private Hashtable<String, Integer> splitSkills(String skills) {
        Hashtable<String, Integer> table = new Hashtable();
        String[] fragments = skills.split(":");
        String[] var4 = fragments;
        int var5 = fragments.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String fragment = var4[var6];
            int midPoint = 0;

            for(int i = 0; i < fragment.length(); ++i) {
                if (fragment.charAt(i) == '+' || fragment.charAt(i) == '-') {
                    midPoint = i;
                    break;
                }
            }

            String skill = fragment.substring(0, midPoint);
            String point = fragment.substring(midPoint);
            point = point.replaceAll("\\+", "");
            table.put(skill, Integer.parseInt(point));
        }

        return table;
    }

    private Hashtable<String, Integer> splitMaterials(String materials) {
        Hashtable<String, Integer> table = new Hashtable();
        String[] fragments = materials.split(":");
        String[] var4 = fragments;
        int var5 = fragments.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String fragment = var4[var6];
            String[] m = fragment.split("\\*");
            table.put(m[0], Integer.parseInt(m[1]));
        }

        return table;
    }

    public String toString() {
        return this.name;
    }

    public String detailedInfo() {
        return "Name = " + this.name + "\nSlot = " + this.slot + "\nSkills = " + this.skillTable;
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
}
