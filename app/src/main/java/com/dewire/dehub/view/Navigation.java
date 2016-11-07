package com.dewire.dehub.view;

import com.dewire.dehub.model.entity.GistEntity;

/**
 * Created by kl on 01/11/16.
 */

public interface Navigation {
  void navigateNewGist();

  void navigateViewGist(GistEntity data);
}
