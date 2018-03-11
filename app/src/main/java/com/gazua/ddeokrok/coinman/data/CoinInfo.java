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
            public String getName() {
                return new String("비트코인");
            }

            @Override
            public String getAbbName() {
                return new String("btc");
            }

            @Override
            public String getIconResName(Resources res) {
                return res.getResourceEntryName(R.drawable.btc2);
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
            public String getIconResName(Resources res) {
                return res.getResourceEntryName(R.drawable.eth2);
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
            public String getIconResName(Resources res) {
                return res.getResourceEntryName(R.drawable.dash);
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
            public String getIconResName(Resources res) {
                return res.getResourceEntryName(R.drawable.ltc);
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
            public String getIconResName(Resources res) {
                return res.getResourceEntryName(R.drawable.etc);
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
            public String getIconResName(Resources res) {
                return res.getResourceEntryName(R.drawable.xrp);
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
            public String getIconResName(Resources res) {
                return res.getResourceEntryName(R.drawable.bch);
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
            public String getIconResName(Resources res) {
                return res.getResourceEntryName(R.drawable.xmr);
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
            public String getIconResName(Resources res) {
                return res.getResourceEntryName(R.drawable.qtum);
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
            public String getIconResName(Resources res) {
                return res.getResourceEntryName(R.drawable.zec);
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
            public String getIconResName(Resources res) {
                return res.getResourceEntryName(R.drawable.btg);
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
            public String getIconResName(Resources res) {
                return res.getResourceEntryName(R.drawable.eos);
            }
        };

        public abstract String getName();
        public abstract String getAbbName();
        public abstract String getIconResName(Resources res);

        static COIN fromName(String name) {
            for (COIN coin : values()) {
                if (coin.getAbbName().compareTo(name) == 0) {
                    return coin;
                }
            }
            throw new IllegalArgumentException();
        }
    }
}
