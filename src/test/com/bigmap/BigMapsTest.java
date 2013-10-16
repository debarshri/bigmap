package com.bigmap;

import com.bigmap.conf.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.hbase.*;
import org.junit.*;

import java.util.*;

import static org.fest.assertions.Assertions.assertThat;

public class BigMapsTest {

    @Ignore
    @Test
    public void shouldCreateBigMap() throws Exception
    {
        assertThat(BigMaps.createBigMap("testTable").getClass()).isEqualTo(BigMapImpl.class);
    }


    @Ignore
    @Test
    public void shouldCreateBigMapAsInstanceOfMap() throws Exception
    {
        Map<String, String> myBigMap = BigMaps.createBigMap("testTable");

        assertThat(myBigMap).isNull();
    }

    @Test
    public void shouldAddValues()
    {
        Configuration myEntries = HBaseConfiguration.create();
        BigMapConfiguration myConfiguration = new BigMapConfiguration(myEntries);

        Map<String,String> myMap = BigMaps.createBigMap("TestMap");

        myMap.put("key1","value1");

        assertThat(myMap.get("key1")).isEqualTo("value1");

        Map<TestObject,Integer> myIntegerMap = BigMaps.createBigMap("testIntegerMap");

        TestObject myTestObject = new TestObject();
        myIntegerMap.put(myTestObject,1);

        assertThat(myIntegerMap.get(myTestObject)).isEqualTo(1);

    }
}
