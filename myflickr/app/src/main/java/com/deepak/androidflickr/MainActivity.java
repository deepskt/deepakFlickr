package com.deepak.androidflickr;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.deepak.androidflickr.database.PhotoFlickr;
import com.deepak.androidflickr.interfase.DataRefreshListener;
import com.deepak.androidflickr.interfase.FlickrDataListner;
import com.deepak.androidflickr.loader.FlickrDataLoader;
import com.deepak.androidflickr.model.ErrorD;
import com.deepak.androidflickr.model.FlickrResponseData;
import com.deepak.androidflickr.ui.main.MainViewModel;
import com.deepak.androidflickr.ui.main.adapter.FlickrDataAdapter;
import com.deepak.androidflickr.ui.main.dialog.ProgressDialog;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = Config.logger + MainActivity.class.getSimpleName();

    private AppCompatImageView avPre;
    private AppCompatImageView avNext;
    private TextView tvPage;
    private RecyclerView recyclerView;
    private ProgressDialog mProgressDialog;
    private int currentPage = 1;
    private MainViewModel mViewModel;
    private List<PhotoFlickr> photoFlickrList;
    private FlickrDataLoader mFLoader;
    private FlickrDataAdapter mFlickrDataAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        avNext  = (AppCompatImageView) findViewById(R.id.avNext);
        avNext.setOnClickListener(this);
        avPre = (AppCompatImageView)findViewById(R.id.avPre);
        avPre.setOnClickListener(this);
        tvPage = (TextView)findViewById(R.id.tv_current_page);
        recyclerView = (RecyclerView)findViewById(R.id.rvPhoto);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvPage.setText(String.valueOf(currentPage));
        mProgressDialog = new ProgressDialog(this);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mFLoader = new FlickrDataLoader();
        mFlickrDataAdapter = new FlickrDataAdapter(dataRefreshListener);
        recyclerView.setAdapter(mFlickrDataAdapter);
        if(photoFlickrList != null && photoFlickrList.size() > 0 ){
            mFlickrDataAdapter.setDataMap(photoFlickrList);
        }else{
            loadPage(flickrDataListner,currentPage);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.avNext:
                currentPage++;
                loadPage(flickrDataListner,currentPage);
                break;
            case R.id.avPre:
                currentPage--;
                loadPage(flickrDataListner,currentPage);
        }

    }

    private void loadPage(FlickrDataListner flickrDataListner, int currentPage) {
        if(currentPage > 0) {
            if(mViewModel.getPhotolist(currentPage)!= null && mViewModel.getPhotolist(currentPage).size() > 0 ){
                setData();
            }else {
                mProgressDialog.show("Loading data . . .");
                mFLoader.getFlickerData(flickrDataListner, currentPage);
            }
        }
    }

    protected void setData(){
        tvPage.setText(String.valueOf(currentPage));
        mFlickrDataAdapter.setDataMap(mViewModel.getPhotolist(currentPage));
    }

    FlickrDataListner flickrDataListner = new FlickrDataListner() {
        @Override
        public void onData(FlickrResponseData data) {
            mProgressDialog.dismiss();
            setData();
        }

        @Override
        public void onFailure(ErrorD errorResponse) {
            mProgressDialog.changeToError(errorResponse.getMessage());

        }
    };

    DataRefreshListener dataRefreshListener = new DataRefreshListener() {
        @Override
        public void onRefresh() {
            mFlickrDataAdapter.setDataMap(mViewModel.getPhotolist(currentPage));
        }
    };
}
