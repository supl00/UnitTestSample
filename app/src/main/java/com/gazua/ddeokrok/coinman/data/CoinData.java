package com.gazua.ddeokrok.coinman.data;


public class CoinData {
    private String mCoinName;
    private String mCoinSubName;
    private String mExchange;

    private String mPrice;
    private String mDiffPercent;
    private String mPremium;
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

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setCurrencyUnit(String unit) {
        mCurrencyUnit = unit;
    }

    public String getCurrencyUnit() {
        return mCurrencyUnit;
    }

    public void setDiffPercent(String percent) {
        mDiffPercent = percent;
    }

    public String getDiffPercent() {
        return mDiffPercent;
    }

    public void setPremium(String premium) {
        mPremium = premium;
    }

    public String getPremium() {
        return mPremium;
    }
}
