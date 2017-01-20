package com.itel.smartkey.listener;

/**
 * 如果recycleview需要实现长按拖拽功能，则其adapter需要implements这个接口，根据需要实现以下接口方法
 * Created by huorong.liang on 2017/1/18.
 */

public interface ItemTouchHelperAdapter {
    //item拖拽时回调该方法，我么你需要在其中通知adapter调换mDatas数据的位置，以及通知跟新数据源
    void onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
