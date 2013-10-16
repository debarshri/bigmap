package com.bigmap;

import com.google.common.collect.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.*;

import java.io.*;
import java.util.*;

import static com.bigmap.conf.BigMapConfiguration.getHBaseAdmin;
import static com.bigmap.hbase.BigMapHBase.createOrGet;

public class BigMapHBaseImpl<K, V> implements BigMap<K, V> {

    private static final String COLUMN_FAMILY = "BIGMAP";
    private static final String QUALIFIER = "VALUE";
    private HTable theHTable;
    private String theTableName;

    public BigMapHBaseImpl(final String aTableName)
    {
        theTableName = aTableName;
        theHTable = createOrGet(aTableName);
    }

    @Override
    public int size()
    {
        try
        {
            ResultScanner myScanner = theHTable.getScanner(Bytes.toBytes("BIGMAP"),
                                                           Bytes.toBytes("VALUE"));
            Iterator<Result> myIterator = myScanner.iterator();

            return Iterators.size(myIterator);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public boolean isEmpty()
    {
        try
        {
            return Iterators.size(theHTable.getScanner(Bytes.toBytes("BIGMAP"),
                                                       Bytes.toBytes("VALUE")).iterator()) == 0;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean containsKey(final Object o)
    {
        Get myGet = new Get(serialize(o));
        myGet.addFamily(Bytes.toBytes("BIGMAP"));
        try
        {
            Result myResult = theHTable.get(myGet);
            return !myResult.isEmpty();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsValue(final Object o)
    {
        try
        {
            ResultScanner myScanner = theHTable.getScanner(Bytes.toBytes("BIGMAP"),
                                                           Bytes.toBytes("VALUE"));

            for (Result myResult = myScanner.next(); myResult != null; myResult = myScanner.next())
            {
                if (Bytes.toString(myResult.getValue(Bytes.toBytes("BIGMAP"),
                                                     Bytes.toBytes("VALUE"))).equals(serialize(o)))
                {
                    myScanner.close();
                    return true;
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public V get(final Object o)
    {
        Get myGet = new Get(serialize(o));
        myGet.addFamily(Bytes.toBytes("BIGMAP"));
        try
        {
            Result myResult = theHTable.get(myGet);
            return (V) deserialize(myResult.getValue(Bytes.toBytes("BIGMAP"),
                                         Bytes.toBytes("VALUE")));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public V put(
            final K aK,
            final V aV)
    {
        Put myPut = new Put(serialize(aK));
        myPut.add(Bytes.toBytes(COLUMN_FAMILY),Bytes.toBytes(QUALIFIER), serialize(aV));
        try
        {
            theHTable.put(myPut);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return aV;
    }

    @Override
    public V remove(final Object o)
    {
        return (V) o;
    }

    @Override
    public void putAll(final Map<? extends K, ? extends V> aMap)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void clear()
    {
        try
        {
            getHBaseAdmin().deleteColumn(theTableName, "VALUE");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Set<K> keySet()
    {
        return Sets.newHashSet();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<V> values()
    {
        return Lists.newArrayList();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Set<Entry<K, V>> entrySet()
    {
        return Sets.newHashSet();  //To change body of implemented methods use File | Settings | File Templates.
    }

    private static byte[] serialize(Object obj) {
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

    private static Object deserialize(byte[] bytes) {
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
