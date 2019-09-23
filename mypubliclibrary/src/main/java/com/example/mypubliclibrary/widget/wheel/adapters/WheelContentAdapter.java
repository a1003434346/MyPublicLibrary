package com.example.mypubliclibrary.widget.wheel.adapters;

import android.content.Context;

import java.util.List;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/7/30.
 */
public class WheelContentAdapter<T> extends AbstractSelectTextAdapter {
    public WheelContentAdapter(Context context, List<T> items) {
        super(context);
        this.items = items;
    }


    private List<T> items;


    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < this.items.size()) {
            T item = this.items.get(index);
            return item instanceof CharSequence ? (CharSequence) item : item.toString();
        } else {
            return null;
        }
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }
}
