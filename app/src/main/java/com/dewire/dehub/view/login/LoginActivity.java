package com.dewire.dehub.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dewire.dehub.DehubApplication;
import com.dewire.dehub.R;
import com.dewire.dehub.model.GistApi;
import com.dewire.dehub.model.State;
import com.dewire.dehub.view.BaseAppCompatActivity;

import javax.inject.Inject;

import nucleus.factory.RequiresPresenter;

@RequiresPresenter(LoginPresenter.class)
public class LoginActivity extends BaseAppCompatActivity<LoginPresenter> {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }

  public void startMainActivity(View view) {
    Intent intent = new Intent(this, AppActivity.class);
    startActivity(intent);
  }
}
