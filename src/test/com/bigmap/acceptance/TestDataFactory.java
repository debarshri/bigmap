package com.bigmap.acceptance;

import com.bigmap.*;

public class TestDataFactory {

    public static String populateHBaseMap()
    {
        String myTestMap = "testMap";
        BigMap<String, String> myBigHBaseMap = BigMaps.createBigHBaseMap(myTestMap);

        for(int i = 0; i < 10000; i++)
        {
           myBigHBaseMap.put(Integer.toString(i),Integer.toString(i*10));
        }

        return myTestMap;
    }
}
