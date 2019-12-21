package com.example.mypubliclibrary.widget.dialog.build;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.util.ColorUtils;
import com.example.mypubliclibrary.widget.bean.ViewAttribute;
import com.example.mypubliclibrary.widget.dialog.basic.BottomIosDialog;
import com.example.mypubliclibrary.widget.dialog.basic.PasswordPayDialog;

import java.util.LinkedList;
import java.util.List;

/**
 * 功能:
 * Created By leeyushi on 2019/12/21.
 */
public abstract class BuildPasswordAttribute extends ViewAttribute {
    /**
     * 输入键盘文本
     */
    private String[] mKeyboardText = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", ""};
    private boolean mAutoDismiss;
    private LinkedList<String> mRecordList = new LinkedList<>();
    private String title;
    private String hintText;
    private String money;
    //输完后自动销毁
    private boolean autoDismiss;
    private Context mContext;
    private int hintColor;
    private int hintSize;

    @Override
    protected void initAttribute() {
        mAutoDismiss = true;
        autoDismiss = true;
        isWindowShadow = true;
        hintSize = 12;
        hintColor = Color.parseColor("#A6A6B2");

    }

    public BuildPasswordAttribute(Context context) {
        this.mContext = context;
    }

    public String[] getKeyboardText() {
        return mKeyboardText;
    }

    public String getTitle() {
        return title;
    }

    public BuildPasswordAttribute setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getHintText() {
        return hintText;
    }

    public BuildPasswordAttribute setHintText(String hintText) {
        this.hintText = hintText;
        return this;
    }

    public String getMoney() {
        return money;
    }

    public BuildPasswordAttribute setMoney(String money) {
        this.money = money;
        return this;
    }

    public boolean isAutoDismiss() {
        return autoDismiss;
    }

    public BuildPasswordAttribute setAutoDismiss(boolean autoDismiss) {
        this.autoDismiss = autoDismiss;
        return this;
    }

    public boolean getAutoDismiss() {
        return mAutoDismiss;
    }

    public LinkedList<String> getRecordList() {
        return mRecordList;
    }

    public int getHintColor() {
        return hintColor;
    }

    public BuildPasswordAttribute setHintColor(int hintColor) {
        this.hintColor = hintColor;
        return this;
    }

    public int getHintSize() {
        return hintSize;
    }

    public BuildPasswordAttribute setHintSize(int hintSize) {
        this.hintSize = hintSize;
        return this;
    }

    protected abstract void onDone(String password);

    public PasswordPayDialog createWindow() {
        return new PasswordPayDialog(mContext) {

            @Override
            protected void onCompleted(String password) {
                onDone(password);
            }
        }.setViewAttribute(this);
    }


}
