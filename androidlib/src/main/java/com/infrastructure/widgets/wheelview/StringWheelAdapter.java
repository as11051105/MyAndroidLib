
package com.infrastructure.widgets.wheelview;


import com.infrastructure.util.StringUtils;

import java.util.List;


/**
 * 类名: StringWheelAdapter.java
 * <p>
 * 功能:轮子适配器（字符串）
 *
 * @author Mr.Z
 * @version 1.0
 * @dateTime 2015-2-9 上午11:25:29
 */
public class StringWheelAdapter implements WheelAdapter {

    /**
     * The default items length.
     */
    public static final int DEFAULT_LENGTH = -1;
    // items
    /**
     * The items.
     */
    private List<String> items;
    // length
    /**
     * The length.
     */
    private int length = -1;

    /**
     * Constructor.
     *
     * @param items  the items
     * @param length the max items length
     */
    public StringWheelAdapter(List<String> items, int length) {
        this.items = items;
        this.length = length;
    }

    /**
     * Constructor.
     *
     * @param items the items
     */
    public StringWheelAdapter(List<String> items) {
        this(items, DEFAULT_LENGTH);
    }

    /**
     * @param index the index
     * @return the item
     * @version v1.0
     * @author: amsoft.cn
     * @date：2013-6-17 上午9:04:49
     */
    @Override
    public String getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return "  " + items.get(index) + "  ";
        }
        return null;
    }

    /**
     * @return the items count
     * @version v1.0
     * @author: amsoft.cn
     * @date：2013-6-17 上午9:04:49
     */
    @Override
    public int getItemsCount() {
        return items.size();
    }

    /**
     * @return the maximum length
     * @version v1.0
     * @author: amsoft.cn
     * @date：2013-6-17 上午9:04:49
     */
    @Override
    public int getMaximumLength() {
        if (length != -1) {
            return length;
        }
        int maxLength = 0;
        for (int i = 0; i < items.size(); i++) {
            String cur = items.get(i);
            int l = StringUtils.strLength(cur);
            if (maxLength < l) {
                maxLength = l;
            }
        }
        return maxLength;
    }

}
