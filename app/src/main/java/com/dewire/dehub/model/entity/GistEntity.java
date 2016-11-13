package com.dewire.dehub.model.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by kl on 13/10/16.
 */

@AutoValue
public abstract class GistEntity implements Parcelable {

  public abstract String description();

  public abstract Map<String, GistFileEntity> files();

  public abstract Date updated_at();

  /**
   * A gist may have many files. We only support getting a single file (which one is not defined).
   * This method returns a Map.Entry object where the key is the file's name and the value is
   * the file entity.
   */
  public Map.Entry<String, GistFileEntity> file() {
    Iterator<Map.Entry<String, GistFileEntity>> iterator = files().entrySet().iterator();
    if (iterator.hasNext()) {
      return iterator.next();
    } else {
      throw new IllegalStateException("GistEntity did not have any files!");
    }
  }

  public static TypeAdapter<GistEntity> typeAdapter(Gson gson) {
    return new AutoValue_GistEntity.GsonTypeAdapter(gson);
  }
}

