package test;

import de.balpha.bite.Bite;
import static de.balpha.bite.Bite.from;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class VariousTests extends BaseTest {

    @Test
    public void TestNoRedundantBiting() {
        Bite<Integer> bite1 = from(Arrays.<Integer>asList(1, 2, 3));
        Bite<Integer> bite2 = from(bite1);

        Assert.assertSame(bite1, bite2);
    }


}
