package com.yly.kind.commonutils_library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lielvwang on 2017/2/15.
 */

public class DemoRecycleViewAdapter extends RecyclerView.Adapter<DemoRecycleViewAdapter.ViewHolder> {

    private ArrayList<String> titles;
    private Context mContext;
    private OnItemClickedListener mItemClickedListener;

    public DemoRecycleViewAdapter(Context context, ArrayList<String> titles) {
        this.mContext = context;
        this.titles = titles;
    }


    @Override
    public DemoRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_headview_item_layout, parent, false);
        return new DemoRecycleViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DemoRecycleViewAdapter.ViewHolder holder, final int position) {
        holder.mTitleText.setText(titles.get(position));
        holder.mTitleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickedListener != null) {
                    mItemClickedListener.OnItem(holder.mTitleText, position);
                }
            }
        });
    }

    public void setItemClickedListener(OnItemClickedListener listener) {
        this.mItemClickedListener = listener;
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleText;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitleText = (TextView) itemView.findViewById(R.id.item_title);
        }
    }
}
