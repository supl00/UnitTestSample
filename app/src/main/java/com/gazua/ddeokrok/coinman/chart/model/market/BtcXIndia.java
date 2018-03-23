package com.gazua.ddeokrok.coinman.chart.model.market;

import com.gazua.ddeokrok.coinman.chart.model.CheckerInfo;
import com.gazua.ddeokrok.coinman.chart.model.Market;
import com.gazua.ddeokrok.coinman.chart.model.Ticker;
import com.gazua.ddeokrok.coinman.chart.model.currency.Currency;
import com.gazua.ddeokrok.coinman.chart.model.currency.VirtualCurrency;
import com.gazua.ddeokrok.coinman.chart.util.ParseUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class BtcXIndia extends Market {

	private final static String NAME = "BTCXIndia";
	private final static String TTS_NAME = "BTC X India";
	private final static String URL = "https://api.btcxindia.com/ticker";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.XRP, new String[]{
				Currency.INR
			});
	}
	
	public BtcXIndia() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("bid");
		ticker.ask = jsonObject.getDouble("ask");
		ticker.vol = jsonObject.getDouble("total_volume_24h");
		ticker.high = ParseUtils.getDoubleFromString(jsonObject, "high");
		ticker.low = ParseUtils.getDoubleFromString(jsonObject, "low");
		ticker.last = jsonObject.getDouble("last_traded_price");
	}
}
