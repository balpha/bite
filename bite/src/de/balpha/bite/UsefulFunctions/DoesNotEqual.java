package de.balpha.bite.UsefulFunctions;

import de.balpha.bite.FunctionalInterfaces.Predicate;

public class DoesNotEqual<T> implements Predicate<T> {

    private T mObj;

    public DoesNotEqual(T obj) {
        mObj = obj;
    }

    @Override
    public Boolean apply(T value) {
        return mObj == null ? value != null : !mObj.equals(value);
    }
}
