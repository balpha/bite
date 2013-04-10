package de.balpha.bite.SpecializedBites;

import de.balpha.bite.Bite;
import de.balpha.bite.FunctionalInterfaces.Func;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class GroupByBite<TKey, TValue> extends Bite<Grouping<TKey, TValue>> {

    private Iterable<TValue> mSource;
    private Func<? super TValue, ? extends TKey> mGrouper;

    public GroupByBite(Iterable<TValue> source, Func<? super TValue, ? extends TKey> grouper) {
        mSource = source;
        mGrouper = grouper;
    }

    @Override
    public Iterator<Grouping<TKey, TValue>> iterator() {
        LinkedHashMap<TKey, LinkedList<TValue>> groups = new LinkedHashMap<TKey, LinkedList<TValue>>();
        TKey key;
        LinkedList<TValue> group;
        for (TValue val : mSource) {
            key = mGrouper.apply(val);
            if (groups.containsKey(key)) {
                group = groups.get(key);
            } else {
                group = new LinkedList<TValue>();
                groups.put(key, group);
            }
            group.add(val);
        }
        return Bite.from(groups.entrySet()).map(new Func<Map.Entry<TKey, LinkedList<TValue>>, Grouping<TKey, TValue>>() {
            @Override
            public Grouping<TKey, TValue> apply(Map.Entry<TKey, LinkedList<TValue>> entry) {
                return new Grouping<TKey, TValue>(entry.getKey(), entry.getValue());
            }
        }).iterator();
    }
}
