package com.example.mypubliclibrary.widget.wheel;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.util.WindowUtils;
import com.example.mypubliclibrary.widget.wheel.adapters.WheelContentAdapter;
import com.example.mypubliclibrary.widget.wheel.constant.SelectViewConfig;
import com.example.mypubliclibrary.widget.wheel.interfaces.OnSelectChangedListener;
import com.example.mypubliclibrary.widget.wheel.interfaces.OnSelectData;

import java.util.List;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/7/30.
 */
public abstract class SelectView<T> implements OnSelectChangedListener, OnSelectData<T> {

    //AbstractSelectTextAdapter可以设置元素属性
    private View mPopView;

    private PopupWindow mPopWindow;


    //滚动栏1选择的内容
    protected T mSelectValueOne;

    //滚动栏2选择的内容
    protected T mSelectValueTwo;

    //滚动栏3选择的内容
    protected T mSelectValueThree;

    private TextView mTvConfirm;

    private TextView mTvTitle;

    protected int mSelectIndexOne;

    protected int mSelectIndexTwo;

    protected int mSelectIndexThree;

    //滚动栏1的View
    protected SelectWheelView mWlvSelectOne;

    //滚动栏2的View
    protected SelectWheelView mWlvSelectTwo;

    //滚动栏3的View
    protected SelectWheelView mWlvSelectThree;

    //滚动栏1的Adapter
    private WheelContentAdapter mContentAdapterOne;

    //滚动栏2的Adapter
    private WheelContentAdapter mContentAdapterTwo;

    //滚动栏3的Adapter
    private WheelContentAdapter mContentAdapterThree;

    //默认布局
    private int mDefaultLayoutId;

    private Context mContext;
    //滚动栏1的数据
    private List<T> mDataListOne;

    /**
     * 滚动栏2的数据
     */
    protected List<T> mDataListTwo;

    /**
     * 滚动栏3的数据
     */
    protected List<T> mDataListThree;


    protected SelectView(final Context mContext) {
        this.mContext = mContext;
        initView();
    }

    /**
     * 设置自定义布局
     *
     * @param layoutId
     * @return
     */
    public SelectView setItemResource(int layoutId) {
        this.mDefaultLayoutId = layoutId;
        return this;
    }


    private void initAdapterOne() {
        mWlvSelectOne.addChangingListener(this);
        mDataListOne = getDataListOne();
        mContentAdapterOne = new WheelContentAdapter<T>(mContext, mDataListOne);
        mSelectValueOne = mDataListOne.get(0);
        mSelectIndexOne = 0;
        mWlvSelectOne.setViewAdapter(mContentAdapterOne);
        setAdapterOneCentre();
    }


    /**
     * 设置上下的间距
     *
     * @param padding 间距
     * @return SelectView
     */
    public SelectView setPadding(int padding) {
        if (mContentAdapterOne != null)
            mContentAdapterOne.setPadding(padding);
        if (mContentAdapterTwo != null)
            mContentAdapterTwo.setPadding(padding);
        if (mContentAdapterThree != null)
            mContentAdapterThree.setPadding(padding);
        return this;
    }

    /**
     * 设置文本颜色
     *
     * @param color 颜色
     * @return SelectView
     */
    public SelectView setTextColor(int color) {
        if (mContentAdapterOne != null)
            mContentAdapterOne.setTextColor(color);
        if (mContentAdapterTwo != null)
            mContentAdapterTwo.setTextColor(color);
        if (mContentAdapterThree != null)
            mContentAdapterThree.setTextColor(color);
        return this;
    }

    /**
     * 设置请选择颜色
     *
     * @param color 颜色
     * @return SelectView
     */
    public SelectView setTitleColor(int color) {
        mTvTitle.setTextColor(color);
        return this;
    }

    /**
     * 设置完成颜色
     *
     * @param color 颜色
     * @return SelectView
     */
    public SelectView setDoneColor(int color) {
        mTvConfirm.setTextColor(color);
        return this;
    }

//    //滚动栏2的事件监听
//    private OnSelectChangedListener onSelectChangeTwoListener;

    //初始化滚动栏2
    private void initAdapterTwo() {
        mContentAdapterTwo = new WheelContentAdapter<T>(mContext, mDataListTwo);
        mSelectValueTwo = mDataListTwo.get(0);
        mSelectIndexTwo = 0;
        mWlvSelectTwo.setViewAdapter(mContentAdapterTwo);
        mWlvSelectTwo.setVisibility(View.VISIBLE);
        mWlvSelectTwo.addChangingListener(this);
        setAdapterTwoCentre();
        if ((mDataListThree = getDataListThree()) != null && mDataListThree.size() > 0 && mWlvSelectThree.getChangListenersLength() == 0) {
            initAdapterThree();
        }
    }

    @Override
    public List<T> getDataListTwo() {
        return null;
    }

    @Override
    public List<T> getDataListThree() {
        return null;
    }

    //初始化滚动栏3
    private void initAdapterThree() {
        mContentAdapterThree = new WheelContentAdapter<T>(mContext, mDataListThree);
        mSelectValueThree = mDataListThree.get(0);
        mSelectIndexThree = 0;
        mWlvSelectThree.setViewAdapter(mContentAdapterThree);
        mWlvSelectThree.setVisibility(View.VISIBLE);
        mWlvSelectThree.addChangingListener(this);
        setAdapterThreeCentre();
    }

    /**
     * 设置滚动栏2位置居中
     */
    private void setAdapterTwoCentre() {
        //如果是循环显示，并且总数大于4，就居中
        int countTwo = mDataListTwo.size();
        if (mWlvSelectTwo.isCyclic && countTwo >= 4) {
            setCurrentItemTwo(countTwo / 2);
        } else {
            mWlvSelectTwo.setCurrentItem(0, false);
        }
    }

    /**
     * 设置滚动栏3位置居中
     */
    private void setAdapterThreeCentre() {
        //如果是循环显示，并且总数大于4，就居中
        int countTwo = mDataListThree.size();
        if (mWlvSelectThree.isCyclic && countTwo >= 4) {
            setCurrentItemThree(countTwo / 2);
        } else {
            mWlvSelectThree.setCurrentItem(0, false);
        }
    }

    /**
     * 设置滚动栏1位置居中
     */
    private void setAdapterOneCentre() {
        //如果是循环显示，并且总数大于4，就居中
        int countOne = mDataListOne.size();
        if (mWlvSelectOne.isCyclic() && countOne >= 4) {
            setCurrentItemOne(countOne / 2);
        } else {
            mWlvSelectOne.setCurrentItem(0, false);
        }
    }

    protected void updateAdapterTwo() {
        if (mContentAdapterTwo == null && mWlvSelectTwo.getChangListenersLength() == 0) {
            initAdapterTwo();
        } else {
            if (mWlvSelectTwo.getVisibility() == View.GONE)
                mWlvSelectTwo.setVisibility(View.VISIBLE);
            mContentAdapterTwo = new WheelContentAdapter<T>(mContext, mDataListTwo);
            mWlvSelectTwo.setViewAdapter(mContentAdapterTwo);
            mSelectIndexTwo = 0;
            mSelectValueTwo = mDataListTwo.get(mSelectIndexTwo);
            setAdapterTwoCentre();
        }
    }

    protected void updateAdapterThree() {
        if (mContentAdapterThree == null && mWlvSelectThree.getChangListenersLength() == 0) {
            initAdapterThree();
        } else {
            if (mWlvSelectThree.getVisibility() == View.GONE)
                mWlvSelectThree.setVisibility(View.VISIBLE);
            mContentAdapterThree = new WheelContentAdapter<T>(mContext, mDataListThree);
            mWlvSelectThree.setViewAdapter(mContentAdapterThree);
            mSelectValueThree = mDataListThree.get(0);
            mSelectIndexThree = 0;
            setAdapterThreeCentre();
        }
    }

    private void initView() {
        if (mPopView == null) {
            mPopView = LayoutInflater.from(mContext).inflate(R.layout.dialog_wheel_select, null);
            mPopWindow = WindowUtils.getPopupWindow(mContext, mPopView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mWlvSelectOne = mPopView.findViewById(R.id.wlv_select_one);
            mWlvSelectTwo = mPopView.findViewById(R.id.wlv_select_two);
            mWlvSelectThree = mPopView.findViewById(R.id.wlv_select_three);
            mTvConfirm = mPopView.findViewById(R.id.tv_confirm);
            mTvTitle = mPopView.findViewById(R.id.tv_title);
            initAdapterOne();
            if ((mDataListTwo = getDataListTwo()) != null && mDataListTwo.size() > 0 && mWlvSelectTwo.getChangListenersLength() == 0) {
                initAdapterTwo();
            }
            mPopView.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectAchieve();
                    mPopWindow.dismiss();
                }
            });
        }
    }


    public SelectView setConfirm(String confirm) {
        if (!confirm.trim().isEmpty()) {
            mTvConfirm.setText(confirm);
        }
        return this;
    }

    public SelectView setTitle(String title) {
        if (!title.trim().isEmpty()) {
            mTvTitle.setText(title);
        }
        return this;
    }

    public SelectView show() {
        WindowUtils.setBackgroundAlpha(mContext, 0.5f);
        mPopWindow.showAtLocation(mPopView, Gravity.BOTTOM, 0, 0);
        return this;
    }


    @Override
    public void onChanged(SelectWheelView selectView, int lastIndex, int selectIndex) {
        if (selectView == mWlvSelectOne) {
            this.mSelectIndexOne = selectIndex;
//            mSelectValueOne = mContentAdapterOne.getItemText(selectIndex).toString();
            mSelectValueOne = mDataListOne.get(selectIndex);
        } else if (selectView == mWlvSelectTwo) {
            mSelectIndexTwo = selectIndex;
            mSelectValueTwo = mDataListTwo.get(selectIndex);
        } else if (selectView == mWlvSelectThree) {
            mSelectIndexThree = selectIndex;
            mSelectValueThree = mDataListThree.get(selectIndex);
        }
    }

    /**
     * 设置循环显示
     *
     * @param cyclic 为空所有不循环,不为空设置指定循环
     *               赋值枚举SelectViewConfig.CYCLIC_ONE
     * @return SelectView
     */
    public SelectView setCyclic(int... cyclic) {
        if (cyclic.length == 0) {
            if (mWlvSelectOne != null) mWlvSelectOne.setCyclic(false);
            if (mWlvSelectTwo != null) mWlvSelectTwo.setCyclic(false);
            if (mWlvSelectThree != null) mWlvSelectThree.setCyclic(false);
            return this;
        }
        for (int data : cyclic) {
            if (data == SelectViewConfig.CYCLIC_ONE && mWlvSelectOne != null)
                mWlvSelectOne.setCyclic(true);
            if (data == SelectViewConfig.CYCLIC_TWO && mWlvSelectTwo != null)
                mWlvSelectTwo.setCyclic(true);
            if (data == SelectViewConfig.CYCLIC_THREE && mWlvSelectThree != null)
                mWlvSelectThree.setCyclic(true);
        }
        return this;
    }

    /**
     * 设置滚动栏为大容量数据,如果是大容量数据,则滑动距离变长
     * 赋值 枚举SelectViewConfig.LONG_DATA_ONE
     *
     * @param longData 为空是设置所有,不为空设置指定滚动栏
     * @return SelectView
     */
    public SelectView setLongData(int... longData) {
        if (longData.length == 0) {
            if (mWlvSelectOne != null) mWlvSelectOne.setLongData();
            if (mWlvSelectTwo != null) mWlvSelectTwo.setLongData();
            if (mWlvSelectTwo != null) mWlvSelectThree.setLongData();
            return this;
        }
        for (int data : longData) {
            if (data == SelectViewConfig.LONG_DATA_ONE && mWlvSelectOne != null)
                mWlvSelectOne.setLongData();
            if (data == SelectViewConfig.LONG_DATA_TWO && mWlvSelectTwo != null)
                mWlvSelectTwo.setLongData();
            if (data == SelectViewConfig.LONG_DATA_THREE && mWlvSelectThree != null)
                mWlvSelectThree.setLongData();
        }
        return this;
    }

    /**
     * 设置滚动栏1当前项。当索引出错时什么也不做。
     */
    public SelectView setCurrentItemOne(int index) {
        mWlvSelectOne.setCurrentItem(index, false);
        mSelectIndexOne = index;
        return this;
    }

    /**
     * 设置滚动栏2当前项。当索引出错时什么也不做。
     */
    public SelectView setCurrentItemTwo(int index) {
        mWlvSelectTwo.setCurrentItem(index, false);
        mSelectIndexTwo = index;
        mSelectValueTwo = mDataListTwo.get(index);
        return this;
    }

    /**
     * 设置滚动栏3当前项。当索引出错时什么也不做。
     */
    public SelectView setCurrentItemThree(int index) {
        mWlvSelectThree.setCurrentItem(index, false);
        mSelectIndexThree = index;
        mSelectValueThree = mDataListThree.get(index);
        return this;
    }


    /**
     * 根据值设置滚动栏1当前项。不存在时什么也不做。
     */
    public SelectView setCurrentItemOne(T selectValue) {
        List<T> list = mDataListOne;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().equals(selectValue.toString())) {
                mWlvSelectOne.setCurrentItem(i, false);
                break;
            }
        }
        return this;
    }


    /**
     * 根据值设置滚动栏2当前项。不存在时什么也不做。
     */
    public SelectView setCurrentItemTwo(T selectValue) {
        List<T> list = mDataListTwo;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().equals(selectValue.toString())) {
                mWlvSelectTwo.setCurrentItem(i, false);
                break;
            }
        }
        return this;
    }

    /**
     * 根据值设置滚动栏3当前项。不存在时什么也不做。
     */
    public SelectView setCurrentItemThree(T selectValue) {
        List<T> list = mDataListThree;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().equals(selectValue.toString())) {
                mWlvSelectThree.setCurrentItem(i, false);
                break;
            }
        }
        return this;
    }


    protected abstract List<T> getDataListOne();

    protected abstract void selectAchieve();
}
