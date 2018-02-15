package com.gazua.ddeokrok.coinman.board;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.gazua.ddeokrok.coinman.R;
import com.gazua.ddeokrok.coinman.common.Logger;
import com.gazua.ddeokrok.coinman.network.ApiUtils;
import com.gazua.ddeokrok.coinman.network.PageService;
import com.gazua.ddeokrok.coinman.network.page.Page;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
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
 * Created by kimju on 2018-02-01.
 */

public class ClienFragment extends Fragment {
    private static final String TAG = "ClienFragment";
    private static final String URI_STRING = "https://m.clien.net/service/board/cm_vcoin";

    private OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor()
    {
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
        pageService.selectContentGetSubList(URI_STRING).enqueue(new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {
                Logger.d(TAG, "res : " + response);
                TextView text = getView().findViewById(R.id.response);
                text.setText(Html.fromHtml(response.body().getContent(), Html.FROM_HTML_MODE_LEGACY));

            }

            @Override
            public void onFailure(Call<Page> call, Throwable t) {
                Logger.d(TAG, "fail : " + t);
            }
        });

        return view;
    }
}
