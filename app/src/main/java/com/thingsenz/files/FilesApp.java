package com.thingsenz.files;

import android.app.Application;

import com.thingsenz.files.activities.MainActivity;
import com.thingsenz.files.models.MediaFileListModel;

import java.util.ArrayList;

public class FilesApp extends Application {

    private static FilesApp inst;
    private ArrayList<MediaFileListModel> mediaFileListModels;

    @Override
    public void onCreate(){
        super.onCreate();
        inst=this;
    }

    public static synchronized FilesApp getInstance(){
        return inst;
    }

    public void setButtonBackPressed(MainActivity.ButtonBackPressListener listener) {
        MainActivity.buttonBackPressListener=listener;
    }

    public void setMediaFileListArrayList(ArrayList<MediaFileListModel> mediaFileListArrayList) {
        this.mediaFileListModels = mediaFileListArrayList;
    }

    public ArrayList<MediaFileListModel> getMediaFileListModeLArray() {
        return mediaFileListModels;
    }

}
