package com.example.mypubliclibrary.widget.dialog.basic;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.base.BasesActivity;
import com.example.mypubliclibrary.util.StringUtils;
import com.example.mypubliclibrary.util.WindowUtils;
import com.example.mypubliclibrary.widget.dialog.build.BuildInputAttribute;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;

/**
 * function:
 * describe: 评论Dialog
 * Created By LiQiang on 2019/7/29.
 */
public class InputDialogTest extends BottomPopupView {

    private XPopup.Builder mPopUp;
    private BuildInputAttribute mViewAttribute;
    private Context mContext;
    //输入内容
    private EditText etInputValue;
    //提交按钮,默认显示
    private Button btSend;
    //连接同步输入的view
    private TextView connectTextView;


    protected InputDialogTest(Context context) {
        super(context);
        this.mContext = context;
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
    protected void onShow() {

    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_comment_input;
    }

    //设置连接同步输入的view
    private void connectView() {
        //设置关联的TextView
        connectTextView = mViewAttribute.connectTextView();
    }

    private void initView() {
        btSend = findViewById(R.id.bt_send);
        etInputValue = findViewById(R.id.et_input_value);
        ((BasesActivity) mContext).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        //初始化输入方式,默认为单行输入模式,键盘右下角为完成
        etInputValue.setInputType(InputType.TYPE_CLASS_TEXT);
        etInputValue.setFocusable(true);
        etInputValue.requestFocus();
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDone();
            }
        });
        findViewById(R.id.ctl_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        etInputValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    selectDone();
                    return true;
                }
                return false;
            }
        });

        etInputValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    btSend.setBackground(mContext.getResources().getDrawable(R.drawable.send_true_bg));
                    btSend.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                } else {
                    btSend.setBackground(mContext.getResources().getDrawable(R.drawable.send_false_bg));
                    btSend.setTextColor(mContext.getResources().getColor(R.color.colorSilverGrey));
                }
                if (connectTextView != null && !isSetInputType) {
                    //关联显示内容
                    connectTextView.setText(charSequence);
                }
                //因为设置输入模式导致收到的回调,设置模式还原为false
                if (isSetInputType) isSetInputType = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        initAttribute();
    }

    private void initAttribute() {
        setSingleLine();
        connectView();
        setSubmitText();
        setMaxLength();
        setInputType();
        setInputValue();
        setTextHint();
        setDigits();
        setSubmitVisibility();
    }

    /**
     * 选择完成
     */
    private void selectDone() {
        String value = etInputValue.getText().toString();
        if (!value.isEmpty()) {
            submitValue(value);
            dismiss();
        }
    }

    private void setMaxLength() {
        if (mViewAttribute.maxLength() > 0)
            etInputValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mViewAttribute.maxLength())});
    }


    private void setInputValue() {
        if (!StringUtils.isEmpty(mViewAttribute.inputValue())) {
            etInputValue.setText(mViewAttribute.inputValue());
            setTextPosition(mViewAttribute.inputValue().length());
        }
    }


    /**
     * 设置光标位置
     *
     * @param index 位置
     */
    private void setTextPosition(int index) {
        etInputValue.setSelection(index);
    }

    public InputDialogTest setOptions(int options) {
        //键盘右下角设置为完成
        etInputValue.setImeOptions(options);
        return this;
    }

    private void setTextHint() {
        if (!StringUtils.isEmpty(mViewAttribute.textHint())) {
            etInputValue.setHint(mViewAttribute.textHint());
        }
    }

    private void setInputType() {
        if (mViewAttribute.inputType() > 0) {
            //设置输入模式
            etInputValue.setInputType(mViewAttribute.inputType());
            setTextPosition(etInputValue.length());
        }
    }

    /**
     * 设置允许输入哪些值
     * 如果设置了setInputType，该参数则会被替换掉，同时设置需要先设置setInputType再设置该属性
     */
    public void setDigits() {
        if (!StringUtils.isEmpty(mViewAttribute.digits()))
            etInputValue.setKeyListener(DigitsKeyListener.getInstance(mViewAttribute.digits()));
    }

    //是否正在设置输入类型
    private boolean isSetInputType;

    /**
     * 是否允许单行输入，true为单行(默认为单行) false为多行
     *
     * @return InputDialog
     */
    private void setSingleLine() {
        isSetInputType = true;
        etInputValue.setSingleLine(mViewAttribute.isSingleLine());
        //如果是多行，设置最大高度为120dp
        if (!mViewAttribute.isSingleLine())
            etInputValue.setMaxHeight(WindowUtils.dip2px(mContext, 120));
        setTextPosition(etInputValue.length());
    }


    private void setSubmitText() {
        if (!StringUtils.isEmpty(mViewAttribute.submitText())) {
            btSend.setText(mViewAttribute.submitText());
        }
    }

    /**
     * 设置提交按钮是否显示，不设置默认为显示
     */
    private void setSubmitVisibility() {
        btSend.setVisibility(mViewAttribute.submitVisibility() ? View.VISIBLE : View.GONE);
    }


    public InputDialogTest show() {
        mPopUp.hasShadowBg(mViewAttribute.isWindowShadow)
                .dismissOnTouchOutside(mViewAttribute.isCancel)
                .moveUpToKeyboard(false)
                .asCustom(this)
                .showWindow();
        return this;
    }

    public InputDialogTest setViewAttribute(BuildInputAttribute viewAttribute) {
        mViewAttribute = viewAttribute;
        return this;
    }

    protected void submitValue(String value) {

    }
}
