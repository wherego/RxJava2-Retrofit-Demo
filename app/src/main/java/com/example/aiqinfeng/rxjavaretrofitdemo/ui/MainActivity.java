package com.example.aiqinfeng.rxjavaretrofitdemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.aiqinfeng.rxjavaretrofitdemo.R;
import com.example.aiqinfeng.rxjavaretrofitdemo.bean.GankResultBean;
import com.example.aiqinfeng.rxjavaretrofitdemo.bean.ResultBean;
import com.example.aiqinfeng.rxjavaretrofitdemo.iretrofit.RxGankService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    TextView mtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mtv = (TextView) findViewById(R.id.textview);
    }

    private void initData() {
        final StringBuilder sb = new StringBuilder();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/data/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RxGankService rxGankService = retrofit.create(RxGankService.class);
        final Observable<GankResultBean> observable = rxGankService.getAndroidData(1);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<GankResultBean, List<ResultBean>>() {
                    @Override
                    public List<ResultBean> apply(GankResultBean gankResultBean) throws Exception {
                        return gankResultBean.getResults();
                    }
                })
                .flatMap(new Function<List<ResultBean>, ObservableSource<ResultBean>>() {
                    @Override
                    public Observable<ResultBean> apply(List<ResultBean> resultBeanList) {
                        return Observable.fromIterable(resultBeanList);
                    }
                })
                .filter(new Predicate<ResultBean>() {
                    @Override
                    public boolean test(ResultBean resultBean) throws Exception {
                        return "Android".equals(resultBean.getType());
                    }
                })
                .subscribe(new Observer<ResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean resultBean) {
                        Log.i(TAG, "onNext: " + resultBean.toString());
                        sb.append(resultBean + "\n");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onCompleted: " + sb.toString());
//                        mtv.setText(sb.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mtv.setText(sb.toString());
                            }
                        });
                    }
                });
    }
}
