package com.example.ducnguyen.demoandroidadvanceapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoadDataFromUrlTask extends AsyncTask<String, Void, List<Repository>> {
    private static final String REQUEST_METHOD = "GET";
    private static final int CONNECTION_TIMEOUT = 15000;
    private static final int READ_TIME_OUT = 10000;
    private static final String BREAK_LINE = "\n";
    private static final String KEY_REPOSITORY_ID = "id";
    private static final String KEY_REPOSITORY_NODE_ID = "node_id";
    private static final String KEY_REPOSITORY_NAME = "name";
    private static final String KEY_REPOSITORY_FULL_NAME = "full_name";
    private static final String KEY_REPOSITORY_OWNER = "owner";
    private static final String KEY_OWNER_LOGIN = "login";
    private static final String KEY_OWNER_ID = "id";
    private static final String KEY_OWNER_NODE_ID = "node_id";
    private static final String KEY_OWNER_URL = "url";
    private OnLoadDataCompleteListener mOnLoadDataCompleteListener;

    public LoadDataFromUrlTask(OnLoadDataCompleteListener onLoadDataCompleteListener) {
        mOnLoadDataCompleteListener = onLoadDataCompleteListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Repository> doInBackground(String... strings) {
        String url = strings[0];
        String jsonString;
        List<Repository> repositories = null;
        try {
            jsonString = getJSONStringFromUrl(url);
            repositories = getListRepository(jsonString);
        } catch (IOException|JSONException e) {
            mOnLoadDataCompleteListener.onLoadDataFail(e.getMessage());
        }
        return repositories;
    }

    @Override
    protected void onPostExecute(List<Repository> repositories) {
        super.onPostExecute(repositories);
        mOnLoadDataCompleteListener.onLoadDataSuccess(repositories);
    }

    private String getJSONStringFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(REQUEST_METHOD);
        httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
        httpURLConnection.setReadTimeout(READ_TIME_OUT);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.connect();
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(BREAK_LINE);
        }
        bufferedReader.close();
        String test = stringBuilder.toString();
        httpURLConnection.disconnect();
        return stringBuilder.toString();
    }

    private List<Repository> getListRepository(String jsonString) throws JSONException {
        List<Repository> repositories = new ArrayList<>();
        JSONArray jsonRepositories = new JSONArray(jsonString);
        for (int i = 0; i < jsonRepositories.length(); i++) {
            JSONObject jsonRepository = jsonRepositories.getJSONObject(i);
            long repositoryId = jsonRepository.getLong(KEY_REPOSITORY_ID);
            String repositoryNodeId = jsonRepository.getString(KEY_REPOSITORY_NODE_ID);
            String repositoryName = jsonRepository.getString(KEY_REPOSITORY_NAME);
            String repositoryFullName = jsonRepository.getString(KEY_REPOSITORY_FULL_NAME);
            JSONObject jsonOwner = jsonRepository.getJSONObject(KEY_REPOSITORY_OWNER);
            String ownerLogin = jsonOwner.getString(KEY_OWNER_LOGIN);
            long ownerId = jsonOwner.getLong(KEY_OWNER_ID);
            String ownerNodeId = jsonOwner.getString(KEY_OWNER_NODE_ID);
            String ownerUrl = jsonOwner.getString(KEY_OWNER_URL);
            Owner repositoryOwner = new Owner(ownerLogin, ownerId, ownerNodeId, ownerUrl);
            Repository repository = new Repository(repositoryId, repositoryNodeId, repositoryName
                    , repositoryFullName, repositoryOwner);
            repositories.add(repository);
        }
        return repositories;
    }
}
