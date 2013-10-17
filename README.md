bigmap
======

A java map implementation that wraps hbase, cassandra and mongodb. It makes migration of NoSQL databases easy.

Prerequisite
==========

You need a running hbase cluster and Java 1.7 and above.


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

