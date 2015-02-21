package com.greenmoon55.easytodo;

import android.app.Application;

/**
 * Created by greenmoon55 on 2015/2/21.
 */
public class EasyTodoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AVService.AVInit(this);
    }
}