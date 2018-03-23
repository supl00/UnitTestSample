package com.gazua.ddeokrok.coinman.chart.model.market;

import com.gazua.ddeokrok.coinman.chart.model.CheckerInfo;
import com.gazua.ddeokrok.coinman.chart.model.CurrencyPairInfo;
import com.gazua.ddeokrok.coinman.chart.model.Market;
import com.gazua.ddeokrok.coinman.chart.model.Ticker;
import com.gazua.ddeokrok.coinman.chart.model.currency.Currency;
import com.gazua.ddeokrok.coinman.chart.model.currency.VirtualCurrency;
import com.gazua.ddeokrok.coinman.chart.util.ParseUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class Bitso extends Market {

	private final static String NAME = "Bitso";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.bitso.com/public/info";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.MXN
			});
		CURRENCY_PAIRS.put(VirtualCurrency.ETH, new String[]{
				VirtualCurrency.BTC,
				Currency.MXN
			});
		CURRENCY_PAIRS.put(VirtualCurrency.XRP, new String[]{
				VirtualCurrency.BTC,
				Currency.MXN
			});
		CURRENCY_PAIRS.put(VirtualCurrency.BCH, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				VirtualCurrency.BTC,
				Currency.MXN
			});
	}
	
	public Bitso() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		String pairId = checkerInfo.getCurrencyPairId();
		if (pairId == null) {
			pairId = checkerInfo.getCurrencyBaseLowerCase()+"_"+checkerInfo.getCurrencyCounterLowerCase();
		}
		final JSONObject pairJsonObject = jsonObject.getJSONObject(pairId);
		ticker.bid = ParseUtils.getDoubleFromString(pairJsonObject, "buy");
		ticker.ask = ParseUtils.getDoubleFromString(pairJsonObject, "sell");
		ticker.vol = ParseUtils.getDoubleFromString(pairJsonObject, "volume");
		ticker.last = ParseUtils.getDoubleFromString(pairJsonObject, "rate");
	}

	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL;
	}

	@Override
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray pairIds = jsonObject.names();
		for(int i=0; i<pairIds.length(); ++i) {
			String pairId = pairIds.getString(i);
			if(pairId==null)
				continue;
			String[] currencies = pairId.split("_");
			if(currencies.length != 2)
				continue;

			pairs.add(new CurrencyPairInfo(
					currencies[0].toUpperCase(Locale.US),
					currencies[1].toUpperCase(Locale.US),
					pairId));
		}
	}
}
