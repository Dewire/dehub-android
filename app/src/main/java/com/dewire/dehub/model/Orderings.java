package com.dewire.dehub.model;

import com.dewire.dehub.model.entity.GistEntity;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Longs;

/**
 * Created by kl on 11/13/16.
 */

public final class Orderings {
  private Orderings() {}

  public static final Ordering<GistEntity> GISTS = new Ordering<GistEntity>() {
    @Override
    public int compare(GistEntity g1, GistEntity g2) {
      return Longs.compare(g2.updated_at().getTime(), g1.updated_at().getTime());
    }
  };
}
