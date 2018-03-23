package com.gazua.ddeokrok.coinman.chart.model.market;

import com.gazua.ddeokrok.coinman.chart.model.CheckerInfo;
import com.gazua.ddeokrok.coinman.chart.model.CurrencyPairInfo;
import com.gazua.ddeokrok.coinman.chart.model.Market;
import com.gazua.ddeokrok.coinman.chart.model.Ticker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Quoine extends Market {

	private final static String NAME = "Quoine";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.quoine.com/products/code/CASH/%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://api.quoine.com/products/";
	
	public Quoine() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyPairId());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("market_bid");
		ticker.ask = jsonObject.getDouble("market_ask");
		ticker.vol = jsonObject.getDouble("volume_24h");
		ticker.high = jsonObject.getDouble("high_market_ask");
		ticker.low = jsonObject.getDouble("low_market_bid");
		ticker.last = jsonObject.getDouble("last_traded_price");
	}

	
	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL_CURRENCY_PAIRS;
	}
	
	@Override
	protected void parseCurrencyPairs(int requestId, String responseString, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray pairsJsonArray = new JSONArray(responseString);
		for(int i=0; i<pairsJsonArray.length(); ++i) {
			final JSONObject pairJsonObject = pairsJsonArray.getJSONObject(i);
			if (!"CASH".equals(pairJsonObject.getString("code"))) {
				continue;
			}
			
			final String currencyCounter = pairJsonObject.getString("currency");
			final String pairName = pairJsonObject.getString("currency_pair_code");
			if (pairName != null && currencyCounter != null && pairName.endsWith(currencyCounter)) {
				final String currencyBase = pairName.substring(0, pairName.length() - currencyCounter.length());
				pairs.add(new CurrencyPairInfo(
						currencyBase,
						currencyCounter,
						pairName));
			}
		}
	}
}
