package com.dewire.dehub.model;

import android.app.Application;

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
}
