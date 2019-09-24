package com.example.mypubliclibrary.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.LinearLayout;

import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.base.BasesActivity;
import com.example.mypubliclibrary.base.bean.EventMsg;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class WebViewActivity extends BasesActivity {

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMsg message) {

    }

    @Override
    protected int onRegistered() {
        return R.layout.activity_web_view;
    }

    private ConstraintLayout ctlWeb;

    @Override
    protected void initView() {
        ctlWeb = (ConstraintLayout) findViewById(R.id.ctl_web);

    }

    @Override
    protected void initData() {
        String title = getIntent().getStringExtra("title");
        if (title == null || title.isEmpty()) {
            hideView(R.id.ctl_title);
        } else {
            setTextValue(R.id.tv_title, title);
        }
        ctlWeb = (ConstraintLayout) findViewById(R.id.ctl_web);
        AgentWeb agentWeb = AgentWeb.with(this)
                .setAgentWebParent(ctlWeb, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(getIntent().getStringExtra("url"));
        agentWeb.getAgentWebSettings().getWebSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    }



    @Override
    public void onClick(View view) {

    }
}
