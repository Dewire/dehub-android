package com.dewire.dehub.model;

import com.dewire.dehub.view.BasePresenter;
import com.dewire.dehub.view.login.LoginPresenter;
import com.dewire.dehub.view.login.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by kl on 14/10/16.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {
  void inject(LoginActivity o);
  void inject(LoginPresenter o);
}
