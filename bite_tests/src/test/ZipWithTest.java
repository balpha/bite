package test;

import de.balpha.bite.FunctionalInterfaces.Func2;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static de.balpha.bite.Bite.from;

public class ZipWithTest extends BaseTest {

    @Test
    public void TestZipWith() {
        List<Integer> source1 = Arrays.asList(13, -3, 7, 5, 0, -2, 1);
        List<Integer> source2 = Arrays.asList(28, 12, -6, 42);

        Func2<Integer, Integer, Integer> sum = new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer value1, Integer value2) {
                return value1 + value2;
            }
        };

        List<Integer> expected = Arrays.asList(41, 9, 1, 47);

        AssertIterEquals(expected, from(source1).zipWith(source2, sum));
        AssertIterEquals(Collections.emptyList(), from(Collections.<Integer>emptyList()).zipWith(source2, sum));
    }
}
