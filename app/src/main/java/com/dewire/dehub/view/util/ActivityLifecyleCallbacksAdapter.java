package com.dewire.dehub.view.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by kl on 01/11/16.
 */

public abstract class ActivityLifecyleCallbacksAdapter
    implements Application.ActivityLifecycleCallbacks {

  @Override
  public void onActivityCreated(Activity activity, Bundle bundle) {

  }

  @Override
  public void onActivityStarted(Activity activity) {

  }

  @Override
  public void onActivityResumed(Activity activity) {

  }

  @Override
  public void onActivityPaused(Activity activity) {

  }

  @Override
  public void onActivityStopped(Activity activity) {

  }

  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

  }

  @Override
  public void onActivityDestroyed(Activity activity) {

  }
}
