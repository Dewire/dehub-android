package com.dewire.dehub.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.dewire.dehub.R;
import com.dewire.dehub.util.CompletionObserver;
import com.dewire.dehub.view.AppActivity;
import com.dewire.dehub.view.BaseAppCompatActivity;
import com.dewire.dehub.view.LoadingIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import rx.Observable;

@RequiresPresenter(LoginPresenter.class)
public class LoginActivity extends BaseAppCompatActivity<LoginPresenter> implements LoadingIndicator {

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
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }

  public void starAppActivity() {
    Intent intent = new Intent(this, AppActivity.class);
    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
  }

  @Override
  public void showLoadingIndicator() {
    Log.d("DEBUG", "SPINNING");
  }

  @Override
  public void hideLoadingIndicator() {
    Log.d("DEBUG", "STOP SPINNING");
  }

  @Override
  protected void onPause() {
    super.onPause();

    boolean d = isChangingConfigurations();

    Log.d("D", "CHANGE: " + d);

  }

}
