package com.bigmap.hbase;

import com.bigmap.conf.*;
import com.bigmap.utils.*;
import com.google.common.collect.*;
import org.apache.hadoop.hbase.client.*;

import java.io.*;
import java.util.*;

import static com.bigmap.conf.BigMapConfiguration.*;
import static com.bigmap.utils.BigMapUtils.*;
import static org.apache.hadoop.hbase.util.Bytes.toBytes;

public class BigMapHBaseUtils {

    public static HTable createOrGet(String aTableName)
    {
        try
        {
            if (getHBaseAdmin().tableExists(aTableName))
            {
                return new HTable(getConfiguration(), aTableName);
            }
            else
            {
              getHBaseAdmin().createTable(BigMapConfiguration.getHTableDescriptor(aTableName));
              return new HTable(getConfiguration(), aTableName);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> List<Map.Entry<String, T>> scan(BigMapHBase<String, T> aBigMap, String StartRow, String aEndRow)
    {

        HTable myHTable = aBigMap.getHTable();

        Scan scan = new Scan(serialize(StartRow), serialize(aEndRow));

       List<Map.Entry<String, T>> entries = Lists.newArrayList();
        try
        {
            ResultScanner myResultScanner = getResultScanner(myHTable,scan);
            for (Result myResult = myResultScanner.next(); myResult != null; myResult = myResultScanner.next())
            {
                entries.add(new BigMapEntry<String, T>((String) deserialize(myResult.getRow()),
                                                 (T) deserialize(myResult.getValue(toBytes("bigmap"),
                                                                                   toBytes("value")))));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return entries;
    }


    private static ResultScanner getResultScanner(HTable aHTable, Scan aScan) throws IOException
    {
        return aHTable.getScanner(aScan);
    }


}

