package com.dewire.dehub.view.creategist.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.dewire.dehub.R;
import com.dewire.dehub.view.BaseSupportFragment;
import com.dewire.dehub.view.creategist.CreateGistPresenter;

import nucleus.factory.RequiresPresenter;

/**
 * Created by kl on 01/11/16.
 */

@RequiresPresenter(CreateGistPresenter.class)
public class CreateGistView extends BaseSupportFragment<CreateGistPresenter>
    implements CreateGistContract.View {

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_create_gist, container, false);
    ((WebView)view.findViewById(R.id.web_view))
        .loadUrl("http://www.animatedgif.net/underconstruction/anim0208-1_e0.gif");
    return view;
  }
}
