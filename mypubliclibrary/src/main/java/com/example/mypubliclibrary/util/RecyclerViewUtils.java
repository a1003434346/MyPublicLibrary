package com.example.mypubliclibrary.util;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.List;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/7/18.
 */
public class RecyclerViewUtils {

    /**
     * 初始化RecyclerView
     *
     * @param context      context
     * @param recyclerView recyclerView
     * @param orientation  排列方向 枚举值：LinearLayoutManager.VERTICAL
     * @param adapter      adapter
     */
    public static void initRecyclerView(Context context, RecyclerView recyclerView, int orientation, RecyclerView.Adapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        //调整RecyclerView的排列方向
        layoutManager.setOrientation(orientation);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 更新数据
     *
     * @param oldData 数据源
     * @param newData 新的数据
     * @param adapter adapter
     */
    public static <T> void updateData(List<T> oldData, List<T> newData, CommonAdapter adapter, boolean... clearOldData) {
        ListUtils.updateListData(oldData, newData, clearOldData);
        adapter.notifyDataSetChanged();
    }

}
