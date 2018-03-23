package com.gazua.ddeokrok.coinman.chart.model.market;

import com.gazua.ddeokrok.coinman.chart.model.CheckerInfo;
import com.gazua.ddeokrok.coinman.chart.model.Market;
import com.gazua.ddeokrok.coinman.chart.model.Ticker;
import com.gazua.ddeokrok.coinman.chart.model.currency.Currency;
import com.gazua.ddeokrok.coinman.chart.model.currency.VirtualCurrency;
import com.gazua.ddeokrok.coinman.chart.util.TimeUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Coinapult extends Market {

	private final static String NAME = "Coinapult";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.coinapult.com/api/ticker?market=%2$s_%1$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();

	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD,
				Currency.EUR,
				Currency.GBP,
				Currency.XAG,
				Currency.XAU
			});
	}

	public Coinapult() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected String parseErrorFromJsonObject(int requestId, JSONObject jsonObject, CheckerInfo checkerInfo) throws Exception {
		return jsonObject.getString("error");
	}

	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.last = jsonObject.getDouble("index");
		ticker.timestamp = jsonObject.getLong("updatetime") * TimeUtils.MILLIS_IN_SECOND;
	}
}
