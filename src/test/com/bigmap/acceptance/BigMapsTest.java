package com.bigmap.acceptance;

import com.bigmap.*;
import com.bigmap.conf.*;
import com.google.common.collect.*;
import com.mongodb.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.hbase.*;
import org.junit.*;

import java.io.*;
import java.net.*;
import java.util.*;

import static com.bigmap.conf.BigMapConfiguration.deleteHBaseMap;
import static com.bigmap.conf.BigMapConfiguration.deleteMongoMap;
import static com.bigmap.conf.BigMapConfiguration.setConfiguration;
import static org.fest.assertions.Assertions.assertThat;

public class BigMapsTest {

    @Before
    public void setUp() throws IOException
    {
        Configuration myEntries = HBaseConfiguration.create();
        MongoClient myMongoClient = new MongoClient();
        DB myTest = myMongoClient.getDB("test");

        setConfiguration(myTest);
        setConfiguration(myEntries);

        deleteHBaseMap("TestMap");
        deleteHBaseMap("testTable");
        deleteHBaseMap("testIntegerMap");

        deleteMongoMap("testMap");
        deleteMongoMap("testMap2");
        deleteMongoMap("testMap4");
        deleteMongoMap("testMap5");
    }

    @After
    public void tearDown() throws IOException
    {
        deleteHBaseMap("TestMap");
        deleteHBaseMap("testTable");
        deleteHBaseMap("testIntegerMap");

        deleteMongoMap("testMap");
        deleteMongoMap("testMap2");
        deleteMongoMap("testMap4");
        deleteMongoMap("testMap5");
    }

    @Test
    public void shouldCreateBigMapAsInstanceOfMap() throws Exception
    {
        Map<String, String> myBigMap = BigMaps.createBigHBaseMap("testTable");

        assertThat(myBigMap).isNotNull();
    }

    @Test
    public void shouldAddValues()
    {
        Map<String,String> myMap = BigMaps.createBigHBaseMap("TestMap");

        myMap.put("key1","value1");
        assertThat(myMap.get("key1")).isEqualTo("value1");
    }

    @Test
    public void shouldAddObjectsInHBase()
    {
        Map<TestObject,Integer> myIntegerMap = BigMaps.createBigHBaseMap("testIntegerMap");

        TestObject myTestObject = new TestObject(1);
        myIntegerMap.put(myTestObject,1);

        assertThat(myIntegerMap.get(myTestObject)).isEqualTo(1);
    }

    @Test
    public void shouldReturnSize()
    {
        Map<TestObject,Integer> myIntegerMap = BigMaps.createBigHBaseMap("testIntegerMap");
        myIntegerMap.put(new TestObject(1),1);

        assertThat(myIntegerMap).hasSize(1);
    }

    @Test
    public void shouldReturnTrueForContainsValue()
    {
        Map<TestObject,Integer> myIntegerMap = BigMaps.createBigHBaseMap("testIntegerMap");
        myIntegerMap.put(new TestObject(1),1);

        assertThat(myIntegerMap.containsValue(1)).isTrue();
        assertThat(myIntegerMap.containsValue(2)).isFalse();
    }

    @Test
    public void shouldReturnTrueForContainsKey()
    {
        Map<TestObject,Integer> myIntegerMap = BigMaps.createBigHBaseMap("testIntegerMap");
        TestObject myTestObject = new TestObject(1);
        myIntegerMap.put(myTestObject,1);

        assertThat(myIntegerMap.containsKey(myTestObject)).isTrue();
        assertThat(myIntegerMap.containsKey(new TestObject(2))).isFalse();
    }

    @Test
    public void shouldReturnKeySet()
    {
        Map<TestObject,Integer> myIntegerMap = BigMaps.createBigHBaseMap("testIntegerMap");
        TestObject myTestObject = new TestObject(1);
        TestObject myTestObject2 = new TestObject(2);

        myIntegerMap.put(myTestObject,1);
        myIntegerMap.put(myTestObject2,3);

        assertThat(myIntegerMap.keySet()).hasSize(2);
    }

    @Test
    public void shouldReturnValueSet()
    {
        Map<TestObject,Integer> myIntegerMap = BigMaps.createBigHBaseMap("testIntegerMap");
        TestObject myTestObject = new TestObject(1);
        TestObject myTestObject2 = new TestObject(2);

        myIntegerMap.put(myTestObject,1);
        myIntegerMap.put(myTestObject2,3);

        assertThat(myIntegerMap.values()).hasSize(2);
    }

    @Test
    public void shouldReturnEntrySet()
    {
        Map<TestObject,Integer> myIntegerMap = BigMaps.createBigHBaseMap("testIntegerMap");
        TestObject myTestObject = new TestObject(1);
        TestObject myTestObject2 = new TestObject(2);

        myIntegerMap.put(myTestObject,1);
        myIntegerMap.put(myTestObject2,3);

        assertThat(myIntegerMap.entrySet()).hasSize(2);
    }

    @Ignore
    @Test
    public void shouldClearMap()
    {
        Map<TestObject,Integer> myIntegerMap = BigMaps.createBigHBaseMap("TestMap");
        myIntegerMap.clear();
        assertThat(myIntegerMap).hasSize(0);
    }

    /**
     *
     * MongoMap tests
     *
     * @throws UnknownHostException
     */
    @Test
    public void shouldPutAndGetFromMongoMap() throws UnknownHostException
    {
        Map<String, String> myTestMap = BigMaps.createBigMongoMap("testMap");
        myTestMap.put("key", "value");
        assertThat(myTestMap.get("key")).isEqualTo("value");
    }

    @Test
    public void shouldPersistObjects() throws UnknownHostException
    {
        Map<String, TestObject> myTestMap2 = BigMaps.createBigMongoMap("testMap2");
        myTestMap2.put("testKey", new TestObject(1));

        TestObject myTestObject = myTestMap2.get("testKey");

        assertThat(myTestObject.getI()).isEqualTo(1);
    }

    @Test
    public void shouldGiveSizeForMongoMap()
    {
        Map<String, TestObject> myTestMap2 = BigMaps.createBigMongoMap("testMap4");
        myTestMap2.put("testKey", new TestObject(1));

        assertThat(myTestMap2.size()).isEqualTo(1);
    }

    @Test
    public void shouldGiveEmptyForMongoMap()
    {
        Map<String, TestObject> myTestMap2 = BigMaps.createBigMongoMap("testMap5");

        assertThat(myTestMap2.size()).isEqualTo(0);
        assertThat(myTestMap2).isEmpty();
    }

    @Test
    public void shouldGiveKeySetForMongoMap()
    {
        Map<String, TestObject> myTestMap2 = BigMaps.createBigMongoMap("testMap5");

        String myTestKey = "testKey";
        myTestMap2.put(myTestKey,
                       new TestObject(1));
        String myOnlyElement = Iterables.getOnlyElement(myTestMap2.keySet());

        assertThat(myOnlyElement).isEqualTo(myTestKey);
    }

    @Test
    public void shouldGiveValueSetForMongoMap()
    {
        Map<String, TestObject> myTestMap2 = BigMaps.createBigMongoMap("testMap5");

        String myTestKey = "testKey";
        myTestMap2.put(myTestKey,
                       new TestObject(1));
        TestObject myOnlyElement = Iterables.getOnlyElement(myTestMap2.values());

        assertThat(myOnlyElement.getI()).isEqualTo(1);
    }

    @Test
    public void shouldGiveEntrySetKeyValueSetForMongoMap()
    {
        Map<String, TestObject> myTestMap2 = BigMaps.createBigMongoMap("testMap5");

        String myTestKey = "testKey";
        myTestMap2.put(myTestKey,
                       new TestObject(1));

        assertThat(Iterables.getOnlyElement(myTestMap2.entrySet())).isNotNull();
    }

    @Test
    public void allowingLamdajOperations()
    {

    }
}
