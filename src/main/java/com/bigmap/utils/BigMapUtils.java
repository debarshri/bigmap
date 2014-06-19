package com.bigmap.utils;

import org.apache.hadoop.hbase.util.*;

import java.io.*;

public class BigMapUtils {

    public static byte[] serialize(Object obj) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o;
        try
        {
            o = new ObjectOutputStream(b);
            o.writeObject(obj);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return b.toByteArray();
    }

    public static Object deserialize(byte[] bytes) {
        if(bytes == null)
        {
            return null;
        }
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o;
        try
        {
            o = new ObjectInputStream(b);
            return o.readObject();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
