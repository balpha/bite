package test;

import de.balpha.bite.FunctionalInterfaces.Predicate;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static de.balpha.bite.Bite.from;

public class AnyAllTest extends BaseTest {

    @Test
    public void TestAnyAll() {
        List<Integer> source1 = Arrays.asList(13, -3, 7, 5, 0, -2, 1);
        List<Integer> source2 = Arrays.asList(28, 12, -6, 42);

        final Predicate<Integer> isEven = new Predicate<Integer>() {
            @Override
            public Boolean apply(Integer value) {
                return value % 2 == 0;
            }
        };
        Predicate<Integer> isOdd = new Predicate<Integer>() {
            @Override
            public Boolean apply(Integer value) {
                return !isEven.apply(value);
            }
        };

        Assert.assertTrue(from(source1).any(isEven));
        Assert.assertTrue(from(source1).any(isOdd));
        Assert.assertFalse(from(source1).all(isEven));
        Assert.assertFalse(from(source1).all(isOdd));
        Assert.assertTrue(from(source2).any(isEven));
        Assert.assertFalse(from(source2).any(isOdd));
        Assert.assertTrue(from(source2).all(isEven));
        Assert.assertFalse(from(source2).all(isOdd));

        Assert.assertFalse(from(Collections.emptyList()).any(new Predicate<Object>() {
            @Override
            public Boolean apply(Object value) {
                return true;
            }
        }));
        Assert.assertTrue(from(Collections.emptyList()).all(new Predicate<Object>() {
            @Override
            public Boolean apply(Object value) {
                return false;
            }
        }));

    }


}
