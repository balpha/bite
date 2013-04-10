package de.balpha.bite.SpecializedBites;

public class Grouping<TKey, TValue> extends CopyBite<TValue> {

    private TKey mKey;

    public TKey getKey() {
        return mKey;
    }

    public Grouping(TKey key, Iterable<TValue> contents) {
        super(contents);
        mKey = key;
    }

}
