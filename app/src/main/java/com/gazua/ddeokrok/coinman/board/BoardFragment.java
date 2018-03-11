package com.gazua.ddeokrok.coinman.board;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gazua.ddeokrok.coinman.R;
import com.gazua.ddeokrok.coinman.board.data.BoardData;
import com.gazua.ddeokrok.coinman.board.url.UrlBuilder;
import com.gazua.ddeokrok.coinman.common.FabActionListener;
import com.gazua.ddeokrok.coinman.common.Logger;
import com.gazua.ddeokrok.coinman.network.ApiUtils;
import com.gazua.ddeokrok.coinman.network.PageService;
import com.gazua.ddeokrok.coinman.network.page.Page;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimju on 2018-02-15.
 */

public class BoardFragment extends Fragment implements FabActionListener {
    private static final String TAG = "BoardFragment";
    private static final String URI_CLIEN = "https://m.clien.net/service/board/cm_vcoin";
    private static final String URI_BULLPEN = "http://mlbpark.donga.com/mp/b.php?m=search&b=bullpen&query=%EC%BD%94%EC%9D%B8&select=sct";

    private int mPageCount = 0;
    private RecyclerView boardRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private final List<BoardData> boardDataList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_main, null);
        this.boardRecyclerView = view.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            boardDataList.clear();
            loadPage(mPageCount = 0);
        });

        this.boardRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.boardRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.boardRecyclerView.setAdapter(new BoardRecyclerViewAdapter(this.boardDataList));
        this.boardRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        this.boardRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!swipeRefreshLayout.isRefreshing() && isMaxScrollReached(recyclerView)) {
                    loadPage(++mPageCount);
                }
            }

            private boolean isMaxScrollReached(RecyclerView recyclerView) {
                int maxScroll = recyclerView.computeVerticalScrollRange();
                int currentScroll = recyclerView.computeVerticalScrollOffset() + recyclerView.computeVerticalScrollExtent();
                return currentScroll >= maxScroll * 0.7f;
            }
        });
        swipeRefreshLayout.post(() -> loadPage(mPageCount));
        return view;
    }

    public void loadPage(int page) {
        Logger.d(TAG, " loadPage - page : " + page);
        swipeRefreshLayout.setRefreshing(true);
        UrlBuilder.target(UrlBuilder.TARGET_SERVER_CLIEN)
                .page(page)
                .category(UrlBuilder.CATEGORY_COIN)
                .query(boardDataList::addAll,
                        throwable -> Logger.d(TAG, "loadPage, e : " + throwable.getMessage()),
                        () -> {
//                            boardRecyclerView.getAdapter().notifyDataSetChanged();
//                            swipeRefreshLayout.setRefreshing(false);
                        });
        UrlBuilder.target(UrlBuilder.TARGET_SERVER_BULLPEN)
                .page(page)
                .category(UrlBuilder.CATEGORY_COIN)
                .query(boardDataList::addAll,
                        throwable -> Logger.d(TAG, "loadPage, e : " + throwable.getMessage()),
                        () -> {
                            boardRecyclerView.getAdapter().notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                        });
    }

    @Override
    public void onClickFab(FloatingActionButton fab) {
        Logger.d(TAG, "onClickFab, fab : " + fab);
        final int startPosition = 30;
        Single.just(((LinearLayoutManager) boardRecyclerView.getLayoutManager()).findFirstVisibleItemPosition())
                .map(pos -> pos > startPosition ? startPosition : pos)
                .subscribe(startPos -> {
                    boardRecyclerView.scrollToPosition(startPos);
                    boardRecyclerView.smoothScrollToPosition(0);
                });
    }


//                TextView text = getView().findViewById(R.id.response);
//                text.setText(Html.fromHtml(response.body().getContent(), Html.FROM_HTML_MODE_LEGACY));
//                while (true) {
//                    Element localElement;
//                    Object localObject;
//                    response.
//                    if (response.hasNext()) {
//                        localElement = (Element) paramResponse.next();
//                        localObject = new ContentSubList();
//                    }
//                    try {
//                        ((ContentSubList) localObject).setTitle(localElement.select("strong").html());
//                        ((ContentSubList) localObject).setRegDate(localElement.select("span.b").html());
//                        ((ContentSubList) localObject).setHitCnt(localElement.select("span.hi").html());
//                        ((ContentSubList) localObject).setLinkUrl("http://m.ppomppu.co.kr/new/" + localElement.select("factory").attr("href"));
//                        ((ContentSubList) localObject).setRegName(localElement.select(".ct").html());
//                        label170:
//                        paramCall.add(localObject);
//                        continue;
//                        if (SubTabFragment.c(SubTabFragment.this).equals("클리앙"))
//                            paramResponse = Jsoup.parse(((Page) paramResponse.body()).getContent()).select("div.list_item").iterator();
//                        while (true) {
//                            if (paramResponse.hasNext()) {
//                                localElement = (Element) paramResponse.next();
//                                localObject = new ContentSubList();
//                            }
//                            try {
//                                ((ContentSubList) localObject).setTitle(localElement.select(".list_title .list_subject > span ").not(".icon_pic").last().html());
//                                ((ContentSubList) localObject).setRegDate(localElement.select(".list_time > span").html());
//                                ((ContentSubList) localObject).setHitCnt(localElement.select(".list_hit > span").html());
//                                ((ContentSubList) localObject).setLinkUrl("https://m.clien.net/" + localElement.select("factory").attr("href"));
//                                ((ContentSubList) localObject).setRegName(localElement.select(".nickname").html());
//                                label342:
//                                paramCall.add(localObject);
//                                continue;
//                                if (SubTabFragment.c(SubTabFragment.this).equals("코인톡")) {
//                                    paramResponse = Jsoup.parse(((Page) paramResponse.body()).getContent()).select("td.sbj");
//                                    DebugUtil.logWrite("seeldize     :  ", Integer.valueOf(paramResponse.size()));
//                                    paramResponse = paramResponse.iterator();
//                                }
//                                while (true) {
//                                    if (paramResponse.hasNext()) {
//                                        localElement = (Element) paramResponse.next();
//                                        localObject = new ContentSubList();
//                                    }
//                                    try {
//                                        ((ContentSubList) localObject).setTitle(localElement.select("factory").first().html());
//                                        ((ContentSubList) localObject).setLinkUrl(localElement.select("factory").first().attr("href").replaceAll("amp;", ""));
//                                        ((ContentSubList) localObject).setRegName(localElement.select("div.desc > div.dropdown > factory").not("img").text());
//                                        label494:
//                                        paramCall.add(localObject);
//                                        continue;
//                                        if (SubTabFragment.c(SubTabFragment.this).equals("코인판")) {
//                                            DebugUtil.logWrite("코인판");
//                                            paramResponse = Jsoup.parse(((Page) paramResponse.body()).getContent());
//                                            DebugUtil.logWrite(paramResponse.toString());
//                                            paramResponse = paramResponse.select("li.clearfix");
//                                            DebugUtil.logWrite("seeldize     :  ", Integer.valueOf(paramResponse.size()));
//                                            paramResponse = paramResponse.iterator();
//                                            while (paramResponse.hasNext()) {
//                                                localObject = (Element) paramResponse.next();
//                                                paramCall.add(new ContentSubList());
//                                            }
//                                        }
//                                        SubTabFragment.this.dataList = paramCall;
//                                        if (SubTabFragment.this.mPageCount == 1) {
//                                            SubTabFragment.factory(SubTabFragment.this, new ArrayList());
//                                            SubTabFragment.d(SubTabFragment.this);
//                                            paramCall = SubTabFragment.this;
//                                            paramCall.mPageCount += 1;
//                                            return;
//                                        }
//                                        SubTabFragment.e(SubTabFragment.this);
//                                        paramCall = SubTabFragment.this;
//                                        paramCall.mPageCount += 1;
//                                        return;
//                                        Toast.makeText(SubTabFragment.this.mContext, "리스트를 불러오지 못 했습니다. 관리자에게 문의해주세요.", 0);
//                                        return;
//                                    } catch (Exception localException1) {
//                                        break label494;
//                                    }
//                                }
//                            } catch (Exception localException2) {
//                                break label342;
//                            }
//                        }
//                    } catch (Exception localException3) {
//                        break label170;
//                    }
//                }
}
