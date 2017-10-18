package com.infrastructure.widgets.iconicTabBar;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.transition.AutoTransition;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infrastructure.R;


public class IconicTab extends LinearLayout {

    private static final int TRANSITION_DURATION = 200;

    private LinearLayout tabItem;
    private ImageView tabIcon;
    private TextView tabText, tabBadge;

    private int tabPosition;
    private int badgeCount;

    private int tabDefaultColor;
    private int tabSelectedColor;

    private OnTabClickListener onTabClickListener;

    public IconicTab(Context context) {
        super(context);
        inflateView();
    }

    public IconicTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateView();
    }

    public IconicTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IconicTab(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateView();
    }

    private void inflateView() {
        inflate(getContext(), R.layout.layout_tab_item, this);

        tabItem = (LinearLayout) findViewById(R.id.tab_item);
        tabIcon = (ImageView) findViewById(R.id.tab_item_icon);
        tabText = (TextView) findViewById(R.id.tab_item_text);
        tabBadge = (TextView) findViewById(R.id.tab_item_badge);

        tabDefaultColor = ContextCompat.getColor(getContext(), R.color.defaultColor);
        tabSelectedColor = ContextCompat.getColor(getContext(), R.color.defaultSelectedColor);

        tabIcon.setColorFilter(tabDefaultColor);
        tabText.setTextColor(tabDefaultColor);
        tabText.setTextSize(11);
        tabText.setVisibility(GONE);
        tabBadge.setVisibility(GONE);
    }

    public void bindData(int position, @DrawableRes int iconResId, @StringRes int textResId) {
        tabPosition = position;
        tabIcon.setImageResource(iconResId);
        tabText.setText(textResId);
        setBadgeCount(0);
        onTabItemClick();
    }

    public void bindData(int position, @DrawableRes int iconResId, CharSequence text) {
        tabPosition = position;
        tabIcon.setImageResource(iconResId);
        tabText.setText(text);
        setBadgeCount(0);
        onTabItemClick();
    }

    public void bindData(int position, Drawable iconDrawable, CharSequence text) {
        tabPosition = position;
        tabIcon.setImageDrawable(iconDrawable);
        tabText.setText(text);
        setBadgeCount(0);
        onTabItemClick();
    }

    public void setIcon(@DrawableRes int iconResId) {
        tabIcon.setImageResource(iconResId);
    }

    public void setIcon(Drawable iconDrawable) {
        tabIcon.setImageDrawable(iconDrawable);
    }

    public void setText(@StringRes int stringResId) {
        tabText.setText(stringResId);
    }

    public void setText(CharSequence text) {
        tabText.setText(text);
    }

    public void setTabDefaultColor(@ColorInt int tabDefaultColor) {
        this.tabDefaultColor = tabDefaultColor;
        tabIcon.setColorFilter(tabDefaultColor);
        tabText.setTextColor(tabDefaultColor);
    }

    public void setTabSelectedColor(@ColorInt int tabSelectedColor) {
        this.tabSelectedColor = tabSelectedColor;
    }

    public void setSelected() {
        tabIcon.setColorFilter(tabSelectedColor);
        tabText.setTextColor(tabSelectedColor);
        tabText.setVisibility(VISIBLE);
    }

    public void setUnselected() {
        tabIcon.setColorFilter(tabDefaultColor);
        tabText.setTextColor(tabDefaultColor);
        tabText.setVisibility(GONE);
    }

    public void setSelectedWithAnimation() {
        TransitionManager.beginDelayedTransition(this, getTransition());
        tabIcon.setColorFilter(tabSelectedColor);
        tabText.setTextColor(tabSelectedColor);
        tabText.setVisibility(VISIBLE);
    }

    public void setUnselectedWithAnimation() {
        TransitionManager.beginDelayedTransition(this, getTransition());
        tabIcon.setColorFilter(tabDefaultColor);
        tabText.setTextColor(tabDefaultColor);
        tabText.setVisibility(GONE);
    }

    public void setBadgeCount(int badgeCount) {
        this.badgeCount = badgeCount;
        if (badgeCount > 0) {
            tabBadge.setVisibility(VISIBLE);
            tabBadge.setText(String.format("%s", badgeCount));
        } else {
            tabBadge.setVisibility(GONE);
        }
    }

    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
    }

    private void onTabItemClick() {
        tabItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTabClickListener != null)
                    onTabClickListener.onTabClick(IconicTab.this, tabPosition);
            }
        });
    }

    private Transition getTransition() {
        return new AutoTransition().setDuration(TRANSITION_DURATION);
    }

    public CharSequence getText() {
        return tabText.getText();
    }

    public Drawable getIcon() {
        return tabIcon.getDrawable();
    }

    public int getBadgeCount() {
        return badgeCount;
    }

    public interface OnTabClickListener {
        void onTabClick(IconicTab tabBottomBar, int position);
    }

}
