package com.bigmap.hbase;

import com.bigmap.conf.*;
import org.apache.hadoop.hbase.client.*;

import java.io.*;

import static com.bigmap.conf.BigMapConfiguration.*;

public class BigMapHBase {

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
}

