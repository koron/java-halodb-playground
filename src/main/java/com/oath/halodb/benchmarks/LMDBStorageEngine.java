package com.oath.halodb.benchmarks;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashSet;

import org.lmdbjava.Dbi;
import org.lmdbjava.Env;
import org.lmdbjava.Txn;

import static org.lmdbjava.DbiFlags.MDB_CREATE;
import static org.lmdbjava.EnvFlags.MDB_NOSUBDIR;

public class LMDBStorageEngine implements StorageEngine {

    final Env<ByteBuffer> env;
    Dbi<ByteBuffer> dbi;

    public LMDBStorageEngine(File dbDirectory, long noOfRecords) {
        env = Env.create()
            .setMapSize(1024L * 1024 * 1024 * 20)
            .open(dbDirectory, MDB_NOSUBDIR);
    }

    @Override
    public void put(byte[] key, byte[] value) {
        var txn = wtxns.get();
        dbi.put(txn, bb(key), bb(value));
    }

    @Override
    public byte[] get(byte[] key) {
        var txn = rtxns.get();
        var val = dbi.get(txn, bb(key));
        if (val == null) {
            return null;
        }
        return val.array();
    }

    @Override
    public void open() {
        dbi = env.openDbi("db1", MDB_CREATE);
    }

    @Override
    public void close() {
        dbi.close();
        for (Txn txn : txnSet) {
            txn.close();
        }
    }

    private ByteBuffer bb(byte[] src) {
        var buf = ByteBuffer.allocateDirect(src.length);
        buf.put(src);
        buf.flip();
        return buf;
    }

    HashSet<Txn<ByteBuffer>> txnSet = new HashSet<>();

    private final ThreadLocal<Txn<ByteBuffer>> wtxns = new ThreadLocal<>() {
        @Override
        protected Txn<ByteBuffer> initialValue() {
            var w = env.txnWrite();
            txnSet.add(w);
            return w;
        }
    };

    private final ThreadLocal<Txn<ByteBuffer>> rtxns = new ThreadLocal<>() {
        @Override
        protected Txn<ByteBuffer> initialValue() {
            var r = env.txnRead();
            txnSet.add(r);
            return r;
        }
    };
}
