# java-halodb-playground

## Benchmarks

### HaloDB + sequencial keys

```console
$ ./gradlew benchmark -Pargs='halodb tmp/halo-seq-001 FILL_SEQUENCE'

$ ./gradlew benchmark -Pargs='halodb tmp/halo-seq-001 READ_RANDOM'
```

Result:

```
Completed 20000000 reads with 8 threads in 37 seconds
Operations per second - 540540
Max value - 4501503
Average value - 14985.452888
95th percentile - 46527
99th percentile - 77951
99.9th percentile - 120831
99.99th percentile - 163711
```

Notes:

1,000,000個のキーの作成に15秒ほどかかる。

### LMDB + sequencial keys

```console
$ ./gradlew benchmark -Pargs='lmdb tmp/lmdb-seq-001 FILL_SEQUENCE

$ ./gradlew benchmark -Pargs='lmdb tmp/lmdb-seq-001 READ_RANDOM
```

```
Completed 20000000 reads with 8 threads in 12 seconds
Operations per second - 1666666
Max value - 284688383
Average value - 4783.480510
95th percentile - 5903
99th percentile - 23311
99.9th percentile - 37407
99.99th percentile - 56319
```

Notes:

1,000,000個のキーの作成に6秒ほどかかる。

### Sparkey + sequencial keys

```console
$ mkdir -p ./tmp/sparkey-seq-001

$ ./gradlew benchmark -Pargs='sparkey tmp/sparkey-seq-001 FILL_SEQUENCE'

$ ./gradlew benchmark -Pargs='sparkey tmp/sparkey-seq-001 READ_RANDOM'
```

Result:

```
Completed 20000000 reads with 8 threads in 5 seconds
Operations per second - 4000000
Max value - 39845887
Average value - 2067.809050
95th percentile - 4001
99th percentile - 5703
99.9th percentile - 10007
99.99th percentile - 36127
```

Notes:

1,000,000個のキーの作成に2~3秒ほどかかる。

### HaloDB + 100M records (100GB)

How to create 100M records:

```
$ ./gradlew benchmark -Pargs='halodb tmp/halo-seq-002 FILL_SEQUENCE'
Completed writing data in 1509
Write rate 64 MB/sec
Size of database 100000000
```

How to make 200M queries against that:

```
$ ./gradlew benchmark -Pargs='halodb tmp/halo-seq-002 READ_RANDOM'
```

*   ブートに時間がかかる(数十秒)
    *   たぶん1分近い
*   ディスクI/O(リード)で律速してそう
*   8M (1M x 8thread) queries in 135 seconds
    *   60K q/s ≒ 60MB/s read (writeと同じだ!)
    *   使ってるSSDはread 95K IOPS, write 84K IOPS
*   8 thread で使用メモリが 5GB
*   450 minutes くらいかかりそう
*   異常終了するとcollapseして復帰方法がない
