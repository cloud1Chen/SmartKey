package com.itel.smartkey.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itel.smartkey.R;

import java.util.List;

/**
 * Created by huorong.liang on 2017/1/9.
 */

public class SelectContactsNumberAdapter extends RecyclerView.Adapter {

    List<String> mDatas;
    Context mContext;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    OnItemClickListener mOnItemClickListener;

    public SelectContactsNumberAdapter(Context mContext, List<String> mDatas) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    public void setDatas(List<String> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public void addDatas(List<String> mDatas){
        this.mDatas.addAll(mDatas);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.contacts_item_layout, parent, false);
        return new ContactsNumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((ContactsNumberViewHolder)holder).textView.setText(mDatas.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    class ContactsNumberViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public ContactsNumberViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.contacts_item_name);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}


