package de.balpha.bite.SpecializedBites;

import de.balpha.bite.Bite;
import de.balpha.bite.FunctionalInterfaces.Func;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class SortByBite<TKey extends Comparable<? super TKey>, TValue> extends Bite<TValue> {

    private Iterable<TValue> mSource;
    private Func<? super TValue, ? extends TKey> mKeyFunc;

    public SortByBite(Iterable<TValue> source, Func<? super TValue, ? extends TKey> keyFunc) {
        mSource = source;
        mKeyFunc = keyFunc;
    }

    @Override
    public Iterator<TValue> iterator() {
        List<TValue> list = Bite.from(mSource).toArrayList();
        Collections.sort(list, new Comparator<TValue>() {
            @Override
            public int compare(TValue o1, TValue o2) {
                return mKeyFunc.apply(o1).compareTo(mKeyFunc.apply(o2));
            }
        });
        return list.iterator();
    }
}
