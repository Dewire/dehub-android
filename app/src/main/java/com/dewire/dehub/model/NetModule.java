package com.dewire.dehub.model;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dewire.dehub.BuildConfig;
import com.dewire.dehub.model.AdapterFactory;
import com.dewire.dehub.model.State;
import com.squareup.moshi.Moshi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by kl on 14/10/16.
 */

@Module
public class NetModule {

  private String baseUrl;

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
  Moshi moshi() {
    return new Moshi.Builder()
        .add(AdapterFactory.create())
        .build();
  }

  @Provides
  @Singleton
  HttpLoggingInterceptor loggingInterceptor() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(BuildConfig.DEBUG ?
        HttpLoggingInterceptor.Level.BODY :
        HttpLoggingInterceptor.Level.NONE);
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
  Retrofit retrofit(State state, OkHttpClient client, Moshi moshi) {
    return new Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build();
  }
}

