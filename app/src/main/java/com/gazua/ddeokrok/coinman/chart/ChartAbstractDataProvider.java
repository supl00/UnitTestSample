package com.gazua.ddeokrok.coinman.chart;

public abstract class ChartAbstractDataProvider {
    public static abstract class BaseData {

        public abstract String getText();

        public abstract void setPinned(boolean pinned);

        public abstract boolean isPinned();
    }

    public static abstract class GroupData extends BaseData {
        public abstract long getGroupId();
        public abstract void setSubName(String str);
        public abstract String getSubName();
        public abstract void setResName(String str);
        public abstract String getResName();
    }

    public static abstract class ChildData extends BaseData {
        public abstract long getChildId();
    }

    public abstract int getGroupCount();
    public abstract int getChildCount(int groupPosition);

    public abstract GroupData getGroupItem(int groupPosition);
    public abstract ChildData getChildItem(int groupPosition, int childPosition);

    public abstract void moveGroupItem(int fromGroupPosition, int toGroupPosition);
    public abstract void moveChildItem(int fromGroupPosition, int fromChildPosition, int toGroupPosition, int toChildPosition);

    public abstract void removeGroupItem(int groupPosition);
    public abstract void removeChildItem(int groupPosition, int childPosition);

    public abstract long undoLastRemoval();
}
