package com.bigmap;

public class BigMaps {

    public static <K,V> BigMap<K,V> createBigMap(String aTableName)
    {
        return new BigMapImpl<K, V>(aTableName);
    }
}
