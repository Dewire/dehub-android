package com.dewire.dehub.view.view_gist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.model.GistApi;
import com.dewire.dehub.model.entity.GistEntity;
import com.dewire.dehub.util.NetObserver;
import com.dewire.dehub.view.BasePresenter;
import com.dewire.dehub.view.view_gist.view.ViewGistContract;
import com.dewire.dehub.view.view_gist.view.ViewGistView;

import javax.inject.Inject;

/**
 * Created by kl on 03/11/16.
 */

public class ViewGistPresenter extends BasePresenter<ViewGistContract.View> {

  @Inject GistApi api;

  @Override
  protected void onInject(AppComponent component) {
    component.inject(this);
  }

  @Override
  protected void onSubscribe(@Nullable Bundle arguments) {
    GistEntity entity = getParcelable(arguments, ViewGistView.GIST_ENTITY_KEY);
    String url = entity.file().getValue().raw_url();

    spin(api.get(url)).subscribe(NetObserver.create(this, gistText -> {
      view().setGistText(gistText);
    }, error -> {
      Log.e("VIEW GIST", "failed");
    }));
  }
}
