package com.gazua.ddeokrok.coinman.chart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gazua.ddeokrok.coinman.R;


public class ChartFragment extends Fragment {
    private ChartManager mChartManager;

    public ChartFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mChartManager = new ChartManager(getView(), savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mChartManager != null) {
            mChartManager.saveInstanceState(outState);
        }
    }

    @Override
    public void onDestroyView() {
        if (mChartManager != null) {
            mChartManager.close();
        }

        super.onDestroyView();
    }
}
