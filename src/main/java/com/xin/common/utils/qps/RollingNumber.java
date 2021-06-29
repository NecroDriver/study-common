package com.xin.common.utils.qps;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
     * 默认60个槽位，槽位的时间片为1000毫秒
     */
    public RollingNumber() {
        this(60, 1000);
    }

    /**
     * 初始化Bucket数量与每个Bucket的时间片等
     */
    public RollingNumber(int bucketSize, int timeSlice) {
        this.bucketSize = bucketSize;
        this.timeSlice = timeSlice;
        this.buckets = new Bucket[bucketSize];
        for (int i = 0; i < bucketSize; i++) {
            this.buckets[i] = new Bucket();
        }
        // 启用定时服务线程每秒清理对应bucket
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleWithFixedDelay(() -> {
            try {
                // 处理2s之前的数据
                long timeMillis = System.currentTimeMillis() - (2L * this.timeSlice);
                int bucketIndex = calculateBucketIndex(timeMillis);
                Bucket bucket = this.buckets[bucketIndex];
                long count = bucket.sum();
                if (count > 0) {
                    // todo 持久化数据
                    System.out.println(new Date(timeMillis) + "请求数量：" + count);
                    // 重置
                    bucket.reset(timeMillis);
                }
            } catch (Exception e) {
                // 保证清理工作顺利运行
                System.out.println(e.getMessage());
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void count() {
        long countTime = System.currentTimeMillis();
        // 计算当前时间下标
        int currentBucketPosition = calculateBucketIndex(countTime);
        Bucket currentBucket = buckets[currentBucketPosition];
        currentBucket.count();
    }

    /**
     * 计算bucket数组下标
     *
     * @param timeMillis 时间戳
     * @return 下标
     */
    private int calculateBucketIndex(long timeMillis) {
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
            long second = System.currentTimeMillis() / 1000;
            System.out.println(second);
            System.out.println(second % 60);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
