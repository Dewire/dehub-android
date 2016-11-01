package com.dewire.dehub.model;

import android.app.Application;

import com.dewire.dehub.view.Navigation;
import com.dewire.dehub.view.Navigator;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by kl on 14/10/16.
 */

@Module
public class AppModule {

  Application application;

  public AppModule(Application application) {
    this.application = application;
  }

  @Provides
  @Singleton
  Application application() {
    return application;
  }

  @Provides
  @Singleton
  State state() {
    return new State();
  }

  @Provides
  @Singleton
  GistApi gistApi(State state, Retrofit retrofit) {
    return new GistApi(state, retrofit);
  }

  @Provides
  @Singleton
  RefWatcher refWatcher() {
    return LeakCanary.install(application);
  }

  @Provides
  @Singleton
  Navigation navigation() {
    return new Navigator();
  }
}
