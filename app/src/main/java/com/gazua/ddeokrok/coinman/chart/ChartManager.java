package com.gazua.ddeokrok.coinman.chart;


import android.database.Cursor;
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

    public ChartManager(View rootLayout) {
        mRootLayout = rootLayout;

        mLayoutManager = new ChartLayoutManager();
        mLayoutManager.setParent(mRootLayout);

        mDbManager = new ChartDbManager(mRootLayout.getContext());

        mLoader = new ChartLoader(mRootLayout.getContext());
        mLoader.setLoaderListener(cursor -> mLayoutManager.addOrUpdateLayout(cursor));

        initTestLayout(rootLayout);
    }

    private void initTestLayout(View v) {
        v.findViewById(R.id.chart_test_refresh).setOnClickListener(clickListener);
        v.findViewById(R.id.chart_test_expand).setOnClickListener(clickListener);
        v.findViewById(R.id.chart_test_collapse).setOnClickListener(clickListener);
        v.findViewById(R.id.chart_test_edit).setOnClickListener(clickListener);
        v.findViewById(R.id.chart_test_delete_all).setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            Log.d(TAG, "onClick() id : " + id);

            if (id == R.id.chart_test_refresh) {
                Toast.makeText(mRootLayout.getContext(), "Refresh", Toast.LENGTH_LONG).show();
                if (mLoader != null) {
                    mLoader.refresh();
                }

            } else if (id == R.id.chart_test_expand) {
                Toast.makeText(mRootLayout.getContext(), "Expand", Toast.LENGTH_LONG).show();
                if (mDbManager != null && mLayoutManager != null) {
                    mDbManager.showAllExchange();
                    mLayoutManager.addOrUpdateLayout(mDbManager.getAllItem());
                }
            } else if (id == R.id.chart_test_collapse) {
                Toast.makeText(mRootLayout.getContext(), "Collpase", Toast.LENGTH_LONG).show();
                if (mDbManager != null && mLayoutManager != null) {
                    mDbManager.hideAllExchange();
                    mLayoutManager.addOrUpdateLayout(mDbManager.getAllItem());
                }

            } else if (id == R.id.chart_test_edit) {
                Toast.makeText(mRootLayout.getContext(), "Edit", Toast.LENGTH_LONG).show();
                if (mLayoutManager != null) {
                    mLayoutManager.setEnableEditMode(!mLayoutManager.isEditModeEnabled());
                    mLayoutManager.addOrUpdateLayout(mDbManager.getAllItem());
                }
            } else if (id == R.id.chart_test_delete_all) {
                Toast.makeText(mRootLayout.getContext(), "Delete All", Toast.LENGTH_LONG).show();
            }
        }
    };
}
