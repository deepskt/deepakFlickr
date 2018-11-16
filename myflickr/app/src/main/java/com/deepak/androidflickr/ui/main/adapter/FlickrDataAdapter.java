package com.deepak.androidflickr.ui.main.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.deepak.androidflickr.Config;
import com.deepak.androidflickr.R;
import com.deepak.androidflickr.database.PhotoFlickr;
import com.deepak.androidflickr.interfase.DataRefreshListener;
import com.deepak.androidflickr.interfase.FlickrImageDataListener;
import com.deepak.androidflickr.loader.FlickrDataLoader;
import com.deepak.androidflickr.manager.DBManager;
import com.deepak.androidflickr.model.ErrorD;
import com.deepak.androidflickr.model.FlickrPageData;
import com.deepak.androidflickr.util.Tracer;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FlickrDataAdapter extends RecyclerView.Adapter<FlickrDataAdapter.FlickrDataAdapterVH> {
    private final String TAG = Config.logger + FlickrDataAdapter.class.getSimpleName();

    private List<PhotoFlickr> mlist;
    private FlickrDataLoader flickrDataLoader;
    private DataRefreshListener mListener;
    private int currentPosition;

    public FlickrDataAdapter(DataRefreshListener listener) {
        mListener = listener;
        flickrDataLoader = new FlickrDataLoader();
    }

    public void setDataMap(List<PhotoFlickr> photoFlickrList) {
        Tracer.debug(TAG," setDataMap "+" "+photoFlickrList.size());
        mlist = new ArrayList<>();
        mlist = photoFlickrList;
        notifyItemChanged(0, this.mlist.size());
        notifyDataSetChanged();
    }

    @Override
    public FlickrDataAdapterVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new FlickrDataAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(FlickrDataAdapterVH holder, int position) {
        Tracer.debug(TAG, " onBindViewHolder " + " ");
        currentPosition = position;
        PhotoFlickr photoFlickr = mlist.get(position);
        Tracer.debug(TAG," onBindViewHolder "+" "+photoFlickr.getId()+" "+photoFlickr.getImageData());
        Picasso.get().load(photoFlickr.getUrl()).error(R.mipmap.image_not_found).fit().centerCrop().into(holder.avPhoto);
    }

    private Bitmap getBitmap(byte[] data){
        Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);
        return image;

    }

    @Override
    public int getItemCount() {
        return mlist == null ? 0 : mlist.size();
    }

    public class FlickrDataAdapterVH extends RecyclerView.ViewHolder {

        private AppCompatImageView avPhoto;

        public FlickrDataAdapterVH(View view) {
            super(view);
            avPhoto = (AppCompatImageView) view.findViewById(R.id.avFlickrphoto);
        }
    }

    FlickrImageDataListener flickrImageDataListener = new FlickrImageDataListener() {
        @Override
        public void imageData(byte[] data) {
            PhotoFlickr photoFlickr = mlist.get(currentPosition);
            photoFlickr.setImageData(data);
            DBManager.updatePhoto(photoFlickr);
          //  notifyDataSetChanged();
            }

        @Override
        public void onFailure(ErrorD errorResponse) {

        }
    };
}



