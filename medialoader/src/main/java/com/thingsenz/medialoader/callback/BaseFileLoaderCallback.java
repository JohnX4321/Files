package com.thingsenz.medialoader.callback;

import android.net.Uri;
import android.provider.MediaStore;

import com.thingsenz.medialoader.bean.FileProperty;
import com.thingsenz.medialoader.bean.FileType;

public abstract class BaseFileLoaderCallback<T> extends BaseLoaderCallback<T> {

    public static final String VOLUME_NAME="external";
    private FileProperty property;

    public BaseFileLoaderCallback() {
        this(new FileProperty(null,null));
    }

    public BaseFileLoaderCallback(FileType type) {
        this(type.getProperty());
    }

    public BaseFileLoaderCallback(FileProperty property) {
        this.property=property;
    }

    @Override
    public Uri getQueryUri() {
        return MediaStore.Files.getContentUri(VOLUME_NAME);
    }

    @Override
    public String getSelections() {
        if (property!=null) return property.createSelection();
        return null;
    }

    @Override
    public String[] getSelectProjection() {
        return new String[]{MediaStore.Files.FileColumns._ID,MediaStore.Files.FileColumns.DATA,MediaStore.Files.FileColumns.SIZE,MediaStore.Files.FileColumns.DISPLAY_NAME,MediaStore.Files.FileColumns.MIME_TYPE,MediaStore.Files.FileColumns.DATE_MODIFIED};
    }

    @Override
    public String[] getSelectionArgs() {
        if (property!=null) property.createSelectionArgs();
        return null;
    }

}
