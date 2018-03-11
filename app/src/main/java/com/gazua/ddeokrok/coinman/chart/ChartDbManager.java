package com.gazua.ddeokrok.coinman.chart;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gazua.ddeokrok.coinman.data.DatabaseHelper;
import com.gazua.ddeokrok.coinman.data.DbSchema;

public class ChartDbManager {
    private static final String TAG = "ChartDbManager";

    private Context mContext;

    public interface DbChangeListener {
        void onUpdate(Cursor cursor);
    }

    public ChartDbManager(Context context) {
        mContext = context;
    }

    public void showAllExchange() {
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext).getWritableDatabase();

        String updateQuery ="UPDATE " + DbSchema.Chart.TABLE_EXCHANGES + " SET " + DbSchema.Chart.Exchange.KEY_EXCHANGE_IS_VISIBLE +" = 1 WHERE " + DbSchema.Chart.Exchange.KEY_EXCHANGE_IS_VISIBLE + " = 0";
        Cursor cursor = db.rawQuery(updateQuery, null);
        cursor.moveToFirst();
        cursor.close();
    }

    public void hideAllExchange() {
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext).getWritableDatabase();

        String updateQuery ="UPDATE " + DbSchema.Chart.TABLE_EXCHANGES + " SET " + DbSchema.Chart.Exchange.KEY_EXCHANGE_IS_VISIBLE +" = 0 WHERE " + DbSchema.Chart.Exchange.KEY_EXCHANGE_IS_MAIN_EXCHAGE + "= 0";
        Cursor cursor = db.rawQuery(updateQuery, null);
        cursor.moveToFirst();
        cursor.close();
    }

    public Cursor getGroupItem() {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(mContext);
        return dbHelper.getCoinItem();
    }

    public Cursor getChildItem(int key) {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(mContext);
        return dbHelper.getExchangeItem(key);
    }

    public Cursor getAllItem() {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(mContext);
        return dbHelper.fetchAll();
    }


}
