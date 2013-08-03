package test;

import de.balpha.bite.Bite;
import de.balpha.bite.FunctionalInterfaces.Func;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static de.balpha.bite.Bite.from;

public class SortByTest extends BaseTest {

    @Test
    public void TestSortBy() {
        List<String> source = Arrays.asList("There", "is", "no", "biz", "like", "show", "biz", "in", "this", "world");

        Bite<String> sorted = from(source).sortBy(new Func<String, Integer>() {
            @Override
            public Integer apply(String value) {
                return value.length();
            }
        });
        Bite<String> sortedReverse = from(source).sortByDescending(new Func<String, Integer>() {
            @Override
            public Integer apply(String value) {
                return value.length();
            }
        });

        List<String> expected = Arrays.asList("is", "no", "in", "biz", "biz", "like", "show", "this", "There", "world");
        List<String> expectedReverse = Arrays.asList("There", "world", "like", "show", "this", "biz", "biz", "is", "no", "in");

        AssertIterEquals(expected, sorted);
        AssertIterEquals(expectedReverse, sortedReverse);

        List<Integer> numbers = Arrays.asList(1, 2, 3, 0);
        Iterable<Integer> badSorted = from(numbers).sortBy(new Func<Integer, Integer>() {
            @Override
            public Integer apply(Integer value) {
                return 100 / value;
            }
        });

        try {
            badSorted.iterator();
            Assert.assertTrue(false);
        } catch (ArithmeticException ex) {
            // all good
        }


    }
}
