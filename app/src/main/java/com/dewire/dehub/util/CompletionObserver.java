package com.dewire.dehub.util;

import rx.Observer;
import rx.functions.Action0;

/**
 * Created by kl on 19/10/16.
 */
public class CompletionObserver implements Observer<Object> {

  private final Action0 callback;

  public CompletionObserver(Action0 callback) {
    this.callback = callback;
  }

  @Override
  public void onNext(Object o) {
  }

  @Override
  public void onCompleted() {
    callback.call();
  }

  @Override
  public void onError(Throwable e) {
    callback.call();
  }
}
