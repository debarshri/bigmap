package com.bigmap.acceptance;

import com.bigmap.*;

public class TestDataFactory {

    public static String populateHBaseMap()
    {
        String myTestMap = "testMap";
        BigMap<String, String> myBigHBaseMap = BigMaps.createNewBigHBaseMap(myTestMap);

        for(int i = 0; i < 100000; i++)
        {
           myBigHBaseMap.put(Integer.toString(i),Integer.toString(i*10));
        }

        return myTestMap;
    }
}
