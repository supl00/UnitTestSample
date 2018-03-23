package com.gazua.ddeokrok.coinman.chart.model.market;

import com.gazua.ddeokrok.coinman.chart.model.CheckerInfo;
import com.gazua.ddeokrok.coinman.chart.model.CurrencyPairInfo;
import com.gazua.ddeokrok.coinman.chart.model.Market;
import com.gazua.ddeokrok.coinman.chart.model.Ticker;
import com.gazua.ddeokrok.coinman.chart.model.currency.VirtualCurrency;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class LocalBitcoins extends Market {

	private final static String NAME = "LocalBitcoins";
	private final static String TTS_NAME = "Local Bitcoins";
	private final static String URL = "https://localbitcoins.com/bitcoinaverage/ticker-all-currencies/";
	private final static String URL_CURRENCY_PAIRS = URL;
	
	public LocalBitcoins() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject pairJsonObject = jsonObject.getJSONObject(checkerInfo.getCurrencyPairId());
		ticker.vol = pairJsonObject.getDouble("volume_btc");
		final JSONObject ratesJsonObject = pairJsonObject.getJSONObject("rates");
		ticker.last = ratesJsonObject.getDouble("last");
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
		final JSONArray pairsJsonArray = jsonObject.names();
		for(int i=0; i<pairsJsonArray.length(); ++i) {
			final String currencyCounter = pairsJsonArray.getString(i);
			if(currencyCounter != null) {
				pairs.add(new CurrencyPairInfo(
						VirtualCurrency.BTC,
						currencyCounter,
						currencyCounter));
			}
		}
	}
}
