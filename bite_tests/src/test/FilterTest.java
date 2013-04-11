package test;

import de.balpha.bite.Bite;
import de.balpha.bite.FunctionalInterfaces.Predicate;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static de.balpha.bite.Bite.from;

public class FilterTest extends BaseTest {

    @Test
    public void TestFilter() {
        List<Integer> source = Arrays.asList(13, -3, 7, 5, 0, -2, 1);

        Bite<Integer> filtered = from(source).filter(new Predicate<Integer>() {
            @Override
            public Boolean apply(Integer value) {
                return value > 0;
            }
        });

        List<Integer> expected = Arrays.asList(13, 7, 5, 1);
        AssertIterEquals(expected, filtered);

        AssertIterEquals(source, from(source).filter(new Predicate<Integer>() {
            @Override
            public Boolean apply(Integer value) {
                return value < 100;
            }
        }));

        AssertIterEquals(Collections.emptyList(), from(source).filter(new Predicate<Integer>() {
            @Override
            public Boolean apply(Integer value) {
                return value == 17;
            }
        }));

    }


}
