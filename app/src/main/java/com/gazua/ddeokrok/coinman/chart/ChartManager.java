package com.gazua.ddeokrok.coinman.chart;


import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gazua.ddeokrok.coinman.R;
import com.gazua.ddeokrok.coinman.data.CoinData;
import com.gazua.ddeokrok.coinman.data.DatabaseHelper;

public class ChartManager {
    private static final String TAG = "ChartManager";

    private ChartLayoutManager mLayoutManager;
    private ChartDbManager mDbManager;
    private ChartLoader mLoader;

    private View mRootLayout;

    public ChartManager(View rootLayout, Bundle savedInstanceState) {
        mRootLayout = rootLayout;

        mLayoutManager = new ChartLayoutManager(rootLayout, savedInstanceState);
        mLayoutManager.setLayoutActionListener(() -> {
            if (mLoader != null) {
                mLoader.refresh();
            }
        });

        mDbManager = new ChartDbManager(mRootLayout.getContext());

        mLoader = new ChartLoader(mRootLayout.getContext());
        mLoader.setLoaderListener(cursor -> {
            mLayoutManager.getDataProvider().updateCursor();
            mLayoutManager.notifyItemChanged();
        }
        );
    }

    public void close() {
        if (mLayoutManager != null) {
            mLayoutManager.close();
            mLayoutManager = null;
        }
    }

    public void saveInstanceState(Bundle outState) {
        if (mLayoutManager != null) {
            mLayoutManager.saveInstanceState(outState);
        }
    }
}
