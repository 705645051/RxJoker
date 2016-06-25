package com.free.rxjoker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by liyaxing on 2016/6/4.
 */
public abstract class RxActivity extends AppCompatActivity {
    public Context mContext;

    private CompositeSubscription mCompositeSubscription;

    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        return this.mCompositeSubscription;
    }

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mContext = this;
    }

    protected abstract int getLayoutId() ;

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);//解绑
        getCompositeSubscription().unsubscribe();
    }

    protected void showMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show(); ;
    }
}
