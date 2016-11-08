package com.dewire.dehub.view;

import android.content.Context;

import com.dewire.dehub.DehubApplication;

import nucleus.factory.ReflectionPresenterFactory;
import nucleus.factory.RequiresPresenter;
import nucleus.presenter.Presenter;

/**
 * Created by kl on 14/10/16.
 */

class InjectingReflectionPresenterFactory<P extends Presenter>
    extends ReflectionPresenterFactory<P> {

  private final Context appContext;

  private InjectingReflectionPresenterFactory(Class<P> presenterClass, Context context) {
    super(presenterClass);
    appContext = context.getApplicationContext();
  }

  static <P extends Presenter> InjectingReflectionPresenterFactory<P> fromViewClass(
      Class<?> viewClass, Context context) {

    RequiresPresenter annotation = viewClass.getAnnotation(RequiresPresenter.class);
    //noinspection unchecked
    Class<P> presenterClass = annotation == null ? null : (Class<P>)annotation.value();
    return presenterClass == null ? null :
        new InjectingReflectionPresenterFactory<>(presenterClass, context);
  }

  @Override
  public P createPresenter() {
    P presenter =  super.createPresenter();
    if (presenter instanceof BasePresenter) {
      ((BasePresenter) presenter).onInject(DehubApplication.getAppComponent(appContext));
    }
    return presenter;
  }
}
