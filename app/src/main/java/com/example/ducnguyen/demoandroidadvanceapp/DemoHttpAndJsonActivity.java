package com.example.ducnguyen.demoandroidadvanceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class DemoHttpAndJsonActivity extends AppCompatActivity
        implements OnLoadDataCompleteListener{
    private static final String GIT_HUB_API_LINK = "https://api.github.com/users/google/repos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_http_and_json);
        LoadDataFromUrlTask loadDataFromUrlTask = new LoadDataFromUrlTask(this);
        loadDataFromUrlTask.execute(GIT_HUB_API_LINK);
    }

    @Override
    public void onLoadDataSuccess(List<Repository> repositories) {
        Toast.makeText(this, R.string.message_load_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadDataFail(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
