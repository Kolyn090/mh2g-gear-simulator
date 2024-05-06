//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Component;

public class Skill {
    private final String type;
    private final int point;
    private final String effect;
    private final int code;

    public Skill(String input) {
        String[] tuple = input.split(" ");
        this.type = tuple[0];
        this.point = Integer.parseInt(tuple[1].replaceAll("\\+", ""));
        this.effect = tuple[2];
        this.code = Integer.parseInt(tuple[3]);
    }

    public String toString() {
        return this.effect;
    }

    public String getType() {
        return this.type;
    }

    public int getPoint() {
        return this.point;
    }

    public String getEffect() {
        return this.effect;
    }

    public int getCode() {
        return this.code;
    }
}
