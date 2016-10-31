package com.dewire.dehub.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.dewire.dehub.R;
import com.dewire.dehub.view.AppActivity;
import com.dewire.dehub.view.BaseAppCompatActivity;

import butterknife.BindView;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(LoginPresenter.class)
public class LoginActivity extends BaseAppCompatActivity<LoginPresenter> {

  /////////////////////////// OUTPUTS ////////////////////////////////

  @BindView(R.id.username_text_view) TextView usernameTextView;
  @BindView(R.id.password_text_view) TextView passwordTextView;
  @BindView(R.id.login_button) Button loginButton;

  /////////////////////////// INPUTS /////////////////////////////////

  public void enableLoginButton(Boolean ok) {
    loginButton.setEnabled(ok);
  }
  ////////////////////////////////////////////////////////////////////

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
  }

  public void starAppActivity() {
    Intent intent = new Intent(this, AppActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
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
