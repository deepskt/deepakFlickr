package com.deepak.androidflickr.net;

import android.util.SparseArray;

import com.deepak.androidflickr.Config;
import com.deepak.androidflickr.util.Tracer;


public class UrlResolver {

    private final static String TAG = Config.logger + UrlResolver.class.getSimpleName();
    public static SparseArray<String> endPointMapper = null;

    // STG LMADe
    public static final String BASE_URL = Config.BASE_URL;


    public static final String withPath(byte endPoint) {
        if (endPointMapper == null||endPointMapper.get(endPoint)==null) {
            init();
            populateMapper();
        }
        return BASE_URL + endPointMapper.get(endPoint);
    }

    public static final String withPath(byte endPoint, String path) {
        if (endPointMapper == null) {
            init();
            populateMapper();
        }
        return BASE_URL + endPointMapper.get(endPoint) + path + "/";
    }

    public static String getPath(byte endPoint) {
        if (endPointMapper == null) {
            init();
            populateMapper();
        }
        return endPointMapper.get(endPoint);
    }

    public static void init() {
        Tracer.debug(TAG, "[init] _ ");
        endPointMapper = new SparseArray<String>();
    }

    private static void populateMapper() {
        Tracer.debug(TAG, "[populateMapper] _ " + "");
        endPointMapper.put(EndPoints.flickrdata,"" );
        endPointMapper.put(EndPoints.flckrphoto, "events/");
    }

    public static class EndPoints {
        public static final byte flickrdata = 1;
        public static final byte flckrphoto = 2;
    }
}
