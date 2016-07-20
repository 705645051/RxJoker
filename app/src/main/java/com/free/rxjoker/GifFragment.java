package com.free.rxjoker;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.free.rxjoker.been.GifBeen;
import com.free.rxjoker.model.Config;
import com.free.rxjoker.model.Host;
import com.free.rxjoker.model.JokerApi;
import com.free.rxjoker.model.RetrofitApiFactory;
import com.free.rxjoker.utils.DensityUtil;
import com.jakewharton.rxbinding.support.v4.widget.RxSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liyaxing on 2016/6/4.
 */
public class GifFragment extends RxFragment {

    @Nullable @Bind(R.id.recycler_view) RecyclerView recyclerView;
    @Nullable @Bind(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;

    private final List<GifBeen> list = new ArrayList<>() ;
    private MyAdapter adapter = null ;

    @Override
    protected int getContentViewId() {
        return R.layout.c_recycler_view;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        Log.d("GifFragment","[initAllMembersView]") ;
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(
                new RecycleViewDivider(getActivity(),
                        LinearLayoutManager.VERTICAL,
                        DensityUtil.dip2px(getActivity(), 10),
                        getResources().getColor(R.color.whitesmoke)));
        recyclerView.setAdapter(adapter = new MyAdapter(getActivity()));
        RxSwipeRefreshLayout.refreshes(swipeRefreshLayout)
                .observeOn(Schedulers.io())
                .flatMap(this :: getListForObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(this :: closeRefreshing)
                .doOnNext(this :: closeRefreshing)
                .subscribe(this :: updateListView,this :: showError) ;
    }

    private void showError(Throwable throwable){
        Toast.makeText(getActivity(),throwable.toString(),Toast.LENGTH_LONG).show();
    }


    private void updateListView(List<GifBeen> list){
        this.list.clear();
        this.list.addAll(list) ;
        this.adapter.notifyDataSetChanged();
    }

    private void closeRefreshing(Object obj){
        Log.d("TextFragment","[closeRefreshing]") ;
        swipeRefreshLayout.setRefreshing(false);
    }

    private Observable<List<GifBeen>> getListForObservable(Void v){
        return RetrofitApiFactory
                .createApi(JokerApi.class)
                .getGifList(Config.JOKER_APP_KEY,20)
                .map(textBeenJokerWrapper -> textBeenJokerWrapper.result.data);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private Context mContext;

        public MyAdapter(Context context) {
            mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(
                    LayoutInflater.from(getActivity()).inflate(R.layout.i_gif, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            GifBeen model = list.get(position) ;
            Uri uri = Uri.parse(model.url);
            DraweeController draweeController =
                    Fresco.newDraweeControllerBuilder()
                            .setUri(uri)
                            .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                            .build();
            holder.simpleDraweeView.setController(draweeController);
            holder.simpleDraweeView.setOnClickListener(v -> new PreViewImgFragment(model, ((RxActivity)mContext).getSupportFragmentManager()));
            if (!TextUtils.isEmpty(model.content)) {
                holder.gitContextTV.setVisibility(View.VISIBLE);
                holder.gitContextTV.setText(model.content);
            } else {
                holder.gitContextTV.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends BaseViewHolder {

            @Nullable @Bind(R.id.simple_drawee_view) SimpleDraweeView simpleDraweeView;
            @Nullable @Bind(R.id.gif_content) TextView gitContextTV;
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
