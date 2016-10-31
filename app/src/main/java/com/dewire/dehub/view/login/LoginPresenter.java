package com.dewire.dehub.view.login;

import android.util.Log;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.model.GistApi;
import com.dewire.dehub.util.NetObserver;
import com.dewire.dehub.util.Tuple;
import com.dewire.dehub.view.BasePresenter;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import javax.inject.Inject;

import rx.Observable;

public class LoginPresenter extends BasePresenter<LoginActivity> {

  @Inject GistApi api;

  @Override
  protected void onTakeView(LoginActivity view) {
    super.onTakeView(view);

    Observable<Tuple<String>> userPass = Observable.combineLatest(
        RxTextView.textChanges(view.usernameTextView).map(CharSequence::toString),
        RxTextView.textChanges(view.passwordTextView).map(CharSequence::toString),
        Tuple::create);

    life(userPass
        .map(up -> !up.first().isEmpty() && !up.second().isEmpty())
        .distinctUntilChanged()
        .subscribe(view::enableLoginButton));

    life(RxView.clicks(view.loginButton)
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
