package com.bigmap.acceptance;

import com.bigmap.*;
import com.bigmap.conf.*;
import com.mongodb.*;
import org.junit.*;

import java.net.*;
import java.util.*;

public class BigExample {

    @Test
    public void shouldPersistMapAsMongo() throws UnknownHostException
    {
        MongoClient myMongoClient = new MongoClient();
        DB myDB = myMongoClient.getDB("test");

        BigMapConfiguration.setConfiguration(myDB);

        Map<TestObject, TestObject> myTestMap = BigMaps.createBigMongoMap("testMap");

        myTestMap.put(new TestObject(2), new TestObject(3));
    }
}
