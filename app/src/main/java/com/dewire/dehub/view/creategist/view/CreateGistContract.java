package com.dewire.dehub.view.creategist.view;

import android.view.MenuItem;

import com.jakewharton.rxbinding2.InitialValueObservable;

import io.reactivex.Observable;


/**
 * Created by kl on 04/11/16.
 */

@SuppressWarnings("unused")
public interface CreateGistContract {
  InitialValueObservable<CharSequence> titleText();

  InitialValueObservable<CharSequence> bodyText();

  Observable<Object> saveClick();

  void enableSaveButton(Boolean enabled);

  void showGistCreatedSuccessfully();
}

