package com.dewire.dehub.model;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dewire.dehub.BuildConfig;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by kl on 14/10/16.
 */

@Module
public class NetModule {

  private final String baseUrl;

  @SuppressWarnings("SameParameterValue")
  public NetModule(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  @Provides
  @Singleton
  SharedPreferences providesSharedPreferences(Application application) {
    return PreferenceManager.getDefaultSharedPreferences(application);
  }

  @Provides
  @Singleton
  Gson gson() {
    return new GsonBuilder()
        .registerTypeAdapter(ImmutableList.class, new ImmutableListDeserializer())
        .registerTypeAdapterFactory(AdapterFactory.create())
        .create();
  }

  @Provides
  @Singleton
  HttpLoggingInterceptor loggingInterceptor() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(BuildConfig.DEBUG
        ? HttpLoggingInterceptor.Level.BODY
        : HttpLoggingInterceptor.Level.NONE);
    return interceptor;
  }

  @Provides
  @Singleton
  OkHttpClient okHttpClient(State state, HttpLoggingInterceptor loggingInterceptor) {
    return new OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(chain -> chain.proceed(
            chain.request().newBuilder()
                .addHeader("Authorization", state.getBasicAuth())
                .build()))
        .build();
  }

  @Provides
  @Singleton
  Retrofit retrofit(OkHttpClient client, Gson gson) {
    return new Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();
  }
}

