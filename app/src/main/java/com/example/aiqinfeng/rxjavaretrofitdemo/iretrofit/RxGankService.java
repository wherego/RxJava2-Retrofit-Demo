package com.example.aiqinfeng.rxjavaretrofitdemo.iretrofit;

import com.example.aiqinfeng.rxjavaretrofitdemo.bean.GankResultBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by AIqinfeng on 3/15/2017.
 */

public interface RxGankService {

    @GET("all/20/{page}")
    Observable<GankResultBean> getAndroidData(@Path("page") int page);
}
