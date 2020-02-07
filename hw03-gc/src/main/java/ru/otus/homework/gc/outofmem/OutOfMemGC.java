package ru.otus.homework.gc.outofmem;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/*О формате логов
        http://openjdk.java.net/jeps/158


        -Xms512m
        -Xmx512m
        -Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
        -XX:+HeapDumpOnOutOfMemoryError
        -XX:HeapDumpPath=./logs/dump
        -XX:+UseG1GC
        */
/*
Serial Collector -XX:+UseSerialGC
        Parallel Collector -XX:+UseParallelGC
        CMS -XX:+UseConcMarkSweepGC
        G1 -XX:+UseG1GC
        ZGC -XX:+UnlockExperimental
        VMOptions -XX:+UseZGC
        */
/*
1)
    default, time: 83 sec (82 without Label_1)
2)
    -XX:MaxGCPauseMillis=100000, time: 82 sec //Sets a target for the maximum GC pause time.
3)
    -XX:MaxGCPauseMillis=10, time: 91 sec
*/
public class OutOfMemGC {
    public static void main(String[] args) throws InterruptedException {
        int stepsBeforeSleep = 5 * 1000 * 1000;
        int sleepTime = 200;
        fillMemory(stepsBeforeSleep, sleepTime);
    }

    private static void fillMemory(int stepsBeforeSleep, int sleepTime) throws InterruptedException {
        List<MemoryFillObject> list = new ArrayList<>();
        long count = 0;
        System.out.println("Start "+ LocalTime.now());
        while (true) {
            for (int i = 0; i < stepsBeforeSleep; i++) {
                list.add(new MemoryFillObject(Integer.valueOf(i)));
                if(i%100000==0) System.out.println(i+" "+LocalTime.now());
            }
            System.out.println(1);
            for (int i = 0; i < stepsBeforeSleep/2; i++) {
                list.remove(list.size()-i-1);
                if(i%100000==0) System.out.println(i+" "+LocalTime.now());
            }
            System.out.println(2);
            count++;
            System.out.println(count+" > "+LocalTime.now());
            Thread.sleep(sleepTime);
        }
    }
}
