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

  /**
   * Given a fragment returns the Parcelable for the given key from the fragments arguments.
   * @throws NullPointerException if the fragment does not have arguments or does not have a
   * value for the key.
   * @throws ClassCastException if the value of for the key was of the wrong type.
   */
  public static <T> T getParcelableViewArgument(Fragment view, String key) {
    Bundle args = checkNotNull(view.getArguments(),
        "tried to get arguments but getArguments() was null");

    Object data = checkNotNull(args.getParcelable(key),
        "tried to get parcelable for key " + key + " but was null");

    //noinspection unchecked
    return (T)data;
  }
}
