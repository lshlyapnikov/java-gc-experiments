# UseEpsilonGC and EscapeAnalysis

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
