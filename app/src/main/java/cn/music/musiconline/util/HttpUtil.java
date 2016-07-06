/*
 * Copyright (C) 2004 - 2016 UCWeb Inc. All Rights Reserved.
 * Description : Volley网络加载队列
 * line, otherwise ... ^_*
 *
 * Creation    : 2015-07-05
 * Author      : feng_wb@ucweb.com
 */
package cn.music.musiconline.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;


import cn.music.musiconline.bean.CloudMusic;
import cn.music.musiconline.bean.CloudSongs;

/**
 * Created by Administrator on 2016/7/5.
 */
public class HttpUtil {

    //创建一个Volley的访问队列
    public static RequestQueue queue;

    public RequestQueue getmRequestQueue(final Context context) {
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
            return queue;
        }
        return null;
    }

    public static void getMusic(final Context context,String name,final OnGetMusicListener listener)
    {
        try{
            //1)判断有没有队列
            if(queue == null)
            {
                queue = Volley.newRequestQueue(context);
            }
            //2)创建网络请求
            //对指定的歌曲名称进行UTF8转码
            String musicname = URLEncoder.encode(name,"utf8");
            //利用转码后的歌曲名称，拼接URL
            String url = UrlContants.CLOUD_MUSIC_API_PREFIX + "type=1&s='" + musicname + "'&limit=10&offset=0";
            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String result) {
                    //网络访问成功后，会调用该方法
                    //onResponse方法是在主线程上调用的
                   /* JsonParser parser = new JsonParser();
                    JsonObject rootObject = parser.parse(result).getAsJsonObject();
                    JsonElement projectElement = rootObject.get("result");
                    Log.e("projectElement",""+projectElement);
                    Log.e("result",""+result);
                    Gson gson = new Gson();
                    List<CloudSongs> projectList = new ArrayList<>();
                    if(projectElement.isJsonObject())
                    {
                        CloudSongs cloudSongs = gson.fromJson(result,CloudSongs.class);
                        //projectList.add(cloudSongs);
                        Log.d("Object", ""+cloudSongs.getResult().getSongCount());
                    }
                    else if(projectElement.isJsonArray())
                    {
                        Type projectListType = new TypeToken<List<CloudResult>>() {}.getType();
                        projectList = gson.fromJson(projectElement, projectListType);
                        Log.d("Array", projectList.toString());
                    }*/
                    Gson gson = new Gson();
                    CloudSongs cloudSongs = gson.fromJson(result,CloudSongs.class);
                    Log.d("TAG",cloudSongs.toString());
                    listener.OnSuccess(cloudSongs);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    //网络访问失败后，会调用该方法
                    //onErrorResponse方法是在主线程上调用的
                    Toast.makeText(context,"网络繁忙，稍后重试",Toast.LENGTH_LONG).show();
                }
            });

            //3)把网络访问请求对象放入队列中
            queue.add(request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
    //定义一个监听器，当网络访问成功后，回调该监听器中的方法
    public interface OnGetMusicListener{
        void OnSuccess(CloudSongs cloudSongs);
    }

}
