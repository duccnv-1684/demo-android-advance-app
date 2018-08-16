package com.example.ducnguyen.demoandroidadvanceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

public class DemoRecyclerViewActivity extends AppCompatActivity {
    private int[] mImageId = {R.raw.hero_one, R.raw.hero_two, R.raw.hero_three, R.raw.hero_four,
            R.raw.hero_five, R.raw.hero_six, R.raw.hero_seven, R.raw.hero_eight, R.raw.hero_nine,
            R.raw.hero_ten, R.raw.hero_eleven};
    private RecyclerView mRecyclerView;
    private ImageAdapter mDemoRecyclerLinearAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final int SPAN_COUNT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_main);
        mDemoRecyclerLinearAdapter = new ImageAdapter(mImageId, R.layout.item_hero_linear);
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
                mDemoRecyclerLinearAdapter = new ImageAdapter(mImageId
                        , R.layout.item_hero_linear);
                mLayoutManager = new LinearLayoutManager(this);
                setUpRecycleView();
                break;
            case R.id.menu_grid_layout_manager:
                mDemoRecyclerLinearAdapter = new ImageAdapter(mImageId
                        , R.layout.item_hero_grid);
                mLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
                setUpRecycleView();
                break;
            case R.id.menu_staggered_grid_layout_manager:
                mDemoRecyclerLinearAdapter = new ImageAdapter(mImageId
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

    private void setUpRecycleView() {
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mDemoRecyclerLinearAdapter);
    }
}
