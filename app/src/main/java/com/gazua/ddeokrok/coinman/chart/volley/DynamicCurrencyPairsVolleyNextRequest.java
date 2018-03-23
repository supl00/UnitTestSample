package com.gazua.ddeokrok.coinman.chart.volley;

import com.android.volley.toolbox.RequestFuture;
import com.gazua.ddeokrok.coinman.chart.volley.generic.GzipVolleyRequest;

import java.util.Map;

public class DynamicCurrencyPairsVolleyNextRequest extends GzipVolleyRequest<String> {

	public DynamicCurrencyPairsVolleyNextRequest(String url, RequestFuture<String> future) {
		super(url, future, future);
	}

	@Override
	protected String parseNetworkResponse(Map<String, String> headers, String responseString) throws Exception {
		return responseString;
	}

}
