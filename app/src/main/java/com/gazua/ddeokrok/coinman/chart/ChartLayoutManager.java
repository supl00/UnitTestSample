package com.gazua.ddeokrok.coinman.chart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gazua.ddeokrok.coinman.R;
import com.gazua.ddeokrok.coinman.model.CoinData;
import com.gazua.ddeokrok.coinman.view.ReorderableLinearLayout;

import java.util.ArrayList;


public class ChartLayoutManager {
    private ReorderableLinearLayout mCoinInfoContainer;
    private boolean mIsEditModeEnabled = false;

    public ChartLayoutManager(View parent) {
        mCoinInfoContainer = parent.findViewById(R.id.chart_container);
    }

    public void setEnableEditMode(boolean enable) {
        mIsEditModeEnabled = enable;

        if (mCoinInfoContainer != null) {
            int childCount = mCoinInfoContainer.getChildCount();
            for (int i = 0; i < childCount; i++) {
                LinearLayout coinInfo = (LinearLayout)mCoinInfoContainer.getChildAt(i);
                if (coinInfo != null) {
                    View dragButton = coinInfo.findViewById(R.id.coin_info_drag);
                    dragButton.setVisibility(enable ? View.VISIBLE : View.GONE);

                    View hideButton = coinInfo.findViewById(R.id.coin_info_hide_button);
                    hideButton.setVisibility(enable ? View.VISIBLE : View.GONE);

                    ReorderableLinearLayout coinDetailContainer = coinInfo.findViewById(R.id.coin_detail_container);
                    if (coinDetailContainer != null) {
                        int detailCount = coinDetailContainer.getChildCount();
                        for (int j = 0; j < detailCount; j++) {
                            LinearLayout coinDetailLayout = (LinearLayout)coinDetailContainer.getChildAt(j);
                            View detailDragButton = coinDetailLayout.findViewById(R.id.coin_detail_thumb);
                            detailDragButton.setVisibility(enable ? View.VISIBLE : View.GONE);
                        }
                    }
                }
            }
        }
    }

    public boolean isEditModeEnabled() {
        return mIsEditModeEnabled;
    }

    public ViewGroup addCoinList(ArrayList<CoinData> list) {
        LayoutInflater inflater = LayoutInflater.from(mCoinInfoContainer.getContext());
        LinearLayout coinInfo = (LinearLayout) inflater.inflate(R.layout.chart_coin_info, null, false);
        TextView abbName = coinInfo.findViewById(R.id.coin_abb_name);
        abbName.setText(list.get(0).getAbbName());

        TextView diffView = coinInfo.findViewById(R.id.coin_represent_ratio);
        diffView.setText(String.format("%.2f", list.get(0).getDiffPercent()) + "%");

        ImageView iconView = coinInfo.findViewById(R.id.coin_icon);
        iconView.setImageDrawable(list.get(0).getDrawable());

        TextView coinFullNameView = coinInfo.findViewById(R.id.coin_info_full_name);
        coinFullNameView.setText(list.get(0).getName());

        mCoinInfoContainer.addView(coinInfo);
        mCoinInfoContainer.setViewDraggable(coinInfo, coinInfo.findViewById(R.id.coin_info_drag));

        ReorderableLinearLayout coinDetailContainer = coinInfo.findViewById(R.id.coin_detail_container);
        int detailCount = list.size();
        for (int i = 0; i < detailCount; i++) {
            LinearLayout coinDetailLayout = (LinearLayout) inflater.inflate(R.layout.chart_coin_detail, null, false);
            CoinData data = list.get(i);

            TextView exchangeView = coinDetailLayout.findViewById(R.id.exchange);
            exchangeView.setText(data.getExchange());

            TextView diffPercentView = coinDetailLayout.findViewById(R.id.rate);
            diffPercentView.setText(String.format("%.2f", data.getDiffPercent()) + "%");

            TextView priceView = coinDetailLayout.findViewById(R.id.coin_price);
            priceView.setText(String.valueOf(data.getPrice()));

            TextView unitView = coinDetailLayout.findViewById(R.id.coin_unit);
            unitView.setText(data.getCurrencyUnit());

            TextView premiumView = coinDetailLayout.findViewById(R.id.coin_premium);
            premiumView.setText(String.format("%.2f", data.getPremium()) + "%");

            if (i!= 0) {
                coinDetailLayout.setVisibility(View.GONE);
            }

            coinDetailContainer.addView(coinDetailLayout);
            coinDetailContainer.setViewDraggable(coinDetailLayout, coinDetailLayout.findViewById(R.id.coin_detail_thumb));
        }

        return coinInfo;
    }
}
