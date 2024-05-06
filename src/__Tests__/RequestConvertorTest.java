package src.__Tests__;

import src.Component.Skill;
import src.RequestConvertor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class RequestConvertorTest {

    public static void main(String[] args) {
        RequestConvertorTest r = new RequestConvertorTest();
        r.testConvert();
    }

    @Test
    public void testConvert() {
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(6);
        set.add(7);
        RequestConvertor r = new RequestConvertor(set);
        ArrayList<String> a = new ArrayList<>();
        for (Skill skill : r.getOutputSkills()) {
            a.add(skill.getEffect());
        }
        assertEquals(a.toString(), "[攻撃力UP【大】, 防御+40]");
    }
}
