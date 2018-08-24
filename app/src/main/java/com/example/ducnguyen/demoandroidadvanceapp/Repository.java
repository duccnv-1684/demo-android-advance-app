package com.example.ducnguyen.demoandroidadvanceapp;

public class Repository {
    private long mId;
    private String mNodeId;
    private String mName;
    private String mFullName;
    private Owner mOwner;

    public Repository(long id, String nodeId, String name, String fullName, Owner owner) {
        mId = id;
        mNodeId = nodeId;
        mName = name;
        mFullName = fullName;
        mOwner = owner;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getNodeId() {
        return mNodeId;
    }

    public void setNodeId(String nodeId) {
        mNodeId = nodeId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public Owner getOwner() {
        return mOwner;
    }

    public void setOwner(Owner owner) {
        mOwner = owner;
    }
}
