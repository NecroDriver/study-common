package com.xin.common.utils.collection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * list转map<key,list>
     *
     * @param dataList   数据源
     * @param methodName 方法名称
     * @param <V>        key
     * @param <T>        源类型
     * @return map
     */
    public static <V, T> Map<V, List<T>> listToListMap(List<T> dataList, String methodName) {
        // 存放返回结果信息
        Map<V, List<T>> resultMap = new HashMap<>();
        try {
            // list按methodName的值分组
            // 记录上次的值
            V lastValue = null;
            // 临时记录列表
            List<T> tempList = null;
            // 循环处理数据
            for (T vo : dataList) {
                // 获取methodName的值
                Method method = vo.getClass().getDeclaredMethod(methodName);
                V v = (V) method.invoke(vo);
                // 不等于，新的分组
                if (null == lastValue || !lastValue.equals(v)) {
                    if (null != lastValue) {
                        // 不为空，保存上份数据
                        resultMap.put(lastValue, tempList);
                    }
                    // 开始新的记录
                    tempList = new ArrayList<>();
                    tempList.add(vo);
                    // 标记赋值
                    lastValue = v;
                } else {
                    // 相等，列表继续放入数据
                    tempList.add(vo);
                }
            }
            // 处理最后一组数据
            resultMap.put(lastValue, tempList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public static void main(String[] args) {
        List<User> list = new ArrayList<>();
        list.add(new User(18, "aaa"));
        list.add(new User(18, "eet"));
        list.add(new User(18, "rrr"));
        list.add(new User(12, "qqq"));
        list.add(new User(12, "ooo"));
        list.add(new User(12, "ttt"));
        list.add(new User(14, "ccc"));
        list.add(new User(14, "sas"));
        list.add(new User(11, "www"));
        list.add(new User(11, "hhh"));
        Map<Object, List<User>> map = listToListMap(list, "getUserCode");
        System.out.println(map);
    }


    static class User {
        private Integer userCode;

        private String userName;

        public User(Integer userCode, String userName) {
            this.userCode = userCode;
            this.userName = userName;
        }

        public Integer getUserCode() {
            return userCode;
        }

        public void setUserCode(Integer userCode) {
            this.userCode = userCode;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
