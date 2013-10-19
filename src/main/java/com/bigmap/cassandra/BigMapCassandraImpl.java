package com.bigmap.cassandra;

import java.util.*;

public class BigMapCassandraImpl<K,V> implements BigMapCassandra<K,V> {
    @Override
    public int size()
    {
        return 0;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }


    @Override
    public boolean containsKey(final Object o)
    {
        return false;
    }

    @Override
    public boolean containsValue(final Object o)
    {
        return false;
    }

    @Override
    public V get(final Object o)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public V put(
            final K aK,
            final V aV)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public V remove(final Object o)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putAll(final Map<? extends K, ? extends V> aMap)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void clear()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Set<K> keySet()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<V> values()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Set<Entry<K, V>> entrySet()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
