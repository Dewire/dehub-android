package com.dewire.dehub.model;

import com.dewire.dehub.model.entity.GistEntity;
import com.google.auto.value.AutoValue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Credentials;
import rx.subjects.BehaviorSubject;

/**
 * Created by kl on 13/10/16.
 */

public class State {
  private String basicAuth = "";
  private List<BehaviorSubject> subjects;

  public State() {
   subjects = getSubjects();
  }

  String getBasicAuth() {
    return basicAuth;
  }

  void setBasicAuth(String username, String password) {
    basicAuth = Credentials.basic(username, password);
  }

  // Invalidation

  public void invalidate() {
    for (BehaviorSubject subject : subjects) {
      Object state = subject.getValue();
      if (state != null) {
        //noinspection unchecked
        subject.onNext(state);
      }
    }
  }

  private List<BehaviorSubject> getSubjects() {
    List<BehaviorSubject> result = new ArrayList<>();
    for (Field field : State.class.getDeclaredFields()) {
      if (field.getType().equals(BehaviorSubject.class)) {
        try {
          result.add((BehaviorSubject)(field.get(this)));
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
    }
    return result;
  }

  // State

  public final BehaviorSubject<List<GistEntity>> gists = BehaviorSubject.create();
}

