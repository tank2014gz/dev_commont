package com.yly.kind.commonutils_library.demoAdapter;

import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yly.kind.commonutils_library.R;
import com.yly.kind.commonutils_library.widget.DMCommonAdapter;
import com.zly.www.easyrecyclerview.adapter.viewholder.BaseViewHolder;

/**
 * Created by yuliyan on 2017/4/14.
 */
public class MuliteTypeAdapter1 extends DMCommonAdapter<String,MuliteTypeAdapter1.DMViewHolder>{
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
    public View onCreateContentView(ViewGroup parent, int viewType) {
       switch (viewType){
           case 0:

               return inflateView(R.layout.item_layout_type0,parent);
           case 1:
               return  inflateView(R.layout.item_layout_type1,parent);
           case 2:
               return  inflateView(R.layout.item_layout_type2,parent);
       }
        return null;
    }

    @Override
    public DMViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {

        if(realContentView!=null){
             return new DMViewHolder(realContentView);
        } else {
            return null;
        }
    }


    @Override
    public void bindCustomViewHolder(DMViewHolder holder, String s, int position) {

        if(!TextUtils.isEmpty(s)){
            holder.textView.setText(s);
        }




    }

    public class DMViewHolder extends BaseViewHolder{
        TextView textView;
        public DMViewHolder(View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.text);
        }
    }
}
