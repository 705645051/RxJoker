package com.free.rxjoker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.free.rxjoker.been.GifBeen;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liyaxing on 2016/6/4.
 */
public class MainActivity extends RxActivity {

    @Nullable @Bind(R.id.app_bar_layout) AppBarLayout  appBarLayout ;
    @Nullable @Bind(R.id.view_pager) ViewPager viewPager ;
    @Nullable @Bind(R.id.tool_bar) Toolbar toolBar ;
    @Nullable @Bind(R.id.tab_layout) TabLayout tabLayout ;

    private final String[] TAB_ITEMS = {"TEXT","GIF"} ;

    private List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView() ;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.a_main;
    }

    private void initView() {
        //初始化ToolBar
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //初始化TabLayout的title
        tabLayout.addTab(tabLayout.newTab().setText(TAB_ITEMS[0]));
        tabLayout.addTab(tabLayout.newTab().setText(TAB_ITEMS[1]));

        //初始化ViewPager的数据集
        fragments = new ArrayList<>();
        fragments.add(new TextFragment());
        fragments.add(new GifFragment());
        //创建ViewPager的adapter
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        //千万别忘了，关联TabLayout与ViewPager
        //同时也要覆写PagerAdapter的getPageTitle方法，否则Tab没有title
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //主界面右上角的menu菜单
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.menu_about_app:
                //intent = new Intent(this,AboutMeActivity.class) ;
                //startActivity(intent);
                GifBeen gifBeen = new GifBeen();
                gifBeen.content = "知乎上的神回复，教你如何正确地吐槽！";
                gifBeen.hashId = "hashId=B62284FB59CAA4F9E023BAAD6CCB258E";
                gifBeen.url = "http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201606/30/B62284FB59CAA4F9E023BAAD6CCB258E.png";
                new PreViewImgFragment(gifBeen, MainActivity.this.getSupportFragmentManager());
                //new BottomPopView(MainActivity.this.getSupportFragmentManager());
                break;
            case R.id.menu_about_members:
                intent = new Intent(this, AboutMemberActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private class FragmentAdapter extends FragmentStatePagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TAB_ITEMS[position];
        }
    }

    //@Override
    //public boolean onKeyDown(int keyCode, KeyEvent event) {
    //    Log.d("onKeyDown", "onKeyDown: ");
    //    if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
    //        return false;
    //    }
    //    return super.onKeyDown(keyCode, event);
    //}

}
