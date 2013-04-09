package de.balpha.bite.SpecializedBites;

import de.balpha.bite.Bite;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SkipBite<T> extends Bite<T> {
    private Iterable<T> mSource;
    private int mCount;

    public SkipBite(Iterable<T> source, int count) {
        mSource = source;
        mCount = count;
    }

    @Override
    public Iterator<T> iterator() {
        return new SkipIterator();
    }

    public class SkipIterator implements Iterator<T> {
        private Iterator<T> mSourceIterator;
        private boolean mSkipped = false;

        public SkipIterator() {
            mSourceIterator = mSource.iterator();
        }

        private void doSkip() {
            int n = 0;
            while (n < mCount && mSourceIterator.hasNext()) {
                mSourceIterator.next();
                n++;
            }
            mSkipped = true;
        }

        @Override
        public boolean hasNext() {
            if (!mSkipped)
                doSkip();
            return mSourceIterator.hasNext();
        }

        @Override
        public T next() {
            if (!hasNext()) // this also skips
                throw new NoSuchElementException();
            return mSourceIterator.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}

