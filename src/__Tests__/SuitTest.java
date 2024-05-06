package src.__Tests__;


import src.Component.Equip;
import src.Component.Suit;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class SuitTest {

    public static void main(String[] args) {
        SuitTest suitTest = new SuitTest();
        suitTest.validSuit();
    }

    @Test
    public void validSuit() {
        String head = "マフモフフード 1 剣/ガ 50z 1 0 0 0 3 --- 加護+2:耐暑-2:耐寒+4:千里眼+3 ガウシカの毛皮*1";
        String plate = "マフモフジャケット 1 剣/ガ 50z 1 0 0 0 3 --- 加護+2:氷耐性+1:耐暑-2:耐寒+4 ガウシカの毛皮*1";
        String gauntlet = "マフモフミトン 1 剣/ガ 50z 1 0 0 0 3 O-- 耐雪+1:加護+2:耐暑-2:耐寒+4 ガウシカの毛皮*1";
        String waist = "マフモフコート 1 剣/ガ 50z 1 0 0 0 3 O-- 加護+2:耐暑-2:耐寒+4:地図+1 ガウシカの毛皮*1";
        String leggings = "マフモフブーツ 1 剣/ガ 50z 1 0 0 0 3 O-- 加護+2:氷耐性+1:耐暑-2:耐寒+4 ガウシカの毛皮*1";

        Equip headEquip = new Equip(head);
        Equip plateEquip = new Equip(plate);
        Equip gauntletEquip = new Equip(gauntlet);
        Equip waistEquip = new Equip(waist);
        Equip leggingsEquip = new Equip(leggings);

        Suit suit = new Suit(new Suit.SuitBuilder(headEquip, plateEquip, gauntletEquip, waistEquip, leggingsEquip));
        assertEquals(suit.getHelmet().getName(), "マフモフフード");
        assertEquals(suit.getPlate().getName(), "マフモフジャケット");
        assertEquals(suit.getGauntlet().getName(), "マフモフミトン");
        assertEquals(suit.getWaist().getName(), "マフモフコート");
        assertEquals(suit.getLeggings().getName(), "マフモフブーツ");
    }
}
