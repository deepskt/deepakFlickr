package com.deepak.androidflickr.ui.main;

import android.arch.lifecycle.ViewModel;

import com.deepak.androidflickr.Config;
import com.deepak.androidflickr.MainActivity;
import com.deepak.androidflickr.database.PhotoFlickr;
import com.deepak.androidflickr.manager.DBManager;
import com.deepak.androidflickr.util.Tracer;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class MainViewModel extends ViewModel {
    private static final String TAG = Config.logger + MainViewModel.class.getSimpleName();

    // TODO: Implement the ViewModel
    private List<PhotoFlickr> mPhotolist;

    public List<PhotoFlickr> getPhotolist(int page){
        Tracer.debug(TAG," getPhotolist "+" ");
        mPhotolist = DBManager.getPhotos(page);
        return mPhotolist;
    }
}
