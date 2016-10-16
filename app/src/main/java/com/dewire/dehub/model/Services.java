package com.dewire.dehub.model;

import com.dewire.dehub.BuildConfig;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by kl on 13/10/16.
 */

public final class Services {
  public final GistApi api;
  public final State state;

  private static String basicAuth = "";
  static void setAuth(String username, String password) {
    basicAuth = Credentials.basic(username, password);
  }

  public Services(GistApi api, State state) {
    this.api = api;
    this.state = state;
  }

  private static Interceptor loggingInterceptor() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
    return interceptor;
  }
}
