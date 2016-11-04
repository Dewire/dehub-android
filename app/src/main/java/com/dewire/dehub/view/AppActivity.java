package com.dewire.dehub.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.dewire.dehub.R;
import com.dewire.dehub.view.login.view.LoginActivity;
import com.dewire.dehub.view.main.view.MainView;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(AppPresenter.class)
public class AppActivity extends BaseAppCompatActivity<AppPresenter> {

  @BindView(R.id.fragment_container) FrameLayout fragmentContainer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_app);
    setSupportActionBar(ButterKnife.findById(this, R.id.toolbar));
    addFragUnlessPresent(R.id.fragment_container, MainView.class);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.app_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_logout) {
      getPresenter().onActionLogout();
      return true;
    }
    return false;
  }

  void logout() {
    Intent intent = new Intent(this, LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
  }
}
