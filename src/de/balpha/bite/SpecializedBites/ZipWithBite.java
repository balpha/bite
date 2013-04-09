package de.balpha.bite.SpecializedBites;

import de.balpha.bite.Bite;
import de.balpha.bite.FunctionalInterfaces.Func2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ZipWithBite<T1, T2, TOut> extends Bite<TOut> {

    private Func2<? super T1, ? super T2, ? extends TOut> mZipper;
    private Iterable<T1> mSource1;
    private Iterable<T2> mSource2;

    public ZipWithBite(Iterable<T1> source1, Iterable<T2> source2, Func2<? super T1, ? super T2, ? extends TOut> zipper) {
        mZipper = zipper;
        mSource1 = source1;
        mSource2 = source2;
    }

    @Override
    public Iterator<TOut> iterator() {
        return new ZipWithIterator();
    }

    public class ZipWithIterator implements Iterator<TOut> {

        private Iterator<T1> mSource1Iterator;
        private Iterator<T2> mSource2Iterator;

        public ZipWithIterator() {
            mSource1Iterator = mSource1.iterator();
            mSource2Iterator = mSource2.iterator();
        }

        @Override
        public boolean hasNext() {
            return mSource1Iterator.hasNext() && mSource2Iterator.hasNext();
        }

        @Override
        public TOut next() {
            if (!hasNext())
                throw new NoSuchElementException();

            return mZipper.apply(mSource1Iterator.next(), mSource2Iterator.next());
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
