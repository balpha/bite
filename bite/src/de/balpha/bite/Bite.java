package de.balpha.bite;

import de.balpha.bite.FunctionalInterfaces.Func;
import de.balpha.bite.FunctionalInterfaces.Func2;
import de.balpha.bite.FunctionalInterfaces.Predicate;
import de.balpha.bite.SpecializedBites.*;
import de.balpha.bite.UsefulFunctions.DoesNotEqual;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public abstract class Bite<T> implements Iterable<T> {

    public static <U> Bite<U> from(Iterable<U> source) {
        checkSourceNotNull(source);
        if (source instanceof Bite)
            return (Bite<U>) source;
        if (source instanceof ArrayList)
            return new CopyArrayListBite<U>((ArrayList<U>)source);
        return new CopyBite<U>(source);
    }

    public static <U> Bite<U> from(U[] source) {
        checkSourceNotNull(source);
        return new CopyArrayBite<U>(source);
    }

    private static void checkSourceNotNull(Object source) {
        if (source == null)
            throw new IllegalArgumentException("source cannot be null");
    }

    public Bite<T> filter(Func<? super T, Boolean> predicate) {
        return new FilteredBite<T>(this, predicate);
    }

    public Bite<T> take(int count) {
        return new TakeBite<T>(this, count);
    }

    public Bite<T> skip(int count) {
        return new SkipBite<T>(this, count);
    }

    public <U> Bite<U> map(Func<? super T, ? extends U> func) {
        return new MapBite<T, U>(this, func);
    }

    public <U, V> Bite<V> zipWith(Iterable<U> other, Func2<? super T, ? super U, ? extends V> zipper) {
        return new ZipWithBite<T, U, V>(this, other, zipper);
    }

    public Bite<T> and(Iterable<? extends T> other) {
        return new AndBite<T>(this, other);
    }

    public <TKey> Bite<Grouping<TKey, T>> groupBy(Func<? super T, ? extends TKey> grouper) {
        return new GroupByBite<TKey, T>(this, grouper);
    }

    public <TKey extends Comparable<? super TKey>> Bite<T> sortBy(Func<? super T, TKey> keyFunc) {
        return new SortByBite<TKey, T>(this, keyFunc, false);
    }

    public <TKey extends Comparable<? super TKey>> Bite<T> sortDescendingBy(Func<? super T, TKey> keyFunc) {
        return new SortByBite<TKey, T>(this, keyFunc, true);
    }

    public Bite<T> except(T value) {
        return new FilteredBite<T>(this, new DoesNotEqual<T>(value));
    }

    @SuppressWarnings("LoopStatementThatDoesntLoop")
    public boolean any(Func<? super T, Boolean> predicate) {
        for (T v : this.filter(predicate)) {
            return true;
        }
        return false;
    }

    public boolean all(Func<? super T, Boolean> predicate) {
        for (T v : this) {
            if (!predicate.apply(v))
                return false;
        }
        return true;
    }

    @SuppressWarnings("LoopStatementThatDoesntLoop")
    public T first() {
        for (T v : this)
            return v;
        throw new NoSuchElementException();
    }

    @SuppressWarnings("LoopStatementThatDoesntLoop")
    public T firstOr(T other) {
        for (T v : this)
            return v;
        return other;
    }

    public long count() {
        long result = 0;
        for (T v : this)
            result++;
        return result;
    }

    public ArrayList<T> toArrayList() {
        ArrayList<T> result = new ArrayList<T>();
        for (T val : this)
            result.add(val);
        return result;
    }
}
