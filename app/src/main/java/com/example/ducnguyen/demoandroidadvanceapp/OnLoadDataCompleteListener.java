package com.example.ducnguyen.demoandroidadvanceapp;

import java.util.List;

public interface OnLoadDataCompleteListener {
    void onLoadDataSuccess(List<Repository> repositories);
    void onLoadDataFail(String errorMessage);
}
