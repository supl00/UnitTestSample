package com.gazua.ddeokrok.coinman.chart.model.market;


import com.gazua.ddeokrok.coinman.chart.model.CheckerInfo;
import com.gazua.ddeokrok.coinman.chart.model.Market;
import com.gazua.ddeokrok.coinman.chart.model.Ticker;
import com.gazua.ddeokrok.coinman.chart.model.currency.Currency;
import com.gazua.ddeokrok.coinman.chart.model.currency.VirtualCurrency;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Upbit extends Market {
    private final static String NAME = "Upbit";
    private final static String TTS_NAME = NAME;
    private final static String URL = "https://crix-api-endpoint.upbit.com/v1/crix/candles/minutes/1?code=CRIX.UPBIT.KRW-%1$s";
    private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<>();

    static {
        CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
                Currency.KRW
        });
    }

    public Upbit() {
        super(NAME, TTS_NAME, CURRENCY_PAIRS);
    }

    @Override
    public String getUrl(int requestId, CheckerInfo checkerInfo) {
        return String.format(URL, checkerInfo.getCurrencyBaseLowerCase());
    }

    @Override
    protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker,
                                             CheckerInfo checkerInfo) throws Exception {
        ticker.high = jsonObject.getDouble("highPrice");
        ticker.low = jsonObject.getDouble("lowPrice");
        ticker.timestamp = jsonObject.getLong("timestamp");
        ticker.last = jsonObject.getDouble("tradePrice");
    }

    @Override
    protected void parseTicker(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
        responseString = responseString.replace("[", "");
        responseString = responseString.replace("]", "");
        super.parseTicker(requestId, responseString, ticker, checkerInfo);
//        parseTickerFromJsonObject(requestId, new JSONObject(responseString), ticker, checkerInfo);
    }
}
