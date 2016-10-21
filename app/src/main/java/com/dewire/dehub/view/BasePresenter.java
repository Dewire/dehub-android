package com.dewire.dehub.view;

import android.util.Log;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.model.GistApi;
import com.dewire.dehub.model.State;
import com.dewire.dehub.util.CompletionObserver;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import nucleus.presenter.Presenter;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.*;

public abstract class BasePresenter<View> extends Presenter<View> {

  private static final String TAG = "BasePresenter";

  @Inject protected State state;
  @Inject protected GistApi api;
  @Inject protected RefWatcher refWatcher;
  public RefWatcher getRefWatcher() { return refWatcher; }

  private final CompositeSubscription disposeBag = new CompositeSubscription();
  private int activeSpinnerRequests = 0;

  protected abstract void onInject(AppComponent appComponent);

  /**
   * Unsubscribes from the subscription in onDropView().
   */
  protected void life(Subscription subscription) {
    disposeBag.add(subscription);
  }

  @Override
  protected void onTakeView(View view) {
    super.onTakeView(view);
    Log.d("Presenter", "onTakeView()");
  }

  @Override
  protected void onDropView() {
    super.onDropView();
    Log.d("Presenter", "onDropView()");
    disposeBag.clear();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d("Presenter", "onDestroy()");
    refWatcher.watch(this);
  }

  /**
   * Calls getView() and throws a NullPointerException is getView() returns null, else returns
   * the view returned by getView().
   */
  protected View view() {
    return checkNotNull(getView(), "getView() returned null. This method must be called" +
        "between onTakeView() and onDropView()");
  }

  /**
   * Calls the showLoadingIndicator() on the view and hideLoadingIndicator() when the
   * observable finishes (completed or error). This method first calls publish().autoConnect(2)
   * on the observable, subscribes to it, and then returns the ConnectableObservable.
   */
  protected <T> Observable<T> spin(Observable<T> observable) {
    if (!(view() instanceof LoadingIndicator)) {
      throw new RuntimeException("spin() called but view did not implement LoadingIndicator");
    }

    if (activeSpinnerRequests < 1) ((LoadingIndicator)view()).showLoadingIndicator();
    activeSpinnerRequests += 1;

    Observable<T> published = observable.publish().autoConnect(2);
    published.subscribe(new CompletionObserver(() -> {
      activeSpinnerRequests -= 1;
      if (getView() ==  null || activeSpinnerRequests > 0) return;
      ((LoadingIndicator)getView()).hideLoadingIndicator();
    }));

    return published;
  }
}
