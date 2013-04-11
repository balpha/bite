package de.balpha.bite.SpecializedBites;

import de.balpha.bite.Bite;
import de.balpha.bite.FunctionalInterfaces.Predicate;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FilteredBite<T> extends Bite<T> {
    private Iterable<T> mSource;
    private Predicate<? super T> mPredicate;

    public FilteredBite(Iterable<T> source, Predicate<? super T> predicate) {
        mSource = source;
        mPredicate = predicate;
    }

    @Override
    public Iterator<T> iterator() {
        return new FilteredIterator();
    }

    public class FilteredIterator implements Iterator<T> {
        private Iterator<T> mSourceIterator;

        private T mNext;
        private boolean mHasNext;
        private boolean mNextFound = false;

        public FilteredIterator() {
            mSourceIterator = mSource.iterator();
        }

        @Override
        public boolean hasNext() {
            if (mNextFound)
                return mHasNext;

            mHasNext = false;

            while (mSourceIterator.hasNext() && !mHasNext) {
                T val = mSourceIterator.next();
                if (mPredicate.apply(val)) {
                    mHasNext = true;
                    mNext = val;
                    mNextFound = true;
                }
            }

            if (!mHasNext) { // source iterator was exhausted
                mHasNext = false;
                mNext = null;
                mNextFound = true;
            }
            return mHasNext;
        }

        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();

            T result = mNext;
            mNext = null;
            mNextFound = false;
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}


