package com.infrastructure.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/10.
 */

public class CollectionUtils {
    /**
     * 判断Map是否为null
     *
     * @param map
     * @return
     */
    public static boolean isEmptyMap(Map<?, ?> map) {
        return map == null || isEmptyList(map.keySet());
    }

    public static boolean isEmptyList(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmptyList(Collection<?> collection) {
        return !isEmptyList(collection);
    }

    /**
     * 集合中对象没清理造成的内存泄漏
     *
     * @param list
     */
    public static void clearList(List list) {
        if (list != null) {
            list.clear();
            list = null;
        }
    }
}
