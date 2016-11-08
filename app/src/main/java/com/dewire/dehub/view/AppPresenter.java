package com.dewire.dehub.view;

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
}


