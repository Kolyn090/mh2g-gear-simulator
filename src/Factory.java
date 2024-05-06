//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Factory {
    public Factory() {
    }

    public static void main(String[] args) {
        int occupation = 0;
        int numOfSuits = 1;
        int[] rarityRange = new int[]{1, 10};
        int weaponSlot = 0;

        for(int i = 0; i < 100; ++i) {
            Set<Integer> input = new HashSet();
            Random r = new Random();

            while(input.size() < 3) {
                input.add(r.nextInt(212) + 1);
            }

            System.out.println(input);
            Assembler assembler = new Assembler(occupation, weaponSlot, rarityRange, input);
            if (!assembler.collectionFail()) {
                new Mosaicist(numOfSuits, assembler.getSuits(), assembler.getSkillRequirements(), assembler.getUsableJewels());
            } else {
                System.out.println("COLLECTION FAILED!!!");
            }

            System.out.println("______________________________");
        }

    }
}
