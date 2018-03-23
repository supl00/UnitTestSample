package com.gazua.ddeokrok.coinman.chart.model.market;

import com.gazua.ddeokrok.coinman.chart.model.CheckerInfo;
import com.gazua.ddeokrok.coinman.chart.model.CurrencyPairInfo;
import com.gazua.ddeokrok.coinman.chart.model.Market;
import com.gazua.ddeokrok.coinman.chart.model.Ticker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Cryptopia extends Market {

	private final static String NAME = "Cryptopia";
	private final static String TTS_NAME = "Cryptopia";
	private final static String URL = "https://www.cryptopia.co.nz/api/GetMarket/%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://www.cryptopia.co.nz/api/GetTradePairs";
	
	public Cryptopia() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyPairId());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject dataJsonObject = jsonObject.getJSONObject("Data");
		ticker.bid = dataJsonObject.getDouble("BidPrice");
		ticker.ask = dataJsonObject.getDouble("AskPrice");
		ticker.vol = dataJsonObject.getDouble("Volume");
		ticker.high = dataJsonObject.getDouble("High");
		ticker.low = dataJsonObject.getDouble("Low");
		ticker.last = dataJsonObject.getDouble("LastPrice");
	}
	
	@Override
	protected String parseErrorFromJsonObject(int requestId, JSONObject jsonObject, CheckerInfo checkerInfo) throws Exception {
		return jsonObject.getString("Message");
	}
	
	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL_CURRENCY_PAIRS;
	}
	
	@Override
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray dataJsonArray = jsonObject.getJSONArray("Data");
		for(int i=0; i<dataJsonArray.length(); ++i) {
			final JSONObject pairJsonObject = dataJsonArray.getJSONObject(i);
			final String currencyBase = pairJsonObject.getString("Symbol");
			final String currencyCounter = pairJsonObject.getString("BaseSymbol");
			final String pairId = pairJsonObject.getString("Id");
			if (currencyCounter != null && currencyCounter != null && pairId != null) {
				pairs.add(new CurrencyPairInfo(
						currencyBase,
						currencyCounter,
						pairId));
			}
		}
	}
}
