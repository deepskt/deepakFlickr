package com.deepak.androidflickr.interfase;

import com.deepak.androidflickr.model.FlickrPageData;

public interface FlickrImageDataListener extends BaseListener {
    void imageData(byte[] data);
}
