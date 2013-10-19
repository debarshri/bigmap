package com.bigmap.mongo;

import com.bigmap.utils.*;
import com.google.common.collect.*;
import com.mongodb.*;

import java.util.*;

import static com.bigmap.conf.BigMapConfiguration.*;
import static com.bigmap.mongo.BigMapMongoUtils.getKeyMongoDBObjects;
import static com.bigmap.mongo.BigMapMongoUtils.getValueMongoDBObjects;
import static com.bigmap.mongo.BigMapMongoUtils.mongoDeserialize;
import static com.bigmap.utils.BigMapUtils.*;

public class BigMapMongoImpl<K, V> implements BigMapMongo<K, V> {
    private String theDB;

    public BigMapMongoImpl(final String theCollectionName)
    {
        theDB = theCollectionName;
    }

    @Override
    public int size()
    {
        return (int) getMongoDB().getCollection(theDB).count();
    }

    @Override
    public boolean isEmpty()
    {
        return getMongoDB().getCollection(theDB).count() == 0L;
    }

    @Override
    public boolean containsKey(final Object o)
    {
        return getKeyMongoDBObjects(theDB,
                                    o) != null;
    }

    @Override
    public boolean containsValue(final Object o)
    {
        return getValueMongoDBObjects(theDB,
                                      o) != null;
    }

    @Override
    public V get(final Object o)
    {
        DBCursor myDBObjects = getKeyMongoDBObjects(theDB,
                                                    o);

        return (V) mongoDeserialize((String) myDBObjects.next().get("value"));

    }

    @Override
    public V put(
            final K aK,
            final V aV)
    {
        getMongoDB()
                .getCollection(theDB)
                .insert(new BasicDBObject("key",
                                          Arrays.toString(serialize(aK)))
                                .append("value",
                                        Arrays.toString(serialize(aV))));
        return aV;
    }

    @Override
    public V remove(final Object o)
    {
        getMongoDB()
                .getCollection(theDB)
                .findAndRemove(new BasicDBObject("key",
                                                 Arrays.toString(serialize(o))));
        return (V) o;
    }

    @Override
    public void putAll(final Map<? extends K, ? extends V> aMap)
    {
        for (Map.Entry<? extends K, ? extends V> myEntry : aMap.entrySet())
        {
            DBCollection myColl = getMongoDB().getCollection(theDB);
            BasicDBObject myDBObject = new BasicDBObject("key",
                                                         Arrays.toString(serialize(myEntry.getKey())))
                    .append("value",
                            Arrays.toString(serialize(myEntry.getValue())));
            myColl.insert(myDBObject);
        }
    }

    @Override
    public void clear()
    {
        getMongoDB().getCollection(theDB).drop();
    }

    @Override
    public Set<K> keySet()
    {
        Set<K> myKeySet = Sets.newHashSet();
        DBCursor myDBObjects = getMongoDB().getCollection(theDB).find();

        while(myDBObjects.hasNext())
        {
            DBObject myNext = myDBObjects.next();
            K myKey = (K) myNext.get("key");
            myKeySet.add(myKey);
        }

        return myKeySet;
    }

    @Override
    public Collection<V> values()
    {
        Set<V> myValueSet = Sets.newHashSet();
        DBCursor myDBObjects = getMongoDB().getCollection(theDB).find();

        while(myDBObjects.hasNext())
        {
            DBObject myNext = myDBObjects.next();
            V myValue = (V) myNext.get("value");
            myValueSet.add(myValue);
        }

        return myValueSet;
    }

    @Override
    public Set<Entry<K, V>> entrySet()
    {
        Set<Entry<K,V>> myKeyValue = Sets.newHashSet();
        DBCursor myDBObjects = getMongoDB().getCollection(theDB).find();

        while(myDBObjects.hasNext())
        {
            DBObject myNext = myDBObjects.next();
            K myKey = (K) myNext.get("key");
            V myValue = (V) myNext.get("value");
            myKeyValue.add(new BigMapEntry<K, V>(myKey,myValue));
        }

        return myKeyValue;
    }
}

