package com.dewire.dehub;

import android.app.Application;
import android.content.Context;
import android.os.Looper;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.model.AppModule;
import com.dewire.dehub.model.DaggerAppComponent;
import com.dewire.dehub.model.NetModule;
import com.squareup.leakcanary.LeakCanary;

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
  }
}
