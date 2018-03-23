package com.gazua.ddeokrok.coinman.chart.model.market;

import com.gazua.ddeokrok.coinman.chart.model.CheckerInfo;
import com.gazua.ddeokrok.coinman.chart.model.Market;
import com.gazua.ddeokrok.coinman.chart.model.Ticker;
import com.gazua.ddeokrok.coinman.chart.model.currency.Currency;
import com.gazua.ddeokrok.coinman.chart.model.currency.VirtualCurrency;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class BitBay extends Market {

	private final static String NAME = "BitBay.net";
	private final static String TTS_NAME = "Bit Bay";
	private final static String URL = "https://bitbay.net/API/Public/%1$s%2$s/ticker.json";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BCC, new String[]{
				VirtualCurrency.BTC,
				Currency.PLN,
				Currency.USD,
				Currency.EUR
		});
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.PLN,
				Currency.USD,
				Currency.EUR
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DASH, new String[]{
				VirtualCurrency.BTC,
				Currency.PLN,
				Currency.USD,
				Currency.EUR
		});
		CURRENCY_PAIRS.put(VirtualCurrency.GAME, new String[]{
				VirtualCurrency.BTC,
				Currency.PLN,
				Currency.USD,
				Currency.EUR
		});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				VirtualCurrency.BTC,
				Currency.PLN,
				Currency.USD,
				Currency.EUR
			});
		CURRENCY_PAIRS.put(VirtualCurrency.ETH, new String[]{
				VirtualCurrency.BTC,
				Currency.PLN,
				Currency.USD,
				Currency.EUR
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LSK, new String[]{
				VirtualCurrency.BTC,
				Currency.PLN,
				Currency.USD,
				Currency.EUR
			});
	}
	
	public BitBay() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("bid");
		ticker.ask = jsonObject.getDouble("ask");
		ticker.vol = jsonObject.getDouble("volume");
		ticker.high = jsonObject.getDouble("max");
		ticker.low = jsonObject.getDouble("min");
		ticker.last = jsonObject.getDouble("last");
	}
}
