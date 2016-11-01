package com.dewire.dehub.view;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.dewire.dehub.R;
import com.dewire.dehub.view.create_gist.CreateGistView;

/**
 * Created by kl on 01/11/16.
 */

public final class Navigator implements Navigation {

  private FragmentActivity fragmentActivity;

  public void setFragmentActivity(FragmentActivity fragmentActivity) {
    this.fragmentActivity = fragmentActivity;
  }

  public void removeFragmentActivity() {
    fragmentActivity = null;
  }

  @Override
  public void navigateNewGist() {
    checkPreconditions();
    transaction()
        .setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right,
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right)
        .replace(R.id.fragment_container, new CreateGistView())
        .addToBackStack(null)
        .commit();
  }

  private void checkPreconditions() {
    if (fragmentActivity == null) {
      throw new IllegalStateException("fragmentActivity is null. The navigate methods must only " +
          "be called when the activity is resumed");
    }
  }

  @SuppressLint("CommitTransaction")
  private FragmentTransaction transaction() {
    return fragmentActivity.getSupportFragmentManager().beginTransaction();
  }

}
