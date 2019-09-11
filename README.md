# java-halodb-playground

## Benchmarks

### HaloDB + sequencial keys

```console
$ ./gradlew benchmark_halodb -Pargs='tmp/halo-seq-001 FILL_SEQUENCE'

$ ./gradlew benchmark_halodb -Pargs='tmp/halo-seq-001 READ_RANDOM'
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

### Sparkey + sequencial keys

```console
$ mkdir -p ./tmp/sparkey-seq-001

$ ./gradlew benchmark_halodb -Pargs='tmp/sparkey-seq-001 FILL_SEQUENCE'

$ ./gradlew benchmark_halodb -Pargs='tmp/sparkey-seq-001 READ_RANDOM'
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
