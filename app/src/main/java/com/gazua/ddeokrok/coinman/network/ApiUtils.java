package com.gazua.ddeokrok.coinman.network;

public class ApiUtils
{
  public static PageService getRpJsoupService()
  {
    return (PageService)RetrofitJsoupClient.getInstance().createApi(PageService.class);
  }
}