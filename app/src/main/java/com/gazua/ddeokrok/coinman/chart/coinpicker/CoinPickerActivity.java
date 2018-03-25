package com.gazua.ddeokrok.coinman.chart.coinpicker;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.gazua.ddeokrok.coinman.R;
import com.gazua.ddeokrok.coinman.chart.config.MarketsConfig;
import com.gazua.ddeokrok.coinman.chart.model.Market;
import com.gazua.ddeokrok.coinman.chart.util.CurrencyPairsMapHelper;
import com.gazua.ddeokrok.coinman.chart.util.MarketsConfigUtils;
import com.gazua.ddeokrok.coinman.widget.advscrollview.HollyViewPager;
import com.gazua.ddeokrok.coinman.widget.advscrollview.HollyViewPagerConfigurator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CoinPickerActivity extends AppCompatActivity {
    private CurrencyPairsMapHelper currencyPairsMapHelper;

    int mMarketCount = MarketsConfig.MARKETS.size();
    HollyViewPager hollyViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_coin_picker_activity);

        hollyViewPager = findViewById(R.id.hollyViewPager);
        hollyViewPager.getViewPager().setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));
        hollyViewPager.setConfigurator(new HollyViewPagerConfigurator() {
            @Override
            public float getHeightPercentForPage(int page) {
                return ((page+4)%10)/10f;
            }
        });

        hollyViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                //if(position%2==0)
                //    return new RecyclerViewFragment();
                //else
                return CoinPickerFragment.newInstance((String) getPageTitle(position));
            }

            @Override
            public int getCount() {
                return mMarketCount;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return MarketsConfigUtils.getMarketById(position).name;
            }
        });

//        rv = (RecyclerView) findViewById(R.id.rv);
//
//        PickerLayoutManager pickerLayoutManager = new PickerLayoutManager(this, PickerLayoutManager.HORIZONTAL, false);
//        pickerLayoutManager.setChangeAlpha(false);
//        pickerLayoutManager.setScaleDownBy(0.7f);
//        pickerLayoutManager.setScaleDownDistance(0.8f);
//
//        adapter = new CoinPickerAdapter(this, getData(), rv);
//        SnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(rv);
//        rv.setLayoutManager(pickerLayoutManager);
//        rv.setAdapter(adapter);
//
//        pickerLayoutManager.setOnScrollStopListener(new PickerLayoutManager.onScrollStopListener() {
//            @Override
//            public void selectedView(View view) {
//                Market selectedMarket = MarketsConfigUtils.getMarketByKey(((TextView) view).getText().toString());
//
//                Toast.makeText(CoinPickerActivity.this, ("Selected value : "+((TextView) view).getText().toString()), Toast.LENGTH_SHORT).show();
//                currencyPairsMapHelper = new CurrencyPairsMapHelper(MarketCurrencyPairsStore.getPairsForMarket(CoinPickerActivity.this, selectedMarket.key));
//                refreshCurrency(selectedMarket);
////                refreshFuturesContractTypeSpinner(selectedMarket);
//            }
//        });

//        currencyPairsMapHelper = new CurrencyPairsMapHelper(MarketCurrencyPairsStore.getPairsForMarket(this, getSelectedMarket().key));
    }

    public List<Market> getData() {
        List<Market> data = new ArrayList<>();

        for(Market market : MarketsConfig.MARKETS.values()) {
            data.add(market);
        }
        return data;
    }

//    private Market getSelectedMarket() {
//        int size = MarketsConfig.MARKETS.size();
//        int idx = (size - 1) - marketSpinner.getSelectedItemPosition();
//        return MarketsConfigUtils.getMarketById(idx);
//    }
//
//    private String getSelectedCurrencyBase() {
//        if(currencyBaseSpinner.getAdapter()==null)
//            return null;
//        return String.valueOf(currencyBaseSpinner.getSelectedItem());
//    }
//
//    private String getSelectedCurrencyCounter() {
//        if(currencyCounterSpinner.getAdapter()==null)
//            return null;
//        return String.valueOf(currencyCounterSpinner.getSelectedItem());
//    }
//
//    private int getSelectedContractType(Market market) {
//        if (market instanceof FuturesMarket) {
//            final FuturesMarket futuresMarket = (FuturesMarket) market;
//            int selection = futuresContractTypeSpinner.getSelectedItemPosition();
//            return futuresMarket.contractTypes[selection];
//        }
//        return Futures.CONTRACT_TYPE_WEEKLY;
//    }

    private void refreshCurrency(Market market) {
        final HashMap<String, CharSequence[]> currencyPairs = getProperCurrencyPairs(market);
        if(currencyPairs!=null && currencyPairs.size()>0) {
            final CharSequence[] entries = new CharSequence[currencyPairs.size()];
            int i=0;
            for(String currency : currencyPairs.keySet()) {
                entries[i++] = currency;
            }
        }
    }

//    private void refreshCurrencyCounterSpinner(Market market) {
//        final HashMap<String, CharSequence[]> currencyPairs = getProperCurrencyPairs(market);
//        if(currencyPairs!=null && currencyPairs.size()>0) {
//            final String selectedCurrencyBase = getSelectedCurrencyBase();
//            final CharSequence[] entries = currencyPairs.get(selectedCurrencyBase).clone();
////            currencyCounterSpinner.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, entries));
//        } else {
////            currencyCounterSpinner.setAdapter(null);
//        }
//    }

    private HashMap<String, CharSequence[]> getProperCurrencyPairs(Market market) {
        if(currencyPairsMapHelper!=null && currencyPairsMapHelper.getCurrencyPairs()!=null && currencyPairsMapHelper.getCurrencyPairs().size()>0)
            return currencyPairsMapHelper.getCurrencyPairs();
        else
            return market.currencyPairs;
    }

}
