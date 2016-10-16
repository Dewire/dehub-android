package com.dewire.dehub.view;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.model.GistApi;
import com.dewire.dehub.model.State;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import nucleus.presenter.Presenter;

public abstract class BasePresenter<View> extends Presenter<View> {

  @Inject protected State state;
  @Inject protected GistApi api;

  private WeakReference<View> takenView = new WeakReference<>(null);

  protected abstract void onInject(AppComponent appComponent);

  @Override
  protected void onTakeView(View view) {
    super.onTakeView(view);
    if (view != takenView.get()) {
      state.invalidate();
      takenView = new WeakReference<>(view);
    }
  }
}
