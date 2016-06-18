package com.free.rxjoker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.facebook.drawee.view.SimpleDraweeView;
import com.free.rxjoker.been.MemberInfoBean;

import butterknife.Bind;

/**
 * Created by tanzhenxing
 * Date: 2016/6/18.
 * Description:参与成员详情
 */
public class MemberDetailActivity extends RxActivity {
    @Nullable
    @Bind(R.id.tool_bar)
    Toolbar toolbar;

    @Nullable
    @Bind(R.id.fab)
    FloatingActionButton actionButton;

    @Nullable
    @Bind(R.id.back_drop)
    SimpleDraweeView userIcon;

    public static final String MEMBER_INFO = "MEMBER_INFO";

    private MemberInfoBean currentInfo;
    @Override
    protected int getLayoutId() {
        return R.layout.i_member_detail;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initValue();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_input_delete);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initValue() {
        Intent intent = getIntent();
        if (intent != null) {
            currentInfo = (MemberInfoBean) intent.getSerializableExtra(MEMBER_INFO);
        }
        if (currentInfo == null) {
            finish();
        } else {
            setTitle(currentInfo.getName());
            userIcon.setImageURI(Uri.parse(currentInfo.getIcon()));
        }
    }

    public static void goMemberDetailActivity(Context context, MemberInfoBean vo) {
        Intent intent = new Intent(context, MemberDetailActivity.class);
        intent.putExtra(MEMBER_INFO, vo);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
