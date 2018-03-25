package com.gazua.ddeokrok.coinman.chart.model.market;

import android.support.annotation.NonNull;

import com.gazua.ddeokrok.coinman.chart.model.CheckerInfo;
import com.gazua.ddeokrok.coinman.chart.model.CurrencyPairInfo;
import com.gazua.ddeokrok.coinman.chart.model.Market;
import com.gazua.ddeokrok.coinman.chart.model.Ticker;
import com.gazua.ddeokrok.coinman.chart.model.currency.VirtualCurrency;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class Binance extends Market {

	private final static String NAME = "Binance";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.binance.com/api/v1/ticker/24hr?symbol=%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://api.binance.com/api/v1/ticker/allPrices";

	private final static String[] COUNTER_CURRENCIES = {
			VirtualCurrency.BNB,
			VirtualCurrency.BTC,
			VirtualCurrency.ETH,
			VirtualCurrency.USDT
	};

	public Binance() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyPairId());
	}

	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("bidPrice");
		ticker.ask = jsonObject.getDouble("askPrice");
		ticker.vol = jsonObject.getDouble("volume");
		ticker.high = jsonObject.getDouble("highPrice");
		ticker.low = jsonObject.getDouble("lowPrice");
		ticker.last = jsonObject.getDouble("lastPrice");
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
		final JSONArray resultJsonArray = new JSONArray(responseString);

		for(int i=0; i<resultJsonArray.length(); ++i) {
			final JSONObject marketJsonObject = resultJsonArray.getJSONObject(i);
			final String symbol = marketJsonObject.getString("symbol");

			for (String counter : COUNTER_CURRENCIES) {
				if (symbol.endsWith(counter)) {
					pairs.add(new CurrencyPairInfo(
							symbol.substring(0, symbol.lastIndexOf(counter)),
							counter,
							symbol));
				}
			}
		}
	}

	@Override
	public int compareTo(@NonNull Market o) {
		return name.compareTo(o.name);
	}
}
