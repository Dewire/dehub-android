package com.dewire.dehub.model;

import android.util.Log;

import com.dewire.dehub.DehubApplication;
import com.dewire.dehub.model.entity.GistEntity;
import com.google.common.base.Preconditions;
import com.ryanharter.auto.value.moshi.MoshiAdapterFactory;
import com.squareup.moshi.Moshi;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.Result;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

import static com.google.common.base.Preconditions.checkNotNull;

public class GistApi {

  private final State state;
  private final RetrofitApi api;

  GistApi(State state, Retrofit retrofit) {
    checkNotNull(state);
    checkNotNull(retrofit);

    this.state = state;
    api = retrofit.create(RetrofitApi.class);
  }

  public Observable<Void> login(String username, String password) {
    state.setBasicAuth(username, password);
    return api.getGists().observeOn(AndroidSchedulers.mainThread()).map(v -> null);
  }

  public Observable<Void> loadGists() {
    return connect(api.getGists(), state.gists);
  }

  private <T> Observable<Void> connect(Observable<T> networkObservable, BehaviorSubject<T> stateSubject) {
    BehaviorSubject<Void> statusSubject = BehaviorSubject.create();
    networkObservable.observeOn(AndroidSchedulers.mainThread())
        .subscribe(data -> {
          stateSubject.onNext(data);
          statusSubject.onNext(null);
        }, statusSubject::onError, statusSubject::onCompleted);

    return statusSubject;
  }

  private interface RetrofitApi {

    @GET("gists")
    Observable<List<GistEntity>> getGists();
  }
}


