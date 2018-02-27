package com.gazua.ddeokrok.coinman.chart;

public class ChartLoader {
    private LoaderListener mListener;

    public interface LoaderListener {
        public void onUpdate();
    }

    public ChartLoader() {
    }

    public void setLoaderListener(LoaderListener listener) {
        mListener = listener;
    }

    public void refresh() {
        if (mListener != null) {
            mListener.onUpdate();
        }
    }
}
