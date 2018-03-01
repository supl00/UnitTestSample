package com.gazua.ddeokrok.coinman.model;


public class DbSchema {
    public class Chart {
        public static final String TABLE_COINS = "coins";
        public static final String TABLE_EXCHANGES = "exchanges";

        public static final String CREATE_COIN_TABLE = "CREATE TABLE " + TABLE_COINS +
                "(" +
                Coin.KEY_COIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                Coin.KEY_COIN_NAME + " TEXT NOT NULL, " +
                Coin.KEY_COIN_ABB_NAME + " TEXT NOT NULL, " +
                Coin.KEY_COIN_IS_VISIBLE + " TEXT NOT NULL" +
                ")";

        public static final String CREATE_EXCHANGE_TABLE = "CREATE TABLE " + TABLE_EXCHANGES +
                "(" +
                Exchange.KEY_EXCHANGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                Exchange.KEY_EXCHANGE_NAME + " TEXT NOT NULL, " +
                Exchange.KEY_EXCHANGE_PRICE + " TEXT NOT NULL, " +
                Exchange.KEY_EXCHANGE_DIFF_PERCENT + " TEXT NOT NULL, " +
                Exchange.KEY_EXCHANGE_PREMIUM + " TEXT NOT NULL, " +
                Exchange.KEY_EXCHANGE_CURRENCY_UNIT + " TEXT NOT NULL, " +
                Exchange.KEY_EXCHANGE_IS_VISIBLE + " TEXT NOT NULL, " +
                Exchange.KEY_EXCHANGE_FK_COIN + " INTEGER, " +
                " FOREIGN KEY(" + Exchange.KEY_EXCHANGE_FK_COIN + ") REFERENCES " + TABLE_COINS + "(" + Coin.KEY_COIN_ID + ")" +
                ")";

        public class Coin {
            public static final String KEY_COIN_ID = "coin_id";
            public static final String KEY_COIN_NAME = "coin_name";
            public static final String KEY_COIN_ABB_NAME = "coin_abb_name";
            public static final String KEY_COIN_IS_VISIBLE = "coin_is_visible";
        }

        public class Exchange {
            public static final String KEY_EXCHANGE_ID = "exchange_id";
            public static final String KEY_EXCHANGE_NAME = "exchange_name";
            public static final String KEY_EXCHANGE_PRICE = "exchange_price";
            public static final String KEY_EXCHANGE_DIFF_PERCENT = "exchange_diff_percent";
            public static final String KEY_EXCHANGE_PREMIUM = "exchange_premium";
            public static final String KEY_EXCHANGE_CURRENCY_UNIT = "exchange_currency_unit";
            public static final String KEY_EXCHANGE_IS_VISIBLE = "exchange_is_visible";
            public static final String KEY_EXCHANGE_FK_COIN = "fk_coin";
        }
    }
}
