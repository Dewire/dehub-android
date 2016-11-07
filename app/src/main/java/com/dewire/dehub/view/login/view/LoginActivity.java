package com.dewire.dehub.view.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;

import com.dewire.dehub.R;
import com.dewire.dehub.view.AppActivity;
import com.dewire.dehub.view.BaseAppCompatActivity;
import com.dewire.dehub.view.login.LoginPresenter;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import nucleus.factory.RequiresPresenter;
import rx.Observable;

@RequiresPresenter(LoginPresenter.class)
public class LoginActivity extends BaseAppCompatActivity<LoginPresenter>
    implements LoginContract.View {

  //===----------------------------------------------------------------------===//
  // View contract
  //===----------------------------------------------------------------------===//

  @Override
  public Observable<CharSequence> usernameText() {
    return RxTextView.textChanges(usernameTextView);
  }

  @Override
  public Observable<CharSequence> passwordText() {
    return RxTextView.textChanges(passwordTextView);
  }

  @Override
  public Observable<Void> loginButtonClick() {
    return RxView.clicks(loginButton);
  }

  @Override
  public void starAppActivity() {
    Intent intent = new Intent(this, AppActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
  }

  //===----------------------------------------------------------------------===//
  // Implementation
  //===----------------------------------------------------------------------===//

  @BindView(R.id.username_text_view) TextView usernameTextView;
  @BindView(R.id.password_text_view) TextView passwordTextView;
  @BindView(R.id.login_button) Button loginButton;

  @Override
  public void enableLoginButton(Boolean enabled) {
    loginButton.setEnabled(enabled);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
  }

  @Override
  protected void onPause() {
    super.onPause();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }
}
