package com.example.cameraapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.File;

public class ClearService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    //강제종료했을때 임시파일 삭제
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        File file = new File(MainActivity.tempFileName);
        if (file.exists()) {
            file.delete();
        }
        stopSelf();
    }

}
