# UseEpsilonGC and EscapeAnalysis

[Escape Analysis in the HotSpot JIT Compiler](https://blogs.oracle.com/javamagazine/post/escape-analysis-in-the-hotspot-jit-compiler)

## Requirements
```
export JAVA_HOME=/usr/lib/jvm/jdk-17
```

## Run with UseEpsilonGC and disabled DoEscapeAnalysis and get OutOfMemoryError
```
$JAVA_HOME/bin/java -Xms32M -Xmx32M -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC -XX:-DoEscapeAnalysis -XX:+PrintGCDetails -cp ./target/java-gc-experiments-1.0-SNAPSHOT.jar com.github.lshlyapnikov.ea.Rect
...
[0.081s][info   ][gc,metaspace] Metaspace: 1032M reserved, 384K (0.04%) committed, 170K (0.02%) used
[0.083s][info   ][gc          ] Heap: 32768K reserved, 32768K (100.00%) committed, 32704K (99.81%) used
[0.083s][info   ][gc,metaspace] Metaspace: 1032M reserved, 384K (0.04%) committed, 170K (0.02%) used
Terminating due to java.lang.OutOfMemoryError: Java heap space
```

## Run with UseEpsilonGC and enabled DoEscapeAnalysis and complete
```
$JAVA_HOME/bin/java -Xms32M -Xmx32M -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC -XX:+DoEscapeAnalysis -XX:+PrintGCDetails -cp ./target/java-gc-experiments-1.0-SNAPSHOT.jar com.github.lshlyapnikov.ea.Rect
...
Same area: 18074578
[3.148s][info   ][gc,heap,exit] Heap
[3.148s][info   ][gc,heap,exit] Epsilon Heap
[3.148s][info   ][gc,heap,exit] Allocation space:
[3.148s][info   ][gc,heap,exit]  space 32768K,  27% used [0x00000000fe000000, 0x00000000fe8e6428, 0x0000000100000000)
[3.148s][info   ][gc,heap,exit]  Metaspace       used 268K, committed 448K, reserved 1056768K
[3.148s][info   ][gc,heap,exit]   class space    used 10K, committed 128K, reserved 1048576K
[3.148s][info   ][gc          ] Heap: 32768K reserved, 32768K (100.00%) committed, 9113K (27.81%) used
[3.148s][info   ][gc,metaspace] Metaspace: 1032M reserved, 448K (0.04%) committed, 268K (0.03%) used
```

## Performance with enabled UseEpsilonGC
```
for i in $(seq 5); do \
    echo ""
    echo "Run: ${i}"
    time $JAVA_HOME/bin/java -Xms32M -Xmx32M -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC -XX:+DoEscapeAnalysis -XX:+AlwaysPreTouch -cp ./target/java-gc-experiments-1.0-SNAPSHOT.jar com.github.lshlyapnikov.ea.Rect
done

Run: 1
Same area: 18080330

real	0m3.320s
user	0m3.269s
sys	0m0.049s

Run: 2
Same area: 18078496

real	0m3.202s
user	0m3.187s
sys	0m0.024s

Run: 3
Same area: 18077310

real	0m3.162s
user	0m3.152s
sys	0m0.008s

Run: 4
Same area: 18079802

real	0m3.153s
user	0m3.158s
sys	0m0.008s

Run: 5
Same area: 18070565

real	0m3.133s
user	0m3.117s
sys	0m0.016s
```

### Descriptive stats
```
$ python3 describe.py 3320,3202,3162,3153,3133

Descriptive statistics and MAD
                 0
count     5.000000
mean   3194.000000
std      74.776333
min    3133.000000
25%    3153.000000
50%    3162.000000
75%    3202.000000
max    3320.000000
mad      53.6
```

## Performance without UseEpsilonGC, using the default GC
```
for i in $(seq 5); do \
    echo ""
    echo "Run: ${i}"
    time $JAVA_HOME/bin/java -Xms32M -Xmx32M -XX:+UnlockExperimentalVMOptions -XX:+DoEscapeAnalysis -XX:+AlwaysPreTouch -cp ./target/java-gc-experiments-1.0-SNAPSHOT.jar com.github.lshlyapnikov.ea.Rect
done

Run: 1
Same area: 18080633

real	0m3.093s
user	0m3.085s
sys	0m0.012s

Run: 2
Same area: 18081125

real	0m3.151s
user	0m3.133s
sys	0m0.057s

Run: 3
Same area: 18081260

real	0m3.120s
user	0m3.119s
sys	0m0.012s

Run: 4
Same area: 18083289

real	0m3.075s
user	0m3.074s
sys	0m0.016s

Run: 5
Same area: 18082351

real	0m3.123s
user	0m3.118s
sys	0m0.008s
```

### Descriptive stats
```
$ python3 describe.py 3093,3151,3120,3075,3123

Descriptive statistics and MAD
                0
count     5.00000
mean   3112.40000
std      29.30529
min    3075.00000
25%    3093.00000
50%    3120.00000
75%    3123.00000
max    3151.00000
mad      22.71999999999998
```
