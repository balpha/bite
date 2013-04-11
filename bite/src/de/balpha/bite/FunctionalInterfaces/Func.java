package de.balpha.bite.FunctionalInterfaces;

public interface Func<TIn, TOut> {
    public TOut apply(TIn value);
}
