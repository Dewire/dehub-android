package com.dewire.dehub.view.util;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kl on 31/10/16.
 */

public abstract class ListRecyclerAdapter<D, VH extends RecyclerView.ViewHolder>
    extends RecyclerView.Adapter<VH> {

  protected List<D> data;

  public void setData(List<D> data) {
    this.data = Collections.unmodifiableList(data);
    notifyDataSetChanged();
  }

  public ListRecyclerAdapter(List<D> data) {
    this.data = Collections.unmodifiableList(data);
  }

  public ListRecyclerAdapter() {
    this.data = Collections.unmodifiableList(new ArrayList<D>());
  }

  @Override
  public int getItemCount() {
    return data.size();
  }
}
