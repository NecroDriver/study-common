package com.xin.common.utils.qps;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * QpsTest
 *
 * @author mfh 2021/6/28 17:08
 * @version 1.0.0
 **/
public class QpsTest {

    public static void main(String[] args) {
        RollingNumber rollingNumber = new RollingNumber();
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threadList.add(new Thread(() -> {
                for (int j = 0; j < 5000000; j++) {
                    rollingNumber.count();
                    try {
                        TimeUnit.MILLISECONDS.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }
        for (Thread thread : threadList) {
            thread.start();
        }
//        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
//        executorService.scheduleWithFixedDelay(() -> {
//            System.out.println(rollingNumber.sum());
//        }, 0, 1, TimeUnit.SECONDS);
    }
}
