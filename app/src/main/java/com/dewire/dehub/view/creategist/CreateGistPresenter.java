package com.dewire.dehub.view.creategist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dewire.dehub.model.AppComponent;
import com.dewire.dehub.model.GistApi;
import com.dewire.dehub.model.entity.CreateGistEntity;
import com.dewire.dehub.model.entity.CreateGistFileEntity;
import com.dewire.dehub.util.LifeObserver;
import com.dewire.dehub.util.Tuple;
import com.dewire.dehub.view.BasePresenter;
import com.dewire.dehub.view.creategist.view.CreateGistContract;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by kl on 01/11/16.
 */

public class CreateGistPresenter extends BasePresenter<CreateGistContract> {

  @Inject GistApi api;

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

    life(titleBody
        .map(tb -> !tb.first().isEmpty() && !tb.second().isEmpty())
        .subscribe(enabled -> view().enableSaveButton(enabled)));

    life(view().saveClick()
        .withLatestFrom(titleBody, (click, texts) -> texts)
        .subscribe(this::saveGist));
  }

  private void saveGist(Tuple<String> texts) {
    CreateGistEntity entity = CreateGistEntity.create(
        texts.first(), texts.first(), true, CreateGistFileEntity.create(texts.second()));

    spinError(api.postGist(entity)).subscribe(LifeObserver.create(this,
        gistEntity -> view().showGistCreatedSuccessfully()));
  }
}

