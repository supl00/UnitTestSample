package com.gazua.ddeokrok.coinman.board.url.builder;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.gazua.ddeokrok.coinman.board.data.BoardData;
import com.gazua.ddeokrok.coinman.board.url.builder.request.RequestBody;
import com.gazua.ddeokrok.coinman.board.url.builder.server.BaseServer;
import com.gazua.ddeokrok.coinman.common.Logger;
import com.gazua.ddeokrok.coinman.network.ApiUtils;
import com.gazua.ddeokrok.coinman.network.PageService;
import com.gazua.ddeokrok.coinman.network.page.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimju on 2018-03-09.
 */

public class UrlBuilder {
    private static final String TAG = "UrlBuilder";
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
        Observable.fromIterable(this.baseServers)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    Logger.d(TAG, "onResponse, doOnError");
                    onError.accept(throwable);
                })
                .doOnComplete(() -> {
                    Logger.d(TAG, "onResponse, doOnComplete");
//                                                 onSuccess.accept(null);
                })
                .doOnTerminate(() -> {
                    Logger.d(TAG, "onResponse, doOnTerminate");
                    onTerminate.run();
                })
                .observeOn(Schedulers.io())
                .forEach(baseServer -> {
                    Logger.d(TAG, " query - base : " + baseUrl(baseServer) + ", total : " + totalUrl(baseServer));
                    PageService pageService = ApiUtils.getRpJsoupService();
                    pageService.selectContentGetSubList(totalUrl(baseServer))
                            .enqueue(new Callback<Page>() {
                                         @Override
                                         public void onResponse(@NonNull Call<Page> call, @NonNull Response<Page> response) {
                                             Logger.d(TAG, "res : " + response);
                                             BaseServer.asBoardItems(baseServer, response.body().getContent())
                                                     .forEach(element -> {
                                                         Logger.d(TAG, "asdasd : " + element);
                                                         Logger.d(TAG, "title : " + element.select(".t_left > a").attr("title"));
                                                         Logger.d(TAG, "date : " + element.select(".date").html());
//                                             Logger.d(TAG, "hit : " + element.select(".viewV").html());
                                                         Logger.d(TAG, "href : " + element.select(".t_left > a").attr("href"));
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
        List<Observable<BoardData>> observables = new ArrayList<>();
        this.baseServers.forEach(baseServer ->
                observables.add(Observable.just(baseServer)
                        .observeOn(Schedulers.io())
                        .flatMapIterable(UrlBuilder::execute))
        );
        Observable.zip(observables, objects ->
                Observable.fromArray(objects)
                        .map(t -> (BoardData) t)
                        .forEach(data -> boardDataList.add(Math.max(0, Collections.binarySearch(boardDataList, data, Comparator.comparing(BoardData::getDate))), data)))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    Logger.d(TAG, "query, doOnError");
                    onError.accept(throwable);
                    onTerminate.run();
                })
                .doOnComplete(() -> {
                    Logger.d(TAG, "query, doOnComplete");
                    onSuccess.accept(boardDataList);
                })
                .doOnTerminate(() -> {
                    Logger.d(TAG, "query, doOnTerminate");
                    onTerminate.run();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();
    }

    public static String baseUrl(@NonNull BaseServer server) {
        return server.baseUrl();
    }

    public static String totalUrl(@NonNull BaseServer server) {
        return server.totalUrl();
    }

    public static List<BoardData> execute(BaseServer baseServer) {
        Logger.d(TAG, " query - base : " + baseUrl(baseServer) + ", total : " + totalUrl(baseServer));
        final List<BoardData> list = new ArrayList<>();
        PageService pageService = ApiUtils.getRpJsoupService();
        try {
            String contents = pageService.selectContentGetSubList(totalUrl(baseServer)).execute().body().getContent();
            BaseServer.asBoardList(baseServer, contents)
                    .forEach(data -> {
                        Logger.d(TAG, "onResponse, data : " + data.toString());
                        list.add(data);
                    });
        } catch (Exception e) {
            Logger.d(TAG, "onFailure : " + e);
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    public RequestBody loadBody(String linkUrl) {
        return new RequestBody(this.baseServers.get(0), linkUrl);
    }
}
