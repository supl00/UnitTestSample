package com.gazua.ddeokrok.coinman.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    // Database Info
    private static final String DATABASE_NAME = "coin_man_database.db";
    private static final int DATABASE_VERSION = 1;

    private static DatabaseHelper sInstance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbSchema.Chart.CREATE_COIN_TABLE);
        db.execSQL(DbSchema.Chart.CREATE_EXCHANGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DbSchema.Chart.TABLE_COINS);
            db.execSQL("DROP TABLE IF EXISTS " + DbSchema.Chart.TABLE_EXCHANGES);
            onCreate(db);
        }
    }

    public long updateCoinData(CoinData coin) {
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues coinContent = new ContentValues();
            coinContent.put(DbSchema.Chart.Coin.KEY_COIN_NAME, coin.getName());
            coinContent.put(DbSchema.Chart.Coin.KEY_COIN_ABB_NAME, coin.getName());

            ContentValues exchangeContent = new ContentValues();
            exchangeContent.put(DbSchema.Chart.Exchange.KEY_EXCHANGE_NAME, coin.getExchange());
            exchangeContent.put(DbSchema.Chart.Exchange.KEY_EXCHANGE_PRICE, coin.getPrice());
            exchangeContent.put(DbSchema.Chart.Exchange.KEY_EXCHANGE_DIFF_PERCENT, coin.getDiffPercent());
            exchangeContent.put(DbSchema.Chart.Exchange.KEY_EXCHANGE_PREMIUM, coin.getPremium());
            exchangeContent.put(DbSchema.Chart.Exchange.KEY_EXCHANGE_CURRENCY_UNIT, coin.getCurrencyUnit());

            Cursor cursor = db.query(DbSchema.Chart.TABLE_COINS,
                    new String[] { DbSchema.Chart.Coin.KEY_COIN_ID}, // SELECT
                    String.format("%s= ?", DbSchema.Chart.Coin.KEY_COIN_NAME), new String[] {coin.getName()},
                    null, null, null, null);

            if (cursor != null) {

            } else {
                long row = db.insertOrThrow(DbSchema.Chart.TABLE_COINS, null, coinContent);
                exchangeContent.put(DbSchema.Chart.Exchange.KEY_EXCHANGE_FK_COIN, row);

                userId = db.insertOrThrow(DbSchema.Chart.TABLE_EXCHANGES, null, exchangeContent);
            }


            int ret = db.update(DbSchema.Chart.TABLE_COINS, coinContent, DbSchema.Chart.Coin.KEY_COIN_NAME + "= ?", new String[]{coin.getName()});
            if (ret == 1) {
                db.update(DbSchema.Chart.TABLE_EXCHANGES, exchangeContent, DbSchema.Chart.Coin.KEY_COIN_NAME + "= ?", new String[]{coin.getName()});
                db.setTransactionSuccessful();
            } else {
                userId = db.insertOrThrow(DbSchema.Chart.TABLE_COINS, null, coinContent);



                userId = db.insertOrThrow(DbSchema.Chart.TABLE_EXCHANGES, null, exchangeContent);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return userId;
    }

    public long addOrUpdateExchange(CoinData coin, long id) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues exchangeContent = new ContentValues();
            exchangeContent.put(DbSchema.Chart.Exchange.KEY_EXCHANGE_NAME, coin.getExchange());
            exchangeContent.put(DbSchema.Chart.Exchange.KEY_EXCHANGE_PRICE, coin.getPrice());
            exchangeContent.put(DbSchema.Chart.Exchange.KEY_EXCHANGE_DIFF_PERCENT, coin.getDiffPercent());
            exchangeContent.put(DbSchema.Chart.Exchange.KEY_EXCHANGE_PREMIUM, coin.getPremium());
            exchangeContent.put(DbSchema.Chart.Exchange.KEY_EXCHANGE_CURRENCY_UNIT, coin.getCurrencyUnit());

            int rows = db.update(DbSchema.Chart.TABLE_EXCHANGES, exchangeContent, DbSchema.Chart.Exchange.KEY_EXCHANGE_FK_COIN + "= ? AND " + DbSchema.Chart.Exchange.KEY_EXCHANGE_NAME + "=? ", new String[]{Long.toString(id), coin.getExchange()});
            if (rows != 1) {
                userId = db.insertOrThrow(DbSchema.Chart.TABLE_EXCHANGES, null, exchangeContent);
                db.setTransactionSuccessful();
            } else {
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return userId;
    }

    public void deleteAll(String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(tableName, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all coins");
        } finally {
            db.endTransaction();
        }
    }
}
