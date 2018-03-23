package com.gazua.ddeokrok.coinman.chart.volley;

import com.android.volley.toolbox.RequestFuture;
import com.gazua.ddeokrok.coinman.chart.model.CheckerInfo;
import com.gazua.ddeokrok.coinman.chart.volley.generic.GenericCheckerVolleyRequest;

import java.util.Map;

public class CheckerVolleyNextRequest extends GenericCheckerVolleyRequest<String> {
	
	public CheckerVolleyNextRequest(String url, CheckerInfo checkerInfo, RequestFuture<String> future) {
		super(url, checkerInfo, future, future);
	}

	@Override
	protected String parseNetworkResponse(Map<String, String> headers, String responseString) throws Exception {
		return responseString;
	}
}
