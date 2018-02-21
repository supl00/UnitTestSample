package com.gazua.ddeokrok.coinman.board;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.gazua.ddeokrok.coinman.R;
import com.gazua.ddeokrok.coinman.common.Logger;
import com.gazua.ddeokrok.coinman.data.ContentSubList;
import com.gazua.ddeokrok.coinman.network.ApiUtils;
import com.gazua.ddeokrok.coinman.network.PageService;
import com.gazua.ddeokrok.coinman.network.page.Page;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
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
 * Created by kimju on 2018-02-01.
 */

public class ClienFragment extends Fragment {
    private static final String TAG = "ClienFragment";
    private static final String URI_STRING = "https://m.clien.net/service/board/cm_vcoin";

    private OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        public okhttp3.Response intercept(Chain paramChain) throws IOException {
            Request localRequest = paramChain.request();
            return paramChain.proceed(localRequest.newBuilder().header("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30").method(localRequest.method(), localRequest.body()).build());
        }
    }).readTimeout(20L, TimeUnit.SECONDS).connectTimeout(15L, TimeUnit.SECONDS).build();
    private Retrofit retrofit = new Retrofit.Builder().baseUrl("https://coinloid.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(PageAdapter.a)
            .client(this.okHttpClient).build();

    public static class PageAdapter
            implements Converter<ResponseBody, Page> {
        static final Factory a = new Factory() {
            public Converter<ResponseBody, ?> responseBodyConverter(Type paramType, Annotation[] paramArrayOfAnnotation, Retrofit paramRetrofit) {
                return null;
            }
        };

        public Page convert(ResponseBody paramResponseBody) throws IOException {
            return new Page(Jsoup.parse(paramResponseBody.string()).html());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_clien, null);
        PageService pageService = ApiUtils.getRpJsoupService();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", 1);
        paramMap.put("title", "클리앙");
        pageService.selectContentSubList(URI_STRING, paramMap).enqueue(new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {
                Logger.d(TAG, "res : " + response);
                Iterator<Element> elements = Jsoup.parse(((Page) response.body()).getContent()).select("div.list_item").iterator();
                while (elements.hasNext()) {
                    Element localElement = elements.next();
                    String title = localElement.select(".list_title .list_subject > span ")
                            .stream()
                            .filter(element -> element.hasAttr("data-role"))
                            .map(element -> element.html())
                            .findFirst()
                            .orElse(null);
                    String regDate = localElement.select(".list_time > span").html();
                    String hitCount = localElement.select(".list_hit > span").html();
                    String linkUrl = "https://m.clien.net/" + localElement.select("a").attr("href");
                    String regName = localElement.select(".nickname").html();
                    String nickImage = localElement.select(".nickimg > img").attr("src");
                    Logger.d(TAG, "title : " + title + ", regDate : " + regDate + ", hitCount : " + hitCount + ", linkUrl : "
                            + linkUrl + ", regName : " + regName + ", nickImage : " + nickImage);
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
//                        ((ContentSubList) localObject).setLinkUrl("http://m.ppomppu.co.kr/new/" + localElement.select("a").attr("href"));
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
//                                ((ContentSubList) localObject).setLinkUrl("https://m.clien.net/" + localElement.select("a").attr("href"));
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
//                                        ((ContentSubList) localObject).setTitle(localElement.select("a").first().html());
//                                        ((ContentSubList) localObject).setLinkUrl(localElement.select("a").first().attr("href").replaceAll("amp;", ""));
//                                        ((ContentSubList) localObject).setRegName(localElement.select("div.desc > div.dropdown > a").not("img").text());
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
//                                            SubTabFragment.a(SubTabFragment.this, new ArrayList());
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
