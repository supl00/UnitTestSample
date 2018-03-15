package com.gazua.ddeokrok.coinman.chart;

public abstract class ChartAbstractDataProvider {
    public static abstract class BaseData {

        public abstract String getText();

        public abstract void setPinned(boolean pinned);

        public abstract boolean isPinned();
    }

    public static abstract class CoinGroupData extends BaseData {
        public abstract long getGroupId();
        public abstract void setSubName(String str);
        public abstract String getSubName();
        public abstract void setIconResId(int id);
        public abstract int getIconResId();
    }

    public static abstract class CoinChildData extends BaseData {
        public abstract long getChildId();
        public abstract void setExchange(String exchange);
        public abstract String getExchange();
        public abstract void setPrice(String price);
        public abstract String getPrice();
        public abstract void setDiffPercent(String diff);
        public abstract String getDiffPercent();
        public abstract void setPremium(String premium);
        public abstract String getPremium();
        public abstract void setCurrencyUnit(String currencyUnit);
        public abstract String getCurrencyUnit();
    }

    public abstract int getGroupCount();
    public abstract int getChildCount(int groupPosition);

    public abstract CoinGroupData getGroupItem(int groupPosition);
    public abstract CoinChildData getChildItem(int groupPosition, int childPosition);

    public abstract void moveGroupItem(int fromGroupPosition, int toGroupPosition);
    public abstract void moveChildItem(int fromGroupPosition, int fromChildPosition, int toGroupPosition, int toChildPosition);

    public abstract void removeGroupItem(int groupPosition);
    public abstract void removeChildItem(int groupPosition, int childPosition);

    public abstract long undoLastRemoval();
}
