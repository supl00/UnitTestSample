package com.gazua.ddeokrok.coinman.network;


import com.gazua.ddeokrok.coinman.network.page.Page;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Version;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitJsoupClient {
    private static RetrofitJsoupClient Instance = null;
    private OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(paramChain -> {
        Request localRequest = paramChain.request();
        return paramChain.proceed(localRequest.newBuilder().header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/6.4 Chrome/56.0.2924.87 Safari/537.36").method(localRequest.method(), localRequest.body()).build());
    }).readTimeout(20L, TimeUnit.SECONDS).connectTimeout(15L, TimeUnit.SECONDS).build();
    private Retrofit retrofit = new Retrofit.Builder().baseUrl("https://coinloid.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(PageAdapter.a).client(this.okHttpClient).build();

    public static RetrofitJsoupClient getInstance() {
        if (Instance == null) {
            Instance = new RetrofitJsoupClient();
        }
        return Instance;
    }

    public <T> T createApi(Class<T> paramClass) {
        return this.retrofit.create(paramClass);
    }

    public static class PageAdapter
            implements Converter<ResponseBody, Page> {
        static final Factory a = new Factory() {
            public Converter<ResponseBody, ?> responseBodyConverter(Type paramType, Annotation[] paramArrayOfAnnotation, Retrofit paramRetrofit) {
                if (paramType == Page.class) {
                    return new RetrofitJsoupClient.PageAdapter();
                }
                return null;
            }
        };

        public Page convert(ResponseBody paramResponseBody) throws IOException {
            return new Page(Jsoup.parse(paramResponseBody.string()).html());
        }
    }
}