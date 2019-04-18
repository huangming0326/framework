package com.newegg.framework.common.collections;


import java.util.*;

public abstract class KeyedCollectionBase<K, V> extends AbstractCollection<V> {

    private final KeyExtractor<? extends K, ? super V> keyExtractor;
    private final LinkedHashMap<K, V> map;

    public KeyedCollectionBase() {
        this.keyExtractor = null;
        this.map = new LinkedHashMap();
    }

    public KeyedCollectionBase(KeyExtractor<? extends K, ? super V> keyExtractor) {
        this.keyExtractor = keyExtractor;
        this.map = new LinkedHashMap();
    }

    public KeyedCollectionBase(Collection<? extends V> c) {
        if (c instanceof KeyedCollectionBase) {
            KeyedCollectionBase keyedCollection = (KeyedCollectionBase)c;
            this.keyExtractor = keyedCollection.keyExtractor;
        } else {
            this.keyExtractor = null;
        }

        this.map = new LinkedHashMap();
        this.addAll(c);
    }

    public KeyedCollectionBase(int initialCapacity) {
        this.keyExtractor = null;
        this.map = new LinkedHashMap(initialCapacity);
    }

    public Map<K, V> toMap() {
        return this.map;
    }

    public boolean add(V value) {
        K key = this.getKey(value);
        if (key == null) {
            throw new NullPointerException("key is null");
        } else {
            V oldValue = this.map.put(key, value);
            return value != oldValue;
        }
    }

    public Iterator<V> iterator() {
        return this.map.values().iterator();
    }

    public int size() {
        return this.map.size();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public void clear() {
        this.map.clear();
    }

    public String toString() {
        return this.map.toString();
    }

    protected K getKey(V value) {
        return this.keyExtractor == null ? (K) ((IKeyedObject)value).getKey() : this.keyExtractor.getKey(value);
    }
}
