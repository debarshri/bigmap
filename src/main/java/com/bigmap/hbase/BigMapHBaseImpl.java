package com.bigmap.hbase;

import com.bigmap.utils.*;
import com.google.common.collect.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.*;

import java.io.*;
import java.util.*;

import static com.bigmap.hbase.BigMapHBaseUtils.createOrGet;
import static com.bigmap.utils.BigMapUtils.deserialize;
import static com.bigmap.utils.BigMapUtils.serialize;
import static org.apache.hadoop.hbase.util.Bytes.toBytes;
import static org.apache.hadoop.hbase.util.Bytes.toString;

public class BigMapHBaseImpl<K, V> implements BigMapHBase<K, V> {

    protected static final String COLUMN_FAMILY = "bigmap";
    protected static final String QUALIFIER = "value";
    protected HTable theHTable;

    public BigMapHBaseImpl(final String aTableName)
    {
        theHTable = createOrGet(aTableName);
    }

    @Override
    public int size()
    {
        try
        {
            ResultScanner myScanner = getResults();
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
            return Iterators.size(theHTable.getScanner(toBytes(COLUMN_FAMILY),
                                                       toBytes(QUALIFIER)).iterator()) == 0;
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
        myGet.addFamily(toBytes(COLUMN_FAMILY));
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
            ResultScanner myScanner = getResults();

            for (Result myResult = myScanner.next(); myResult != null; myResult = myScanner.next())
            {
                if (deSerializeAndScan(o, myScanner, myResult))
                {
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
        myGet.addFamily(toBytes(COLUMN_FAMILY));
        try
        {
            Result myResult = theHTable.get(myGet);
            return (V) deserialize(myResult.getValue(toBytes(COLUMN_FAMILY),
                                                     toBytes(QUALIFIER)));
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

        myPut.add(toBytes(COLUMN_FAMILY),
                  toBytes(QUALIFIER), serialize(aV));
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
        Delete myDelete = new Delete(serialize(0));
        myDelete.deleteColumn(toBytes(COLUMN_FAMILY), toBytes(QUALIFIER));
        try
        {
            theHTable.delete(myDelete);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return (V) o;
    }

    @Override
    public void putAll(final Map<? extends K, ? extends V> aMap)
    {
        List<Put> myPuts = Lists.newArrayList();

        for(Map.Entry<? extends K,? extends V> aEntry : aMap.entrySet() )
        {
            updatePuts(myPuts, aEntry);
        }

        try
        {
            theHTable.put(myPuts);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void clear()
    {
        try
        {
            ResultScanner myScanner = getResults();

            for (Result myResult = myScanner.next(); myResult != null; myResult = myScanner.next())
            {
                Delete myDelete = new Delete(myResult.getRow());
                myDelete.deleteColumns(toBytes(COLUMN_FAMILY), toBytes(QUALIFIER));

                theHTable.delete(myDelete);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Set<K> keySet()
    {
        Set<K> myKeys = Sets.newHashSet();

        try
        {
            ResultScanner myScanner = getResults();

            for (Result myResult = myScanner.next(); myResult != null; myResult = myScanner.next())
            {
                myKeys.add((K) deserialize(myResult.getRow()));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return myKeys;
    }

    @Override
    public Collection<V> values()
    {
        List<V> myValues = Lists.newArrayList();

        try
        {
            ResultScanner myScanner = getResults();

            for (Result myResult = myScanner.next(); myResult != null; myResult = myScanner.next())
            {
                myValues.add((V) deserialize(myResult.getValue(toBytes(COLUMN_FAMILY),
                                                               toBytes(QUALIFIER))));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return myValues;
    }

    @Override
    public Set<Entry<K, V>> entrySet()
    {
        Set<Entry<K,V>> myKeys = Sets.newHashSet();

        try
        {
            ResultScanner myScanner = getResults();

            for (Result myResult = myScanner.next(); myResult != null; myResult = myScanner.next())
            {
                myKeys.add(new BigMapEntry<K, V>((K) deserialize(myResult.getRow()),
                                                 (V) deserialize(myResult.getValue(toBytes(COLUMN_FAMILY),
                                                                                   toBytes(QUALIFIER)))));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return myKeys;
    }

    private void updatePuts(
            final List<Put> aPuts,
            final Entry<? extends K, ? extends V> aEntry)
    {
        Put myPut = new Put(serialize(aEntry.getKey()));
        myPut.add(toBytes(COLUMN_FAMILY),
                  toBytes(QUALIFIER), serialize(aEntry.getValue()));
        aPuts.add(myPut);
    }

    private boolean deSerializeAndScan(
            final Object o,
            final ResultScanner aScanner,
            final Result aResult)
    {
        if (deserialize(aResult.getValue(toBytes(COLUMN_FAMILY),
                                         toBytes(QUALIFIER))).equals(o))
        {
            aScanner.close();
            return true;
        }
        return false;
    }

    private ResultScanner getResults() throws IOException
    {
        return theHTable.getScanner(toBytes(COLUMN_FAMILY),
                                    toBytes(QUALIFIER));
    }

    @Override
    public HTable getHTable()
    {
        return theHTable;
    }
}
