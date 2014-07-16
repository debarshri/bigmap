package com.bigmap.acceptance;

import com.bigmap.*;
import com.bigmap.conf.*;
import com.bigmap.hbase.*;
import org.apache.hadoop.hbase.*;

import java.util.*;

public class TimeSeriesTest {

    public static void  main(String[] args)
    {
        BigMapConfiguration.setConfiguration(HBaseConfiguration.create());
        BigMap<String, String> myTimeSeries = BigMaps.createBigHBaseMap("TimeSeries");
        myTimeSeries.clear();

        for(int i=0; i < 1000;i++)
        myTimeSeries.put(String.valueOf(i),"asdad");

        List<Map.Entry<String, String>> myScan = BigMapHBaseUtils.scan((BigMapHBase<String, String>) myTimeSeries,
                                                                       "10",
                                                                       "100");

        for(Map.Entry<String,String> key : myScan)
        {
            System.out.println(key.getKey() +" : "+key.getValue());
        }
    }
}
