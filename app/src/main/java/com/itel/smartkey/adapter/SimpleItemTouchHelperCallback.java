package com.itel.smartkey.adapter;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.itel.smartkey.listener.ItemTouchHelperAdapter;

/**
 * 使RecyclerView可以长按拖拽的ItemTouchHelper.Callback类
 * Created by huorong.liang on 2017/1/18.
 */

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
    ItemTouchHelperAdapter mItemTouchHelperAdapter;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter mItemTouchHelperAdapter) {
        this.mItemTouchHelperAdapter = mItemTouchHelperAdapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager){
            int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END;
            int swipeFlag = 0;
            return makeMovementFlags(dragFlag, swipeFlag);
        }else {
            int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlag, swipeFlag);
        }
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mItemTouchHelperAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            viewHolder.itemView.setBackgroundColor(Color.GRAY);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(0);
    }
}
