package com.dewire.dehub.view.creategist.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;

import com.dewire.dehub.R;
import com.dewire.dehub.view.BaseSupportFragment;
import com.dewire.dehub.view.creategist.CreateGistPresenter;
import com.dewire.dehub.view.util.Views;
import com.jakewharton.rxbinding.view.RxMenuItem;
import com.jakewharton.rxbinding.widget.RxTextView;

import nucleus.factory.RequiresPresenter;
import rx.Observable;

/**
 * Created by kl on 01/11/16.
 */

@RequiresPresenter(CreateGistPresenter.class)
public class CreateGistView extends BaseSupportFragment<CreateGistPresenter>
    implements CreateGistContract {

  //===----------------------------------------------------------------------===//
  // View contract
  //===----------------------------------------------------------------------===//

  @Override
  public Observable<CharSequence> titleText() {
    return RxTextView.textChanges(titleInput);
  }

  @Override
  public Observable<CharSequence> bodyText() {
    return RxTextView.textChanges(bodyInput);
  }

  @Override
  public Observable<Void> saveClick() {
    return RxMenuItem.clicks(saveMenuItem);
  }

  @Override
  public void enableSaveButton(Boolean enabled) {
    Views.setEnabledMenuItem(saveMenuItem, enabled);
  }

  @Override
  public void showGistCreatedSuccessfully() {
    //noinspection ConstantConditions
    Snackbar.make(this.getView(), R.string.gist_created, Snackbar.LENGTH_LONG).show();
  }

  //===----------------------------------------------------------------------===//
  // Implementation
  //===----------------------------------------------------------------------===//

  @BindView(R.id.title_input) EditText titleInput;
  @BindView(R.id.body_input) EditText bodyInput;
  MenuItem saveMenuItem;

  @Override
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    setHasOptionsMenu(true);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {

    return inflater.inflate(R.layout.fragment_create_gist, container, false);
  }

  @Override
  public void onPrepareOptionsMenu(Menu menu) {
    super.onPrepareOptionsMenu(menu);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.fragment_create_menu, menu);
    saveMenuItem = menu.findItem(R.id.save_gist);
  }

}
