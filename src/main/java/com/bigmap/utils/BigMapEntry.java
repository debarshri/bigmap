package com.bigmap.utils;

import java.util.*;

public class BigMapEntry<K,V> implements Map.Entry<K,V> {

    private final K theKey;
    private V theValue;

    public BigMapEntry(K aKey, V aValue)
    {
        theKey = aKey;
        theValue = aValue;
    }
    @Override
    public K getKey()
    {
        return theKey;
    }

    @Override
    public V getValue()
    {
        return theValue;
    }

    @Override
    public V setValue(final V aV)
    {
        theValue = aV;
        return theValue;
    }
}
