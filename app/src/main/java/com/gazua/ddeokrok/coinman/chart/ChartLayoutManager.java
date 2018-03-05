package com.gazua.ddeokrok.coinman.chart;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gazua.ddeokrok.coinman.R;
import com.gazua.ddeokrok.coinman.data.CoinData;
import com.gazua.ddeokrok.coinman.data.DatabaseHelper;
import com.gazua.ddeokrok.coinman.data.DbSchema;
import com.gazua.ddeokrok.coinman.view.ReorderableLinearLayout;

import java.util.ArrayList;


public class ChartLayoutManager {
    private static final String TAG = "ChartLayoutManager";

    private ReorderableLinearLayout mCoinInfoContainer;
    private boolean mIsEditModeEnabled = true;

    public ChartLayoutManager() {
    }

    public void setParent(View parent) {
        mCoinInfoContainer = parent.findViewById(R.id.chart_container);
    }

    public void setEnableEditMode(boolean enable) {
        mIsEditModeEnabled = enable;

        if (mCoinInfoContainer != null) {
            int childCount = mCoinInfoContainer.getChildCount();
            for (int i = 0; i < childCount; i++) {
                LinearLayout coinInfo = (LinearLayout)mCoinInfoContainer.getChildAt(i);
                if (coinInfo != null) {
                    View dragButton = coinInfo.findViewById(R.id.chart_coin_content_drag);
                    dragButton.setVisibility(enable ? View.VISIBLE : View.GONE);

                    View hideButton = coinInfo.findViewById(R.id.chart_coin_content_enable_button);
                    hideButton.setVisibility(enable ? View.VISIBLE : View.GONE);

                    ReorderableLinearLayout coinDetailContainer = coinInfo.findViewById(R.id.chart_coin_content_detail_container);
                    if (coinDetailContainer != null) {
                        int detailCount = coinDetailContainer.getChildCount();
                        for (int j = 0; j < detailCount; j++) {
                            LinearLayout coinDetailLayout = (LinearLayout)coinDetailContainer.getChildAt(j);
                            View detailDragButton = coinDetailLayout.findViewById(R.id.chart_coin_detail_drag);
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

    public void addOrUpdateLayout(Cursor cursor) {
        if (mCoinInfoContainer == null) {
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(mCoinInfoContainer.getContext());

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String coinName = cursor.getString(cursor.getColumnIndex(DbSchema.Chart.Coin.KEY_COIN_NAME));
                String exchange = cursor.getString(cursor.getColumnIndex(DbSchema.Chart.Exchange.KEY_EXCHANGE_NAME));

                LinearLayout coinInfoLayout = mCoinInfoContainer.findViewWithTag(coinName);
                if (coinInfoLayout != null) {


                    LinearLayout coinDetailLayout = coinInfoLayout.findViewWithTag(exchange);
                    if (coinDetailLayout != null) {
                        updateDetailLayout(cursor, coinDetailLayout);
                    } else {
                        ReorderableLinearLayout coinDetailContainer = coinInfoLayout.findViewById(R.id.chart_coin_content_detail_container);
                        coinDetailLayout = (LinearLayout) inflater.inflate(R.layout.chart_coin_detail, null, false);
                        updateDetailLayout(cursor, coinDetailLayout);
                        coinDetailLayout.setTag(exchange);

                        coinDetailContainer.addView(coinDetailLayout);
                        coinDetailContainer.setViewDraggable(coinDetailLayout, coinDetailLayout.findViewById(R.id.chart_coin_detail_drag));
                    }
                } else {
                    LinearLayout coinInfo = (LinearLayout) inflater.inflate(R.layout.chart_coin_content, null, false);

                    TextView abbName = coinInfo.findViewById(R.id.chart_coin_content_abb_name);
                    abbName.setText(cursor.getString(cursor.getColumnIndex(DbSchema.Chart.Coin.KEY_COIN_ABB_NAME)));

                    TextView diffView = coinInfo.findViewById(R.id.chart_coin_content_diff_percent);
                    diffView.setText(String.format("%.2f", Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbSchema.Chart.Exchange.KEY_EXCHANGE_DIFF_PERCENT)))) + "%");

                    //ImageView iconView = coinInfo.findViewById(R.id.chart_coin_content_icon);
                    //iconView.setImageResource(resId);

                    TextView nameView = coinInfo.findViewById(R.id.chart_coin_content_name);
                    nameView.setText(coinName);

                    coinInfo.setTag(coinName);

                    mCoinInfoContainer.addView(coinInfo);
                    mCoinInfoContainer.setViewDraggable(coinInfo, coinInfo.findViewById(R.id.chart_coin_content_drag));

                    ReorderableLinearLayout coinDetailContainer = coinInfo.findViewById(R.id.chart_coin_content_detail_container);

                    LinearLayout coinDetailLayout = (LinearLayout) inflater.inflate(R.layout.chart_coin_detail, null, false);

                    updateDetailLayout(cursor, coinDetailLayout);
                    coinDetailLayout.setTag(exchange);

                    coinDetailContainer.addView(coinDetailLayout);
                    coinDetailContainer.setViewDraggable(coinDetailLayout, coinDetailLayout.findViewById(R.id.chart_coin_detail_drag));
                }

                cursor.moveToNext();
            }
        }

    }

    private void updateDetailLayout(Cursor cursor, ViewGroup parent) {
        TextView exchangeView = parent.findViewById(R.id.chart_coin_detail_exchange_name);
        exchangeView.setText(cursor.getString(cursor.getColumnIndex(DbSchema.Chart.Exchange.KEY_EXCHANGE_NAME)));

        TextView diffPercentView = parent.findViewById(R.id.chart_coin_detail_diff_percent);
        diffPercentView.setText(String.format("%.2f", Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbSchema.Chart.Exchange.KEY_EXCHANGE_DIFF_PERCENT)))) + "%");

        TextView priceView = parent.findViewById(R.id.chart_coin_detail_price);
        priceView.setText(cursor.getString(cursor.getColumnIndex(DbSchema.Chart.Exchange.KEY_EXCHANGE_PRICE)));

        TextView unitView = parent.findViewById(R.id.chart_coin_detail_currency_unit);
        unitView.setText(cursor.getString(cursor.getColumnIndex(DbSchema.Chart.Exchange.KEY_EXCHANGE_CURRENCY_UNIT)));

        TextView premiumView = parent.findViewById(R.id.chart_coin_detail_premium);
        premiumView.setText(String.format("%.2f", Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbSchema.Chart.Exchange.KEY_EXCHANGE_PREMIUM)))) + "%");

    }
}
