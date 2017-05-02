package com.yly.kind.commonutils_library.widget;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuLayout;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuView;
import com.zly.www.easyrecyclerview.adapter.viewholder.BaseViewHolder;
import com.zly.www.easyrecyclerview.adapter.viewholder.FooterViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yuliyan on 2017/4/14.
 */
public abstract class DMCommonAdapter  <T, VH extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {

        public static final int TYPE_FOOTER = 233333;
        private final String TAG = "CommonAdapter";
        private final Object mLock = new Object();
        private Context mContext;

        private View mFooter;
        private RecyclerView mRecyclerView;
        private List<T> mDatas = new ArrayList<>();

    /**
     * Swipe menu creator。
     */
    private SwipeMenuCreator mSwipeMenuCreator;

    /**
     * Swipe menu click listener。
     */
    private OnSwipeMenuItemClickListener mSwipeMenuItemClickListener;

    /**
     * Set to create menu listener.
     *
     * @param swipeMenuCreator listener.
     */
    void setSwipeMenuCreator(SwipeMenuCreator swipeMenuCreator) {
        this.mSwipeMenuCreator = swipeMenuCreator;
    }

    /**
     * Set to click menu listener.
     *
     * @param swipeMenuItemClickListener listener.
     */
    void setSwipeMenuItemClickListener(OnSwipeMenuItemClickListener swipeMenuItemClickListener) {
        this.mSwipeMenuItemClickListener = swipeMenuItemClickListener;
    }



    @Override
        public int getItemCount() {
                return mDatas == null || mDatas.size() == 0 ? 0 : mFooter == null ? mDatas.size() : mDatas.size() + 1;
                }

        @Override
        public int getItemViewType(int position) {
                if (getFooter() != null && getItemCount() - 1 == position) {
                return TYPE_FOOTER;
                } else {
                return super.getItemViewType(position);
                }
                }

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if (mContext == null)
                mContext = parent.getContext();
                if (mRecyclerView == null)
                mRecyclerView = (RecyclerView) parent;

                if (viewType == TYPE_FOOTER) {
                return new FooterViewHolder(mFooter);
                }

               View contentView= onCreateContentView(parent,viewType);
                if(mSwipeMenuCreator!=null){
                    SwipeMenuLayout swipeMenuLayout = (SwipeMenuLayout) LayoutInflater.from(parent.getContext()).inflate(com.yanzhenjie.recyclerview.swipe.R.layout.yanzhenjie_item_default, parent, false);
                    SwipeMenu swipeLeftMenu = new SwipeMenu(swipeMenuLayout, viewType);
                    SwipeMenu swipeRightMenu = new SwipeMenu(swipeMenuLayout, viewType);

                    mSwipeMenuCreator.onCreateMenu(swipeLeftMenu, swipeRightMenu, viewType);

                    int leftMenuCount = swipeLeftMenu.getMenuItems().size();
                    if (leftMenuCount > 0) {
                        SwipeMenuView swipeLeftMenuView = (SwipeMenuView) swipeMenuLayout.findViewById(com.yanzhenjie.recyclerview.swipe.R.id.swipe_left);
                        swipeLeftMenuView.setOrientation(swipeLeftMenu.getOrientation());
                        swipeLeftMenuView.bindMenu(swipeLeftMenu, SwipeMenuRecyclerView.LEFT_DIRECTION);
                        swipeLeftMenuView.bindMenuItemClickListener(mSwipeMenuItemClickListener, swipeMenuLayout);
                    }

                    int rightMenuCount = swipeRightMenu.getMenuItems().size();
                    if (rightMenuCount > 0) {
                        SwipeMenuView swipeRightMenuView = (SwipeMenuView) swipeMenuLayout.findViewById(com.yanzhenjie.recyclerview.swipe.R.id.swipe_right);
                        swipeRightMenuView.setOrientation(swipeRightMenu.getOrientation());
                        swipeRightMenuView.bindMenu(swipeRightMenu, SwipeMenuRecyclerView.RIGHT_DIRECTION);
                        swipeRightMenuView.bindMenuItemClickListener(mSwipeMenuItemClickListener, swipeMenuLayout);
                    }

                    if (leftMenuCount > 0 || rightMenuCount > 0) {
                        ViewGroup viewGroup = (ViewGroup) swipeMenuLayout.findViewById(com.yanzhenjie.recyclerview.swipe.R.id.swipe_content);
                        viewGroup.addView(contentView);
                        contentView = swipeMenuLayout;

                    }
                }

            return onCompatCreateViewHolder(contentView, viewType);
     }
    /**
     * Create view for item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new view.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    public abstract View onCreateContentView(ViewGroup parent, int viewType);
    /**
     * Instead {@link #onCreateViewHolder(ViewGroup, int)}.
     *
     * @param realContentView Is this Item real view, {@link SwipeMenuLayout} or {@link #onCreateContentView(ViewGroup, int)}.
     * @param viewType        The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see # (RecyclerView.ViewHolder, int, List)
     */
    public abstract VH onCompatCreateViewHolder(View realContentView, int viewType);


        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {
                if (getItemViewType(position) != TYPE_FOOTER) {
                    View itemView = holder.itemView;
                    if (itemView instanceof SwipeMenuLayout) {
                        SwipeMenuLayout swipeMenuLayout = (SwipeMenuLayout) itemView;
                        int childCount = swipeMenuLayout.getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            View childView = swipeMenuLayout.getChildAt(i);
                            if (childView instanceof SwipeMenuView) {
                                ((SwipeMenuView) childView).bindAdapterViewHolder(holder);
                            }
                        }
                    }
                    T t=mDatas.get(position);
                    bindCustomViewHolder((VH) holder, t, position);
                }
        }

        public View inflateView(@LayoutRes int resId, ViewGroup parent) {
                return LayoutInflater.from(mContext).inflate(resId, parent, false);
        }

        public Context getContext() {
                return mContext;
                }

        public View getFooter() {
                return mFooter;
                }

        public void setFooter(View footer) {
                mFooter = footer;
                }

        public void removeFooter() {
                mFooter = null;
                }

        public void add(@NonNull T object) {
        synchronized (mLock) {
                if (null != mDatas) {
                mDatas.add(object);
                notifyItemInserted(mDatas.size() - 1);
                }
                }
                }

        public void addAll(@NonNull Collection<? extends T> collection) {
        synchronized (mLock) {
                if (null != mDatas) {
                mDatas.addAll(collection);

                if (mDatas.size() - collection.size() != 0) {
                notifyItemRangeInserted(mDatas.size() - collection.size(), collection.size());
                } else {
                notifyDataSetChanged();
                }
                }
                }

                }

        @SafeVarargs
        public final void addAll(@NonNull T... items) {
        synchronized (mLock) {
                if (null != mDatas) {
                Collections.addAll(mDatas, items);
                if (mDatas.size() - items.length != 0) {
                notifyItemRangeInserted(mDatas.size() - items.length, items.length);
                } else {
                notifyDataSetChanged();
                }
                }
                }
                }

        public void insert(@NonNull T object, int index) {
                if (mDatas == null || index < 0 || index > mDatas.size()) {
                Log.i(TAG, "insert: index error");
                return;
                }
        synchronized (mLock) {
                if (null != mDatas) {
                mDatas.add(index, object);
                notifyItemInserted(index);

                if (index == 0)
                scrollTop();
                }
                }
                }

        public void insertAll(@NonNull Collection<? extends T> collection, int index) {
                if (mDatas == null || index < 0 || index > mDatas.size()) {
                Log.i(TAG, "insertAll: index error");
                return;
                }
        synchronized (mLock) {
                if (null != mDatas) {
                mDatas.addAll(index, collection);
                notifyItemRangeInserted(index, collection.size());

                if (index == 0)
                scrollTop();
                }
                }
                }

        public void remove(int index) {
                if (mDatas == null || index < 0 || index > mDatas.size() - 1) {
                Log.i(TAG, "remove: index error");
                return;
                }
        synchronized (mLock) {
                mDatas.remove(index);
                notifyItemRemoved(index);
                }
                }

        public boolean remove(@NonNull T object) {
                int removeIndex = -1;
                boolean removeSuccess = false;

                if (mDatas == null || mDatas.size() == 0) {
                Log.i(TAG, "remove fail datas emply");
                return false;
                }

        synchronized (mLock) {
                removeIndex = mDatas.indexOf(object);
                removeSuccess = mDatas.remove(object);
                }

                if (removeSuccess) {
                notifyItemRemoved(removeIndex);
                return true;
                }
                return false;
                }

        public void clear() {
        synchronized (mLock) {
                if (mDatas != null) {
                mDatas.clear();
                }
                }
                notifyDataSetChanged();
                }

        public void sort(Comparator<? super T> comparator) {
        synchronized (mLock) {
                if (mDatas != null) {
                Collections.sort(mDatas, comparator);
                }
                }
                notifyDataSetChanged();
                }

        public void update(@NonNull List<T> mDatas) {
        synchronized (mLock) {
                this.mDatas = mDatas;
                }
                notifyDataSetChanged();
                }

        public T getItem(int position) {
                return mDatas.get(position);
                }

        public int getPosition(T item) {
                return mDatas.indexOf(item);
                }

        public List<T> getData() {
                if (null == mDatas)
                mDatas = new ArrayList<>();
                return mDatas;
                }

        /**
         * 处理insert index为0时新数据未显示问题
         */
        private void scrollTop() {
                if (null != mRecyclerView)
                mRecyclerView.scrollToPosition(0);
                }



        public abstract void bindCustomViewHolder(VH holder, T t, int position);
}
