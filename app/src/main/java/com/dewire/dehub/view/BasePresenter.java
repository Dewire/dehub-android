package com.dewire.dehub.view;

import static com.google.common.base.Preconditions.checkNotNull;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.util.CompletionObserver;
import com.dewire.dehub.util.ErrorIndicator;
import com.dewire.dehub.view.util.LoadingIndicator;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import nucleus.presenter.Presenter;
import io.reactivex.Observable;

public abstract class BasePresenter<V> extends Presenter<V> {

  private static final String TAG = "BasePresenter";

  @Inject
  RefWatcher refWatcher;

  public RefWatcher getRefWatcher() {
    return refWatcher;
  }

  private final CompositeDisposable disposeBag = new CompositeDisposable();
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
  protected void life(Disposable disposable) {
    disposeBag.add(disposable);
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

    if (shouldCallOnSubscribeFromOnTakeView(view)) {
      onSubscribe(getViewArguments(view));
    }
  }

  // This is needed because for some stupid reason Android calls onCreateOptionsMenu
  // after onResume. This is not good because onSubscribe likely depends on the options menu
  // being created in order to observe it. So if the view is a fragment with an options menu
  // we call onSubscribe once the menu has been created.
  private boolean shouldCallOnSubscribeFromOnTakeView(V view) {
    if (!(view instanceof BaseSupportFragment)) {
      return true;
    }

    BaseSupportFragment fragment = (BaseSupportFragment)view;
    return !fragment.hasOptionsMenu() || fragment.hasCreatedOptionsMenu();
  }

  // This is called by the BaseSupportFragment.
  final void onOptionsMenuCreated(V view) {
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
   * Publishes and subscribes to the given observable doing the equivalent of
   * error(spin(observable)).
   */
  protected <T> ObservableTransformer<T, T> spinError() {
    return observable -> {
      Observable<T> published = observable.publish().autoConnect(3);
      observeSpin(published);
      observeError(published);
      return published;
    };
  }

  /**
   * Publishes and subscribes to the given observable and calls showLoadingIndicator()
   * on the view and hideLoadingIndicator() when the observable finishes (completed or error).
   * @return the published observable
   */
  protected <T> ObservableTransformer<T, T> spin() {
    return observable -> {
      Observable<T> published = observable.publish().autoConnect(2);
      observeSpin(published);
      return published;
    };
  }

  /**
   * Publishes and subscribes to the given observable and calls the showErrorIndicator()
   * on the view if the given observable throws an error.
   * @return the published observable
   */
  protected <T> ObservableTransformer<T, T> error() {
    return observable -> {
      Observable<T> published = observable.publish().autoConnect(2);
      observeError(published);
      return published;
    };
  }

  private void observeSpin(Observable<?> observable) {
    if (!(view() instanceof LoadingIndicator)) {
      throw new RuntimeException("spin() called but view did not implement LoadingIndicator");
    }

    if (activeSpinnerRequests == 0) {
      ((LoadingIndicator)view()).showLoadingIndicator();
    }
    activeSpinnerRequests += 1;

    observable.subscribe(CompletionObserver.create(() -> {
      activeSpinnerRequests -= 1;
      if (getView() != null && activeSpinnerRequests == 0) {
        ((LoadingIndicator)getView()).hideLoadingIndicator();
      }
    }));
  }

  private void observeError(Observable<?> observable) {
    if (!(view() instanceof ErrorIndicator)) {
      throw new RuntimeException("error() called but view did not implement ErrorIndicator");
    }

    observable.subscribe(
        t -> { },
        error -> {
          if (getView() != null) {
            ((ErrorIndicator)getView()).showErrorIndicator(error);
          }
        });
  }

  /**
   * Given a Bundle returns the Parcelable for the given key.
   * @throws NullPointerException if the fragment does not have arguments or does not have a
   *     value for the key.
   * @throws ClassCastException if the value for the key was of the wrong type.
   */
  @SuppressWarnings("SameParameterValue")
  protected <T> T getParcelable(Bundle bundle, String key) {
    Object data = checkNotNull(bundle.getParcelable(key),
        "tried to get parcelable for key " + key + " but was null");

    //noinspection unchecked
    return (T)data;
  }
}
