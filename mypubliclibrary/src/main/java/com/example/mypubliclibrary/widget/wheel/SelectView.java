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



    private View popView;

    private PopupWindow popWindow;

    //滚动栏1选择的内容
    protected T selectValueOne;

    //滚动栏2选择的内容
    protected T selectValueTwo;

    //滚动栏3选择的内容
    protected T selectValueThree;

    private TextView tvConfirm;

    private TextView tvTitle;

    protected int selectIndexOne;

    protected int selectIndexTwo;

    protected int selectIndexThree;

    //滚动栏1的View
    protected SelectWheelView wlvSelectOne;

    //滚动栏2的View
    protected SelectWheelView wlvSelectTwo;

    //滚动栏3的View
    protected SelectWheelView wlvSelectThree;

    //滚动栏1的Adapter
    private WheelContentAdapter contentAdapterOne;

    //滚动栏2的Adapter
    private WheelContentAdapter contentAdapterTwo;

    //滚动栏3的Adapter
    private WheelContentAdapter contentAdapterThree;

    private int defaultLayoutId;

    private Context context;
    //滚动栏1的数据
    private List<T> dataListOne;

    /**
     * 滚动栏2的数据
     */
    protected List<T> dataListTwo;

    /**
     * 滚动栏3的数据
     */
    protected List<T> dataListThree;


    protected SelectView(final Context context) {
        this.context = context;
        initView();
    }

    public SelectView setItemResource(int layoutId) {
        this.defaultLayoutId = layoutId;
        return this;
    }


    private void initAdapterOne() {
        wlvSelectOne.addChangingListener(this);
        dataListOne = getDataListOne();
        contentAdapterOne = new WheelContentAdapter<T>(context, dataListOne);
        selectValueOne = dataListOne.get(0);
        selectIndexOne = 0;
        wlvSelectOne.setViewAdapter(contentAdapterOne);
        setAdapterOneCentre();
    }
//    //滚动栏2的事件监听
//    private OnSelectChangedListener onSelectChangeTwoListener;

    //初始化滚动栏2
    private void initAdapterTwo() {
        contentAdapterTwo = new WheelContentAdapter<T>(context, dataListTwo);
        selectValueTwo = dataListTwo.get(0);
        selectIndexTwo = 0;
        wlvSelectTwo.setViewAdapter(contentAdapterTwo);
        wlvSelectTwo.setVisibility(View.VISIBLE);
        wlvSelectTwo.addChangingListener(this);
        setAdapterTwoCentre();
        if ((dataListThree = getDataListThree()) != null && dataListThree.size() > 0 && wlvSelectThree.getChangListenersLength() == 0) {
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
        contentAdapterThree = new WheelContentAdapter<T>(context, dataListThree);
        selectValueThree = dataListThree.get(0);
        selectIndexThree = 0;
        wlvSelectThree.setViewAdapter(contentAdapterThree);
        wlvSelectThree.setVisibility(View.VISIBLE);
        wlvSelectThree.addChangingListener(this);
        setAdapterThreeCentre();
    }

    /**
     * 设置滚动栏2位置居中
     */
    private void setAdapterTwoCentre() {
        //如果是循环显示，并且总数大于4，就居中
        int countTwo = dataListTwo.size();
        if (wlvSelectTwo.isCyclic && countTwo >= 4) {
            setCurrentItemTwo(countTwo / 2);
        } else {
            wlvSelectTwo.setCurrentItem(0, false);
        }
    }

    /**
     * 设置滚动栏3位置居中
     */
    private void setAdapterThreeCentre() {
        //如果是循环显示，并且总数大于4，就居中
        int countTwo = dataListThree.size();
        if (wlvSelectThree.isCyclic && countTwo >= 4) {
            setCurrentItemThree(countTwo / 2);
        } else {
            wlvSelectThree.setCurrentItem(0, false);
        }
    }

    /**
     * 设置滚动栏1位置居中
     */
    private void setAdapterOneCentre() {
        //如果是循环显示，并且总数大于4，就居中
        int countOne = dataListOne.size();
        if (wlvSelectOne.isCyclic() && countOne >= 4) {
            setCurrentItemOne(countOne / 2);
        } else {
            wlvSelectOne.setCurrentItem(0, false);
        }
    }

    protected void updateAdapterTwo() {
        if (contentAdapterTwo == null && wlvSelectTwo.getChangListenersLength() == 0) {
            initAdapterTwo();
        } else {
            if (wlvSelectTwo.getVisibility() == View.GONE) wlvSelectTwo.setVisibility(View.VISIBLE);
            contentAdapterTwo = new WheelContentAdapter<T>(context, dataListTwo);
            wlvSelectTwo.setViewAdapter(contentAdapterTwo);
            selectIndexTwo = 0;
            selectValueTwo = dataListTwo.get(selectIndexTwo);
            setAdapterTwoCentre();
        }
    }

    protected void updateAdapterThree() {
        if (contentAdapterThree == null && wlvSelectThree.getChangListenersLength() == 0) {
            initAdapterThree();
        } else {
            if (wlvSelectThree.getVisibility() == View.GONE)
                wlvSelectThree.setVisibility(View.VISIBLE);
            contentAdapterThree = new WheelContentAdapter<T>(context, dataListThree);
            wlvSelectThree.setViewAdapter(contentAdapterThree);
            selectValueThree = dataListThree.get(0);
            selectIndexThree = 0;
            setAdapterThreeCentre();
        }
    }

    private void initView() {
        if (popView == null) {
            popView = LayoutInflater.from(context).inflate(R.layout.dialog_wheel_select, null);
            popWindow = WindowUtils.getPopupWindow(context, popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            wlvSelectOne = popView.findViewById(R.id.wlv_select_one);
            wlvSelectTwo = popView.findViewById(R.id.wlv_select_two);
            wlvSelectThree = popView.findViewById(R.id.wlv_select_three);
            tvConfirm = popView.findViewById(R.id.tv_confirm);
            tvTitle = popView.findViewById(R.id.tv_title);
            initAdapterOne();
            if ((dataListTwo = getDataListTwo()) != null && dataListTwo.size() > 0 && wlvSelectTwo.getChangListenersLength() == 0) {
                initAdapterTwo();
            }
            popView.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectAchieve();
                    popWindow.dismiss();
                }
            });
        }
    }


    public SelectView setConfirm(String confirm) {
        if (!confirm.trim().isEmpty()) {
            tvConfirm.setText(confirm);
        }
        return this;
    }

    public SelectView setTitle(String title) {
        if (!title.trim().isEmpty()) {
            tvTitle.setText(title);
        }
        return this;
    }

    public SelectView show() {
        WindowUtils.setBackgroundAlpha(context, 0.5f);
        popWindow.showAtLocation(popView, Gravity.BOTTOM, 0, 0);
        return this;
    }


    @Override
    public void onChanged(SelectWheelView selectView, int lastIndex, int selectIndex) {
        if (selectView == wlvSelectOne) {
            this.selectIndexOne = selectIndex;
//            selectValueOne = contentAdapterOne.getItemText(selectIndex).toString();
            selectValueOne = dataListOne.get(selectIndex);
        } else if (selectView == wlvSelectTwo) {
            selectIndexTwo = selectIndex;
            selectValueTwo = dataListTwo.get(selectIndex);
        } else if (selectView == wlvSelectThree) {
            selectIndexThree = selectIndex;
            selectValueThree = dataListThree.get(selectIndex);
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
            if (wlvSelectOne != null) wlvSelectOne.setCyclic(false);
            if (wlvSelectTwo != null) wlvSelectTwo.setCyclic(false);
            if (wlvSelectThree != null) wlvSelectThree.setCyclic(false);
            return this;
        }
        for (int data : cyclic) {
            if (data == SelectViewConfig.CYCLIC_ONE && wlvSelectOne != null)
                wlvSelectOne.setCyclic(true);
            if (data == SelectViewConfig.CYCLIC_TWO && wlvSelectTwo != null)
                wlvSelectTwo.setCyclic(true);
            if (data == SelectViewConfig.CYCLIC_THREE && wlvSelectThree != null)
                wlvSelectThree.setCyclic(true);
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
            if (wlvSelectOne != null) wlvSelectOne.setLongData();
            if (wlvSelectTwo != null) wlvSelectTwo.setLongData();
            if (wlvSelectTwo != null) wlvSelectThree.setLongData();
            return this;
        }
        for (int data : longData) {
            if (data == SelectViewConfig.LONG_DATA_ONE && wlvSelectOne != null)
                wlvSelectOne.setLongData();
            if (data == SelectViewConfig.LONG_DATA_TWO && wlvSelectTwo != null)
                wlvSelectTwo.setLongData();
            if (data == SelectViewConfig.LONG_DATA_THREE && wlvSelectThree != null)
                wlvSelectThree.setLongData();
        }
        return this;
    }

    /**
     * 设置滚动栏1当前项。当索引出错时什么也不做。
     */
    public SelectView setCurrentItemOne(int index) {
        wlvSelectOne.setCurrentItem(index, false);
        selectIndexOne = index;
        return this;
    }

    /**
     * 设置滚动栏2当前项。当索引出错时什么也不做。
     */
    public SelectView setCurrentItemTwo(int index) {
        wlvSelectTwo.setCurrentItem(index, false);
        selectIndexTwo = index;
        selectValueTwo = dataListTwo.get(index);
        return this;
    }

    /**
     * 设置滚动栏3当前项。当索引出错时什么也不做。
     */
    public SelectView setCurrentItemThree(int index) {
        wlvSelectThree.setCurrentItem(index, false);
        selectIndexThree = index;
        selectValueThree = dataListThree.get(index);
        return this;
    }


    /**
     * 根据值设置滚动栏1当前项。不存在时什么也不做。
     */
    public SelectView setCurrentItemOne(T selectValue) {
        List<T> list = dataListOne;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().equals(selectValue.toString())) {
                wlvSelectOne.setCurrentItem(i, false);
                break;
            }
        }
        return this;
    }


    /**
     * 根据值设置滚动栏2当前项。不存在时什么也不做。
     */
    public SelectView setCurrentItemTwo(T selectValue) {
        List<T> list = dataListTwo;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().equals(selectValue.toString())) {
                wlvSelectTwo.setCurrentItem(i, false);
                break;
            }
        }
        return this;
    }

    /**
     * 根据值设置滚动栏3当前项。不存在时什么也不做。
     */
    public SelectView setCurrentItemThree(T selectValue) {
        List<T> list = dataListThree;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().equals(selectValue.toString())) {
                wlvSelectThree.setCurrentItem(i, false);
                break;
            }
        }
        return this;
    }


    protected abstract List<T> getDataListOne();

    protected abstract void selectAchieve();
}
