package com.dewire.dehub.model;

import com.dewire.dehub.DehubApplication;
import com.dewire.dehub.view.AppActivity;
import com.dewire.dehub.view.AppPresenter;
import com.dewire.dehub.view.creategist.CreateGistPresenter;
import com.dewire.dehub.view.login.LoginPresenter;
import com.dewire.dehub.view.login.view.LoginActivity;
import com.dewire.dehub.view.main.MainPresenter;
import com.dewire.dehub.view.viewgist.ViewGistPresenter;

import dagger.Component;
import javax.inject.Singleton;


/**
 * Created by kl on 14/10/16.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
@SuppressWarnings("unused")
public interface AppComponent {
  void inject(DehubApplication dehubApplication);

  void inject(LoginActivity loginActivity);

  void inject(AppActivity appActivity);

  void inject(LoginPresenter loginPresenter);

  void inject(AppPresenter appPresenter);

  void inject(MainPresenter mainPresenter);

  void inject(CreateGistPresenter createGistPresenter);

  void inject(ViewGistPresenter viewGistPresenter);
}
