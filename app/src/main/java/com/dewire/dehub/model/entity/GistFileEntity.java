package com.dewire.dehub.model.entity;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by kl on 31/10/16.
 */

@AutoValue
public abstract class GistFileEntity {
  public abstract int size();
  public abstract String raw_url();
  public abstract String type();
  public abstract boolean truncated();
  @Nullable abstract String language();

  public static JsonAdapter<GistFileEntity> jsonAdapter(Moshi moshi) {
    return new AutoValue_GistFileEntity.MoshiJsonAdapter(moshi);
  }
}
