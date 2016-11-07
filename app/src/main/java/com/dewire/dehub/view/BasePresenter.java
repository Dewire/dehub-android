package com.dewire.dehub.view;

import static com.google.common.base.Preconditions.checkNotNull;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.util.CompletionObserver;
import com.dewire.dehub.view.util.LoadingIndicator;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import nucleus.presenter.Presenter;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<V> extends Presenter<V> {

  private static final String TAG = "BasePresenter";

  @Inject protected RefWatcher refWatcher;

  public RefWatcher getRefWatcher() {
    return refWatcher;
  }

  private final CompositeSubscription disposeBag = new CompositeSubscription();
  private int activeSpinnerRequests = 0;

  /**
   * The presenter must inject itself in the component provided by this method.
   * If the presenter fails to inject itself the app will crash.
   * This method is called when the fragment instance is created and before the start of
   * it's lifecycle.
   */
  protected abstract void onInject(AppComponent component);

  /**
   * Unsubscribes from the subscription in onDropView().
   */
  protected void life(Subscription subscription) {
    disposeBag.add(subscription);
  }

  @CallSuper
  @Override
  protected void onCreate(@Nullable Bundle savedState) {
    checkNotNull(refWatcher, "did you forget to properly inject the presenter in onInject()?");
  }

  @CallSuper
  @Override
  protected void onTakeView(V view) {
    Log.d(TAG, debug("onTakeView()") + " : id " + view.hashCode());
    Log.d(TAG, "PRESENTER: " + hashCode());
    onSubscribe(getViewArguments(view));
  }

  /**
   * Use this method to do RxJava subscribes. This method is called immediately after onTakeView().
   * It is safe to call view() from this method.
   * @param arguments any arguments that were supplied to the view (e.g. via setArguments() on
   *                  a fragment).
   */
  protected void onSubscribe(@Nullable Bundle arguments) {
  }

  private Bundle getViewArguments(V view) {
    if (view instanceof Fragment) {
      return ((Fragment)view).getArguments();
    }
    return null;
  }

  @CallSuper
  @Override
  protected void onDropView() {
    Log.d(TAG, debug("onDropView()"));
    disposeBag.clear();
  }

  @CallSuper
  @Override
  protected void onDestroy() {
    Log.d(TAG, debug("onDestroy() "));
    refWatcher.watch(this);
  }

  private String debug(String message) {
    return message + " : " + getClass().getSimpleName();
  }

  /**
   * Calls getView() and throws a NullPointerException is getView() returns null, else returns
   * the view returned by getView().
   */
  protected V view() {
    return checkNotNull(getView(), "getView() returned null. This method must be called"
        + "between onTakeView() and onDropView()");
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

    if (activeSpinnerRequests < 1) {
      ((LoadingIndicator)view()).showLoadingIndicator();
    }
    activeSpinnerRequests += 1;

    Observable<T> published = observable.publish().autoConnect(2);

    published.subscribe(CompletionObserver.create(() -> {
      activeSpinnerRequests -= 1;
      if (getView() ==  null || activeSpinnerRequests > 0) {
        return;
      }
      ((LoadingIndicator)getView()).hideLoadingIndicator();
    }));

    return published;
  }

  /**
   * Given a Bundle returns the Parcelable for the given key.
   * @throws NullPointerException if the fragment does not have arguments or does not have a
   *     value for the key.
   * @throws ClassCastException if the value for the key was of the wrong type.
   */
  protected <T> T getParcelable(Bundle bundle, String key) {
    Object data = checkNotNull(bundle.getParcelable(key),
        "tried to get parcelable for key " + key + " but was null");

    //noinspection unchecked
    return (T)data;
  }
}
