package com.thingsenz.medialoader;

import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.loader.content.Loader;

import com.thingsenz.medialoader.callback.OnAudioLoaderCallback;
import com.thingsenz.medialoader.callback.OnFileLoaderCallback;
import com.thingsenz.medialoader.callback.OnLoaderCallback;
import com.thingsenz.medialoader.callback.OnPhotoLoaderCallback;
import com.thingsenz.medialoader.callback.OnVideoLoaderCallback;
import com.thingsenz.medialoader.loaders.AbsLoaderCallback;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MediaLoader {

    private static int DEF_START_ID=1000;
    private String TAG=this.getClass().getSimpleName();
    private static MediaLoader loader=new MediaLoader();
    private Map<String, Queue<LoaderTask>> taskGroup=new HashMap<>();

    private Map<String, Integer> ids=new HashMap<>();
    private final int MSG_CODE_LOAD_FINISH=101;
    private final int MSG_CODE_LOAD_START=102;

    public static class LoaderTask {
        public WeakReference<FragmentActivity> activity;
        public OnLoaderCallback onLoaderCallback;

        public LoaderTask(WeakReference<FragmentActivity> activity,OnLoaderCallback onLoaderCallback){
            this.activity=activity;
            this.onLoaderCallback=onLoaderCallback;
        }

    }

    private Handler handler=new Handler(Looper.getMainLooper()){
      @Override
      public void handleMessage(Message msg) {
          super.handleMessage(msg);
          switch (msg.what) {
              case MSG_CODE_LOAD_FINISH:
                  Message msf = new Message();
                  msf.what=MSG_CODE_LOAD_START;
                  msf.obj=msg.obj;
                  sendMessage(msf);
                  break;
              case MSG_CODE_LOAD_START:
                  String group=(String) msg.obj;
                  if (!TextUtils.isEmpty(group)){
                      Queue<LoaderTask> queue=taskGroup.get(group);
                      LoaderTask task=queue.poll();
                      if (task!=null) queueLoader(task.activity.get(),task.onLoaderCallback);
                  }
                  break;
          }
      }
    };

    private MediaLoader() {}
    private int checkIds(FragmentActivity activity) {
        String name=activity.getClass().getName();
        int id;
        if (!ids.containsKey(name)) {
            id=DEF_START_ID;
            ids.put(name,id);
        } else {
            int preId=ids.get(name);
            preId++;
            ids.put(name,preId);
            id=preId;
        }
        return id;
    }

    private void loadMedia(FragmentActivity activity, AbsLoaderCallback absLoaderCallBack){
        activity.getSupportLoaderManager().restartLoader(checkIds(activity),null,absLoaderCallBack);
    }

    private synchronized void load(FragmentActivity activity, OnLoaderCallback onLoaderCallBack){

        String name = activity.getClass().getSimpleName();
        Queue<LoaderTask> queue = taskGroup.get(name);
        LoaderTask task = new LoaderTask(new WeakReference<>(activity),onLoaderCallBack);
        if(queue==null){
            queue = new LinkedList<>();
            taskGroup.put(name,queue);
        }
        queue.offer(task);
        Log.d(TAG,"after offer current queue group = " + name + " queue length = " + queue.size());
        if(queue.size()==1){
            Message message = Message.obtain();
            message.what = MSG_CODE_LOAD_START;
            message.obj = name;
            handler.sendMessage(message);
        }
    }

    private void queueLoader(final FragmentActivity activity, OnLoaderCallback onLoaderCallBack){
        loadMedia(activity, new AbsLoaderCallback(activity,onLoaderCallBack){
            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                super.onLoaderReset(loader);
                Queue<LoaderTask> queue = taskGroup.get(activity.getClass().getSimpleName());
                if(queue!=null){
                    queue.clear();
                }
                Log.d(TAG,"***onLoaderReset***");
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                super.onLoadFinished(loader, data);
                Message message = Message.obtain();
                message.what = MSG_CODE_LOAD_FINISH;
                message.obj = activity.getClass().getSimpleName();
                handler.sendMessage(message);
                Log.d(TAG,"***onLoaderFinished***");
            }
        });
    }

    public void loadPhotos(FragmentActivity activity, OnPhotoLoaderCallback onPhotoLoaderCallBack){
        load(activity,onPhotoLoaderCallBack);
    }

    public void loadVideos(FragmentActivity activity, OnVideoLoaderCallback onVideoLoaderCallBack){
        load(activity,onVideoLoaderCallBack);
    }

    public void loadAudios(FragmentActivity activity, OnAudioLoaderCallback onAudioLoaderCallBack){
        load(activity,onAudioLoaderCallBack);
    }

    public void loadFiles(FragmentActivity activity, OnFileLoaderCallback onFileLoaderCallBack){
        load(activity,onFileLoaderCallBack);
    }





}
