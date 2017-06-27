package com.qhy.letter.net;

import com.qhy.letter.utils.Logs;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


public class HttpUtils {
    /**
     * get请求
     * @param url
     * @param json
     * @param mCallBack
     */
    private static void doGet(String url, JSONObject json, final OnHttpCallBack mCallBack){
        RequestParams params = new RequestParams(url);
        params.setAsJsonContent(true);
        params.setBodyContent(json.toString());
        Logs.i("request", "param: "+json.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(null != mCallBack){
                    mCallBack.onSuccess(result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Logs.d("onError", "onError: "+ex.getMessage());
                if(null != mCallBack){
                    mCallBack.onError(ex.getMessage());
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * post请求
     * @param url
     * @param json
     * @param mCallBack
     */
    public static void doPost(String url, JSONObject json,  final OnHttpCallBack mCallBack){
        RequestParams params = new RequestParams(url);
        params.setAsJsonContent(true);
        params.setBodyContent(json.toString());
        Logs.i("request", "param: "+json.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(null != mCallBack){
                    mCallBack.onSuccess(result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Logs.d("onError", "onError: "+ex.getMessage());
                if(null != mCallBack){
                    mCallBack.onError(ex.getMessage());
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }



    public interface OnHttpCallBack{
        void onSuccess(String result);
        void onError(String errorMessage);
    }

}
