package test;

import de.balpha.bite.Bite;
import de.balpha.bite.FunctionalInterfaces.Func;
import de.balpha.bite.FunctionalInterfaces.Predicate;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

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

    @Test
    public void TestCount() {
        Bite<Integer> bite = from(Arrays.<Integer>asList(1, 2, 3, 4, 5, 6, 23));

        Assert.assertTrue(bite.count() == 7);
        Assert.assertTrue(Bite.from(Collections.<String>emptyList()).count() == 0);
    }

    @Test
    public void BitedArrayListDoesNotIterate() {

        TestList<Integer> list = new TestList<Integer>();
        TestList<Integer> empty = new TestList<Integer>();
        list.addAll(Arrays.asList(1, -7, 5, 9 ,-388, 2, 0));

        Assert.assertFalse(list.getIteratorCalled());
        Assert.assertTrue(Bite.from(list).count() == 7);
        Assert.assertFalse(list.getIteratorCalled());
        Assert.assertTrue(Bite.from(list).filter(new Predicate<Integer>() {
            @Override
            public Boolean apply(Integer value) {
                return true;
            }
        }).count() == 7);
        Assert.assertTrue(list.getIteratorCalled());

        list.resetIteratorCalled();
        Assert.assertFalse(list.getIteratorCalled());
        Assert.assertTrue(Bite.from(list).first() == 1);
        Assert.assertFalse(list.getIteratorCalled());
        Assert.assertTrue(Bite.from(list).filter(new Predicate<Integer>() {
            @Override
            public Boolean apply(Integer value) {
                return true;
            }
        }).first() == 1);
        Assert.assertTrue(list.getIteratorCalled());

        Assert.assertFalse(empty.getIteratorCalled());
        Assert.assertTrue(Bite.from(empty).firstOr(123) == 123);
        Assert.assertFalse(empty.getIteratorCalled());
        Assert.assertTrue(Bite.from(empty).filter(new Predicate<Integer>() {
            @Override
            public Boolean apply(Integer value) {
                return true;
            }
        }).firstOr(123) == 123);
        Assert.assertTrue(empty.getIteratorCalled());

    }

    private class TestList<T> extends ArrayList<T> {
        private boolean mIteratorCalled = false;

        public boolean getIteratorCalled() {
            return mIteratorCalled;
        }

        public void resetIteratorCalled() {
            mIteratorCalled = false;
        }

        @Override
        public Iterator<T> iterator() {
            mIteratorCalled = true;
            return super.iterator();
        }
    }

}
