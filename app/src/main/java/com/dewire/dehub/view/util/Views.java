package com.dewire.dehub.view.util;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by kl on 28/10/16.
 */

public final class Views {
  private Views() {}

  /**
   * Returns a pre-configured loading indicator.
   */
  public static ProgressBar loadingIndicator(Context context) {
    ProgressBar progressBar = new ProgressBar(context);
    progressBar.setIndeterminate(true);
    progressBar.setVisibility(View.GONE);
    return progressBar;
  }

  /**
   * Instantiates the fragment. If an exception is thrown it is rethrown as a RuntimeException.
   * @param fragmentClass the fragment class to instantiate
   */
  public static Fragment instantiateFragment(Class<? extends Fragment> fragmentClass) {
    try {
      return fragmentClass.newInstance();
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }
  }

}
