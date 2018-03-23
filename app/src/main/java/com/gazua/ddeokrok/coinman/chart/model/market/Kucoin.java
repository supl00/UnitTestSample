package com.gazua.ddeokrok.coinman.chart.model.market;

import com.gazua.ddeokrok.coinman.chart.model.CheckerInfo;
import com.gazua.ddeokrok.coinman.chart.model.CurrencyPairInfo;
import com.gazua.ddeokrok.coinman.chart.model.Market;
import com.gazua.ddeokrok.coinman.chart.model.Ticker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


public class Kucoin extends Market {

    private final static String NAME = "Kucoin";
    private final static String TTS_NAME = NAME;
    private final static String URL = "https://api.kucoin.com/v1/open/tick?symbol=%1$s";

    private final static String URL_COINS_PAIRS = "https://api.kucoin.com/v1/market/open/symbols";

    public Kucoin() {
        super(NAME, TTS_NAME, null);
    }

    @Override
    public String getUrl(int requestId, CheckerInfo checkerInfo) {
        return String.format(URL, checkerInfo.getCurrencyPairId());
    }

    @Override
    protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
        JSONObject tickerJsonObject = jsonObject.getJSONObject("data");
        ticker.bid = tickerJsonObject.getDouble("buy");
        ticker.ask = tickerJsonObject.getDouble("sell");
        ticker.vol = tickerJsonObject.getDouble("vol");
        ticker.high = tickerJsonObject.getDouble("high");
        ticker.low = tickerJsonObject.getDouble("low");
        ticker.last = tickerJsonObject.getDouble("lastDealPrice");
        ticker.timestamp = tickerJsonObject.getLong("datetime");
    }

    // ====================
    // Get currency pairs
    // ====================
    @Override
    public String getCurrencyPairsUrl(int requestId) {
        return URL_COINS_PAIRS;
    }

    @Override
    protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
        final JSONArray data = jsonObject.getJSONArray("data");

        for(int i=0; i< data.length(); ++i) {
            final JSONObject pairJsonObject = data.getJSONObject(i);
            pairs.add(new CurrencyPairInfo(
                pairJsonObject.getString("coinType"),
                pairJsonObject.getString("coinTypePair"),
                pairJsonObject.getString("symbol")
            ));
        }
    }

}
