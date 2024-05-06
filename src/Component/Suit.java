//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Component;

public final class Suit {
    private final Equip helmet;
    private final Equip plate;
    private final Equip gauntlet;
    private final Equip waist;
    private final Equip leggings;
    private int score;

    public Equip getHelmet() {
        return this.helmet;
    }

    public Equip getPlate() {
        return this.plate;
    }

    public Equip getGauntlet() {
        return this.gauntlet;
    }

    public Equip getWaist() {
        return this.waist;
    }

    public Equip getLeggings() {
        return this.leggings;
    }

    public Equip[] equipIterator() {
        return new Equip[]{this.helmet, this.plate, this.gauntlet, this.waist, this.leggings};
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String toString() {
        String var10000 = this.helmet.detailedInfo();
        return "Helmet: " + var10000 + "\nPlate: " + this.plate.detailedInfo() + "\nGauntlet: " + this.gauntlet.detailedInfo() + "\nWaist: " + this.waist.detailedInfo() + "\nLeggings: " + this.leggings.detailedInfo() + "\nScore: " + this.score;
    }

    public Suit(SuitBuilder suitBuilder) {
        this.helmet = suitBuilder.head;
        this.plate = suitBuilder.plate;
        this.gauntlet = suitBuilder.gauntlet;
        this.waist = suitBuilder.waist;
        this.leggings = suitBuilder.leggings;
        this.score = 0;
    }

    public static class SuitBuilder {

        private final Equip head;
        private final Equip plate;
        private final Equip gauntlet;
        private final Equip waist;
        private final Equip leggings;

        public SuitBuilder(Equip head, Equip plate, Equip gauntlet, Equip waist, Equip leggings) {
            this.head = head;
            this.plate = plate;
            this.gauntlet = gauntlet;
            this.waist = waist;
            this.leggings = leggings;
        }

        public Suit build() {
            return new Suit(this);
        }
    }
}
