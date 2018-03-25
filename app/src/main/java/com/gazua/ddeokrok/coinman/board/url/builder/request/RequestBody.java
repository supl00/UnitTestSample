package com.gazua.ddeokrok.coinman.board.url.builder.request;

import android.util.Pair;
import android.widget.TextView;

import com.gazua.ddeokrok.coinman.board.url.BaseServer;
import com.gazua.ddeokrok.coinman.common.Logger;
import com.gazua.ddeokrok.coinman.network.ApiUtils;
import com.gazua.ddeokrok.coinman.network.PageService;

import org.jsoup.Jsoup;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RequestBody {
    private static final String TAG = "RequestBody";

    public interface RequestBodyListener {
        void onEnd(String body);
    }

    private static final Map<TextView, Pair<String, Disposable>> mBodyCache = new HashMap<>();
    private String link;
    private BaseServer server;
    private RequestBodyListener listener;

    public RequestBody(BaseServer server, String link) {
        this.link = link;
        this.server = server;
    }

    public RequestBody listener(RequestBodyListener listener) {
        this.listener = listener;
        return this;
    }

    public void into(TextView body) {
        if (mBodyCache.containsKey(body)) {
            if (mBodyCache.get(body).first.equals(link)) {
                return;
            }
            mBodyCache.remove(body).second.dispose();
        }
        try {
            PageService pageService = ApiUtils.getRpJsoupService();
            Disposable disposable = Single.fromCallable(() -> pageService.selectContentGetSubList(link).execute().body().getContent())
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map(Jsoup::parse)
                    .map(document -> document.select(server.bodyContentsTag()))
                    .map(elements -> elements.select(server.bodyContentsTextTag()).text())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bodyText -> {
                        body.setAlpha(0f);
                        body.setText(bodyText);
                        body.animate().setDuration(150).alpha(1f);
                        mBodyCache.remove(body);

                        if (listener != null) {
                            listener.onEnd(bodyText);
                        }
                    });
            mBodyCache.put(body, new Pair<>(link, disposable));
        } catch (Exception e) {
            Logger.e(TAG, e.getMessage());
        }
    }
}