package com.gazua.ddeokrok.coinman.util;

import android.content.Context;
import android.content.res.Resources;


import com.gazua.ddeokrok.coinman.data.CoinData;
import com.gazua.ddeokrok.coinman.data.CoinInfo;

import java.util.ArrayList;
import java.util.Random;


public class CoinGenerator {
    private int MAX_RATIO = 100;
    private int MIN_RATIO = -100;

    private int MAX_BITCOIN_PRICE = 30000000;
    private int MIN_BITCOIN_PRICE = 5000000;

    public static int MAX_COIN_COUNT = 12;


    public static ArrayList<CoinData> getCoinSampleData(Context context, int index) {
        ArrayList<CoinData> list = new ArrayList<>();

//        int count = getRandomNumber(5, 10);
        for (int i = 0; i < 8; i++) {
            CoinData data = new CoinData(CoinInfo.COIN.values()[index].getName(), CoinInfo.EXCHANGE.values()[i].getName());
            data.setName(CoinInfo.COIN.values()[index].getName());
            data.setAbbName(CoinInfo.COIN.values()[index].getAbbName());
            data.setDiffPercent(getRandomRatio(0, 70));
            data.setPremium(getRandomRatio(0, 70));
            data.setExchange(CoinInfo.EXCHANGE.values()[i].getName());
            data.setCurrencyUnit(CoinInfo.EXCHANGE.values()[i].isUSDUnitType() ? "USD" : "KRW");
            data.setPrice(getRandomNumber(10000, 30000000));
            data.setIconResName(CoinInfo.COIN.values()[index].getIconResName(context.getResources()));
            list.add(data);
        }

        return list;
    }

    private static int getRandomNumber(int min,int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }

    private static float getRandomRatio(float min,float max) {
        return (max - min) * (new Random()).nextFloat() + min;
    }
}
