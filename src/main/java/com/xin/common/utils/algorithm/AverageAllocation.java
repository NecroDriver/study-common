package com.xin.common.utils.algorithm;

/**
 * AverageAllocation 平均分配
 * 使用场景 已知一堆固定数量物资分均分配各固定数量节点
 *
 * @author mfh 2021/4/22 11:23
 * @version 1.0.0
 **/
public class AverageAllocation {

    public static void main(String[] args) {
        int resourceNum = 59;
        int nodeNum = 5;
        for (int i = 0; i < nodeNum; i++) {
            int left = i * resourceNum / nodeNum;
            int right = (i + 1) * resourceNum / nodeNum;
            System.out.println(left + "-" + right + " " + (right-left));
        }
    }
}
