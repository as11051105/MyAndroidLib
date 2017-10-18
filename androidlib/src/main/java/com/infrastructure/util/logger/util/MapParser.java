package com.infrastructure.util.logger.util;


import com.infrastructure.util.CollectionUtils;

import java.util.Iterator;
import java.util.Map;


/**
 * Created by pengwei08 on 2015/7/25. Thanks to zhutiantao for providing an
 * array of analytical methods.
 */
public final class MapParser {


    public static String parseMap(Map<String, String> map) {
        if (CollectionUtils.isEmptyMap(map)) {
            return "map is null";
        }
        StringBuffer sb = new StringBuffer();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            sb.append("key= " + entry.getKey() + " and value= " + entry.getValue() + "\r\n");
            // System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }
        return sb.toString();
    }

}
