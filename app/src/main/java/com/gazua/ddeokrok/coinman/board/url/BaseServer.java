package com.gazua.ddeokrok.coinman.board.url;

import android.support.annotation.NonNull;

import com.gazua.ddeokrok.coinman.board.data.BoardData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.Objects;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kimju on 2018-03-11.
 */

public abstract class BaseServer {
    private static final String TAG = "BaseServer";

    protected static final String TAG_AND = "&";

    protected int category = UrlBuilder.CATEGORY_COIN;
    protected int currentPage = 0;

    public static <T extends BaseServer> T asServer(String server) throws IllegalArgumentException {
        T baseServer;
        switch (server) {
            case UrlBuilder.TARGET_SERVER_CLIEN:
                baseServer = (T) new ClienServer();
                break;
            case UrlBuilder.TARGET_SERVER_BULLPEN:
                baseServer = (T) new BullpenServer();
                break;
            default:
                throw new IllegalArgumentException("Target server not exist!!");
        }
        return baseServer;
    }

    public abstract String baseUrl();

    public abstract String totalUrl();

    public BaseServer category(int category) {
        this.category = category;
        return this;
    }

    public BaseServer page(int page) {
        this.currentPage = page;
        return this;
    }

    public abstract String listTag();

    public abstract String pageTag();

    public abstract String categoryTag();

    public abstract String parseTitle(Element elements);

    public abstract String parseDate(Element elements);

    public abstract String parseHitsCount(Element elements);

    public abstract String parseLinkUrl(Element elements);

    public abstract String parseUserNickname(Element elements);

    public abstract String parseUserImage(Element elements);

    @NonNull
    public static Observable<BoardData> asBoardList(BaseServer server, String content) {
        return Maybe.just(content)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .filter(Objects::nonNull)
                .map(Jsoup::parse)
                .map(document -> document.select(server.listTag()))
                .flatMapObservable(Observable::fromIterable)
                .map(elements -> BoardData.asData(server.parseTitle(elements),
                        server.parseDate(elements),
                        server.parseHitsCount(elements),
                        server.parseLinkUrl(elements),
                        server.parseUserNickname(elements),
                        server.parseUserImage(elements)));
    }
}
