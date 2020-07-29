package com.xin.common.utils.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * ListUtils 集合工具类
 *
 * @author mfh 2020/7/29 16:07
 * @version 1.0.0
 **/
public class ListUtils {

    /**
     * 列表按数量分组
     *
     * @param list     数组
     * @param splitNum 分组数量
     * @param <T>      泛型
     * @return 分组后的列表
     */
    public static <T> List<List<T>> splitList(List<T> list, Integer splitNum) {
        List<List<T>> resultList = new ArrayList<>();
        // 分组次数
        int time = (list.size() + splitNum - 1) / splitNum;
        for (int i = 0; i < time; i++) {
            int fromIndex = splitNum * i;
            int toIndex = (i < time - 1) ? splitNum * (i + 1) : list.size();
            resultList.add(list.subList(fromIndex, toIndex));
        }
        return resultList;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);

        System.out.println(splitList(list, 4).toString());
    }
}
