package com.dewire.dehub.view.util;

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dewire.dehub.util.Action2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by kl on 31/10/16.
 */

public abstract class ListRecyclerAdapter<D, H extends RecyclerView.ViewHolder>
    extends RecyclerView.Adapter<H> {

  private List<D> data;
  private Action2<Integer, D> clickListener;

  public void setOnItemClickListener(Action2<Integer, D> listener) {
    clickListener = listener;
  }

  public void setData(List<D> data) {
    this.data = Collections.unmodifiableList(data);
    notifyDataSetChanged();
  }

  @SuppressWarnings("unused")
  public ListRecyclerAdapter(List<D> data) {
    this.data = Collections.unmodifiableList(data);
  }

  @SuppressWarnings("unused")
  public ListRecyclerAdapter() {
    this.data = Collections.unmodifiableList(new ArrayList<D>());
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  @Override
  @CallSuper
  public void onBindViewHolder(H holder, int position) {
    D entity = data.get(position);
    setViewClickListener(holder.itemView, position, entity);
    onBindViewHolderWithData(holder, entity);
  }

  private void setViewClickListener(View itemView, int position, D entity) {
    if (isClickable()) {
      itemView.setOnClickListener(l -> {
        if (clickListener != null) {
          clickListener.call(position, entity);
        }
      });
    }
  }

  public void onBindViewHolderWithData(H holder, D data) {
  }

  public boolean isClickable() {
    return false;
  }
}
