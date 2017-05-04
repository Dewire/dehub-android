package com.dewire.dehub.model;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dewire.dehub.model.entity.GistEntity;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Longs;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import okhttp3.Credentials;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by kl on 13/10/16.
 */

public final class State {

  private static final String AUTH_KEY = "AUTH_KEY";

  private final Iterable<Field> subjectFields = Iterables.filter(
      Arrays.asList(getClass().getDeclaredFields()), field -> {
        return field.getType().equals(BehaviorSubject.class);
      });

  private String basicAuth = "";

  String getBasicAuth() {
    return basicAuth;
  }

  void setBasicAuth(String username, String password) {
    basicAuth = Credentials.basic(username, password);
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
    } catch (IllegalAccessException exception) {
      throw new RuntimeException(exception);
    }
  }

  /**
   * Returns true if the given state has data (i.e. onNext has been called at least once since
   * the state was created).
   * @param state the state which must be a BehaviorSubject.
   * @return true if the state has data, otherwise false.
   */
  public static boolean hasData(Observable<?> state) {
    if (state instanceof BehaviorSubject) {
      return ((BehaviorSubject<?>)state).getValue() != null;
    } else {
      throw new IllegalStateException("state was not a BehaviorSubject");
    }
  }

  // State

  BehaviorSubject<ImmutableList<GistEntity>> gists = BehaviorSubject.create();

  public Observable<ImmutableList<GistEntity>> gists() {
    return gists;
  }

  public void restoreState(Bundle savedState) {
    basicAuth = savedState.getString(AUTH_KEY, "");
  }

  public void saveState(Bundle state) {
    state.putString(AUTH_KEY, basicAuth);
  }
}

