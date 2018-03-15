package com.gazua.ddeokrok.coinman.data;


import android.content.res.Resources;

import com.gazua.ddeokrok.coinman.R;

public class CoinInfo {
    public enum EXCHANGE {
        BITHUMB {
            @Override
            public String getName() {
                return new String("빗썸");
            }

            @Override
            public boolean isUSDUnitType() {
                return false;
            }
        },
        COINONE {
            @Override
            public String getName() {
                return new String("코인원");
            }

            @Override
            public boolean isUSDUnitType() {
                return false;
            }
        },
        KORBIT {
            @Override
            public String getName() {
                return new String("코빗");
            }

            @Override
            public boolean isUSDUnitType() {
                return false;
            }
        },
        UPBIT {
            @Override
            public String getName() {
                return new String("업비트");
            }

            @Override
            public boolean isUSDUnitType() {
                return false;
            }
        },
        COINNEST {
            @Override
            public String getName() {
                return new String("코인네스트");
            }

            @Override
            public boolean isUSDUnitType() {
                return false;
            }
        },
        POLONIEX {
            @Override
            public String getName() {
                return new String("폴로닉스");
            }

            @Override
            public boolean isUSDUnitType() {
                return true;
            }
        },
        BITREX {
            @Override
            public String getName() {
                return new String("비트렉스");
            }

            @Override
            public boolean isUSDUnitType() {
                return true;
            }
        },
        BITFINEX {
            @Override
            public String getName() {
                return new String("비트파이넥스");
            }

            @Override
            public boolean isUSDUnitType() {
                return true;
            }
        };

        public abstract String getName();
        public abstract boolean isUSDUnitType();

        static EXCHANGE fromName(String name) {
            for (EXCHANGE ex : values()) {
                if (ex.getName().compareTo(name) == 0) {
                    return ex;
                }
            }
            throw new IllegalArgumentException();
        }
    }

    public enum COIN {
        BTC {
            @Override
            public String getName(Resources res) {
                return res.getString(R.string.coin_name_btc);
            }

            @Override
            public String getSubName(Resources res) {
                return res.getString(R.string.coin_sub_name_btc);
            }

            @Override
            public int getResId() {
                return R.drawable.btc2;
            }
        },
        ETH {
            @Override
            public String getName(Resources res) {
                return res.getString(R.string.coin_name_eth);
            }

            @Override
            public String getSubName(Resources res) {
                return res.getString(R.string.coin_sub_name_eth);
            }

            @Override
            public int getResId() {
                return R.drawable.eth2;
            }
        },
        DASH {
            @Override
            public String getName(Resources res) {
                return res.getString(R.string.coin_name_dash);
            }

            @Override
            public String getSubName(Resources res) {
                return res.getString(R.string.coin_sub_name_dash);
            }

            @Override
            public int getResId() {
                return R.drawable.dash;
            }
        },
        LTC {
            @Override
            public String getName(Resources res) {
                return res.getString(R.string.coin_name_ltc);
            }

            @Override
            public String getSubName(Resources res) {
                return res.getString(R.string.coin_sub_name_ltc);
            }

            @Override
            public int getResId() {
                return R.drawable.ltc;
            }
        },
        ETC {
            @Override
            public String getName(Resources res) {
                return res.getString(R.string.coin_name_etc);
            }

            @Override
            public String getSubName(Resources res) {
                return res.getString(R.string.coin_sub_name_etc);
            }

            @Override
            public int getResId() {
                return R.drawable.etc;
            }
        },
        XRP {
            @Override
            public String getName(Resources res) {
                return res.getString(R.string.coin_name_xrp);
            }

            @Override
            public String getSubName(Resources res) {
                return res.getString(R.string.coin_sub_name_xrp);
            }

            @Override
            public int getResId() {
                return R.drawable.xrp;
            }
        },
        BCH {
            @Override
            public String getName(Resources res) {
                return res.getString(R.string.coin_name_bch);
            }

            @Override
            public String getSubName(Resources res) {
                return res.getString(R.string.coin_sub_name_bch);
            }

            @Override
            public int getResId() {
                return R.drawable.bch;
            }
        },
        XMR {
            @Override
            public String getName(Resources res) {
                return res.getString(R.string.coin_name_xmr);
            }

            @Override
            public String getSubName(Resources res) {
                return res.getString(R.string.coin_sub_name_xmr);
            }

            @Override
            public int getResId() {
                return R.drawable.xmr;
            }
        },
        QTUM {
            @Override
            public String getName(Resources res) {
                return res.getString(R.string.coin_name_qtum);
            }

            @Override
            public String getSubName(Resources res) {
                return res.getString(R.string.coin_sub_name_qtum);
            }

            @Override
            public int getResId() {
                return R.drawable.qtum;
            }
        },
        ZEC {
            @Override
            public String getName(Resources res) {
                return res.getString(R.string.coin_name_zec);
            }

            @Override
            public String getSubName(Resources res) {
                return res.getString(R.string.coin_sub_name_zec);
            }

            @Override
            public int getResId() {
                return R.drawable.zec;
            }
        },
        BTG {
            @Override
            public String getName(Resources res) {
                return res.getString(R.string.coin_name_btg);
            }

            @Override
            public String getSubName(Resources res) {
                return res.getString(R.string.coin_sub_name_btg);
            }

            @Override
            public int getResId() {
                return R.drawable.btg;
            }
        },
        EOS {
            @Override
            public String getName(Resources res) {
                return res.getString(R.string.coin_name_eos);
            }

            @Override
            public String getSubName(Resources res) {
                return res.getString(R.string.coin_sub_name_eos);
            }

            @Override
            public int getResId() {
                return R.drawable.eos;
            }
        };

        public abstract String getName(Resources res);
        public abstract String getSubName(Resources res);
        public abstract int getResId();

        public static int getIconResId(Resources res, String name) {
            for (COIN coin : values()) {
                if (coin.getName(res).compareTo(name) == 0) {
                    return coin.getResId();
                }
            }

            return 0;
        }
    }
}
