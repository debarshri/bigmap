package com.bigmap;

public class BigMaps {

    public static <K,V> BigMap<K,V> createBigHBaseMap(String aTableName)
    {
        return new BigMapHBaseImpl<K, V>(aTableName);
    }
}
