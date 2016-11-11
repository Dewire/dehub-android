package com.dewire.dehub.view.creategist;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.util.Tuple;
import com.dewire.dehub.view.BasePresenter;
import com.dewire.dehub.view.creategist.view.CreateGistContract;

import rx.Observable;

/**
 * Created by kl on 01/11/16.
 */

public class CreateGistPresenter extends BasePresenter<CreateGistContract> {

  @Override
  protected void onInject(AppComponent component) {
    component.inject(this);
  }

  @Override
  protected void onSubscribe(@Nullable Bundle arguments) {
    super.onSubscribe(arguments);

    Observable<Tuple<String>> titleBody = Observable.combineLatest(
        view().titleText().map(CharSequence::toString),
        view().bodyText().map(CharSequence::toString),
        Tuple::create);








  }
}

