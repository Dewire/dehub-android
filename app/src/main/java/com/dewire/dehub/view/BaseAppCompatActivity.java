package com.dewire.dehub.view;

import android.os.Bundle;

import nucleus.presenter.Presenter;
import nucleus.view.NucleusAppCompatActivity;

/**
 * Created by kl on 14/10/16.
 */

public abstract class BaseAppCompatActivity<P extends Presenter> extends NucleusAppCompatActivity<P> {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setPresenterFactory(InjectingReflectionPresenterFactory.fromViewClass(getClass(), this));
    super.onCreate(savedInstanceState);
  }
}
