package de.balpha.bite.SpecializedBites;

import de.balpha.bite.Bite;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TakeBite<T> extends Bite<T> {
    private Iterable<T> mSource;
    private int mCount;

    public TakeBite(Iterable<T> source, int count) {
        mSource = source;
        mCount = count;
    }

    @Override
    public Iterator<T> iterator() {
        return new TakeIterator();
    }

    public class TakeIterator implements Iterator<T> {
        private Iterator<T> mSourceIterator;
        private int mTaken = 0;

        public TakeIterator() {
            mSourceIterator = mSource.iterator();
        }

        @Override
        public boolean hasNext() {
            return mTaken < mCount && mSourceIterator.hasNext();
        }

        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            mTaken++;
            return mSourceIterator.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}

