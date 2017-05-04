package com.dewire.dehub.util;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by kl on 19/10/16.
 */
public class CompletionObserver implements Observer<Object> {

  public static CompletionObserver create(Action0 callback) {
    return new CompletionObserver(callback);
  }

  private final Action0 callback;

  private CompletionObserver(Action0 callback) {
    this.callback = callback;
  }

  @Override
  public void onSubscribe(@NonNull Disposable d) {

  }

  @Override
  public void onNext(Object object) {
  }

  @Override
  public void onComplete() {
    callback.call();
  }

  @Override
  public void onError(Throwable error) {
    callback.call();
  }
}
