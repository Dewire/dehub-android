package com.dewire.dehub.view.create_gist;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.view.BasePresenter;
import com.dewire.dehub.view.create_gist.view.CreateGistContract;
import com.dewire.dehub.view.create_gist.view.CreateGistView;

/**
 * Created by kl on 01/11/16.
 */

public class CreateGistPresenter extends BasePresenter<CreateGistContract.View> {

  @Override
  protected void onInject(AppComponent component) {
    component.inject(this);
  }
}

