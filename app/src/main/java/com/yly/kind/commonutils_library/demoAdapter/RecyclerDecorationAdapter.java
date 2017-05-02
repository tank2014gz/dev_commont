package com.yly.kind.commonutils_library.demoAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yly.kind.commonutils_library.R;

import java.util.ArrayList;

/**
 * Created by lielvwang on 2017/2/17.
 */

public class RecyclerDecorationAdapter extends RecyclerView.Adapter<RecyclerDecorationAdapter.DecorationHolder> {
    private ArrayList<String> mDatas;

    public RecyclerDecorationAdapter(ArrayList<String> datas) {
        this.mDatas = datas;
    }

    @Override
    public DecorationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dectoration_item_layout, parent, false);
        return new DecorationHolder(view);
    }

    @Override
    public void onBindViewHolder(DecorationHolder holder, int position) {
        holder.mTextView.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    static class DecorationHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public DecorationHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_title);

        }
    }
}
