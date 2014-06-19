package com.bigmap.acceptance;

import java.io.*;

public class TestObject implements Serializable {

    private int theI;

    public TestObject(final int i)
    {
        theI = i;
    }

    public int getI()
    {
        return theI;
    }
}
