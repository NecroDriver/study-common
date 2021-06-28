package com.xin.common.utils.qps;

import java.util.concurrent.atomic.LongAdder;

/**
 * Bucket
 *
 * @author mfh 2021/6/28 16:06
 * @version 1.0.0
 **/
public class Bucket {

    /**
     * 计数器
     */
    private final LongAdder counter;
    /**
     * 上次计入时间
     */
    private Long latestCountTime;

    public Bucket() {
        latestCountTime = System.currentTimeMillis();
        counter = new LongAdder();
    }

    public void count() {
        counter.add(1);
    }

    public void reset(long latestCountTime) {
        this.counter.reset();
        this.latestCountTime = latestCountTime;
    }

    public long sum() {
        return counter.sum();
    }
}
