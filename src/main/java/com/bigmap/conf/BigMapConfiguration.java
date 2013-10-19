package com.bigmap.conf;

import com.mongodb.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;

import java.io.*;

public class BigMapConfiguration {

    private static final String COLUMN_FAMILY = "bigmap";
    private static Configuration theConfiguration;
    private static DB theMongoDBObject;

    public static void setConfiguration(Configuration aConfiguration)
    {
        theConfiguration = aConfiguration;
    }

    public static void setConfiguration(DB aMongoDBObject)
    {
        theMongoDBObject = aMongoDBObject;
    }

    public static DB getMongoDB()
    {
        return theMongoDBObject;
    }

    public static HBaseAdmin getHBaseAdmin() throws MasterNotRunningException, ZooKeeperConnectionException
    {
        return new HBaseAdmin(theConfiguration);
    }

    public static Configuration getConfiguration()
    {
        return theConfiguration;
    }

    public static HTableDescriptor getHTableDescriptor(String aTableName)
    {
        HTableDescriptor myHTableDescriptor = new HTableDescriptor(aTableName);
        myHTableDescriptor.addFamily(new HColumnDescriptor(COLUMN_FAMILY));

        return myHTableDescriptor;
    }

    public static void deleteHBaseMap(String aTable) throws IOException
    {
        if(getHBaseAdmin().tableExists(aTable))
        {
            getHBaseAdmin().disableTable(aTable);
            getHBaseAdmin().deleteTable(aTable);
        }
    }

    public static void deleteMongoMap(String aCollection)
    {
        getMongoDB().getCollection(aCollection).drop();
    }
}
