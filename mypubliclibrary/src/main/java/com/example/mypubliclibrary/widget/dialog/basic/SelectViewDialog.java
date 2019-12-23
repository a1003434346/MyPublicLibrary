package com.example.mypubliclibrary.widget.dialog.basic;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.widget.dialog.build.BuildSelectTextAttribute;
import com.example.mypubliclibrary.widget.wheel.SelectWheelView;
import com.example.mypubliclibrary.widget.wheel.adapters.WheelContentAdapter;
import com.example.mypubliclibrary.widget.wheel.constant.SelectViewConfig;
import com.example.mypubliclibrary.widget.wheel.interfaces.OnSelectChangedListener;
import com.example.mypubliclibrary.widget.wheel.interfaces.OnSelectData;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;

import java.util.List;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/7/30.
 */
public abstract class SelectViewDialog<T> extends BottomPopupView implements OnSelectChangedListener {


    private XPopup.Builder mPopUp;


    private TextView mTvConfirm;

    private TextView mTvTitle;


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


    protected SelectViewDialog(final Context context) {
        super(context);
        this.mContext = context;
        mPopUp = new XPopup.Builder(getContext()).enableDrag(true);
    }

    /**
     * 设置自定义布局
     *
     * @param layoutId
     * @return
     */
    public SelectViewDialog setItemResource(int layoutId) {
        this.mDefaultLayoutId = layoutId;
        return this;
    }


    private void initAdapterOne() {
        mWlvSelectOne.addChangingListener(this);
        mContentAdapterOne = new WheelContentAdapter<T>(mContext, mViewAttribute.getListOne());
        mViewAttribute.mSelectValueOne = mViewAttribute.getListOne().get(0);
        mViewAttribute.setSelectIndexOne(0);
        mWlvSelectOne.setViewAdapter(mContentAdapterOne);
        setAdapterOneCentre();
    }


    /**
     * 设置上下的间距
     *
     * @return SelectView
     */
    private void setPadding() {
        if (mContentAdapterOne != null)
            mContentAdapterOne.setPadding(mViewAttribute.getPadding());
        if (mContentAdapterTwo != null)
            mContentAdapterTwo.setPadding(mViewAttribute.getPadding());
        if (mContentAdapterThree != null)
            mContentAdapterThree.setPadding(mViewAttribute.getPadding());
    }

    /**
     * 设置文本颜色
     *
     * @return SelectView
     */
    private void setTextColor() {
        if (mContentAdapterOne != null)
            mContentAdapterOne.setTextColor(mViewAttribute.getTextColor());
        if (mContentAdapterTwo != null)
            mContentAdapterTwo.setTextColor(mViewAttribute.getTextColor());
        if (mContentAdapterThree != null)
            mContentAdapterThree.setTextColor(mViewAttribute.getTextColor());
    }

    /**
     * 设置title颜色
     */
    private void setTitleColor() {
        mTvTitle.setTextColor(mViewAttribute.getTitleColor());
    }

    @Override
    protected void onDismiss() {

    }

    @Override
    protected void onShow() {

    }

    /**
     * 设置完成颜色
     */
    private void setDoneColor() {
        mTvConfirm.setTextColor(mViewAttribute.getDoneColor());
    }

//    //滚动栏2的事件监听
//    private OnSelectChangedListener onSelectChangeTwoListener;

    //初始化滚动栏2
    private void initAdapterTwo() {
        mContentAdapterTwo = new WheelContentAdapter<T>(mContext, mViewAttribute.getListTwo());
        mViewAttribute.mSelectValueTwo = mViewAttribute.getListTwo().get(0);
        mViewAttribute.setSelectIndexTwo(0);
        mWlvSelectTwo.setViewAdapter(mContentAdapterTwo);
        mWlvSelectTwo.setVisibility(View.VISIBLE);
        mWlvSelectTwo.addChangingListener(this);
        setAdapterTwoCentre();
        if ((mViewAttribute.getListThree() != null && mViewAttribute.getListThree().size() > 0 && mWlvSelectThree.getChangListenersLength() == 0)) {
            initAdapterThree();
        }
    }

    @Override
    protected void onCreate() {
        initView();
    }


    //初始化滚动栏3
    private void initAdapterThree() {
        mContentAdapterThree = new WheelContentAdapter<T>(mContext, mViewAttribute.getListThree());
        mViewAttribute.mSelectValueThree = mViewAttribute.getListThree().get(0);
        mViewAttribute.setSelectIndexThree(0);
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
        int countTwo = mViewAttribute.getListTwo().size();
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
        int countTwo = mViewAttribute.getListThree().size();
        if (mWlvSelectThree.isCyclic && countTwo >= 4) {
            setCurrentItemThree(countTwo / 2);
        } else {
            mWlvSelectThree.setCurrentItem(0, false);
        }
    }

    private void setCurrentItemOne(int index) {
        mWlvSelectOne.setCurrentItem(mViewAttribute.getCurrentItemOne(), false);
        mViewAttribute.setSelectIndexOne(mViewAttribute.getCurrentItemOne());
    }

    /**
     * 设置滚动栏1位置居中
     */
    private void setAdapterOneCentre() {
        //如果是循环显示，并且总数大于4，就居中
        int countOne = mViewAttribute.getListOne().size();
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
            mContentAdapterTwo = new WheelContentAdapter<T>(mContext, mViewAttribute.getListTwo());
            mWlvSelectTwo.setViewAdapter(mContentAdapterTwo);
            mViewAttribute.setSelectIndexTwo(0);
            mViewAttribute.mSelectValueTwo = mViewAttribute.getListTwo().get(mViewAttribute.getSelectIndexTwo());
            setAdapterTwoCentre();
        }
    }

    protected void updateAdapterThree() {
        if (mContentAdapterThree == null && mWlvSelectThree.getChangListenersLength() == 0) {
            initAdapterThree();
        } else {
            if (mWlvSelectThree.getVisibility() == View.GONE)
                mWlvSelectThree.setVisibility(View.VISIBLE);
            mContentAdapterThree = new WheelContentAdapter<T>(mContext, mViewAttribute.getListThree());
            mWlvSelectThree.setViewAdapter(mContentAdapterThree);
            mViewAttribute.mSelectValueThree = mViewAttribute.getListThree().get(0);
            mViewAttribute.setSelectIndexThree(0);
            setAdapterThreeCentre();
        }
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_wheel_select;
    }

    private void initView() {
        mWlvSelectOne = findViewById(R.id.wlv_select_one);
        mWlvSelectTwo = findViewById(R.id.wlv_select_two);
        mWlvSelectThree = findViewById(R.id.wlv_select_three);
        mTvConfirm = findViewById(R.id.tv_confirm);
        mTvTitle = findViewById(R.id.tv_title);
        initAdapterOne();
        if ((mViewAttribute.getListTwo() != null && mViewAttribute.getListTwo().size() > 0 && mWlvSelectTwo.getChangListenersLength() == 0)) {
            initAdapterTwo();
        }
        findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAchieve();
                dismiss();
            }
        });
        setCurrentItem();
        setTextColor();
        setPadding();
        setTitleColor();
        setDoneColor();
    }


    public SelectViewDialog setConfirm(String confirm) {
        if (!confirm.trim().isEmpty()) {
            mTvConfirm.setText(confirm);
        }
        return this;
    }

    public SelectViewDialog setTitle(String title) {
        if (!title.trim().isEmpty()) {
            mTvTitle.setText(title);
        }
        return this;
    }

    private BuildSelectTextAttribute mViewAttribute;

    public SelectViewDialog setViewAttribute(BuildSelectTextAttribute mViewAttribute) {
        this.mViewAttribute = mViewAttribute;
        return this;
    }

    /**
     * 显示窗口
     *
     * @return SelectView
     */
    public SelectViewDialog show() {
        mPopUp.hasShadowBg(mViewAttribute.isWindowShadow)
                .dismissOnTouchOutside(mViewAttribute.isCancel)
                .asCustom(this)
                .showWindow();
        return this;
    }


    @Override
    public void onChanged(SelectWheelView selectView, int lastIndex, int selectIndex) {
        if (selectView == mWlvSelectOne) {
            mViewAttribute.setSelectIndexOne(selectIndex);
//            mSelectValueOne = mContentAdapterOne.getItemText(selectIndex).toString();
            mViewAttribute.mSelectValueOne = mViewAttribute.getListOne().get(selectIndex);
        } else if (selectView == mWlvSelectTwo) {
            mViewAttribute.setSelectIndexTwo(selectIndex);
            mViewAttribute.mSelectValueTwo = mViewAttribute.getListTwo().get(selectIndex);
        } else if (selectView == mWlvSelectThree) {
            mViewAttribute.setSelectIndexThree(selectIndex);
            mViewAttribute.mSelectValueThree = mViewAttribute.getListThree().get(selectIndex);
        }
    }

    /**
     * 设置循环显示
     *
     * @param cyclic 为空所有不循环,不为空设置指定循环
     *               赋值枚举SelectViewConfig.CYCLIC_ONE
     * @return SelectView
     */
    public SelectViewDialog setCyclic(int... cyclic) {
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
    public SelectViewDialog setLongData(int... longData) {
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
     * 设置滚动栏当前项。当索引出错时什么也不做。
     * 可以用list集合.indexOf(对象名)获取索引
     *
     * @return
     */
    public SelectViewDialog setCurrentItem() {
        if (mViewAttribute.getCurrentItemOne() != -1 && mViewAttribute.getCurrentItemOne() < mViewAttribute.getListOne().size()) {
            mWlvSelectOne.setCurrentItem(mViewAttribute.getCurrentItemOne(), false);
            mViewAttribute.setSelectIndexOne(mViewAttribute.getCurrentItemOne());
        }
        if (mViewAttribute.getCurrentItemTwo() != -1 && mViewAttribute.getCurrentItemTwo() < mViewAttribute.getListTwo().size()) {
            mWlvSelectTwo.setCurrentItem(mViewAttribute.getCurrentItemTwo(), false);
            mViewAttribute.setSelectIndexTwo(mViewAttribute.getCurrentItemTwo());
        }
        if (mViewAttribute.getCurrentItemThree() != -1 && mViewAttribute.getCurrentItemThree() < mViewAttribute.getListThree().size()) {
            mWlvSelectThree.setCurrentItem(mViewAttribute.getCurrentItemThree(), false);
            mViewAttribute.setSelectIndexThree(mViewAttribute.getCurrentItemThree());
        }
        return this;
    }

    /**
     * 设置滚动栏2当前项。当索引出错时什么也不做。
     */
    private void setCurrentItemTwo(int index) {
        mWlvSelectTwo.setCurrentItem(index, false);
        mViewAttribute.setSelectIndexTwo(index);
        mViewAttribute.mSelectValueTwo = mViewAttribute.getListTwo().get(index);
    }

    /**
     * 设置滚动栏3当前项。当索引出错时什么也不做。
     */
    private void setCurrentItemThree(int index) {
        mWlvSelectThree.setCurrentItem(index, false);
        mViewAttribute.setSelectIndexThree(index);
        mViewAttribute.mSelectValueThree = mViewAttribute.getListThree().get(index);
    }


//    protected abstract List<T> getDataListOne();

    protected abstract void selectAchieve();
}
