package com.gazua.ddeokrok.coinman.chart;

import android.content.Context;
import android.database.Cursor;

import com.gazua.ddeokrok.coinman.data.CoinData;
import com.gazua.ddeokrok.coinman.data.DatabaseHelper;
import com.gazua.ddeokrok.coinman.util.CoinGenerator;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChartLoader {
    private static final String TAG = "ChartLoader";

    private Context mContext;
    private LoaderListener mListener;

    public interface LoaderListener {
        void onUpdate(Cursor cursor);
    }

    public ChartLoader(Context context) {
        mContext = context;
    }

    public void setLoaderListener(LoaderListener listener) {
        mListener = listener;
    }

    public void refresh() {
        Observable.range(0, CoinGenerator.MAX_COIN_COUNT)
                .subscribeOn(Schedulers.io())
                .map(integer -> CoinGenerator.getCoinSampleData(mContext, integer))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coinData -> addCoinList(coinData));
    }

    private void addCoinList(ArrayList<CoinData> coinLists) {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(mContext);
        for (CoinData coin : coinLists) {
            dbHelper.addOrUpdateCoinData(coin);
        }
        Cursor cursor = dbHelper.fetchAll();

        if (mListener != null) {
            mListener.onUpdate(cursor);
        }
    }
}
