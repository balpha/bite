package test;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static de.balpha.bite.Bite.from;

public class AndTest extends BaseTest {

    @Test
    public void TestAnd() {
        List<Number> source1 = Arrays.<Number>asList(13, -3, 7, 5, 0, -2, 1);
        List<Integer> source2 = Arrays.asList(28, 12, -6, 42);

        List<Number> expected = Arrays.<Number>asList(13, -3, 7, 5, 0, -2, 1, 28, 12, -6, 42);

        AssertIterEquals(expected, from(source1).and(source2));
        AssertIterEquals(source1, from(source1).and(Collections.<Number>emptyList()));
        AssertIterEquals(source1, from(Collections.<Number>emptyList()).and(source1));
        AssertIterEquals(Collections.<Number>emptyList(), from(Collections.<Number>emptyList()).and(Collections.<Number>emptyList()));
    }
}
