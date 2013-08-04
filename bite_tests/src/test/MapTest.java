package test;

import de.balpha.bite.Bite;
import de.balpha.bite.FunctionalInterfaces.Func;
import de.balpha.bite.FunctionalInterfaces.Predicate;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static de.balpha.bite.Bite.from;

public class MapTest extends BaseTest {

    @Test
    public void TestMap() {
        List<Integer> source = Arrays.asList(13, -3, 7, 5, 0, -2, 1);

        Func<Integer, Double> halve = new Func<Integer, Double>() {
            @Override
            public Double apply(Integer value) {
                return value * 0.5;
            }
        };
        Bite<Double> mapped = from(source).map(halve);

        List<Double> expected = Arrays.asList(6.5, -1.5, 3.5, 2.5, 0.0, -1.0, 0.5);
        AssertIterEquals(expected, mapped);

        Func<Number, String> numberString = new Func<Number, String>() {
            @Override
            public String apply(Number value) {
                return value.toString();
            }
        };
        Predicate<Object> notNull = new Predicate<Object>() {
            @Override
            public Boolean apply(Object value) {
                return value != null;
            }
        };

        // just checking that this compiles
        from(source).map(numberString).filter(notNull);

        AssertIterEquals(Collections.emptyList(), from(Collections.<Integer>emptyList()).map(halve));
    }


}
