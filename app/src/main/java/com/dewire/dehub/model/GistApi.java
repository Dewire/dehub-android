package com.dewire.dehub.model;

import android.util.Log;

import com.dewire.dehub.model.entity.GistEntity;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
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
    return networkObservable(api.getGists()).map(v -> null);
  }

  public Observable<Void> loadGists() {
    return connect(api.getGists(), state.gists);
  }

  private <T> Observable<Void> connect(Observable<T> observable, BehaviorSubject<T> stateSubject) {
    BehaviorSubject<Void> statusSubject = BehaviorSubject.create();

    networkObservable(observable).subscribe(data -> {
          stateSubject.onNext(data);
          statusSubject.onNext(null);
        },
        statusSubject::onError,
        statusSubject::onCompleted);

    return statusSubject;
  }

  // A helper method to configure common options that should apply for all network
  // request observables.
  private <T> Observable<T> networkObservable(Observable<T> observable) {
    return observable.observeOn(AndroidSchedulers.mainThread())
        .doOnError(e -> Log.e("NetworkRequest", e.toString()));
  }

  private interface RetrofitApi {

    @GET("gists")
    Observable<List<GistEntity>> getGists();
  }
}


