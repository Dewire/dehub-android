package com.dewire.dehub.model.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by kl on 11/13/16.
 */

@AutoValue
public abstract class CreateGistFileEntity {

  public abstract String content();

  public static CreateGistFileEntity create(String content) {
    return new AutoValue_CreateGistFileEntity(content);
  }

  public static TypeAdapter<CreateGistFileEntity> typeAdapter(Gson gson) {
    return new AutoValue_CreateGistFileEntity.GsonTypeAdapter(gson);
  }
}
