package com.example.mypubliclibrary.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.base.BasesActivity;
import com.example.mypubliclibrary.base.bean.EventMsg;
import com.example.mypubliclibrary.base.interfaces.WebCall;
import com.example.mypubliclibrary.util.EventBusUtils;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class WebViewActivity extends BasesActivity {


    @Override
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(EventMsg message) {
        switch (message.getType()) {
            case "webCall":
                mWebCall = message.getData();
                AgentWeb.IndicatorBuilder setAgentWebParent = AgentWeb.with(this)
                        .setAgentWebParent(ctlWeb, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                AgentWeb.CommonBuilder commonBuilder;
                if (getIntent().getBooleanExtra("isShowLoadingBar", true)) {
                    commonBuilder = setAgentWebParent.useDefaultIndicator();
                } else {
                    commonBuilder = setAgentWebParent.closeIndicator();
                }
                WebChromeClient mWebChromeClient = new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                        //do you work
                        if (newProgress == 100 && !isLoadDone) {
                            isLoadDone = true;
                            mWebCall.onLoadDone();
                        }

                    }
                };
                if (mWebCall != null)
                    commonBuilder = commonBuilder.setWebChromeClient(mWebChromeClient);
                commonBuilder
                        .createAgentWeb()
                        .ready()
                        .go(getIntent().getStringExtra("url"))
                        .getAgentWebSettings()
                        .getWebSettings()
                        .setCacheMode(WebSettings.LOAD_NO_CACHE);
                break;
        }
    }

    @Override
    protected int onRegistered() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtils.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtils.unregister(this);
        EventBusUtils.removeAllStickyEvents();
    }

    private ConstraintLayout ctlWeb;

    //是否加载完成
    private boolean isLoadDone;

    private WebCall mWebCall;

    @Override
    protected void initView() {
        ctlWeb = (ConstraintLayout) bindId(R.id.ctl_web);
        String title = getIntent().getStringExtra("title");
        if (title == null || title.isEmpty()) {
            hideView(R.id.ctl_title);
        } else {
            setTextValue(R.id.tv_title, title);
        }
    }

    @Override
    protected void initStyle() {

    }

    @Override
    protected void getPageRequestData() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void mayRefreshData() {

    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onQuestError(EventMsg message) {

    }

    @Override
    public void onQuestSuccess(EventMsg message) {

    }
}
