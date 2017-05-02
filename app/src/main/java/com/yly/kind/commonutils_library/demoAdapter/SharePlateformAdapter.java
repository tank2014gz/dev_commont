package com.yly.kind.commonutils_library.demoAdapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yly.kind.commonutils_library.R;
import com.yly.kind.commonutils_library.modle.ShareWidgetContent;

import java.util.ArrayList;

/**
 * Created by yuliyan on 2017/4/10.
 */
public class SharePlateformAdapter extends RecyclerView.Adapter<SharePlateformAdapter.SharePlateformViewHolder> {


 private ArrayList<ShareWidgetContent> mDatas;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SharePlateformAdapter(ArrayList<ShareWidgetContent> datas){
       this.mDatas=datas;
   }
    @Override
    public SharePlateformViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.share_widget_item_layout,parent,false);
        return new SharePlateformViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SharePlateformViewHolder holder, final int position) {
        ShareWidgetContent itemData=mDatas.get(position);
        holder.itemView .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(onItemClickListener!=null){
                  onItemClickListener.onItemClicke(position);
              }
            }
        });

        if(itemData!=null){
            String name=itemData.icon_name;
            if(TextUtils.isEmpty(name)){
                name="第三方平台";
            }
            holder.iconName.setText(name);

            int iconImgId=itemData.icon_id;
            holder.iconImg.setImageResource(iconImgId);
        }


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class  SharePlateformViewHolder extends RecyclerView.ViewHolder{
          ImageView iconImg;
          TextView iconName;
        public SharePlateformViewHolder(View itemView) {
            super(itemView);

            iconImg=(ImageView) itemView.findViewById(R.id.share_icon_img);
            iconName=(TextView)itemView.findViewById(R.id.share_icon_name);
        }
    }

    public interface  OnItemClickListener{
        public  void  onItemClicke(int position);
    }
}
