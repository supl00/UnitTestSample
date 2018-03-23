package com.gazua.ddeokrok.coinman.chart.model.market;

import com.gazua.ddeokrok.coinman.chart.model.CheckerInfo;
import com.gazua.ddeokrok.coinman.chart.model.Market;
import com.gazua.ddeokrok.coinman.chart.model.Ticker;
import com.gazua.ddeokrok.coinman.chart.model.currency.Currency;
import com.gazua.ddeokrok.coinman.chart.model.currency.VirtualCurrency;
import com.gazua.ddeokrok.coinman.chart.util.ParseUtils;
import com.gazua.ddeokrok.coinman.chart.util.TimeUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class FoscEx extends Market {
	
	private final static String NAME = "Fosc-Ex";
	private final static String TTS_NAME = "Fosc Ex";
	private final static String URL = "http://www.fosc-ex.com/api-public-ticker";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.KNC, new String[]{
				Currency.KRW
			});
	}
	
	public FoscEx() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.vol = ParseUtils.getDoubleFromString(jsonObject, "volume");
		ticker.last = ParseUtils.getDoubleFromString(jsonObject, "last");
		ticker.timestamp = jsonObject.getLong("timestamp") * TimeUtils.MILLIS_IN_SECOND;
		
	}
	
	@Override
	protected String parseErrorFromJsonObject(int requestId, JSONObject jsonObject, CheckerInfo checkerInfo) throws Exception {
		return jsonObject.getString("error");
	}

}
