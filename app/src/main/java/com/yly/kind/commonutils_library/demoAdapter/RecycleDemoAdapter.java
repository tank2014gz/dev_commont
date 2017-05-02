package com.yly.kind.commonutils_library.demoAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yly.kind.commonutils_library.R;

import java.util.ArrayList;

/**
 * Created by lielvwang on 2017/2/16.
 */

public class RecycleDemoAdapter extends RecyclerView.Adapter<RecycleDemoAdapter.ViewHolder> {

    private ArrayList<String> mDatas;


    public RecycleDemoAdapter(Context context, ArrayList<String> datas) {
        this.mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_headview_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDatas.get(position));

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_title);
        }
    }


    public void addItem(String content, int position) {
        mDatas.add(position, content);
        notifyItemInserted(position); //Attention!
    }


    public void delecteItem(String string) {
        int positon = mDatas.indexOf(string);
        mDatas.remove(positon);
        notifyItemRemoved(positon);

    }

    public void delecteItem(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);

    }
}
