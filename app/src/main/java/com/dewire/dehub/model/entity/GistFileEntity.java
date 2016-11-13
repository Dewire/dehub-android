package com.dewire.dehub.model.entity;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

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

  public static TypeAdapter<GistFileEntity> typeAdapter(Gson gson) {
    return new AutoValue_GistFileEntity.GsonTypeAdapter(gson);
  }
}
