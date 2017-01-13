package com.itel.smartkey.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itel.smartkey.R;
import com.itel.smartkey.bean.ContactsBean;

import java.util.List;

/**
 * Created by huorong.liang on 2017/1/6.
 */

public class SelectCantactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<ContactsBean> mList;
    onItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(SelectCantactsAdapter.onItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface onItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public SelectCantactsAdapter(Context mContext, List<ContactsBean> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void setDatas(List<ContactsBean> mList){
        this.mList = mList;
        this.notifyDataSetChanged();
    }

    public void addDatas(List<ContactsBean> mList){
        this.mList.addAll(mList);
        this.notifyDataSetChanged();
    }

    //添加一项
    public void addItem(int position, ContactsBean bean){
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.contacts_item_layout, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ContactsViewHolder viewHolder = (ContactsViewHolder) holder;
        viewHolder.tv_name.setText(mList.get(position).getName());
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

    class ContactsViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.contacts_item_name);
        }
    }
}


