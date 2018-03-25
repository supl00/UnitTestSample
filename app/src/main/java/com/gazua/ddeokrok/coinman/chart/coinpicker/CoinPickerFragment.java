package com.gazua.ddeokrok.coinman.chart.coinpicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gazua.ddeokrok.coinman.R;
import com.gazua.ddeokrok.coinman.chart.config.MarketsConfig;
import com.gazua.ddeokrok.coinman.chart.model.Market;
import com.gazua.ddeokrok.coinman.chart.util.CurrencyPairsMapHelper;
import com.gazua.ddeokrok.coinman.chart.util.MarketCurrencyPairsStore;
import com.gazua.ddeokrok.coinman.chart.util.MarketsConfigUtils;
import com.gazua.ddeokrok.coinman.widget.advscrollview.HollyViewPagerBus;
import com.gazua.ddeokrok.coinman.widget.advscrollview.ObservableScrollView;

import java.util.HashMap;


public class CoinPickerFragment extends Fragment {
    private static final String TAG = "CoinPickerFragment";

    private ObservableScrollView mScrollView;
    private TextView mMarketView;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerAdapter mAdapter;

    public static CoinPickerFragment newInstance(String title){
        Log.d(TAG, "newInstance() title = " + title);
        Bundle args = new Bundle();
        args.putString("title",title);
        CoinPickerFragment fragment = new CoinPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chart_coin_picker_fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated()");

        super.onViewCreated(view, savedInstanceState);

        mMarketView = view.findViewById(R.id.chart_coin_picker_fragment_market_name);
        mScrollView = view.findViewById(R.id.chart_coin_picker_fragment_main_scrollview);
        mRecyclerView = view.findViewById(R.id.recycler_view);


        mMarketView.setText(getArguments().getString("title"));

        HollyViewPagerBus.registerScrollView(getActivity(), mScrollView);

        final Market selectedMarket = getSelectedMarket();
        mAdapter = new RecyclerAdapter(getContext(), selectedMarket);
        mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter.notifyDataSetChanged();
    }

    private Market getSelectedMarket() {
        int size = MarketsConfig.MARKETS.size();
        return MarketsConfigUtils.getMarketByKey(mMarketView.getText().toString());
    }



}
