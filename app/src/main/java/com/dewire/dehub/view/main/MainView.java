package com.dewire.dehub.view.main;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dewire.dehub.R;
import com.dewire.dehub.model.entity.GistEntity;
import com.dewire.dehub.view.BaseSupportFragment;
import com.dewire.dehub.view.util.ListRecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusSupportFragment;
import rx.functions.Action2;
import rx.functions.Action3;
import rx.functions.Func2;

/**
 * Created by kl on 28/10/16.
 */

@RequiresPresenter(MainPresenter.class)
public class MainView extends BaseSupportFragment<MainPresenter> {

  private final Adapter adapter = new Adapter();

  @BindView(R.id.gists_recycler_view) RecyclerView gistsView;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {

    return inflater.inflate(R.layout.fragment_main, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    gistsView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    gistsView.setAdapter(adapter);
  }

  void displayGists(List<GistEntity> gists) {
    adapter.setData(gists);
  }

  private static class ViewHolder extends RecyclerView.ViewHolder {

    TextView name;

    public ViewHolder(View itemView) {
      super(itemView);
      name = ButterKnife.findById(itemView, R.id.name);
    }
  }

  private static class Adapter extends ListRecyclerAdapter<GistEntity, ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.gist_cell, parent, false);

      return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      GistEntity entity = data.get(position);
      holder.name.setText(entity.description());
    }
  }

}
