package com.dewire.dehub.view.create_gist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dewire.dehub.R;
import com.dewire.dehub.view.BaseSupportFragment;

import nucleus.factory.RequiresPresenter;

/**
 * Created by kl on 01/11/16.
 */

@RequiresPresenter(CreateGistPresenter.class)
public class CreateGistView extends BaseSupportFragment<CreateGistPresenter> {

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {

    return inflater.inflate(R.layout.fragment_create_gist, container, false);
  }
}
