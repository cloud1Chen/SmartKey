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
 * 工具箱里的recycleview的adapter，支持长按拖拽
 * Created by huorong.liang on 2017/1/18.
 */

public class FrontToolboxFuncAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {

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

    public FrontToolboxFuncAdapter(Context mContext, List<Settings> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;

        //add for icon lhr 2017/2/4
        mDBService = new DBService(mContext);
    }

    public void addDatas(ArrayList<Settings> mDatas){
        this.mDatas.addAll(mDatas);
        this.notifyDataSetChanged();
    }

    public void updateDatas(ArrayList<Settings> mDatas){
        this.mDatas.clear();
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
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_layout_rv_toolbox_name_with_icon, parent, false);
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
                    onItemClickListener.itemClick(holder.itemView, holder.getLayoutPosition());
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


            mHolder.iv_toolbox_name_with_icon_delete.setOnClickListener(new View.OnClickListener() {//删除按钮的点击事件
                @Override
                public void onClick(View view) {
                    int truePosition = holder.getLayoutPosition();
                    Settings settignsBean = mDatas.get(truePosition);
                    int mSettingsFuncAcId = settignsBean.getFuncAcId();//获取该item的funcAcId并保存下来
                    mDatas.remove(truePosition);
                    notifyItemRemoved(truePosition);//展示删除的动画
                    Settings bean = new Settings();
                    bean.setFuncAcId(mSettingsFuncAcId);//把刚才获取的funcAcId赋值给新的SettingsBean
                    bean.setFuncId(MyContants.NOT_FUNCTION);
                    Log.d("LHRTAG", "FrontToolboxFuncAdapter FuncAcId " + mSettingsFuncAcId);
                    mDatas.add(bean);
                    if (truePosition != mDatas.size()) {
                        notifyItemRangeChanged(truePosition, mDatas.size() - truePosition);//对于被删掉的位置及其后range大小范围内的view进行重新onBindViewHolder
                    }
                    setSettingsBeanToDb();
                    Log.d("LHRTAG", "positon " + position);
                    Log.d("LHRTAG", "truePosition " + truePosition);
                    Log.d("LHRTAG", "mDatas size " + mDatas.size());
                }
            });
        }

        if (holder instanceof NotFunctionViewHolder){//如果没有功能的item
            NotFunctionViewHolder mHolder = (NotFunctionViewHolder) holder;
            Glide.with(mContext).load(R.mipmap.pic_add).into(mHolder.iv_toolbox_nofunctin);
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
        setSettingsBeanToDb();
        Log.d("LHRTAG", "FrontToolboxFuncAdapter fromPosition " + fromPosition);
        Log.d("LHRTAG", "FrontToolboxFuncAdapter toPosition " + toPosition);
    }

    /**
     * 排序或者删除某一项导致mDatas数据集发生改变时，
     * 把最新的settings保存到数据库表2("set_table")中
     */
    private void setSettingsBeanToDb() {
        for (int i = 0; i<mDatas.size(); i++ ){
            int idIncre = i + 3;
            int reault = mDBService.updateSettingsById(mDatas.get(i), idIncre);
            Log.d("LHRTAG", "FrontToolboxFuncAdapter reault " + reault);
            Log.d("LHRTAG", "FrontToolboxFuncAdapter mDatas.funcId " + mDatas.get(i).getFuncId());
            Log.d("LHRTAG", "FrontToolboxFuncAdapter mDatas.funcAcId " + mDatas.get(i).getFuncAcId());
        }
    }

    @Override
    public void onItemDismiss(int position) {

    }

    /**
     * 具有功能的ViewHolder
     */
    class FunctionViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_toolbox_name_with_icon;
        ImageView iv_toolbox_name_with_icon_delete;
        TextView tv_toolbox_name_with_icon;

        public FunctionViewHolder(View itemView) {
            super(itemView);
            iv_toolbox_name_with_icon = (ImageView) itemView.findViewById(R.id.iv_toolbox_name_with_icon);
            iv_toolbox_name_with_icon_delete = (ImageView) itemView.findViewById(R.id.iv_toolbox_name_with_icon_delete);
            tv_toolbox_name_with_icon = (TextView) itemView.findViewById(R.id.tv_toolbox_name_with_icon);
        }
    }

    /**
     * 不具有功能的ViewHolder
     */
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
