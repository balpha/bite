package de.balpha.bite.SpecializedBites;

import de.balpha.bite.Bite;
import de.balpha.bite.FunctionalInterfaces.Func;

import java.util.Iterator;

public class MapBite<TIn, TOut> extends Bite<TOut> {

    private Iterable<TIn> mSource;
    private Func<? super TIn, ? extends TOut> mFunc;

    public MapBite(Iterable<TIn> source, Func<? super TIn, ? extends TOut> func) {
        mSource = source;
        mFunc = func;
    }

    @Override
    public Iterator<TOut> iterator() {
        return new MapIterator();
    }

    public class MapIterator implements Iterator<TOut> {
        private Iterator<? extends TIn> mSourceIterator;

        public MapIterator() {
            mSourceIterator = mSource.iterator();
        }

        @Override
        public boolean hasNext() {
            return mSourceIterator.hasNext();
        }

        @Override
        public TOut next() {
            return mFunc.apply(mSourceIterator.next());
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
