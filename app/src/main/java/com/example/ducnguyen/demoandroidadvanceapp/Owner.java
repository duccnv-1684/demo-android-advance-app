package com.example.ducnguyen.demoandroidadvanceapp;

public class Owner {
    private String mLogin;
    private long mId;
    private String mNodeId;
    private String mUrl;

    public Owner(String login, long id, String nodeId, String url) {
        this.mLogin = login;
        this.mId = id;
        this.mNodeId = nodeId;
        this.mUrl = url;
    }

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        this.mLogin = login;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getNodeId() {
        return mNodeId;
    }

    public void setNodeId(String nodeId) {
        this.mNodeId = nodeId;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }
}
