package com.dewire.dehub.view.creategist.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
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
import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding.widget.RxTextView;

import javax.inject.Inject;

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
  public Observable<MenuItem> saveClick() {
    return RxToolbar.itemClicks(getToolbar())
        .filter(menuItem -> menuItem.getItemId() == R.id.save_gist);
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
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.fragment_create_menu, menu);
  }

}
