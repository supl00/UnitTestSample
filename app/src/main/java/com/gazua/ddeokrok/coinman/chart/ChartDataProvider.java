package com.gazua.ddeokrok.coinman.chart;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.util.Pair;


import com.gazua.ddeokrok.coinman.data.CoinInfo;
import com.gazua.ddeokrok.coinman.data.DbSchema;
import com.gazua.ddeokrok.coinman.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChartDataProvider extends ChartAbstractDataProvider {
    private List<Pair<CoinGroupData, List<CoinChildData>>> mData;

    // for undo group item
    private Pair<CoinGroupData, List<CoinChildData>> mLastRemovedGroup;
    private int mLastRemovedGroupPosition = -1;

    // for undo child item
    private CoinChildData mLastRemovedChild;
    private long mLastRemovedChildParentGroupId = -1;
    private int mLastRemovedChildPosition = -1;

    private ChartDbManager mDbManager;
    private Context mContext;


    public ChartDataProvider(Context context, ChartDbManager dbManager) {
        mContext = context;
        mDbManager = dbManager;

        mData = new LinkedList<>();

        updateCursor();
    }

    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return mData.get(groupPosition).second.size();
    }

    @Override
    public CoinGroupData getGroupItem(int groupPosition) {
        if (groupPosition < 0 || groupPosition >= getGroupCount()) {
            throw new IndexOutOfBoundsException("groupPosition = " + groupPosition);
        }

        return mData.get(groupPosition).first;
    }

    @Override
    public CoinChildData getChildItem(int groupPosition, int childPosition) {
        if (groupPosition < 0 || groupPosition >= getGroupCount()) {
            throw new IndexOutOfBoundsException("groupPosition = " + groupPosition);
        }

        final List<CoinChildData> children = mData.get(groupPosition).second;

        if (childPosition < 0 || childPosition >= children.size()) {
            throw new IndexOutOfBoundsException("childPosition = " + childPosition);
        }

        return children.get(childPosition);
    }

    @Override
    public void moveGroupItem(int fromGroupPosition, int toGroupPosition) {
        if (fromGroupPosition == toGroupPosition) {
            return;
        }

        final Pair<CoinGroupData, List<CoinChildData>> item = mData.remove(fromGroupPosition);
        mData.add(toGroupPosition, item);
    }

    @Override
    public void moveChildItem(int fromGroupPosition, int fromChildPosition, int toGroupPosition, int toChildPosition) {
        if (fromGroupPosition != toGroupPosition || fromChildPosition == toChildPosition) {
            return;
        }

        final Pair<CoinGroupData, List<CoinChildData>> group = mData.get(fromGroupPosition);
        final ConcreteCoinChildData item = (ConcreteCoinChildData) group.second.remove(fromChildPosition);

        group.second.add(toChildPosition, item);

        if (toChildPosition == 0 || fromChildPosition == 0) {
            copyCoinData(group.first, (ConcreteCoinChildData)group.second.get(0));
        }
    }

    @Override
    public void removeGroupItem(int groupPosition) {
        mLastRemovedGroup = mData.remove(groupPosition);
        mLastRemovedGroupPosition = groupPosition;

        mLastRemovedChild = null;
        mLastRemovedChildParentGroupId = -1;
        mLastRemovedChildPosition = -1;
    }

    @Override
    public void removeChildItem(int groupPosition, int childPosition) {
        mLastRemovedChild = mData.get(groupPosition).second.remove(childPosition);
        mLastRemovedChildParentGroupId = mData.get(groupPosition).first.getGroupId();
        mLastRemovedChildPosition = childPosition;

        mLastRemovedGroup = null;
        mLastRemovedGroupPosition = -1;
    }


    @Override
    public long undoLastRemoval() {
        if (mLastRemovedGroup != null) {
            return undoGroupRemoval();
        } else if (mLastRemovedChild != null) {
            return undoChildRemoval();
        } else {
            return RecyclerViewExpandableItemManager.NO_EXPANDABLE_POSITION;
        }
    }

    public void updateCursor() {
        if (!mData.isEmpty()) {
            mData.clear();
        }

        Cursor cursor = mDbManager.getGroupItem();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                final long groupId = cursor.getInt(cursor.getColumnIndex(DbSchema.Chart.Coin.KEY_COIN_ID));
                final String groupText = cursor.getString(cursor.getColumnIndex(DbSchema.Chart.Coin.KEY_COIN_NAME));

                final ConcreteCoinGroupData group = new ConcreteCoinGroupData(groupId);
                group.setCoinName(groupText);
                group.setCoinSubName(cursor.getString(cursor.getColumnIndex(DbSchema.Chart.Coin.KEY_COIN_SUB_NAME)));
                group.setIconResId(CoinInfo.COIN.getIconResId(mContext.getResources(), groupText));

                Cursor childCursor = mDbManager.getChildItem((int)groupId);
                if (childCursor.moveToFirst()) {
                    final List<CoinChildData> children = new ArrayList<>();
                    while (!childCursor.isAfterLast()) {
                        final long childId = group.generateNewChildId();

                        int visibility = childCursor.getInt(childCursor.getColumnIndex(DbSchema.Chart.Exchange.KEY_EXCHANGE_IS_VISIBLE));
                        if (visibility != 0) {
                            String exchange = childCursor.getString(childCursor.getColumnIndex(DbSchema.Chart.Exchange.KEY_EXCHANGE_NAME));
                            String price = childCursor.getString(childCursor.getColumnIndex(DbSchema.Chart.Exchange.KEY_EXCHANGE_PRICE));
                            String diff = childCursor.getString(childCursor.getColumnIndex(DbSchema.Chart.Exchange.KEY_EXCHANGE_DIFF_PERCENT));
                            String premium = childCursor.getString(childCursor.getColumnIndex(DbSchema.Chart.Exchange.KEY_EXCHANGE_PREMIUM));
                            String currencyUnit = childCursor.getString(childCursor.getColumnIndex(DbSchema.Chart.Exchange.KEY_EXCHANGE_CURRENCY_UNIT));

                            ConcreteCoinChildData coinData = new ConcreteCoinChildData(childId);
                            coinData.setExchange(exchange);
                            coinData.setPrice(price);
                            coinData.setDiffPercent(diff);
                            coinData.setPremium(premium);
                            coinData.setCurrencyUnit(currencyUnit);

                            if (children.size() == 0) {
                                copyCoinData(group, coinData);
                            }

                            children.add(coinData);
                        }

                        childCursor.moveToNext();
                    }
                    mData.add(new Pair<>(group, children));
                }

                cursor.moveToNext();
            }
        }
    }

    private long undoGroupRemoval() {
        int insertedPosition;
        if (mLastRemovedGroupPosition >= 0 && mLastRemovedGroupPosition < mData.size()) {
            insertedPosition = mLastRemovedGroupPosition;
        } else {
            insertedPosition = mData.size();
        }

        mData.add(insertedPosition, mLastRemovedGroup);

        mLastRemovedGroup = null;
        mLastRemovedGroupPosition = -1;

        return RecyclerViewExpandableItemManager.getPackedPositionForGroup(insertedPosition);
    }

    private long undoChildRemoval() {
        Pair<CoinGroupData, List<CoinChildData>> group = null;
        int groupPosition = -1;

        // find the group
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).first.getGroupId() == mLastRemovedChildParentGroupId) {
                group = mData.get(i);
                groupPosition = i;
                break;
            }
        }

        if (group == null) {
            return RecyclerViewExpandableItemManager.NO_EXPANDABLE_POSITION;
        }

        int insertedPosition;
        if (mLastRemovedChildPosition >= 0 && mLastRemovedChildPosition < group.second.size()) {
            insertedPosition = mLastRemovedChildPosition;
        } else {
            insertedPosition = group.second.size();
        }

        group.second.add(insertedPosition, mLastRemovedChild);

        mLastRemovedChildParentGroupId = -1;
        mLastRemovedChildPosition = -1;
        mLastRemovedChild = null;

        return RecyclerViewExpandableItemManager.getPackedPositionForChild(groupPosition, insertedPosition);
    }

    private void copyCoinData(CoinGroupData dst, ConcreteCoinChildData src) {
        dst.setExchange(src.getExchange());
        dst.setPrice(src.getPrice());
        dst.setDiffPercent(src.getDiffPercent());
        dst.setCurrencyUnit(src.getCurrencyUnit());
        dst.setPremium(src.getPremium());
    }

    public static final class ConcreteCoinGroupData extends CoinGroupData {
        private final long mId;

        private String mCoinName;
        private String mCoinSubName;
        private long mNextChildId;
        private int mIconResId;
        private boolean mPinned;

        private String mMainExchange;
        private String mMainPrice;
        private String mMainDiffPercent;
        private String mMainPremium;
        private String mMainCurrencyUnit;

        ConcreteCoinGroupData(long id) {
            mId = id;
            mNextChildId = 0;
        }

        @Override
        public long getGroupId() {
            return mId;
        }

        @Override
        public void setCoinName(String str) {
            mCoinName = str;
        }

        @Override
        public String getCoinName() {
            return mCoinName;
        }

        @Override
        public void setCoinSubName(String str) {
            mCoinSubName = str;
        }

        @Override
        public String getCoinSubName() {
            return mCoinSubName;
        }

        @Override
        public void setIconResId(int id) {
            mIconResId = id;
        }

        @Override
        public int getIconResId() {
            return mIconResId;
        }

        @Override
        public void setPinned(boolean pinnedToSwipeLeft) {
            mPinned = pinnedToSwipeLeft;
        }

        @Override
        public boolean isPinned() {
            return mPinned;
        }

        public long generateNewChildId() {
            final long id = mNextChildId;
            mNextChildId += 1;
            return id;
        }

        @Override
        public void setExchange(String exchange) {
            mMainExchange = exchange;
        }

        @Override
        public String getExchange() {
            return mMainExchange;
        }

        @Override
        public void setPrice(String price) {
            mMainPrice = price;
        }

        @Override
        public String getPrice() {
            return mMainPrice;
        }

        @Override
        public void setDiffPercent(String diff) {
            mMainDiffPercent = diff;
        }

        @Override
        public String getDiffPercent() {
            return mMainDiffPercent;
        }

        @Override
        public void setPremium(String premium) {
            mMainPremium = premium;
        }

        @Override
        public String getPremium() {
            return mMainPremium;
        }

        @Override
        public void setCurrencyUnit(String currencyUnit) {
            mMainCurrencyUnit = currencyUnit;
        }

        @Override
        public String getCurrencyUnit() {
            return mMainCurrencyUnit;
        }
    }

    public static final class ConcreteCoinChildData extends CoinChildData {
        private long mId;

        private boolean mPinned;

        private String mExchange;
        private String mPrice;
        private String mDiffPercent;
        private String mPremium;
        private String mCurrencyUnit;

        ConcreteCoinChildData(long id) {
            mId = id;
        }

        @Override
        public long getChildId() {
            return mId;
        }

        @Override
        public void setExchange(String exchange) {
            mExchange = exchange;
        }

        @Override
        public String getExchange() {
            return mExchange;
        }

        @Override
        public void setPrice(String price) {
            mPrice = price;
        }

        @Override
        public String getPrice() {
            return mPrice;
        }

        @Override
        public void setDiffPercent(String diff) {
            mDiffPercent = diff;
        }

        @Override
        public String getDiffPercent() {
            return mDiffPercent;
        }

        @Override
        public void setPremium(String premium) {
            mPremium = premium;
        }

        @Override
        public String getPremium() {
            return mPremium;
        }

        @Override
        public void setCurrencyUnit(String currencyUnit) {
            mCurrencyUnit = currencyUnit;
        }

        @Override
        public String getCurrencyUnit() {
            return mCurrencyUnit;
        }

        @Override
        public void setPinned(boolean pinned) {
            mPinned = pinned;
        }

        @Override
        public boolean isPinned() {
            return mPinned;
        }

        public void setChildId(long id) {
            this.mId = id;
        }
    }
}
