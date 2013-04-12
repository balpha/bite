package de.balpha.bite.SpecializedBites;

import de.balpha.bite.Bite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CopyArrayListBite<T> extends Bite<T> {

    private ArrayList<T> mList;

    public CopyArrayListBite(ArrayList<T> source) {
        mList = source;
    }

    @Override
    public Bite<T> skip(int count) {
        return super.skip(count);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public T first() {
        if (mList.size() == 0)
            throw new NoSuchElementException();
        return mList.get(0);
    }

    @Override
    public T firstOr(T other) {
        if (mList.size() == 0)
            return other;
        return mList.get(0);
    }

    @Override
    public long count() {
        return mList.size();
    }

    @Override
    public Iterator<T> iterator() {
        return mList.iterator();
    }
}
