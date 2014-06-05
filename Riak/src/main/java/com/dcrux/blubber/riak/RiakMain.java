package com.dcrux.blubber.riak;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.IRiakObject;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.bucket.Bucket;
import com.basho.riak.client.builders.RiakObjectBuilder;
import com.basho.riak.client.query.indexes.IntIndex;
import com.dcrux.blubber.abstr.implementables.*;
import com.dcrux.blubber.abstr.implementables.keyvalue.*;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.common.implementables.IExternalExecutor;
import com.dcrux.blubber.common.implementation.SingleThreadExecutor;
import com.dcrux.blubber.iface.field.*;
import com.dcrux.blubber.iface.item.ItemId;
import com.dcrux.blubber.iface.itemdef.ItemDef;
import com.dcrux.blubber.iface.itemdef.ItemDefHash;
import com.dcrux.blubber.iface.itemdef.ItemDefId;
import com.dcrux.blubber.iface.itemdef.ItemDefIdOrHash;
import com.dcrux.blubber.iface.security.DomainId;
import com.dcrux.blubber.iface.security.UserId;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.Arrays;
import java.util.List;

/**
 * Created by caelis on 14/05/14.
 */
public class RiakMain {

    private static ICallable<Void> perform(boolean output, IKvCreateValue createValue, byte[] bucket, IKvGetValue getValue, IKvIndexValue indexValue,
                                           byte[] indexName, IKvQueryIndex queryIndex) {
        return executor -> {
            byte[] key = executor.call(createValue.createValue(bucket, new byte[]{20, 20}));
            if (output)
                System.out.println(Arrays.toString(key));
            byte[] value = executor.call(getValue.getValue(bucket, key));
            if (output)
                System.out.println("Value:" + Arrays.toString(value));
            byte[] valueInINdex = new byte[]{22, 32};
            executor.call(indexValue.addIndex(bucket, key, indexName, valueInINdex));
            if (output)
                System.out.println("Index set");
            QueryResult reply = executor.call(queryIndex.query(bucket, indexName, valueInINdex, valueInINdex, true, true, 10));
            if (output) {
                System.out.println("Number of results: " + reply.getKeys().size());
                System.out.println("Results: " + reply.getKeys());
            }
            return null;
        };
    }

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new DemoModule());
        IKvCreateValue createValue = injector.getInstance(IKvCreateValue.class);
        IKvGetValue getValue = injector.getInstance(IKvGetValue.class);
        IKvIndexValue indexValue = injector.getInstance(IKvIndexValue.class);
        IKvQueryIndex queryIndex = injector.getInstance(IKvQueryIndex.class);

        IDefinitionStorageSvc definitionStorageSvc = injector.getInstance(IDefinitionStorageSvc.class);
        IDefinitionHashGenSvc definitionHashGenSvc = injector.getInstance(IDefinitionHashGenSvc.class);
        ICreateItemSvc createItemSvc = injector.getInstance(ICreateItemSvc.class);
        IUserSvc userSvc = injector.getInstance(IUserSvc.class);
        IConnectionSvc connectionSvc = injector.getInstance(IConnectionSvc.class);
        ISetFieldValueSvc setFieldValueSvc = injector.getInstance(ISetFieldValueSvc.class);
        IGetFieldValueSvc getFieldValueSvc = injector.getInstance(IGetFieldValueSvc.class);
        ICommitSvc commitSvc = injector.getInstance(ICommitSvc.class);

        byte[] bucket = new byte[]{0};
        byte[] indexName = new byte[]{32, 34, 45};
        //IExecutor outerExecutor = new ExecutorBasedExecutor(new ExecutorCache());
        IExternalExecutor outerExecutor = new SingleThreadExecutor();

        if (false) {
            long beginTime0 = System.nanoTime();
            for (int i = 0; i < 1; i++) {
                outerExecutor.call(WorkExamples.combinedComputation(0));
            }
            long endTime0 = System.nanoTime();
            System.out.println("Processing Time (Simulation): " + (endTime0 - beginTime0) / 1000000 + "ms");
        }

        /* Test Blubran */

        /* Note: Currently only works with single thread executor */
        outerExecutor.call(executor -> {
            executor.call(userSvc.setRequestUser(new UserId("asdas")));
            executor.call(connectionSvc.setConnectionId(new byte[]{0, 3}));

            ItemDef itemDef = new ItemDef();
            FieldName stringName = new FieldName("aString");
            FieldDef fieldDef = new FieldDef(stringName, FieldType.string, null);
            itemDef.getFields().addFieldDef((short) 0, fieldDef);
            ICallable<ItemDefHash> hash = definitionHashGenSvc.getHash(itemDef);
            ItemDefHash hashEvaluated = executor.call(hash);
            ItemDefId defId = executor.call(definitionStorageSvc.setDefinition(hashEvaluated, itemDef));
            ItemDefId defId2 = executor.call(definitionStorageSvc.setDefinition(hashEvaluated, itemDef));

            System.out.println("Added new DefID: " + defId + ", 2 " + defId2);

            ItemId itemId = executor.call(createItemSvc.createItem(ItemDefIdOrHash.id(defId), DomainId.DEFAULT));
            System.out.println("CreatedItem in incubation: " + itemId);

            executor.call(setFieldValueSvc.setValue(itemId, new FieldNameIndex(stringName), new NullableValue(TypedValue.string("Hallo welt"))));
            System.out.println("Field value set");

            executor.call(commitSvc.commit(itemId));

            NullableValue value = executor.call(getFieldValueSvc.getValue(itemId, new FieldNameIndex(stringName)));
            if (value.isNull()) {
                System.out.println("Value is null");
            } else {
                System.out.println("Value : " + value.getTypedValue().getString());
            }
            return null;
        });

        /* END TEST BLUBRAN */

        System.out.println("BEGIN...");
        long beginTime = System.nanoTime();
        for (int i = 0; i < 60 / 3; i++) {
            outerExecutor.call(perform(false, createValue, bucket, getValue, indexValue, indexName, queryIndex),
                    perform(false, createValue, bucket, getValue, indexValue, indexName, queryIndex),
                    perform(false, createValue, bucket, getValue, indexValue, indexName, queryIndex));
        }
        long endTime = System.nanoTime();
        System.out.println("Processing Time: " + (endTime - beginTime) / 1000000 + "ms");

        outerExecutor.shutdown();

        if (true)
            return;

        IRiakClient client = RiakFactory.pbcClient();
        /*
        try {
            client.createBucket("daBucket").execute();
        } catch (RiakException re) {
        }*/
        Bucket myBucket = client.fetchBucket("test").execute();

        IRiakObject ro = RiakObjectBuilder.newBuilder("test", null)
                .withValue("Dies ist ein Inhalt im Riak").build();

        ro.addIndex("daIndex", 8888);
        ro.addIndex("daIndex", 8889);


        IRiakObject so = myBucket.store(ro).withoutFetch().returnBody(true).execute();
        System.out.println("Generated Key: " + so.getKey());

        //myBucket.fetch("dasd").execute().addIndex()


        ro.addIndex("daIndex", 8889);
        ro.addIndex("daIndex", 8889);

        IRiakObject so2 = myBucket.store(ro).withoutFetch().returnBody(true).execute();


        //IRiakObject so3 = myBucket.store(ro).withoutFetch().returnBody(true).execute();

        //myBucket.delete()


        IntIndex b_index = IntIndex.named("daIndex");
        //IndexQuery q = new IntRangeQuery(b_index, "test", 0l, 10l);

        //PBClientAdapter raw_client = new PBClientAdapter(client);
        List<String> result = myBucket.fetchIndex(b_index).from(8887l).to(9999l).execute();
        System.out.println("Results: ");
        System.out.println("Results: " + result);


        // IRiakObject fetched = myBucket.fetch("daKey").execute();
        // System.out.println("Fetched: " + fetched.getValueAsString());


        client.shutdown();

    }
}
