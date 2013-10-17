package com.bigmap.acceptance;

import com.bigmap.*;
import com.bigmap.conf.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.hbase.*;

import java.util.*;

public class BigMapExample {

    public static void main(String[] args)
    {
        Configuration myConfiguration = HBaseConfiguration.create();
        BigMapConfiguration.setConfiguration(myConfiguration);

        Map<String,String> myBigMap = BigMaps.createBigHBaseMap("ExampleTestMap");
    }
}
