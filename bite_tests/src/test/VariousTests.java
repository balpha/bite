package test;

import de.balpha.bite.Bite;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

import static de.balpha.bite.Bite.from;

public class VariousTests extends BaseTest {

    @Test
    public void TestNoRedundantBiting() {
        Bite<Integer> bite1 = from(Arrays.<Integer>asList(1, 2, 3));
        Bite<Integer> bite2 = from(bite1);

        Assert.assertSame(bite1, bite2);
    }

    @Test
    public void TestFirst() {
        Bite<Integer> bite1 = from(Arrays.<Integer>asList(1, 2, 3));
        Bite<Integer> bite2 = from(Collections.<Integer>emptyList());

        Assert.assertTrue(bite1.first() == 1);
        Assert.assertTrue(bite1.firstOr(42) == 1);
        Assert.assertTrue(bite2.firstOr(42) == 42);

        try {
            bite2.first();
            junit.framework.Assert.assertTrue(false);
        } catch (NoSuchElementException ex) {
            // all good
        }
    }



}
