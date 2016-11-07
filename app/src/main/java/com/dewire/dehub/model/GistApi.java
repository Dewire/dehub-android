package com.dewire.dehub.model;

import static com.google.common.base.Preconditions.checkNotNull;

import android.util.Log;

import com.dewire.dehub.model.entity.GistEntity;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.BehaviorSubject;

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

    networkObservable(observable).subscribe(
        data -> {
          stateSubject.onNext(data);
          statusSubject.onNext(null);
        },
        statusSubject::onError,
        statusSubject::onCompleted);

    return statusSubject;
  }

  /**
   * Downloads an URL. The server response content type must be text/plain.
   * @param url gets the URL.
   * @return an Observable String of the URL's body.
   */
  public Observable<String> get(String url) {
    return networkObservable(api.get(url));
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

    @GET
    Observable<String> get(@Url String url);
  }
}


