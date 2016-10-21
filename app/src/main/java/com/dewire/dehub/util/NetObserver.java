package com.dewire.dehub.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dewire.dehub.view.BasePresenter;

import nucleus.presenter.Presenter;
import rx.Observer;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by kl on 21/10/16.
 */

public class NetObserver<T> implements Observer<T> {

  private BasePresenter<?> presenter;
  private Action1<T> _onNext;
  private Action1<Throwable> _onError;
  private Action0 _onCompleted;

  public static <T> NetObserver<T> create(
      @NonNull BasePresenter<?> presenter,
      @NonNull Action1<T> onNext
  ) {
    return create(presenter, onNext, null, null);
  }

  public static <T> NetObserver<T> create(
      @NonNull BasePresenter<?> presenter,
      @NonNull Action1<T> onNext,
      @Nullable Action1<Throwable> onError
  ) {
    return create(presenter, onNext, onError, null);
  }

  public static <T> NetObserver<T> create(
      @NonNull BasePresenter<?> presenter,
      @NonNull Action1<T> onNext,
      @Nullable Action1<Throwable> onError,
      @Nullable Action0 onCompleted
  ) {
    NetObserver<T> observer = new NetObserver<>();
    observer.presenter = presenter;
    observer._onNext = onNext;
    observer._onError = onError;
    observer._onCompleted = onCompleted;
    return observer;
  }

  @Override
  public void onCompleted() {
    presenter.getRefWatcher().watch(this);
    if (presenter.getView() != null && _onCompleted != null) _onCompleted.call();
  }

  @Override
  public void onError(Throwable throwable) {
    presenter.getRefWatcher().watch(this);
    if (presenter.getView() != null && _onError != null) _onError.call(throwable);
  }

  @Override
  public void onNext(T t) {
    if (presenter.getView() != null) _onNext.call(t);
  }
}
