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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Single;

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
//        UrlBuilder.target(UrlBuilder.TARGET_SERVER_CLIEN)
//                .page(page)
//                .category(UrlBuilder.CATEGORY_COIN)
//                .queryTest(null,
//                        throwable -> Logger.d(TAG, "loadPage, e : " + throwable.getMessage()),
//                        () -> {
////                            boardRecyclerView.getAdapter().notifyDataSetChanged();
////                            swipeRefreshLayout.setRefreshing(false);
//                        });
        UrlBuilder.target(UrlBuilder.TARGET_SERVER_CLIEN, UrlBuilder.TARGET_SERVER_BULLPEN)
                .page(page)
                .category(UrlBuilder.CATEGORY_COIN)
                .query(boardDatas -> {
                            Single.just(page).filter(p -> p == 0).subscribe(integer -> {
                                Logger.d(TAG, "loadPage, refresh");
                                boardDataList.clear();
                            });
                            boardDataList.addAll(boardDatas);
                        },
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
        Single.fromCallable(() -> ((LinearLayoutManager) boardRecyclerView.getLayoutManager()))
                .filter(Objects::nonNull)
                .map(LinearLayoutManager::findFirstVisibleItemPosition)
                .map(pos -> pos > startPosition ? startPosition : pos)
                .subscribe(startPos -> {
                    boardRecyclerView.scrollToPosition(startPos);
                    boardRecyclerView.smoothScrollToPosition(0);
                });
    }
}
