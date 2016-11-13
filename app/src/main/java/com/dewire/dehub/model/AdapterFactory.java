package com.dewire.dehub.model;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by kl on 11/13/16.
 */

@GsonTypeAdapterFactory
public abstract class AdapterFactory implements TypeAdapterFactory {

  // Static factory method to access the package
  // private generated implementation
  public static TypeAdapterFactory create() {
    return new AutoValueGson_AdapterFactory();
  }

}
