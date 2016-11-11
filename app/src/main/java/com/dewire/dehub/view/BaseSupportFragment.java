package com.dewire.dehub.view;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import butterknife.ButterKnife;

import com.dewire.dehub.util.ErrorIndicator;
import com.dewire.dehub.view.util.LoadingIndicator;
import com.dewire.dehub.view.util.Views;
import com.squareup.leakcanary.RefWatcher;

import nucleus.view.NucleusSupportFragment;

/**
 * Created by kl on 28/10/16.
 */

public class BaseSupportFragment<P extends BasePresenter>
    extends NucleusSupportFragment<P> implements LoadingIndicator, ErrorIndicator {

  private static final String IS_SPINNING = "IS_SPINNING";

  private RefWatcher refWatcher;

  private ProgressBar loadingIndicator;

  @CallSuper
  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    setPresenterFactory(InjectingReflectionPresenterFactory.fromViewClass(getClass(), context));
  }

  @CallSuper
  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    checkNotNull(view, "subclass must return a non null value from onCreateView()");
    ButterKnife.bind(this, view);
    setActivityBackground(view);

    loadingIndicator = Views.loadingIndicator(getContext());
    ((FrameLayout)view.getParent()).addView(loadingIndicator);

    if (savedInstanceState != null && savedInstanceState.getBoolean(IS_SPINNING, false)) {
      showLoadingIndicator();
    }
  }

  private void setActivityBackground(View view) {
    Drawable background = view.getBackground();

    if (background instanceof ColorDrawable) {
      getActivity().findViewById(android.R.id.content)
          .setBackgroundColor(((ColorDrawable)background).getColor());
    }
  }

  @CallSuper
  @Override
  public void onSaveInstanceState(Bundle bundle) {
    super.onSaveInstanceState(bundle);

    // Yes apparently this can happen. The documentation for onSaveInstanceState() says
    // "this method may be called at any time before onDestroy()" so I guess it is sometimes called
    // before onViewCreated() because I got a NPE here.
    if (loadingIndicator != null) {
      bundle.putBoolean(IS_SPINNING, loadingIndicator.getVisibility() == View.VISIBLE);
    }
  }

  @CallSuper
  @Override
  public void onResume() {
    super.onResume();
    if (refWatcher == null) {
      refWatcher = getPresenter().getRefWatcher();
    }
  }

  @CallSuper
  @Override
  public void onDestroy() {
    super.onDestroy();
    if (refWatcher != null) {
      refWatcher.watch(this);
    }
  }

  @Override
  public void showLoadingIndicator() {
    Log.d("DEBUG", "SPINNING");
    loadingIndicator.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoadingIndicator() {
    Log.d("DEBUG", "STOP SPINNING");
    loadingIndicator.setVisibility(View.GONE);
  }

  @Override
  public void showErrorIndicator(Throwable error) {
    checkNotNull(getView());
    Views.makeErrorSnackbar(getView()).show();
  }

  /**
   * Returns the toolbar that is associated with the fragments activity.
   * @throws NullPointerException if the activity does not have a toolbar.
   */
  protected Toolbar getToolbar() {
    AppCompatActivity activity = (AppCompatActivity)getActivity();
    ActionBar actionBar = checkNotNull(activity.getSupportActionBar(),
        "Tried to get Toolbar but the Activity did not have an ActionBar");

    return (Toolbar)actionBar.getCustomView();
  }
}

