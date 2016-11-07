package com.dewire.dehub.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.dewire.dehub.R;
import com.dewire.dehub.model.entity.GistEntity;
import com.dewire.dehub.view.creategist.view.CreateGistView;
import com.dewire.dehub.view.viewgist.view.ViewGistView;

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
    replaceMainFragment(new CreateGistView());
  }

  @Override
  public void navigateViewGist(GistEntity data) {
    ViewGistView view = new ViewGistView();
    view.setArguments(parcelBundle(ViewGistView.GIST_ENTITY_KEY, data));
    replaceMainFragment(view);
  }

  private void replaceMainFragment(Fragment fragment) {
    transaction()
        .setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right,
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right)
        .replace(R.id.fragment_container, fragment)
        .addToBackStack(null)
        .commit();
  }

  @SuppressLint("CommitTransaction")
  private FragmentTransaction transaction() {
    checkPreconditions();
    return fragmentActivity.getSupportFragmentManager().beginTransaction();
  }

  private void checkPreconditions() {
    if (fragmentActivity == null) {
      throw new IllegalStateException("fragmentActivity is null. The navigate methods must only "
          + "be called when the activity is resumed");
    }
  }

  private Bundle parcelBundle(String key, Parcelable parcelable) {
    Bundle bundle = new Bundle();
    bundle.putParcelable(key, parcelable);
    return bundle;
  }
}
