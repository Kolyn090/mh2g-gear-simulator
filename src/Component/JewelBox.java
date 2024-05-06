//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Component;

import java.util.Arrays;

public final class JewelBox {
    private final Jewel[][] box = new Jewel[5][0];
    private final int[] slots;

    public JewelBox(int[] slots) {
        this.slots = slots;
    }

    public Jewel[][] getBox() {
        return this.box;
    }

    public void addJewel2Box(Jewel newJewel, int position) {
        if (this.slots[position] >= newJewel.getSlot()) {
            this.resize(position);
            int lenInPosition = this.box[position].length - 1;
            this.box[position][lenInPosition] = newJewel;
            this.slots[position] -= newJewel.getSlot();
        }
    }

    private void resize(int position) {
        Jewel[] temp = this.box[position];
        this.box[position] = new Jewel[this.box[position].length + 1];
        System.arraycopy(temp, 0, this.box[position], 0, temp.length);
    }

    public String toString() {
        return Arrays.deepToString(this.box);
    }
}
