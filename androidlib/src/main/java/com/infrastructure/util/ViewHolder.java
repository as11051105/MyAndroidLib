package com.infrastructure.util;

import android.util.SparseArray;
import android.view.View;

/**
 * 类名: ViewHolder.java
 * <p>
 * 功能:超简洁的ViewHolder. 代码更简单，性能略低，可以忽略
 *
 * @author Mr.Z
 * @version 1.0
 * @dateTime 2015-2-9 上午11:21:37
 */
public class ViewHolder {

    /**
     * ImageView view = AbViewHolder.get(convertView, R.id.imageView);
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();

        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
