package com.gazua.ddeokrok.coinman.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.gazua.ddeokrok.coinman.R;


/**
 * Created by 집 on 2018-02-11.
 */

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
    }

    public enum COIN {
        BTC {
            @Override
            public String getName() {
                return new String("비트코인");
            }

            @Override
            public String getAbbName() {
                return new String("btc");
            }

            @Override
            public Drawable getIcon(Resources res, Context context) {
                if (res == null || context == null) {
                    return null;
                }
                return res.getDrawable(R.drawable.btc2, context.getTheme());
            }
        },
        ETH {
            @Override
            public String getName() {
                return new String("이더리움");
            }

            @Override
            public String getAbbName() {
                return new String("eth");
            }

            @Override
            public Drawable getIcon(Resources res, Context context) {
                if (res == null || context == null) {
                    return null;
                }
                return res.getDrawable(R.drawable.eth2, context.getTheme());
            }
        },
        DASH {
            @Override
            public String getName() {
                return new String("대쉬");
            }

            @Override
            public String getAbbName() {
                return new String("dash");
            }

            @Override
            public Drawable getIcon(Resources res, Context context) {
                if (res == null || context == null) {
                    return null;
                }
                return res.getDrawable(R.drawable.dash, context.getTheme());
            }
        },
        LTC {
            @Override
            public String getName() {
                return new String("라이트코인");
            }

            @Override
            public String getAbbName() {
                return new String("ltc");
            }

            @Override
            public Drawable getIcon(Resources res, Context context) {
                if (res == null || context == null) {
                    return null;
                }
                return res.getDrawable(R.drawable.ltc, context.getTheme());
            }
        },
        ETC {
            @Override
            public String getName() {
                return new String("이더리움 클래식");
            }

            @Override
            public String getAbbName() {
                return new String("etc");
            }

            @Override
            public Drawable getIcon(Resources res, Context context) {
                if (res == null || context == null) {
                    return null;
                }
                return res.getDrawable(R.drawable.etc, context.getTheme());
            }
        },
        XRP {
            @Override
            public String getName() {
                return new String("리플");
            }

            @Override
            public String getAbbName() {
                return new String("xrp");
            }

            @Override
            public Drawable getIcon(Resources res, Context context) {
                if (res == null || context == null) {
                    return null;
                }
                return res.getDrawable(R.drawable.xrp, context.getTheme());
            }
        },
        BCH {
            @Override
            public String getName() {
                return new String("비트코인캐쉬");
            }

            @Override
            public String getAbbName() {
                return new String("bch");
            }

            @Override
            public Drawable getIcon(Resources res, Context context) {
                if (res == null || context == null) {
                    return null;
                }
                return res.getDrawable(R.drawable.bch, context.getTheme());
            }
        },
        XMR {
            @Override
            public String getName() {
                return new String("모네로");
            }

            @Override
            public String getAbbName() {
                return new String("xmr");
            }

            @Override
            public Drawable getIcon(Resources res, Context context) {
                if (res == null || context == null) {
                    return null;
                }
                return res.getDrawable(R.drawable.xmr, context.getTheme());
            }
        },
        QTUM {
            @Override
            public String getName() {
                return new String("퀀텀");
            }

            @Override
            public String getAbbName() {
                return new String("qtum");
            }

            @Override
            public Drawable getIcon(Resources res, Context context) {
                if (res == null || context == null) {
                    return null;
                }
                return res.getDrawable(R.drawable.qtum, context.getTheme());
            }
        },
        ZEC {
            @Override
            public String getName() {
                return new String("제트코인");
            }

            @Override
            public String getAbbName() {
                return new String("zec");
            }

            @Override
            public Drawable getIcon(Resources res, Context context) {
                if (res == null || context == null) {
                    return null;
                }
                return res.getDrawable(R.drawable.zec, context.getTheme());
            }
        },
        BTG {
            @Override
            public String getName() {
                return new String("비트코인골드");
            }

            @Override
            public String getAbbName() {
                return new String("btg");
            }

            @Override
            public Drawable getIcon(Resources res, Context context) {
                if (res == null || context == null) {
                    return null;
                }
                return res.getDrawable(R.drawable.btg, context.getTheme());
            }
        },
        EOS {
            @Override
            public String getName() {
                return new String("이오스");
            }

            @Override
            public String getAbbName() {
                return new String("eos");
            }

            @Override
            public Drawable getIcon(Resources res, Context context) {
                if (res == null || context == null) {
                    return null;
                }
                return res.getDrawable(R.drawable.eos, context.getTheme());
            }
        };

        public abstract String getName();
        public abstract String getAbbName();
        public abstract Drawable getIcon(Resources res, Context context);
    }
}
