package com.thingsenz.medialoader.bean;

import java.io.Serializable;

public class BaseItem implements Serializable {
    private int id;
    private String displayName;
    private String path;
    private long size;
    private long modified;

    public BaseItem() {
    }

    public BaseItem(int id, String displayName, String path) {
        this(id, displayName, path, 0);
    }

    public BaseItem(int id, String displayName, String path, long size) {
        this(id, displayName, path, size, 0);
    }

    public BaseItem(int id, String displayName, String path, long size, long modified) {
        this.id = id;
        this.displayName = displayName;
        this.path = path;
        this.size = size;
        this.modified = modified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getModified() {
        return modified;
    }

    public void setModified(long modified) {
        this.modified = modified;
    }
}