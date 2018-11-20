package com.deepak.androidflickr.manager;

import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import com.deepak.androidflickr.Config;
import com.deepak.androidflickr.FlickrApplication;
import com.deepak.androidflickr.database.DaoMaster;
import com.deepak.androidflickr.database.DaoSession;
import com.deepak.androidflickr.database.PhotoFlickr;
import com.deepak.androidflickr.database.PhotoFlickrDao;
import com.deepak.androidflickr.util.Tracer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

import static java.lang.Math.min;

/*
 *
 * @author deepsindeep
 * */

public class DBManager {
    private static final String TAG = Config.logger + DBManager.class.getSimpleName();

    /**
     * DB ReArch
     */
     private static PhotoFlickrDao mPhotoFlickrDao;


    public synchronized static void init() {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(FlickrApplication.getsAppContext(), "FlickrDB", null);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Tracer.debug(TAG, " init " + " DB version" + db.getVersion());
        DaoSession daoSession = new DaoMaster(db).newSession(IdentityScopeType.None);
        mPhotoFlickrDao = daoSession.getPhotoFlickrDao();
    }

    public synchronized static void clearDB(boolean clearNSL) {
        Tracer.debug(TAG, " clearDB " + " ");
    }

    public synchronized static  void insertIntoDb(List<PhotoFlickr>list){
        mPhotoFlickrDao.insertOrReplaceInTx(list);
    }

    public synchronized  static List<PhotoFlickr>getPhotos(int page){
        Tracer.debug(TAG," getPhotos "+" ");
        return mPhotoFlickrDao.queryBuilder().where(PhotoFlickrDao.Properties.Page.eq(page),PhotoFlickrDao.Properties.ImageData.isNotNull()).list();
    }

    public static void updatePhoto(PhotoFlickr photoFlickr) {
        mPhotoFlickrDao.updateInTx(photoFlickr);
    }

    public static List<PhotoFlickr> getPhotoWithoutImageData(int page) {
        return mPhotoFlickrDao.queryBuilder().where(PhotoFlickrDao.Properties.Page.eq(page),PhotoFlickrDao.Properties.ImageData.isNull()).limit(5).list();

    }
}