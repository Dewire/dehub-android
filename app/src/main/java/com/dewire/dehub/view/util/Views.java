package com.dewire.dehub.view.util;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by kl on 28/10/16.
 */

public final class Views {
  private Views() {}

  public static ProgressBar loadingIndicator(Context context) {
    ProgressBar i = new ProgressBar(context);
    i.setIndeterminate(true);
    i.setVisibility(View.GONE);
    return i;
  }

  /**
   * Instantiates the fragment. If an exception is thrown it is rethrown as a RuntimeException.
   * @param fragmentClass the fragment class to instantiate
   */
  public static Fragment instantiateFragment(Class<? extends Fragment> fragmentClass) {
    try {
      return fragmentClass.newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
