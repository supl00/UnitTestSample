package com.gazua.ddeokrok.coinman.data;


public class CoinData {
    private String mCoinName;
    private String mCoinSubName;
    private String mExchange;

    private int mPrice;
    private float mDiffPercent = 0.0f;
    private float mPremium = 0.0f;
    private String mCurrencyUnit = "KRW";

    public CoinData() {
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

    public void setSubName(String str) {
        mCoinSubName = str;
    }

    public String getSubName() {
        return mCoinSubName;
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
}
