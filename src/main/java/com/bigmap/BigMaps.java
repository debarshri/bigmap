package com.bigmap;

import com.bigmap.hbase.*;
import com.bigmap.mongo.*;

public class BigMaps {

    /**
     *
     * Returns a BigMap which implements Map,
     * When a TableName is given to the static method,
     * it checks if the table exists if not then it creates the table in HBase.
     * All original implementation of Map can be done on BigMap.
     *
     * @param aTableName
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K,V> BigMap<K,V> createBigHBaseMap(String aTableName)
    {
        return new BigMapHBaseImpl<K, V>(aTableName);
    }

    public static <K,V> BigMap<K,V> createNewBigHBaseMap(String aTableName)
    {
        return new BigMapHBaseImpl<K, V>(aTableName);
    }

    public static <K,V> BigMap<K,V> createBigMongoMap(String aTableName)
    {
        return new BigMapMongoImpl<K, V>(aTableName);
    }

    public static <K,V> BigMap<K,V> createCassandraMap(String aTableName)
    {
        return null;
    }
}
