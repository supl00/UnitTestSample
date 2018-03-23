package com.gazua.ddeokrok.coinman.chart.volley.generic;

import com.android.volley.NetworkResponse;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

import java.util.Map;

public abstract class ResponseErrorListener implements ErrorListener {

	public abstract void onErrorResponse(String url, Map<String, String> requestHeaders, NetworkResponse networkResponse, String responseString, VolleyError error);
	
	@Override
	public void onErrorResponse(VolleyError error) {
		onErrorResponse(null, null, null, null, error);
	}

}
