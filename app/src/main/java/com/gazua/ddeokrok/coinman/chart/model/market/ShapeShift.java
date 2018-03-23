package com.gazua.ddeokrok.coinman.chart.model.market;

import com.gazua.ddeokrok.coinman.chart.model.CheckerInfo;
import com.gazua.ddeokrok.coinman.chart.model.CurrencyPairInfo;
import com.gazua.ddeokrok.coinman.chart.model.Market;
import com.gazua.ddeokrok.coinman.chart.model.Ticker;
import com.gazua.ddeokrok.coinman.chart.util.ParseUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShapeShift extends Market {

	private final static String NAME = "ShapeShift";
	private final static String TTS_NAME = "Shape Shift";
	private final static String URL = "https://shapeshift.io/rate/%1$s_%2$s";
	private final static String URL_CURRENCY_PAIRS = "https://shapeshift.io/getcoins";
	
	public ShapeShift() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.last = ParseUtils.getDoubleFromString(jsonObject, "rate");
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
		final JSONArray jsonCoinNames = jsonObject.names();
		final List<String> availableCoinNames = new ArrayList<String>(jsonCoinNames.length());
		for(int i=0; i<jsonCoinNames.length(); ++i) {
			final JSONObject coinJsonObject = jsonObject.getJSONObject(jsonCoinNames.getString(i));
			if ("available".equals(coinJsonObject.optString("status"))) {
				availableCoinNames.add(coinJsonObject.getString("symbol"));
			}
		}
		
		final int coinesCount = availableCoinNames.size();
		for (int i = 0; i < coinesCount; ++i) {
			for (int j = 0; j < coinesCount; ++j) {
				if (i != j) {
					String currencyBase = availableCoinNames.get(i);
					String currencyCounter = availableCoinNames.get(j);
					pairs.add(new CurrencyPairInfo(
							currencyBase,
							currencyCounter,
							null));
				}
			}
		}
	}
}
