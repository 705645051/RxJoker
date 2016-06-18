package com.free.rxjoker;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.free.rxjoker.been.MemberInfoBean;
import com.free.rxjoker.been.TextBeen;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by tanzhenxing
 * Date: 2016/6/6.
 * Description:参与成员
 */
public class AboutMemberActivity extends RxActivity {
    @Nullable @Bind(R.id.tool_bar) Toolbar toolbar;
    @Nullable @Bind(R.id.recycler) RecyclerView recyclerView;
    private MyAdapter adapter = null;
    private List<MemberInfoBean> list = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initVaule();
    }

    private void initVaule() {
        MemberInfoBean memberInfoBean = new MemberInfoBean();
        memberInfoBean.setAge(24);
        memberInfoBean.setJobName("Android开发工程师");
        memberInfoBean.setName("谭振兴");
        memberInfoBean.setSex(true);
        memberInfoBean.setIcon("http://i4.buimg.com/a0f481fdaf692028t.jpg");
        MemberInfoBean memberInfoBean1 = new MemberInfoBean();
        memberInfoBean1.setAge(25);
        memberInfoBean1.setJobName("Android开发工程师");
        memberInfoBean1.setName("李亚星");
        memberInfoBean1.setSex(true);
        memberInfoBean1.setIcon("http://i1.buimg.com/833bf5d29e809e7ft.jpg");
        list.add(memberInfoBean1);
        list.add(memberInfoBean);
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_input_delete);
        actionBar.setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(
                new RecycleViewDivider(this, LinearLayoutManager.VERTICAL,10, Color.TRANSPARENT));
        recyclerView.setAdapter(adapter = new MyAdapter());
        adapter.setOnClickItemListener(new OnItemClickListener());
    }

    class OnItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int postion = (int) view.getTag();
            MemberDetailActivity.goMemberDetailActivity(mContext, list.get(postion));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.a_about_member;
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private View.OnClickListener onClickItemListener;
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(
                    LayoutInflater.from(AboutMemberActivity.this).inflate(R.layout.i_member, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            MemberInfoBean model = list.get(position);
            holder.age.setText(model.getAge() + "岁");
            if (!TextUtils.isEmpty(model.getName())) {
                holder.name.setText(model.getName() + ",");
            }
            holder.icon.setImageURI(Uri.parse(model.getIcon()));
            if (model.isSex()) {
                holder.sex.setText("男");
                holder.sex.setBackgroundResource(R.drawable.i_member_list_item_sex_man_bg);
            } else {
                holder.sex.setBackgroundResource(R.drawable.i_member_list_item_sex_woman_bg);
                holder.sex.setText("女");
            }
            holder.jobName.setText("工作岗位：" + model.getJobName());
            holder.rootView.setTag(position);
            if (onClickItemListener != null) {
                holder.rootView.setOnClickListener(onClickItemListener);
            }
        }

        public void setOnClickItemListener(View.OnClickListener onClickItemListener) {
            this.onClickItemListener = onClickItemListener;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends BaseViewHolder {

            @Nullable @Bind(R.id.user_icon) SimpleDraweeView icon;
            @Nullable @Bind(R.id.user_name) TextView name;
            @Nullable @Bind(R.id.user_sex) TextView sex;
            @Nullable @Bind(R.id.user_age) TextView age;
            @Nullable @Bind(R.id.user_job) TextView jobName;
            @Nullable @Bind(R.id.list_item_header_root_view) ViewGroup rootView;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
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
