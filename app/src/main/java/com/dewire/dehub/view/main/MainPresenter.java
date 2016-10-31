package com.dewire.dehub.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.model.GistApi;
import com.dewire.dehub.model.State;
import com.dewire.dehub.view.BasePresenter;

import javax.inject.Inject;

/**
 * Created by kl on 28/10/16.
 */

public class MainPresenter extends BasePresenter<MainView> {

  @Inject State state;
  @Inject GistApi api;

  @Override
  protected void onInject(AppComponent component) {
    component.inject(this);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedState) {
    super.onCreate(savedState);

  }

  @Override
  protected void onTakeView(MainView view) {
    super.onTakeView(view);
    if (!State.hasData(state.gists())) {
      spin(api.loadGists()).subscribe();
    }

    life(state.gists().subscribe(view::displayGists));
  }


}
