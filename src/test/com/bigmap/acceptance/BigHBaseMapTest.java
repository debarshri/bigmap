package com.bigmap.acceptance;

import com.bigmap.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.hbase.*;
import org.junit.*;

import java.io.*;
import java.util.*;

import static com.bigmap.conf.BigMapConfiguration.deleteHBaseMap;
import static com.bigmap.conf.BigMapConfiguration.setConfiguration;
import static org.fest.assertions.Assertions.assertThat;

public class BigHBaseMapTest {

    private Map<String,String> theTestMap;

    @Before
    public void setUp() throws IOException
    {
        Configuration myEntries = HBaseConfiguration.create();

        setConfiguration(myEntries);

        deleteHBaseMap("testNewMap");

        theTestMap = BigMaps.createNewBigHBaseMap("testNewMap");

    }

    @Test
    public void shouldAllowNormalMapOperationsForString()
    {
        theTestMap.put("a","b");

        assertThat(theTestMap.get("a")).isEqualTo("b");
        assertThat(theTestMap.containsKey("a")).isTrue();
        assertThat(theTestMap.keySet()).hasSize(1);
        assertThat(theTestMap.size()).isEqualTo(1);
        assertThat(theTestMap.entrySet()).hasSize(1);
        assertThat(theTestMap.containsValue("b"));

        theTestMap.clear();

        assertThat(theTestMap.size()).isEqualTo(0);
    }

    @After
    public void tearDown() throws IOException
    {
        deleteHBaseMap("testNewMap");
    }
}
