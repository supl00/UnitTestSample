package com.gazua.ddeokrok.coinman.chart.model.market;

import android.support.annotation.NonNull;

import com.gazua.ddeokrok.coinman.R;
import com.gazua.ddeokrok.coinman.chart.model.CheckerInfo;
import com.gazua.ddeokrok.coinman.chart.model.Market;
import com.gazua.ddeokrok.coinman.chart.model.currency.VirtualCurrency;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Unknown extends Market {

	private final static String NAME = "UNKNOWN";
	private final static String TTS_NAME = NAME;
	private final static String URL = "";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{ VirtualCurrency.BTC });
	}
	
	public Unknown() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public int getCautionResId() {
		return R.string.market_caution_unknown;
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}

	@Override
	public int compareTo(@NonNull Market o) {
		return name.compareTo(o.name);
	}
}
