package com.bigmap;

import com.bigmap.acceptance.*;
import com.bigmap.conf.*;
import com.mongodb.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.hbase.*;
import org.junit.*;

import java.net.*;
import java.util.*;

import static org.fest.assertions.Assertions.assertThat;

public class BigMapsTest {

    @Before
    public void setUp()
    {
        Configuration myEntries = HBaseConfiguration.create();
        BigMapConfiguration.setConfiguration(myEntries);
    }

    @Ignore
    @Test
    public void shouldCreateBigMapAsInstanceOfMap() throws Exception
    {
        Map<String, String> myBigMap = BigMaps.createBigHBaseMap("testTable");

        assertThat(myBigMap).isNull();
    }

    @Ignore
    @Test
    public void shouldAddValues()
    {
        Map<String,String> myMap = BigMaps.createBigHBaseMap("TestMap");

        myMap.put("key1","value1");

        assertThat(myMap.get("key1")).isEqualTo("value1");

        Map<TestObject,Integer> myIntegerMap = BigMaps.createBigHBaseMap("testIntegerMap");

        TestObject myTestObject = new TestObject(1);
        myIntegerMap.put(myTestObject,1);

        assertThat(myIntegerMap.get(myTestObject)).isEqualTo(1);

    }

    @Ignore
    @Test
    public void shouldReturnSize()
    {
        Map<TestObject,Integer> myIntegerMap = BigMaps.createBigHBaseMap("testIntegerMap");

        assertThat(myIntegerMap).hasSize(1);
    }

    @Ignore
    @Test
    public void shouldClearMap()
    {
        Map<TestObject,Integer> myIntegerMap = BigMaps.createBigHBaseMap("TestMap");

        myIntegerMap.clear();

        assertThat(myIntegerMap).hasSize(0);
    }

    @Test
    public void shouldPutAndGetFromMongoMap() throws UnknownHostException
    {
        MongoClient myMongoClient = new MongoClient();
        DB myTest = myMongoClient.getDB("test");
        BigMapConfiguration.setConfiguration(myTest);

        Map<String, String> myTestMap = BigMaps.createBigMongoMap("testMap");

        myTestMap.put("key", "value");

        assertThat(myTestMap.get("key")).isEqualTo("value");

        Map<String, TestObject> myTestMap2 = BigMaps.createBigMongoMap("testMap2");
        myTestMap2.put("testKey", new TestObject(1));

        TestObject myTestObject = myTestMap2.get("testKey");

        System.out.println(myTestObject.getI());
    }
}
