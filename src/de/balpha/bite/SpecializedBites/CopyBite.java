package de.balpha.bite.SpecializedBites;

import de.balpha.bite.Bite;

import java.util.Iterator;

public class CopyBite<T> extends Bite<T> {
    private Iterable<T> mSource;

    public CopyBite(Iterable<T> source) {
        mSource = source;
    }

    @Override
    public Iterator<T> iterator() {
        return mSource.iterator();
    }
}
