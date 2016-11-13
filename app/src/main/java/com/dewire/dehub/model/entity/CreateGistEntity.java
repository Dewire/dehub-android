package com.dewire.dehub.model.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kl on 11/13/16.
 */

@AutoValue
public abstract class CreateGistEntity {

  public abstract String description();

  @SerializedName("public")
  public abstract boolean isPublic();

  public abstract Map<String, CreateGistFileEntity> files();

  /**
   * Factory.
   */
  public static CreateGistEntity create(String filename,
                                        String description,
                                        boolean isPublic,
                                        CreateGistFileEntity file) {

    Map<String, CreateGistFileEntity> fileMap = new HashMap<>();
    fileMap.put(filename, file);
    return new AutoValue_CreateGistEntity(description, isPublic, fileMap);
  }

  public static TypeAdapter<CreateGistEntity> typeAdapter(Gson gson) {
    return new AutoValue_CreateGistEntity.GsonTypeAdapter(gson);
  }
}
