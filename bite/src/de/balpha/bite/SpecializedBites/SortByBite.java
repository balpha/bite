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
    private Comparator<TValue> mComparator;

    private class AscendingComparator implements Comparator<TValue> {
        @Override
        public int compare(TValue o1, TValue o2) {
            return mKeyFunc.apply(o1).compareTo(mKeyFunc.apply(o2));
        }
    }
    private class DescendingComparator implements Comparator<TValue> {
        @Override
        public int compare(TValue o1, TValue o2) {
            return mKeyFunc.apply(o2).compareTo(mKeyFunc.apply(o1));
        }
    }


    public SortByBite(Iterable<TValue> source, Func<? super TValue, ? extends TKey> keyFunc, boolean descending) {
        mSource = source;
        mKeyFunc = keyFunc;
        mComparator = descending ? new DescendingComparator() : new AscendingComparator();
    }

    @Override
    public Iterator<TValue> iterator() {
        List<TValue> list = Bite.from(mSource).toArrayList();
        Collections.sort(list, mComparator);
        return list.iterator();
    }
}
