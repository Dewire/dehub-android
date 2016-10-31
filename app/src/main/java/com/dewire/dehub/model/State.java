package com.dewire.dehub.model;

import com.dewire.dehub.model.entity.GistEntity;
import com.google.auto.value.AutoValue;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Credentials;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by kl on 13/10/16.
 */

public final class State {
  private String basicAuth = "";
  private Iterable<Field> subjectFields = getSubjectFields();

  private Iterable<Field> getSubjectFields() {
    return Iterables.filter(Arrays.asList(getClass().getDeclaredFields()), field -> {
      return field.getType().equals(BehaviorSubject.class);
    });
  }

  /**
   * Creates new subjects so that the state is reset.
   * Note that if this method is called all subscribers will be lost
   * and must resubscribe. So only call this method when the user logs out.
   */
  public void reset() {
    try {
      for (Field f : subjectFields) {
        f.set(this, BehaviorSubject.create());
      }
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  String getBasicAuth() {
    return basicAuth;
  }

  void setBasicAuth(String username, String password) {
    basicAuth = Credentials.basic(username, password);
  }

  public static boolean hasData(Observable<?> state) {
    if (state instanceof BehaviorSubject) {
      return ((BehaviorSubject<?>)state).getValue() != null;
    } else {
      throw new IllegalStateException("state was not a BehaviorSubject");
    }
  }

  // State

  BehaviorSubject<List<GistEntity>> gists = BehaviorSubject.create();
  public Observable<List<GistEntity>> gists() { return gists; }


}

