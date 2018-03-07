package com.gazua.ddeokrok.coinman.chart;


import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gazua.ddeokrok.coinman.R;

public class ChartManager {
    private ChartLayoutManager mLayoutManager;
    private ChartLoader mLoader;

    private View mRootLayout;

    public ChartManager(View rootLayout) {
        mRootLayout = rootLayout;

        mLayoutManager = new ChartLayoutManager();
        mLayoutManager.setParent(mRootLayout);

        mLoader = new ChartLoader(mRootLayout.getContext());
        mLoader.setLoaderListener(cursor -> mLayoutManager.addOrUpdateLayout(cursor));

        initTestLayout(rootLayout);
    }

    private void initTestLayout(View v) {
        Button refreshButton = v.findViewById(R.id.chart_test_refresh);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mRootLayout.getContext(), "Refresh", Toast.LENGTH_LONG).show();
                if (mLoader != null) {
                    mLoader.refresh();
                }
            }
        });

        Button editButton = v.findViewById(R.id.chart_test_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mRootLayout.getContext(), "Edit", Toast.LENGTH_LONG).show();
            }
        });

        Button deleteAllButton = v.findViewById(R.id.chart_test_delete_all);
        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mRootLayout.getContext(), "Delete All", Toast.LENGTH_LONG).show();
            }
        });
    }
}
