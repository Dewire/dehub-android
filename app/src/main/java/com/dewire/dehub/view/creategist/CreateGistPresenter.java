package com.dewire.dehub.view.creategist;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.view.BasePresenter;
import com.dewire.dehub.view.creategist.view.CreateGistContract;

/**
 * Created by kl on 01/11/16.
 */

public class CreateGistPresenter extends BasePresenter<CreateGistContract.View> {

  @Override
  protected void onInject(AppComponent component) {
    component.inject(this);
  }
}

