package com.dewire.dehub.view;

import static com.google.common.base.Preconditions.checkNotNull;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import butterknife.ButterKnife;

import com.dewire.dehub.util.ErrorIndicator;
import com.dewire.dehub.view.util.LoadingIndicator;
import com.dewire.dehub.view.util.Views;

import nucleus.view.NucleusAppCompatActivity;


/**
 * Created by kl on 14/10/16.
 */

public abstract class BaseAppCompatActivity<P extends BasePresenter>
    extends NucleusAppCompatActivity<P> implements LoadingIndicator, ErrorIndicator {

  private static final String IS_SPINNING = "IS_SPINNING";

  private FrameLayout contentView;
  private ProgressBar loadingIndicator;

  @CallSuper
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setPresenterFactory(InjectingReflectionPresenterFactory.fromViewClass(getClass(), this));
    super.onCreate(savedInstanceState);
  }

  @CallSuper
  @Override
  protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    setupViews();

    if (savedInstanceState != null && savedInstanceState.getBoolean(IS_SPINNING, false)) {
      showLoadingIndicator();
    }
  }

  private void setupViews() {
    contentView = (FrameLayout)findViewById(android.R.id.content);
    checkNotNull(contentView,
        "contentView is null. Subclass must call setContentView() in onCreate()");

    loadingIndicator = Views.loadingIndicator(this);
    contentView.addView(loadingIndicator);
  }

  @CallSuper
  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBoolean(IS_SPINNING, loadingIndicator.getVisibility() == View.VISIBLE);
  }

  @CallSuper
  @Override
  public void setContentView(View contentView) {
    super.setContentView(contentView);
    ButterKnife.bind(this);
  }

  @CallSuper
  @Override
  public void setContentView(@LayoutRes int layoutResId) {
    super.setContentView(layoutResId);
    ButterKnife.bind(this);
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

  /**
   * Adds a fragment to the fragId view unless a fragment is already added to that container.
   * @param fragId the view container ID to add the fragment to
   * @param fragClass the class of the fragment.
   */
  @SuppressWarnings("SameParameterValue")
  void addFragUnlessPresent(@IdRes int fragId, Class<? extends Fragment> fragClass) {
    if (getSupportFragmentManager().findFragmentById(fragId) != null) {
      return;
    }

    Fragment frag = Views.instantiateFragment(fragClass);

    getSupportFragmentManager().beginTransaction()
        .add(fragId, frag)
        .commit();
  }

  @Override
  public void showErrorIndicator(Throwable error) {
    Views.makeErrorSnackbar(contentView).show();
  }
}


