package com.dcrux.blubber.abstr.implementation.keyvalue;

import com.dcrux.blubber.abstr.implementables.IBinaryValueSvc;
import com.dcrux.blubber.abstr.implementables.keyvalue.*;
import com.dcrux.blubber.abstr.utils.BinaryComparator;
import com.dcrux.blubber.common.implementables.ICallable;
import com.dcrux.blubber.common.implementables.IExecutor;
import com.google.inject.Inject;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by caelis on 25/05/14.
 */
public class MemoryKeyValue implements IKvCreateValue, IKvDeleteValue, IKvGetValue, IKvIndexValue, IKvQueryIndex, IKvRemoveAllIndex, IKvRemoveIndex, IKvSetValue {

    @Inject
    private IBinaryValueSvc binaryValueSvc;

    private final AtomicLong counter = new AtomicLong(0);

    private final Map<byte[], byte[]> values = new TreeMap<>(BinaryComparator.SINGLETON);
    private final Map<byte[], ReverseInfo> reverseIndexInfo = new TreeMap<>(BinaryComparator.SINGLETON);
    private final Map<byte[], Map<byte[], NavigableMap<byte[], Set<byte[]>>>> indexes = new TreeMap<>(BinaryComparator.SINGLETON);

    private ICallable<byte[]> createValuesKey(byte[] bucket, byte[] key) {
        /* Key first, since key is fixed len */
        return this.binaryValueSvc.concat(key, bucket);
    }

    private class ReverseInfo {
        Map<byte[], Set<byte[]>> indexToValue = new TreeMap<>(BinaryComparator.SINGLETON);
    }

    private synchronized byte[] _createValue(IExecutor executor, byte[] bucket, byte[] value) throws Exception {
        byte[] key = executor.call(this.binaryValueSvc.fromLong(this.counter.getAndIncrement()));
        byte[] finalKey = executor.call(createValuesKey(bucket, key));
        this.values.put(finalKey, value);
        return key;
    }

    private synchronized void _deleteValue(IExecutor executor, byte[] bucket, byte[] key) throws Exception {
        byte[] finalKey = executor.call(createValuesKey(bucket, key));
        this.values.remove(finalKey);
        /* Remove from index */
        _removeAllIndex(executor, bucket, key, null);
    }

    private synchronized void _removeSingleIndex(byte[] bucket, byte[] key, byte[] indexName, byte[] indexValue) {
        Map<byte[], NavigableMap<byte[], Set<byte[]>>> debucket = this.indexes.get(bucket);
        if (debucket == null) {
            return;
        }
        NavigableMap<byte[], Set<byte[]>> deIndex = debucket.get(indexName);
        if (deIndex == null) {
            return;
        }
        Set<byte[]> devalued = deIndex.get(indexValue);
        if (devalued == null) {
            return;
        }
        devalued.remove(key);
    }

    private synchronized void _removeSingleReverse(IExecutor executor, byte[] bucket, byte[] key, byte[] indexName, byte[] indexValue) throws Exception {
        byte[] finalKey = executor.call(createValuesKey(bucket, key));
        ReverseInfo reverseInfo = this.reverseIndexInfo.get(finalKey);
        if (reverseInfo != null) {
            final Set<byte[]> singleIndex = reverseInfo.indexToValue.get(indexName);
            if (singleIndex != null) {
                singleIndex.remove(indexValue);
            }
        }
    }

    private synchronized void _removeAllIndex(IExecutor executor, byte[] bucket, byte[] key, @Nullable byte[] indexName) throws Exception {
        byte[] finalKey = executor.call(createValuesKey(bucket, key));
        ReverseInfo reverseInfo = this.reverseIndexInfo.get(finalKey);
        if (reverseInfo != null) {
            if (indexName == null) {
                for (final Map.Entry<byte[], Set<byte[]>> entry : reverseInfo.indexToValue.entrySet()) {
                    for (byte[] singleItem : entry.getValue()) {
                        _removeSingleIndex(bucket, key, entry.getKey(), singleItem);
                    }
                }
                this.reverseIndexInfo.clear();
            } else {
                final Set<byte[]> singleIndex = reverseInfo.indexToValue.get(indexName);
                if (singleIndex != null) {
                    for (byte[] singleItem : singleIndex) {
                        _removeSingleIndex(bucket, key, indexName, singleItem);
                    }
                    reverseInfo.indexToValue.remove(indexName);
                }
            }

            if (this.reverseIndexInfo.isEmpty())
                this.reverseIndexInfo.remove(finalKey);
        }
    }

    private synchronized byte[] _getValue(IExecutor executor, byte[] bucket, byte[] key) throws Exception {
        byte[] finalKey = executor.call(createValuesKey(bucket, key));
        return this.values.get(finalKey);
    }

    private synchronized void _setValue(IExecutor executor, byte[] bucket, byte[] key, byte[] value, boolean keepIndexes) throws Exception {
        if (!keepIndexes) {
            _removeAllIndex(executor, bucket, key, null);
        }
        byte[] finalKey = executor.call(createValuesKey(bucket, key));
        this.values.put(finalKey, value);
    }

    private synchronized void _addToReverseIndex(IExecutor executor, byte[] bucket, byte[] key, byte[] indexName, byte[] indexValue) throws Exception {
        byte[] finalKey = executor.call(createValuesKey(bucket, key));
        ReverseInfo reverseInfo = this.reverseIndexInfo.get(finalKey);
        if (reverseInfo == null) {
            reverseInfo = new ReverseInfo();
            this.reverseIndexInfo.put(finalKey, reverseInfo);
        }
        Set<byte[]> set = reverseInfo.indexToValue.get(indexName);
        if (set == null) {
            set = new TreeSet<>(BinaryComparator.SINGLETON);
            reverseInfo.indexToValue.put(indexName, set);
        }
        set.add(indexValue);
    }

    private synchronized void _addIndex(IExecutor executor, byte[] bucket, byte[] key, byte[] indexName, byte[] indexValue) throws Exception {
        Map<byte[], NavigableMap<byte[], Set<byte[]>>> debucket = this.indexes.get(bucket);
        if (debucket == null) {
            debucket = new TreeMap<>(BinaryComparator.SINGLETON);
            this.indexes.put(bucket, debucket);
        }
        NavigableMap<byte[], Set<byte[]>> deIndex = debucket.get(indexName);
        if (deIndex == null) {
            deIndex = new TreeMap<>(BinaryComparator.SINGLETON);
            debucket.put(indexName, deIndex);
        }
        Set<byte[]> devalued = deIndex.get(indexValue);
        if (devalued == null) {
            devalued = new TreeSet<>(BinaryComparator.SINGLETON);
            deIndex.put(indexValue, devalued);
        }
        devalued.add(key);

        /* Add to reverse */
        _addToReverseIndex(executor, bucket, key, indexName, indexValue);
    }

    private static final QueryResult EMPTY = new QueryResult(Collections.emptyList(), false);

    private synchronized QueryResult _query(byte[] bucket, byte[] indexName, byte[] from, byte[] to, boolean fromIncluded, boolean toIncluded, int limit) {
        Map<byte[], NavigableMap<byte[], Set<byte[]>>> debucket = this.indexes.get(bucket);
        if (debucket == null) {
            return EMPTY;
        }
        NavigableMap<byte[], Set<byte[]>> deIndex = debucket.get(indexName);
        if (deIndex == null) {
            return EMPTY;
        }

        List<byte[]> results = new ArrayList<>();
        NavigableMap<byte[], Set<byte[]>> subMap = deIndex.subMap(from, fromIncluded, to, toIncluded);
        if (subMap.isEmpty()) {
            return EMPTY;
        } else {
            for (Map.Entry<byte[], Set<byte[]>> entry : subMap.entrySet()) {
                results.addAll(entry.getValue());
                if (results.size() > limit) {
                    break;
                }
            }
            /* Need to cut? */
            final boolean overflow;
            if (results.size() > limit) {
                overflow = true;
                results = results.subList(0, limit);
            } else {
                overflow = false;
            }
            return new QueryResult(results, overflow);
        }
    }


    @Override
    public ICallable<byte[]> createValue(byte[] bucket, byte[] value) {
        return executor -> _createValue(executor, bucket, value);
    }

    @Override
    public ICallable<Void> deleteValue(byte[] bucket, byte[] key) {
        return executor -> {
            _deleteValue(executor, bucket, key);
            return null;
        };
    }

    @Override
    public ICallable<byte[]> getValue(byte[] bucket, byte[] key) {
        return executor -> _getValue(executor, bucket, key);
    }

    @Override
    public ICallable<Void> addIndex(byte[] bucket, byte[] key, byte[] indexName, byte[] indexValue) {
        return executor -> {
            _addIndex(executor, bucket, key, indexName, indexValue);
            return null;
        };
    }

    @Override
    public ICallable<QueryResult> query(byte[] bucket, byte[] indexName, byte[] from, byte[] to, boolean fromIncluded, boolean toIncluded, int limit) {
        return executor -> _query(bucket, indexName, from, to, fromIncluded, toIncluded, limit);
    }

    @Override
    public ICallable<Void> removeAllIndex(byte[] bucket, byte[] key, Optional<byte[]> indexName) {
        return executor -> {
            _removeAllIndex(executor, bucket, key, indexName.orElse(null));
            return null;
        };
    }

    @Override
    public ICallable<Void> removeIndex(byte[] bucket, byte[] key, byte[] indexName, byte[] indexValue) {
        return executor -> {
            _removeSingleIndex(bucket, key, indexName, indexValue);
            _removeSingleReverse(executor, bucket, key, indexName, indexValue);
            return null;
        };
    }

    @Override
    public ICallable<Void> setValue(byte[] bucket, byte[] key, byte[] value, boolean keepIndexes) {
        return executor -> {
            _setValue(executor, bucket, key, value, keepIndexes);
            return null;
        };
    }
}
