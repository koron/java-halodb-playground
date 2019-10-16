The first tests on Windows was failed on step 4.
The second tests on Linux.

## Step 1. `FILL_SEQUENCE`

Creating 1,000,000 records takes about 10 seconds.

```
$ ./gradlew benchmark -Pargs='halodb tmp/halo-seq-003 FILL_SEQUENCE'

Completed writing data in 124
Write rate 78 MB/sec
Size of database 10000000
```

## Step 2. `READ_RANDOM`

```
$ ./gradlew benchmark -Pargs='halodb tmp/halo-seq-003 READ_RANDOM'

Completed 20000000 reads with 8 threads in 12 seconds
Operations per second - 1666666
Max value - 40075263
Average value - 4914.473118
95th percentile - 7155
99th percentile - 78079
99.9th percentile - 211327
99.99th percentile - 507647
```

## Step 3. `RANDOM_UPDATE`

Updating 1,000,000 records takes about 50 seconds.

```
$ ./gradlew benchmark -Pargs='halodb tmp/halo-seq-003 RANDOM_UPDATE'

Completed over writing data in 486
Write rate 20 MB/sec
Size of database 10000000
```

## Step 4. `READ_RANDOM` again

```
$ ./gradlew benchmark -Pargs='halodb tmp/halo-seq-003 READ_RANDOM'

Completed 20000000 reads with 8 threads in 12 seconds
Operations per second - 1666666
Max value - 25640959
Average value - 4914.732803
95th percentile - 4887
99th percentile - 79231
99.9th percentile - 227199
99.99th percentile - 481535
```

## Step 5. `READ_AND_UPDATE`

```
$ ./gradlew benchmark -Pargs='halodb tmp/halo-seq-003 READ_AND_UPDATE'

Completed over writing data in 16
Write operations per second - 21810
Write rate 21 MB/sec
Size of database 10000000
Maximum time taken by a read thread to complete - 16
Completed 20000000 reads with 8 thread in 16 seconds
Read operations per second - 1250000
Max value - 31621119
Average value - 6226.423897
95th percentile - 4735
99th percentile - 86463
99.9th percentile - 322303
99.99th percentile - 4816895
```

## Step 6. `READ_RANDOM` again

```
$ ./gradlew benchmark -Pargs='halodb tmp/halo-seq-003 READ_RANDOM'

Completed 20000000 reads with 8 threads in 12 seconds
Operations per second - 1666666
Max value - 22953983
Average value - 4808.502620
95th percentile - 5803
99th percentile - 76671
99.9th percentile - 207743
99.99th percentile - 455679
```
