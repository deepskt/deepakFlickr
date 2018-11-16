package com.deepak.androidflickr.loader;

import android.widget.Toast;

import com.deepak.androidflickr.Config;
import com.deepak.androidflickr.R;
import com.deepak.androidflickr.interfase.BaseListener;
import com.deepak.androidflickr.model.ErrorD;
import com.deepak.androidflickr.model.FlickrResponseData;
import com.deepak.androidflickr.net.DeepakClient;
import com.deepak.androidflickr.net.ResponseHandler;
import com.deepak.androidflickr.util.JsonUtil;
import com.deepak.androidflickr.util.Tracer;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import java.io.IOException;

public abstract class BaseLoader {
    private final String TAG = Config.logger + BaseLoader.class.getSimpleName();
    public DeepakClient mClient;

    protected abstract void onSuccess(String json, BaseListener baseListner);

    protected abstract void onFailure(ErrorD error, BaseListener baseListner, String json);

    public BaseLoader() {
        Tracer.debug(TAG, "[BaseLoaderUMS] _ " + "");
        try {
            mClient = DeepakClient.getInstance();
        } catch (Exception e) {
            Tracer.debug(TAG, " BaseLoaderUMS " + " " + e);
            e.printStackTrace();
        }
    }

    /*
    * Get Request
    * @params url, url params , token, Base listner for callback
    * */
    protected void requestGet(String url, RequestParams reqParams,
                              final BaseListener mListner) {

        final String loggerUrl = url;
        final String requestData = "URL:" + url + " " + "ResquestParam:" + JsonUtil.jsonify(reqParams);
            try {

                mClient.get(url, reqParams, new ResponseHandler() {

                    @Override
                    public void onSuccess(String str, int statuscode) {
                        Tracer.debug(TAG, "[requestGet] _ onSuccess" + "  url  " + loggerUrl + " Json " + str);
                        if (str != null) {
                                BaseLoader.this.onSuccess(str, mListner);
                        } else {
                            BaseLoader.this.onFailure(getErrorD(str,statuscode), mListner, str);
                        }
                    }

                    @Override
                    public void onFailure(String json, int statuscode) {
                        Tracer.debug(TAG, " onFailure " + json + " " + statuscode);
                        BaseLoader.this.onFailure(getErrorD(json, statuscode), mListner,
                                json);
                    }
                });
            } catch (Exception e) {
                Tracer.debug(TAG, " requestGet Exceprtion " + e);
                BaseLoader.this.onFailure(new ErrorD("Json Error", 111),
                        mListner, null);
            }
    }

    private ErrorD getErrorD(String jsonMessage, int statuscode) {
        Tracer.debug(TAG, " getErrorD " + " ");
         return new ErrorD(jsonMessage,statuscode);
    }

}
