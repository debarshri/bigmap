bigmap
======

A java map implementation that wraps hbase, cassandra and mongodb.
Allows people to cache objects in these NoSQL database via the native map interface.
It makes migration of NoSQL databases easy.

The idea is also to give Hazelcast run for its money.

Prerequisite
==========

You need a running hbase cluster, mongodb or cassandra and Java 1.7 and above.

The following example is for HBase-0.90.6 version.

Note : BigMap is optimized if you HBase is optimized.
It is not a magic bullet, it helps embedding these NoSQL seamlessly in java projects

Installation
============

- For maven users
    - Git clone https://github.com/debarshri/bigmap
    - mvn clean install -DskipTests
    - Dependency can be added as follows : 
   
```
<dependency>
   <groupId>com.bigmap</groupId>
    <artifactId>bigmap</artifactId>
    <version>1.2-SNAPSHOT</version>
</dependency>
```

- For non-maven users
    - Download the [released version](https://github.com/debarshri/bigmap/releases) and export the jar in the classpath.


Example
=======

```
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

        myBigMap.put("A-key","A-Value");

        String myValue = myBigMap.get("A-key");
    }
}
```

Persisting objects in BigMap

- You need to make the Object as serializable
- Persist it in the map like any other object


Sample Class
```
package com.bigmap.acceptance;

import java.io.*;

public class TestObject implements Serializable {

    private int a;
    
    public TestObject(int aA)
    {
        a = aA;
    }

    public int getA()
    {
        return a;
    }
}
```

Example

```
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

        Map<TestObject,Integer> myIntegerMap = BigMaps.createBigHBaseMap("testIntegerMap");

        TestObject myTestObject = new TestObject(1);
        
        myIntegerMap.put(myTestObject,1);
    }
}
```

Example

You can do the same with MongoDB


```
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
```



