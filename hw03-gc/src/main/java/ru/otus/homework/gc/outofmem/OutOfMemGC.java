package ru.otus.homework.gc.outofmem;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        int stepsBeforeSleep = 1000_000;
        int sleepTime = 100;
        int shortTimeArraySize = 20;
        fillMemory(stepsBeforeSleep, sleepTime, shortTimeArraySize);
    }

    private static void fillMemory(int stepsBeforeSleep, int sleepTime, int shortTimeArraySize) throws InterruptedException {
        List<MemoryFillObject> immortalList = new ArrayList<>();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss:SSS");
        System.out.println( LocalTime.now().format(timeFormat) + " > Start");
        long beginTime = System.currentTimeMillis();

        while (true) {
            List<MemoryFillObject> tempList = new ArrayList<>();
            for (int i = 0; i < stepsBeforeSleep; i++) {
                immortalList.add(new MemoryFillObject(Integer.valueOf(i)));
                Object[] shortTimeArray = new Object[shortTimeArraySize];
                for (int j = 0; j < shortTimeArraySize; j++) {
                    shortTimeArray[j] = new MemoryFillObject(j);
                }
            }
            System.out.println((System.currentTimeMillis() - beginTime) / 1000 + "s > " + immortalList.size());
            Thread.sleep(sleepTime);
        }
    }
}
