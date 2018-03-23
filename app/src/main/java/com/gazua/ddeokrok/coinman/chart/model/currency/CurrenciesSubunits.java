package com.gazua.ddeokrok.coinman.chart.model.currency;

import com.gazua.ddeokrok.coinman.chart.model.CurrencySubunit;
import com.gazua.ddeokrok.coinman.chart.model.CurrencySubunitsMap;

import java.util.HashMap;

public class CurrenciesSubunits {

	public final static HashMap<String, CurrencySubunitsMap> CURRENCIES_SUBUNITS = new HashMap<String, CurrencySubunitsMap>();
	static {
		CURRENCIES_SUBUNITS.put(VirtualCurrency.BTC, new CurrencySubunitsMap(
				new CurrencySubunit(VirtualCurrency.BTC, 1),
				new CurrencySubunit(VirtualCurrency.mBTC, 1000),
				new CurrencySubunit(VirtualCurrency.uBTC, 1000000),
				new CurrencySubunit(VirtualCurrency.Satoshi, 100000000, false)
			)
		);
		CURRENCIES_SUBUNITS.put(VirtualCurrency.LTC, new CurrencySubunitsMap(
				new CurrencySubunit(VirtualCurrency.LTC, 1),
				new CurrencySubunit(VirtualCurrency.mLTC, 1000)
			)
		);
	}
}
