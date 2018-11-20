package com.deepak.androidflickr.loader;

import com.deepak.androidflickr.Config;
import com.deepak.androidflickr.interfase.BaseListener;
import com.deepak.androidflickr.interfase.FlickrDataListner;
import com.deepak.androidflickr.interfase.FlickrImageDataListener;
import com.deepak.androidflickr.manager.DBManager;
import com.deepak.androidflickr.manager.DbMapper;
import com.deepak.androidflickr.model.ErrorD;
import com.deepak.androidflickr.model.FlickrResponseData;
import com.deepak.androidflickr.net.UrlResolver;
import com.deepak.androidflickr.util.JsonUtil;
import com.deepak.androidflickr.util.Tracer;

public class FlickrDataLoader extends BaseLoader {
    private static final String TAG = Config.logger + FlickrDataLoader.class.getSimpleName();

    public void getFlickerData(FlickrDataListner listener, int page){
        String url = UrlResolver.withPath(UrlResolver.EndPoints.flickrdata, String.valueOf(page));
        requestGet(url, null, listener);
    }

    public void getImageData(FlickrImageDataListener listener, String url){
        Tracer.debug(TAG," getImageData "+" "+url);
        requestSyncGet(url,null,listener);
    }

    @Override
    protected void onSuccess(byte[] data, BaseListener baseListener) {
        if(baseListener instanceof FlickrImageDataListener){
            ((FlickrImageDataListener) baseListener).imageData(data);
        }
    }

    @Override
    protected void onSuccess(String json, BaseListener baseListner) {
        if(baseListner instanceof FlickrDataListner){
            FlickrResponseData responseData = (FlickrResponseData)JsonUtil.objectify(json,FlickrResponseData.class);
            if(responseData!= null && responseData.getPhotos()!= null
                    && responseData.getPhotos().getPhoto()!= null && responseData.getPhotos().getPhoto().size()>0){
                DBManager.insertIntoDb(DbMapper.setPhotoListData(responseData.getPhotos().getPhoto(),responseData.getPhotos().getPage()));
                ((FlickrDataListner) baseListner).onData(responseData);
            }else {
                baseListner.onFailure(new ErrorD("No Data Found", 0));
            }
        }
    }

    @Override
    protected void onFailure(ErrorD error, BaseListener baseListner, String json) {
        baseListner.onFailure(error);
    }
}
