package com.gazua.ddeokrok.coinman.chart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gazua.ddeokrok.coinman.R;
import com.gazua.ddeokrok.coinman.model.CoinData;
import com.gazua.ddeokrok.coinman.util.CoinGenerator;
import com.gazua.ddeokrok.coinman.view.ReorderableLinearLayout;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ChartFragment extends Fragment {
    private static final String TAG = "ChartFragment";

    private static final String PRICE_STRING = "https://www.clien.net/service/api/coin/info?coinName=";
    private static final String COINPAN_PRICE_STRING = "https://coinpan.com/files/currency/coinpan.html?";
    private static final String BTC = "BTC";
    private static final String ETH = "ETH";
    private static final String XRP = "XRP";
    private static final String BCH = "BCH";
    private static final String LTC = "LTC";
    private static final String BTG = "BTG";
    private static final String ZEC = "ZEC";

    private ReorderableLinearLayout mContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_main, null);
        mContainer = view.findViewById(R.id.chart_container);

        Observable.range(0, CoinGenerator.MAX_COIN_COUNT)
                .subscribeOn(Schedulers.io())
                .map(integer -> CoinGenerator.getCoinSampleData(integer, view.getResources(), view.getContext()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coinData -> addCoin(createCoin(coinData)));

//        PageService pageService = ApiUtils.getRpJsoupService();
//
//        pageService.selectContentGetSubList(COINPAN_PRICE_STRING).enqueue(new Callback<Page>() {
//            @Override
//            public void onResponse(Call<Page> call, Response<Page> response) {
//                Logger.d(TAG, "res : " + response);
//                String html = response.body().getContent();
//                Document document = Jsoup.parse(html);
//                Elements elements = document.select("[data-cid=btc]");
//                for (Element element:elements) {
//                    if (element.attr("name_of_attribute_you want to check").equals("value_of_the_attribute")){
//                        //Save As you want to
////                        Log.d(TAG, " myHTMLResponseCallback : " +element.attr("value"));
//                    }
//                }
//
//                TextView view = getView().findViewById(R.id.btc);
//                view.setText(Html.fromHtml(response.body().getContent(), Html.FROM_HTML_MODE_LEGACY));
//            }
//
//            @Override
//            public void onFailure(Call<Page> call, Throwable t) {
//                Logger.d(TAG, "fail : " + t);
//            }
//        });
//        pageService.selectContentGetSubList(PRICE_STRING+BTC).enqueue(new Callback<Page>() {
//            @Override
//            public void onResponse(Call<Page> call, Response<Page> response) {
//                Logger.d(TAG, "res : " + response);
//                TextView view = getView().findViewById(R.id.btc);
//                view.setText(Html.fromHtml(response.body().getContent(), Html.FROM_HTML_MODE_LEGACY));
//            }
//
//            @Override
//            public void onFailure(Call<Page> call, Throwable t) {
//                Logger.d(TAG, "fail : " + t);
//            }
//        });

//        pageService.selectContentGetSubList(PRICE_STRING+ETH).enqueue(new Callback<Page>() {
//            @Override
//            public void onResponse(Call<Page> call, Response<Page> response) {
//                Logger.d(TAG, "res : " + response);
//                TextView view = getView().findViewById(R.id.eth);
//                view.setText(Html.fromHtml(response.body().getContent(), Html.FROM_HTML_MODE_LEGACY));
//            }
//
//            @Override
//            public void onFailure(Call<Page> call, Throwable t) {
//                Logger.d(TAG, "fail : " + t);
//            }
//        });
//
//        pageService.selectContentGetSubList(PRICE_STRING+XRP).enqueue(new Callback<Page>() {
//            @Override
//            public void onResponse(Call<Page> call, Response<Page> response) {
//                Logger.d(TAG, "res : " + response);
//                TextView view = getView().findViewById(R.id.xrp);
//                view.setText(Html.fromHtml(response.body().getContent(), Html.FROM_HTML_MODE_LEGACY));
//            }
//
//            @Override
//            public void onFailure(Call<Page> call, Throwable t) {
//                Logger.d(TAG, "fail : " + t);
//            }
//        });
//
//        pageService.selectContentGetSubList(PRICE_STRING+BCH).enqueue(new Callback<Page>() {
//            @Override
//            public void onResponse(Call<Page> call, Response<Page> response) {
//                Logger.d(TAG, "res : " + response);
//                TextView view = getView().findViewById(R.id.bch);
//                view.setText(Html.fromHtml(response.body().getContent(), Html.FROM_HTML_MODE_LEGACY));
//            }
//
//            @Override
//            public void onFailure(Call<Page> call, Throwable t) {
//                Logger.d(TAG, "fail : " + t);
//            }
//        });
//
//        pageService.selectContentGetSubList(PRICE_STRING+LTC).enqueue(new Callback<Page>() {
//            @Override
//            public void onResponse(Call<Page> call, Response<Page> response) {
//                Logger.d(TAG, "res : " + response);
//                TextView view = getView().findViewById(R.id.ltc);
//                view.setText(Html.fromHtml(response.body().getContent(), Html.FROM_HTML_MODE_LEGACY));
//            }
//
//            @Override
//            public void onFailure(Call<Page> call, Throwable t) {
//                Logger.d(TAG, "fail : " + t);
//            }
//        });
//
//        pageService.selectContentGetSubList(PRICE_STRING+BTG).enqueue(new Callback<Page>() {
//            @Override
//            public void onResponse(Call<Page> call, Response<Page> response) {
//                Logger.d(TAG, "res : " + response);
//                TextView view = getView().findViewById(R.id.btg);
//                view.setText(Html.fromHtml(response.body().getContent(), Html.FROM_HTML_MODE_LEGACY));
//            }
//
//            @Override
//            public void onFailure(Call<Page> call, Throwable t) {
//                Logger.d(TAG, "fail : " + t);
//            }
//        });
//
//        pageService.selectContentGetSubList(PRICE_STRING+ZEC).enqueue(new Callback<Page>() {
//            @Override
//            public void onResponse(Call<Page> call, Response<Page> response) {
//                Logger.d(TAG, "res : " + response);
//                TextView view = getView().findViewById(R.id.zec);
//                view.setText(Html.fromHtml(response.body().getContent(), Html.FROM_HTML_MODE_LEGACY));
//            }
//
//            @Override
//            public void onFailure(Call<Page> call, Throwable t) {
//                Logger.d(TAG, "fail : " + t);
//            }
//        });

        return view;
    }


//    void parsingData(Call<Page> paramAnonymousCall, Response<Page> paramAnonymousResponse) {
//        for (;;)
//        {
//            Element localElement;
//            ContentSubList localContentSubList;
//            if (paramAnonymousResponse.hasNext())
//            {
//                localElement = (Element)paramAnonymousResponse.next();
//                if (!StringUtil.isEmpty(localElement.select(".notice-text").text())) {
//                    continue;
//                }
//                localContentSubList = new ContentSubList();
//            }
//            try
//            {
//                localContentSubList.setTitle(localElement.select(".title ").text());
//                localContentSubList.setRegDate(localElement.select(".date").html());
//                localContentSubList.setHitCnt(localElement.select(".hit").html().replaceAll("���� ��", ""));
//                localContentSubList.setLinkUrl(localElement.select("a").attr("href"));
//                localContentSubList.setRegName(localElement.select(".name").text());
//                paramAnonymousCall.add(localContentSubList);
//                continue;
//                paramAnonymousResponse = Jsoup.parse(((Page)paramAnonymousResponse.body()).getContent());
//                paramAnonymousResponse = paramAnonymousResponse.select("div.contents > ul.lists > li.items");
//                paramAnonymousResponse = paramAnonymousResponse.iterator();
//                for (;;)
//                {
//                    if (paramAnonymousResponse.hasNext())
//                    {
//                        localElement = (Element)paramAnonymousResponse.next();
//                        localContentSubList = new ContentSubList();
//                    }
//                    try
//                    {
//                        localContentSubList.setTitle(localElement.select(".title").text());
//                        localContentSubList.setLinkUrl(localElement.select(".title > a").attr("href"));
//                        localContentSubList.setRegName(localElement.select(".nick").text());
//                        localContentSubList.setRegDate(localElement.select(".date").text());
//                        paramAnonymousCall.add(localContentSubList);
//                        continue;
//                        SubTabFragment.this.dataList = paramAnonymousCall;
//                        if (SubTabFragment.this.mPageCount == 1)
//                        {
//                            SubTabFragment.a(SubTabFragment.this, new ArrayList());
//                            SubTabFragment.d(SubTabFragment.this);
//                            paramAnonymousCall = SubTabFragment.this;
//                            paramAnonymousCall.mPageCount += 1;
//                            return;
//                        }
//                        SubTabFragment.e(SubTabFragment.this);
//                        paramAnonymousCall = SubTabFragment.this;
//                        paramAnonymousCall.mPageCount += 1;
//                        return;
//                    }
//                    catch (Exception localException1)
//                    {
//                        for (;;) {}
//                    }
//                }
//            }
//            catch (Exception localException2)
//            {
//                for (;;) {}
//            }
//        }
//    }

    private ViewGroup createCoin(ArrayList<CoinData> list) {
        LinearLayout coinInfo = (LinearLayout) View.inflate(getContext(), R.layout.chart_coin_info, null);
        TextView abbName = coinInfo.findViewById(R.id.coin_abb_name);
        abbName.setText(list.get(0).getAbbName());

        TextView diffView = coinInfo.findViewById(R.id.coin_represent_ratio);
        diffView.setText(String.format("%.2f", list.get(0).getDiffPercent()) + "%");

        ImageView iconView = coinInfo.findViewById(R.id.coin_icon);
        iconView.setImageDrawable(list.get(0).getDrawable());

        ReorderableLinearLayout coinDetailContainer = coinInfo.findViewById(R.id.coin_detail_container);
        int detailCount = list.size();
        for (int i = 0; i < detailCount; i++) {
            LinearLayout coinDetailLayout = (LinearLayout) View.inflate(getContext(), R.layout.chart_coin_detail, null);
            CoinData data = list.get(i);

            TextView exchangeView = coinDetailLayout.findViewById(R.id.exchange);
            exchangeView.setText(data.getExchange());

            TextView diffPercentView = coinDetailLayout.findViewById(R.id.rate);
            diffPercentView.setText(String.format("%.2f", data.getDiffPercent()) + "%");

            TextView priceView = coinDetailLayout.findViewById(R.id.coin_price);
            priceView.setText(String.valueOf(data.getPrice()));

            TextView unitView = coinDetailLayout.findViewById(R.id.coin_unit);
            unitView.setText(data.getCurrencyUnit());

            TextView premiumView = coinDetailLayout.findViewById(R.id.coin_premium);
            premiumView.setText(String.format("%.2f", data.getPremium()) + "%");

            if (i > 0) {
//                coinDetailLayout.setVisibility(View.GONE);
            }

            coinDetailContainer.addView(coinDetailLayout);
            coinDetailContainer.setViewDraggable(coinDetailLayout, coinDetailLayout.findViewById(R.id.coin_detail_thumb));
        }

        return coinInfo;
    }

    private void addCoin(ViewGroup coin) {
        mContainer.addView(coin);
        mContainer.setViewDraggable(coin, coin.findViewById(R.id.coin_info_thumb));
    }
}
