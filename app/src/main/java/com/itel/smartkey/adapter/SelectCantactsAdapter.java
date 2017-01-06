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

public class SelectCantactsAdapter extends RecyclerView.Adapter<ContactsViewHolder> {
    Context mContext;
    List<ContactsBean> mList;
    OnRecycleViewItemClickListener onItemClickListener;

    public void setOnRecycleViewItemClickListener(OnRecycleViewItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public SelectCantactsAdapter(Context mContext, List<ContactsBean> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void setDatas(List<ContactsBean> mList){
        this.mList = mList;
        this.notifyDataSetChanged();
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.contacts_item_layout, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContactsViewHolder holder, final int position) {
        holder.tv_name.setText(mList.get(position).getName());
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(holder.tv_name, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

class ContactsViewHolder extends RecyclerView.ViewHolder{
    TextView tv_name;

    public ContactsViewHolder(View itemView) {
        super(itemView);
        tv_name = (TextView) itemView.findViewById(R.id.contacts_item_name);
    }
}
