package com.example.mypubliclibrary.widget.dialog.basic;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.widget.dialog.build.BuildPasswordAttribute;
import com.example.mypubliclibrary.widget.password.BaseRecyclerViewAdapter;
import com.example.mypubliclibrary.widget.password.KeyboardAdapter;
import com.example.mypubliclibrary.widget.password.PasswordView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;

import java.util.Arrays;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/12/2
 * desc   : 支付密码对话框
 */
public abstract class PasswordPayDialog extends BottomPopupView implements BaseRecyclerViewAdapter.OnItemClickListener, View.OnClickListener {
    private XPopup.Builder mPopUp;
    private BuildPasswordAttribute mBuildAttribute;

    public PasswordPayDialog(@NonNull Context context) {
        super(context);
        mPopUp = new XPopup.Builder(getContext()).enableDrag(true);
    }

    @Override
    protected void onCreate() {
        initView();
    }

    @Override
    protected void onDismiss() {

    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_pay_password;
    }

    @Override
    protected void onShow() {

    }

    /**
     * 显示窗口
     *
     * @return BottomIosDialog
     */
    public PasswordPayDialog show() {
        mPopUp.hasShadowBg(mBuildAttribute.isWindowShadow)
                .dismissOnTouchOutside(mBuildAttribute.isCancel)
                .asCustom(this)
                .showWindow();
        return this;
    }


    public PasswordPayDialog setViewAttribute(BuildPasswordAttribute viewAttribute) {
        mBuildAttribute = viewAttribute;
        return this;
    }


    private void initView() {
        mTitleView = findViewById(R.id.tv_pay_title);
        mCloseView = findViewById(R.id.iv_pay_close);
        mSubTitleView = findViewById(R.id.tv_pay_sub_title);
        mMoneyView = findViewById(R.id.tv_pay_money);
        mPasswordView = findViewById(R.id.pw_pay_view);
        mRecyclerView = findViewById(R.id.rv_pay_list);
        initData();
    }

    private void initData() {
        mCloseView.setOnClickListener(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mAdapter = new KeyboardAdapter(getContext());
        mAdapter.setData(Arrays.asList(mBuildAttribute.getKeyboardText()));
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mTitleView.setText(mBuildAttribute.getTitle());
        mSubTitleView.setText(mBuildAttribute.getHintText());
        mSubTitleView.setTextSize(mBuildAttribute.getHintSize());
        mSubTitleView.setTextColor(mBuildAttribute.getHintColor());
        mMoneyView.setText(mBuildAttribute.getMoney());
    }

    private TextView mTitleView;
    private ImageView mCloseView;
    private TextView mSubTitleView;
    private TextView mMoneyView;
    private PasswordView mPasswordView;

    private RecyclerView mRecyclerView;

    private KeyboardAdapter mAdapter;

    @Override
    public void onClick(View v) {
        if (v == mCloseView) {
            if (mBuildAttribute.getAutoDismiss()) dismiss();
        }
    }


    /**
     * 输入完成时回调
     *
     * @param password 六位支付密码
     */
    protected abstract void onCompleted(String password);

    /**
     * 数字按钮条目
     */
    private final int TYPE_NORMAL = 0;
    /**
     * 删除按钮条目
     */
    private final int TYPE_DELETE = 1;
    /**
     * 空按钮条目
     */
    private final int TYPE_EMPTY = 2;

    /**
     * {@link BaseRecyclerViewAdapter.OnItemClickListener}
     */
    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        switch (mAdapter.getItemViewType(position)) {
            case TYPE_DELETE:
                // 点击回退按钮删除
                if (mBuildAttribute.getRecordList().size() != 0) {
                    mBuildAttribute.getRecordList().removeLast();
                }
                break;
            case TYPE_EMPTY:
                // 点击空白的地方不做任何操作
                break;
            default:
                // 判断密码是否已经输入完毕
                if (mBuildAttribute.getRecordList().size() < PasswordView.PASSWORD_COUNT) {
                    // 点击数字，显示在密码行
                    mBuildAttribute.getRecordList().add(mBuildAttribute.getKeyboardText()[position]);
                }

                // 判断密码是否已经输入完毕
                if (mBuildAttribute.getRecordList().size() == PasswordView.PASSWORD_COUNT) {
                    if (mBuildAttribute.getAutoDismiss()) {
                        dismiss();
                    }
                    // 获取输入的支付密码
                    StringBuilder password = new StringBuilder();
                    for (String s : mBuildAttribute.getRecordList()) {
                        password.append(s);
                    }
                    onCompleted(password.toString());
                }
                break;
        }
        mPasswordView.setPassWordLength(mBuildAttribute.getRecordList().size());
    }
}
