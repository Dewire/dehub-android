package com.dewire.dehub.view.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.model.GistApi;
import com.dewire.dehub.model.State;
import com.dewire.dehub.view.BasePresenter;
import com.dewire.dehub.view.login.LoginActivity;

import javax.inject.Inject;

import nucleus.presenter.Presenter;

public class LoginPresenter extends BasePresenter<LoginActivity> {

  @Override
  protected void onCreate(@Nullable Bundle savedState) {
    /*
    state.gists.subscribe(gists -> {
      System.out.println("got gists: " + gists.size());
      System.out.println("view is null: " + (getView() == null));
    });
    */

    api.login("kl", "fotbollen9871").subscribe(v -> {
      Log.d("LOGIN", "ok");
      getView().startMainActivity(null);
    }, error -> {
      Log.e("LOGIN", "failed");
    });
  }

  @Override
  protected void onTakeView(LoginActivity loginActivity) {
    super.onTakeView(loginActivity);
  }

  protected void onInject(AppComponent component) {
    component.inject(this);
  }


}
