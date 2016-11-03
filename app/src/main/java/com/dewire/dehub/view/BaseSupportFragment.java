package com.dewire.dehub.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.dewire.dehub.view.util.LoadingIndicator;
import com.dewire.dehub.view.util.Views;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import nucleus.view.NucleusSupportFragment;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by kl on 28/10/16.
 */

public class BaseSupportFragment<P extends BasePresenter>
  extends NucleusSupportFragment<P> implements LoadingIndicator {

  private static final String IS_SPINNING = "IS_SPINNING";

  RefWatcher refWatcher;

  private ProgressBar loadingIndicator;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    setPresenterFactory(InjectingReflectionPresenterFactory.fromViewClass(getClass(), context));
  }

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

  @Override
  public void onResume() {
    super.onResume();
    if (refWatcher == null) refWatcher = getPresenter().getRefWatcher();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (refWatcher != null) refWatcher.watch(this);
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
}

