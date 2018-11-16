package com.deepak.androidflickr.interfase;

import com.deepak.androidflickr.model.FlickrResponseData;

public interface FlickrDataListner extends BaseListener {
    void onData(FlickrResponseData data);
}
