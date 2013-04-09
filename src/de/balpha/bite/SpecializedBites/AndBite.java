package de.balpha.bite.SpecializedBites;

import de.balpha.bite.Bite;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AndBite<T> extends Bite<T> {

    private Iterable<T> mSource1;
    private Iterable<? extends T> mSource2;

    public AndBite(Iterable<T> source1, Iterable<? extends T> source2) {
        mSource1 = source1;
        mSource2 = source2;
    }

    @Override
    public Iterator<T> iterator() {
        return new AndIterator();
    }

    public class AndIterator implements Iterator<T> {

        private Iterator<? extends T> mSourceIterator;
        private boolean inSecond = false;

        public AndIterator() {
            mSourceIterator = mSource1.iterator();
        }

        @Override
        public boolean hasNext() {
            if (mSourceIterator.hasNext())
                return true;
            if (inSecond)
                return false;
            mSourceIterator = mSource2.iterator();
            inSecond = true;
            return mSourceIterator.hasNext();
        }

        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();

            return mSourceIterator.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
