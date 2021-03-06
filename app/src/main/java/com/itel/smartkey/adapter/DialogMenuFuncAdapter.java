package com.itel.smartkey.adapter;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itel.smartkey.R;
import com.itel.smartkey.bean.Function;
import com.itel.smartkey.bean.Settings;
import com.itel.smartkey.contants.MyContants;
import com.itel.smartkey.listener.ItemTouchHelperAdapter;
import com.itel.smartkey.service.DBService;
import com.itel.smartkey.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 弹出菜单的recycleview的adapter，支持长按拖拽，但我们这里不需要，因此不启用。
 * Created by huorong.liang on 2017/1/18.
 */

public class DialogMenuFuncAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {

    private Context mContext;
    private List<Settings> mDatas;
    //add for icon lhr 2017/2/4
    private DBService mDBService;//操作数据库的工具类

    private static final int VIEW_TYPE_ISFUNCTION = 1;
    private static final int VIEW_TYPE_NOTFUNCTION = 2;
    private OnItemClickListener onItemClickListener;

    //item的点击事件
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public DialogMenuFuncAdapter(Context mContext, List<Settings> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;

        //add for icon lhr 2017/2/4
        mDBService = new DBService(mContext);
    }

    public void addDatas(ArrayList<Settings> mDatas){
        this.mDatas.addAll(mDatas);
        this.notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {//如果具有功能，则为1；如果不具有功能是空的，则为2
        if (mDatas.get(position).getFuncId() == MyContants.NOT_FUNCTION) {//不具有功能，则该项的图标应该标识为＋
            return VIEW_TYPE_NOTFUNCTION;
        } else {
            return VIEW_TYPE_ISFUNCTION;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ISFUNCTION){
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_layout_rv_dialogmenu_name_with_icon, parent, false);
            return new FunctionViewHolder(itemView);
        }else if (viewType == VIEW_TYPE_NOTFUNCTION){
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_layout_rv_toolbox_nofunction, parent, false);
            return new NotFunctionViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null){
                    Log.d("LHRTAG", "你点击了 " + position + "项，布局中的真实位置为 " + holder.getLayoutPosition() );
                    onItemClickListener.itemClick(holder.itemView, position);
                }
            }
        });
        if (holder instanceof FunctionViewHolder){//如果是有功能的item
            final FunctionViewHolder mHolder = (FunctionViewHolder) holder;
            //add for icon lhr 2017/2/4 {
            //读取名称
            String itemName = mDatas.get(position).getFuncAcName();
            Log.d("LHRTAG", "is itemName null" + (itemName != null ? "false" : "true"));
            if (itemName != null){
                mHolder.tv_toolbox_name_with_icon.setText(itemName);
            }else {
                Function functionBean = mDBService.findFunction(mDatas.get(position).getFuncId() + "");
                Log.d("LHRTAG", "functionBean Id " + functionBean.getId() );
                String name = functionBean.getName();
                Log.d("LHRTAG", "name " + name);
                mHolder.tv_toolbox_name_with_icon.setText(Utils.getStringById(mContext, name));
            }
            //add for icon lhr 2017/2/4 {
            //读取图标
            byte[] imageBytes = mDatas.get(position).getFuncAcIconBytes();
            Log.d("LHRTAG", "is imageBytes null" + (imageBytes != null ? "false" : "true"));
            if (imageBytes != null){//如果表2中保存有图标，则从表2中读取图标并设置
                Glide.with(mContext).load(imageBytes).into(mHolder.iv_toolbox_name_with_icon);
            } else{//从表1中获取图标
                Function functionBean = mDBService.findFunction(mDatas.get(position).getFuncId() + "");
                Log.d("LHRTAG", "functionBean Id " + functionBean.getId() );
                int iconId = Utils.getDrawableIdByString(mContext, functionBean.getIcon() + "_toolbox");
                Log.d("LHRTAG", "iconId " + iconId);
                Glide.with(mContext).load(iconId).into(mHolder.iv_toolbox_name_with_icon);
            }
            //add end }
        }

        if (holder instanceof NotFunctionViewHolder){//如果没有功能的item,则什么都不显示
            NotFunctionViewHolder mHolder = (NotFunctionViewHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //调换位置的时候震动70毫秒
        Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(70);
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mDatas, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mDatas, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {

    }

    class FunctionViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_toolbox_name_with_icon;
        TextView tv_toolbox_name_with_icon;

        public FunctionViewHolder(View itemView) {
            super(itemView);
            iv_toolbox_name_with_icon = (ImageView) itemView.findViewById(R.id.iv_toolbox_name_with_icon);
            tv_toolbox_name_with_icon = (TextView) itemView.findViewById(R.id.tv_toolbox_name_with_icon);
        }
    }

    class NotFunctionViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_toolbox_nofunctin;

        public NotFunctionViewHolder(View itemView) {
            super(itemView);
            iv_toolbox_nofunctin = (ImageView) itemView.findViewById(R.id.iv_toolbox_nofunctin);
        }
    }

    public interface OnItemClickListener{
        void itemClick(View view, int position);
    }

}
