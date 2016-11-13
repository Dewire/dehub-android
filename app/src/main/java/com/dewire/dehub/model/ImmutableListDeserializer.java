package com.dewire.dehub.model;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by kl on 11/13/16.
 */

public final class ImmutableListDeserializer implements JsonDeserializer<ImmutableList<?>> {

  @Override
  public ImmutableList<?> deserialize(final JsonElement json,
                                      final Type type,
                                      final JsonDeserializationContext context)
      throws JsonParseException {

    final Type[] typeArguments = ((ParameterizedType)type).getActualTypeArguments();
    final Type parameterizedType = listOf(typeArguments[0]).getType();
    final List<?> list = context.deserialize(json, parameterizedType);
    return ImmutableList.copyOf(list);
  }

  private static <E> TypeToken<List<E>> listOf(final Type arg) {
    //noinspection unchecked
    return new TypeToken<List<E>>() {}
        .where(new TypeParameter<E>() {}, (TypeToken<E>)TypeToken.of(arg));
  }
}
