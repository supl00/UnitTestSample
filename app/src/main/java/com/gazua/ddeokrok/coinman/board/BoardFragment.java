package com.gazua.ddeokrok.coinman.board;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gazua.ddeokrok.coinman.R;
import com.gazua.ddeokrok.coinman.board.data.BoardData;
import com.gazua.ddeokrok.coinman.common.Logger;
import com.gazua.ddeokrok.coinman.network.ApiUtils;
import com.gazua.ddeokrok.coinman.network.PageService;
import com.gazua.ddeokrok.coinman.network.page.Page;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kimju on 2018-02-15.
 */

public class BoardFragment extends Fragment {
    private static final String TAG = "BoardFragment";
    private static final String URI_STRING = "https://m.clien.net/service/board/cm_vcoin";

    private RecyclerView boardRecyclerView;
    private final List<BoardData> boardDataList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_main, null);
        this.boardRecyclerView = view.findViewById(R.id.board_recycler_view);
        this.boardRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.boardRecyclerView.setAdapter(new BoardRecyclerViewAdapter(this.boardDataList));
        this.boardRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        PageService pageService = ApiUtils.getRpJsoupService();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", 1);
        paramMap.put("title", "클리앙");
        pageService.selectContentSubList(URI_STRING, paramMap).enqueue(new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {
                Logger.d(TAG, "res : " + response);
                final RecyclerView recyclerView = boardRecyclerView;
                final List<BoardData> list = boardDataList;
                Optional.ofNullable(response.body())
                        .ifPresent(page -> Jsoup.parse(page.getContent()).select("div.list_item")
                                .stream()
                                .map(localElement -> BoardData.asData(localElement.select(".list_title .list_subject > span ")
                                                .stream()
                                                .filter(element -> element.hasAttr("data-role"))
                                                .map(element -> element.html())
                                                .findFirst()
                                                .orElse(null),
                                        localElement.select(".list_time > span").html(),
                                        localElement.select(".list_hit > span").html(),
                                        "https://m.clien.net/" + localElement.select("factory").attr("href"),
                                        localElement.select(".nickname").html(),
                                        localElement.select(".nickimg > img").attr("src")))
                                .forEach(boardData -> list.add(boardData)));

                recyclerView.getAdapter().notifyDataSetChanged();
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

            @Override
            public void onFailure(Call<Page> call, Throwable t) {
                Logger.d(TAG, "fail : " + t);
            }
        });

        return view;
    }
}
