package com.itel.smartkey.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itel.smartkey.R;
import com.itel.smartkey.bean.FuncBean;
import com.itel.smartkey.listener.ItemTouchHelperAdapter;

import java.util.Collections;
import java.util.List;

/**
 * 选择功能菜单的recycleview的adapter
 * Created by huorong.liang on 2017/1/6.
 */

public class ChooseFunctionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {
    Context mContext;
    List<FuncBean> mList;
    OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(ChooseFunctionAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    //item移动时的回调
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {

    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public ChooseFunctionAdapter(Context mContext, List<FuncBean> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void setDatas(List<FuncBean> mList){
        this.mList = mList;
        this.notifyDataSetChanged();
    }

    public void addDatas(List<FuncBean> mList){
        this.mList.addAll(mList);
        this.notifyDataSetChanged();
    }

    //添加一项
    public void addItem(int position, FuncBean bean){
        mList.add(position, bean);
        notifyItemInserted(position);
    }

    //删除一项
    public void removeItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_rv_choose_function, parent, false);
        return new ChooseFunctionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ChooseFunctionViewHolder viewHolder = (ChooseFunctionViewHolder) holder;
//        Log.d("LHRTAG", mList.get(position).getFuncName());
        viewHolder.tv_choose_function_name.setText(mList.get(position).getFuncName());
        Glide.with(mContext).load(mList.get(position).getIconId()).into(viewHolder.iv_choose_function_icon);

        //item的点击事件
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(viewHolder.itemView, position);
                }
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemLongClick(viewHolder.itemView, viewHolder.getLayoutPosition());
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class ChooseFunctionViewHolder extends RecyclerView.ViewHolder{
        TextView tv_choose_function_name;
        ImageView iv_choose_function_icon;

        public ChooseFunctionViewHolder(View itemView) {
            super(itemView);
            tv_choose_function_name = (TextView) itemView.findViewById(R.id.tv_choose_function_name);
            iv_choose_function_icon = (ImageView) itemView.findViewById(R.id.iv_choose_function_icon);
        }
    }
}


