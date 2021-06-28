package com.xin.common.utils.http;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: gxj
 */
public class QPSCalculator {


    private RollingNumber rollingNumber;

    public QPSCalculator() {
        this.rollingNumber = new RollingNumber();
    }


    public void pass() {
        rollingNumber.record();
    }


    private static final class RollingNumber {
        /**
         * 槽位的数量
         */
        private int sizeOfBuckets;
        /**
         * 时间片，单位毫秒
         */
        private int unitOfTimeSlice;
        /**
         * 用于判断是否可跳过锁争抢
         */
        private int timeSliceUsedToCheckIfPossibleToBypass;
        /**
         * 槽位
         */
        private Bucket[] buckets;
        /**
         * 目标槽位的位置
         */
        private volatile Integer targetBucketPosition;
        /**
         * 接近目标槽位最新更新时间的时间
         */
        private volatile Long latestPassedTimeCloseToTargetBucket;
        /**
         * 进入下一个槽位时使用的锁
         */
        private ReentrantLock enterNextBucketLock;
        /**
         * 默认60个槽位，槽位的时间片为1000毫秒
         */
        public RollingNumber() {
            this(60, 1000);
        }
        /**
         * 初始化Bucket数量与每个Bucket的时间片等
         *
         * @param sizeOfBuckets
         * @param unitOfTimeSlice
         */
        public RollingNumber(int sizeOfBuckets, int unitOfTimeSlice) {
            this.latestPassedTimeCloseToTargetBucket = System.currentTimeMillis() - (2 * unitOfTimeSlice);
            this.targetBucketPosition = null;
            this.sizeOfBuckets = sizeOfBuckets;
            this.unitOfTimeSlice = unitOfTimeSlice;
            this.enterNextBucketLock = new ReentrantLock();
            this.buckets = new Bucket[sizeOfBuckets];
            this.timeSliceUsedToCheckIfPossibleToBypass = 3 * unitOfTimeSlice;
            for (int i = 0; i < sizeOfBuckets; i++) {
                this.buckets[i] = new Bucket();
            }
        }


        private void record() {
            long passTime = System.currentTimeMillis();
            if (targetBucketPosition == null) {
                targetBucketPosition = (int) (passTime / unitOfTimeSlice) % sizeOfBuckets;
            }
            Bucket currentBucket = buckets[targetBucketPosition];
            if (passTime - latestPassedTimeCloseToTargetBucket >= unitOfTimeSlice) {
                if (enterNextBucketLock.isLocked() && (passTime - latestPassedTimeCloseToTargetBucket) < timeSliceUsedToCheckIfPossibleToBypass) {
                } else {
                    enterNextBucketLock.lock();
                    try {
                        if (passTime - latestPassedTimeCloseToTargetBucket >= unitOfTimeSlice) {
                            int nextTargetBucketPosition = (int) (passTime / unitOfTimeSlice) % sizeOfBuckets;
                            Bucket nextBucket = buckets[nextTargetBucketPosition];
                            if (nextBucket.equals(currentBucket)) {
                                if (passTime - latestPassedTimeCloseToTargetBucket >= unitOfTimeSlice) {
                                    latestPassedTimeCloseToTargetBucket = passTime;
                                }
                            } else {
                                nextBucket.reset(passTime);
                                targetBucketPosition = nextTargetBucketPosition;
                                latestPassedTimeCloseToTargetBucket = passTime;
                            }
                            nextBucket.pass();
                            return;
                        } else {
                            currentBucket = buckets[targetBucketPosition];
                        }
                    } finally {
                        enterNextBucketLock.unlock();
                    }
                }
            }
            currentBucket.pass();
        }

        public Bucket[] getBuckets() {
            return buckets;
        }
    }


    private static class Bucket implements Serializable {

        private static final long serialVersionUID = -9085720164508215774L;

        private Long latestPassedTime;

        private LongAdder longAdder;

        public Bucket() {
            this.latestPassedTime = System.currentTimeMillis();
            this.longAdder = new LongAdder();
        }


        public void pass() {
            longAdder.add(1);
        }

        public long countTotalPassed() {
            return longAdder.sum();
        }

        public long getLatestPassedTime() {
            return latestPassedTime;
        }

        public void reset(long latestPassedTime) {
            this.longAdder.reset();
            this.latestPassedTime = latestPassedTime;
        }
    }



    public static void main(String[] args) {
        try {
            final QPSCalculator qpsCalculator = new QPSCalculator();
            int threadNum = 4;
            CountDownLatch countDownLatch = new CountDownLatch(threadNum);
            List<Thread> threadList = new ArrayList<>();
            for (int i = 0; i < threadNum; i++) {
                threadList.add(new Thread(() -> {
                    for (int i1 = 0; i1 < 50000000; i1++) {
                        qpsCalculator.pass();
                    }
                    countDownLatch.countDown();
                }));
            }

            long startTime = System.currentTimeMillis();
            for (Thread thread : threadList) {
                thread.start();
            }
            countDownLatch.await();
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.print("totalMilliseconds:  " + totalTime);
            TimeUnit.SECONDS.sleep(1000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
