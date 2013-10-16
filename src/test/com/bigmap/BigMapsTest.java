package com.bigmap;

import com.bigmap.acceptance.*;
import com.bigmap.conf.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.hbase.*;
import org.junit.*;

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
    public void shouldCreateBigMap() throws Exception
    {
        assertThat(BigMaps.createBigHBaseMap("testTable").getClass()).isEqualTo(BigMapHBaseImpl.class);
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

        TestObject myTestObject = new TestObject();
        myIntegerMap.put(myTestObject,1);

        assertThat(myIntegerMap.get(myTestObject)).isEqualTo(1);

    }

    @Ignore
    @Test
    public void shouldReturnSize()
    {
        Map<TestObject,Integer> myIntegerMap = BigMaps.createBigHBaseMap("testIntegerMap");

        assertThat(myIntegerMap).hasSize(3);
    }
}
