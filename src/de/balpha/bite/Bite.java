package de.balpha.bite;

import de.balpha.bite.FunctionalInterfaces.Func;
import de.balpha.bite.FunctionalInterfaces.Func2;
import de.balpha.bite.FunctionalInterfaces.Predicate;
import de.balpha.bite.SpecializedBites.*;

import java.util.ArrayList;

public abstract class Bite<T> implements Iterable<T> {

    public static <U> Bite<U> from(Iterable<U> source) {
        return new CopyBite<U>(source);
    }

    public Bite<T> filter(Predicate<? super T> predicate) {
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


    public boolean any(Predicate<? super T> predicate) {
        for (T v : this.filter(predicate)) {
            return true;
        }
        return false;
    }

    public boolean all(Predicate<? super T> predicate) {
        for (T v : this) {
            if (!predicate.apply(v))
                return false;
        }
        return true;
    }

    public ArrayList<T> ToArrayList() {
        ArrayList<T> result = new ArrayList<T>();
        for (T val : this)
            result.add(val);
        return result;
    }
}
