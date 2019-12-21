package com.example.mypubliclibrary.widget.password;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mypubliclibrary.R;

/**
 * 功能:
 * Created By leeyushi on 2019/12/21.
 */
public class KeyboardAdapter extends MyRecyclerViewAdapter<String> {
    /** 数字按钮条目 */
    private  final int TYPE_NORMAL = 0;
    /** 删除按钮条目 */
    private  final int TYPE_DELETE = 1;
    /** 空按钮条目 */
    private  final int TYPE_EMPTY = 2;

    public KeyboardAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 9:
                return TYPE_EMPTY;
            case 11:
                return TYPE_DELETE;
            default:
                return TYPE_NORMAL;
        }
    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_DELETE:
                return new MyRecyclerViewAdapter.SimpleHolder(R.layout.item_pay_password_delete);
            case TYPE_EMPTY:
                return new MyRecyclerViewAdapter.SimpleHolder(R.layout.item_pay_password_empty);
            default:
                return new KeyboardAdapter.ViewHolder();
        }
    }

    final class ViewHolder extends MyRecyclerViewAdapter.ViewHolder {

        private final TextView mTextView;

        ViewHolder() {
            super(R.layout.item_pay_password_normal);
            mTextView = (TextView) getItemView();
        }

        @Override
        public void onBindView(int position) {
            mTextView.setText(getItem(position));
        }
    }
}
