package com.dewire.dehub.view.view_gist;

import android.util.Log;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.model.GistApi;
import com.dewire.dehub.model.entity.GistEntity;
import com.dewire.dehub.util.NetObserver;
import com.dewire.dehub.view.BasePresenter;
import com.dewire.dehub.view.util.Views;

import javax.inject.Inject;

/**
 * Created by kl on 03/11/16.
 */

public class ViewGistPresenter extends BasePresenter<ViewGistView> {

  @Inject GistApi api;

  @Override
  protected void onInject(AppComponent component) {
    component.inject(this);
  }

  @Override
  protected void onTakeView(ViewGistView view) {
    super.onTakeView(view);

    GistEntity entity = Views.getParcelableViewArgument(view, ViewGistView.GIST_ENTITY_KEY);
    String url = entity.file().getValue().raw_url();

    spin(api.get(url)).subscribe(NetObserver.create(this, gistText -> {
      view().gistText.setText(gistText);
    }, error -> {
      Log.e("VIEW GIST", "failed");
    }));
  }
}
