package com.bigmap.acceptance;

import com.bigmap.*;
import org.apache.hadoop.hbase.*;

import java.util.*;

import static com.bigmap.conf.BigMapConfiguration.setConfiguration;
import static org.fest.assertions.Assertions.assertThat;

public class Benchmark {

    public static void main(String[] args)
    {
        setConfiguration(HBaseConfiguration.create());

        long startTime = System.currentTimeMillis();

        String myTableMap = TestDataFactory.populateHBaseMap();

        long endTime = System.currentTimeMillis();

        System.out.println(String.format("Time taken to load 10000 records - %dms",(endTime - startTime)));

        Map<String, String> myBigHBaseMap = BigMaps.createNewBigHBaseMap(myTableMap);

        long startTime2 = System.currentTimeMillis();

        String myValue = myBigHBaseMap.get("100");

        long endTime2 = System.currentTimeMillis();
        System.out.println(String.format("Time taken to get records - %dms",(endTime2 - startTime2)));

        assertThat(myValue).isEqualTo("1000");

        int mySize = myBigHBaseMap.size();
        System.out.println(mySize);
        assertThat(mySize).isNotNull();
    }
}
