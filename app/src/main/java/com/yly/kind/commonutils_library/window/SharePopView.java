package com.yly.kind.commonutils_library.window;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.yly.kind.commonutils_library.R;
import com.yly.kind.commonutils_library.demoAdapter.SharePlateformAdapter;
import com.yly.kind.commonutils_library.modle.ShareWidgetContent;
import com.yly.kind.commonutils_library.widget.NewsFontSettingView;

import java.util.ArrayList;

/**
 * Created by yuliyan on 2017/4/10.
 */
public class SharePopView  extends PopupWindow    implements View.OnClickListener {

    private View mMenuView;
    private RecyclerView mShareListView;
    private LinearLayoutManager linearLayoutManager;
    private SharePlateformAdapter sharePlateformAdapter;
    private ArrayList<ShareWidgetContent> mSharePlateFormList=new ArrayList<>();
    private int[] widgetImgIds=new int[]{R.drawable.share_pengyouquan,R.drawable.share_weixin,R.drawable.share_qq,R.drawable.share_qzone,R.drawable.share_sina,R.drawable.share_copy};
    private String [] widgetNames=new String[]{"朋友圈","微信好友","QQ","QQ空间","新浪微博","复制链接"};

    private RecyclerView mMoreListView;
    private LinearLayoutManager linearLayoutManager_1;
    private SharePlateformAdapter mMoreShareAdapter;
    private int[] widgetOtherImgIds=new int[]{R.drawable.share_gengduo_zitishezhi,R.drawable.share_gengduo_night_light_selector,R.drawable.share_gengduo_jvbao};
    private String[] widgetOtherNames=new String[]{"字体设置","夜间模式","举报"};
    private ArrayList<ShareWidgetContent> mShareMoreList=new ArrayList<>();




    private NewsFontSettingView mFontSettingView;
    private View mFontSettingParentView;
    private NewsFontSettingView.OnSelectChangeListener mFontSizeChangerListener;


    private TextView mCancleImageView;
    private Context mContext;


    public SharePopView(Context context) {
        super(context);
        this.mContext=context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.share_popupwindow_layout, null,false);
        mShareListView=(RecyclerView) mMenuView.findViewById(R.id.share_plateform_list);
        linearLayoutManager=new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        mShareListView.setLayoutManager(linearLayoutManager);
        sharePlateformAdapter=new SharePlateformAdapter(mSharePlateFormList);
        mShareListView.setAdapter(sharePlateformAdapter);

        mMoreListView=(RecyclerView) mMenuView.findViewById(R.id.share_other_operation_list);
        linearLayoutManager_1=new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        mMoreListView.setLayoutManager(linearLayoutManager_1);
        mMoreShareAdapter=new SharePlateformAdapter(mShareMoreList);
        mMoreListView.setAdapter(mMoreShareAdapter);
        initShareDataList();

        sharePlateformAdapter.setOnItemClickListener(mShareOnItemClickListener);
        mMoreShareAdapter.setOnItemClickListener(mShareOtherOnItemClickListener);


        mFontSettingView=(NewsFontSettingView) mMenuView.findViewById(R.id.newsFontSettingView);
        mFontSettingParentView=mMenuView.findViewById(R.id.font_setting_parentview);


        mCancleImageView=(TextView) mMenuView.findViewById(R.id.cancel_action);
        mCancleImageView.setOnClickListener(this);
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.setOutsideTouchable(false);
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.share_plateform_list).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                         dismiss();
                    }
                }
                return true;
            }

        });
    }


    public void setFontSizeChangeListener( NewsFontSettingView.OnSelectChangeListener listener){
        if(mFontSettingView!=null) {
            mFontSettingView.setOnSelectChangeListener(listener);
        }
    }


    private void showFontSizeSettingLayout(){
        mFontSettingParentView.setVisibility(View.VISIBLE);
        mShareListView.setVisibility(View.GONE);
        mMoreListView.setVisibility(View.GONE);
    }

    private void hideFontSizeSettingLayout(){
        mFontSettingParentView.setVisibility(View.GONE);
        mShareListView.setVisibility(View.VISIBLE);
        mMoreListView.setVisibility(View.VISIBLE);
    }



    public void showWindow(View view) {
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }
    private void initShareDataList(){
        ShareWidgetContent content;
        for (int i=0;i<widgetImgIds.length;i++){
            content=new ShareWidgetContent();
            content.icon_id=widgetImgIds[i];
            content.icon_name=widgetNames[i];
            mSharePlateFormList.add(content);
        }
        sharePlateformAdapter.notifyDataSetChanged();


        for(int i=0;i<widgetOtherImgIds.length;i++){
            content=new ShareWidgetContent();
            content.icon_name=widgetOtherNames[i];
            content.icon_id=widgetOtherImgIds[i];
            mShareMoreList.add(content);
        }

        mMoreShareAdapter.notifyDataSetChanged();
    }


    SharePlateformAdapter.OnItemClickListener mShareOnItemClickListener=new SharePlateformAdapter.OnItemClickListener(){
        @Override
        public void onItemClicke(int position) {
            Toast.makeText(mContext,widgetNames[position],Toast.LENGTH_SHORT).show();
            switch (position){
                case 0:

                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
        }
    };

    SharePlateformAdapter.OnItemClickListener mShareOtherOnItemClickListener=new SharePlateformAdapter.OnItemClickListener(){
        @Override
        public void onItemClicke(int position) {
            Toast.makeText(mContext,widgetOtherNames[position],Toast.LENGTH_SHORT).show();
            switch (position){
                case 0:
                     if(mFontSettingParentView.getVisibility()==View.VISIBLE){
                         hideFontSizeSettingLayout();
                     }else {
                         showFontSizeSettingLayout();
                     }


                    break;
                case 1:
                    break;
                case 2:
                    break;

            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_action:
                dismiss();
                break;
        }
    }
}
