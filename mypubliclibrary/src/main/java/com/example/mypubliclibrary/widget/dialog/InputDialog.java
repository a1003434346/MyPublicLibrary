package com.example.mypubliclibrary.widget.dialog;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.util.WindowUtils;

/**
 * function:
 * describe: 评论Dialog
 * Created By LiQiang on 2019/7/29.
 */
public abstract class InputDialog {
//    final Context context;


    private View popView;

    private PopupWindow popWindow;

    private Context context;
    //输入内容
    private EditText etInputValue;
    //提交按钮,默认显示
    private Button btSend;
    //连接同步输入的view
    private TextView connectTextView;
//    private EditText connectEditText;

    protected InputDialog(Context context) {
        this.context = context;
        initView();
    }

    //设置连接同步输入的view
    public InputDialog connectView(View view) {
        //设置关联的TextView
        connectTextView = (TextView) view;
        return this;
    }

    private void initView() {
        if (popView == null) {
            popView = LayoutInflater.from(context).inflate(R.layout.dialog_comment_input, null);
            popWindow = WindowUtils.getPopupWindow(context, popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//            popWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            popWindow.setAnimationStyle(R.style.popWindowAnimation);
//            popWindow.setBackgroundDrawable(new ColorDrawable());
//            popWindow.setTouchable(true);
//            popWindow.setOutsideTouchable(false);
//            popWindow.setFocusable(true);
            //SOFT_INPUT_ADJUST_RESIZE防止被键盘挡住,SOFT_INPUT_STATE_ALWAYS_VISIBLE显示键盘
//            popWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

//            popWindow.setClippingEnabled(true);
            btSend = popView.findViewById(R.id.bt_send);
            etInputValue = popView.findViewById(R.id.et_input_value);
            popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            popView.findViewById(R.id.ctl_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popWindow.dismiss();
                }
            });
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
                        btSend.setBackground(context.getResources().getDrawable(R.drawable.send_true_bg));
                        btSend.setTextColor(context.getResources().getColor(R.color.colorWhite));
                    } else {
                        btSend.setBackground(context.getResources().getDrawable(R.drawable.send_false_bg));
                        btSend.setTextColor(context.getResources().getColor(R.color.colorSilverGrey));
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
        }
    }

    /**
     * 选择完成
     */
    private void selectDone() {
        String value = etInputValue.getText().toString();
        if (!value.isEmpty()) {
            submitValue(value);
            popWindow.dismiss();
        }
    }

    public InputDialog setMaxLength(int length) {
        etInputValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
        return this;
    }


    public InputDialog setInputValue(String inputValue) {
        if (!inputValue.trim().isEmpty()) {
            etInputValue.setText(inputValue);
            setTextPosition(inputValue.length());
        }
        return this;
    }


    /**
     * 设置光标位置
     *
     * @param index 位置
     */
    private void setTextPosition(int index) {
        etInputValue.setSelection(index);
    }

    public InputDialog setOptions(int options) {
        //键盘右下角设置为完成
        etInputValue.setImeOptions(options);
        return this;
    }

    public InputDialog setTextHint(String textHint) {
        if (!textHint.trim().isEmpty()) {
            etInputValue.setHint(textHint);
        }
        return this;
    }

    public InputDialog setInputType(int inputType) {
        //设置输入模式
        etInputValue.setInputType(inputType);
        setTextPosition(etInputValue.length());

        return this;
    }

    /**
     * 设置允许输入哪些值
     * 如果设置了setInputType，该参数则会被替换掉，同时设置需要先设置setInputType再设置该属性
     *
     * @param value 允许输入的内容
     * @return InputDialog
     */
    public InputDialog setDigits(String value) {
        etInputValue.setKeyListener(DigitsKeyListener.getInstance(value));
        return this;
    }

    //是否正在设置输入类型
    private boolean isSetInputType;

    /**
     * 是否允许单行输入，true为单行(默认为单行) false为多行
     *
     * @param singleLine true/false
     * @return InputDialog
     */
    public InputDialog setSingleLine(boolean singleLine) {
        isSetInputType = true;
        etInputValue.setSingleLine(singleLine);
        //如果是多行，设置最大高度为120dp
        if (!singleLine) etInputValue.setMaxHeight(WindowUtils.dip2px(context, 120));
        setTextPosition(etInputValue.length());
        return this;
    }


    public InputDialog setSubmitText(String submitText) {
        if (!submitText.trim().isEmpty()) {
            btSend.setText(submitText);
        }
        return this;
    }

    /**
     * 设置提交按钮的显示状态，不设置默认为显示
     *
     * @param visibility true/false
     * @return InputDialog
     */
    public InputDialog setSubmitVisibility(boolean visibility) {
        btSend.setVisibility(visibility ? View.VISIBLE : View.GONE);
        return this;
    }


    public InputDialog show() {
        popWindow.showAtLocation(popView, Gravity.BOTTOM, 0, 0);
        return this;
    }


    protected abstract void submitValue(String value);
}
