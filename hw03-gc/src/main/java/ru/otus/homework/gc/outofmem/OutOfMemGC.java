package ru.otus.homework.gc.outofmem;

import java.io.*;
import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.List;

public class OutOfMemGC {
    private static final int BIG_LOOPS = 10000;
    private static final int SHORT_LOOPS = 10;
    private static final int SLEEP_TIME = 50;
    private static PrintStream writer;
    private static final List<MemoryFillObject> immortalList = new ArrayList<>(BIG_LOOPS);

    public static void main(String[] args) throws Exception {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        String gcName = gcbeans.get(0).getName();
        String fileName = "./logs/" + gcName + ".txt";
        try (PrintStream logWriter = new PrintStream(new FileOutputStream(fileName))) {
            writer = logWriter;
            fillMemory();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void fillMemory() throws InterruptedException {
        long beginTime = System.currentTimeMillis();
        long count = 0;
        writeLog("time", "immortalList.size()", "MemoryFillObject.getQuantity()");
        while (true) {
            for (int i = 0; i < BIG_LOOPS; i++) {
                Object[] shortTimeArray = new Object[SHORT_LOOPS];
                immortalList.add(new MemoryFillObject(new String(" ")));
                for (int j = 0; j < SHORT_LOOPS; j++) {
                    shortTimeArray[j] = new MemoryFillObject();
                }
            }
            System.out.println((System.currentTimeMillis() - beginTime) / 1000 + " s > " + count);
            count++;
            writeLog((System.currentTimeMillis() - beginTime), immortalList.size(), MemoryFillObject.getQuantity());
            Thread.sleep(SLEEP_TIME);
        }
    }

    private static void writeLog(Object... objects) {
        if (writer != null) {
            StringBuilder resultStr = new StringBuilder();
            for (Object o : objects) {
                resultStr.append(o.toString()).append(";");
            }
            writer.println(resultStr.toString());
        }
    }
}
