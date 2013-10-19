package com.bigmap.mongo;

import com.mongodb.*;

import java.util.*;

import static com.bigmap.conf.BigMapConfiguration.getMongoDB;
import static com.bigmap.utils.BigMapUtils.deserialize;
import static com.bigmap.utils.BigMapUtils.serialize;

class BigMapMongoUtils {

    static Object mongoDeserialize(String aSerializeObject)
    {
        String[] mySplit = aSerializeObject
                .substring(1,
                           aSerializeObject.length() - 1)
                .split(",");

        byte [] myBytes = new byte[mySplit.length];

        for(int i=0; i < mySplit.length; i++)
        {
            myBytes[i] = Byte.parseByte(mySplit[i].trim());
        }

        return deserialize(myBytes);
    }

    static DBCursor getKeyMongoDBObjects(String aDB, final Object o)
    {
        DBCollection myColl = getMongoDB().getCollection(aDB);
        BasicDBObject myDBObject = new BasicDBObject("key",
                                                     Arrays.toString(serialize(o)));
        return myColl.find(myDBObject);
    }

    static DBCursor getValueMongoDBObjects(String aDB, final Object o)
    {
        DBCollection myColl = getMongoDB().getCollection(aDB);
        BasicDBObject myDBObject = new BasicDBObject("value",
                                                     Arrays.toString(serialize(o)));
        return myColl.find(myDBObject);
    }
}
