package test;

import de.balpha.bite.Bite;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static de.balpha.bite.Bite.from;

public class SkipTest extends BaseTest {

    @Test
    public void TestSkip() {
        List<Integer> source = Arrays.asList(13, -3, 7, 5, 0, -2, 1);

        Bite<Integer> skipped = from(source).skip(4);

        List<Integer> expected = Arrays.asList(0, -2, 1);
        AssertIterEquals(expected, skipped);

        AssertIterEquals(source, from(source).skip(0));
        AssertIterEquals(Collections.emptyList(), from(source).skip(42));
    }


}
