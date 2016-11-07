package com.dewire.dehub.model.entity;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by kl on 31/10/16.
 */

@AutoValue
public abstract class GistFileEntity implements Parcelable {

  public abstract int size();

  public abstract String raw_url();

  public abstract String type();

  public abstract boolean truncated();

  @Nullable public abstract String language();

  public static JsonAdapter<GistFileEntity> jsonAdapter(Moshi moshi) {
    return new AutoValue_GistFileEntity.MoshiJsonAdapter(moshi);
  }
}
