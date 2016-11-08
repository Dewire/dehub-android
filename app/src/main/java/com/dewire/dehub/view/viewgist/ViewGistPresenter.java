package com.dewire.dehub.view.viewgist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.model.GistApi;
import com.dewire.dehub.model.entity.GistEntity;
import com.dewire.dehub.util.LifeObserver;
import com.dewire.dehub.view.BasePresenter;
import com.dewire.dehub.view.viewgist.view.ViewGistContract;
import com.dewire.dehub.view.viewgist.view.ViewGistView;

import javax.inject.Inject;

/**
 * Created by kl on 03/11/16.
 */

public class ViewGistPresenter extends BasePresenter<ViewGistContract> {

  @Inject GistApi api;

  @Override
  protected void onInject(AppComponent component) {
    component.inject(this);
  }

  @Override
  protected void onSubscribe(@Nullable Bundle arguments) {
    GistEntity entity = getParcelable(arguments, ViewGistView.GIST_ENTITY_KEY);
    String url = entity.file().getValue().raw_url();

    spinError(api.get(url)).subscribe(LifeObserver.create(this,
        gistText -> view().setGistText(gistText),
        error -> Log.e("VIEW GIST", "failed")));
  }
}
