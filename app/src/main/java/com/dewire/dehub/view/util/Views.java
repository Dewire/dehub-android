package com.dewire.dehub.view.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.dewire.dehub.R;

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

  /**
   * Returns a snackbar that is used to display for example network errors.
   * @param view the view that the snackbar should be associated with
   */
  public static Snackbar makeErrorSnackbar(View view) {
    return Snackbar.make(view, R.string.error_message_generic, Snackbar.LENGTH_LONG);
  }

  /**
   * Enables the MenuItem and sets its alpha to 255.
   */
  public static void enableMenuItem(MenuItem menuItem) {
    menuItem.setEnabled(true);
    menuItem.getIcon().setAlpha(255);
  }

  /**
   * Disabled the MenuItem and decreases its alpha so that it is greyed out.
   */
  public static void disableMenuItem(MenuItem menuItem) {
    menuItem.setEnabled(false);
    menuItem.getIcon().setAlpha(100);
  }

  /**
   * Disabled and greys out or enables the MenuItem.
   */
  public static void setEnabledMenuItem(MenuItem menuItem, Boolean enabled) {
    if (enabled) {
      enableMenuItem(menuItem);
    } else {
      disableMenuItem(menuItem);
    }
  }
}
