package com.deepak.androidflickr.backgroundTask;

import android.os.AsyncTask;

import com.deepak.androidflickr.Config;
import com.deepak.androidflickr.database.PhotoFlickr;
import com.deepak.androidflickr.interfase.DataRefreshListener;
import com.deepak.androidflickr.interfase.FlickrImageDataListener;
import com.deepak.androidflickr.loader.FlickrDataLoader;
import com.deepak.androidflickr.manager.DBManager;
import com.deepak.androidflickr.manager.DbMapper;
import com.deepak.androidflickr.model.ErrorD;
import com.deepak.androidflickr.ui.main.dialog.ProgressDialog;
import com.deepak.androidflickr.util.Tracer;

import java.util.ArrayList;
import java.util.List;

public class ImageDataDBTask extends AsyncTask<List<PhotoFlickr>, Void, Boolean> {
    private final String TAG = Config.logger + ImageDataDBTask.class.getSimpleName();

    private PhotoFlickr mPhotoFlickr;
    private FlickrDataLoader mFlickrDataLoader;
    private DataRefreshListener mDataRefreshListener;
    private int count=0;
    private List<PhotoFlickr> photoFlickrList;


    public ImageDataDBTask( DataRefreshListener dataRefreshListener) {
        Tracer.debug(TAG," ImageDataDBTask "+" ");
        mFlickrDataLoader  = new FlickrDataLoader();
        mDataRefreshListener = dataRefreshListener;
        photoFlickrList = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(List<PhotoFlickr>... params) {
        Tracer.debug(TAG," doInBackground "+" "+params[0].size());
        count = 0;
        if(params[0]!= null) {
            photoFlickrList = params[0];
            return getData(count);
        }else{
            return false;
        }
    }

    private Boolean getData(int index) {
        Tracer.debug(TAG," getData "+" "+index);
        if(index < photoFlickrList.size()){
            mPhotoFlickr = photoFlickrList.get(index);
            if(mPhotoFlickr.getImageData() == null) {
                Tracer.debug(TAG," getData "+" "+mPhotoFlickr.getUrl());
                mFlickrDataLoader.getImageData(flickrImageDataListener, mPhotoFlickr.getUrl());
            }else{
                count++;
                getData(count);
            }
        }else{
            return false;
        }
        return true;
    }


    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Tracer.debug(TAG," onPostExecute "+" ");
        mDataRefreshListener.onRefresh();
    }

    FlickrImageDataListener flickrImageDataListener = new FlickrImageDataListener() {
        @Override
        public void imageData(byte[] data) {
            mPhotoFlickr.setImageData(data);
            DBManager.updatePhoto(mPhotoFlickr);
            count++;
            getData(count);
        }

        @Override
        public void onFailure(ErrorD errorResponse) {
            count++;
            getData(count);
        }
    };
}
