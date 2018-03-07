package com.gazua.ddeokrok.coinman.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;


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

    public long addOrUpdateCoinData(CoinData coin) {
        Log.d(TAG, "addOrUpdateCoinData() : " + coin.getName() + ", " + coin.getExchange());

        long coinId = getCoinPrimaryId(coin);
        long userId = -1;

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues coinContent = new ContentValues();
            coinContent.put(DbSchema.Chart.Coin.KEY_COIN_NAME, coin.getName());
            coinContent.put(DbSchema.Chart.Coin.KEY_COIN_ABB_NAME, coin.getAbbName());
            coinContent.put(DbSchema.Chart.Coin.KEY_COIN_ICON, coin.getIconResId());

            ContentValues exchangeContent = new ContentValues();
            exchangeContent.put(DbSchema.Chart.Exchange.KEY_EXCHANGE_NAME, coin.getExchange());
            exchangeContent.put(DbSchema.Chart.Exchange.KEY_EXCHANGE_PRICE, coin.getPrice());
            exchangeContent.put(DbSchema.Chart.Exchange.KEY_EXCHANGE_DIFF_PERCENT, coin.getDiffPercent());
            exchangeContent.put(DbSchema.Chart.Exchange.KEY_EXCHANGE_PREMIUM, coin.getPremium());
            exchangeContent.put(DbSchema.Chart.Exchange.KEY_EXCHANGE_CURRENCY_UNIT, coin.getCurrencyUnit());

            if (coinId == -1) {
                coinId = db.insertOrThrow(DbSchema.Chart.TABLE_COINS, null, coinContent);

                exchangeContent.put(DbSchema.Chart.Exchange.KEY_EXCHANGE_IS_MAIN_EXCHAGE, 1);
                exchangeContent.put(DbSchema.Chart.Exchange.KEY_EXCHANGE_FK_COIN, coinId);
                db.insertOrThrow(DbSchema.Chart.TABLE_EXCHANGES, null, exchangeContent);
                db.setTransactionSuccessful();
            } else {
                int ret = db.update(DbSchema.Chart.TABLE_EXCHANGES, exchangeContent, DbSchema.Chart.Exchange.KEY_EXCHANGE_NAME + "=? AND " +
                        DbSchema.Chart.Exchange.KEY_EXCHANGE_FK_COIN + "=?", new String[]{coin.getExchange(), Long.toString(coinId)});
                if (ret > 0) {
                    db.setTransactionSuccessful();
                } else {
                    exchangeContent.put(DbSchema.Chart.Exchange.KEY_EXCHANGE_FK_COIN, coinId);
                    userId = db.insertOrThrow(DbSchema.Chart.TABLE_EXCHANGES, null, exchangeContent);

                    db.setTransactionSuccessful();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }

        return userId;
    }

    public void deleteAll(String tableName) {
        Log.d(TAG, "deleteAll()");

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

    public Cursor fetchAll() {
        Cursor ret = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            final String queryString = "SELECT * FROM " + DbSchema.Chart.TABLE_COINS + ", " + DbSchema.Chart.TABLE_EXCHANGES + " WHERE " +
                    DbSchema.Chart.TABLE_COINS+"."+ DbSchema.Chart.Coin.KEY_COIN_ID + "=" + DbSchema.Chart.TABLE_EXCHANGES + "." + DbSchema.Chart.Exchange.KEY_EXCHANGE_FK_COIN;

            ret = db.rawQuery(queryString, null);


        } catch(Exception e) {
            Log.e(TAG, "Error while trying to fetchAll()");
        }

        return ret;
    }

    private long getCoinPrimaryId(CoinData coin) {
        Log.d(TAG, "getCoinPrimaryId()");
        long rv = -1;

        SQLiteDatabase db = getReadableDatabase();
        String[] columns = new String[] {
                DbSchema.Chart.Coin.KEY_COIN_ID
        };

        String whereclause = DbSchema.Chart.Coin.KEY_COIN_NAME + "=? AND " +
                DbSchema.Chart.Coin.KEY_COIN_ABB_NAME + "=?";

        String[] whereargs = new String[] {
                coin.getName(),
                coin.getAbbName()
        };

        try {
            Cursor cursor = db.query(DbSchema.Chart.TABLE_COINS, columns, whereclause, whereargs, null, null, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                rv = cursor.getLong(cursor.getColumnIndex(DbSchema.Chart.Coin.KEY_COIN_ID));
            }

            cursor.close();
            db.close();

        } catch (Exception e) {
            Log.e(TAG, "Error while trying to getCoinPrimaryId");

        }

        Log.d(TAG, "getCoinPrimaryId() return : " + rv);
        return rv;
    }
}
