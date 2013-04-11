package test;

import de.balpha.bite.Bite;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static de.balpha.bite.Bite.from;

public class TakeTest extends BaseTest {

    @Test
    public void TestTake() {
        List<Integer> source = Arrays.asList(13, -3, 7, 5, 0, -2, 1);

        Bite<Integer> taken = from(source).take(4);

        List<Integer> expected = Arrays.asList(13, -3, 7, 5);
        AssertIterEquals(expected, taken);

        AssertIterEquals(source, from(source).take(42));
        AssertIterEquals(Collections.emptyList(), from(source).take(0));
    }


}
