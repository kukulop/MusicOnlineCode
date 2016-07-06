/*
 * Copyright (C) 2004 - 2016 UCWeb Inc. All Rights Reserved.
 * Description : 这是音乐播放器的后台进程，已实现播放，暂停等操作
 * line, otherwise ... ^_*
 *
 * Creation    : 2016-07-05
 * Author      : feng_wb@ucweb.com
 */
package cn.music.musiconline.service;

import java.io.IOException;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import cn.music.musiconline.util.Conts;

/**
 * Created by Administrator on 2016/7/4.
 */
public class MyMusicService extends Service implements Conts, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {


    private int currentMusicIndex;
    private int pauseposition = 0;

    //播放地址转化为Url
    private String MusicUrl;

    //声明一个广播接收者
    private MusicServiceReceiver receiver;

    /*
    线程模块
     */
    private UpdateThread updateThread;
    private boolean isRunning;

    /*
    声明媒体播放器
     */
    private MediaPlayer mediaPlayer;
    //private Timer mTimer = new Timer();

    /*
    声明拖动进度条时
     */
    private boolean isStacking = false;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //设置广播接收器
        initBroadReceiver();
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
                mediaPlayer.setOnBufferingUpdateListener(this);
                mediaPlayer.setOnPreparedListener(this);
                mediaPlayer.setOnCompletionListener(this);
            } catch (Exception e) {
                e.printStackTrace();
            }


        //每一秒触发一次
        //mTimer.schedule(timerTask,0,100);
    }

    /*private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if(mediaPlayer==null)
            {
                return;
            }
            if(mediaPlayer.isPlaying())
            {
                handler.sendEmptyMessage(0);//发送消息
            }
        }
    };
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int position = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();
            if(duration>0)
            {
                long pos = 100*position/duration;
                //seekBar.setProgress((int) pos);
            }
        }
    };*/



    private void initBroadReceiver() {

        receiver = new MusicServiceReceiver();
        IntentFilter filter = new IntentFilter();

        filter.addAction(ACTION_PLAY_OR_PAUSE);
        filter.addAction(ACTION_PREVIOUS);
        filter.addAction(ACTION_NEXT);
        filter.addAction(ACTION_PLAY);
        filter.addAction(ACTION_PLAY_FROM_PRECENT);
        registerReceiver(receiver, filter);
    }

    private int BufferProgress;
    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        BufferProgress = i;
        int currentProgress = 100*mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration();
        Log.e(currentProgress+"% play",i + "buffer");
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        stopUdateThread();
        play();
    }

    private class MusicServiceReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
              String action = intent.getAction();
              if(action.equals(ACTION_PLAY_OR_PAUSE))
              {
                  if(mediaPlayer.isPlaying()==true)
                  {
                      playUrl(MusicUrl);
                      Log.i("MusicUrl",""+MusicUrl);
                      pause();
                  }
                  else
                  {
                      MusicUrl = intent.getStringExtra(EXTRA_MUSIC_URL);
                      play();
                  }
              }
              else if(action.equals(ACTION_NEXT))
              {
                    next();
              }
              else if(action.equals(ACTION_PREVIOUS))
              {
                    previous();
              }
              else if(ACTION_PLAY_FROM_PRECENT.equals(action))
              {
                  float percent = intent.getFloatExtra(EXTRA_PERCENT, 0F);
                  isStacking = true;
                  play(percent);
              }
        }
    }

    /**
     * 重置播放器，解析地址
     * @param url
     */
    public void playUrl(String url)
    {
        try{
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url); //设置数据源
            mediaPlayer.prepare();  // prepare自动播放
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    /**
     * 音乐播放器进行播放
     */
    private void play(){
        if(isStacking==false)
        {
            playUrl(MusicUrl);
        }
        mediaPlayer.seekTo(pauseposition);
        mediaPlayer.start();
        isStacking = false;
        Log.i("musicplay", "music play success");
        Intent intent = new Intent();
        intent.setAction(ACTION_SET_PLAY_STATE);
        intent.putExtra(EXTRA_DURATION, mediaPlayer.getDuration());
        sendBroadcast(intent);
        startUpdateThread();
    }

    /**
     * 拖动音乐播放器进度条
     * @param progress
     */
    private void play(float progress) {
        pauseposition = (int)(mediaPlayer.getCurrentPosition()*progress /100 );
        Log.i("play progress", ""+pauseposition);
        play();
    }

    /**
     * 音乐播放器暂停的时候
     */
    private void pause(){
        if(mediaPlayer.isPlaying()==true)
        {
            mediaPlayer.pause();
            pauseposition = (int) mediaPlayer.getCurrentPosition();
            Intent intent = new Intent();
            intent.setAction(ACTION_SET_PAUSE_STATE);
            sendBroadcast(intent);
            stopUdateThread();
        }
    }

    /**
     * 音乐播放器播放上一首
     */
    private void previous() {
    }

    /**
     * 音乐播放器播放下一首
     */
    private void next() {
    }


    /*
    实现后台播放的线程模块
     */
    /**
     * 开启后台播放线程
     */
     private void startUpdateThread()
     {
         if(updateThread==null)
         {
             updateThread = new UpdateThread();
             isRunning = true;
             updateThread.start();
         }
     }
    /**
     * 关闭后台播放线程
     */
    private void stopUdateThread()
    {
        if(updateThread!=null)
        {
            isRunning = false;
            updateThread = null;
        }
    }
    /**
     * 播放线程的实现
     */
    private class UpdateThread extends Thread{
        @Override
        public void run() {
            Intent intent = new Intent();
            while(isRunning)
            {
                intent.setAction(ACTION_UPDATE_PROGRESS);
                intent.putExtra(EXTRA_CURRENTION, mediaPlayer.getCurrentPosition());
                int percent = mediaPlayer.getCurrentPosition() * 100 / mediaPlayer.getDuration();
                intent.putExtra(EXTRA_PERCENT, percent == 0 ? 0 : percent);
                intent.putExtra(EXTRA_SEEKBAR_PROGRESS,BufferProgress);
                sendBroadcast(intent);

                try{
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 服务销毁时
     */
    @Override
    public void onDestroy() {
        // 释放播放器的资源
        mediaPlayer.release();
        // 取消注册广播接收者
        unregisterReceiver(receiver);
    }
}
