package com.gazua.ddeokrok.coinman.chart.model.market;

import com.gazua.ddeokrok.coinman.chart.model.CheckerInfo;
import com.gazua.ddeokrok.coinman.chart.model.Market;
import com.gazua.ddeokrok.coinman.chart.model.Ticker;
import com.gazua.ddeokrok.coinman.chart.model.currency.Currency;
import com.gazua.ddeokrok.coinman.chart.model.currency.VirtualCurrency;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by naveed on 17/01/2017.
 */

public class Urdubit extends Market {

    private final static String NAME = "Urdubit";
    private final static String TTS_NAME = NAME;
    private final static String URL = "https://api.blinktrade.com/api/v1/%2$s/ticker?crypto_currency=%1$s";
    private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
    static {
        CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
                Currency.PKR
        });

    }

    public Urdubit() {
        super(NAME, TTS_NAME, CURRENCY_PAIRS);
    }

    @Override
    public String getUrl(int requestId, CheckerInfo checkerInfo) {
        return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
    }

    @Override
    protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
        ticker.bid = jsonObject.getDouble("buy");
        ticker.ask = jsonObject.getDouble("sell");
        ticker.vol = jsonObject.getDouble("vol");
        ticker.high = jsonObject.getDouble("high");
        ticker.low = jsonObject.getDouble("low");
        ticker.last = jsonObject.getDouble("last");
        //ticker.timestamp = jsonObject.getLong("timestamp");
    }

}
