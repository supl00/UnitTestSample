package com.gazua.ddeokrok.coinman.widget.advtextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.gazua.ddeokrok.coinman.widget.advtextview.base.AnimationListener;
import com.gazua.ddeokrok.coinman.widget.advtextview.base.HTextView;


public class VerticalAnimTextView extends HTextView {
    private VerticalAnimText verticalAnimText;

    public VerticalAnimTextView(Context context) {
        this(context,null);
    }

    public VerticalAnimTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VerticalAnimTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    @Override
    public void setAnimationListener(AnimationListener listener) {
        verticalAnimText.setAnimationListener(listener);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        verticalAnimText = new VerticalAnimText();
        verticalAnimText.init(this, attrs, defStyleAttr);
        setMaxLines(1);
        setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override
    public void setProgress(float progress) {
        verticalAnimText.setProgress(progress);
    }

    @Override
    public void animateText(CharSequence text) {
        boolean enableFallAnim = isFallAnimation(text);
        verticalAnimText.enableFallAnim(enableFallAnim);
        verticalAnimText.animateText(text);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        verticalAnimText.onDraw(canvas);
    }

    private boolean isFallAnimation(CharSequence test) {
        String prevText = getText().toString().replaceAll("[^0-9]", "");
        int prev = Integer.parseInt(prevText);
        String currentText = test.toString().replaceAll("[^0-9]", "");
        int current = Integer.parseInt(currentText);

        return prev > current;
    }
}
