package com.free.rxjoker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.free.rxjoker.been.TextBeen;
import com.free.rxjoker.model.Config;
import com.free.rxjoker.model.JokerApi;
import com.free.rxjoker.model.RetrofitApiFactory;
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
public class TextFragment extends RxFragment {

    @Nullable @Bind(R.id.recycler_view) RecyclerView recyclerView;
    @Nullable @Bind(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;

    private final List<TextBeen> list = new ArrayList<>() ;
    private MyAdapter adapter = null ;

    @Override
    protected int getContentViewId() {
        return R.layout.c_recycler_view;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        Log.d("TextFragment","[initAllMembersView]") ;
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(
                new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL,10, Color.TRANSPARENT));
        recyclerView.setAdapter(adapter = new MyAdapter());
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


    private void updateListView(List<TextBeen> list){
        this.list.clear();
        this.list.addAll(list) ;
        this.adapter.notifyDataSetChanged();
    }

    private void closeRefreshing(Object obj){
        Log.d("TextFragment","[closeRefreshing]") ;
        swipeRefreshLayout.setRefreshing(false);
    }

    private Observable<List<TextBeen>> getListForObservable(Void v){
        return RetrofitApiFactory
                .createApi(JokerApi.class)
                .getTextList(Config.JOKER_APP_KEY,20)
                .map(textBeenJokerWrapper -> textBeenJokerWrapper.result.data);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(
                    LayoutInflater.from(getActivity()).inflate(R.layout.i_text, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TextBeen model = list.get(position) ;
            holder.content.setText(model.content);
            holder.updateTime.setText(model.updatetime);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends BaseViewHolder {

            @Nullable @Bind(R.id.content) TextView content;
            @Nullable @Bind(R.id.update_time) TextView updateTime;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
