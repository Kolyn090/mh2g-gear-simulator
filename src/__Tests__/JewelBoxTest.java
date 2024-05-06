package src.__Tests__;

import src.Component.Jewel;
import src.Component.JewelBox;
import static org.junit.Assert.assertEquals;

class JewelBoxTest {

    public static void main(String[] args) {
        JewelBoxTest jewelBoxTest = new JewelBoxTest();
        jewelBoxTest.testAddJewelBox();
    }

    public void testAddJewelBox() {
        int[] slots = new int[] {3, 3, 3, 3, 3};
        JewelBox jewelBox = new JewelBox(slots);
        Jewel newjewel = new Jewel("攻撃珠 攻撃+1 O-- 水光原珠:怪力の種*1:怪鳥の鱗*1 下位");
        jewelBox.addJewel2Box(newjewel, 1);
        jewelBox.addJewel2Box(newjewel, 1);
        jewelBox.addJewel2Box(newjewel, 1);
        jewelBox.addJewel2Box(newjewel, 1);

        String[][] given = new String[5][3];
        for (int i = 0; i < jewelBox.getBox().length; i++) {
            for (int j = 0; j < jewelBox.getBox()[i].length; j++) {
                given[i][j] = jewelBox.getBox()[i][j].getName();
            }
        }

        String[][] result = new String[5][3];
        result[1] = new String[3];
        result[1][0] = "攻撃珠";
        result[1][1] = "攻撃珠";
        result[1][2] = "攻撃珠";

        assertEquals(result, given);
    }
}
