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

public class RecyclerHeaderAndFooterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_FOOTER = 2;
    private Context mContext;
    private ArrayList<String> mDatas;
    private View mHeaderView;
    private View mFooterView;

    public RecyclerHeaderAndFooterAdapter(Context context, ArrayList<String> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }


    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ListHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new ListHolder(mFooterView);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_headview_item_layout, parent, false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            if (holder instanceof ListHolder) {
                ((ListHolder) holder).tv.setText(mDatas.get(position - 1));
            }
        } else if (getItemViewType(position) == TYPE_HEADER) {
            return;
        } else {
            return;
        }


    }

    @Override
    public int getItemCount() {
        if (mFooterView == null && mHeaderView == null) {
            return mDatas.size();
        } else if (mFooterView == null && mHeaderView != null) {
            return mDatas.size() + 1;
        } else if (mHeaderView == null && mFooterView != null) {
            return mDatas.size() + 1;
        } else {
            return mDatas.size() + 2;
        }

    }


    class ListHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public ListHolder(View itemView) {
            super(itemView);
            //如果是header或者footer,直接返回
            if (itemView == mHeaderView) {
                return;
            }
            if (itemView == mFooterView) {
                return;
            }
            tv = (TextView) itemView.findViewById(R.id.item_title);
        }
    }
}
