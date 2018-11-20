package com.deepak.androidflickr.ui.main.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.deepak.androidflickr.Config;
import com.deepak.androidflickr.R;
import com.deepak.androidflickr.database.PhotoFlickr;
import com.deepak.androidflickr.interfase.DataRefreshListener;
import com.deepak.androidflickr.loader.FlickrDataLoader;
import com.deepak.androidflickr.util.Tracer;

import java.util.ArrayList;
import java.util.List;

public class FlickrDataAdapter extends RecyclerView.Adapter<FlickrDataAdapter.FlickrDataAdapterVH> {
    private final String TAG = Config.logger + FlickrDataAdapter.class.getSimpleName();

    private List<PhotoFlickr> mlist;
    private FlickrDataLoader flickrDataLoader;
    private DataRefreshListener mListener;
    private int currentPosition;

    public FlickrDataAdapter(DataRefreshListener dataRefreshListener) {
        mListener = dataRefreshListener;
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
        if(getBitmap(photoFlickr.getImageData()) != null){
                holder.avPhoto.setImageBitmap(getBitmap(photoFlickr.getImageData()));
        }else{
            holder.avPhoto.setImageResource(R.mipmap.image_not_found);
        }
        if(position == mlist.size()-1){
            mListener.onDataFetch();
        }
    }

    private Bitmap getBitmap(byte[] data){
        Tracer.debug(TAG," getBitmap "+" "+data);
        if(data != null) {
            Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);
            return image;
        }else{
            return null;
        }

    }

    @Override
    public int getItemCount() {
        return mlist == null ? 0 : mlist.size();
    }

    public class FlickrDataAdapterVH extends RecyclerView.ViewHolder {

        private ImageView avPhoto;

        public FlickrDataAdapterVH(View view) {
            super(view);
            avPhoto = (ImageView) view.findViewById(R.id.avFlickrphoto);
        }
    }
}



