package com.gazua.ddeokrok.coinman.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class ChartDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "ChartDatabaseHelper";

    // Database Info
    private static final String DATABASE_NAME = "coinDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_COINS = "coins";

    // Coin Table Columns
    private static final String KEY_COIN_ID = "id";
    private static final String KEY_COIN_UNIQUE_NAME = "coinUniqueName";
    private static final String KEY_COIN_NAME = "coinName";
    private static final String KEY_COIN_ABB_NAME = "coinAbbName";
    private static final String KEY_COIN_EXCHANGE = "coinExchange";
    private static final String KEY_COIN_PRICE = "coinPrice";
    private static final String KEY_COIN_DIFF_PERCENT = "coinDiffPercent";
    private static final String KEY_COIN_PREMIUM = "coinPremium";
    private static final String KEY_COIN_CURRENCY_UNIT = "coinCurrencyUnit";

    private static ChartDatabaseHelper sInstance;

    public static synchronized ChartDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ChartDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private ChartDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COIN_TABLE = "CREATE TABLE " + TABLE_COINS +
                "(" +
                KEY_COIN_ID + " INTEGER PRIMARY KEY," +
                KEY_COIN_NAME + " TEXT," +
                KEY_COIN_ABB_NAME + " TEXT," +
                KEY_COIN_EXCHANGE + " TEXT," +
                KEY_COIN_PRICE + " TEXT," +
                KEY_COIN_DIFF_PERCENT + " TEXT," +
                KEY_COIN_PREMIUM + " TEXT," +
                KEY_COIN_CURRENCY_UNIT + " TEXT," +
                ")";

        db.execSQL(CREATE_COIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_COINS);
            onCreate(db);
        }
    }

    public long updateCoinData(CoinData coin) {
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_COIN_NAME, coin.getName());
            values.put(KEY_COIN_ABB_NAME, coin.getAbbName());
            values.put(KEY_COIN_EXCHANGE, coin.getExchange());
            values.put(KEY_COIN_PRICE, coin.getPrice());
            values.put(KEY_COIN_DIFF_PERCENT, coin.getDiffPercent());
            values.put(KEY_COIN_PREMIUM, coin.getPremium());
            values.put(KEY_COIN_CURRENCY_UNIT, coin.getCurrencyUnit());

            int rows = db.update(TABLE_COINS, values, KEY_COIN_UNIQUE_NAME + "= ?", new String[]{coin.getUniqueName()});
            if (rows == 1) {
                db.setTransactionSuccessful();
            } else {
                userId = db.insertOrThrow(TABLE_COINS, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return userId;
    }

    public void deleteAllPostsAndUsers() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_COINS, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all coins");
        } finally {
            db.endTransaction();
        }
    }
}
