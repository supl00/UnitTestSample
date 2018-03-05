package com.gazua.ddeokrok.coinman.data;


import android.graphics.drawable.Drawable;

public class CoinData {
    private String mCoinName;
    private String mCoinAbbName;
    private String mUniqueName;
    private String mExchange;

    private int mPrice;
    private float mDiffPercent = 0.0f;
    private float mPremium = 0.0f;
    private String mCurrencyUnit = "KRW";

    private int mIconResId;

    public CoinData(String coinName, String exchange) {
        mUniqueName = coinName + exchange;
    }

    public String getUniqueName() {
        return mUniqueName;
    }

    public void setExchange(String str) {
        mExchange = str;
    }

    public String getExchange() {
        return mExchange;
    }

    public void setName(String str) {
        mCoinName = str;
    }

    public String getName() {
        return mCoinName;
    }

    public void setAbbName(String str) {
        mCoinAbbName = str;
    }

    public String getAbbName() {
        return mCoinAbbName;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setCurrencyUnit(String unit) {
        mCurrencyUnit = unit;
    }

    public String getCurrencyUnit() {
        return mCurrencyUnit;
    }

    public void setDiffPercent(float percent) {
        mDiffPercent = percent;
    }

    public float getDiffPercent() {
        return mDiffPercent;
    }

    public void setPremium(float premium) {
        mPremium = premium;
    }

    public float getPremium() {
        return mPremium;
    }

    public void setIconResId(int resId) {
        mIconResId = resId;
    }

    public int getIconResId() {
        return mIconResId;
    }
}
