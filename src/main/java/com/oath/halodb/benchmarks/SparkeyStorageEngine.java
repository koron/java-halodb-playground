package com.oath.halodb.benchmarks;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import com.spotify.sparkey.Sparkey;
import com.spotify.sparkey.SparkeyReader;
import com.spotify.sparkey.SparkeyWriter;
import com.spotify.sparkey.CompressionType;

public class SparkeyStorageEngine implements StorageEngine {

    File dir;
    File file;
    HashSet<SparkeyWriter> writerSet = new HashSet<>();
    HashSet<SparkeyReader> readerSet = new HashSet<>();

    public SparkeyStorageEngine(File dbDirectory, long noOfRecords) {
        dir = dbDirectory;
        file = new File(dir, "sparkey_data");
    }

    private final ThreadLocal<SparkeyWriter> writers = new ThreadLocal<SparkeyWriter>() {
        @Override
        protected SparkeyWriter initialValue() {
            try {
                var w = Sparkey.appendOrCreate(file, CompressionType.NONE, 0);
                writerSet.add(w);
                return w;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    };

    private final ThreadLocal<SparkeyReader> readers = new ThreadLocal<SparkeyReader>() {
        @Override
        protected SparkeyReader initialValue() {
            try {
                var r = Sparkey.open(file);
                readerSet.add(r);
                return r;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    };

    public void put(byte[] key, byte[] value) {
        try {
            writers.get().put(key, value);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public byte[] get(byte[] key) {
        try {
            return readers.get().getAsByteArray(key);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void open() {
        // nothing to do.
    }

    public void close() {
        for (SparkeyWriter w : writerSet) {
            try {
                w.writeHash();
                w.close();
            } catch (IOException ex) {
                // do nothings.
            }
        }
        for (SparkeyReader r : readerSet) {
            r.close();
        }
    }
}
