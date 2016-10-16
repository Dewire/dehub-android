package com.dewire.dehub.model.entity;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by kl on 13/10/16.
 */

@AutoValue
public abstract class GistEntity {
  public abstract String description();

  public static JsonAdapter<GistEntity> jsonAdapter(Moshi moshi) {
    return new AutoValue_GistEntity.MoshiJsonAdapter(moshi);
  }
}

