package com.yly.kind.commonutils_library.demoAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yly.kind.commonutils_library.R;
import com.zly.www.easyrecyclerview.adapter.CommonAdapter;
import com.zly.www.easyrecyclerview.adapter.viewholder.BaseViewHolder;

import java.util.ArrayList;

/**
 * Created by yuliyan on 2017/4/5.
 */

public class MuliteTypeAdapter extends CommonAdapter<String,MuliteTypeAdapter.AdapterViewHolderType1> {

    public static final int TYPE_0=0;
    public static final int TYPE_1=1;
    public static final int TYPE_2=2;




    @Override
    public int getItemViewType(int position) {
        if (getFooter() != null && getItemCount() - 1 == position) {
            return TYPE_FOOTER;
        } else {

            return  position%3;
        }
    }


    @Override
    public AdapterViewHolderType1 createCustomViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_0:
                return new AdapterViewHolderType1(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_type0, parent, false));
            case TYPE_1:
                return new AdapterViewHolderType1(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_type1, parent, false));
            case TYPE_2:
                return new AdapterViewHolderType1(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_type2, parent, false));
        }
        return null;
    }

    @Override
    public void bindCustomViewHolder(AdapterViewHolderType1 holder, String s, int position) {

    }


    class AdapterViewHolderType1 extends BaseViewHolder {

        public AdapterViewHolderType1(View itemView) {
            super(itemView);
        }
    }


    class AdapterViewHolderType2 extends AdapterViewHolderType1{

        public AdapterViewHolderType2(View itemView) {
            super(itemView);
        }
    }


    class AdapterViewHodler3 extends AdapterViewHolderType1{

        public AdapterViewHodler3(View itemView) {
            super(itemView);
        }
    }
}
