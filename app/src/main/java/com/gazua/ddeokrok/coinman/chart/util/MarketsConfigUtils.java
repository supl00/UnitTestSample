package com.gazua.ddeokrok.coinman.chart.util;

import com.gazua.ddeokrok.coinman.chart.config.MarketsConfig;
import com.gazua.ddeokrok.coinman.chart.model.Market;
import com.gazua.ddeokrok.coinman.chart.model.market.Unknown;

import java.util.ArrayList;

public class MarketsConfigUtils {

	private final static Market UNKNOWN = new Unknown();
	
	public static Market getMarketById(int id) {
		synchronized (MarketsConfig.MARKETS) {
			if(id>=0 && id<MarketsConfig.MARKETS.size()) {
				return new ArrayList<Market>(MarketsConfig.MARKETS.values()).get(id);
			}
		}
		return UNKNOWN;
	}
	
	public static Market getMarketByKey(String key) {
		synchronized (MarketsConfig.MARKETS) {
			if(MarketsConfig.MARKETS.containsKey(key))
				return MarketsConfig.MARKETS.get(key);
		}
		return UNKNOWN;
	}
	
	public static int getMarketIdByKey(String key) {
		int i=0;
		for(Market market : MarketsConfig.MARKETS.values()){
			if(market.key.equals(key))
				return i;
			++i;
		}
		
		return 0;
	}
}
