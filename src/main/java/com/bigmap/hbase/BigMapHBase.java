package com.bigmap.hbase;

import com.bigmap.*;
import org.apache.hadoop.hbase.client.*;

public interface BigMapHBase<K,V> extends BigMap<K,V> {

    public HTable getHTable();

}
