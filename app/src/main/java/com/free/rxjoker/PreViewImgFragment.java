package com.free.rxjoker;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.free.rxjoker.been.GifBeen;

import butterknife.Bind;

/**
 * Created by tanzhenxing
 * Date: 2016/6/30.
 * Description:
 */
public class PreViewImgFragment extends RxFragment{
    @Nullable @Bind(R.id.image)
    SimpleDraweeView simpleDraweeView;
    @Nullable @Bind(R.id.imag_content)
    ViewGroup content;
    private GifBeen been;
    private FragmentManager manager;
    private int screenWidth = 0;
    private boolean isPop;
    private View thisView;
    private ViewGroup rootView;
    public PreViewImgFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thisView = super.onCreateView(inflater, container, savedInstanceState);
        rootView = (ViewGroup) getActivity().getWindow().getDecorView();
        rootView.addView(thisView);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @SuppressLint("ValidFragment")
    public PreViewImgFragment(GifBeen been, FragmentManager manager) {
        TAG = this.getClass().getSimpleName();
        this.been = been;
        this.manager = manager;
        isPop = false;
        popView();
    }

    public void popView() {
        if (!isPop) {
            isPop = true;
            FragmentTransaction transaction = this.manager.beginTransaction();
            transaction.add(this,TAG);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.a_preview_img_fragment;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        if (been != null) {
            DraweeController draweeController =
                    Fresco.newDraweeControllerBuilder()
                            .setUri(Uri.parse(been.url))
                            .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                            .build();
            simpleDraweeView.setController(draweeController);
        }
        initListener();
    }

    private void initListener() {
        content.setOnClickListener(v -> hideView());
    }

    public void hideView() {
        if (isPop) {
            manager.popBackStack();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(this);
            transaction.commit();
            isPop = false;
        }
    }

    @Override
    public void onDestroyView() {
        rootView.removeView(thisView);
        super.onDestroyView();
    }
}
