package com.dewire.dehub.view.main.view;

import com.dewire.dehub.model.entity.GistEntity;

import java.util.List;

/**
 * Created by kl on 04/11/16.
 */

public interface MainContract {

  interface View {
    void displayGists(List<GistEntity> gists);
  }
}
