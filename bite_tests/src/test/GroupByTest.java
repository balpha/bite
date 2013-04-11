package test;

import de.balpha.bite.Bite;
import de.balpha.bite.FunctionalInterfaces.Func;
import de.balpha.bite.SpecializedBites.Grouping;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static de.balpha.bite.Bite.from;

public class GroupByTest extends BaseTest {

    @Test
    public void TestGroupBy() {
        List<String> source1 = Arrays.asList("There", "is", "no", "biz", "like", "show", "biz", "in", "this", "world");

        Bite<Grouping<Integer, String>> grouped = from(source1).groupBy(new Func<String, Integer>() {
            @Override
            public Integer apply(String value) {
                return value.length();
            }
        });

        List<Integer> expectedGroups = Arrays.asList(5, 2, 3, 4);
        HashMap<Integer, List<String>> expectedContents = new HashMap<Integer, List<String>>();
        expectedContents.put(5, Arrays.asList("There", "world"));
        expectedContents.put(2, Arrays.asList("is", "no", "in"));
        expectedContents.put(3, Arrays.asList("biz", "biz"));
        expectedContents.put(4, Arrays.asList("like", "show", "this"));

        AssertIterEquals(expectedGroups, grouped.map(new Func<Grouping<Integer, String>, Object>() {
            @Override
            public Object apply(Grouping<Integer, String> value) {
                return value.getKey();
            }
        }));
        for (Grouping<Integer, String> group : grouped) {
            AssertIterEquals(expectedContents.get(group.getKey()), group);
        }

        List<Integer> numbers = Arrays.asList(1, 2, 3, 0);
        Iterable<Grouping<Integer, Integer>> badGrouped = from(numbers).groupBy(new Func<Integer, Integer>() {
            @Override
            public Integer apply(Integer value) {
                return 100 / value;
            }
        });

        try {
            badGrouped.iterator();
            Assert.assertTrue(false);
        } catch (ArithmeticException ex) {
            // all good
        }


    }
}
