package de.balpha.bite.SpecializedBites;

import de.balpha.bite.Bite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CopyArrayBite<T> extends Bite<T> {

    private T[] mArray;

    public CopyArrayBite(T[] source) {
        mArray = source;
    }

    @Override
    public T first() {
        if (mArray.length == 0)
            throw new NoSuchElementException();
        return mArray[0];
    }

    @Override
    public T firstOr(T other) {
        if (mArray.length == 0)
            return other;
        return mArray[0];
    }

    @Override
    public long count() {
        return mArray.length;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<T> {
        private int nextPos = 0;

        @Override
        public boolean hasNext() {
            return nextPos < mArray.length;
        }

        @Override
        public T next() {
            T result = mArray[nextPos];
            nextPos++;
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
