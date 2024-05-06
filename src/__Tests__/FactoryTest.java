package src.__Tests__;

import src.Assembler;
import src.Mosaicist;

import java.util.HashSet;
import java.util.Set;

// [146, 180, 185]
// 剥ぎ取り鉄人, 不運, 捕獲の見極め
// [16, 86, 184]
// 体力-10, 通常弾全レベル追加, 捕獲上手
// [150, 10, 44]
// 精霊の気まぐれ, 防御-20, 龍耐性-10
// [19, 67, 212]
// 全耐性+10, 護法, オトモ防御力UP

public class FactoryTest {

    public static void main(String[] args) {
        int occupation = 0;
        int numOfSuits = 1;
        int[] rarityRange = new int[]{9, 10};
        int weaponSlot = 3;
        Set<Integer> requestedSkillCode = new HashSet<>();
        requestedSkillCode.add(22);
        requestedSkillCode.add(55);
        requestedSkillCode.add(30);

        Assembler assembler = new Assembler(occupation, weaponSlot, rarityRange, requestedSkillCode);

        if (!assembler.collectionFail()) {
            Mosaicist mosaicist = new Mosaicist(numOfSuits,
                    assembler.getSuits(),
                    assembler.getSkillRequirements(),
                    assembler.getUsableJewels());
        } else {
            System.out.println("COLLECTION FAILED!!!");
        }
        System.out.println("______________________________");
    }
}
