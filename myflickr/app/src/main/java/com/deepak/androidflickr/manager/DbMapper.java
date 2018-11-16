package com.deepak.androidflickr.manager;

import com.deepak.androidflickr.Config;
import com.deepak.androidflickr.database.PhotoFlickr;
import com.deepak.androidflickr.model.Photo;
import com.deepak.androidflickr.util.Tracer;

import java.util.ArrayList;
import java.util.List;

public class DbMapper {

    private static final String TAG = Config.logger + DbMapper.class.getSimpleName();

    public static List<PhotoFlickr> setPhotoListData(List<Photo> photolist, int page) {
        Tracer.debug(TAG," setPhotoListData "+" "+photolist.size());
        List<PhotoFlickr>photoFlickrs = new ArrayList<>();
        String url;
        for(Photo photo: photolist){
            PhotoFlickr photoFlickr = new PhotoFlickr();
            photoFlickr.setId(photo.getId());
            url = "http://farm"+photo.getFarm()+".static.flickr.com/"+photo.getServer()+"/"+photo.getId()+"_"+photo.getSecret()+".jpg";
            photoFlickr.setUrl(url);
            photoFlickr.setImageData(null);
            photoFlickr.setPage(page);
            photoFlickrs.add(photoFlickr);
        }
        return photoFlickrs;
    }
}
