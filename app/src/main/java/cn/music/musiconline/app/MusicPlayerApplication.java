/*
 * Copyright (C) 2004 - 2016 UCWeb Inc. All Rights Reserved.
 * Description : 已定义音乐播放器的Application，用于预先加载一些网络访问请求
 * line, otherwise ... ^_*
 *
 * Creation    : 2015-07-05
 * Author      : feng_wb@ucweb.com
 */
package cn.music.musiconline.app;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/7/5.
 */
public class MusicPlayerApplication extends Application {

    //创建一个Volley的访问队列
    public static RequestQueue mRequestQueue;

    public RequestQueue getmRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            return mRequestQueue;
        }
        return null;
    }


    public <T> void addToRequestQueue(Request<T> request){
        getmRequestQueue().add(request);
    }


    private List<URL> list_musics;
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
       /* IDao<URL> IDao= MusicFactory.newMediaInstance(this);
        list_musics = IDao.getData();*/
    }

    public void setList_musics(List<URL> list_musics) {
        if(list_musics == null)
        {
            list_musics = new ArrayList<URL>();
        }
        this.list_musics = list_musics;
    }

    public List<URL> getData()
    {
        return list_musics;
    }
}
