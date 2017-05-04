package com.dewire.dehub.util;

import com.dewire.dehub.view.BasePresenter;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * Created by kl on 21/10/16.
 */

/**
 * An observable that keeps a (weak) reference to a BasePresenter. If the BasePresenter or its
 * view is null when the callback (onNext, onError, onCompleted) this class will not call through
 * to the its registered onNext, onError, onCompleted handlers.
 */
public class LifeObserver<T> implements Observer<T> {

  private LifeObserver() { }

  private WeakReference<BasePresenter<?>> presenterWeakRef;
  private Action1<T> onNext;
  private Action1<Throwable> onError;
  private Action0 onCompleted;

  /**
   * Calls callback on either onError or onCompleted.
   */
  public static <T> LifeObserver<T> create(
      BasePresenter<?> presenter,
      Action0 callback
  ) {
    return create(presenter, null, e -> callback.call(), callback);
  }

  @SuppressWarnings("unused")
  public static <T> LifeObserver<T> create(
      BasePresenter<?> presenter,
      Action1<T> onNext
  ) {
    return create(presenter, onNext, null, null);
  }

  public static <T> LifeObserver<T> create(
      BasePresenter<?> presenter,
      Action1<T> onNext,
      Action1<Throwable> onError
  ) {
    return create(presenter, onNext, onError, null);
  }

  /**
   * Creates a LifeObserver. All the callbacks may be null in which case they will not be called.
   */
  private static <T> LifeObserver<T> create(
      BasePresenter<?> presenter,
      Action1<T> onNext,
      Action1<Throwable> onError,
      Action0 onCompleted
  ) {
    LifeObserver<T> observer = new LifeObserver<>();
    observer.presenterWeakRef = new WeakReference<>(presenter);
    observer.onNext = onNext;
    observer.onError = onError;
    observer.onCompleted = onCompleted;
    return observer;
  }

  @Override
  public void onSubscribe(@NonNull Disposable d) {

  }

  @Override
  public void onComplete() {
    BasePresenter<?> presenter = presenterWeakRef.get();
    if (presenter == null) {
      return;
    }

    presenter.getRefWatcher().watch(this);
    if (presenter.getView() != null && onCompleted != null) {
      onCompleted.call();
    }
  }

  @Override
  public void onError(Throwable throwable) {
    BasePresenter<?> presenter = presenterWeakRef.get();
    if (presenter == null) {
      return;
    }

    presenter.getRefWatcher().watch(this);
    if (presenter.getView() != null && onError != null) {
      onError.call(throwable);
    }
  }

  @Override
  public void onNext(T value) {
    BasePresenter<?> presenter = presenterWeakRef.get();
    if (presenter == null) {
      return;
    }

    if (presenter.getView() != null && onNext != null) {
      onNext.call(value);
    }
  }
}
