package com.dewire.dehub.view;

import android.content.Context;

import com.dewire.dehub.DehubApplication;

import nucleus.factory.ReflectionPresenterFactory;
import nucleus.factory.RequiresPresenter;
import nucleus.presenter.Presenter;

/**
 * Created by kl on 14/10/16.
 */

public class InjectingReflectionPresenterFactory<P extends Presenter> extends ReflectionPresenterFactory<P> {

  private Context appContext;

  public InjectingReflectionPresenterFactory(Class<P> presenterClass, Context contex) {
    super(presenterClass);
    appContext = contex.getApplicationContext();
  }

  public static <P extends Presenter> InjectingReflectionPresenterFactory<P> fromViewClass(Class<?> viewClass, Context context) {
    RequiresPresenter annotation = viewClass.getAnnotation(RequiresPresenter.class);
    //noinspection unchecked
    Class<P> presenterClass = annotation == null ? null : (Class<P>)annotation.value();
    return presenterClass == null ? null : new InjectingReflectionPresenterFactory<>(presenterClass, context);
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
