package com.xiaozhi.test;

import java.util.Comparator;

/**
 * @author zhangzy
 * @date 2020/9/13-9:41
 * @since v1.0
 */
public class CompA  implements Comparable, Comparator {
    @Override
        public int compareTo(Object o) {
        return 0;
    }

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }
}
