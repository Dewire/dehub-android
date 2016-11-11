package com.dewire.dehub.view.main.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.dewire.dehub.R;
import com.dewire.dehub.model.entity.GistEntity;
import com.dewire.dehub.view.BaseSupportFragment;
import com.dewire.dehub.view.main.MainPresenter;
import com.dewire.dehub.view.util.ListRecyclerAdapter;

import java.util.List;

import nucleus.factory.RequiresPresenter;

/**
 * Created by kl on 28/10/16.
 */

@RequiresPresenter(MainPresenter.class)
public class MainView extends BaseSupportFragment<MainPresenter>
    implements MainContract {

  //===----------------------------------------------------------------------===//
  // View contract
  //===----------------------------------------------------------------------===//

  @Override
  public void displayGists(List<GistEntity> gists) {
    adapter.setData(gists);
  }

  @Override
  public void stopRefreshing() {
    Log.d("DEBUG", "stopRefreshing()");
    swipeRefresh.setRefreshing(false);
  }

  //===----------------------------------------------------------------------===//
  // Implementation
  //===----------------------------------------------------------------------===//

  private static final java.lang.String SWIPE_REFRESHING = "SWIPE_REFRESHING";

  private final Adapter adapter = createAdapter();

  private Adapter createAdapter() {
    Adapter adapter = new Adapter();
    adapter.setOnItemClickListener((position, data) -> getPresenter().onActionViewGist(data));
    return adapter;
  }

  @BindView(R.id.gists_recycler_view) RecyclerView gistsView;
  @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;

  @Override
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    setHasOptionsMenu(true);
  }

  @Override
  public void onSaveInstanceState(Bundle bundle) {
    super.onSaveInstanceState(bundle);
    bundle.putBoolean(SWIPE_REFRESHING, swipeRefresh != null && swipeRefresh.isRefreshing());
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.fragment_main_menu, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.save_gist) {
      getPresenter().onActionNewGist();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {

    return inflater.inflate(R.layout.fragment_main, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    setupSwipeRefresh(savedInstanceState);
    setupGistsView(view.getContext());
  }

  private void setupSwipeRefresh(Bundle savedInstanceState) {
    swipeRefresh.setRefreshing(
        savedInstanceState != null && savedInstanceState.getBoolean(SWIPE_REFRESHING, false));

    swipeRefresh.setOnRefreshListener(getPresenter()::onRefresh);
  }

  private void setupGistsView(Context context) {
    gistsView.setLayoutManager(new LinearLayoutManager(context));
    gistsView.addItemDecoration(new DividerItemDecoration(gistsView.getContext(),
        LinearLayoutManager.VERTICAL));
    gistsView.setAdapter(adapter);
  }

  private static class ViewHolder extends RecyclerView.ViewHolder {

    final TextView name;
    final TextView language;

    public ViewHolder(View itemView) {
      super(itemView);
      name = ButterKnife.findById(itemView, R.id.name);
      language = ButterKnife.findById(itemView, R.id.language);
    }
  }

  private static class Adapter extends ListRecyclerAdapter<GistEntity, ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.gist_cell, parent, false);

      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolderWithData(ViewHolder holder, GistEntity entity) {
      holder.name.setText(entity.file().getKey());
      holder.language.setText(entity.file().getValue().language());
    }

    @Override
    public boolean isClickable() {
      return true;
    }
  }

}
