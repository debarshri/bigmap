package com.bigmap.mongo;

import com.bigmap.*;
import com.mongodb.*;

import java.util.*;

import static com.bigmap.conf.BigMapConfiguration.getMongoDBObject;
import static com.bigmap.utils.BigMapUtils.deserialize;
import static com.bigmap.utils.BigMapUtils.serialize;

public class BigMapMongoImpl<K,V> implements BigMap<K,V> {
    private String theDB;

    public BigMapMongoImpl(final String theCollectionName)
    {
        theDB = theCollectionName;
    }

    @Override
    public int size()
    {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isEmpty()
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean containsKey(final Object o)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean containsValue(final Object o)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public V get(final Object o)
    {
        DBCollection myColl = getMongoDBObject().getCollection(theDB);
        BasicDBObject myDBObject = new BasicDBObject("key",
                                                     Arrays.toString(serialize(o)));
        DBCursor myDBObjects = myColl.find(myDBObject);

        return (V) mongoDeserialize((String) myDBObjects.next().get("value"));

    }

    @Override
    public V put(
            final K aK,
            final V aV)
    {

        DBCollection myColl = getMongoDBObject().getCollection(theDB);
        BasicDBObject myDBObject = new BasicDBObject("key", Arrays.toString(serialize(aK)))
                .append("value",Arrays.toString(serialize(aV)));

        myColl.insert(myDBObject);
        return aV;  //To change body of implemented methods use File | Settings | File Templates.
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

    private static Object mongoDeserialize(String aSerializeObject)
    {
        String[] mySplit = aSerializeObject
                .substring(1,aSerializeObject.length() - 1)
                .split(",");

        byte [] myBytes = new byte[mySplit.length];

        for(int i=0; i < mySplit.length; i++)
        {
            myBytes[i] = Byte.parseByte(mySplit[i].trim());
        }

        return deserialize(myBytes);
    }
}

