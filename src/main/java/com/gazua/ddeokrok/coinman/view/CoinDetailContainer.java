package com.gazua.ddeokrok.coinman.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class CoinDetailContainer extends LinearLayout {
    public CoinDetailContainer(Context context) {
        this(context, null);
    }

    public CoinDetailContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
//        return super.onInterceptTouchEvent(ev);
    }
}
