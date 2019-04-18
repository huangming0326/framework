package com.newegg.framework.common.collections;

public class KeyedCollection<T> extends KeyedCollectionBase<String, T> {
    public T get(String key) {
        return super.toMap().get(key);
    }

    public boolean hasKey(String key) {
        return super.toMap().containsKey(key);
    }
}
