package com.dewire.dehub.view.login;

import android.util.Log;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.model.GistApi;
import com.dewire.dehub.util.NetObserver;
import com.dewire.dehub.util.Tuple;
import com.dewire.dehub.view.BasePresenter;
import com.dewire.dehub.view.login.view.LoginContract;

import javax.inject.Inject;

import rx.Observable;

public class LoginPresenter extends BasePresenter<LoginContract.View> {

  @Inject GistApi api;

  @Override
  protected void onTakeView(LoginContract.View view) {
    super.onTakeView(view);

    Observable<Tuple<String>> userPass = Observable.combineLatest(
        view.usernameText().map(CharSequence::toString),
        view.passwordText().map(CharSequence::toString),
        Tuple::create);

    life(userPass
        .map(up -> !up.first().isEmpty() && !up.second().isEmpty())
        .distinctUntilChanged()
        .subscribe(view::enableLoginButton));

    life(view.loginButtonClick()
        .withLatestFrom(userPass, (click, up) -> up)
        .subscribe(up -> tryLogin(up.first(), up.second())));
  }

  private void tryLogin(String usernameText, String passwordText) {
    view().enableLoginButton(false);

    spin(api.login(usernameText, passwordText)).subscribe(NetObserver.create(this, v -> {
      Log.d("LOGIN", "ok");
      view().starAppActivity();
    }, error -> {
      Log.e("LOGIN", "failed");
      view().enableLoginButton(true);
    }));
  }

  protected void onInject(AppComponent component) {
    component.inject(this);
  }
}
