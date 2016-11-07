package com.dewire.dehub.view.viewgist.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;

import com.dewire.dehub.R;
import com.dewire.dehub.view.BaseSupportFragment;
import com.dewire.dehub.view.viewgist.ViewGistPresenter;

import nucleus.factory.RequiresPresenter;

/**
 * Created by kl on 03/11/16.
 */

@RequiresPresenter(ViewGistPresenter.class)
public class ViewGistView extends BaseSupportFragment<ViewGistPresenter>
    implements ViewGistContract.View {

  //===----------------------------------------------------------------------===//
  // View contract
  //===----------------------------------------------------------------------===//

  @Override
  public void setGistText(CharSequence text) {
    gistText.setText(text);
  }

  //===----------------------------------------------------------------------===//
  // Implementation
  //===----------------------------------------------------------------------===//

  public static final String GIST_ENTITY_KEY = "GIST_ENTITY_KEY";

  @BindView(R.id.gist_text) TextView gistText;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {

    return inflater.inflate(R.layout.fragment_view_gist, container, false);
  }
}
