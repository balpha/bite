package test;

import de.balpha.bite.Bite;
import de.balpha.bite.FunctionalInterfaces.Func;
import de.balpha.bite.FunctionalInterfaces.Func2;
import de.balpha.bite.FunctionalInterfaces.Predicate;
import de.balpha.bite.SpecializedBites.Grouping;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class TautologyTests extends BaseTest {

    @Test
    public void testTautologies() {
        test(Bite.from(new Object[0]));
        test(Bite.from(Arrays.asList("foo", "bar", "baz", "quux")));
        test(Bite.from(Arrays.<Object>asList(13, "Hello", null, Bite.class)));
        test(Bite.from(new InfiniteNumbers()).take(12345));
        test(Bite.from(new Double[]{ 3.14, 0., -16.66666, 1e100, 2.14, -12.5 }));
    }

    private static class EmptyIter<T> implements Iterable<T> {
        @Override
        public Iterator<T> iterator() {
            return new EmptyIterator<T>();
        }

        private static class EmptyIterator<T> implements Iterator<T> {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public T next() {
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
    }

    private static class InfiniteNumbers implements Iterable<Integer> {
        @Override
        public Iterator<Integer> iterator() {
            return new InfiniteNumbersIterator();
        }

        private static class InfiniteNumbersIterator implements Iterator<Integer> {
            private Random r = new Random(42);
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                return r.nextInt();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
    }


    private <T> void test(Bite<T> bite) {

        EmptyIter<T> empty = new EmptyIter<T>();
        InfiniteNumbers inf = new InfiniteNumbers();

        Assert.assertTrue(bite.take(0).count() == 0);
        AssertIterEquals(bite.take(0), empty);
        AssertIterEquals(bite, bite.take(2).and(bite.skip(2)));

        Func<T, Integer> zero = new Func<T, Integer>() {
            @Override
            public Integer apply(T value) {
                return 0;
            }
        };
        AssertIterEquals(bite.sortBy(zero), bite);
        AssertIterEquals(bite.sortDescendingBy(zero), bite);

        Func2<T, T, Integer> badZipper = new Func2<T, T, Integer>() {
            @Override
            public Integer apply(T value1, T value2) {
                Assert.assertTrue(false);
                return 0;
            }
        };



        AssertIterEquals(empty, Bite.from(empty).zipWith(bite, badZipper));
        AssertIterEquals(empty, bite.zipWith(empty, badZipper));
        AssertIterEquals(bite, bite.zipWith(inf, new Func2<T, Integer, T>() {
            @Override
            public T apply(T value1, Integer value2) {
                return value1;
            }
        }));

        AssertIterEquals(bite, bite.and(empty));
        AssertIterEquals(bite, Bite.from(empty).and(bite));

        Predicate<T> no = new Predicate<T>() {
            @Override
            public Boolean apply(T value) {
                return false;
            }
        };
        Predicate<T> yes = new Predicate<T>() {
            @Override
            public Boolean apply(T value) {
                return true;
            }
        };
        AssertIterEquals(empty, bite.filter(no));
        AssertIterEquals(bite, bite.filter(yes));

        Func<T, Integer> counter = new Func<T, Integer>() {
            int n = 0;
            @Override
            public Integer apply(T value) {
                n++;
                return n;
            }
        };

        Func<Grouping<Integer, T>, Integer> groupSorter = new Func<Grouping<Integer, T>, Integer>() {
            @Override
            public Integer apply(Grouping<Integer, T> value) {
                return value.getKey();
            }
        };

        AssertIterEquals(bite, bite.groupBy(counter).map(new Func<Grouping<Integer, T>, T>() {
            @Override
            public T apply(Grouping<Integer, T> value) {
                return value.first();
            }
        }));
        Bite<T> reverseAndBack = bite
                .groupBy(counter)
                .sortDescendingBy(groupSorter)
                .map(new Func<Grouping<Integer, T>, T>() {
                    @Override
                    public T apply(Grouping<Integer, T> value) {
                        return value.first();
                    }
                })
                .groupBy(counter)
                .sortDescendingBy(groupSorter)
                .map(new Func<Grouping<Integer, T>, T>() {
                    @Override
                    public T apply(Grouping<Integer, T> value) {
                        return value.first();
                    }
                });

        AssertIterEquals(bite, reverseAndBack);

        AssertIterEquals(bite, bite.map(new Func<T, T>() {
            @Override
            public T apply(T value) {
                return value;
            }
        }));

        Assert.assertEquals(bite.toArrayList().size(), bite.count());

        Assert.assertTrue(bite.all(yes));
        Assert.assertFalse(bite.any(no));


    }

}
