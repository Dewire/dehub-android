package com.dewire.dehub;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.model.AppModule;
import com.dewire.dehub.model.DaggerAppComponent;
import com.dewire.dehub.model.NetModule;
import com.dewire.dehub.view.AppActivity;
import com.dewire.dehub.view.Navigation;
import com.dewire.dehub.view.Navigator;
import com.dewire.dehub.view.util.ActivityLifecyleCallbacksAdapter;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

/**
 * Created by kl on 14/10/16.
 */

public class DehubApplication extends Application {

  public static AppComponent getAppComponent(Context context) {
    return ((DehubApplication) context.getApplicationContext()).getAppComponent();
  }

  private AppComponent appComponent;

  public AppComponent getAppComponent() {
    return appComponent;
  }

  @Inject Navigation navigation;

  @Override
  public void onCreate() {
    super.onCreate();
    if (LeakCanary.isInAnalyzerProcess(this)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return;
    }

    appComponent = DaggerAppComponent.builder()
        .appModule(new AppModule(this))
        .netModule(new NetModule("https://api.github.com"))
        .build();

    appComponent.inject(this);

    registerCallbacks();
  }

  private void registerCallbacks() {
    registerActivityLifecycleCallbacks(new ActivityLifecyleCallbacksAdapter() {

      @Override
      public void onActivityResumed(Activity activity) {
        if (activity instanceof FragmentActivity) {
          ((Navigator)navigation).setFragmentActivity((FragmentActivity)activity);
        }
      }

      @Override
      public void onActivityPaused(Activity activity) {
        if (activity instanceof FragmentActivity) {
          ((Navigator)navigation).removeFragmentActivity();
        }
      }
    });
  }
}
