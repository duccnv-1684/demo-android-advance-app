package com.example.ducnguyen.demoandroidadvanceapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DemoStorageActivity extends AppCompatActivity {
    private static final int SPAN_COUNT = 2;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private RecyclerView mRecyclerView;
    private ImageAdapter mDemoRecyclerLinearAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Image> mImages;
    private ProgressBar mProgressBar;
    private LoadImagesTask mLoadImagesTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImages = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recycler_main);
        mProgressBar = findViewById(R.id.progress_bar_main);
        mLoadImagesTask = new LoadImagesTask();
        checkPermissionAndLoadData();
        mLayoutManager = new LinearLayoutManager(this);
        setUpRecycleView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_linear_layout_manager:
                mDemoRecyclerLinearAdapter = new ImageAdapter(mImages
                        , R.layout.item_hero_linear);
                mLayoutManager = new LinearLayoutManager(this);
                setUpRecycleView();
                break;
            case R.id.menu_grid_layout_manager:
                mDemoRecyclerLinearAdapter = new ImageAdapter(mImages
                        , R.layout.item_hero_grid);
                mLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
                setUpRecycleView();
                break;
            case R.id.menu_staggered_grid_layout_manager:
                mDemoRecyclerLinearAdapter = new ImageAdapter(mImages
                        , R.layout.item_hero_staggerd);
                mLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT
                        , StaggeredGridLayoutManager.VERTICAL);
                setUpRecycleView();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLoadImagesTask.execute();
                } else {
                    Toast.makeText(this, R.string.message_permission_denied
                            , Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void checkPermissionAndLoadData() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) mLoadImagesTask.execute();
        else if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            mLoadImagesTask.execute();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                    , PERMISSION_REQUEST_CODE);
        }
    }

    private void setUpRecycleView() {
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mDemoRecyclerLinearAdapter);
    }

    private class LoadImagesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Cursor cursor = getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    , null
                    , null
                    , null
                    , MediaStore.Images.Media.DEFAULT_SORT_ORDER
            );
            assert cursor != null;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                Image image = new Image(data);
                mImages.add(image);
                cursor.moveToNext();
            }
            cursor.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mDemoRecyclerLinearAdapter = new ImageAdapter(mImages, R.layout.item_hero_linear);
            setUpRecycleView();
        }
    }
}
