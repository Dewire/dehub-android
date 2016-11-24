package com.dewire.dehub.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.model.GistApi;
import com.dewire.dehub.model.State;
import com.dewire.dehub.model.entity.GistEntity;
import com.dewire.dehub.util.LifeObserver;
import com.dewire.dehub.view.BasePresenter;
import com.dewire.dehub.view.Navigation;
import com.dewire.dehub.view.main.view.MainContract;

import javax.inject.Inject;

/**
 * Created by kl on 28/10/16.
 */

public class MainPresenter extends BasePresenter<MainContract> {

  //===----------------------------------------------------------------------===//
  // Presenter contract
  //===----------------------------------------------------------------------===//

  public void onActionNewGist() {
    navigation.navigateNewGist();
  }

  public void onActionViewGist(GistEntity data) {
    navigation.navigateViewGist(data);
  }

  public void onRefresh() {
    api.loadGists()
        .compose(error())
        .subscribe(LifeObserver.create(this, () -> view().stopRefreshing()));
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
  protected void onSubscribe(@Nullable Bundle arguments) {
    if (!State.hasData(state.gists())) {
      api.loadGists().compose(spinError()).subscribe();
    }

    life(state.gists().subscribe(view()::displayGists));
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d("DEBUG", "destroying presenter: " + hashCode());
  }
}
