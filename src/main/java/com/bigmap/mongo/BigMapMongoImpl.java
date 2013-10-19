package com.bigmap.mongo;

import com.mongodb.*;

import java.util.*;

import static com.bigmap.conf.BigMapConfiguration.*;
import static com.bigmap.mongo.BigMapMongoUtils.getKeyMongoDBObjects;
import static com.bigmap.mongo.BigMapMongoUtils.getValueMongoDBObjects;
import static com.bigmap.mongo.BigMapMongoUtils.mongoDeserialize;
import static com.bigmap.utils.BigMapUtils.*;

public class BigMapMongoImpl<K,V> implements BigMapMongo<K,V> {
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

        DBCollection myColl = getMongoDB().getCollection(theDB);
        BasicDBObject myDBObject = new BasicDBObject("key", Arrays.toString(serialize(aK)))
                .append("value",Arrays.toString(serialize(aV)));

        myColl.insert(myDBObject);
        return aV;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public V remove(final Object o)
    {
        return null;
    }

    @Override
    public void putAll(final Map<? extends K, ? extends V> aMap)
    {
      //todo
    }

    @Override
    public void clear()
    {
       //todo
    }

    @Override
    public Set<K> keySet()
    {
        return null;
    }

    @Override
    public Collection<V> values()
    {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet()
    {
        return null;
    }
}

