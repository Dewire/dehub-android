package com.dewire.dehub.util;

import com.google.auto.value.AutoValue;

/**
 * Created by kl on 18/10/16.
 */

@AutoValue
public abstract class Tuple<T> {
  public abstract T first();
  public abstract T second();

  public static<T> Tuple<T> create(T first, T second) {
    return new AutoValue_Tuple<>(first, second);
  }
}
