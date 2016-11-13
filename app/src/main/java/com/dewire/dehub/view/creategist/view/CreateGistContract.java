package com.dewire.dehub.view.creategist.view;

import android.view.MenuItem;

import rx.Observable;

/**
 * Created by kl on 04/11/16.
 */

@SuppressWarnings("unused")
public interface CreateGistContract {
  Observable<CharSequence> titleText();

  Observable<CharSequence> bodyText();

  Observable<Void> saveClick();

  void enableSaveButton(Boolean enabled);

  void showGistCreatedSuccessfully();
}

