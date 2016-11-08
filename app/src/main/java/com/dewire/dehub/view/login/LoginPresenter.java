package com.dewire.dehub.view.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.model.GistApi;
import com.dewire.dehub.util.LifeObserver;
import com.dewire.dehub.util.Tuple;
import com.dewire.dehub.view.BasePresenter;
import com.dewire.dehub.view.login.view.LoginContract;

import javax.inject.Inject;

import rx.Observable;

public class LoginPresenter extends BasePresenter<LoginContract> {

  @Inject GistApi api;

  @Override
  protected void onSubscribe(@Nullable Bundle arguments) {

    Observable<Tuple<String>> userPass = Observable.combineLatest(
        view().usernameText().map(CharSequence::toString),
        view().passwordText().map(CharSequence::toString),
        Tuple::create);

    life(userPass
        .map(up -> !up.first().isEmpty() && !up.second().isEmpty())
        .distinctUntilChanged()
        .subscribe(view()::enableLoginButton));

    life(view().loginButtonClick()
        .withLatestFrom(userPass, (click, up) -> up)
        .subscribe(up -> tryLogin(up.first(), up.second())));
  }

  private void tryLogin(String usernameText, String passwordText) {
    view().enableLoginButton(false);

    spinError(api.login(usernameText, passwordText)).subscribe(LifeObserver.create(this,
        v -> {
          Log.d("LOGIN", "ok");
          view().starAppActivity();
        },
        error -> {
          Log.e("LOGIN", "failed");
          view().enableLoginButton(true);
        }));
  }

  protected void onInject(AppComponent component) {
    component.inject(this);
  }
}
