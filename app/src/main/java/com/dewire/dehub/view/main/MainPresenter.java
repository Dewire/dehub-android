package com.dewire.dehub.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.model.GistApi;
import com.dewire.dehub.model.State;
import com.dewire.dehub.model.entity.GistEntity;
import com.dewire.dehub.view.BasePresenter;
import com.dewire.dehub.view.Navigation;
import com.dewire.dehub.view.main.view.MainContract;

import javax.inject.Inject;

/**
 * Created by kl on 28/10/16.
 */

public class MainPresenter extends BasePresenter<MainContract.View> {

  //===----------------------------------------------------------------------===//
  // Presenter contract
  //===----------------------------------------------------------------------===//

  public void onActionNewGist() {
    navigation.navigateNewGist();
  }

  public void onActionViewGist(GistEntity data) {
    navigation.navigateViewGist(data);
  }

  //===----------------------------------------------------------------------===//
  // Implementation
  //===----------------------------------------------------------------------===//

  @Inject State state;
  @Inject GistApi api;
  @Inject Navigation navigation;

  @Override
  protected void onInject(AppComponent component) {
    component.inject(this);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedState) {
    super.onCreate(savedState);

  }

  @Override
  protected void onTakeView(MainContract.View view) {
    super.onTakeView(view);
    if (!State.hasData(state.gists())) {
      spin(api.loadGists()).subscribe();
    }

    life(state.gists().subscribe(view::displayGists));
  }
}
