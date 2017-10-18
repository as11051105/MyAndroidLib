package com.infrastructure.widgets.wheelview;

/**
 * Wheel scrolled listener interface.
 */
public interface OnWheelScrollListenerDate {
    /**
     * Callback method to be invoked when scrolling started.
     * 
     * @param wheel
     *            the wheel view whose state has changed.
     */
    void onScrollingStarted(WheelViewDate wheel);

    /**
     * Callback method to be invoked when scrolling ended.
     * 
     * @param wheel
     *            the wheel view whose state has changed.
     */
    void onScrollingFinished(WheelViewDate wheel);
}
