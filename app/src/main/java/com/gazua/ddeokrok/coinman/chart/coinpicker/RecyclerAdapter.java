package com.gazua.ddeokrok.coinman.chart.coinpicker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gazua.ddeokrok.coinman.R;
import com.gazua.ddeokrok.coinman.chart.model.Market;
import com.gazua.ddeokrok.coinman.chart.util.CurrencyPairsMapHelper;
import com.gazua.ddeokrok.coinman.chart.util.MarketCurrencyPairsStore;

import java.util.HashMap;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "RecyclerAdapter";

    protected static final int TYPE_HEADER = 0;
    protected static final int TYPE_CELL = 1;

    private CurrencyPairsMapHelper currencyPairsMapHelper;
    private CharSequence[] mCurrencyList;

    public RecyclerAdapter(Context context, Market market) {
        currencyPairsMapHelper = new CurrencyPairsMapHelper(MarketCurrencyPairsStore.getPairsForMarket(context, market.key));
        refreshCurrency(market);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 0: return TYPE_HEADER;
            default: return TYPE_CELL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        Log.d(TAG, "onCreateViewHolder() type : " + type);

        View view;
        switch (type){
            case TYPE_HEADER:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hvp_header_placeholder, viewGroup,false);
                break;
            default:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chart_coin_picker_currency_info, viewGroup,false);
                break;
        }

//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chart_coin_picker_currency_info, viewGroup,false);
        return new RecyclerView.ViewHolder(view) {};
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Log.d(TAG, "onBindViewHolder() position : " + position);
        if (viewHolder.getItemViewType() != TYPE_HEADER) {
            TextView v = viewHolder.itemView.findViewById(R.id.currency_name);
            v.setText(mCurrencyList[position]);
        }
    }

    @Override
    public int getItemCount() {
        if (mCurrencyList == null) {
            return 0;
        }

        return mCurrencyList.length;
    }

    private void refreshCurrency(Market market) {
        Log.d(TAG, "refreshCurrency() market = " + market.name);

        final HashMap<String, CharSequence[]> currencyPairs = getProperCurrencyPairs(market);
        if(currencyPairs!=null && currencyPairs.size()>0) {
            mCurrencyList = new CharSequence[currencyPairs.size()];
            int i=0;
            for(String currency : currencyPairs.keySet()) {
                mCurrencyList[i++] = currency;
                Log.d(TAG, "refreshCurrency + " + currency);
            }
        } else {
        }
    }

    private HashMap<String, CharSequence[]> getProperCurrencyPairs(Market market) {
        if(currencyPairsMapHelper!=null && currencyPairsMapHelper.getCurrencyPairs()!=null && currencyPairsMapHelper.getCurrencyPairs().size()>0)
            return currencyPairsMapHelper.getCurrencyPairs();
        else
            return market.currencyPairs;
    }
}
