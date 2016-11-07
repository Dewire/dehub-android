package com.dewire.dehub;

import com.dewire.dehub.view.BasePresenter;
import com.dewire.dehub.view.login.LoginPresenter;
import com.dewire.dehub.view.login.view.LoginContract;
import com.squareup.leakcanary.RefWatcher;

import java.lang.reflect.Field;

import nucleus.presenter.Presenter;

/**
 * Created by kl on 11/5/16.
 */
public class TestUtil {

  public static <T> void setView(Presenter<T> presenter, T view) {
    setField("view", Presenter.class, presenter, view);
  }

  public static void setRefWatcher(BasePresenter presenter, RefWatcher watcher) {
    setField("refWatcher", BasePresenter.class, presenter, watcher);
  }

  private static <T> void setField(String fieldName, Class<T> targetClass, T target, Object value) {
    try {
      Field field = targetClass.getDeclaredField(fieldName);
      field.setAccessible(true);
      field.set(target, value);
    } catch (NoSuchFieldException | IllegalAccessException exception) {
      throw new RuntimeException(exception);
    }
  }
}
