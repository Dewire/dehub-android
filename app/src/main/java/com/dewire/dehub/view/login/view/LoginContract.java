package com.dewire.dehub.view.login.view;

import rx.Observable;

/**
 * Created by kl on 04/11/16.
 */

public interface LoginContract {

  Observable<CharSequence> usernameText();

  Observable<CharSequence> passwordText();

  Observable<Void> loginButtonClick();

  void enableLoginButton(Boolean enabled);

  void starAppActivity();
}
