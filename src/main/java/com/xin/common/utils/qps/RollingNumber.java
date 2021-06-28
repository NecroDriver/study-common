package com.xin.common.utils.qps;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * RollingNumber 轮盘 默认60s一圈，每秒里有一个桶子
 *
 * @author mfh 2021/6/28 16:22
 * @version 1.0.0
 **/
public class RollingNumber {

    /**
     * 槽位
     */
    private final Bucket[] buckets;
    /**
     * 槽位数量
     */
    private final int bucketSize;
    /**
     * 固定时间片，单位毫秒
     */
    private final int timeSlice;
    /**
     * 目标槽位的位置
     */
    private volatile Integer currentBucketPosition;
    /**
     * 最新更新时间
     */
    private volatile Long latestCountTime;
    /**
     * 进入下一个槽位时使用的锁
     */
    private final ReentrantLock nextBucketLock;

    /**
     * 默认60个槽位，槽位的时间片为1000毫秒
     */
    public RollingNumber() {
        this(60, 1000);
    }
    /**
     * 初始化Bucket数量与每个Bucket的时间片等
     */
    public RollingNumber(int bucketSize, int timeSlice) {
        this.latestCountTime = System.currentTimeMillis();
        this.currentBucketPosition = null;
        this.nextBucketLock = new ReentrantLock();
        this.bucketSize = bucketSize;
        this.timeSlice = timeSlice;
        this.buckets = new Bucket[bucketSize];
        for (int i = 0; i < bucketSize; i++) {
            this.buckets[i] = new Bucket();
        }
    }

    public void count() {
        long countTime = System.currentTimeMillis();
        if (currentBucketPosition == null) {
            // 计算当前时间下标
            currentBucketPosition = calculateBucketIndex(countTime);
        }
        Bucket currentBucket = buckets[currentBucketPosition];
        if (countTime - latestCountTime >= timeSlice) {
            // 不在当前秒
            nextBucketLock.lock();
            try {
                if (countTime - latestCountTime >= timeSlice) {
                    int nextBucketPosition = calculateBucketIndex(countTime);
                    if (nextBucketPosition == currentBucketPosition) {
                        // 大一轮，调用存储当前数据位置数据
                        Bucket nextBucket = buckets[nextBucketPosition];
                        // todo
                        // 重新计数
                        nextBucket.reset(countTime);
                    } else {

                    }
                    if (!nextBucket.equals(currentBucket)) {

                        currentBucketPosition = nextBucketPosition;
                    }
                    latestCountTime = countTime;
                    nextBucket.count();
                    return;
                } else {
                    currentBucket = buckets[currentBucketPosition];
                }
            } finally {
                nextBucketLock.unlock();
            }
        }
        currentBucket.count();
    }

    /**
     * 计算bucket数组下标
     * @param timeMillis 时间戳
     * @return 下标
     */
    private int calculateBucketIndex (long timeMillis) {
        return (int) (timeMillis / timeSlice) % bucketSize;
    }

    public long sum() {
        long totalCount = 0;
        for (Bucket bucket : buckets) {
            totalCount += bucket.sum();
        }
        return totalCount;
    }

    public Bucket[] getBuckets() {
        return buckets;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 60; i++) {
            long second = System.currentTimeMillis()/1000;
            System.out.println(second);
            System.out.println(second % 60);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
