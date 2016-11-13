package com.dewire.dehub.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.model.State;

import javax.inject.Inject;

/**
 * Created by kl on 27/10/16.
 */

public class AppPresenter extends BasePresenter<AppActivity> {

  @Inject
  State state;

  void onActionLogout() {
    state.reset();
    view().logout();
  }

  @Override
  protected void onInject(AppComponent component) {
    component.inject(this);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedState) {
    super.onCreate(savedState);
    if (savedState != null) {
      state.restoreState(savedState);
    }
  }

  @Override
  protected void onSave(Bundle state) {
    super.onSave(state);
    this.state.saveState(state);
  }
}


