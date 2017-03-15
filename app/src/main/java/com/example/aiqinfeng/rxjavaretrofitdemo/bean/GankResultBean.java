package com.example.aiqinfeng.rxjavaretrofitdemo.bean;

import java.util.List;

/**
 * Created by AIqinfeng on 3/15/2017.
 */

public class GankResultBean {
    private boolean error;

    private List<ResultBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultBean> getResults() {
        return results;
    }

    public void setResults(List<ResultBean> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "GankResultBean{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
