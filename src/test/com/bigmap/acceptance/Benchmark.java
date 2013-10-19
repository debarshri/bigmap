package com.bigmap.acceptance;

import com.bigmap.*;
import org.apache.hadoop.hbase.*;
import org.junit.*;

import static com.bigmap.conf.BigMapConfiguration.setConfiguration;
import static org.fest.assertions.Assertions.assertThat;

public class Benchmark {

    @Test
    public void HBaseMapBenchmark()
    {
        setConfiguration(HBaseConfiguration.create());

        long startTime = System.currentTimeMillis();

        String myTableMap = TestDataFactory.populateHBaseMap();

        long endTime = System.currentTimeMillis();

        System.out.println(String.format("Time taken to load 10000 records - %ds",(endTime - startTime)/1000));

        BigMap<String, String> myBigHBaseMap = BigMaps.createBigHBaseMap(myTableMap);

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
