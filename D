
----- pid 2756 at 2018-09-14 13:46:56 -----
Cmd line: com.roamingsoft.manager
Build fingerprint: 'Mobilead/m80/m80:7.0/04.04.20180125/1516857511:user/dev-keys'
ABI: 'arm'
Build type: optimized
Zygote loaded classes=4707 post zygote classes=54
Intern table: 48418 strong; 313 weak
JNI: CheckJNI is off; globals=465 (plus 159 weak)
Libraries: /data/app/com.roamingsoft.manager-2/lib/arm/libwifihelper.so /system/lib/libandroid.so /system/lib/libcompiler_rt.so /system/lib/libjavacrypto.so /system/lib/libjnigraphics.so /system/lib/libmedia_jni.so /system/lib/libwebviewchromium_loader.so libjavacore.so libopenjdk.so (9)
Heap: 52% free, 3MB/7MB; 37311 objects
Dumping cumulative Gc timings
Total number of allocations 37311
Total bytes allocated 3MB
Total bytes freed 0B
Free memory 3MB
Free memory until GC 3MB
Free memory until OOME 252MB
Total memory 7MB
Max memory 256MB
Zygote space size 1616KB
Total mutator paused time: 0
Total time waiting for GC to complete: 1.385us
Total GC count: 0
Total GC time: 0
Total blocking GC count: 0
Total blocking GC time: 0
Histogram of native allocation 0:623,128:239,256:117,384:61,640:1,896:25,1152:2 bucket size 128
Histogram of native free 0:27,64:1,96:28,192:215,288:37,480:9 bucket size 32
/data/app/com.roamingsoft.manager-2/oat/arm/base.odex: speed-profile
Current JIT code cache size: 0B
Current JIT data cache size: 0B
Current JIT capacity: 64KB
Current number of JIT code cache entries: 0
Total number of JIT compilations: 0
Total number of JIT compilations for on stack replacement: 0
Total number of deoptimizations: 0
Total number of JIT code cache collections: 0
Memory used for stack maps: Avg: 0B Max: 0B Min: -1B
Memory used for compiled code: Avg: 0B Max: 0B Min: -1B
Memory used for profiling info: Avg: 0B Max: 0B Min: -1B
Start Dumping histograms for 0 iterations for JIT timings
Done Dumping histograms
Memory used for compilation: Avg: 0B Max: 0B Min: -1B
ProfileSaver total_bytes_written=0
ProfileSaver total_number_of_writes=0
ProfileSaver total_number_of_code_cache_queries=0
ProfileSaver total_number_of_skipped_writes=0
ProfileSaver total_number_of_failed_writes=0
ProfileSaver total_ms_of_sleep=2000
ProfileSaver total_ms_of_work=0
ProfileSaver total_number_of_foreign_dex_marks=0
ProfileSaver max_number_profile_entries_cached=1
ProfileSaver total_number_of_hot_spikes=0
ProfileSaver total_number_of_wake_ups=0

suspend all histogram:	Sum: 2.096ms 99% C.I. 21us-1465.600us Avg: 209.600us Max: 1506us
DALVIK THREADS (12):
"Signal Catcher" daemon prio=5 tid=3 Runnable
  | group="system" sCount=0 dsCount=0 obj=0x12c00e50 self=0xe13b4800
  | sysTid=2762 nice=0 cgrp=default sched=0/0 handle=0xe99a7920
  | state=R schedstat=( 9652689 1158541 17 ) utm=0 stm=0 core=1 HZ=100
  | stack=0xe98ab000-0xe98ad000 stackSize=1014KB
  | held mutexes= "mutator lock"(shared held)
  (no managed stack frames)

"main" prio=5 tid=1 Native
  | group="main" sCount=1 dsCount=0 obj=0x75c494e8 self=0xea385400
  | sysTid=2756 nice=0 cgrp=default sched=0/0 handle=0xed871534
  | state=S schedstat=( 203403840 3938769 132 ) utm=14 stm=6 core=0 HZ=100
  | stack=0xff254000-0xff256000 stackSize=8MB
  | held mutexes=
  at android.os.BinderProxy.transactNative(Native method)
  at android.os.BinderProxy.transact(Binder.java:622)
  at android.net.wifi.IWifiManager$Stub$Proxy.disableNetwork(IWifiManager.java:1297)
  at android.net.wifi.WifiManager.disableNetwork(WifiManager.java:1096)
  at com.roamingsoft.manager.Manager.n(unavailable:-1)
  - locked <0x090d8971> (a java.util.ArrayList)
  at com.roamingsoft.manager.Manager.m(unavailable:-1)
  at com.roamingsoft.manager.Manager.a(unavailable:-1)
  at com.roamingsoft.manager.Manager.q(unavailable:-1)
  at com.roamingsoft.manager.Manager.onResume(unavailable:-1)
  at android.app.Instrumentation.callActivityOnResume(Instrumentation.java:1269)
  at android.app.Activity.performResume(Activity.java:6770)
  at android.app.ActivityThread.performResumeActivity(ActivityThread.java:3477)
  at android.app.ActivityThread.handleResumeActivity(ActivityThread.java:3546)
  at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2795)
  at android.app.ActivityThread.-wrap12(ActivityThread.java:-1)
  at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1527)
  at android.os.Handler.dispatchMessage(Handler.java:110)
  at android.os.Looper.loop(Looper.java:203)
  at android.app.ActivityThread.main(ActivityThread.java:6251)
  at java.lang.reflect.Method.invoke!(Native method)
  at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1063)
  at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:924)

"Jit thread pool worker thread 0" prio=5 tid=2 Native (still starting up)
  | group="" sCount=1 dsCount=0 obj=0x0 self=0xe0103a00
  | sysTid=2761 nice=9 cgrp=default sched=0/0 handle=0xe9aa8920
  | state=S schedstat=( 531922 0 2 ) utm=0 stm=0 core=0 HZ=100
  | stack=0xe99aa000-0xe99ac000 stackSize=1022KB
  | held mutexes=
  (no managed stack frames)

"ReferenceQueueDaemon" daemon prio=5 tid=4 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c00ee0 self=0xe13b5c00
  | sysTid=2763 nice=0 cgrp=default sched=0/0 handle=0xe98a8920
  | state=S schedstat=( 508462 662614 12 ) utm=0 stm=0 core=2 HZ=100
  | stack=0xe97a6000-0xe97a8000 stackSize=1038KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x090a2356> (a java.lang.Class<java.lang.ref.ReferenceQueue>)
  at java.lang.Daemons$ReferenceQueueDaemon.run(Daemons.java:150)
  - locked <0x090a2356> (a java.lang.Class<java.lang.ref.ReferenceQueue>)
  at java.lang.Thread.run(Thread.java:761)

"FinalizerDaemon" daemon prio=5 tid=5 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c00f70 self=0xe13b6100
  | sysTid=2764 nice=0 cgrp=default sched=0/0 handle=0xe97a3920
  | state=S schedstat=( 443617 762847 15 ) utm=0 stm=0 core=0 HZ=100
  | stack=0xe96a1000-0xe96a3000 stackSize=1038KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0a996fd7> (a java.lang.Object)
  at java.lang.Object.wait(Object.java:407)
  at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:188)
  - locked <0x0a996fd7> (a java.lang.Object)
  at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:209)
  at java.lang.Daemons$FinalizerDaemon.run(Daemons.java:205)
  at java.lang.Thread.run(Thread.java:761)

"FinalizerWatchdogDaemon" daemon prio=5 tid=6 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c180d0 self=0xe13b6600
  | sysTid=2766 nice=0 cgrp=default sched=0/0 handle=0xe969e920
  | state=S schedstat=( 271691 156616 11 ) utm=0 stm=0 core=2 HZ=100
  | stack=0xe959c000-0xe959e000 stackSize=1038KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0ab250c4> (a java.lang.Daemons$FinalizerWatchdogDaemon)
  at java.lang.Daemons$FinalizerWatchdogDaemon.sleepUntilNeeded(Daemons.java:270)
  - locked <0x0ab250c4> (a java.lang.Daemons$FinalizerWatchdogDaemon)
  at java.lang.Daemons$FinalizerWatchdogDaemon.run(Daemons.java:250)
  at java.lang.Thread.run(Thread.java:761)

"HeapTaskDaemon" daemon prio=5 tid=7 Blocked
  | group="system" sCount=1 dsCount=0 obj=0x12c18160 self=0xe13b6b00
  | sysTid=2767 nice=0 cgrp=default sched=0/0 handle=0xe4199920
  | state=S schedstat=( 299849 74385 8 ) utm=0 stm=0 core=2 HZ=100
  | stack=0xe4097000-0xe4099000 stackSize=1038KB
  | held mutexes=
  at dalvik.system.VMRuntime.runHeapTasks(Native method)
  - waiting to lock an unknown object
  at java.lang.Daemons$HeapTaskDaemon.run(Daemons.java:438)
  at java.lang.Thread.run(Thread.java:761)

"Binder:2756_1" prio=5 tid=8 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c18310 self=0xe0104e00
  | sysTid=2768 nice=0 cgrp=default sched=0/0 handle=0xe3e01920
  | state=S schedstat=( 4859311 1346768 18 ) utm=0 stm=0 core=0 HZ=100
  | stack=0xe3d05000-0xe3d07000 stackSize=1014KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/2768/stack)
  native: #00 pc 00049588  /system/lib/libc.so (__ioctl+8)
  native: #01 pc 0001aa3f  /system/lib/libc.so (ioctl+38)
  native: #02 pc 0003ccd7  /system/lib/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+202)
  native: #03 pc 0003cded  /system/lib/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+8)
  native: #04 pc 0003d36b  /system/lib/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+46)
  native: #05 pc 0004f99b  /system/lib/libbinder.so (???)
  native: #06 pc 0000e355  /system/lib/libutils.so (_ZN7android6Thread11_threadLoopEPv+140)
  native: #07 pc 00066fe7  /system/lib/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+102)
  native: #08 pc 00047fd3  /system/lib/libc.so (_ZL15__pthread_startPv+22)
  native: #09 pc 00019a3d  /system/lib/libc.so (__start_thread+6)
  (no managed stack frames)

"Binder:2756_2" prio=5 tid=9 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c183a0 self=0xea385e00
  | sysTid=2769 nice=0 cgrp=default sched=0/0 handle=0xd22d2920
  | state=S schedstat=( 2685922 266230 11 ) utm=0 stm=0 core=3 HZ=100
  | stack=0xd21d6000-0xd21d8000 stackSize=1014KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/2769/stack)
  native: #00 pc 00049588  /system/lib/libc.so (__ioctl+8)
  native: #01 pc 0001aa3f  /system/lib/libc.so (ioctl+38)
  native: #02 pc 0003ccd7  /system/lib/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+202)
  native: #03 pc 0003cded  /system/lib/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+8)
  native: #04 pc 0003d36b  /system/lib/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+46)
  native: #05 pc 0004f99b  /system/lib/libbinder.so (???)
  native: #06 pc 0000e355  /system/lib/libutils.so (_ZN7android6Thread11_threadLoopEPv+140)
  native: #07 pc 00066fe7  /system/lib/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+102)
  native: #08 pc 00047fd3  /system/lib/libc.so (_ZL15__pthread_startPv+22)
  native: #09 pc 00019a3d  /system/lib/libc.so (__start_thread+6)
  (no managed stack frames)

"Profile Saver" daemon prio=5 tid=10 Native
  | group="system" sCount=1 dsCount=0 obj=0x12c18670 self=0xe0106200
  | sysTid=2770 nice=0 cgrp=default sched=0/0 handle=0xd1de7920
  | state=S schedstat=( 14906383 2517385 16 ) utm=1 stm=0 core=1 HZ=100
  | stack=0xd1ceb000-0xd1ced000 stackSize=1014KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/2770/stack)
  native: #00 pc 00017424  /system/lib/libc.so (syscall+28)
  native: #01 pc 000b687d  /system/lib/libart.so (_ZN3art17ConditionVariable16WaitHoldingLocksEPNS_6ThreadE+92)
  native: #02 pc 00259c95  /system/lib/libart.so (_ZN3art12ProfileSaver3RunEv+296)
  native: #03 pc 0025aff5  /system/lib/libart.so (_ZN3art12ProfileSaver21RunProfileSaverThreadEPv+52)
  native: #04 pc 00047fd3  /system/lib/libc.so (_ZL15__pthread_startPv+22)
  native: #05 pc 00019a3d  /system/lib/libc.so (__start_thread+6)
  (no managed stack frames)

"ConnectivityThread" prio=5 tid=12 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c5e820 self=0xdf4c3800
  | sysTid=2773 nice=0 cgrp=default sched=0/0 handle=0xd167a920
  | state=S schedstat=( 329769 0 1 ) utm=0 stm=0 core=1 HZ=100
  | stack=0xd1578000-0xd157a000 stackSize=1038KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/2773/stack)
  native: #00 pc 00049448  /system/lib/libc.so (__epoll_pwait+20)
  native: #01 pc 00019ded  /system/lib/libc.so (epoll_pwait+60)
  native: #02 pc 00019e1d  /system/lib/libc.so (epoll_wait+12)
  native: #03 pc 00011c93  /system/lib/libutils.so (_ZN7android6Looper9pollInnerEi+118)
  native: #04 pc 00011b8f  /system/lib/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+26)
  native: #05 pc 00092a99  /system/lib/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+22)
  native: #06 pc 0064894d  /system/framework/arm/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+96)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"Timer-0" daemon prio=5 tid=13 TimedWaiting
  | group="main" sCount=1 dsCount=0 obj=0x12cef3a0 self=0xdf4c4200
  | sysTid=2776 nice=0 cgrp=default sched=0/0 handle=0xd1575920
  | state=S schedstat=( 4402306 2159462 27 ) utm=0 stm=0 core=0 HZ=100
  | stack=0xd1473000-0xd1475000 stackSize=1038KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0e204bad> (a java.util.TaskQueue)
  at java.lang.Object.wait(Object.java:407)
  at java.util.TimerThread.mainLoop(Timer.java:552)
  - locked <0x0e204bad> (a java.util.TaskQueue)
  at java.util.TimerThread.run(Timer.java:505)

----- end 2756 -----

----- pid 1122 at 2018-09-14 13:46:56 -----
Cmd line: system_server
Build fingerprint: 'Mobilead/m80/m80:7.0/04.04.20180125/1516857511:user/dev-keys'
ABI: 'arm64'
Build type: optimized
Zygote loaded classes=4375 post zygote classes=3395
Intern table: 52683 strong; 3829 weak
JNI: CheckJNI is off; globals=1434 (plus 172 weak)
Libraries: /system/lib64/libandroid.so /system/lib64/libandroid_servers.so /system/lib64/libcompiler_rt.so /system/lib64/libjavacrypto.so /system/lib64/libjnigraphics.so /system/lib64/libmedia_jni.so /system/lib64/libmediatek_exceptionlog.so /system/lib64/libsoundpool.so /system/lib64/libwebviewchromium_loader.so /system/lib64/libwifi-service.so /vendor/lib64/libnativecheck-jni.so libjavacore.so libopenjdk.so (13)
Heap: 3% free, 14MB/15MB; 188186 objects
Dumping cumulative Gc timings
Start Dumping histograms for 1 iterations for concurrent mark sweep
ProcessMarkStack:	Sum: 39.494ms 99% C.I. 0.003ms-39.012ms Avg: 13.164ms Max: 39.463ms
UpdateAndMarkImageModUnionTable:	Sum: 10.109ms 99% C.I. 2us-5004us Avg: 631.812us Max: 5249us
MarkConcurrentRoots:	Sum: 6.549ms 99% C.I. 0.007ms-6.535ms Avg: 3.274ms Max: 6.542ms
SweepMallocSpace:	Sum: 4.038ms 99% C.I. 0.009ms-4.010ms Avg: 2.019ms Max: 4.029ms
MarkRootsCheckpoint:	Sum: 1.720ms 99% C.I. 494us-1226us Avg: 860us Max: 1226us
ScanGrayZygoteSpaceObjects:	Sum: 1.025ms 99% C.I. 1.025ms-1.025ms Avg: 1.025ms Max: 1.025ms
ScanGrayImageSpaceObjects:	Sum: 733us 99% C.I. 1us-648us Avg: 45.812us Max: 692us
SweepSystemWeaks:	Sum: 610us 99% C.I. 610us-610us Avg: 610us Max: 610us
MarkAllocStackAsLive:	Sum: 550us 99% C.I. 550us-550us Avg: 550us Max: 550us
ReMarkRoots:	Sum: 456us 99% C.I. 456us-456us Avg: 456us Max: 456us
BindBitmaps:	Sum: 209us 99% C.I. 209us-209us Avg: 209us Max: 209us
MarkNonThreadRoots:	Sum: 200us 99% C.I. 61us-139us Avg: 100us Max: 139us
ProcessReferences:	Sum: 189us 99% C.I. 189us-189us Avg: 189us Max: 189us
FinishPhase:	Sum: 182us 99% C.I. 182us-182us Avg: 182us Max: 182us
ImageModUnionClearCards:	Sum: 154us 99% C.I. 1us-49us Avg: 4.812us Max: 49us
AllocSpaceClearCards:	Sum: 117us 99% C.I. 1us-60us Avg: 29.250us Max: 60us
SweepZygoteSpace:	Sum: 103us 99% C.I. 103us-103us Avg: 103us Max: 103us
SweepLargeObjects:	Sum: 84us 99% C.I. 84us-84us Avg: 84us Max: 84us
ProcessCards:	Sum: 69us 99% C.I. 30us-39us Avg: 34.500us Max: 39us
MarkingPhase:	Sum: 56us 99% C.I. 56us-56us Avg: 56us Max: 56us
EnqueueFinalizerReferences:	Sum: 49us 99% C.I. 49us-49us Avg: 49us Max: 49us
(Paused)ScanGrayImageSpaceObjects:	Sum: 39us 99% C.I. 1us-18us Avg: 2.437us Max: 18us
PreCleanCards:	Sum: 37us 99% C.I. 37us-37us Avg: 37us Max: 37us
(Paused)PausePhase:	Sum: 36us 99% C.I. 36us-36us Avg: 36us Max: 36us
InitializePhase:	Sum: 28us 99% C.I. 28us-28us Avg: 28us Max: 28us
SwapBitmaps:	Sum: 27us 99% C.I. 27us-27us Avg: 27us Max: 27us
RevokeAllThreadLocalAllocationStacks:	Sum: 26us 99% C.I. 26us-26us Avg: 26us Max: 26us
ReclaimPhase:	Sum: 16us 99% C.I. 16us-16us Avg: 16us Max: 16us
Sweep:	Sum: 15us 99% C.I. 15us-15us Avg: 15us Max: 15us
MarkRoots:	Sum: 10us 99% C.I. 10us-10us Avg: 10us Max: 10us
(Paused)ScanGrayZygoteSpaceObjects:	Sum: 9us 99% C.I. 9us-9us Avg: 9us Max: 9us
RecursiveMark:	Sum: 4us 99% C.I. 4us-4us Avg: 4us Max: 4us
SwapStacks:	Sum: 3us 99% C.I. 3us-3us Avg: 3us Max: 3us
FindDefaultSpaceBitmap:	Sum: 2us 99% C.I. 2us-2us Avg: 2us Max: 2us
(Paused)ProcessMarkStack:	Sum: 1us 99% C.I. 1us-1us Avg: 1us Max: 1us
Done Dumping histograms
concurrent mark sweep paused:	Sum: 690us 99% C.I. 690us-690us Avg: 690us Max: 690us
concurrent mark sweep total time: 67.213ms mean time: 67.213ms
concurrent mark sweep freed: 31758 objects with total size 1807KB
concurrent mark sweep throughput: 474000/s / 26MB/s
Start Dumping histograms for 11 iterations for partial concurrent mark sweep
ProcessMarkStack:	Sum: 441.744ms 99% C.I. 0.006ms-54.666ms Avg: 13.386ms Max: 54.666ms
MarkRootsCheckpoint:	Sum: 118.116ms 99% C.I. 0.504ms-41.440ms Avg: 5.368ms Max: 42.911ms
UpdateAndMarkImageModUnionTable:	Sum: 83.858ms 99% C.I. 1.478us-6480us Avg: 476.465us Max: 18669us
SweepMallocSpace:	Sum: 50.244ms 99% C.I. 0.013ms-7.052ms Avg: 2.283ms Max: 7.052ms
MarkConcurrentRoots:	Sum: 47.899ms 99% C.I. 0.006ms-8.181ms Avg: 2.177ms Max: 8.465ms
UpdateAndMarkZygoteModUnionTable:	Sum: 39.794ms 99% C.I. 2.281ms-8.046ms Avg: 3.617ms Max: 8.148ms
ScanGrayAllocSpaceObjects:	Sum: 26.925ms 99% C.I. 0.001ms-4.425ms Avg: 1.223ms Max: 4.425ms
ReMarkRoots:	Sum: 13.586ms 99% C.I. 0.425ms-1.682ms Avg: 1.235ms Max: 1.682ms
AllocSpaceClearCards:	Sum: 13.068ms 99% C.I. 1us-7523.999us Avg: 297us Max: 8776us
MarkAllocStackAsLive:	Sum: 8.257ms 99% C.I. 474us-1124us Avg: 750.636us Max: 1124us
ScanGrayImageSpaceObjects:	Sum: 6.936ms 99% C.I. 0.257us-2102us Avg: 39.409us Max: 2843us
SweepSystemWeaks:	Sum: 4.879ms 99% C.I. 144us-545us Avg: 443.545us Max: 545us
ImageModUnionClearCards:	Sum: 4.457ms 99% C.I. 1us-49.891us Avg: 12.661us Max: 3211us
(Paused)ScanGrayAllocSpaceObjects:	Sum: 4.352ms 99% C.I. 1us-2135us Avg: 197.818us Max: 2255us
MarkNonThreadRoots:	Sum: 3.581ms 99% C.I. 60us-460us Avg: 162.772us Max: 460us
(Paused)ScanGrayImageSpaceObjects:	Sum: 3.209ms 99% C.I. 1us-856us Avg: 18.232us Max: 1959us
EnqueueFinalizerReferences:	Sum: 3.202ms 99% C.I. 53us-587us Avg: 291.090us Max: 587us
ProcessReferences:	Sum: 1.842ms 99% C.I. 15us-340us Avg: 167.454us Max: 340us
FinishPhase:	Sum: 1.601ms 99% C.I. 76us-483.500us Avg: 145.545us Max: 497us
BindBitmaps:	Sum: 1.258ms 99% C.I. 107us-129us Avg: 114.363us Max: 129us
RevokeAllThreadLocalAllocationStacks:	Sum: 1.024ms 99% C.I. 20us-189us Avg: 93.090us Max: 189us
SweepLargeObjects:	Sum: 952us 99% C.I. 21us-255us Avg: 86.545us Max: 255us
MarkingPhase:	Sum: 726us 99% C.I. 55us-106us Avg: 66us Max: 106us
ProcessCards:	Sum: 673us 99% C.I. 23us-62us Avg: 30.590us Max: 62us
(Paused)PausePhase:	Sum: 493us 99% C.I. 36us-61us Avg: 44.818us Max: 61us
PreCleanCards:	Sum: 454us 99% C.I. 35us-47us Avg: 41.272us Max: 47us
ScanGrayZygoteSpaceObjects:	Sum: 418us 99% C.I. 9us-124us Avg: 38us Max: 124us
ZygoteModUnionClearCards:	Sum: 341us 99% C.I. 10us-77us Avg: 15.500us Max: 77us
ReclaimPhase:	Sum: 268us 99% C.I. 16us-37us Avg: 24.363us Max: 37us
(Paused)ScanGrayZygoteSpaceObjects:	Sum: 220us 99% C.I. 7us-127us Avg: 20us Max: 127us
InitializePhase:	Sum: 174us 99% C.I. 3us-22us Avg: 15.818us Max: 22us
Sweep:	Sum: 162us 99% C.I. 12us-16us Avg: 14.727us Max: 16us
SwapBitmaps:	Sum: 131us 99% C.I. 9us-14us Avg: 11.909us Max: 14us
MarkRoots:	Sum: 81us 99% C.I. 6us-11us Avg: 7.363us Max: 11us
(Paused)ProcessMarkStack:	Sum: 71us 99% C.I. 1us-32us Avg: 6.454us Max: 32us
RecursiveMark:	Sum: 39us 99% C.I. 3us-4us Avg: 3.545us Max: 4us
SwapStacks:	Sum: 38us 99% C.I. 2us-6us Avg: 3.454us Max: 6us
UnBindBitmaps:	Sum: 21us 99% C.I. 1us-2us Avg: 1.909us Max: 2us
SweepZygoteSpace:	Sum: 20us 99% C.I. 1us-2us Avg: 1.818us Max: 2us
FindDefaultSpaceBitmap:	Sum: 12us 99% C.I. 1us-2us Avg: 1.090us Max: 2us
PreSweepingGcVerification:	Sum: 11us 99% C.I. 1us-1us Avg: 1us Max: 1us
Done Dumping histograms
partial concurrent mark sweep paused:	Sum: 26.848ms 99% C.I. 0.672ms-7.252ms Avg: 2.440ms Max: 7.378ms
partial concurrent mark sweep total time: 885.137ms mean time: 80.467ms
partial concurrent mark sweep freed: 292423 objects with total size 19MB
partial concurrent mark sweep throughput: 330421/s / 21MB/s
Start Dumping histograms for 19 iterations for sticky concurrent mark sweep
ScanGrayAllocSpaceObjects:	Sum: 189.677ms 99% C.I. 0.004ms-24.448ms Avg: 2.495ms Max: 28.207ms
MarkRootsCheckpoint:	Sum: 145.798ms 99% C.I. 0.427ms-20.514ms Avg: 3.836ms Max: 20.514ms
FreeList:	Sum: 121.130ms 99% C.I. 6us-2217.499us Avg: 179.985us Max: 6645us
MarkConcurrentRoots:	Sum: 112.175ms 99% C.I. 0.007ms-18.640ms Avg: 2.951ms Max: 19.357ms
ProcessMarkStack:	Sum: 75.248ms 99% C.I. 1.583us-11996us Avg: 990.105us Max: 14036us
ScanGrayImageSpaceObjects:	Sum: 51.093ms 99% C.I. 0.265us-2394us Avg: 84.034us Max: 3484us
SweepArray:	Sum: 31.384ms 99% C.I. 0.680ms-2.888ms Avg: 1.651ms Max: 2.930ms
ReMarkRoots:	Sum: 22.875ms 99% C.I. 0.521ms-1.955ms Avg: 1.203ms Max: 1.955ms
AllocSpaceClearCards:	Sum: 21.720ms 99% C.I. 1.134us-9184us Avg: 285.789us Max: 14104us
SweepSystemWeaks:	Sum: 11.325ms 99% C.I. 164us-2290.750us Avg: 596.052us Max: 2367us
MarkingPhase:	Sum: 8.268ms 99% C.I. 265us-962us Avg: 435.157us Max: 962us
(Paused)ScanGrayAllocSpaceObjects:	Sum: 8.186ms 99% C.I. 1us-2569.999us Avg: 215.421us Max: 2914us
MarkNonThreadRoots:	Sum: 5.847ms 99% C.I. 61us-527us Avg: 153.868us Max: 527us
ScanGrayZygoteSpaceObjects:	Sum: 4.218ms 99% C.I. 7us-1621.999us Avg: 111us Max: 1841us
BindBitmaps:	Sum: 3.766ms 99% C.I. 107us-396us Avg: 198.210us Max: 396us
ImageModUnionClearCards:	Sum: 3.414ms 99% C.I. 1us-86.999us Avg: 5.615us Max: 657us
(Paused)ScanGrayImageSpaceObjects:	Sum: 3.355ms 99% C.I. 1us-601.999us Avg: 11.036us Max: 1213us
ProcessReferences:	Sum: 3.037ms 99% C.I. 8us-1779us Avg: 159.842us Max: 1903us
RecordFree:	Sum: 2.288ms 99% C.I. 1us-2041us Avg: 120.421us Max: 2249us
EnqueueFinalizerReferences:	Sum: 2.242ms 99% C.I. 5us-933.500us Avg: 118us Max: 998us
FinishPhase:	Sum: 2.054ms 99% C.I. 38us-914.500us Avg: 108.105us Max: 981us
RevokeAllThreadLocalAllocationStacks:	Sum: 1.755ms 99% C.I. 22us-149us Avg: 92.368us Max: 149us
ForwardSoftReferences:	Sum: 1.635ms 99% C.I. 2us-1367us Avg: 86.052us Max: 1461us
ResetStack:	Sum: 1.349ms 99% C.I. 17us-195us Avg: 71us Max: 195us
ProcessCards:	Sum: 1.294ms 99% C.I. 26us-74us Avg: 34.052us Max: 74us
(Paused)PausePhase:	Sum: 840us 99% C.I. 36us-72us Avg: 44.210us Max: 72us
PreCleanCards:	Sum: 752us 99% C.I. 34us-47us Avg: 39.578us Max: 47us
ZygoteModUnionClearCards:	Sum: 610us 99% C.I. 8us-40us Avg: 16.052us Max: 40us
InitializePhase:	Sum: 552us 99% C.I. 13us-88us Avg: 29.052us Max: 88us
ReclaimPhase:	Sum: 452us 99% C.I. 18us-39us Avg: 23.789us Max: 39us
(Paused)ProcessMarkStack:	Sum: 266us 99% C.I. 1us-77us Avg: 14us Max: 77us
(Paused)ScanGrayZygoteSpaceObjects:	Sum: 191us 99% C.I. 7us-24us Avg: 10.052us Max: 24us
MarkRoots:	Sum: 190us 99% C.I. 6us-49us Avg: 10us Max: 49us
SwapBitmaps:	Sum: 163us 99% C.I. 6us-22us Avg: 8.578us Max: 22us
UnBindBitmaps:	Sum: 72us 99% C.I. 3us-5us Avg: 3.789us Max: 5us
SwapStacks:	Sum: 60us 99% C.I. 3us-5us Avg: 3.157us Max: 5us
FindDefaultSpaceBitmap:	Sum: 45us 99% C.I. 1us-5us Avg: 2.368us Max: 5us
PreSweepingGcVerification:	Sum: 19us 99% C.I. 1us-1us Avg: 1us Max: 1us
Done Dumping histograms
sticky concurrent mark sweep paused:	Sum: 42.557ms 99% C.I. 1.094ms-4.874ms Avg: 2.239ms Max: 4.874ms
sticky concurrent mark sweep total time: 839.345ms mean time: 44.176ms
sticky concurrent mark sweep freed: 665452 objects with total size 44MB
sticky concurrent mark sweep throughput: 793149/s / 52MB/s
Total time spent in GC: 1.791s
Mean GC size throughput: 32MB/s
Mean GC object throughput: 552229 objects/s
Total number of allocations 1177612
Total bytes allocated 71MB
Total bytes freed 57MB
Free memory 579KB
Free memory until GC 579KB
Free memory until OOME 241MB
Total memory 15MB
Max memory 256MB
Zygote space size 1648KB
Total mutator paused time: 70.095ms
Total time waiting for GC to complete: 131.071us
Total GC count: 31
Total GC time: 1.791s
Total blocking GC count: 1
Total blocking GC time: 67.664ms
Histogram of GC count per 10000 ms: 0:8,1:9,4:1,5:1,12:1
Histogram of blocking GC count per 10000 ms: 0:19,1:1
Histogram of native allocation 0:4959,262144:2,524288:2 bucket size 65536
Histogram of native free 0:4069,524288:1 bucket size 65536
/system/priv-app/SettingsProvider/oat/arm64/SettingsProvider.odex: speed
/vendor/plugin/FwkPlugin/oat/arm64/FwkPlugin.odex: speed
/vendor/plugin/FwkPlugin/oat/arm64/FwkPlugin.odex: speed
/system/priv-app/Telecom/oat/arm64/Telecom.odex: speed
/system/framework/oat/arm64/com.android.location.provider.odex: speed
/system/priv-app/FusedLocation/oat/arm64/FusedLocation.odex: speed
/vendor/plugin/FwkPlugin/oat/arm64/FwkPlugin.odex: speed
/system/framework/oat/arm64/services.odex: speed
/system/framework/oat/arm64/ethernet-service.odex: speed
/system/framework/oat/arm64/wifi-service.odex: speed
Running non JIT

suspend all histogram:	Sum: 7.985ms 99% C.I. 37us-969.280us Avg: 190.119us Max: 971us
DALVIK THREADS (100):
"Signal Catcher" daemon prio=5 tid=2 Runnable
  | group="system" sCount=0 dsCount=0 obj=0x12c010d0 self=0x7a1040e000
  | sysTid=1127 nice=0 cgrp=default sched=0/0 handle=0x7a16408450
  | state=R schedstat=( 62289694 663307 14 ) utm=3 stm=4 core=1 HZ=100
  | stack=0x7a1630e000-0x7a16310000 stackSize=1005KB
  | held mutexes= "mutator lock"(shared held)
  (no managed stack frames)

"main" prio=5 tid=1 Native
  | group="main" sCount=1 dsCount=0 obj=0x762d4998 self=0x7a16c96a00
  | sysTid=1122 nice=0 cgrp=default sched=0/0 handle=0x7a1b22ca98
  | state=S schedstat=( 4573573158 1249630728 6319 ) utm=357 stm=100 core=0 HZ=100
  | stack=0x7fd06c6000-0x7fd06c8000 stackSize=8MB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at com.android.server.SystemServer.run(SystemServer.java:421)
  at com.android.server.SystemServer.main(SystemServer.java:275)
  at java.lang.reflect.Method.invoke!(Native method)
  at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1063)
  at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:924)

"ReferenceQueueDaemon" daemon prio=5 tid=3 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c01160 self=0x7a0d907000
  | sysTid=1128 nice=0 cgrp=default sched=0/0 handle=0x7a1630b450
  | state=S schedstat=( 21585627 1403535 123 ) utm=2 stm=1 core=0 HZ=100
  | stack=0x7a16209000-0x7a1620b000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0bc2bccb> (a java.lang.Class<java.lang.ref.ReferenceQueue>)
  at java.lang.Daemons$ReferenceQueueDaemon.run(Daemons.java:150)
  - locked <0x0bc2bccb> (a java.lang.Class<java.lang.ref.ReferenceQueue>)
  at java.lang.Thread.run(Thread.java:761)

"FinalizerDaemon" daemon prio=5 tid=4 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c011f0 self=0x7a0d907a00
  | sysTid=1129 nice=0 cgrp=default sched=0/0 handle=0x7a16206450
  | state=S schedstat=( 42188234 4919542 84 ) utm=3 stm=1 core=2 HZ=100
  | stack=0x7a16104000-0x7a16106000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x07726ca8> (a java.lang.Object)
  at java.lang.Object.wait(Object.java:407)
  at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:188)
  - locked <0x07726ca8> (a java.lang.Object)
  at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:209)
  at java.lang.Daemons$FinalizerDaemon.run(Daemons.java:205)
  at java.lang.Thread.run(Thread.java:761)

"FinalizerWatchdogDaemon" daemon prio=5 tid=5 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c01280 self=0x7a0d908e00
  | sysTid=1130 nice=0 cgrp=default sched=0/0 handle=0x7a16101450
  | state=S schedstat=( 1888082 10515846 37 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x7a15fff000-0x7a16001000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0ac3a3c1> (a java.lang.Daemons$FinalizerWatchdogDaemon)
  at java.lang.Daemons$FinalizerWatchdogDaemon.sleepUntilNeeded(Daemons.java:270)
  - locked <0x0ac3a3c1> (a java.lang.Daemons$FinalizerWatchdogDaemon)
  at java.lang.Daemons$FinalizerWatchdogDaemon.run(Daemons.java:250)
  at java.lang.Thread.run(Thread.java:761)

"HeapTaskDaemon" daemon prio=5 tid=6 Blocked
  | group="system" sCount=1 dsCount=0 obj=0x12c01310 self=0x7a0d90ac00
  | sysTid=1131 nice=0 cgrp=default sched=0/0 handle=0x7a15ffc450
  | state=S schedstat=( 1368125149 368219243 961 ) utm=127 stm=9 core=0 HZ=100
  | stack=0x7a15efa000-0x7a15efc000 stackSize=1037KB
  | held mutexes=
  at dalvik.system.VMRuntime.runHeapTasks(Native method)
  - waiting to lock an unknown object
  at java.lang.Daemons$HeapTaskDaemon.run(Daemons.java:438)
  at java.lang.Thread.run(Thread.java:761)

"Binder:1122_1" prio=5 tid=7 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c01670 self=0x7a0d90b600
  | sysTid=1134 nice=0 cgrp=default sched=0/0 handle=0x7a103ff450
  | state=S schedstat=( 412517002 387543305 1576 ) utm=26 stm=15 core=0 HZ=100
  | stack=0x7a10305000-0x7a10307000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1134/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Binder:1122_2" prio=5 tid=8 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c01700 self=0x7a10414400
  | sysTid=1135 nice=0 cgrp=default sched=0/0 handle=0x7a10302450
  | state=S schedstat=( 422739468 373737287 1524 ) utm=31 stm=11 core=0 HZ=100
  | stack=0x7a10208000-0x7a1020a000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1135/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"android.bg" prio=5 tid=10 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c5f670 self=0x7a16d62a00
  | sysTid=1138 nice=0 cgrp=default sched=0/0 handle=0x7a001ff450
  | state=S schedstat=( 2328635000 1189542783 858 ) utm=64 stm=168 core=0 HZ=100
  | stack=0x7a000fd000-0x7a000ff000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1138/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"ActivityManager" prio=5 tid=11 TimedWaiting
  | group="main" sCount=1 dsCount=0 obj=0x12c5c6a0 self=0x7a16d63400
  | sysTid=1139 nice=0 cgrp=default sched=0/0 handle=0x7a000fa450
  | state=S schedstat=( 788624552 809136004 2905 ) utm=57 stm=21 core=1 HZ=100
  | stack=0x79ffff8000-0x79ffffa000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0977b766> (a com.android.server.am.ActivityManagerService$6)
  at java.lang.Object.wait(Object.java:407)
  at com.android.server.am.ActivityManagerService.dumpStackTraces(ActivityManagerService.java:5586)
  - locked <0x0977b766> (a com.android.server.am.ActivityManagerService$6)
  at com.android.server.am.ActivityManagerService.dumpStackTraces(ActivityManagerService.java:5524)
  at com.android.server.am.ActivityManagerService$AnrActivityManagerService.dumpStackTraces(ActivityManagerService.java:23391)
  at com.mediatek.anrmanager.ANRManager$AnrDumpMgr.dumpAnrDebugInfoLocked(SourceFile:885)
  - locked <0x00be5aa7> (a com.mediatek.anrmanager.ANRManager$AnrDumpRecord)
  at com.mediatek.anrmanager.ANRManager$AnrDumpMgr.dumpAnrDebugInfo(SourceFile:763)
  at com.android.server.am.AppErrors.appNotResponding(AppErrors.java:880)
  - locked <0x00be5aa7> (a com.mediatek.anrmanager.ANRManager$AnrDumpRecord)
  at com.android.server.am.ActivityManagerService$15.run(ActivityManagerService.java:12968)
  at android.os.Handler.handleCallback(Handler.java:836)
  at android.os.Handler.dispatchMessage(Handler.java:103)
  at android.os.Looper.loop(Looper.java:203)
  at android.os.HandlerThread.run(HandlerThread.java:61)
  at com.android.server.ServiceThread.run(ServiceThread.java:46)

"android.ui" prio=5 tid=12 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c5c740 self=0x7a16d63e00
  | sysTid=1140 nice=0 cgrp=default sched=0/0 handle=0x79ffff5450
  | state=S schedstat=( 737160180 728961282 2744 ) utm=43 stm=30 core=3 HZ=100
  | stack=0x79ffef3000-0x79ffef5000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1140/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)
  at com.android.server.ServiceThread.run(ServiceThread.java:46)

"ActivityManager:kill" prio=5 tid=13 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c5c7e0 self=0x7a16d64800
  | sysTid=1141 nice=10 cgrp=bg_non_interactive sched=0/0 handle=0x79ffef0450
  | state=S schedstat=( 34275996 339057226 262 ) utm=0 stm=3 core=0 HZ=100
  | stack=0x79ffdee000-0x79ffdf0000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1141/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)
  at com.android.server.ServiceThread.run(ServiceThread.java:46)

"batterystats-sync" prio=5 tid=14 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c5ca60 self=0x7a16d65200
  | sysTid=1142 nice=0 cgrp=default sched=0/0 handle=0x79ffdeb450
  | state=S schedstat=( 37007537 35529927 69 ) utm=3 stm=0 core=0 HZ=100
  | stack=0x79ffce9000-0x79ffceb000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1142/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)
  at com.android.server.ServiceThread.run(ServiceThread.java:46)

"FileObserver" prio=5 tid=15 Native
  | group="main" sCount=1 dsCount=0 obj=0x12d7c940 self=0x7a16d65c00
  | sysTid=1143 nice=0 cgrp=default sched=0/0 handle=0x79ffce6450
  | state=S schedstat=( 1762613 5164306 16 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79ffbe4000-0x79ffbe6000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1143/stack)
  native: #00 pc 000000000006d288  /system/lib64/libc.so (read+4)
  native: #01 pc 000000000014abac  /system/lib64/libandroid_runtime.so (???)
  native: #02 pc 0000000000897708  /system/framework/arm64/boot-framework.oat (Java_android_os_FileObserver_00024ObserverThread_observe__I+132)
  at android.os.FileObserver$ObserverThread.observe(Native method)
  at android.os.FileObserver$ObserverThread.run(FileObserver.java:85)

"android.fg" prio=5 tid=16 Native
  | group="main" sCount=1 dsCount=0 obj=0x12d94100 self=0x7a16d66600
  | sysTid=1144 nice=0 cgrp=default sched=0/0 handle=0x79ffbe1450
  | state=S schedstat=( 92509081 38344304 155 ) utm=8 stm=1 core=0 HZ=100
  | stack=0x79ffadf000-0x79ffae1000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1144/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)
  at com.android.server.ServiceThread.run(ServiceThread.java:46)

"android.io" prio=5 tid=17 Native
  | group="main" sCount=1 dsCount=0 obj=0x12d941a0 self=0x7a00258000
  | sysTid=1145 nice=0 cgrp=default sched=0/0 handle=0x79ffadc450
  | state=S schedstat=( 3836002 3140691 16 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x79ff9da000-0x79ff9dc000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1145/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)
  at com.android.server.ServiceThread.run(ServiceThread.java:46)

"android.display" prio=5 tid=18 Native
  | group="main" sCount=1 dsCount=0 obj=0x12d94240 self=0x7a00258a00
  | sysTid=1146 nice=0 cgrp=default sched=0/0 handle=0x79ff9d7450
  | state=S schedstat=( 985268212 976377754 3409 ) utm=80 stm=18 core=0 HZ=100
  | stack=0x79ff8d5000-0x79ff8d7000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1146/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)
  at com.android.server.ServiceThread.run(ServiceThread.java:46)

"CpuTracker" prio=5 tid=19 TimedWaiting
  | group="main" sCount=1 dsCount=0 obj=0x12d7cb80 self=0x7a00259400
  | sysTid=1147 nice=0 cgrp=default sched=0/0 handle=0x79ff8d2450
  | state=S schedstat=( 19518768 14156310 25 ) utm=1 stm=0 core=1 HZ=100
  | stack=0x79ff7d0000-0x79ff7d2000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x04d5ab54> (a com.android.server.am.ActivityManagerService$5)
  at java.lang.Object.wait(Object.java:407)
  at com.android.server.am.ActivityManagerService$5.run(ActivityManagerService.java:2818)
  - locked <0x04d5ab54> (a com.android.server.am.ActivityManagerService$5)

"AnrMonitorThread" prio=5 tid=20 Native
  | group="main" sCount=1 dsCount=0 obj=0x12da93a0 self=0x7a00259e00
  | sysTid=1148 nice=0 cgrp=default sched=0/0 handle=0x79ff7cd450
  | state=S schedstat=( 616306 321385 5 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79ff6cb000-0x79ff6cd000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1148/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"PowerManagerService" prio=5 tid=21 Native
  | group="main" sCount=1 dsCount=0 obj=0x12d94880 self=0x7a0025a800
  | sysTid=1149 nice=-4 cgrp=default sched=0/0 handle=0x79ff6c8450
  | state=S schedstat=( 139908174 157275762 1797 ) utm=6 stm=7 core=0 HZ=100
  | stack=0x79ff5c6000-0x79ff5c8000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)
  at com.android.server.ServiceThread.run(ServiceThread.java:46)

"BatteryStats_wakeupReason" prio=5 tid=22 Native
  | group="main" sCount=1 dsCount=0 obj=0x12d949c0 self=0x7a0025b200
  | sysTid=1152 nice=-2 cgrp=default sched=0/0 handle=0x79ff3c9450
  | state=S schedstat=( 481154 318462 1 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79ff2c7000-0x79ff2c9000 stackSize=1037KB
  | held mutexes=
  at com.android.server.am.BatteryStatsService.nativeWaitWakeup(Native method)
  at com.android.server.am.BatteryStatsService.-wrap0(BatteryStatsService.java:-1)
  at com.android.server.am.BatteryStatsService$WakeupReasonThread.waitWakeup(BatteryStatsService.java:1069)
  at com.android.server.am.BatteryStatsService$WakeupReasonThread.run(BatteryStatsService.java:1054)

"PackageManager" prio=5 tid=23 Native
  | group="main" sCount=1 dsCount=0 obj=0x12f112e0 self=0x7a0025bc00
  | sysTid=1153 nice=10 cgrp=bg_non_interactive sched=0/0 handle=0x79ff2c4450
  | state=S schedstat=( 414000 267154 1 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79ff1c2000-0x79ff1c4000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)
  at com.android.server.ServiceThread.run(ServiceThread.java:46)

"PackageInstaller" prio=5 tid=24 Native
  | group="main" sCount=1 dsCount=0 obj=0x1323d550 self=0x7a0025c600
  | sysTid=1154 nice=0 cgrp=default sched=0/0 handle=0x79ff1bf450
  | state=S schedstat=( 532538 317538 1 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79ff0bd000-0x79ff0bf000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"SensorEventAckReceiver" prio=10 tid=25 Native
  | group="main" sCount=1 dsCount=0 obj=0x1323d9d0 self=0x7a002b0a00
  | sysTid=1156 nice=-8 cgrp=default sched=0/0 handle=0x79fed02450
  | state=S schedstat=( 854540 654000 7 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fec08000-0x79fec0a000 stackSize=1005KB
  | held mutexes=
  (no managed stack frames)

"SensorService" prio=10 tid=26 Native
  | group="main" sCount=1 dsCount=0 obj=0x1323da60 self=0x7a10436800
  | sysTid=1157 nice=-8 cgrp=default sched=0/0 handle=0x79fec05450
  | state=S schedstat=( 843461 444846 4 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x79feb0b000-0x79feb0d000 stackSize=1005KB
  | held mutexes=
  (no managed stack frames)

"CameraService_proxy" prio=5 tid=27 Native
  | group="main" sCount=1 dsCount=0 obj=0x12f44b00 self=0x7a00311e00
  | sysTid=1158 nice=-4 cgrp=default sched=0/0 handle=0x79feb08450
  | state=S schedstat=( 568231 66000 1 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fea06000-0x79fea08000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)
  at com.android.server.ServiceThread.run(ServiceThread.java:46)

"SettingsProvider" prio=5 tid=28 Native
  | group="main" sCount=1 dsCount=0 obj=0x1309c280 self=0x7a00312800
  | sysTid=1166 nice=10 cgrp=bg_non_interactive sched=0/0 handle=0x79fe9fc450
  | state=S schedstat=( 8111232 24059303 32 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fe8fa000-0x79fe8fc000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"AlarmManager" prio=5 tid=29 Native
  | group="main" sCount=1 dsCount=0 obj=0x1313e310 self=0x7a00313200
  | sysTid=1167 nice=0 cgrp=default sched=0/0 handle=0x79fe8ad450
  | state=S schedstat=( 66739159 103094070 169 ) utm=5 stm=1 core=0 HZ=100
  | stack=0x79fe7ab000-0x79fe7ad000 stackSize=1037KB
  | held mutexes=
  at com.android.server.AlarmManagerService.waitForAlarm(Native method)
  at com.android.server.AlarmManagerService.-wrap4(AlarmManagerService.java:-1)
  at com.android.server.AlarmManagerService$AlarmThread.run(AlarmManagerService.java:2947)

"InputDispatcher" prio=10 tid=30 Native
  | group="main" sCount=1 dsCount=0 obj=0x13253160 self=0x7a10548c00
  | sysTid=1168 nice=0 cgrp=default sched=0/0 handle=0x79fe464450
  | state=S schedstat=( 618272765 407582539 3155 ) utm=39 stm=22 core=0 HZ=100
  | stack=0x79fe36a000-0x79fe36c000 stackSize=1005KB
  | held mutexes=
  (no managed stack frames)

"InputReader" prio=10 tid=31 Native
  | group="main" sCount=1 dsCount=0 obj=0x132531f0 self=0x7a00272000
  | sysTid=1169 nice=-8 cgrp=default sched=0/0 handle=0x79fe362450
  | state=S schedstat=( 354668611 43126153 1006 ) utm=19 stm=16 core=0 HZ=100
  | stack=0x79fe268000-0x79fe26a000 stackSize=1005KB
  | held mutexes=
  (no managed stack frames)

"MountService" prio=5 tid=32 Native
  | group="main" sCount=1 dsCount=0 obj=0x132e71f0 self=0x7a00313c00
  | sysTid=1170 nice=0 cgrp=default sched=0/0 handle=0x79fdc1e450
  | state=S schedstat=( 98195926 30539766 98 ) utm=8 stm=1 core=1 HZ=100
  | stack=0x79fdb1c000-0x79fdb1e000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"VoldConnector" prio=5 tid=33 Native
  | group="main" sCount=1 dsCount=0 obj=0x132e7310 self=0x7a00314600
  | sysTid=1171 nice=0 cgrp=default sched=0/0 handle=0x79fdb12450
  | state=S schedstat=( 14178308 10672539 36 ) utm=1 stm=0 core=0 HZ=100
  | stack=0x79fda10000-0x79fda12000 stackSize=1037KB
  | held mutexes=
  at android.net.LocalSocketImpl.readba_native(Native method)
  at android.net.LocalSocketImpl.-wrap1(LocalSocketImpl.java:-1)
  at android.net.LocalSocketImpl$SocketInputStream.read(LocalSocketImpl.java:110)
  - locked <0x0025bafd> (a java.lang.Object)
  at com.android.server.NativeDaemonConnector.listenToSocket(NativeDaemonConnector.java:210)
  at com.android.server.NativeDaemonConnector.run(NativeDaemonConnector.java:143)
  at java.lang.Thread.run(Thread.java:761)

"CryptdConnector" prio=5 tid=34 Native
  | group="main" sCount=1 dsCount=0 obj=0x132e73a0 self=0x7a002ea400
  | sysTid=1172 nice=0 cgrp=default sched=0/0 handle=0x79fda06450
  | state=S schedstat=( 4585461 3785845 14 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x79fd904000-0x79fd906000 stackSize=1037KB
  | held mutexes=
  at android.net.LocalSocketImpl.readba_native(Native method)
  at android.net.LocalSocketImpl.-wrap1(LocalSocketImpl.java:-1)
  at android.net.LocalSocketImpl$SocketInputStream.read(LocalSocketImpl.java:110)
  - locked <0x0bb7e3f2> (a java.lang.Object)
  at com.android.server.NativeDaemonConnector.listenToSocket(NativeDaemonConnector.java:210)
  at com.android.server.NativeDaemonConnector.run(NativeDaemonConnector.java:143)
  at java.lang.Thread.run(Thread.java:761)

"RenderThread" prio=5 tid=35 Native
  | group="main" sCount=1 dsCount=0 obj=0x13329e50 self=0x7a00389200
  | sysTid=1173 nice=-4 cgrp=default sched=0/0 handle=0x79fe763450
  | state=S schedstat=( 770497211 225475244 2209 ) utm=61 stm=16 core=2 HZ=100
  | stack=0x79fe669000-0x79fe66b000 stackSize=1005KB
  | held mutexes=
  (no managed stack frames)

"hwuiTask1" prio=5 tid=36 Native
  | group="main" sCount=1 dsCount=0 obj=0x12f7ad30 self=0x79fe012400
  | sysTid=1186 nice=-2 cgrp=default sched=0/0 handle=0x79fcfff450
  | state=S schedstat=( 38805157 66047697 300 ) utm=0 stm=3 core=3 HZ=100
  | stack=0x79fcf05000-0x79fcf07000 stackSize=1005KB
  | held mutexes=
  (no managed stack frames)

"hwuiTask2" prio=5 tid=37 Native
  | group="main" sCount=1 dsCount=0 obj=0x12facdc0 self=0x7a00337600
  | sysTid=1187 nice=-2 cgrp=default sched=0/0 handle=0x79fcf02450
  | state=S schedstat=( 936000 592078 7 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fce08000-0x79fce0a000 stackSize=1005KB
  | held mutexes=
  (no managed stack frames)

"NetdConnector" prio=5 tid=38 Native
  | group="main" sCount=1 dsCount=0 obj=0x1308eee0 self=0x7a002eae00
  | sysTid=1188 nice=0 cgrp=default sched=0/0 handle=0x79fce05450
  | state=S schedstat=( 93378700 64538694 168 ) utm=9 stm=0 core=1 HZ=100
  | stack=0x79fcd03000-0x79fcd05000 stackSize=1037KB
  | held mutexes=
  at android.net.LocalSocketImpl.readba_native(Native method)
  at android.net.LocalSocketImpl.-wrap1(LocalSocketImpl.java:-1)
  at android.net.LocalSocketImpl$SocketInputStream.read(LocalSocketImpl.java:110)
  - locked <0x03ebfa43> (a java.lang.Object)
  at com.android.server.NativeDaemonConnector.listenToSocket(NativeDaemonConnector.java:210)
  at com.android.server.NativeDaemonConnector.run(NativeDaemonConnector.java:143)
  at java.lang.Thread.run(Thread.java:761)

"NetworkStats" prio=5 tid=39 Native
  | group="main" sCount=1 dsCount=0 obj=0x130f6820 self=0x7a002eb800
  | sysTid=1190 nice=0 cgrp=default sched=0/0 handle=0x79fc32e450
  | state=S schedstat=( 9943151 18136850 23 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x79fc22c000-0x79fc22e000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"NetworkPolicy" prio=5 tid=40 Native
  | group="main" sCount=1 dsCount=0 obj=0x1314edc0 self=0x7a002ec200
  | sysTid=1191 nice=0 cgrp=default sched=0/0 handle=0x79fc131450
  | state=S schedstat=( 18017541 43730771 86 ) utm=1 stm=0 core=2 HZ=100
  | stack=0x79fc02f000-0x79fc031000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"WifiP2pService" prio=5 tid=41 Native
  | group="main" sCount=1 dsCount=0 obj=0x1318bee0 self=0x7a002ecc00
  | sysTid=1192 nice=0 cgrp=default sched=0/0 handle=0x79fc02c450
  | state=S schedstat=( 109128150 34847230 88 ) utm=9 stm=1 core=0 HZ=100
  | stack=0x79fbf2a000-0x79fbf2c000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"WifiService" prio=5 tid=42 Native
  | group="main" sCount=1 dsCount=0 obj=0x132273a0 self=0x7a002ed600
  | sysTid=1195 nice=0 cgrp=default sched=0/0 handle=0x79fbeda450
  | state=S schedstat=( 241870696 45073848 252 ) utm=7 stm=17 core=0 HZ=100
  | stack=0x79fbdd8000-0x79fbdda000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"WifiStateMachine" prio=5 tid=43 Native
  | group="main" sCount=1 dsCount=0 obj=0x132270d0 self=0x7a00386000
  | sysTid=1197 nice=0 cgrp=default sched=0/0 handle=0x79fbdd5450
  | state=S schedstat=( 1641917248 1483616923 2002 ) utm=145 stm=19 core=0 HZ=100
  | stack=0x79fbcd3000-0x79fbcd5000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"IpManager.wlan0" prio=5 tid=44 Native
  | group="main" sCount=1 dsCount=0 obj=0x13281dc0 self=0x7a00386a00
  | sysTid=1199 nice=0 cgrp=default sched=0/0 handle=0x79fbc24450
  | state=S schedstat=( 46234393 74695456 59 ) utm=3 stm=1 core=1 HZ=100
  | stack=0x79fbb22000-0x79fbb24000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"WifiScanningService" prio=5 tid=45 Native
  | group="main" sCount=1 dsCount=0 obj=0x132e7af0 self=0x7a00387400
  | sysTid=1200 nice=0 cgrp=default sched=0/0 handle=0x79fbb1f450
  | state=S schedstat=( 414922694 342824307 461 ) utm=37 stm=4 core=2 HZ=100
  | stack=0x79fba1d000-0x79fba1f000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"WifiRttService" prio=5 tid=46 Native
  | group="main" sCount=1 dsCount=0 obj=0x132e7940 self=0x7a00387e00
  | sysTid=1201 nice=0 cgrp=default sched=0/0 handle=0x79fba1a450
  | state=S schedstat=( 1001001 2086154 4 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x79fb918000-0x79fb91a000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"ConnectivityServiceThread" prio=5 tid=47 Native
  | group="main" sCount=1 dsCount=0 obj=0x132d44c0 self=0x79fd10ea00
  | sysTid=1202 nice=0 cgrp=default sched=0/0 handle=0x79fb915450
  | state=S schedstat=( 77953385 57631002 141 ) utm=5 stm=2 core=0 HZ=100
  | stack=0x79fb813000-0x79fb815000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"android.pacmanager" prio=5 tid=48 Native
  | group="main" sCount=1 dsCount=0 obj=0x132d4af0 self=0x79fd10f400
  | sysTid=1203 nice=0 cgrp=default sched=0/0 handle=0x79fb810450
  | state=S schedstat=( 491000 0 1 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fb70e000-0x79fb710000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"NsdService" prio=5 tid=49 Native
  | group="main" sCount=1 dsCount=0 obj=0x132d4c10 self=0x79fd10fe00
  | sysTid=1204 nice=0 cgrp=default sched=0/0 handle=0x79fb70b450
  | state=S schedstat=( 1935307 1235538 4 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x79fb609000-0x79fb60b000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"mDnsConnector" prio=5 tid=50 Native
  | group="main" sCount=1 dsCount=0 obj=0x132d4ee0 self=0x79fd110800
  | sysTid=1205 nice=0 cgrp=default sched=0/0 handle=0x79fb606450
  | state=S schedstat=( 837077 0 1 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x79fb504000-0x79fb506000 stackSize=1037KB
  | held mutexes=
  at android.net.LocalSocketImpl.readba_native(Native method)
  at android.net.LocalSocketImpl.-wrap1(LocalSocketImpl.java:-1)
  at android.net.LocalSocketImpl$SocketInputStream.read(LocalSocketImpl.java:110)
  - locked <0x048c28c0> (a java.lang.Object)
  at com.android.server.NativeDaemonConnector.listenToSocket(NativeDaemonConnector.java:210)
  at com.android.server.NativeDaemonConnector.run(NativeDaemonConnector.java:143)
  at java.lang.Thread.run(Thread.java:761)

"ranker" prio=5 tid=51 Native
  | group="main" sCount=1 dsCount=0 obj=0x13386310 self=0x79fd111200
  | sysTid=1206 nice=10 cgrp=bg_non_interactive sched=0/0 handle=0x79fb4fc450
  | state=S schedstat=( 1242999 1479693 4 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fb3fa000-0x79fb3fc000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"notification-sqlite-log" prio=5 tid=52 Native
  | group="main" sCount=1 dsCount=0 obj=0x13386790 self=0x79fd111c00
  | sysTid=1207 nice=10 cgrp=bg_non_interactive sched=0/0 handle=0x79fb3f0450
  | state=S schedstat=( 15410304 261248773 128 ) utm=0 stm=1 core=0 HZ=100
  | stack=0x79fb2ee000-0x79fb2f0000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"ConditionProviders.ECP" prio=5 tid=53 Native
  | group="main" sCount=1 dsCount=0 obj=0x133a2f70 self=0x79fd112600
  | sysTid=1208 nice=0 cgrp=default sched=0/0 handle=0x79fb2c4450
  | state=S schedstat=( 23718306 35170545 66 ) utm=2 stm=0 core=2 HZ=100
  | stack=0x79fb1c2000-0x79fb1c4000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"AudioService" prio=5 tid=54 Native
  | group="main" sCount=1 dsCount=0 obj=0x1342b8b0 self=0x79fcb87000
  | sysTid=1209 nice=0 cgrp=default sched=0/0 handle=0x79fb1b8450
  | state=S schedstat=( 30109382 40893617 150 ) utm=2 stm=1 core=0 HZ=100
  | stack=0x79fb0b6000-0x79fb0b8000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at com.android.server.audio.AudioService$AudioSystemThread.run(AudioService.java:4322)

"UEventObserver" prio=5 tid=56 Native
  | group="main" sCount=1 dsCount=0 obj=0x1342bb80 self=0x79fcb88400
  | sysTid=1213 nice=0 cgrp=default sched=0/0 handle=0x79fafa0450
  | state=S schedstat=( 2992000 10010310 17 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fae9e000-0x79faea0000 stackSize=1037KB
  | held mutexes=
  at android.os.UEventObserver.nativeWaitForNextEvent(Native method)
  at android.os.UEventObserver.-wrap0(UEventObserver.java:-1)
  at android.os.UEventObserver$UEventThread.run(UEventObserver.java:182)

"PerfServiceManager" prio=5 tid=57 Native
  | group="main" sCount=1 dsCount=0 obj=0x13227160 self=0x79fcb89800
  | sysTid=1215 nice=-2 cgrp=default sched=0/0 handle=0x79fc9df450
  | state=S schedstat=( 71980640 59776536 279 ) utm=4 stm=3 core=0 HZ=100
  | stack=0x79fc8dd000-0x79fc8df000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"ConnectivityManager" prio=5 tid=58 Native
  | group="main" sCount=1 dsCount=0 obj=0x1323d8b0 self=0x79fcb8ac00
  | sysTid=1217 nice=0 cgrp=default sched=0/0 handle=0x79fc647450
  | state=S schedstat=( 2633384 7478155 10 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fc545000-0x79fc547000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"ConnectivityThread" prio=5 tid=59 Native
  | group="main" sCount=1 dsCount=0 obj=0x1323dca0 self=0x79fcb8b600
  | sysTid=1218 nice=0 cgrp=default sched=0/0 handle=0x79fc542450
  | state=S schedstat=( 406692 204616 2 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x79fc440000-0x79fc442000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"EthernetServiceThread" prio=5 tid=60 Native
  | group="main" sCount=1 dsCount=0 obj=0x1308ed30 self=0x79fcbbd000
  | sysTid=1219 nice=0 cgrp=default sched=0/0 handle=0x79fabff450
  | state=S schedstat=( 6906463 15343768 18 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79faafd000-0x79faaff000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"PhotonicModulator" prio=5 tid=61 Waiting
  | group="main" sCount=1 dsCount=0 obj=0x12fa5ce0 self=0x79fcb88e00
  | sysTid=1220 nice=0 cgrp=default sched=0/0 handle=0x79faafa450
  | state=S schedstat=( 61723532 62342697 360 ) utm=3 stm=3 core=0 HZ=100
  | stack=0x79fa9f8000-0x79fa9fa000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x089809f9> (a java.lang.Object)
  at com.android.server.display.DisplayPowerState$PhotonicModulator.run(DisplayPowerState.java:437)
  - locked <0x089809f9> (a java.lang.Object)

"AMPlus" prio=5 tid=62 Native
  | group="main" sCount=1 dsCount=0 obj=0x1316f9d0 self=0x79fcbbda00
  | sysTid=1221 nice=-2 cgrp=default sched=0/0 handle=0x79fa5ff450
  | state=S schedstat=( 457153 99231 6 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x79fa4fd000-0x79fa4ff000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"LazyTaskWriterThread" prio=5 tid=63 Waiting
  | group="main" sCount=1 dsCount=0 obj=0x12d7caf0 self=0x79fcbbe400
  | sysTid=1222 nice=0 cgrp=default sched=0/0 handle=0x79fa4fa450
  | state=S schedstat=( 113401461 35667153 89 ) utm=9 stm=2 core=0 HZ=100
  | stack=0x79fa3f8000-0x79fa3fa000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0b5c4d3e> (a com.android.server.am.TaskPersister)
  at com.android.server.am.TaskPersister$LazyTaskWriterThread.run(TaskPersister.java:664)
  - locked <0x0b5c4d3e> (a com.android.server.am.TaskPersister)

"SyncHandler-0" prio=5 tid=64 Native
  | group="main" sCount=1 dsCount=0 obj=0x1318bca0 self=0x79fde28800
  | sysTid=1223 nice=0 cgrp=default sched=0/0 handle=0x79fa3f5450
  | state=S schedstat=( 1842922 4688230 26 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x79fa2f3000-0x79fa2f5000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"UsbService host thread" prio=5 tid=65 Native
  | group="main" sCount=1 dsCount=0 obj=0x12cc9310 self=0x79fcbbee00
  | sysTid=1249 nice=0 cgrp=default sched=0/0 handle=0x79f9cd8450
  | state=S schedstat=( 1101076 7055924 10 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x79f9bd6000-0x79f9bd8000 stackSize=1037KB
  | held mutexes=
  at com.android.server.usb.UsbHostManager.monitorUsbHostBus(Native method)
  at com.android.server.usb.UsbHostManager.-wrap0(UsbHostManager.java:-1)
  at com.android.server.usb.UsbHostManager$1.run(UsbHostManager.java:256)
  at java.lang.Thread.run(Thread.java:761)

"Thread-4" prio=5 tid=66 Native
  | group="main" sCount=1 dsCount=0 obj=0x12cc9280 self=0x79fcbbf800
  | sysTid=1250 nice=0 cgrp=default sched=0/0 handle=0x79f9bd3450
  | state=S schedstat=( 3179385 4207384 4 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x79f9ad1000-0x79f9ad3000 stackSize=1037KB
  | held mutexes=
  at libcore.io.Posix.accept(Native method)
  at libcore.io.BlockGuardOs.accept(BlockGuardOs.java:64)
  at android.system.Os.accept(Os.java:43)
  at com.android.server.am.NativeCrashListener.run(NativeCrashListener.java:129)

"Binder:1122_3" prio=5 tid=67 Native
  | group="main" sCount=1 dsCount=0 obj=0x12cc90d0 self=0x79faddf400
  | sysTid=1253 nice=0 cgrp=default sched=0/0 handle=0x79f9ace450
  | state=S schedstat=( 452554910 347088108 1413 ) utm=32 stm=13 core=0 HZ=100
  | stack=0x79f99d4000-0x79f99d6000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1253/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Binder:1122_4" prio=5 tid=68 Native
  | group="main" sCount=1 dsCount=0 obj=0x12d55c10 self=0x79fdee1000
  | sysTid=1254 nice=0 cgrp=default sched=0/0 handle=0x79f99d1450
  | state=S schedstat=( 396775636 340264616 1475 ) utm=31 stm=9 core=0 HZ=100
  | stack=0x79f98d7000-0x79f98d9000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1254/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"SoundPool" prio=5 tid=69 Native
  | group="main" sCount=1 dsCount=0 obj=0x130d9ee0 self=0x7a003c9c00
  | sysTid=1255 nice=0 cgrp=default sched=0/0 handle=0x79f98d4450
  | state=S schedstat=( 2891235 458308 19 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x79f97da000-0x79f97dc000 stackSize=1005KB
  | held mutexes=
  (no managed stack frames)

"SoundPoolThread" prio=5 tid=70 Native
  | group="main" sCount=1 dsCount=0 obj=0x130d9e50 self=0x79fdee7400
  | sysTid=1256 nice=0 cgrp=default sched=0/0 handle=0x79f97d7450
  | state=S schedstat=( 78701452 101403773 1106 ) utm=4 stm=3 core=1 HZ=100
  | stack=0x79f96dd000-0x79f96df000 stackSize=1005KB
  | held mutexes=
  (no managed stack frames)

"Binder:1122_5" prio=5 tid=73 Native
  | group="main" sCount=1 dsCount=0 obj=0x1318be50 self=0x79fdf1ac00
  | sysTid=1309 nice=0 cgrp=default sched=0/0 handle=0x79f92db450
  | state=S schedstat=( 351869838 292332467 1394 ) utm=26 stm=9 core=0 HZ=100
  | stack=0x79f91e1000-0x79f91e3000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1309/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"NetworkStatsObservers" prio=5 tid=74 Native
  | group="main" sCount=1 dsCount=0 obj=0x13227310 self=0x79fcb14800
  | sysTid=1329 nice=0 cgrp=default sched=0/0 handle=0x79f91de450
  | state=S schedstat=( 760232 8313769 9 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x79f90dc000-0x79f90de000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"watchdog" prio=5 tid=75 TimedWaiting
  | group="main" sCount=1 dsCount=0 obj=0x12cd45b0 self=0x79fcb15200
  | sysTid=1454 nice=0 cgrp=default sched=0/0 handle=0x79f90d9450
  | state=S schedstat=( 9411463 243461 9 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79f8fd7000-0x79f8fd9000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x01c9f79f> (a com.android.server.Watchdog)
  at java.lang.Object.wait(Object.java:407)
  at com.android.server.Watchdog.run(Watchdog.java:426)
  - locked <0x01c9f79f> (a com.android.server.Watchdog)

"[NlpUtils]" prio=5 tid=76 Native
  | group="main" sCount=1 dsCount=0 obj=0x13698c10 self=0x79fcb15c00
  | sysTid=1462 nice=0 cgrp=default sched=0/0 handle=0x79f8fd4450
  | state=S schedstat=( 528385 210307 1 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79f8ed2000-0x79f8ed4000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"Thread-5" prio=5 tid=77 Native
  | group="main" sCount=1 dsCount=0 obj=0x13698ca0 self=0x79fcb16600
  | sysTid=1463 nice=0 cgrp=default sched=0/0 handle=0x79f8bff450
  | state=S schedstat=( 821154 124769 1 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x79f8afd000-0x79f8aff000 stackSize=1037KB
  | held mutexes=
  at libcore.io.Posix.accept(Native method)
  at libcore.io.BlockGuardOs.accept(BlockGuardOs.java:64)
  at android.system.Os.accept(Os.java:43)
  at android.net.LocalSocketImpl.accept(LocalSocketImpl.java:336)
  at android.net.LocalServerSocket.accept(LocalServerSocket.java:90)
  at com.mediatek.location.NlpUtils.doServerTask(NlpUtils.java:258)
  at com.mediatek.location.NlpUtils.-wrap3(NlpUtils.java:-1)
  at com.mediatek.location.NlpUtils$3.run(NlpUtils.java:74)

"MtkAgpsSocket" prio=5 tid=78 Native
  | group="main" sCount=1 dsCount=0 obj=0x13698f70 self=0x7a003c6000
  | sysTid=1465 nice=0 cgrp=default sched=0/0 handle=0x79f8afa450
  | state=S schedstat=( 2201768 556231 6 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x79f89f8000-0x79f89fa000 stackSize=1037KB
  | held mutexes=
  at android.net.LocalSocketImpl.readba_native(Native method)
  at android.net.LocalSocketImpl.-wrap1(LocalSocketImpl.java:-1)
  at android.net.LocalSocketImpl$SocketInputStream.read(LocalSocketImpl.java:110)
  - locked <0x006b10ec> (a java.lang.Object)
  at java.io.DataInputStream.read(DataInputStream.java:100)
  at com.mediatek.socket.base.UdpServer.read(UdpServer.java:88)
  at com.mediatek.location.Agps2FrameworkInterface$Agps2FrameworkInterfaceReceiver.readAndDecode(Agps2FrameworkInterface.java:164)
  at com.mediatek.location.AgpsHelper.waitForAgpsCommands(AgpsHelper.java:231)
  at com.mediatek.location.AgpsHelper$2.run(AgpsHelper.java:120)

"MtkAgpsHandler" prio=5 tid=79 Native
  | group="main" sCount=1 dsCount=0 obj=0x136ed160 self=0x79fa64b400
  | sysTid=1467 nice=0 cgrp=default sched=0/0 handle=0x79f89f5450
  | state=S schedstat=( 480847 584231 2 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x79f88f3000-0x79f88f5000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"gpshal_worker_thread" prio=5 tid=80 Native
  | group="main" sCount=1 dsCount=0 obj=0x136ed670 self=0x79fa6f9e00
  | sysTid=1472 nice=0 cgrp=default sched=0/0 handle=0x79f88f0450
  | state=S schedstat=( 872386 1764769 6 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x79f87f6000-0x79f87f8000 stackSize=1005KB
  | held mutexes=
  (no managed stack frames)

"NetworkTimeUpdateService" prio=5 tid=81 Native
  | group="main" sCount=1 dsCount=0 obj=0x136ed310 self=0x7a003c6a00
  | sysTid=1473 nice=0 cgrp=default sched=0/0 handle=0x79f87f3450
  | state=S schedstat=( 18993927 5586229 38 ) utm=1 stm=0 core=2 HZ=100
  | stack=0x79f86f1000-0x79f86f3000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"NetworkTimeUpdateService" prio=5 tid=82 Native
  | group="main" sCount=1 dsCount=0 obj=0x136ed430 self=0x7a003c7400
  | sysTid=1474 nice=0 cgrp=default sched=0/0 handle=0x79f86ee450
  | state=S schedstat=( 446614 226539 4 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x79f85ec000-0x79f85ee000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"Binder:1122_6" prio=5 tid=72 Native
  | group="main" sCount=1 dsCount=0 obj=0x12ca8d30 self=0x79fa64a000
  | sysTid=1520 nice=0 cgrp=default sched=0/0 handle=0x79f93d8450
  | state=S schedstat=( 375727845 299611169 1261 ) utm=19 stm=18 core=0 HZ=100
  | stack=0x79f92de000-0x79f92e0000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1520/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Thread-7" prio=5 tid=84 Native
  | group="main" sCount=1 dsCount=0 obj=0x1323d5e0 self=0x79fa72d200
  | sysTid=1596 nice=0 cgrp=default sched=0/0 handle=0x79f6a3d450
  | state=S schedstat=( 6291081 28933998 56 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x79f693b000-0x79f693d000 stackSize=1037KB
  | held mutexes=
  at com.android.server.wifi.WifiNative.waitForHalEventNative(Native method)
  at com.android.server.wifi.WifiNative.-wrap0(WifiNative.java:-1)
  at com.android.server.wifi.WifiNative$MonitorThread.run(WifiNative.java:1604)

"com.android.server.telecom.CallAudioRouteStateMachine" prio=5 tid=83 Native
  | group="main" sCount=1 dsCount=0 obj=0x13588550 self=0x7a003c9200
  | sysTid=1661 nice=0 cgrp=default sched=0/0 handle=0x79f6938450
  | state=S schedstat=( 615384 1196154 2 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79f6836000-0x79f6838000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"CallAudioModeStateMachine" prio=5 tid=85 Native
  | group="main" sCount=1 dsCount=0 obj=0x135b03a0 self=0x79f6c1a600
  | sysTid=1662 nice=0 cgrp=default sched=0/0 handle=0x79f6833450
  | state=S schedstat=( 782154 1733308 2 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79f6731000-0x79f6733000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"AsyncQueryWorker" prio=5 tid=86 Native
  | group="main" sCount=1 dsCount=0 obj=0x135b0ca0 self=0x79f6c31000
  | sysTid=1664 nice=0 cgrp=default sched=0/0 handle=0x79f672e450
  | state=S schedstat=( 8312078 6843771 33 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79f662c000-0x79f662e000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"WifiMonitor" prio=5 tid=87 Native
  | group="main" sCount=1 dsCount=0 obj=0x12ca8ca0 self=0x79fa72dc00
  | sysTid=1680 nice=0 cgrp=default sched=0/0 handle=0x79f7e19450
  | state=S schedstat=( 113486226 91853079 114 ) utm=9 stm=2 core=0 HZ=100
  | stack=0x79f7d17000-0x79f7d19000 stackSize=1037KB
  | held mutexes=
  at com.android.server.wifi.WifiNative.waitForEventNative(Native method)
  at com.android.server.wifi.WifiNative.waitForEvent(WifiNative.java:253)
  at com.android.server.wifi.WifiMonitor$MonitorThread.run(WifiMonitor.java:830)

"backup" prio=5 tid=89 Native
  | group="main" sCount=1 dsCount=0 obj=0x1327a1f0 self=0x79f6c40800
  | sysTid=1692 nice=0 cgrp=default sched=0/0 handle=0x79f5f8a450
  | state=S schedstat=( 2015464 12668076 15 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x79f5e88000-0x79f5e8a000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"Binder:1122_7" prio=5 tid=90 Waiting
  | group="main" sCount=1 dsCount=0 obj=0x1329e280 self=0x79f5c51200
  | sysTid=1795 nice=0 cgrp=default sched=0/0 handle=0x79f7b1c450
  | state=S schedstat=( 299758300 223007937 920 ) utm=18 stm=11 core=2 HZ=100
  | stack=0x79f7a22000-0x79f7a24000 stackSize=1005KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0f464cb5> (a java.lang.Object)
  at com.android.internal.util.AsyncChannel$SyncMessenger.sendMessageSynchronously(AsyncChannel.java:820)
  - locked <0x0f464cb5> (a java.lang.Object)
  at com.android.internal.util.AsyncChannel$SyncMessenger.-wrap0(AsyncChannel.java:-1)
  at com.android.internal.util.AsyncChannel.sendMessageSynchronously(AsyncChannel.java:654)
  at com.android.internal.util.AsyncChannel.sendMessageSynchronously(AsyncChannel.java:682)
  at com.android.server.wifi.WifiStateMachine.syncDisableNetwork(WifiStateMachine.java:2273)
  at com.android.server.wifi.WifiServiceImpl.disableNetwork(WifiServiceImpl.java:1147)
  at android.net.wifi.IWifiManager$Stub.onTransact(IWifiManager.java:219)
  at android.os.Binder.execTransact(Binder.java:570)

"Binder:1122_8" prio=5 tid=91 Native
  | group="main" sCount=1 dsCount=0 obj=0x1329e1f0 self=0x79f8cfba00
  | sysTid=1797 nice=0 cgrp=default sched=0/0 handle=0x79f78ec450
  | state=S schedstat=( 292570933 186919005 940 ) utm=21 stm=8 core=0 HZ=100
  | stack=0x79f77f2000-0x79f77f4000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1797/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Binder:1122_9" prio=5 tid=92 Native
  | group="main" sCount=1 dsCount=0 obj=0x132d40d0 self=0x79f5cbfc00
  | sysTid=1798 nice=0 cgrp=default sched=0/0 handle=0x79f751a450
  | state=S schedstat=( 320543456 218623446 976 ) utm=26 stm=6 core=0 HZ=100
  | stack=0x79f7420000-0x79f7422000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1798/stack)
  native: #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  native: #01 pc 00000000000e77c8  /system/lib64/libart.so (_ZN3art17ConditionVariable16WaitHoldingLocksEPNS_6ThreadE+156)
  native: #02 pc 000000000032ec44  /system/lib64/libart.so (_ZN3art3JNI18CallBooleanMethodVEP7_JNIEnvP8_jobjectP10_jmethodIDSt9__va_list+300)
  native: #03 pc 00000000000a8890  /system/lib64/libandroid_runtime.so (???)
  native: #04 pc 00000000000fefb8  /system/lib64/libandroid_runtime.so (???)
  native: #05 pc 0000000000049ee4  /system/lib64/libbinder.so (_ZN7android7BBinder8transactEjRKNS_6ParcelEPS1_j+132)
  native: #06 pc 0000000000055cac  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14executeCommandEi+992)
  native: #07 pc 0000000000055810  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+156)
  native: #08 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #09 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #10 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #11 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #12 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #13 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Binder:1122_A" prio=5 tid=93 Native
  | group="main" sCount=1 dsCount=0 obj=0x132e74c0 self=0x79f8d51e00
  | sysTid=1849 nice=0 cgrp=default sched=0/0 handle=0x79f60ee450
  | state=S schedstat=( 271905398 192808013 925 ) utm=19 stm=8 core=0 HZ=100
  | stack=0x79f5ff4000-0x79f5ff6000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1849/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Binder:1122_B" prio=5 tid=94 Native
  | group="main" sCount=1 dsCount=0 obj=0x13329790 self=0x79fad5f000
  | sysTid=1851 nice=0 cgrp=default sched=0/0 handle=0x79f5bff450
  | state=S schedstat=( 293948317 174746452 908 ) utm=20 stm=9 core=0 HZ=100
  | stack=0x79f5b05000-0x79f5b07000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1851/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Binder:1122_C" prio=5 tid=95 Native
  | group="main" sCount=1 dsCount=0 obj=0x133295e0 self=0x79f8d9e200
  | sysTid=1853 nice=0 cgrp=default sched=0/0 handle=0x79f5b02450
  | state=S schedstat=( 309815704 206270373 881 ) utm=22 stm=8 core=0 HZ=100
  | stack=0x79f5a08000-0x79f5a0a000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1853/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Timer-0" prio=5 tid=96 TimedWaiting
  | group="main" sCount=1 dsCount=0 obj=0x1329e9d0 self=0x79fde29200
  | sysTid=2086 nice=0 cgrp=default sched=0/0 handle=0x79fa9f5450
  | state=S schedstat=( 292538 1630462 1 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fa8f3000-0x79fa8f5000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x054bff4a> (a java.util.TaskQueue)
  at java.lang.Object.wait(Object.java:407)
  at java.util.TimerThread.mainLoop(Timer.java:552)
  - locked <0x054bff4a> (a java.util.TaskQueue)
  at java.util.TimerThread.run(Timer.java:505)

"Timer-1" prio=5 tid=97 TimedWaiting
  | group="main" sCount=1 dsCount=0 obj=0x1329e940 self=0x79fdeb0c00
  | sysTid=2087 nice=0 cgrp=default sched=0/0 handle=0x79f9fff450
  | state=S schedstat=( 204384 2317769 1 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79f9efd000-0x79f9eff000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x06606ebb> (a java.util.TaskQueue)
  at java.lang.Object.wait(Object.java:407)
  at java.util.TimerThread.mainLoop(Timer.java:552)
  - locked <0x06606ebb> (a java.util.TaskQueue)
  at java.util.TimerThread.run(Timer.java:505)

"SyncHandler-1" prio=5 tid=98 Native
  | group="main" sCount=1 dsCount=0 obj=0x1372f310 self=0x79f6c34200
  | sysTid=2613 nice=0 cgrp=default sched=0/0 handle=0x79f9dfd450
  | state=S schedstat=( 10837303 10064769 176 ) utm=1 stm=0 core=0 HZ=100
  | stack=0x79f9cfb000-0x79f9cfd000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"NetworkMonitorNetworkAgentInfo [WIFI () - 100]" prio=5 tid=9 Native
  | group="main" sCount=1 dsCount=0 obj=0x135b0670 self=0x79f6c19200
  | sysTid=2634 nice=0 cgrp=default sched=0/0 handle=0x7a005a5450
  | state=S schedstat=( 1455384 637231 3 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x7a004a3000-0x7a004a5000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/2634/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"Thread-14" prio=5 tid=55 Native
  | group="main" sCount=1 dsCount=0 obj=0x136265e0 self=0x79f8cf8800
  | sysTid=2635 nice=0 cgrp=default sched=0/0 handle=0x79fb0ae450
  | state=S schedstat=( 17933612 4019231 22 ) utm=1 stm=0 core=0 HZ=100
  | stack=0x79fafac000-0x79fafae000 stackSize=1037KB
  | held mutexes=
  at libcore.io.Posix.readBytes(Native method)
  at libcore.io.Posix.read(Posix.java:161)
  at libcore.io.BlockGuardOs.read(BlockGuardOs.java:226)
  at android.system.Os.read(Os.java:368)
  at android.net.netlink.NetlinkSocket.recvMessage(NetlinkSocket.java:123)
  at android.net.netlink.NetlinkSocket.recvMessage(NetlinkSocket.java:94)
  at android.net.ip.IpReachabilityMonitor$NetlinkSocketObserver.recvKernelReply(IpReachabilityMonitor.java:502)
  at android.net.ip.IpReachabilityMonitor$NetlinkSocketObserver.run(IpReachabilityMonitor.java:458)
  at java.lang.Thread.run(Thread.java:761)

"DhcpClient" prio=5 tid=71 Native
  | group="main" sCount=1 dsCount=0 obj=0x13626700 self=0x79f8cf9200
  | sysTid=2636 nice=0 cgrp=default sched=0/0 handle=0x79f96da450
  | state=S schedstat=( 26227614 24274462 36 ) utm=1 stm=1 core=0 HZ=100
  | stack=0x79f95d8000-0x79f95da000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"Dhcp6Client" prio=5 tid=88 Native
  | group="main" sCount=1 dsCount=0 obj=0x13698af0 self=0x79f8cf9c00
  | sysTid=2637 nice=0 cgrp=default sched=0/0 handle=0x79f80e9450
  | state=S schedstat=( 6592385 401309 10 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79f7fe7000-0x79f7fe9000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"Thread-15" prio=5 tid=99 Native
  | group="main" sCount=1 dsCount=0 obj=0x137d1430 self=0x79f6c16000
  | sysTid=2638 nice=0 cgrp=default sched=0/0 handle=0x79f7fe4450
  | state=S schedstat=( 7943848 1096846 10 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79f7ee2000-0x79f7ee4000 stackSize=1037KB
  | held mutexes=
  at libcore.io.Posix.readBytes(Native method)
  at libcore.io.Posix.read(Posix.java:169)
  at libcore.io.BlockGuardOs.read(BlockGuardOs.java:231)
  at android.system.Os.read(Os.java:373)
  at android.net.dhcp.DhcpClient$ReceiveThread.run(DhcpClient.java:373)

"Binder:1122_D" prio=5 tid=100 Native
  | group="main" sCount=1 dsCount=0 obj=0x137fb4c0 self=0x79f6536e00
  | sysTid=2640 nice=0 cgrp=default sched=0/0 handle=0x79f7d14450
  | state=S schedstat=( 59301304 24959382 188 ) utm=4 stm=1 core=0 HZ=100
  | stack=0x79f7c1a000-0x79f7c1c000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/2640/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"system_server" prio=5 (not attached)
  | sysTid=1150 nice=-2 cgrp=default
  | state=S schedstat=( 106769 0 2 ) utm=0 stm=0 core=0 HZ=100
  kernel: (couldn't read /proc/self/task/1150/stack)

"system_server" prio=5 (not attached)
  | sysTid=1151 nice=-2 cgrp=default
  | state=S schedstat=( 28923 0 1 ) utm=0 stm=0 core=0 HZ=100
  kernel: (couldn't read /proc/self/task/1151/stack)

"system_server" prio=5 (not attached)
  | sysTid=1155 nice=0 cgrp=default
  | state=S schedstat=( 717999 28308 5 ) utm=0 stm=0 core=1 HZ=100
  kernel: (couldn't read /proc/self/task/1155/stack)

"mali-mem-purge" prio=5 (not attached)
  | sysTid=1177 nice=-4 cgrp=default
  | state=S schedstat=( 7070230 6500077 75 ) utm=0 stm=0 core=2 HZ=100
  kernel: (couldn't read /proc/self/task/1177/stack)

"mali-event-hnd" prio=5 (not attached)
  | sysTid=1178 nice=-4 cgrp=default
  | state=S schedstat=( 81244221 84890695 972 ) utm=2 stm=6 core=1 HZ=100
  kernel: (couldn't read /proc/self/task/1178/stack)

"mali-utility-wo" prio=5 (not attached)
  | sysTid=1179 nice=-4 cgrp=default
  | state=S schedstat=( 36154 16538 1 ) utm=0 stm=0 core=0 HZ=100
  kernel: (couldn't read /proc/self/task/1179/stack)

"mali-utility-wo" prio=5 (not attached)
  | sysTid=1180 nice=-4 cgrp=default
  | state=S schedstat=( 24461 14923 1 ) utm=0 stm=0 core=0 HZ=100
  kernel: (couldn't read /proc/self/task/1180/stack)

"mali-utility-wo" prio=5 (not attached)
  | sysTid=1181 nice=-4 cgrp=default
  | state=S schedstat=( 47615 0 2 ) utm=0 stm=0 core=0 HZ=100
  kernel: (couldn't read /proc/self/task/1181/stack)

"mali-utility-wo" prio=5 (not attached)
  | sysTid=1182 nice=-4 cgrp=default
  | state=S schedstat=( 356385 11308 2 ) utm=0 stm=0 core=1 HZ=100
  kernel: (couldn't read /proc/self/task/1182/stack)

"mali-renderer" prio=5 (not attached)
  | sysTid=1183 nice=-4 cgrp=default
  | state=S schedstat=( 259368840 145877936 1673 ) utm=6 stm=19 core=0 HZ=100
  kernel: (couldn't read /proc/self/task/1183/stack)

"mali-hist-dump" prio=5 (not attached)
  | sysTid=1184 nice=-4 cgrp=default
  | state=S schedstat=( 8572077 2430305 73 ) utm=0 stm=0 core=3 HZ=100
  kernel: (couldn't read /proc/self/task/1184/stack)

"FileSourceProxy" prio=5 (not attached)
  | sysTid=1258 nice=0 cgrp=default
  | state=S schedstat=( 662692 173999 7 ) utm=0 stm=0 core=2 HZ=100
  kernel: (couldn't read /proc/self/task/1258/stack)

"AudioTrack" prio=5 (not attached)
  | sysTid=2331 nice=-16 cgrp=default
  | state=S schedstat=( 5983230 3079691 79 ) utm=0 stm=0 core=0 HZ=100
  kernel: (couldn't read /proc/self/task/2331/stack)

----- end 1122 -----

----- pid 1813 at 2018-09-14 13:46:57 -----
Cmd line: com.mobilead.apps.mwedge
Build fingerprint: 'Mobilead/m80/m80:7.0/04.04.20180125/1516857511:user/dev-keys'
ABI: 'arm64'
Build type: optimized
Zygote loaded classes=4375 post zygote classes=8
Intern table: 48042 strong; 175 weak
JNI: CheckJNI is on; globals=457 (plus 118 weak)
Libraries: /system/lib64/libandroid.so /system/lib64/libcompiler_rt.so /system/lib64/libjavacrypto.so /system/lib64/libjnigraphics.so /system/lib64/libmedia_jni.so /system/lib64/libwebviewchromium_loader.so libjavacore.so libopenjdk.so (8)
Heap: 64% free, 2MB/7MB; 26136 objects
Dumping cumulative Gc timings
Total number of allocations 26136
Total bytes allocated 2MB
Total bytes freed 0B
Free memory 4MB
Free memory until GC 4MB
Free memory until OOME 125MB
Total memory 7MB
Max memory 128MB
Zygote space size 1648KB
Total mutator paused time: 0
Total time waiting for GC to complete: 1.231us
Total GC count: 0
Total GC time: 0
Total blocking GC count: 0
Total blocking GC time: 0
Histogram of native allocation 0:560,128:232,256:102,384:60,640:1,896:16,1152:2 bucket size 128
Histogram of native free 0:27,96:28,192:214,288:37,480:9 bucket size 32
/system/app/MWedge/oat/arm64/MWedge.odex: speed
Current JIT code cache size: 0B
Current JIT data cache size: 0B
Current JIT capacity: 64KB
Current number of JIT code cache entries: 0
Total number of JIT compilations: 0
Total number of JIT compilations for on stack replacement: 0
Total number of deoptimizations: 0
Total number of JIT code cache collections: 0
Memory used for stack maps: Avg: 0B Max: 0B Min: -1B
Memory used for compiled code: Avg: 0B Max: 0B Min: -1B
Memory used for profiling info: Avg: 0B Max: 0B Min: -1B
Start Dumping histograms for 0 iterations for JIT timings
Done Dumping histograms
Memory used for compilation: Avg: 0B Max: 0B Min: -1B

suspend all histogram:	Sum: 742us 99% C.I. 15us-130us Avg: 67.454us Max: 130us
DALVIK THREADS (10):
"Signal Catcher" daemon prio=5 tid=3 Runnable
  | group="system" sCount=0 dsCount=0 obj=0x12c214c0 self=0x7a0d907000
  | sysTid=1820 nice=0 cgrp=default sched=0/0 handle=0x7a160fe450
  | state=R schedstat=( 5946152 15613232 5 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x7a16004000-0x7a16006000 stackSize=1005KB
  | held mutexes= "mutator lock"(shared held)
  (no managed stack frames)

"main" prio=5 tid=1 Native
  | group="main" sCount=1 dsCount=0 obj=0x762d4998 self=0x7a16c96a00
  | sysTid=1813 nice=0 cgrp=default sched=0/0 handle=0x7a1b22ca98
  | state=S schedstat=( 43998308 16698539 60 ) utm=0 stm=4 core=1 HZ=100
  | stack=0x7fd06c6000-0x7fd06c8000 stackSize=8MB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.app.ActivityThread.main(ActivityThread.java:6251)
  at java.lang.reflect.Method.invoke!(Native method)
  at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1063)
  at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:924)

"Jit thread pool worker thread 0" prio=5 tid=2 Native (still starting up)
  | group="" sCount=1 dsCount=0 obj=0x0 self=0x7a1040e000
  | sysTid=1818 nice=9 cgrp=default sched=0/0 handle=0x7a161ff450
  | state=S schedstat=( 418846 6231307 2 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x7a16101000-0x7a16103000 stackSize=1021KB
  | held mutexes=
  (no managed stack frames)

"JDWP" daemon prio=5 tid=4 WaitingInMainDebuggerLoop
  | group="system" sCount=1 dsCount=0 obj=0x12c21550 self=0x7a10417400
  | sysTid=1826 nice=0 cgrp=default sched=0/0 handle=0x7a16001450
  | state=S schedstat=( 16809533 6844924 125 ) utm=0 stm=1 core=0 HZ=100
  | stack=0x7a15f07000-0x7a15f09000 stackSize=1005KB
  | held mutexes=
  (no managed stack frames)

"ReferenceQueueDaemon" daemon prio=5 tid=5 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c215e0 self=0x7a16cc3e00
  | sysTid=1828 nice=0 cgrp=default sched=0/0 handle=0x7a15f04450
  | state=S schedstat=( 389768 3380231 10 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x7a15e02000-0x7a15e04000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x035fca32> (a java.lang.Class<java.lang.ref.ReferenceQueue>)
  at java.lang.Daemons$ReferenceQueueDaemon.run(Daemons.java:150)
  - locked <0x035fca32> (a java.lang.Class<java.lang.ref.ReferenceQueue>)
  at java.lang.Thread.run(Thread.java:761)

"FinalizerWatchdogDaemon" daemon prio=5 tid=6 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c21700 self=0x7a16cc5200
  | sysTid=1830 nice=0 cgrp=default sched=0/0 handle=0x7a103ff450
  | state=S schedstat=( 367692 5129770 7 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x7a102fd000-0x7a102ff000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x04108383> (a java.lang.Daemons$FinalizerWatchdogDaemon)
  at java.lang.Daemons$FinalizerWatchdogDaemon.sleepUntilNeeded(Daemons.java:270)
  - locked <0x04108383> (a java.lang.Daemons$FinalizerWatchdogDaemon)
  at java.lang.Daemons$FinalizerWatchdogDaemon.run(Daemons.java:250)
  at java.lang.Thread.run(Thread.java:761)

"FinalizerDaemon" daemon prio=5 tid=7 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c21670 self=0x7a16cc4800
  | sysTid=1829 nice=0 cgrp=default sched=0/0 handle=0x7a10845450
  | state=S schedstat=( 280153 2144616 3 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x7a10743000-0x7a10745000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0d6c1900> (a java.lang.Object)
  at java.lang.Object.wait(Object.java:407)
  at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:188)
  - locked <0x0d6c1900> (a java.lang.Object)
  at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:209)
  at java.lang.Daemons$FinalizerDaemon.run(Daemons.java:205)
  at java.lang.Thread.run(Thread.java:761)

"Binder:1813_1" prio=5 tid=8 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c21940 self=0x7a10427800
  | sysTid=1837 nice=0 cgrp=default sched=0/0 handle=0x79fe64e450
  | state=S schedstat=( 2597538 24169692 9 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x79fe554000-0x79fe556000 stackSize=1005KB
  | held mutexes=
  kernel: __switch_to+0x80/0x8c
  kernel: binder_thread_read+0xf1c/0x108c
  kernel: binder_ioctl_write_read+0x190/0x498
  kernel: binder_ioctl+0x294/0x60c
  kernel: do_vfs_ioctl+0x488/0x568
  kernel: SyS_ioctl+0x60/0x88
  kernel: el0_svc_naked+0x24/0x28
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"HeapTaskDaemon" daemon prio=5 tid=9 Blocked
  | group="system" sCount=1 dsCount=0 obj=0x12c21790 self=0x7a16cc5c00
  | sysTid=1831 nice=0 cgrp=default sched=0/0 handle=0x7a102fa450
  | state=S schedstat=( 353921 21922696 6 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x7a101f8000-0x7a101fa000 stackSize=1037KB
  | held mutexes=
  kernel: __switch_to+0x80/0x8c
  kernel: futex_wait_queue_me+0xe4/0x144
  kernel: futex_wait+0xec/0x204
  kernel: do_futex+0xcc/0x87c
  kernel: SyS_futex+0xf4/0x16c
  kernel: el0_svc_naked+0x24/0x28
  native: #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  native: #01 pc 00000000000e77c8  /system/lib64/libart.so (_ZN3art17ConditionVariable16WaitHoldingLocksEPNS_6ThreadE+156)
  native: #02 pc 00000000002410dc  /system/lib64/libart.so (_ZN3art2gc13TaskProcessor7GetTaskEPNS_6ThreadE+276)
  native: #03 pc 0000000000241a14  /system/lib64/libart.so (_ZN3art2gc13TaskProcessor11RunAllTasksEPNS_6ThreadE+92)
  native: #04 pc 00000000001e2eb0  /system/framework/arm64/boot-core-libart.oat (Java_dalvik_system_VMRuntime_runHeapTasks__+124)
  at dalvik.system.VMRuntime.runHeapTasks(Native method)
  - waiting to lock an unknown object
  at java.lang.Daemons$HeapTaskDaemon.run(Daemons.java:438)
  at java.lang.Thread.run(Thread.java:761)

"Binder:1813_2" prio=5 tid=10 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c219d0 self=0x7a1042dc00
  | sysTid=1840 nice=0 cgrp=default sched=0/0 handle=0x79fe551450
  | state=S schedstat=( 1756384 1921923 3 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x79fe457000-0x79fe459000 stackSize=1005KB
  | held mutexes=
  kernel: __switch_to+0x80/0x8c
  kernel: binder_thread_read+0xf1c/0x108c
  kernel: binder_ioctl_write_read+0x190/0x498
  kernel: binder_ioctl+0x294/0x60c
  kernel: do_vfs_ioctl+0x488/0x568
  kernel: SyS_ioctl+0x60/0x88
  kernel: el0_svc_naked+0x24/0x28
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

----- end 1813 -----

----- pid 1801 at 2018-09-14 13:46:57 -----
Cmd line: com.mobilead.decoderservice
Build fingerprint: 'Mobilead/m80/m80:7.0/04.04.20180125/1516857511:user/dev-keys'
ABI: 'arm'
Build type: optimized
Zygote loaded classes=4375 post zygote classes=50
Intern table: 48071 strong; 308 weak
JNI: CheckJNI is off; globals=458 (plus 120 weak)
Libraries: /data/user/0/com.mobilead.decoderservice/files/tmpFile /system/app/DecoderService/lib/arm/libkey.so /system/lib/libandroid.so /system/lib/libcompiler_rt.so /system/lib/libjavacrypto.so /system/lib/libjnigraphics.so /system/lib/libmedia_jni.so /system/lib/libwebviewchromium_loader.so libjavacore.so libopenjdk.so (10)
Heap: 21% free, 5MB/7MB; 32238 objects
Dumping cumulative Gc timings
Total number of allocations 32238
Total bytes allocated 5MB
Total bytes freed 0B
Free memory 1582KB
Free memory until GC 1582KB
Free memory until OOME 122MB
Total memory 7MB
Max memory 128MB
Zygote space size 1616KB
Total mutator paused time: 0
Total time waiting for GC to complete: 2.769us
Total GC count: 0
Total GC time: 0
Total blocking GC count: 0
Total blocking GC time: 0
Histogram of native allocation 0:560,128:234,256:102,384:61,640:1,896:16,1152:2 bucket size 128
Histogram of native free 0:27,96:28,192:215,288:37,480:9 bucket size 32
/data/dalvik-cache/arm/system@app@DecoderService@DecoderService.apk@classes.dex: speed-profile
Current JIT code cache size: 11KB
Current JIT data cache size: 7KB
Current JIT capacity: 64KB
Current number of JIT code cache entries: 14
Total number of JIT compilations: 14
Total number of JIT compilations for on stack replacement: 2
Total number of deoptimizations: 0
Total number of JIT code cache collections: 0
Memory used for stack maps: Avg: 462B Max: 2036B Min: 20B
Memory used for compiled code: Avg: 785B Max: 3KB Min: 56B
Memory used for profiling info: Avg: 64B Max: 376B Min: 16B
Start Dumping histograms for 14 iterations for JIT timings
Compiling:	Sum: 107.207ms 99% C.I. 0.429ms-34.444ms Avg: 7.657ms Max: 35.190ms
TrimMaps:	Sum: 688us 99% C.I. 23us-128us Avg: 49.142us Max: 128us
Done Dumping histograms
Memory used for compilation: Avg: 154KB Max: 503KB Min: 28KB
ProfileSaver total_bytes_written=0
ProfileSaver total_number_of_writes=0
ProfileSaver total_number_of_code_cache_queries=1
ProfileSaver total_number_of_skipped_writes=1
ProfileSaver total_number_of_failed_writes=0
ProfileSaver total_ms_of_sleep=21999
ProfileSaver total_ms_of_work=0
ProfileSaver total_number_of_foreign_dex_marks=0
ProfileSaver max_number_profile_entries_cached=0
ProfileSaver total_number_of_hot_spikes=0
ProfileSaver total_number_of_wake_ups=2

suspend all histogram:	Sum: 2.077ms 99% C.I. 19us-1465.600us Avg: 207.700us Max: 1506us
DALVIK THREADS (10):
"Signal Catcher" daemon prio=5 tid=3 Runnable
  | group="system" sCount=0 dsCount=0 obj=0x12c00550 self=0xe13b4800
  | sysTid=1811 nice=0 cgrp=default sched=0/0 handle=0xe99a7920
  | state=R schedstat=( 15014001 11636384 5 ) utm=1 stm=0 core=1 HZ=100
  | stack=0xe98ab000-0xe98ad000 stackSize=1014KB
  | held mutexes= "mutator lock"(shared held)
  (no managed stack frames)

"main" prio=5 tid=1 Native
  | group="main" sCount=1 dsCount=0 obj=0x75c494e8 self=0xea385400
  | sysTid=1801 nice=0 cgrp=default sched=0/0 handle=0xed871534
  | state=S schedstat=( 483264615 33830613 186 ) utm=38 stm=10 core=2 HZ=100
  | stack=0xff254000-0xff256000 stackSize=8MB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.app.ActivityThread.main(ActivityThread.java:6251)
  at java.lang.reflect.Method.invoke!(Native method)
  at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1063)
  at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:924)

"Jit thread pool worker thread 0" prio=5 tid=2 Native (still starting up)
  | group="" sCount=1 dsCount=0 obj=0x0 self=0xe0103a00
  | sysTid=1810 nice=9 cgrp=default sched=0/0 handle=0xe9aa8920
  | state=S schedstat=( 46988688 42196080 60 ) utm=4 stm=0 core=0 HZ=100
  | stack=0xe99aa000-0xe99ac000 stackSize=1022KB
  | held mutexes=
  (no managed stack frames)

"FinalizerDaemon" daemon prio=5 tid=4 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c00670 self=0xe13b6100
  | sysTid=1821 nice=0 cgrp=default sched=0/0 handle=0xe97a3920
  | state=S schedstat=( 393076 1507385 7 ) utm=0 stm=0 core=3 HZ=100
  | stack=0xe96a1000-0xe96a3000 stackSize=1038KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0ca4d539> (a java.lang.Object)
  at java.lang.Object.wait(Object.java:407)
  at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:188)
  - locked <0x0ca4d539> (a java.lang.Object)
  at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:209)
  at java.lang.Daemons$FinalizerDaemon.run(Daemons.java:205)
  at java.lang.Thread.run(Thread.java:761)

"HeapTaskDaemon" daemon prio=5 tid=5 Blocked
  | group="system" sCount=1 dsCount=0 obj=0x12c00790 self=0xe13b6b00
  | sysTid=1823 nice=0 cgrp=default sched=0/0 handle=0xe4199920
  | state=S schedstat=( 309692 3781153 3 ) utm=0 stm=0 core=3 HZ=100
  | stack=0xe4097000-0xe4099000 stackSize=1038KB
  | held mutexes=
  at dalvik.system.VMRuntime.runHeapTasks(Native method)
  - waiting to lock an unknown object
  at java.lang.Daemons$HeapTaskDaemon.run(Daemons.java:438)
  at java.lang.Thread.run(Thread.java:761)

"ReferenceQueueDaemon" daemon prio=5 tid=6 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c005e0 self=0xe13b5c00
  | sysTid=1819 nice=0 cgrp=default sched=0/0 handle=0xe98a8920
  | state=S schedstat=( 356923 11524693 4 ) utm=0 stm=0 core=3 HZ=100
  | stack=0xe97a6000-0xe97a8000 stackSize=1038KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0e3c577e> (a java.lang.Class<java.lang.ref.ReferenceQueue>)
  at java.lang.Daemons$ReferenceQueueDaemon.run(Daemons.java:150)
  - locked <0x0e3c577e> (a java.lang.Class<java.lang.ref.ReferenceQueue>)
  at java.lang.Thread.run(Thread.java:761)

"FinalizerWatchdogDaemon" daemon prio=5 tid=7 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c00700 self=0xe13b6600
  | sysTid=1822 nice=0 cgrp=default sched=0/0 handle=0xe969e920
  | state=S schedstat=( 201924 13215923 4 ) utm=0 stm=0 core=2 HZ=100
  | stack=0xe959c000-0xe959e000 stackSize=1038KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0bcdd4df> (a java.lang.Daemons$FinalizerWatchdogDaemon)
  at java.lang.Daemons$FinalizerWatchdogDaemon.sleepUntilNeeded(Daemons.java:270)
  - locked <0x0bcdd4df> (a java.lang.Daemons$FinalizerWatchdogDaemon)
  at java.lang.Daemons$FinalizerWatchdogDaemon.run(Daemons.java:250)
  at java.lang.Thread.run(Thread.java:761)

"Binder:1801_1" prio=5 tid=8 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c00940 self=0xe0104e00
  | sysTid=1825 nice=0 cgrp=default sched=0/0 handle=0xe3e01920
  | state=S schedstat=( 2929384 22118849 16 ) utm=0 stm=0 core=1 HZ=100
  | stack=0xe3d05000-0xe3d07000 stackSize=1014KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1825/stack)
  native: #00 pc 00049588  /system/lib/libc.so (__ioctl+8)
  native: #01 pc 0001aa3f  /system/lib/libc.so (ioctl+38)
  native: #02 pc 0003ccd7  /system/lib/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+202)
  native: #03 pc 0003cded  /system/lib/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+8)
  native: #04 pc 0003d36b  /system/lib/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+46)
  native: #05 pc 0004f99b  /system/lib/libbinder.so (???)
  native: #06 pc 0000e355  /system/lib/libutils.so (_ZN7android6Thread11_threadLoopEPv+140)
  native: #07 pc 00066fe7  /system/lib/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+102)
  native: #08 pc 00047fd3  /system/lib/libc.so (_ZL15__pthread_startPv+22)
  native: #09 pc 00019a3d  /system/lib/libc.so (__start_thread+6)
  (no managed stack frames)

"Binder:1801_2" prio=5 tid=9 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c009d0 self=0xea385e00
  | sysTid=1838 nice=0 cgrp=default sched=0/0 handle=0xd22d2920
  | state=S schedstat=( 1850848 19541690 6 ) utm=0 stm=0 core=2 HZ=100
  | stack=0xd21d6000-0xd21d8000 stackSize=1014KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1838/stack)
  native: #00 pc 00049588  /system/lib/libc.so (__ioctl+8)
  native: #01 pc 0001aa3f  /system/lib/libc.so (ioctl+38)
  native: #02 pc 0003ccd7  /system/lib/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+202)
  native: #03 pc 0003cded  /system/lib/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+8)
  native: #04 pc 0003d36b  /system/lib/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+46)
  native: #05 pc 0004f99b  /system/lib/libbinder.so (???)
  native: #06 pc 0000e355  /system/lib/libutils.so (_ZN7android6Thread11_threadLoopEPv+140)
  native: #07 pc 00066fe7  /system/lib/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+102)
  native: #08 pc 00047fd3  /system/lib/libc.so (_ZL15__pthread_startPv+22)
  native: #09 pc 00019a3d  /system/lib/libc.so (__start_thread+6)
  (no managed stack frames)

"Profile Saver" daemon prio=5 tid=10 Native
  | group="system" sCount=1 dsCount=0 obj=0x12c00c10 self=0xe0106200
  | sysTid=1850 nice=0 cgrp=default sched=0/0 handle=0xd21d3920
  | state=S schedstat=( 3850693 6114922 14 ) utm=0 stm=0 core=0 HZ=100
  | stack=0xd20d7000-0xd20d9000 stackSize=1014KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1850/stack)
  native: #00 pc 00017424  /system/lib/libc.so (syscall+28)
  native: #01 pc 000b687d  /system/lib/libart.so (_ZN3art17ConditionVariable16WaitHoldingLocksEPNS_6ThreadE+92)
  native: #02 pc 00259c95  /system/lib/libart.so (_ZN3art12ProfileSaver3RunEv+296)
  native: #03 pc 0025aff5  /system/lib/libart.so (_ZN3art12ProfileSaver21RunProfileSaverThreadEPv+52)
  native: #04 pc 00047fd3  /system/lib/libc.so (_ZL15__pthread_startPv+22)
  native: #05 pc 00019a3d  /system/lib/libc.so (__start_thread+6)
  (no managed stack frames)

----- end 1801 -----

----- pid 1790 at 2018-09-14 13:46:57 -----
Cmd line: com.android.nfc
Build fingerprint: 'Mobilead/m80/m80:7.0/04.04.20180125/1516857511:user/dev-keys'
ABI: 'arm64'
Build type: optimized
Zygote loaded classes=4375 post zygote classes=42
Intern table: 48093 strong; 175 weak
JNI: CheckJNI is off; globals=467 (plus 119 weak)
Libraries: /system/app/Nfc/lib/arm64/libmtknfc_dynamic_load_jni.so /system/lib64/libandroid.so /system/lib64/libcompiler_rt.so /system/lib64/libjavacrypto.so /system/lib64/libjnigraphics.so /system/lib64/libmedia_jni.so /system/lib64/libwebviewchromium_loader.so libjavacore.so libopenjdk.so (9)
Heap: 62% free, 2MB/7MB; 26923 objects
Dumping cumulative Gc timings
Total number of allocations 26923
Total bytes allocated 2MB
Total bytes freed 0B
Free memory 4MB
Free memory until GC 4MB
Free memory until OOME 125MB
Total memory 7MB
Max memory 128MB
Zygote space size 1648KB
Total mutator paused time: 0
Total time waiting for GC to complete: 1.231us
Total GC count: 0
Total GC time: 0
Total blocking GC count: 0
Total blocking GC time: 0
Histogram of native allocation 0:561,128:235,256:102,384:61,640:1,896:16,1152:2 bucket size 128
Histogram of native free 0:27,64:1,96:28,192:214,288:37,480:9 bucket size 32
/system/app/Nfc/oat/arm64/Nfc.odex: speed
Current JIT code cache size: 0B
Current JIT data cache size: 0B
Current JIT capacity: 64KB
Current number of JIT code cache entries: 0
Total number of JIT compilations: 0
Total number of JIT compilations for on stack replacement: 0
Total number of deoptimizations: 0
Total number of JIT code cache collections: 0
Memory used for stack maps: Avg: 0B Max: 0B Min: -1B
Memory used for compiled code: Avg: 0B Max: 0B Min: -1B
Memory used for profiling info: Avg: 0B Max: 0B Min: -1B
Start Dumping histograms for 0 iterations for JIT timings
Done Dumping histograms
Memory used for compilation: Avg: 0B Max: 0B Min: -1B

suspend all histogram:	Sum: 740us 99% C.I. 13us-130us Avg: 67.272us Max: 130us
DALVIK THREADS (9):
"Signal Catcher" daemon prio=5 tid=3 Runnable
  | group="system" sCount=0 dsCount=0 obj=0x12c21280 self=0x7a0d907000
  | sysTid=1800 nice=0 cgrp=default sched=0/0 handle=0x7a160fe450
  | state=R schedstat=( 7131077 82616 3 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x7a16004000-0x7a16006000 stackSize=1005KB
  | held mutexes= "mutator lock"(shared held)
  (no managed stack frames)

"main" prio=5 tid=1 Native
  | group="main" sCount=1 dsCount=0 obj=0x762d4998 self=0x7a16c96a00
  | sysTid=1790 nice=0 cgrp=default sched=0/0 handle=0x7a1b22ca98
  | state=S schedstat=( 79757232 47678840 316 ) utm=5 stm=2 core=0 HZ=100
  | stack=0x7fd06c6000-0x7fd06c8000 stackSize=8MB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.app.ActivityThread.main(ActivityThread.java:6251)
  at java.lang.reflect.Method.invoke!(Native method)
  at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1063)
  at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:924)

"Jit thread pool worker thread 0" prio=5 tid=2 Native (still starting up)
  | group="" sCount=1 dsCount=0 obj=0x0 self=0x7a1040e000
  | sysTid=1799 nice=9 cgrp=default sched=0/0 handle=0x7a161ff450
  | state=S schedstat=( 482769 268461 2 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x7a16101000-0x7a16103000 stackSize=1021KB
  | held mutexes=
  (no managed stack frames)

"FinalizerWatchdogDaemon" daemon prio=5 tid=4 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c21430 self=0x7a16cc5200
  | sysTid=1805 nice=0 cgrp=default sched=0/0 handle=0x7a103ff450
  | state=S schedstat=( 362613 5020232 12 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x7a102fd000-0x7a102ff000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x00f9c52c> (a java.lang.Daemons$FinalizerWatchdogDaemon)
  at java.lang.Daemons$FinalizerWatchdogDaemon.sleepUntilNeeded(Daemons.java:270)
  - locked <0x00f9c52c> (a java.lang.Daemons$FinalizerWatchdogDaemon)
  at java.lang.Daemons$FinalizerWatchdogDaemon.run(Daemons.java:250)
  at java.lang.Thread.run(Thread.java:761)

"HeapTaskDaemon" daemon prio=5 tid=5 Blocked
  | group="system" sCount=1 dsCount=0 obj=0x12c214c0 self=0x7a16cc5c00
  | sysTid=1807 nice=0 cgrp=default sched=0/0 handle=0x7a102fa450
  | state=S schedstat=( 374077 2303845 6 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x7a101f8000-0x7a101fa000 stackSize=1037KB
  | held mutexes=
  at dalvik.system.VMRuntime.runHeapTasks(Native method)
  - waiting to lock an unknown object
  at java.lang.Daemons$HeapTaskDaemon.run(Daemons.java:438)
  at java.lang.Thread.run(Thread.java:761)

"ReferenceQueueDaemon" daemon prio=5 tid=6 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c21310 self=0x7a16cc3e00
  | sysTid=1802 nice=0 cgrp=default sched=0/0 handle=0x7a16001450
  | state=S schedstat=( 309307 4889154 5 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x7a15eff000-0x7a15f01000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x00748bf5> (a java.lang.Class<java.lang.ref.ReferenceQueue>)
  at java.lang.Daemons$ReferenceQueueDaemon.run(Daemons.java:150)
  - locked <0x00748bf5> (a java.lang.Class<java.lang.ref.ReferenceQueue>)
  at java.lang.Thread.run(Thread.java:761)

"FinalizerDaemon" daemon prio=5 tid=7 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c213a0 self=0x7a16cc4800
  | sysTid=1803 nice=0 cgrp=default sched=0/0 handle=0x7a10845450
  | state=S schedstat=( 419000 11829155 8 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x7a10743000-0x7a10745000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x08c26d8a> (a java.lang.Object)
  at java.lang.Object.wait(Object.java:407)
  at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:188)
  - locked <0x08c26d8a> (a java.lang.Object)
  at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:209)
  at java.lang.Daemons$FinalizerDaemon.run(Daemons.java:205)
  at java.lang.Thread.run(Thread.java:761)

"Binder:1790_1" prio=5 tid=8 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c21670 self=0x7a10417400
  | sysTid=1812 nice=0 cgrp=default sched=0/0 handle=0x79fe74c450
  | state=S schedstat=( 2504539 16844768 7 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x79fe652000-0x79fe654000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1812/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Binder:1790_2" prio=5 tid=9 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c21700 self=0x7a16cc6600
  | sysTid=1824 nice=0 cgrp=default sched=0/0 handle=0x79fe64f450
  | state=S schedstat=( 1805693 5771462 7 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x79fe555000-0x79fe557000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1824/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

----- end 1790 -----

----- pid 1776 at 2018-09-14 13:46:57 -----
Cmd line: com.mediatek.gba
Build fingerprint: 'Mobilead/m80/m80:7.0/04.04.20180125/1516857511:user/dev-keys'
ABI: 'arm64'
Build type: optimized
Zygote loaded classes=4375 post zygote classes=10
Intern table: 48046 strong; 175 weak
JNI: CheckJNI is off; globals=460 (plus 118 weak)
Libraries: /system/lib64/libandroid.so /system/lib64/libcompiler_rt.so /system/lib64/libjavacrypto.so /system/lib64/libjnigraphics.so /system/lib64/libmedia_jni.so /system/lib64/libwebviewchromium_loader.so libjavacore.so libopenjdk.so (8)
Heap: 65% free, 2MB/7MB; 25858 objects
Dumping cumulative Gc timings
Total number of allocations 25858
Total bytes allocated 2MB
Total bytes freed 0B
Free memory 4MB
Free memory until GC 4MB
Free memory until OOME 125MB
Total memory 7MB
Max memory 128MB
Zygote space size 1648KB
Total mutator paused time: 0
Total time waiting for GC to complete: 1.231us
Total GC count: 0
Total GC time: 0
Total blocking GC count: 0
Total blocking GC time: 0
Histogram of native allocation 0:560,128:232,256:102,384:60,640:1,896:16,1152:2 bucket size 128
Histogram of native free 0:27,96:28,192:214,288:37,480:9 bucket size 32
/system/vendor/app/Gba/oat/arm64/Gba.odex: speed
Current JIT code cache size: 0B
Current JIT data cache size: 0B
Current JIT capacity: 64KB
Current number of JIT code cache entries: 0
Total number of JIT compilations: 0
Total number of JIT compilations for on stack replacement: 0
Total number of deoptimizations: 0
Total number of JIT code cache collections: 0
Memory used for stack maps: Avg: 0B Max: 0B Min: -1B
Memory used for compiled code: Avg: 0B Max: 0B Min: -1B
Memory used for profiling info: Avg: 0B Max: 0B Min: -1B
Start Dumping histograms for 0 iterations for JIT timings
Done Dumping histograms
Memory used for compilation: Avg: 0B Max: 0B Min: -1B

suspend all histogram:	Sum: 744us 99% C.I. 14us-130us Avg: 67.636us Max: 130us
DALVIK THREADS (9):
"Signal Catcher" daemon prio=5 tid=3 Runnable
  | group="system" sCount=0 dsCount=0 obj=0x12c1ff70 self=0x7a0d907000
  | sysTid=1784 nice=0 cgrp=default sched=0/0 handle=0x7a160fe450
  | state=R schedstat=( 9369847 87385 2 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x7a16004000-0x7a16006000 stackSize=1005KB
  | held mutexes= "mutator lock"(shared held)
  (no managed stack frames)

"main" prio=5 tid=1 Native
  | group="main" sCount=1 dsCount=0 obj=0x762d4998 self=0x7a16c96a00
  | sysTid=1776 nice=0 cgrp=default sched=0/0 handle=0x7a1b22ca98
  | state=S schedstat=( 44560383 27665540 50 ) utm=1 stm=3 core=1 HZ=100
  | stack=0x7fd06c6000-0x7fd06c8000 stackSize=8MB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.app.ActivityThread.main(ActivityThread.java:6251)
  at java.lang.reflect.Method.invoke!(Native method)
  at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1063)
  at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:924)

"Jit thread pool worker thread 0" prio=5 tid=2 Native (still starting up)
  | group="" sCount=1 dsCount=0 obj=0x0 self=0x7a1040e000
  | sysTid=1783 nice=9 cgrp=default sched=0/0 handle=0x7a161ff450
  | state=S schedstat=( 504461 95077 2 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x7a16101000-0x7a16103000 stackSize=1021KB
  | held mutexes=
  (no managed stack frames)

"ReferenceQueueDaemon" daemon prio=5 tid=4 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c220d0 self=0x7a16cc3e00
  | sysTid=1785 nice=0 cgrp=default sched=0/0 handle=0x7a16001450
  | state=S schedstat=( 224846 0 3 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x7a15eff000-0x7a15f01000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0d6c1900> (a java.lang.Class<java.lang.ref.ReferenceQueue>)
  at java.lang.Daemons$ReferenceQueueDaemon.run(Daemons.java:150)
  - locked <0x0d6c1900> (a java.lang.Class<java.lang.ref.ReferenceQueue>)
  at java.lang.Thread.run(Thread.java:761)

"FinalizerWatchdogDaemon" daemon prio=5 tid=5 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c221f0 self=0x7a16cc5200
  | sysTid=1787 nice=0 cgrp=default sched=0/0 handle=0x7a103ff450
  | state=S schedstat=( 300154 1961308 6 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x7a102fd000-0x7a102ff000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0ca4d539> (a java.lang.Daemons$FinalizerWatchdogDaemon)
  at java.lang.Daemons$FinalizerWatchdogDaemon.sleepUntilNeeded(Daemons.java:270)
  - locked <0x0ca4d539> (a java.lang.Daemons$FinalizerWatchdogDaemon)
  at java.lang.Daemons$FinalizerWatchdogDaemon.run(Daemons.java:250)
  at java.lang.Thread.run(Thread.java:761)

"HeapTaskDaemon" daemon prio=5 tid=6 Blocked
  | group="system" sCount=1 dsCount=0 obj=0x12c22280 self=0x7a16cc5c00
  | sysTid=1788 nice=0 cgrp=default sched=0/0 handle=0x7a102fa450
  | state=S schedstat=( 262769 6247076 2 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x7a101f8000-0x7a101fa000 stackSize=1037KB
  | held mutexes=
  at dalvik.system.VMRuntime.runHeapTasks(Native method)
  - waiting to lock an unknown object
  at java.lang.Daemons$HeapTaskDaemon.run(Daemons.java:438)
  at java.lang.Thread.run(Thread.java:761)

"Binder:1776_1" prio=5 tid=7 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c22430 self=0x7a16cc6600
  | sysTid=1789 nice=0 cgrp=default sched=0/0 handle=0x79fe74c450
  | state=S schedstat=( 2293616 4121230 15 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x79fe652000-0x79fe654000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1789/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"FinalizerDaemon" daemon prio=5 tid=8 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c22160 self=0x7a16cc4800
  | sysTid=1786 nice=0 cgrp=default sched=0/0 handle=0x7a10845450
  | state=S schedstat=( 376231 3072153 9 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x7a10743000-0x7a10745000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0e3c577e> (a java.lang.Object)
  at java.lang.Object.wait(Object.java:407)
  at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:188)
  - locked <0x0e3c577e> (a java.lang.Object)
  at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:209)
  at java.lang.Daemons$FinalizerDaemon.run(Daemons.java:205)
  at java.lang.Thread.run(Thread.java:761)

"Binder:1776_2" prio=5 tid=9 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c224c0 self=0x7a0d8dca00
  | sysTid=1796 nice=0 cgrp=default sched=0/0 handle=0x79fe64f450
  | state=S schedstat=( 1992769 9808770 4 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x79fe555000-0x79fe557000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1796/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

----- end 1776 -----

----- pid 1507 at 2018-09-14 13:46:57 -----
Cmd line: com.mediatek.wfo.impl
Build fingerprint: 'Mobilead/m80/m80:7.0/04.04.20180125/1516857511:user/dev-keys'
ABI: 'arm64'
Build type: optimized
Zygote loaded classes=4375 post zygote classes=59
Intern table: 48151 strong; 175 weak
JNI: CheckJNI is off; globals=479 (plus 119 weak)
Libraries: /system/lib64/libandroid.so /system/lib64/libcompiler_rt.so /system/lib64/libjavacrypto.so /system/lib64/libjnigraphics.so /system/lib64/libmedia_jni.so /system/lib64/libwebviewchromium_loader.so /vendor/lib64/libwfo_jni.so libjavacore.so libopenjdk.so (9)
Heap: 63% free, 2MB/7MB; 27180 objects
Dumping cumulative Gc timings
Total number of allocations 27180
Total bytes allocated 2MB
Total bytes freed 0B
Free memory 4MB
Free memory until GC 4MB
Free memory until OOME 125MB
Total memory 7MB
Max memory 128MB
Zygote space size 1648KB
Total mutator paused time: 0
Total time waiting for GC to complete: 1.615us
Total GC count: 0
Total GC time: 0
Total blocking GC count: 0
Total blocking GC time: 0
Histogram of native allocation 0:561,128:233,256:102,384:60,640:1,896:16,1152:2 bucket size 128
Histogram of native free 0:27,64:1,96:28,192:214,288:37,480:9 bucket size 32
/vendor/priv-app/WfoService/oat/arm64/WfoService.odex: speed
Current JIT code cache size: 0B
Current JIT data cache size: 0B
Current JIT capacity: 64KB
Current number of JIT code cache entries: 0
Total number of JIT compilations: 0
Total number of JIT compilations for on stack replacement: 0
Total number of deoptimizations: 0
Total number of JIT code cache collections: 0
Memory used for stack maps: Avg: 0B Max: 0B Min: -1B
Memory used for compiled code: Avg: 0B Max: 0B Min: -1B
Memory used for profiling info: Avg: 0B Max: 0B Min: -1B
Start Dumping histograms for 0 iterations for JIT timings
Done Dumping histograms
Memory used for compilation: Avg: 0B Max: 0B Min: -1B
ProfileSaver total_bytes_written=0
ProfileSaver total_number_of_writes=0
ProfileSaver total_number_of_code_cache_queries=0
ProfileSaver total_number_of_skipped_writes=0
ProfileSaver total_number_of_failed_writes=0
ProfileSaver total_ms_of_sleep=2000
ProfileSaver total_ms_of_work=0
ProfileSaver total_number_of_foreign_dex_marks=0
ProfileSaver max_number_profile_entries_cached=0
ProfileSaver total_number_of_hot_spikes=0
ProfileSaver total_number_of_wake_ups=0

suspend all histogram:	Sum: 747us 99% C.I. 18us-130us Avg: 67.909us Max: 130us
DALVIK THREADS (12):
"Signal Catcher" daemon prio=5 tid=3 Runnable
  | group="system" sCount=0 dsCount=0 obj=0x12c00ee0 self=0x7a0d907000
  | sysTid=1513 nice=0 cgrp=default sched=0/0 handle=0x7a160fe450
  | state=R schedstat=( 13751308 93231 4 ) utm=0 stm=1 core=0 HZ=100
  | stack=0x7a16004000-0x7a16006000 stackSize=1005KB
  | held mutexes= "mutator lock"(shared held)
  (no managed stack frames)

"main" prio=5 tid=1 Native
  | group="main" sCount=1 dsCount=0 obj=0x762d4998 self=0x7a16c96a00
  | sysTid=1507 nice=0 cgrp=default sched=0/0 handle=0x7a1b22ca98
  | state=S schedstat=( 105019453 132865703 383 ) utm=7 stm=3 core=0 HZ=100
  | stack=0x7fd06c6000-0x7fd06c8000 stackSize=8MB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.app.ActivityThread.main(ActivityThread.java:6251)
  at java.lang.reflect.Method.invoke!(Native method)
  at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1063)
  at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:924)

"Jit thread pool worker thread 0" prio=5 tid=2 Native (still starting up)
  | group="" sCount=1 dsCount=0 obj=0x0 self=0x7a1040e000
  | sysTid=1512 nice=9 cgrp=default sched=0/0 handle=0x7a161ff450
  | state=S schedstat=( 471999 1621693 2 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x7a16101000-0x7a16103000 stackSize=1021KB
  | held mutexes=
  (no managed stack frames)

"ReferenceQueueDaemon" daemon prio=5 tid=4 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c00f70 self=0x7a16cc3e00
  | sysTid=1514 nice=0 cgrp=default sched=0/0 handle=0x7a16001450
  | state=S schedstat=( 246231 358230 3 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x7a15eff000-0x7a15f01000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x00f9c52c> (a java.lang.Class<java.lang.ref.ReferenceQueue>)
  at java.lang.Daemons$ReferenceQueueDaemon.run(Daemons.java:150)
  - locked <0x00f9c52c> (a java.lang.Class<java.lang.ref.ReferenceQueue>)
  at java.lang.Thread.run(Thread.java:761)

"FinalizerDaemon" daemon prio=5 tid=5 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c200d0 self=0x7a16cc4800
  | sysTid=1515 nice=0 cgrp=default sched=0/0 handle=0x7a10845450
  | state=S schedstat=( 579767 1548462 11 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x7a10743000-0x7a10745000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x00748bf5> (a java.lang.Object)
  at java.lang.Object.wait(Object.java:407)
  at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:188)
  - locked <0x00748bf5> (a java.lang.Object)
  at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:209)
  at java.lang.Daemons$FinalizerDaemon.run(Daemons.java:205)
  at java.lang.Thread.run(Thread.java:761)

"FinalizerWatchdogDaemon" daemon prio=5 tid=6 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c20160 self=0x7a16cc5200
  | sysTid=1516 nice=0 cgrp=default sched=0/0 handle=0x7a103ff450
  | state=S schedstat=( 371845 1554541 12 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x7a102fd000-0x7a102ff000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x08c26d8a> (a java.lang.Daemons$FinalizerWatchdogDaemon)
  at java.lang.Daemons$FinalizerWatchdogDaemon.sleepUntilNeeded(Daemons.java:270)
  - locked <0x08c26d8a> (a java.lang.Daemons$FinalizerWatchdogDaemon)
  at java.lang.Daemons$FinalizerWatchdogDaemon.run(Daemons.java:250)
  at java.lang.Thread.run(Thread.java:761)

"HeapTaskDaemon" daemon prio=5 tid=7 Blocked
  | group="system" sCount=1 dsCount=0 obj=0x12c201f0 self=0x7a16cc5c00
  | sysTid=1517 nice=0 cgrp=default sched=0/0 handle=0x7a102fa450
  | state=S schedstat=( 339692 295694 8 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x7a101f8000-0x7a101fa000 stackSize=1037KB
  | held mutexes=
  at dalvik.system.VMRuntime.runHeapTasks(Native method)
  - waiting to lock an unknown object
  at java.lang.Daemons$HeapTaskDaemon.run(Daemons.java:438)
  at java.lang.Thread.run(Thread.java:761)

"Binder:1507_1" prio=5 tid=8 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c203a0 self=0x7a10417400
  | sysTid=1518 nice=0 cgrp=default sched=0/0 handle=0x79fe74c450
  | state=S schedstat=( 7186387 7057077 33 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fe652000-0x79fe654000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1518/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Binder:1507_2" prio=5 tid=9 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c20430 self=0x7a16cc6600
  | sysTid=1519 nice=0 cgrp=default sched=0/0 handle=0x79fe64f450
  | state=S schedstat=( 8896926 7142689 36 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fe555000-0x79fe557000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1519/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Profile Saver" daemon prio=5 tid=10 Native
  | group="system" sCount=1 dsCount=0 obj=0x12c20550 self=0x7a10427800
  | sysTid=1542 nice=0 cgrp=default sched=0/0 handle=0x79fe552450
  | state=S schedstat=( 3301766 512154 21 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x79fe458000-0x79fe45a000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1542/stack)
  native: #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  native: #01 pc 00000000000e77c8  /system/lib64/libart.so (_ZN3art17ConditionVariable16WaitHoldingLocksEPNS_6ThreadE+156)
  native: #02 pc 000000000031deb4  /system/lib64/libart.so (_ZN3art12ProfileSaver3RunEv+284)
  native: #03 pc 000000000031f600  /system/lib64/libart.so (_ZN3art12ProfileSaver21RunProfileSaverThreadEPv+100)
  native: #04 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #05 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"ConnectivityThread" prio=5 tid=11 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c20940 self=0x7a0d8e3a00
  | sysTid=1572 nice=0 cgrp=default sched=0/0 handle=0x79fd0a1450
  | state=S schedstat=( 559154 1176616 4 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x79fcf9f000-0x79fcfa1000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1572/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"ConnectivityManager" prio=5 tid=12 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c20d30 self=0x7a0d8e4400
  | sysTid=1578 nice=0 cgrp=default sched=0/0 handle=0x79fcf97450
  | state=S schedstat=( 4610233 7848845 13 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fce95000-0x79fce97000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1578/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"diatek.wfo.impl" prio=5 (not attached)
  | sysTid=1545 nice=0 cgrp=default
  | state=S schedstat=( 84846 516154 1 ) utm=0 stm=0 core=2 HZ=100
  kernel: (couldn't read /proc/self/task/1545/stack)

"diatek.wfo.impl" prio=5 (not attached)
  | sysTid=1546 nice=0 cgrp=default
  | state=S schedstat=( 45615 23462 1 ) utm=0 stm=0 core=3 HZ=100
  kernel: (couldn't read /proc/self/task/1546/stack)

"diatek.wfo.impl" prio=5 (not attached)
  | sysTid=1547 nice=0 cgrp=default
  | state=S schedstat=( 40615 15153 1 ) utm=0 stm=0 core=3 HZ=100
  kernel: (couldn't read /proc/self/task/1547/stack)

"diatek.wfo.impl" prio=5 (not attached)
  | sysTid=1548 nice=0 cgrp=default
  | state=S schedstat=( 40692 15846 1 ) utm=0 stm=0 core=3 HZ=100
  kernel: (couldn't read /proc/self/task/1548/stack)

"diatek.wfo.impl" prio=5 (not attached)
  | sysTid=1549 nice=0 cgrp=default
  | state=S schedstat=( 38769 14770 1 ) utm=0 stm=0 core=3 HZ=100
  kernel: (couldn't read /proc/self/task/1549/stack)

"diatek.wfo.impl" prio=5 (not attached)
  | sysTid=1550 nice=0 cgrp=default
  | state=S schedstat=( 37077 15692 1 ) utm=0 stm=0 core=3 HZ=100
  kernel: (couldn't read /proc/self/task/1550/stack)

"diatek.wfo.impl" prio=5 (not attached)
  | sysTid=1551 nice=0 cgrp=default
  | state=S schedstat=( 38077 14923 1 ) utm=0 stm=0 core=3 HZ=100
  kernel: (couldn't read /proc/self/task/1551/stack)

"diatek.wfo.impl" prio=5 (not attached)
  | sysTid=1552 nice=0 cgrp=default
  | state=S schedstat=( 74846 98461 2 ) utm=0 stm=0 core=2 HZ=100
  kernel: (couldn't read /proc/self/task/1552/stack)

"diatek.wfo.impl" prio=5 (not attached)
  | sysTid=1553 nice=0 cgrp=default
  | state=S schedstat=( 63923 26923 1 ) utm=0 stm=0 core=2 HZ=100
  kernel: (couldn't read /proc/self/task/1553/stack)

"diatek.wfo.impl" prio=5 (not attached)
  | sysTid=1554 nice=0 cgrp=default
  | state=S schedstat=( 62539 13153 1 ) utm=0 stm=0 core=2 HZ=100
  kernel: (couldn't read /proc/self/task/1554/stack)

"diatek.wfo.impl" prio=5 (not attached)
  | sysTid=1555 nice=0 cgrp=default
  | state=S schedstat=( 67616 12384 1 ) utm=0 stm=0 core=3 HZ=100
  kernel: (couldn't read /proc/self/task/1555/stack)

"diatek.wfo.impl" prio=5 (not attached)
  | sysTid=1556 nice=0 cgrp=default
  | state=S schedstat=( 42077 22846 1 ) utm=0 stm=0 core=3 HZ=100
  kernel: (couldn't read /proc/self/task/1556/stack)

"diatek.wfo.impl" prio=5 (not attached)
  | sysTid=1557 nice=0 cgrp=default
  | state=S schedstat=( 42154 8461 1 ) utm=0 stm=0 core=3 HZ=100
  kernel: (couldn't read /proc/self/task/1557/stack)

"diatek.wfo.impl" prio=5 (not attached)
  | sysTid=1558 nice=0 cgrp=default
  | state=S schedstat=( 37923 7154 1 ) utm=0 stm=0 core=3 HZ=100
  kernel: (couldn't read /proc/self/task/1558/stack)

"diatek.wfo.impl" prio=5 (not attached)
  | sysTid=1560 nice=0 cgrp=default
  | state=S schedstat=( 99000 516538 4 ) utm=0 stm=0 core=3 HZ=100
  kernel: (couldn't read /proc/self/task/1560/stack)

"Thread-5" prio=5 (not attached)
  | sysTid=1561 nice=0 cgrp=default
  | state=S schedstat=( 4786306 16286002 31 ) utm=0 stm=0 core=2 HZ=100
  kernel: (couldn't read /proc/self/task/1561/stack)

"diatek.wfo.impl" prio=5 (not attached)
  | sysTid=1562 nice=0 cgrp=default
  | state=S schedstat=( 117384 275847 5 ) utm=0 stm=0 core=0 HZ=100
  kernel: (couldn't read /proc/self/task/1562/stack)

"diatek.wfo.impl" prio=5 (not attached)
  | sysTid=1563 nice=0 cgrp=default
  | state=S schedstat=( 39923 135384 2 ) utm=0 stm=0 core=3 HZ=100
  kernel: (couldn't read /proc/self/task/1563/stack)

"diatek.wfo.impl" prio=5 (not attached)
  | sysTid=1564 nice=0 cgrp=default
  | state=S schedstat=( 4505000 7749153 20 ) utm=0 stm=0 core=2 HZ=100
  kernel: (couldn't read /proc/self/task/1564/stack)

----- end 1507 -----

----- pid 1493 at 2018-09-14 13:46:57 -----
Cmd line: com.mediatek.ims
Build fingerprint: 'Mobilead/m80/m80:7.0/04.04.20180125/1516857511:user/dev-keys'
ABI: 'arm64'
Build type: optimized
Zygote loaded classes=4375 post zygote classes=160
Intern table: 48357 strong; 177 weak
JNI: CheckJNI is off; globals=492 (plus 121 weak)
Libraries: /system/lib64/libandroid.so /system/lib64/libcompiler_rt.so /system/lib64/libjavacrypto.so /system/lib64/libjnigraphics.so /system/lib64/libmedia_jni.so /system/lib64/libwebviewchromium_loader.so libjavacore.so libopenjdk.so (8)
Heap: 50% free, 3MB/7MB; 34469 objects
Dumping cumulative Gc timings
Total number of allocations 34469
Total bytes allocated 3MB
Total bytes freed 0B
Free memory 3MB
Free memory until GC 3MB
Free memory until OOME 124MB
Total memory 7MB
Max memory 128MB
Zygote space size 1648KB
Total mutator paused time: 0
Total time waiting for GC to complete: 1.539us
Total GC count: 0
Total GC time: 0
Total blocking GC count: 0
Total blocking GC time: 0
Histogram of native allocation 0:561,128:294,256:102,384:60,640:1,896:16,1152:2 bucket size 128
Histogram of native free 0:27,64:1,96:28,192:214,288:37,480:9 bucket size 32
/vendor/plugin/FwkPlugin/oat/arm64/FwkPlugin.odex: speed
/vendor/plugin/FwkPlugin/oat/arm64/FwkPlugin.odex: speed
/vendor/priv-app/ImsService/oat/arm64/ImsService.odex: speed
Current JIT code cache size: 344B
Current JIT data cache size: 2064B
Current JIT capacity: 64KB
Current number of JIT code cache entries: 1
Total number of JIT compilations: 1
Total number of JIT compilations for on stack replacement: 0
Total number of deoptimizations: 0
Total number of JIT code cache collections: 0
Memory used for stack maps: Avg: 88B Max: 88B Min: 88B
Memory used for compiled code: Avg: 308B Max: 308B Min: 308B
Memory used for profiling info: Avg: 656B Max: 1664B Min: 152B
Start Dumping histograms for 1 iterations for JIT timings
Compiling:	Sum: 17.165ms 99% C.I. 17.165ms-17.165ms Avg: 17.165ms Max: 17.165ms
TrimMaps:	Sum: 133us 99% C.I. 133us-133us Avg: 133us Max: 133us
Done Dumping histograms
Memory used for compilation: Avg: 187KB Max: 187KB Min: 187KB
ProfileSaver total_bytes_written=0
ProfileSaver total_number_of_writes=0
ProfileSaver total_number_of_code_cache_queries=0
ProfileSaver total_number_of_skipped_writes=0
ProfileSaver total_number_of_failed_writes=0
ProfileSaver total_ms_of_sleep=2000
ProfileSaver total_ms_of_work=0
ProfileSaver total_number_of_foreign_dex_marks=2
ProfileSaver max_number_profile_entries_cached=0
ProfileSaver total_number_of_hot_spikes=0
ProfileSaver total_number_of_wake_ups=0

suspend all histogram:	Sum: 754us 99% C.I. 20us-130us Avg: 68.545us Max: 130us
DALVIK THREADS (18):
"Signal Catcher" daemon prio=5 tid=3 Runnable
  | group="system" sCount=0 dsCount=0 obj=0x12c00ca0 self=0x7a0d907000
  | sysTid=1500 nice=0 cgrp=default sched=0/0 handle=0x7a160fe450
  | state=R schedstat=( 6280385 736462 6 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x7a16004000-0x7a16006000 stackSize=1005KB
  | held mutexes= "mutator lock"(shared held)
  (no managed stack frames)

"main" prio=5 tid=1 Native
  | group="main" sCount=1 dsCount=0 obj=0x762d4998 self=0x7a16c96a00
  | sysTid=1493 nice=0 cgrp=default sched=0/0 handle=0x7a1b22ca98
  | state=S schedstat=( 189816768 167167922 532 ) utm=11 stm=8 core=0 HZ=100
  | stack=0x7fd06c6000-0x7fd06c8000 stackSize=8MB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.app.ActivityThread.main(ActivityThread.java:6251)
  at java.lang.reflect.Method.invoke!(Native method)
  at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1063)
  at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:924)

"Jit thread pool worker thread 0" prio=5 tid=2 Native (still starting up)
  | group="" sCount=1 dsCount=0 obj=0x0 self=0x7a1040e000
  | sysTid=1499 nice=9 cgrp=default sched=0/0 handle=0x7a161ff450
  | state=S schedstat=( 5557460 6605077 19 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x7a16101000-0x7a16103000 stackSize=1021KB
  | held mutexes=
  (no managed stack frames)

"ReferenceQueueDaemon" daemon prio=5 tid=4 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c00d30 self=0x7a16cc3e00
  | sysTid=1501 nice=0 cgrp=default sched=0/0 handle=0x7a16001450
  | state=S schedstat=( 348307 828154 9 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x7a15eff000-0x7a15f01000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x02f103fd> (a java.lang.Class<java.lang.ref.ReferenceQueue>)
  at java.lang.Daemons$ReferenceQueueDaemon.run(Daemons.java:150)
  - locked <0x02f103fd> (a java.lang.Class<java.lang.ref.ReferenceQueue>)
  at java.lang.Thread.run(Thread.java:761)

"FinalizerDaemon" daemon prio=5 tid=5 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c00dc0 self=0x7a16cc4800
  | sysTid=1502 nice=0 cgrp=default sched=0/0 handle=0x7a10845450
  | state=S schedstat=( 569538 4134845 18 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x7a10743000-0x7a10745000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0283f8f2> (a java.lang.Object)
  at java.lang.Object.wait(Object.java:407)
  at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:188)
  - locked <0x0283f8f2> (a java.lang.Object)
  at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:209)
  at java.lang.Daemons$FinalizerDaemon.run(Daemons.java:205)
  at java.lang.Thread.run(Thread.java:761)

"FinalizerWatchdogDaemon" daemon prio=5 tid=6 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c00e50 self=0x7a16cc5200
  | sysTid=1503 nice=0 cgrp=default sched=0/0 handle=0x7a103ff450
  | state=S schedstat=( 469691 3346079 14 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x7a102fd000-0x7a102ff000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0536eb43> (a java.lang.Daemons$FinalizerWatchdogDaemon)
  at java.lang.Daemons$FinalizerWatchdogDaemon.sleepUntilNeeded(Daemons.java:270)
  - locked <0x0536eb43> (a java.lang.Daemons$FinalizerWatchdogDaemon)
  at java.lang.Daemons$FinalizerWatchdogDaemon.run(Daemons.java:250)
  at java.lang.Thread.run(Thread.java:761)

"HeapTaskDaemon" daemon prio=5 tid=7 Blocked
  | group="system" sCount=1 dsCount=0 obj=0x12c00ee0 self=0x7a16cc5c00
  | sysTid=1504 nice=0 cgrp=default sched=0/0 handle=0x7a102fa450
  | state=S schedstat=( 480769 1800539 7 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x7a101f8000-0x7a101fa000 stackSize=1037KB
  | held mutexes=
  at dalvik.system.VMRuntime.runHeapTasks(Native method)
  - waiting to lock an unknown object
  at java.lang.Daemons$HeapTaskDaemon.run(Daemons.java:438)
  at java.lang.Thread.run(Thread.java:761)

"Binder:1493_1" prio=5 tid=8 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c20160 self=0x7a10417400
  | sysTid=1505 nice=0 cgrp=default sched=0/0 handle=0x79fe74c450
  | state=S schedstat=( 15577385 21675155 51 ) utm=1 stm=0 core=1 HZ=100
  | stack=0x79fe652000-0x79fe654000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1505/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Binder:1493_2" prio=5 tid=9 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c201f0 self=0x7a16cc6600
  | sysTid=1506 nice=0 cgrp=default sched=0/0 handle=0x79fe64f450
  | state=S schedstat=( 20631463 19683695 48 ) utm=2 stm=0 core=0 HZ=100
  | stack=0x79fe555000-0x79fe557000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1506/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Profile Saver" daemon prio=5 tid=10 Native
  | group="system" sCount=1 dsCount=0 obj=0x12c20280 self=0x7a10427800
  | sysTid=1543 nice=0 cgrp=default sched=0/0 handle=0x79fe2e6450
  | state=S schedstat=( 3712922 5430539 21 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fe1ec000-0x79fe1ee000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1543/stack)
  native: #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  native: #01 pc 00000000000e77c8  /system/lib64/libart.so (_ZN3art17ConditionVariable16WaitHoldingLocksEPNS_6ThreadE+156)
  native: #02 pc 000000000031deb4  /system/lib64/libart.so (_ZN3art12ProfileSaver3RunEv+284)
  native: #03 pc 000000000031f600  /system/lib64/libart.so (_ZN3art12ProfileSaver21RunProfileSaverThreadEPv+100)
  native: #04 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #05 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Thread-3" prio=5 tid=11 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c208b0 self=0x7a0d8e3a00
  | sysTid=1574 nice=0 cgrp=default sched=0/0 handle=0x79fe1e9450
  | state=S schedstat=( 2267230 1738616 6 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fe0e7000-0x79fe0e9000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1574/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at com.mediatek.ims.internal.DataDispatcher$3.run(DataDispatcher.java:356)

"Thread-4" prio=5 tid=12 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c20c10 self=0x7a0d8e4400
  | sysTid=1580 nice=0 cgrp=default sched=0/0 handle=0x79fe0e4450
  | state=S schedstat=( 770230 2537154 4 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x79fdfe2000-0x79fdfe4000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1580/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at com.mediatek.ims.internal.ImsSimservsDispatcher$1.run(ImsSimservsDispatcher.java:58)

"Thread-2" prio=5 tid=13 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c147c0 self=0x7a0d8e4e00
  | sysTid=1582 nice=0 cgrp=default sched=0/0 handle=0x79fdfdf450
  | state=S schedstat=( 1428080 13672844 15 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fdedd000-0x79fdedf000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1582/stack)
  native: #00 pc 000000000006d318  /system/lib64/libc.so (recvmsg+4)
  native: #01 pc 00000000000f6014  /system/lib64/libandroid_runtime.so (???)
  native: #02 pc 00000000000f6330  /system/lib64/libandroid_runtime.so (???)
  native: #03 pc 0000000000746dd8  /system/framework/arm64/boot-framework.oat (Java_android_net_LocalSocketImpl_readba_1native___3BIILjava_io_FileDescriptor_2+196)
  at android.net.LocalSocketImpl.readba_native(Native method)
  at android.net.LocalSocketImpl.-wrap1(LocalSocketImpl.java:-1)
  at android.net.LocalSocketImpl$SocketInputStream.read(LocalSocketImpl.java:110)
  - locked <0x0ce2c5c0> (a java.lang.Object)
  at java.io.DataInputStream.readFully(DataInputStream.java:195)
  at com.mediatek.ims.ImsAdapter$VaSocketIO.readInt(ImsAdapter.java:492)
  at com.mediatek.ims.ImsAdapter$VaSocketIO.readEvent(ImsAdapter.java:507)
  at com.mediatek.ims.ImsAdapter$VaSocketIO.run(ImsAdapter.java:330)

"ImsRILSender" prio=5 tid=14 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c64310 self=0x7a0d8e5800
  | sysTid=1583 nice=0 cgrp=default sched=0/0 handle=0x79fdeda450
  | state=S schedstat=( 1739999 606153 7 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fddd8000-0x79fddda000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1583/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"ImsRILReceiver" prio=5 tid=15 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c643a0 self=0x7a0d8e6200
  | sysTid=1584 nice=0 cgrp=default sched=0/0 handle=0x79fddd5450
  | state=S schedstat=( 7674459 5817539 26 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x79fdcd3000-0x79fdcd5000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1584/stack)
  native: #00 pc 000000000006d318  /system/lib64/libc.so (recvmsg+4)
  native: #01 pc 00000000000f6014  /system/lib64/libandroid_runtime.so (???)
  native: #02 pc 00000000000f6330  /system/lib64/libandroid_runtime.so (???)
  native: #03 pc 0000000000746dd8  /system/framework/arm64/boot-framework.oat (Java_android_net_LocalSocketImpl_readba_1native___3BIILjava_io_FileDescriptor_2+196)
  at android.net.LocalSocketImpl.readba_native(Native method)
  at android.net.LocalSocketImpl.-wrap1(LocalSocketImpl.java:-1)
  at android.net.LocalSocketImpl$SocketInputStream.read(LocalSocketImpl.java:110)
  - locked <0x074ee2f9> (a java.lang.Object)
  at com.mediatek.ims.ImsRILAdapter.readRilMessage(ImsRILAdapter.java:1107)
  at com.mediatek.ims.ImsRILAdapter.-wrap2(ImsRILAdapter.java:-1)
  at com.mediatek.ims.ImsRILAdapter$ImsRILReceiver.run(ImsRILAdapter.java:1339)
  at java.lang.Thread.run(Thread.java:761)

"ImsConfig-0" prio=5 tid=16 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c64550 self=0x7a0d8e6c00
  | sysTid=1585 nice=0 cgrp=default sched=0/0 handle=0x79fdcd0450
  | state=S schedstat=( 705383 178385 4 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x79fdbce000-0x79fdbd0000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1585/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"Binder:1493_3" prio=5 tid=17 Native
  | group="main" sCount=1 dsCount=0 obj=0x12cc7430 self=0x7a0d8e7600
  | sysTid=1881 nice=0 cgrp=default sched=0/0 handle=0x79fd9cb450
  | state=S schedstat=( 13338458 1142536 33 ) utm=0 stm=1 core=2 HZ=100
  | stack=0x79fd8d1000-0x79fd8d3000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1881/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Binder:1493_4" prio=5 tid=18 Native
  | group="main" sCount=1 dsCount=0 obj=0x12d485e0 self=0x7a10448c00
  | sysTid=2203 nice=0 cgrp=default sched=0/0 handle=0x79fd7c2450
  | state=S schedstat=( 961846 219307 2 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x79fd6c8000-0x79fd6ca000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/2203/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

----- end 1493 -----

----- pid 1480 at 2018-09-14 13:46:57 -----
Cmd line: com.android.phone
Build fingerprint: 'Mobilead/m80/m80:7.0/04.04.20180125/1516857511:user/dev-keys'
ABI: 'arm64'
Build type: optimized
Zygote loaded classes=4375 post zygote classes=660
Intern table: 49002 strong; 178 weak
JNI: CheckJNI is off; globals=646 (plus 133 weak)
Libraries: /system/lib64/libandroid.so /system/lib64/libcompiler_rt.so /system/lib64/libjavacrypto.so /system/lib64/libjnigraphics.so /system/lib64/libmedia_jni.so /system/lib64/libwebviewchromium_loader.so libjavacore.so libopenjdk.so (8)
Heap: 16% free, 5MB/7MB; 73842 objects
Dumping cumulative Gc timings
Start Dumping histograms for 1 iterations for sticky concurrent mark sweep
FreeList:	Sum: 7.213ms 99% C.I. 68us-384us Avg: 200.361us Max: 384us
ScanGrayImageSpaceObjects:	Sum: 5.557ms 99% C.I. 1us-2594us Avg: 173.656us Max: 2817us
ScanGrayAllocSpaceObjects:	Sum: 4.525ms 99% C.I. 0.002ms-3.931ms Avg: 1.131ms Max: 3.988ms
ProcessMarkStack:	Sum: 4.169ms 99% C.I. 0.002ms-3.924ms Avg: 1.042ms Max: 3.983ms
MarkConcurrentRoots:	Sum: 3.863ms 99% C.I. 0.008ms-3.855ms Avg: 1.931ms Max: 3.855ms
MarkRootsCheckpoint:	Sum: 1.912ms 99% C.I. 532us-1380us Avg: 956us Max: 1380us
SweepArray:	Sum: 1.847ms 99% C.I. 1.847ms-1.847ms Avg: 1.847ms Max: 1.847ms
ScanGrayZygoteSpaceObjects:	Sum: 1.600ms 99% C.I. 10us-1584.500us Avg: 800us Max: 1590us
ReMarkRoots:	Sum: 631us 99% C.I. 631us-631us Avg: 631us Max: 631us
MarkingPhase:	Sum: 370us 99% C.I. 370us-370us Avg: 370us Max: 370us
SweepSystemWeaks:	Sum: 268us 99% C.I. 268us-268us Avg: 268us Max: 268us
AllocSpaceClearCards:	Sum: 267us 99% C.I. 6us-131us Avg: 66.750us Max: 131us
ImageModUnionClearCards:	Sum: 245us 99% C.I. 1us-92us Avg: 7.656us Max: 92us
MarkNonThreadRoots:	Sum: 216us 99% C.I. 93us-123us Avg: 108us Max: 123us
BindBitmaps:	Sum: 197us 99% C.I. 197us-197us Avg: 197us Max: 197us
EnqueueFinalizerReferences:	Sum: 105us 99% C.I. 105us-105us Avg: 105us Max: 105us
ZygoteModUnionClearCards:	Sum: 73us 99% C.I. 22us-51us Avg: 36.500us Max: 51us
ResetStack:	Sum: 70us 99% C.I. 70us-70us Avg: 70us Max: 70us
ProcessCards:	Sum: 67us 99% C.I. 33us-34us Avg: 33.500us Max: 34us
(Paused)PausePhase:	Sum: 65us 99% C.I. 65us-65us Avg: 65us Max: 65us
ProcessReferences:	Sum: 56us 99% C.I. 56us-56us Avg: 56us Max: 56us
(Paused)ScanGrayImageSpaceObjects:	Sum: 52us 99% C.I. 1us-24us Avg: 3.250us Max: 24us
InitializePhase:	Sum: 50us 99% C.I. 50us-50us Avg: 50us Max: 50us
FinishPhase:	Sum: 47us 99% C.I. 47us-47us Avg: 47us Max: 47us
PreCleanCards:	Sum: 42us 99% C.I. 42us-42us Avg: 42us Max: 42us
(Paused)ScanGrayAllocSpaceObjects:	Sum: 40us 99% C.I. 2us-38us Avg: 20us Max: 38us
ReclaimPhase:	Sum: 35us 99% C.I. 35us-35us Avg: 35us Max: 35us
RevokeAllThreadLocalAllocationStacks:	Sum: 31us 99% C.I. 31us-31us Avg: 31us Max: 31us
SwapBitmaps:	Sum: 11us 99% C.I. 11us-11us Avg: 11us Max: 11us
(Paused)ScanGrayZygoteSpaceObjects:	Sum: 10us 99% C.I. 10us-10us Avg: 10us Max: 10us
MarkRoots:	Sum: 8us 99% C.I. 8us-8us Avg: 8us Max: 8us
ForwardSoftReferences:	Sum: 6us 99% C.I. 6us-6us Avg: 6us Max: 6us
UnBindBitmaps:	Sum: 4us 99% C.I. 4us-4us Avg: 4us Max: 4us
FindDefaultSpaceBitmap:	Sum: 3us 99% C.I. 3us-3us Avg: 3us Max: 3us
(Paused)ProcessMarkStack:	Sum: 2us 99% C.I. 2us-2us Avg: 2us Max: 2us
PreSweepingGcVerification:	Sum: 1us 99% C.I. 1us-1us Avg: 1us Max: 1us
Done Dumping histograms
sticky concurrent mark sweep paused:	Sum: 1.003ms 99% C.I. 1.003ms-1.003ms Avg: 1.003ms Max: 1.003ms
sticky concurrent mark sweep total time: 33.664ms mean time: 33.664ms
sticky concurrent mark sweep freed: 35297 objects with total size 2MB
sticky concurrent mark sweep throughput: 1.06961e+06/s / 83MB/s
Total time spent in GC: 33.664ms
Mean GC size throughput: 78MB/s
Mean GC object throughput: 1.04833e+06 objects/s
Total number of allocations 109133
Total bytes allocated 8MB
Total bytes freed 2MB
Free memory 1215KB
Free memory until GC 1215KB
Free memory until OOME 122MB
Total memory 7MB
Max memory 128MB
Zygote space size 1648KB
Total mutator paused time: 1.003ms
Total time waiting for GC to complete: 6.231us
Total GC count: 1
Total GC time: 33.664ms
Total blocking GC count: 0
Total blocking GC time: 0
Histogram of GC count per 10000 ms: 0:1
Histogram of blocking GC count per 10000 ms: 0:1
Histogram of native allocation 0:562,128:291,256:102,384:61,640:1,896:16,1152:2 bucket size 128
Histogram of native free 0:27,64:2,96:28,192:235,288:37,480:9 bucket size 32
/vendor/plugin/FwkPlugin/oat/arm64/FwkPlugin.odex: speed
/vendor/plugin/FwkPlugin/oat/arm64/FwkPlugin.odex: speed
/vendor/plugin/FwkPlugin/oat/arm64/FwkPlugin.odex: speed
/vendor/plugin/FwkPlugin/oat/arm64/FwkPlugin.odex: speed
/vendor/plugin/FwkPlugin/oat/arm64/FwkPlugin.odex: speed
/vendor/priv-app/Stk1/oat/arm64/Stk1.odex: speed
/system/priv-app/TeleService/oat/arm64/TeleService.odex: speed
/system/priv-app/TelephonyProvider/oat/arm64/TelephonyProvider.odex: speed
Current JIT code cache size: 23KB
Current JIT data cache size: 32KB
Current JIT capacity: 128KB
Current number of JIT code cache entries: 9
Total number of JIT compilations: 9
Total number of JIT compilations for on stack replacement: 0
Total number of deoptimizations: 0
Total number of JIT code cache collections: 1
Memory used for stack maps: Avg: 2657B Max: 19KB Min: 88B
Memory used for compiled code: Avg: 2687B Max: 17KB Min: 276B
Memory used for profiling info: Avg: 609B Max: 3KB Min: 32B
Start Dumping histograms for 11 iterations for JIT timings
Compiling:	Sum: 185.842ms 99% C.I. 1.249ms-119.359ms Avg: 18.584ms Max: 124.725ms
TrimMaps:	Sum: 4.516ms 99% C.I. 20us-2300us Avg: 451.600us Max: 2346us
Code cache collection:	Sum: 1.434ms 99% C.I. 1.434ms-1.434ms Avg: 1.434ms Max: 1.434ms
Done Dumping histograms
Memory used for compilation: Avg: 1212KB Max: 8MB Min: 98KB
ProfileSaver total_bytes_written=0
ProfileSaver total_number_of_writes=0
ProfileSaver total_number_of_code_cache_queries=0
ProfileSaver total_number_of_skipped_writes=0
ProfileSaver total_number_of_failed_writes=0
ProfileSaver total_ms_of_sleep=2000
ProfileSaver total_ms_of_work=0
ProfileSaver total_number_of_foreign_dex_marks=1
ProfileSaver max_number_profile_entries_cached=0
ProfileSaver total_number_of_hot_spikes=0
ProfileSaver total_number_of_wake_ups=0

suspend all histogram:	Sum: 903us 99% C.I. 35us-130us Avg: 75.250us Max: 130us
DALVIK THREADS (24):
"Signal Catcher" daemon prio=5 tid=3 Runnable
  | group="system" sCount=0 dsCount=0 obj=0x12c00a60 self=0x7a0d907000
  | sysTid=1487 nice=0 cgrp=default sched=0/0 handle=0x7a160fe450
  | state=R schedstat=( 11860923 620693 4 ) utm=1 stm=0 core=0 HZ=100
  | stack=0x7a16004000-0x7a16006000 stackSize=1005KB
  | held mutexes= "mutator lock"(shared held)
  (no managed stack frames)

"main" prio=5 tid=1 Native
  | group="main" sCount=1 dsCount=0 obj=0x762d4998 self=0x7a16c96a00
  | sysTid=1480 nice=0 cgrp=default sched=0/0 handle=0x7a1b22ca98
  | state=S schedstat=( 908414522 537656934 1899 ) utm=70 stm=20 core=0 HZ=100
  | stack=0x7fd06c6000-0x7fd06c8000 stackSize=8MB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.app.ActivityThread.main(ActivityThread.java:6251)
  at java.lang.reflect.Method.invoke!(Native method)
  at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1063)
  at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:924)

"Jit thread pool worker thread 0" prio=5 tid=2 Native (still starting up)
  | group="" sCount=1 dsCount=0 obj=0x0 self=0x7a1040e000
  | sysTid=1486 nice=9 cgrp=default sched=0/0 handle=0x7a161ff450
  | state=S schedstat=( 164124849 30476615 97 ) utm=13 stm=3 core=0 HZ=100
  | stack=0x7a16101000-0x7a16103000 stackSize=1021KB
  | held mutexes=
  (no managed stack frames)

"ReferenceQueueDaemon" daemon prio=5 tid=4 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c00af0 self=0x7a16cc3e00
  | sysTid=1488 nice=0 cgrp=default sched=0/0 handle=0x7a16001450
  | state=S schedstat=( 597231 751078 9 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x7a15eff000-0x7a15f01000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0f5167e8> (a java.lang.Class<java.lang.ref.ReferenceQueue>)
  at java.lang.Daemons$ReferenceQueueDaemon.run(Daemons.java:150)
  - locked <0x0f5167e8> (a java.lang.Class<java.lang.ref.ReferenceQueue>)
  at java.lang.Thread.run(Thread.java:761)

"FinalizerDaemon" daemon prio=5 tid=5 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c00b80 self=0x7a16cc4800
  | sysTid=1489 nice=0 cgrp=default sched=0/0 handle=0x7a10845450
  | state=S schedstat=( 2401232 671152 7 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x7a10743000-0x7a10745000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x08c21e01> (a java.lang.Object)
  at java.lang.Object.wait(Object.java:407)
  at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:188)
  - locked <0x08c21e01> (a java.lang.Object)
  at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:209)
  at java.lang.Daemons$FinalizerDaemon.run(Daemons.java:205)
  at java.lang.Thread.run(Thread.java:761)

"FinalizerWatchdogDaemon" daemon prio=5 tid=6 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c00c10 self=0x7a16cc5200
  | sysTid=1490 nice=0 cgrp=default sched=0/0 handle=0x7a103ff450
  | state=S schedstat=( 772462 2698311 17 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x7a102fd000-0x7a102ff000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x059144a6> (a java.lang.Daemons$FinalizerWatchdogDaemon)
  at java.lang.Daemons$FinalizerWatchdogDaemon.sleepUntilNeeded(Daemons.java:270)
  - locked <0x059144a6> (a java.lang.Daemons$FinalizerWatchdogDaemon)
  at java.lang.Daemons$FinalizerWatchdogDaemon.run(Daemons.java:250)
  at java.lang.Thread.run(Thread.java:761)

"HeapTaskDaemon" daemon prio=5 tid=7 Blocked
  | group="system" sCount=1 dsCount=0 obj=0x12c00ca0 self=0x7a16cc5c00
  | sysTid=1491 nice=0 cgrp=default sched=0/0 handle=0x7a102fa450
  | state=S schedstat=( 35894383 4958386 19 ) utm=3 stm=0 core=0 HZ=100
  | stack=0x7a101f8000-0x7a101fa000 stackSize=1037KB
  | held mutexes=
  at dalvik.system.VMRuntime.runHeapTasks(Native method)
  - waiting to lock an unknown object
  at java.lang.Daemons$HeapTaskDaemon.run(Daemons.java:438)
  at java.lang.Thread.run(Thread.java:761)

"Binder:1480_1" prio=5 tid=8 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c00e50 self=0x7a10417400
  | sysTid=1492 nice=0 cgrp=default sched=0/0 handle=0x79fe74c450
  | state=S schedstat=( 71962072 67011159 331 ) utm=4 stm=3 core=0 HZ=100
  | stack=0x79fe652000-0x79fe654000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1492/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Binder:1480_2" prio=5 tid=9 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c00ee0 self=0x7a16cc6600
  | sysTid=1496 nice=0 cgrp=default sched=0/0 handle=0x79fe64f450
  | state=S schedstat=( 76256307 83841539 342 ) utm=7 stm=0 core=0 HZ=100
  | stack=0x79fe555000-0x79fe557000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1496/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"pool-1-thread-1" prio=5 tid=10 Waiting
  | group="main" sCount=1 dsCount=0 obj=0x12c37dc0 self=0x7a0d8e5800
  | sysTid=1595 nice=0 cgrp=default sched=0/0 handle=0x79fda34450
  | state=S schedstat=( 681001 45461 3 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x79fd932000-0x79fd934000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0028fee7> (a java.lang.Object)
  at java.lang.Thread.parkFor$(Thread.java:2127)
  - locked <0x0028fee7> (a java.lang.Object)
  at sun.misc.Unsafe.park(Unsafe.java:325)
  at java.util.concurrent.locks.LockSupport.park(LockSupport.java:161)
  at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2035)
  at java.util.concurrent.LinkedBlockingQueue.take(LinkedBlockingQueue.java:413)
  at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1058)
  at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1118)
  at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:607)
  at java.lang.Thread.run(Thread.java:761)

"RILSender0" prio=5 tid=11 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c73790 self=0x7a0d8e6200
  | sysTid=1647 nice=0 cgrp=default sched=0/0 handle=0x79fd8ef450
  | state=S schedstat=( 11894384 18408305 78 ) utm=0 stm=1 core=0 HZ=100
  | stack=0x79fd7ed000-0x79fd7ef000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1647/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"RILReceiver0" prio=5 tid=12 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c73820 self=0x7a0d8e6c00
  | sysTid=1648 nice=0 cgrp=default sched=0/0 handle=0x79fd7ea450
  | state=S schedstat=( 50688393 73339847 152 ) utm=3 stm=2 core=0 HZ=100
  | stack=0x79fd6e8000-0x79fd6ea000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1648/stack)
  native: #00 pc 000000000006d318  /system/lib64/libc.so (recvmsg+4)
  native: #01 pc 00000000000f6014  /system/lib64/libandroid_runtime.so (???)
  native: #02 pc 00000000000f6330  /system/lib64/libandroid_runtime.so (???)
  native: #03 pc 0000000000746dd8  /system/framework/arm64/boot-framework.oat (Java_android_net_LocalSocketImpl_readba_1native___3BIILjava_io_FileDescriptor_2+196)
  at android.net.LocalSocketImpl.readba_native(Native method)
  at android.net.LocalSocketImpl.-wrap1(LocalSocketImpl.java:-1)
  at android.net.LocalSocketImpl$SocketInputStream.read(LocalSocketImpl.java:110)
  - locked <0x0cc91a94> (a java.lang.Object)
  at com.android.internal.telephony.RIL.readRilMessage(RIL.java:752)
  at com.android.internal.telephony.RIL.-wrap2(RIL.java:-1)
  at com.android.internal.telephony.RIL$RILReceiver.run(RIL.java:905)
  at java.lang.Thread.run(Thread.java:761)

"GsmInboundSmsHandler" prio=5 tid=14 Native
  | group="main" sCount=1 dsCount=0 obj=0x12d25e50 self=0x7a0d8e7600
  | sysTid=1682 nice=0 cgrp=default sched=0/0 handle=0x79fd52d450
  | state=S schedstat=( 3788388 3313307 17 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x79fd42b000-0x79fd42d000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1682/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"CellBroadcastHandler" prio=5 tid=15 Native
  | group="main" sCount=1 dsCount=0 obj=0x12d3c670 self=0x7a16289a00
  | sysTid=1683 nice=0 cgrp=default sched=0/0 handle=0x79fd401450
  | state=S schedstat=( 886230 557077 5 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fd2ff000-0x79fd301000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1683/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"CdmaInboundSmsHandler" prio=5 tid=16 Native
  | group="main" sCount=1 dsCount=0 obj=0x12d3c700 self=0x7a1628a400
  | sysTid=1684 nice=0 cgrp=default sched=0/0 handle=0x79fd2f5450
  | state=S schedstat=( 1634309 1076384 12 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fd1f3000-0x79fd1f5000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1684/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"CdmaServiceCategoryProgramHandler" prio=5 tid=17 Native
  | group="main" sCount=1 dsCount=0 obj=0x12d3c820 self=0x7a1628ae00
  | sysTid=1685 nice=0 cgrp=default sched=0/0 handle=0x79fd1e9450
  | state=S schedstat=( 811692 2091155 4 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x79fd0e7000-0x79fd0e9000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1685/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"GsmCellBroadcastHandler" prio=5 tid=18 Native
  | group="main" sCount=1 dsCount=0 obj=0x12d3cd30 self=0x7a10427800
  | sysTid=1686 nice=0 cgrp=default sched=0/0 handle=0x79fd0dd450
  | state=S schedstat=( 761846 1571847 4 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x79fcfdb000-0x79fcfdd000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1686/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"SSRequestHandler" prio=5 tid=13 Native
  | group="main" sCount=1 dsCount=0 obj=0x12d810d0 self=0x7a1628b800
  | sysTid=1688 nice=0 cgrp=default sched=0/0 handle=0x79fd637450
  | state=S schedstat=( 363693 76384 2 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x79fd535000-0x79fd537000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1688/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"Binder:1480_3" prio=5 tid=19 Native
  | group="main" sCount=1 dsCount=0 obj=0x12d81ee0 self=0x7a16289000
  | sysTid=1695 nice=0 cgrp=default sched=0/0 handle=0x79fcf73450
  | state=S schedstat=( 72944305 67952699 320 ) utm=6 stm=2 core=0 HZ=100
  | stack=0x79fce79000-0x79fce7b000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1695/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Binder:1480_4" prio=5 tid=20 Native
  | group="main" sCount=1 dsCount=0 obj=0x12d81f70 self=0x7a1046ec00
  | sysTid=1697 nice=0 cgrp=default sched=0/0 handle=0x79fce6f450
  | state=S schedstat=( 65597695 70297307 295 ) utm=6 stm=1 core=0 HZ=100
  | stack=0x79fcd75000-0x79fcd77000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1697/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"DcHandlerThread" prio=5 tid=21 Native
  | group="main" sCount=1 dsCount=0 obj=0x12db81f0 self=0x7a0d8aec00
  | sysTid=1722 nice=0 cgrp=default sched=0/0 handle=0x79fcd69450
  | state=S schedstat=( 728462 1956077 2 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x79fcc67000-0x79fcc69000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"Thread-2" prio=5 tid=22 Native
  | group="main" sCount=1 dsCount=0 obj=0x12db85e0 self=0x7a0d8af600
  | sysTid=1737 nice=0 cgrp=default sched=0/0 handle=0x79fcc5d450
  | state=S schedstat=( 9179769 352000 12 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x79fcb5b000-0x79fcb5d000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at com.android.internal.telephony.dataconnection.DcTracker$9.run(DcTracker.java:6366)

"Binder:1480_5" prio=5 tid=23 Native
  | group="main" sCount=1 dsCount=0 obj=0x130331f0 self=0x7a1629d000
  | sysTid=1961 nice=0 cgrp=default sched=0/0 handle=0x79fbb47450
  | state=S schedstat=( 46580155 36520539 193 ) utm=3 stm=1 core=0 HZ=100
  | stack=0x79fba4d000-0x79fba4f000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1961/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Profile Saver" daemon prio=5 tid=26 Native
  | group="system" sCount=1 dsCount=0 obj=0x1307e0d0 self=0x79fb837000
  | sysTid=2099 nice=0 cgrp=default sched=0/0 handle=0x79fb54f450
  | state=S schedstat=( 5317235 1607614 21 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x79fb455000-0x79fb457000 stackSize=1005KB
  | held mutexes=
  (no managed stack frames)

----- end 1480 -----

----- pid 1229 at 2018-09-14 13:46:57 -----
Cmd line: com.android.systemui
Build fingerprint: 'Mobilead/m80/m80:7.0/04.04.20180125/1516857511:user/dev-keys'
ABI: 'arm64'
Build type: optimized
Zygote loaded classes=4375 post zygote classes=1534
Intern table: 50074 strong; 221 weak
JNI: CheckJNI is off; globals=646 (plus 855 weak)
Libraries: /system/lib64/libandroid.so /system/lib64/libcompiler_rt.so /system/lib64/libjavacrypto.so /system/lib64/libjnigraphics.so /system/lib64/libmedia_jni.so /system/lib64/libsoundpool.so /system/lib64/libwebviewchromium_loader.so libjavacore.so libopenjdk.so (9)
Heap: 19% free, 12MB/15MB; 80143 objects
Dumping cumulative Gc timings
Start Dumping histograms for 2 iterations for partial concurrent mark sweep
ProcessMarkStack:	Sum: 36.696ms 99% C.I. 0.004ms-22.888ms Avg: 6.116ms Max: 22.916ms
UpdateAndMarkImageModUnionTable:	Sum: 13.565ms 99% C.I. 1us-4900us Avg: 423.906us Max: 5244us
MarkConcurrentRoots:	Sum: 9.076ms 99% C.I. 0.006ms-5.841ms Avg: 2.269ms Max: 5.841ms
UpdateAndMarkZygoteModUnionTable:	Sum: 6.937ms 99% C.I. 2.105ms-4.823ms Avg: 3.468ms Max: 4.832ms
SweepMallocSpace:	Sum: 5.042ms 99% C.I. 0.030ms-2.781ms Avg: 1.260ms Max: 2.781ms
ScanGrayAllocSpaceObjects:	Sum: 4.582ms 99% C.I. 0.002ms-3.789ms Avg: 1.145ms Max: 3.826ms
MarkRootsCheckpoint:	Sum: 3.420ms 99% C.I. 498us-1093us Avg: 855us Max: 1093us
ReMarkRoots:	Sum: 1.159ms 99% C.I. 517us-642us Avg: 579.500us Max: 642us
MarkAllocStackAsLive:	Sum: 811us 99% C.I. 324us-487us Avg: 405.500us Max: 487us
SweepSystemWeaks:	Sum: 635us 99% C.I. 301us-334us Avg: 317.500us Max: 334us
ImageModUnionClearCards:	Sum: 524us 99% C.I. 1us-219.999us Avg: 8.187us Max: 297us
MarkNonThreadRoots:	Sum: 357us 99% C.I. 72us-109us Avg: 89.250us Max: 109us
EnqueueFinalizerReferences:	Sum: 332us 99% C.I. 113us-219us Avg: 166us Max: 219us
ProcessReferences:	Sum: 329us 99% C.I. 130us-199us Avg: 164.500us Max: 199us
SweepLargeObjects:	Sum: 257us 99% C.I. 32us-225us Avg: 128.500us Max: 225us
FinishPhase:	Sum: 232us 99% C.I. 102us-130us Avg: 116us Max: 130us
BindBitmaps:	Sum: 230us 99% C.I. 111us-119us Avg: 115us Max: 119us
AllocSpaceClearCards:	Sum: 201us 99% C.I. 1us-70us Avg: 25.125us Max: 70us
(Paused)ScanGrayAllocSpaceObjects:	Sum: 168us 99% C.I. 2us-127us Avg: 42us Max: 127us
ProcessCards:	Sum: 117us 99% C.I. 27us-33us Avg: 29.250us Max: 33us
MarkingPhase:	Sum: 115us 99% C.I. 55us-60us Avg: 57.500us Max: 60us
(Paused)ScanGrayImageSpaceObjects:	Sum: 104us 99% C.I. 1us-24us Avg: 3.250us Max: 24us
(Paused)PausePhase:	Sum: 79us 99% C.I. 38us-41us Avg: 39.500us Max: 41us
PreCleanCards:	Sum: 75us 99% C.I. 36us-39us Avg: 37.500us Max: 39us
RevokeAllThreadLocalAllocationStacks:	Sum: 62us 99% C.I. 31us-31us Avg: 31us Max: 31us
ZygoteModUnionClearCards:	Sum: 48us 99% C.I. 10us-14us Avg: 12us Max: 14us
ReclaimPhase:	Sum: 41us 99% C.I. 18us-23us Avg: 20.500us Max: 23us
Sweep:	Sum: 26us 99% C.I. 12us-14us Avg: 13us Max: 14us
SwapBitmaps:	Sum: 24us 99% C.I. 12us-12us Avg: 12us Max: 12us
(Paused)ScanGrayZygoteSpaceObjects:	Sum: 19us 99% C.I. 9us-10us Avg: 9.500us Max: 10us
ScanGrayZygoteSpaceObjects:	Sum: 16us 99% C.I. 8us-8us Avg: 8us Max: 8us
MarkRoots:	Sum: 12us 99% C.I. 6us-6us Avg: 6us Max: 6us
(Paused)ProcessMarkStack:	Sum: 8us 99% C.I. 1us-7us Avg: 4us Max: 7us
RecursiveMark:	Sum: 6us 99% C.I. 3us-3us Avg: 3us Max: 3us
SwapStacks:	Sum: 5us 99% C.I. 2us-3us Avg: 2.500us Max: 3us
UnBindBitmaps:	Sum: 4us 99% C.I. 2us-2us Avg: 2us Max: 2us
FindDefaultSpaceBitmap:	Sum: 2us 99% C.I. 1us-1us Avg: 1us Max: 1us
Done Dumping histograms
partial concurrent mark sweep paused:	Sum: 1.851ms 99% C.I. 801us-1050us Avg: 925.500us Max: 1050us
partial concurrent mark sweep total time: 85.456ms mean time: 42.728ms
partial concurrent mark sweep freed: 31765 objects with total size 2MB
partial concurrent mark sweep throughput: 373706/s / 32MB/s
Start Dumping histograms for 3 iterations for sticky concurrent mark sweep
ScanGrayAllocSpaceObjects:	Sum: 40.692ms 99% C.I. 0.004ms-23.344ms Avg: 3.391ms Max: 24.034ms
ScanGrayImageSpaceObjects:	Sum: 20.346ms 99% C.I. 1us-5459.999us Avg: 211.937us Max: 5661us
ProcessMarkStack:	Sum: 18.472ms 99% C.I. 0.001ms-9.652ms Avg: 1.539ms Max: 9.945ms
FreeList:	Sum: 15.615ms 99% C.I. 12us-1275.500us Avg: 159.336us Max: 1284us
MarkConcurrentRoots:	Sum: 11.541ms 99% C.I. 0.007ms-5.146ms Avg: 1.923ms Max: 5.159ms
MarkRootsCheckpoint:	Sum: 7.943ms 99% C.I. 0.487ms-3.352ms Avg: 1.323ms Max: 3.395ms
SweepArray:	Sum: 5.094ms 99% C.I. 1.288ms-2.175ms Avg: 1.698ms Max: 2.175ms
ReMarkRoots:	Sum: 2ms 99% C.I. 508us-956us Avg: 666.666us Max: 956us
ScanGrayZygoteSpaceObjects:	Sum: 1.866ms 99% C.I. 9us-1559.500us Avg: 311us Max: 1560us
SweepSystemWeaks:	Sum: 1.838ms 99% C.I. 249us-1088us Avg: 612.666us Max: 1088us
EnqueueFinalizerReferences:	Sum: 1.077ms 99% C.I. 71us-646.250us Avg: 359us Max: 648us
MarkingPhase:	Sum: 1.049ms 99% C.I. 238us-524us Avg: 349.666us Max: 524us
AllocSpaceClearCards:	Sum: 932us 99% C.I. 4us-202us Avg: 77.666us Max: 202us
(Paused)ScanGrayAllocSpaceObjects:	Sum: 701us 99% C.I. 2us-536.500us Avg: 116.833us Max: 549us
MarkNonThreadRoots:	Sum: 600us 99% C.I. 68us-121us Avg: 100us Max: 121us
ImageModUnionClearCards:	Sum: 502us 99% C.I. 1us-57us Avg: 5.229us Max: 57us
ProcessCards:	Sum: 441us 99% C.I. 25us-196us Avg: 73.500us Max: 196us
BindBitmaps:	Sum: 404us 99% C.I. 110us-176us Avg: 134.666us Max: 176us
ProcessReferences:	Sum: 323us 99% C.I. 15us-165us Avg: 107.666us Max: 165us
ResetStack:	Sum: 230us 99% C.I. 48us-116us Avg: 76.666us Max: 116us
(Paused)ScanGrayImageSpaceObjects:	Sum: 155us 99% C.I. 1us-26us Avg: 3.229us Max: 26us
FinishPhase:	Sum: 152us 99% C.I. 33us-84us Avg: 50.666us Max: 84us
(Paused)PausePhase:	Sum: 143us 99% C.I. 38us-56us Avg: 47.666us Max: 56us
ZygoteModUnionClearCards:	Sum: 133us 99% C.I. 12us-53us Avg: 22.166us Max: 53us
PreCleanCards:	Sum: 122us 99% C.I. 38us-45us Avg: 40.666us Max: 45us
RevokeAllThreadLocalAllocationStacks:	Sum: 117us 99% C.I. 29us-46us Avg: 39us Max: 46us
InitializePhase:	Sum: 76us 99% C.I. 19us-30us Avg: 25.333us Max: 30us
ReclaimPhase:	Sum: 73us 99% C.I. 19us-30us Avg: 24.333us Max: 30us
(Paused)ScanGrayZygoteSpaceObjects:	Sum: 28us 99% C.I. 8us-10us Avg: 9.333us Max: 10us
SwapBitmaps:	Sum: 24us 99% C.I. 7us-9us Avg: 8us Max: 9us
ForwardSoftReferences:	Sum: 20us 99% C.I. 3us-12us Avg: 6.666us Max: 12us
(Paused)ProcessMarkStack:	Sum: 14us 99% C.I. 1us-12us Avg: 4.666us Max: 12us
UnBindBitmaps:	Sum: 10us 99% C.I. 3us-4us Avg: 3.333us Max: 4us
SwapStacks:	Sum: 9us 99% C.I. 3us-3us Avg: 3us Max: 3us
RecordFree:	Sum: 6us 99% C.I. 2us-2us Avg: 2us Max: 2us
FindDefaultSpaceBitmap:	Sum: 5us 99% C.I. 1us-3us Avg: 1.666us Max: 3us
PreSweepingGcVerification:	Sum: 3us 99% C.I. 1us-1us Avg: 1us Max: 1us
Done Dumping histograms
sticky concurrent mark sweep paused:	Sum: 3.600ms 99% C.I. 0.973ms-1.325ms Avg: 1.200ms Max: 1.325ms
sticky concurrent mark sweep total time: 132.776ms mean time: 44.258ms
sticky concurrent mark sweep freed: 95518 objects with total size 5MB
sticky concurrent mark sweep throughput: 723621/s / 45MB/s
Total time spent in GC: 218.232ms
Mean GC size throughput: 36MB/s
Mean GC object throughput: 583191 objects/s
Total number of allocations 207414
Total bytes allocated 20MB
Total bytes freed 8MB
Free memory 3MB
Free memory until GC 3MB
Free memory until OOME 115MB
Total memory 15MB
Max memory 128MB
Zygote space size 1648KB
Total mutator paused time: 5.451ms
Total time waiting for GC to complete: 20.386us
Total GC count: 5
Total GC time: 218.232ms
Total blocking GC count: 0
Total blocking GC time: 0
Histogram of GC count per 10000 ms: 0:17,1:2,2:1
Histogram of blocking GC count per 10000 ms: 0:20
Histogram of native allocation 0:2204,262144:2 bucket size 32768
Histogram of native free 0:812,262144:1 bucket size 32768
/vendor/plugin/FwkPlugin/oat/arm64/FwkPlugin.odex: speed
/vendor/plugin/FwkPlugin/oat/arm64/FwkPlugin.odex: speed
/system/priv-app/SystemUI/oat/arm64/SystemUI.odex: speed
Current JIT code cache size: 1136B
Current JIT data cache size: 2392B
Current JIT capacity: 64KB
Current number of JIT code cache entries: 4
Total number of JIT compilations: 4
Total number of JIT compilations for on stack replacement: 0
Total number of deoptimizations: 0
Total number of JIT code cache collections: 0
Memory used for stack maps: Avg: 106B Max: 288B Min: 16B
Memory used for compiled code: Avg: 244B Max: 516B Min: 12B
Memory used for profiling info: Avg: 142B Max: 656B Min: 32B
Start Dumping histograms for 4 iterations for JIT timings
Compiling:	Sum: 103.632ms 99% C.I. 1.660ms-72.432ms Avg: 25.908ms Max: 73.404ms
TrimMaps:	Sum: 189us 99% C.I. 23us-66us Avg: 47.250us Max: 66us
Done Dumping histograms
Memory used for compilation: Avg: 71KB Max: 136KB Min: 23KB

suspend all histogram:	Sum: 1.343ms 99% C.I. 37us-193us Avg: 83.937us Max: 193us
DALVIK THREADS (27):
"Signal Catcher" daemon prio=5 tid=3 Runnable
  | group="system" sCount=0 dsCount=0 obj=0x12c005e0 self=0x7a0d907000
  | sysTid=1236 nice=0 cgrp=default sched=0/0 handle=0x7a160fe450
  | state=R schedstat=( 19328461 361769 5 ) utm=1 stm=0 core=1 HZ=100
  | stack=0x7a16004000-0x7a16006000 stackSize=1005KB
  | held mutexes= "mutator lock"(shared held)
  (no managed stack frames)

"main" prio=5 tid=1 Native
  | group="main" sCount=1 dsCount=0 obj=0x762d4998 self=0x7a16c96a00
  | sysTid=1229 nice=0 cgrp=default sched=0/0 handle=0x7a1b22ca98
  | state=S schedstat=( 2462202003 1094710560 4081 ) utm=205 stm=41 core=0 HZ=100
  | stack=0x7fd06c6000-0x7fd06c8000 stackSize=8MB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.app.ActivityThread.main(ActivityThread.java:6251)
  at java.lang.reflect.Method.invoke!(Native method)
  at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1063)
  at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:924)

"Jit thread pool worker thread 0" prio=5 tid=2 Native (still starting up)
  | group="" sCount=1 dsCount=0 obj=0x0 self=0x7a1040e000
  | sysTid=1235 nice=9 cgrp=default sched=0/0 handle=0x7a161ff450
  | state=S schedstat=( 12754310 37840766 56 ) utm=0 stm=1 core=0 HZ=100
  | stack=0x7a16101000-0x7a16103000 stackSize=1021KB
  | held mutexes=
  (no managed stack frames)

"ReferenceQueueDaemon" daemon prio=5 tid=4 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c00670 self=0x7a16cc3e00
  | sysTid=1239 nice=0 cgrp=default sched=0/0 handle=0x7a16001450
  | state=S schedstat=( 2086230 1797384 9 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x7a15eff000-0x7a15f01000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0a9a64a2> (a java.lang.Class<java.lang.ref.ReferenceQueue>)
  at java.lang.Daemons$ReferenceQueueDaemon.run(Daemons.java:150)
  - locked <0x0a9a64a2> (a java.lang.Class<java.lang.ref.ReferenceQueue>)
  at java.lang.Thread.run(Thread.java:761)

"FinalizerDaemon" daemon prio=5 tid=5 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c00700 self=0x7a16cc4800
  | sysTid=1240 nice=0 cgrp=default sched=0/0 handle=0x7a10845450
  | state=S schedstat=( 12441536 2774999 12 ) utm=1 stm=0 core=2 HZ=100
  | stack=0x7a10743000-0x7a10745000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x05b26533> (a java.lang.Object)
  at java.lang.Object.wait(Object.java:407)
  at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:188)
  - locked <0x05b26533> (a java.lang.Object)
  at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:209)
  at java.lang.Daemons$FinalizerDaemon.run(Daemons.java:205)
  at java.lang.Thread.run(Thread.java:761)

"HeapTaskDaemon" daemon prio=5 tid=6 Blocked
  | group="system" sCount=1 dsCount=0 obj=0x12c00820 self=0x7a16cc5c00
  | sysTid=1243 nice=0 cgrp=default sched=0/0 handle=0x7a102fa450
  | state=S schedstat=( 168520926 62768996 201 ) utm=16 stm=0 core=0 HZ=100
  | stack=0x7a101f8000-0x7a101fa000 stackSize=1037KB
  | held mutexes=
  at dalvik.system.VMRuntime.runHeapTasks(Native method)
  - waiting to lock an unknown object
  at java.lang.Daemons$HeapTaskDaemon.run(Daemons.java:438)
  at java.lang.Thread.run(Thread.java:761)

"FinalizerWatchdogDaemon" daemon prio=5 tid=7 Waiting
  | group="system" sCount=1 dsCount=0 obj=0x12c00790 self=0x7a16cc5200
  | sysTid=1241 nice=0 cgrp=default sched=0/0 handle=0x7a103ff450
  | state=S schedstat=( 776540 8397921 9 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x7a102fd000-0x7a102ff000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x003cd0f0> (a java.lang.Daemons$FinalizerWatchdogDaemon)
  at java.lang.Daemons$FinalizerWatchdogDaemon.sleepUntilNeeded(Daemons.java:270)
  - locked <0x003cd0f0> (a java.lang.Daemons$FinalizerWatchdogDaemon)
  at java.lang.Daemons$FinalizerWatchdogDaemon.run(Daemons.java:250)
  at java.lang.Thread.run(Thread.java:761)

"Binder:1229_1" prio=5 tid=8 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c009d0 self=0x7a10417400
  | sysTid=1248 nice=0 cgrp=default sched=0/0 handle=0x79fe74c450
  | state=S schedstat=( 66722614 91469693 358 ) utm=4 stm=2 core=0 HZ=100
  | stack=0x79fe652000-0x79fe654000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1248/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Binder:1229_2" prio=5 tid=9 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c00a60 self=0x7a16cc6600
  | sysTid=1252 nice=0 cgrp=default sched=0/0 handle=0x79fe64f450
  | state=S schedstat=( 59210526 91253477 325 ) utm=2 stm=3 core=0 HZ=100
  | stack=0x79fe555000-0x79fe557000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1252/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"ConnectivityThread" prio=5 tid=10 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c77280 self=0x7a0d8e4400
  | sysTid=1320 nice=0 cgrp=default sched=0/0 handle=0x79fd38d450
  | state=S schedstat=( 382000 0 1 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x79fd28b000-0x79fd28d000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1320/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"SoundPoolThread" prio=5 tid=11 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c77790 self=0x7a0d8e4e00
  | sysTid=1351 nice=0 cgrp=default sched=0/0 handle=0x79fd18b450
  | state=S schedstat=( 43711775 76448538 631 ) utm=0 stm=4 core=2 HZ=100
  | stack=0x79fd091000-0x79fd093000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1351/stack)
  native: #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  native: #01 pc 0000000000069a8c  /system/lib64/libc.so (pthread_cond_wait+96)
  native: #02 pc 0000000000008fc8  /system/lib64/libsoundpool.so (_ZN7android15SoundPoolThread3runEv+124)
  native: #03 pc 0000000000008eb8  /system/lib64/libsoundpool.so (_ZN7android15SoundPoolThread11beginThreadEPv+8)
  native: #04 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #05 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #06 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"SoundPool" prio=5 tid=12 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c77820 self=0x7a10427800
  | sysTid=1350 nice=0 cgrp=default sched=0/0 handle=0x79fd288450
  | state=S schedstat=( 904538 1041462 4 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x79fd18e000-0x79fd190000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1350/stack)
  native: #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  native: #01 pc 0000000000069a8c  /system/lib64/libc.so (pthread_cond_wait+96)
  native: #02 pc 0000000000006264  /system/lib64/libsoundpool.so (_ZN7android9SoundPool3runEv+52)
  native: #03 pc 0000000000006220  /system/lib64/libsoundpool.so (_ZN7android9SoundPool11beginThreadEPv+8)
  native: #04 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #05 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #06 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Recents-TaskResourceLoader" prio=5 tid=14 Waiting
  | group="main" sCount=1 dsCount=0 obj=0x12ca13a0 self=0x7a1624f200
  | sysTid=1391 nice=10 cgrp=bg_non_interactive sched=0/0 handle=0x79fcd94450
  | state=S schedstat=( 1158459 4199387 8 ) utm=0 stm=0 core=3 HZ=100
  | stack=0x79fcc92000-0x79fcc94000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0b2e4669> (a com.android.systemui.recents.model.TaskResourceLoadQueue)
  at com.android.systemui.recents.model.BackgroundTaskLoader.run(RecentsTaskLoader.java:232)
  - locked <0x0b2e4669> (a com.android.systemui.recents.model.TaskResourceLoadQueue)
  at android.os.Handler.handleCallback(Handler.java:836)
  at android.os.Handler.dispatchMessage(Handler.java:103)
  at android.os.Looper.loop(Looper.java:203)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"recents.fg" prio=5 tid=15 Native
  | group="main" sCount=1 dsCount=0 obj=0x12ca15e0 self=0x7a1624fc00
  | sysTid=1392 nice=0 cgrp=default sched=0/0 handle=0x79fcc8f450
  | state=S schedstat=( 487461 415308 1 ) utm=0 stm=0 core=1 HZ=100
  | stack=0x79fcb8d000-0x79fcb8f000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1392/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"VolumeDialogController" prio=5 tid=16 Native
  | group="main" sCount=1 dsCount=0 obj=0x12d19550 self=0x7a16298000
  | sysTid=1431 nice=0 cgrp=default sched=0/0 handle=0x79fcb8a450
  | state=S schedstat=( 13223466 41406993 104 ) utm=1 stm=0 core=0 HZ=100
  | stack=0x79fca88000-0x79fca8a000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1431/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"pool-1-thread-1" prio=5 tid=17 Waiting
  | group="main" sCount=1 dsCount=0 obj=0x12df2ca0 self=0x7a1629a800
  | sysTid=1479 nice=0 cgrp=default sched=0/0 handle=0x79fca85450
  | state=S schedstat=( 4433774 1976002 37 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x79fc983000-0x79fc985000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x0a5e28ee> (a java.lang.Object)
  at java.lang.Thread.parkFor$(Thread.java:2127)
  - locked <0x0a5e28ee> (a java.lang.Object)
  at sun.misc.Unsafe.park(Unsafe.java:325)
  at java.util.concurrent.locks.LockSupport.park(LockSupport.java:161)
  at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2035)
  at java.util.concurrent.LinkedBlockingQueue.take(LinkedBlockingQueue.java:413)
  at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1058)
  at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1118)
  at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:607)
  at java.lang.Thread.run(Thread.java:761)

"Binder:1229_3" prio=5 tid=18 Native
  | group="main" sCount=1 dsCount=0 obj=0x12df2e50 self=0x7a16299e00
  | sysTid=1540 nice=0 cgrp=default sched=0/0 handle=0x79fc840450
  | state=S schedstat=( 56840529 78880548 313 ) utm=4 stm=2 core=0 HZ=100
  | stack=0x79fc746000-0x79fc748000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1540/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Binder:1229_4" prio=5 tid=19 Native
  | group="main" sCount=1 dsCount=0 obj=0x12df2ee0 self=0x7a10490000
  | sysTid=1541 nice=0 cgrp=default sched=0/0 handle=0x79fc743450
  | state=S schedstat=( 54713305 88200699 333 ) utm=0 stm=5 core=0 HZ=100
  | stack=0x79fc649000-0x79fc64b000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1541/stack)
  native: #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  native: #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  native: #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  native: #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  native: #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  native: #05 pc 0000000000072a94  /system/lib64/libbinder.so (???)
  native: #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #07 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #08 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #09 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"RenderThread" prio=5 tid=13 Native
  | group="main" sCount=1 dsCount=0 obj=0x12e3f4c0 self=0x7a1044bc00
  | sysTid=1559 nice=-4 cgrp=default sched=0/0 handle=0x79fce91450
  | state=S schedstat=( 247414138 66220614 766 ) utm=17 stm=7 core=0 HZ=100
  | stack=0x79fcd97000-0x79fcd99000 stackSize=1005KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1559/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 000000000003c764  /system/lib64/libhwui.so (_ZN7android10uirenderer12renderthread12RenderThread10threadLoopEv+624)
  native: #05 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  native: #06 pc 00000000000a1398  /system/lib64/libandroid_runtime.so (_ZN7android14AndroidRuntime15javaThreadShellEPv+128)
  native: #07 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  native: #08 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)
  (no managed stack frames)

"Keyboard" prio=5 tid=20 Native
  | group="main" sCount=1 dsCount=0 obj=0x12e7f310 self=0x7a1632ba00
  | sysTid=1573 nice=10 cgrp=bg_non_interactive sched=0/0 handle=0x79fc402450
  | state=S schedstat=( 855923 2488921 4 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fc300000-0x79fc302000 stackSize=1037KB
  | held mutexes=
  kernel: (couldn't read /proc/self/task/1573/stack)
  native: #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  native: #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  native: #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  native: #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  native: #04 pc 00000000000f2600  /system/lib64/libandroid_runtime.so (_ZN7android18NativeMessageQueue8pollOnceEP7_JNIEnvP8_jobjecti+48)
  native: #05 pc 00000000008ce850  /system/framework/arm64/boot-framework.oat (Java_android_os_MessageQueue_nativePollOnce__JI+140)
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"pool-2-thread-1" prio=5 tid=21 Waiting
  | group="main" sCount=1 dsCount=0 obj=0x12e7fe50 self=0x7a1632c400
  | sysTid=1581 nice=0 cgrp=default sched=0/0 handle=0x79fc2fd450
  | state=S schedstat=( 614462 1194231 2 ) utm=0 stm=0 core=2 HZ=100
  | stack=0x79fc1fb000-0x79fc1fd000 stackSize=1037KB
  | held mutexes=
  at java.lang.Object.wait!(Native method)
  - waiting on <0x00a4218f> (a java.lang.Object)
  at java.lang.Thread.parkFor$(Thread.java:2127)
  - locked <0x00a4218f> (a java.lang.Object)
  at sun.misc.Unsafe.park(Unsafe.java:325)
  at java.util.concurrent.locks.LockSupport.park(LockSupport.java:161)
  at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2035)
  at java.util.concurrent.LinkedBlockingQueue.take(LinkedBlockingQueue.java:413)
  at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1058)
  at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1118)
  at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:607)
  at java.lang.Thread.run(Thread.java:761)

"PhoneStatusBar" prio=5 tid=23 Native
  | group="main" sCount=1 dsCount=0 obj=0x12c41430 self=0x7a1632d800
  | sysTid=1690 nice=10 cgrp=bg_non_interactive sched=0/0 handle=0x79fb985450
  | state=S schedstat=( 101568623 467257293 360 ) utm=10 stm=0 core=0 HZ=100
  | stack=0x79fb883000-0x79fb885000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"ConnectivityManager" prio=5 tid=24 Native
  | group="main" sCount=1 dsCount=0 obj=0x12ca1790 self=0x7a16314600
  | sysTid=1694 nice=0 cgrp=default sched=0/0 handle=0x79fb880450
  | state=S schedstat=( 2756616 7177768 9 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fb77e000-0x79fb780000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"FlashlightController" prio=5 tid=25 Native
  | group="main" sCount=1 dsCount=0 obj=0x12cffa60 self=0x7a1632e200
  | sysTid=1827 nice=10 cgrp=bg_non_interactive sched=0/0 handle=0x79fb779450
  | state=S schedstat=( 542692 1865077 2 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79fb677000-0x79fb679000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"QSTileHost" prio=5 tid=27 Native
  | group="main" sCount=1 dsCount=0 obj=0x12dc58b0 self=0x7a16350800
  | sysTid=1910 nice=10 cgrp=bg_non_interactive sched=0/0 handle=0x79fb541450
  | state=S schedstat=( 52697389 207797310 498 ) utm=5 stm=0 core=3 HZ=100
  | stack=0x79fb43f000-0x79fb441000 stackSize=1037KB
  | held mutexes=
  at android.os.MessageQueue.nativePollOnce(Native method)
  at android.os.MessageQueue.next(MessageQueue.java:328)
  at android.os.Looper.loop(Looper.java:148)
  at android.os.HandlerThread.run(HandlerThread.java:61)

"hwuiTask1" prio=5 tid=29 Native
  | group="main" sCount=1 dsCount=0 obj=0x12e7f430 self=0x79f93f3c00
  | sysTid=1956 nice=-2 cgrp=default sched=0/0 handle=0x79f91ff450
  | state=S schedstat=( 6043231 10792154 44 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79f9105000-0x79f9107000 stackSize=1005KB
  | held mutexes=
  (no managed stack frames)

"hwuiTask2" prio=5 tid=30 Native
  | group="main" sCount=1 dsCount=0 obj=0x12e7f280 self=0x7a104f4800
  | sysTid=1957 nice=-2 cgrp=default sched=0/0 handle=0x79f9102450
  | state=S schedstat=( 1986767 3999386 15 ) utm=0 stm=0 core=0 HZ=100
  | stack=0x79f9008000-0x79f900a000 stackSize=1005KB
  | held mutexes=
  (no managed stack frames)

"FileSourceProxy" prio=5 (not attached)
  | sysTid=1352 nice=0 cgrp=default
  | state=S schedstat=( 429616 525230 4 ) utm=0 stm=0 core=2 HZ=100
  kernel: (couldn't read /proc/self/task/1352/stack)

"mali-mem-purge" prio=5 (not attached)
  | sysTid=1933 nice=-4 cgrp=default
  | state=S schedstat=( 33029300 20636769 240 ) utm=1 stm=2 core=0 HZ=100
  kernel: (couldn't read /proc/self/task/1933/stack)

"mali-event-hnd" prio=5 (not attached)
  | sysTid=1934 nice=-4 cgrp=default
  | state=S schedstat=( 37475310 24227460 250 ) utm=0 stm=3 core=0 HZ=100
  kernel: (couldn't read /proc/self/task/1934/stack)

"mali-utility-wo" prio=5 (not attached)
  | sysTid=1935 nice=-4 cgrp=default
  | state=S schedstat=( 86308 14384 3 ) utm=0 stm=0 core=1 HZ=100
  kernel: (couldn't read /proc/self/task/1935/stack)

"mali-utility-wo" prio=5 (not attached)
  | sysTid=1937 nice=-4 cgrp=default
  | state=S schedstat=( 117614 876770 3 ) utm=0 stm=0 core=2 HZ=100
  kernel: (couldn't read /proc/self/task/1937/stack)

"mali-utility-wo" prio=5 (not attached)
  | sysTid=1938 nice=-4 cgrp=default
  | state=S schedstat=( 114230 1000154 6 ) utm=0 stm=0 core=2 HZ=100
  kernel: (couldn't read /proc/self/task/1938/stack)

"mali-utility-wo" prio=5 (not attached)
  | sysTid=1939 nice=-4 cgrp=default
  | state=S schedstat=( 1639231 3531308 8 ) utm=0 stm=0 core=0 HZ=100
  kernel: (couldn't read /proc/self/task/1939/stack)

"mali-renderer" prio=5 (not attached)
  | sysTid=1940 nice=-4 cgrp=default
  | state=S schedstat=( 91804619 49023007 460 ) utm=2 stm=7 core=0 HZ=100
  kernel: (couldn't read /proc/self/task/1940/stack)

"mali-hist-dump" prio=5 (not attached)
  | sysTid=1941 nice=-4 cgrp=default
  | state=S schedstat=( 55556097 9345841 225 ) utm=5 stm=0 core=0 HZ=100
  kernel: (couldn't read /proc/self/task/1941/stack)

"ged-swd" prio=5 (not attached)
  | sysTid=1942 nice=-4 cgrp=default
  | state=S schedstat=( 8741607 23666850 197 ) utm=0 stm=0 core=0 HZ=100
  kernel: (couldn't read /proc/self/task/1942/stack)

"AudioTrack" prio=5 (not attached)
  | sysTid=2066 nice=-16 cgrp=default
  | state=S schedstat=( 404615 38538 6 ) utm=0 stm=0 core=1 HZ=100
  kernel: (couldn't read /proc/self/task/2066/stack)

----- end 1229 -----

----- pid 282 at 2018-09-14 13:46:58 -----
Cmd line: /system/bin/surfaceflinger
ABI: 'arm64'
 
"surfaceflinger" sysTid=282
  #00 pc 000000000006c794  /system/lib64/libc.so (__epoll_pwait+8)
  #01 pc 000000000001e290  /system/lib64/libc.so (epoll_pwait+64)
  #02 pc 0000000000018010  /system/lib64/libutils.so (_ZN7android6Looper9pollInnerEi+156)
  #03 pc 0000000000017eb4  /system/lib64/libutils.so (_ZN7android6Looper8pollOnceEiPiS1_PPv+60)
  #04 pc 0000000000033450  /system/lib64/libsurfaceflinger.so
  #05 pc 00000000000425ac  /system/lib64/libsurfaceflinger.so (_ZN7android14SurfaceFlinger3runEv+24)
  #06 pc 0000000000001394  /system/bin/surfaceflinger
  #07 pc 000000000001a594  /system/lib64/libc.so (__libc_init+88)
  #08 pc 0000000000001100  /system/bin/surfaceflinger

------------------------

"Binder:282_1" sysTid=293
  #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  #05 pc 0000000000072a94  /system/lib64/libbinder.so
  #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  #07 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  #08 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)

------------------------

"DispSync" sysTid=294
  #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  #01 pc 0000000000069a8c  /system/lib64/libc.so (pthread_cond_wait+96)
  #02 pc 0000000000027040  /system/lib64/libsurfaceflinger.so
  #03 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  #04 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  #05 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)

------------------------

"Binder:282_2" sysTid=295
  #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  #05 pc 0000000000072a94  /system/lib64/libbinder.so
  #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  #07 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  #08 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)

------------------------

"SWWatchDog" sysTid=296
  #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  #01 pc 0000000000069b70  /system/lib64/libc.so (pthread_cond_timedwait+156)
  #02 pc 000000000000812c  /system/vendor/lib64/libui_ext.so
  #03 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  #04 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  #05 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)

------------------------

"mali-mem-purge" sysTid=298
  #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  #01 pc 000000000002445c  /system/lib64/libc.so (sem_wait+100)
  #02 pc 000000000057b62c  /system/vendor/lib64/egl/libGLES_mali.so

------------------------

"mali-event-hnd" sysTid=299
  #00 pc 000000000006d288  /system/lib64/libc.so (read+4)
  #01 pc 0000000000579a3c  /system/vendor/lib64/egl/libGLES_mali.so

------------------------

"mali-utility-wo" sysTid=300
  #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  #01 pc 000000000002445c  /system/lib64/libc.so (sem_wait+100)
  #02 pc 000000000057b840  /system/vendor/lib64/egl/libGLES_mali.so

------------------------

"mali-utility-wo" sysTid=301
  #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  #01 pc 000000000002445c  /system/lib64/libc.so (sem_wait+100)
  #02 pc 000000000057b840  /system/vendor/lib64/egl/libGLES_mali.so

------------------------

"mali-utility-wo" sysTid=302
  #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  #01 pc 000000000002445c  /system/lib64/libc.so (sem_wait+100)
  #02 pc 000000000057b840  /system/vendor/lib64/egl/libGLES_mali.so

------------------------

"mali-utility-wo" sysTid=303
  #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  #01 pc 000000000002445c  /system/lib64/libc.so (sem_wait+100)
  #02 pc 000000000057b840  /system/vendor/lib64/egl/libGLES_mali.so

------------------------

"mali-renderer" sysTid=304
  #00 pc 000000000006c8b0  /system/lib64/libc.so (__ppoll+4)
  #01 pc 0000000000023204  /system/lib64/libc.so (poll+92)
  #02 pc 000000000057ac80  /system/vendor/lib64/egl/libGLES_mali.so

------------------------

"mali-hist-dump" sysTid=305
  #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  #01 pc 000000000002445c  /system/lib64/libc.so (sem_wait+100)
  #02 pc 000000000052c920  /system/vendor/lib64/egl/libGLES_mali.so

------------------------

"ged-swd" sysTid=306
  #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  #01 pc 0000000000069a8c  /system/lib64/libc.so (pthread_cond_wait+96)
  #02 pc 0000000000005c38  /system/vendor/lib64/libged.so (ged_cond_lock+28)
  #03 pc 0000000000005d18  /system/vendor/lib64/libged.so
  #04 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  #05 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)

------------------------

"UEventThreadHWC" sysTid=307
  #00 pc 000000000006c8b0  /system/lib64/libc.so (__ppoll+4)
  #01 pc 0000000000023204  /system/lib64/libc.so (poll+92)
  #02 pc 0000000000029900  /system/vendor/lib64/hw/hwcomposer.mt6735.so (_ZN12UEventThread10threadLoopEv+68)
  #03 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  #04 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  #05 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)

------------------------

"Dispatcher_0" sysTid=308
  #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  #01 pc 000000000002445c  /system/lib64/libc.so (sem_wait+100)
  #02 pc 0000000000020b74  /system/vendor/lib64/hw/hwcomposer.mt6735.so (_ZN14DispatchThread10threadLoopEv+56)
  #03 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  #04 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  #05 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)

------------------------

"UICompThread_0" sysTid=309
  #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  #01 pc 000000000002445c  /system/lib64/libc.so (sem_wait+100)
  #02 pc 0000000000023eec  /system/vendor/lib64/hw/hwcomposer.mt6735.so (_ZN17ComposeThreadBase10threadLoopEv+20)
  #03 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  #04 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  #05 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)

------------------------

"MMCompThread_0" sysTid=310
  #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  #01 pc 000000000002445c  /system/lib64/libc.so (sem_wait+100)
  #02 pc 0000000000023eec  /system/vendor/lib64/hw/hwcomposer.mt6735.so (_ZN17ComposeThreadBase10threadLoopEv+20)
  #03 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  #04 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  #05 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)

------------------------

"VSyncThread_0" sysTid=311
  #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  #01 pc 0000000000069a8c  /system/lib64/libc.so (pthread_cond_wait+96)
  #02 pc 0000000000028eb0  /system/vendor/lib64/hw/hwcomposer.mt6735.so (_ZN11VSyncThread10threadLoopEv+92)
  #03 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  #04 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  #05 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)

------------------------

"POSIX timer 0" sysTid=312
  #00 pc 000000000006c974  /system/lib64/libc.so (__rt_sigtimedwait+8)
  #01 pc 0000000000023864  /system/lib64/libc.so (_ZL20__timer_thread_startPv+96)
  #02 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  #03 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)

------------------------

"EventThread" sysTid=313
  #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  #01 pc 0000000000069a8c  /system/lib64/libc.so (pthread_cond_wait+96)
  #02 pc 0000000000028c70  /system/lib64/libsurfaceflinger.so
  #03 pc 00000000000287b4  /system/lib64/libsurfaceflinger.so
  #04 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  #05 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  #06 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)

------------------------

"POSIX timer 1" sysTid=314
  #00 pc 000000000006c974  /system/lib64/libc.so (__rt_sigtimedwait+8)
  #01 pc 0000000000023864  /system/lib64/libc.so (_ZL20__timer_thread_startPv+96)
  #02 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  #03 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)

------------------------

"EventThread" sysTid=315
  #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  #01 pc 0000000000069a8c  /system/lib64/libc.so (pthread_cond_wait+96)
  #02 pc 0000000000028c70  /system/lib64/libsurfaceflinger.so
  #03 pc 00000000000287b4  /system/lib64/libsurfaceflinger.so
  #04 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  #05 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  #06 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)

------------------------

"EventControl" sysTid=316
  #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  #01 pc 0000000000069a8c  /system/lib64/libc.so (pthread_cond_wait+96)
  #02 pc 0000000000027e94  /system/lib64/libsurfaceflinger.so
  #03 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  #04 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  #05 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)

------------------------

"DispSync" sysTid=318
  #00 pc 000000000001bcec  /system/lib64/libc.so (syscall+28)
  #01 pc 0000000000069a8c  /system/lib64/libc.so (pthread_cond_wait+96)
  #02 pc 0000000000005258  /system/vendor/lib64/libged.so (_Z16ged_vsync_workerPv+176)
  #03 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  #04 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)

------------------------

"Binder:282_3" sysTid=333
  #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  #05 pc 0000000000072a94  /system/lib64/libbinder.so
  #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  #07 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  #08 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)

------------------------

"Binder:282_4" sysTid=1175
  #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  #05 pc 0000000000072a94  /system/lib64/libbinder.so
  #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  #07 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  #08 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)

------------------------

"Binder:282_5" sysTid=1611
  #00 pc 000000000006c880  /system/lib64/libc.so (__ioctl+4)
  #01 pc 000000000001fba0  /system/lib64/libc.so (ioctl+140)
  #02 pc 0000000000055628  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+264)
  #03 pc 000000000005578c  /system/lib64/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+24)
  #04 pc 0000000000055ec4  /system/lib64/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+72)
  #05 pc 0000000000072a94  /system/lib64/libbinder.so
  #06 pc 000000000001245c  /system/lib64/libutils.so (_ZN7android6Thread11_threadLoopEPv+272)
  #07 pc 000000000006a35c  /system/lib64/libc.so (_ZL15__pthread_startPv+208)
  #08 pc 000000000001db68  /system/lib64/libc.so (__start_thread+16)

----- end 282 -----
----- pid 456 at 2018-09-14 13:46:58 -----
Cmd line: /system/bin/mediaserver
ABI: 'arm'
 
"mediaserver" sysTid=456
  #00 pc 00049588  /system/lib/libc.so (__ioctl+8)
  #01 pc 0001aa3f  /system/lib/libc.so (ioctl+38)
  #02 pc 0003ccd7  /system/lib/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+202)
  #03 pc 0003cded  /system/lib/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+8)
  #04 pc 0003d36b  /system/lib/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+46)
  #05 pc 00001007  /system/bin/mediaserver
  #06 pc 00016c6d  /system/lib/libc.so (__libc_init+48)
  #07 pc 00000da8  /system/bin/mediaserver

------------------------

"Binder:456_1" sysTid=572
  #00 pc 00049588  /system/lib/libc.so (__ioctl+8)
  #01 pc 0001aa3f  /system/lib/libc.so (ioctl+38)
  #02 pc 0003ccd7  /system/lib/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+202)
  #03 pc 0003cded  /system/lib/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+8)
  #04 pc 0003d36b  /system/lib/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+46)
  #05 pc 0004f99b  /system/lib/libbinder.so
  #06 pc 0000e355  /system/lib/libutils.so (_ZN7android6Thread11_threadLoopEPv+140)
  #07 pc 00047fd3  /system/lib/libc.so (_ZL15__pthread_startPv+22)
  #08 pc 00019a3d  /system/lib/libc.so (__start_thread+6)

------------------------

"Binder:456_2" sysTid=1266
  #00 pc 00049588  /system/lib/libc.so (__ioctl+8)
  #01 pc 0001aa3f  /system/lib/libc.so (ioctl+38)
  #02 pc 0003ccd7  /system/lib/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+202)
  #03 pc 0003cded  /system/lib/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+8)
  #04 pc 0003d36b  /system/lib/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+46)
  #05 pc 0004f99b  /system/lib/libbinder.so
  #06 pc 0000e355  /system/lib/libutils.so (_ZN7android6Thread11_threadLoopEPv+140)
  #07 pc 00047fd3  /system/lib/libc.so (_ZL15__pthread_startPv+22)
  #08 pc 00019a3d  /system/lib/libc.so (__start_thread+6)

------------------------

"Binder:456_3" sysTid=1371
  #00 pc 00049588  /system/lib/libc.so (__ioctl+8)
  #01 pc 0001aa3f  /system/lib/libc.so (ioctl+38)
  #02 pc 0003ccd7  /system/lib/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+202)
  #03 pc 0003cded  /system/lib/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+8)
  #04 pc 0003d36b  /system/lib/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+46)
  #05 pc 0004f99b  /system/lib/libbinder.so
  #06 pc 0000e355  /system/lib/libutils.so (_ZN7android6Thread11_threadLoopEPv+140)
  #07 pc 00047fd3  /system/lib/libc.so (_ZL15__pthread_startPv+22)
  #08 pc 00019a3d  /system/lib/libc.so (__start_thread+6)

------------------------

"Binder:456_4" sysTid=1393
  #00 pc 00049588  /system/lib/libc.so (__ioctl+8)
  #01 pc 0001aa3f  /system/lib/libc.so (ioctl+38)
  #02 pc 0003ccd7  /system/lib/libbinder.so (_ZN7android14IPCThreadState14talkWithDriverEb+202)
  #03 pc 0003cded  /system/lib/libbinder.so (_ZN7android14IPCThreadState20getAndExecuteCommandEv+8)
  #04 pc 0003d36b  /system/lib/libbinder.so (_ZN7android14IPCThreadState14joinThreadPoolEb+46)
  #05 pc 0004f99b  /system/lib/libbinder.so
  #06 pc 0000e355  /system/lib/libutils.so (_ZN7android6Thread11_threadLoopEPv+140)
  #07 pc 00047fd3  /system/lib/libc.so (_ZL15__pthread_startPv+22)
  #08 pc 00019a3d  /system/lib/libc.so (__start_thread+6)

------------------------

"FileSourceProxy" sysTid=1732
  #00 pc 00017424  /system/lib/libc.so (syscall+28)
  #01 pc 00047b5d  /system/lib/libc.so (_ZL24__pthread_cond_timedwaitP23pthread_cond_internal_tP15pthread_mutex_tbPK8timespec+102)
  #02 pc 0012192b  /system/lib/libstagefright.so (_ZN7android15FileSourceProxy4loopEv+146)
  #03 pc 0000e3d1  /system/lib/libutils.so (_ZN7android6Thread11_threadLoopEPv+264)
  #04 pc 00047fd3  /system/lib/libc.so (_ZL15__pthread_startPv+22)
  #05 pc 00019a3d  /system/lib/libc.so (__start_thread+6)

----- end 456 -----
