package com.gazua.ddeokrok.coinman.board.url;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.gazua.ddeokrok.coinman.board.data.BoardData;
import com.gazua.ddeokrok.coinman.common.Logger;
import com.gazua.ddeokrok.coinman.network.ApiUtils;
import com.gazua.ddeokrok.coinman.network.PageService;
import com.gazua.ddeokrok.coinman.network.page.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by kimju on 2018-03-09.
 */

public class UrlBuilder {
    public static final String TARGET_SERVER_BULLPEN = "bullpen";
    public static final String TARGET_SERVER_CLIEN = "clien";

    public static final int CATEGORY_COIN = 1;

    private List<BaseServer> baseServers;

    private UrlBuilder(List<BaseServer> servers) {
        this.baseServers = servers;
    }

    private static UrlBuilder build(List<BaseServer> servers) {
        return new UrlBuilder(servers);
    }

    public static UrlBuilder target(@NonNull String... targetServer) {
        return build(Observable.fromIterable(Arrays.asList(targetServer))
                .map(BaseServer::asServer)
                .toList()
                .blockingGet());
    }

    public String baseUrl(@NonNull BaseServer server) {
        return server.baseUrl();
    }

    public String totalUrl(@NonNull BaseServer server) {
        return server.totalUrl();
    }

    public UrlBuilder category(int category) {
        this.baseServers.forEach(baseServer -> baseServer.category(category));
        return this;
    }

    public UrlBuilder page(int page) {
        this.baseServers.forEach(baseServer -> baseServer.page(page));
        return this;
    }

    public void query() {
        query(Functions.emptyConsumer());
    }

    public void query(@NonNull Consumer<? super List<BoardData>> onSuccess) {
        query(onSuccess, Functions.ERROR_CONSUMER);
    }

    public void query(@NonNull Consumer<? super List<BoardData>> onSuccess, @NonNull Consumer<? super Throwable> onError) {
        query(onSuccess, Functions.ERROR_CONSUMER, Functions.EMPTY_ACTION);
    }

    @VisibleForTesting
    public void queryTest(@NonNull Consumer<? super List<BoardData>> onSuccess, @NonNull Consumer<? super Throwable> onError, Action onTerminate) {
        this.baseServers.forEach(baseServer -> {
            Logger.d(TAG, " query - base : " + baseUrl(baseServer) + ", total : " + totalUrl(baseServer));
            PageService pageService = ApiUtils.getRpJsoupService();
            pageService.selectContentGetSubList(totalUrl(baseServer))
                    .enqueue(new Callback<Page>() {
                                 @Override
                                 public void onResponse(@NonNull Call<Page> call, @NonNull Response<Page> response) {
                                     Logger.d(TAG, "res : " + response);
                                     BaseServer.asBoardItems(baseServer, response.body().getContent())
                                             .observeOn(AndroidSchedulers.mainThread())
                                             .doOnError(throwable -> {
                                                 Logger.d(TAG, "onResponse, doOnError");
                                                 onError.accept(throwable);
                                             })
                                             .doOnComplete(() -> {
                                                 Logger.d(TAG, "onResponse, doOnComplete");
                                                 onSuccess.accept(null);
                                             })
                                             .doOnTerminate(() -> {
                                                 Logger.d(TAG, "onResponse, doOnTerminate");
                                                 onTerminate.run();
                                             })
                                             .forEach(element -> {
                                                 Logger.d(TAG, "title : " + element.select(".title ").text());
                                                 Logger.d(TAG, "date : " + element.select(".date").html());
//                                             Logger.d(TAG, "hit : " + element.select(".viewV").html());
                                                 Logger.d(TAG, "href : " + element.select(".title > a").attr("href"));
                                                 Logger.d(TAG, "image : " + element.select("img").attr("src"));
                                                 Logger.d(TAG, "name : " + element.select(".nick").text());
                                                 Logger.d(TAG, "reply count : " + element.select(".replycnt").text());
                                             });
                                 }

                                 @Override
                                 public void onFailure(Call<Page> call, Throwable t) {
                                     Logger.d(TAG, "onFailure : " + t);
                                     try {
                                         onError.accept(t);
                                         onTerminate.run();
                                     } catch (Throwable e) {
                                     }
                                 }
                             }
                    );
        });
    }

    public void query(@NonNull Consumer<? super List<BoardData>> onSuccess, @NonNull Consumer<? super Throwable> onError, Action onTerminate) {
        final List<BoardData> boardDataList = new ArrayList<>();
        this.baseServers.forEach(baseServer -> {
            Logger.d(TAG, " query - base : " + baseUrl(baseServer) + ", total : " + totalUrl(baseServer));
            PageService pageService = ApiUtils.getRpJsoupService();
            pageService.selectContentGetSubList(totalUrl(baseServer))
                    .enqueue(new Callback<Page>() {
                                 @Override
                                 public void onResponse(@NonNull Call<Page> call, @NonNull Response<Page> response) {
                                     Logger.d(TAG, "res : " + response);
                                     BaseServer.asBoardList(baseServer, response.body().getContent())
                                             .observeOn(AndroidSchedulers.mainThread())
                                             .doOnError(throwable -> {
                                                 Logger.d(TAG, "onResponse, doOnError");
                                                 onError.accept(throwable);
                                             })
                                             .doOnComplete(() -> {
                                                 Logger.d(TAG, "onResponse, doOnComplete");
                                                 onSuccess.accept(boardDataList);
                                             })
                                             .doOnTerminate(() -> {
                                                 Logger.d(TAG, "onResponse, doOnTerminate");
                                                 onTerminate.run();
                                             })
                                             .forEach(data -> {
                                                 Logger.d(TAG, "onResponse, data : " + data.toString());
                                                 boardDataList.add(data);
                                             });
                                 }

                                 @Override
                                 public void onFailure(Call<Page> call, Throwable t) {
                                     Logger.d(TAG, "onFailure : " + t);
                                     try {
                                         onError.accept(t);
                                         onTerminate.run();
                                     } catch (Throwable e) {
                                     }
                                 }
                             }
                    );
        });
    }
}
