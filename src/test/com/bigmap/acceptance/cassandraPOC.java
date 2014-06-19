package com.bigmap.acceptance;

import com.netflix.astyanax.*;
import com.netflix.astyanax.connectionpool.*;
import com.netflix.astyanax.connectionpool.impl.*;
import com.netflix.astyanax.impl.*;
import com.netflix.astyanax.thrift.*;
import org.junit.*;

public class cassandraPOC {

    @Test
    public void poc()
    {

        AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
                .forCluster("Test Cluster")
                .forKeyspace("newspace")
                .withAstyanaxConfiguration(new AstyanaxConfigurationImpl()
                                                   .setDiscoveryType(NodeDiscoveryType.NONE)
                )
                .withConnectionPoolConfiguration(new ConnectionPoolConfigurationImpl("MyConnectionPool")
                                                         .setPort(9160)
                                                         .setMaxConnsPerHost(1)
                                                         .setSeeds("127.0.0.1:9160")
                )
                .withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
                .buildKeyspace(ThriftFamilyFactory.getInstance());

        context.start();
    }
}
