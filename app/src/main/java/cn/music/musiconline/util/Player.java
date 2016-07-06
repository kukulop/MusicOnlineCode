/**
 * 这是一个自定义的流媒体播放器
 * @Author:Zalos
 * @DateTime:20160704
 * @Version:Ver0.1
 */
package cn.music.musiconline.util;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import cn.music.musiconline.dal.solveProgress;

/**
 * Created by Administrator on 2016/7/4.
 */
public class Player implements Conts,solveProgress,MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private MediaPlayer mediaPlayer;
    private Timer mTimer = new Timer();

    private SeekBar seekBar;//

    private boolean isplaying = false;//是否正在播放
    private int pauseposition = 0;//播放断点

    public Player(SeekBar seekbar)
    {
        super();
        this.seekBar = seekbar;

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }

        //每一秒触发一次
        mTimer.schedule(timerTask,0,100);
    }

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if(mediaPlayer==null)
            {
                return;
            }
            if(mediaPlayer.isPlaying() && seekBar.isPressed()==false)
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
                long pos = seekBar.getMax()*position/duration;
                seekBar.setProgress((int) pos);
            }
        }
    };


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
     * 开始播放
     */
    public void play(){
        mediaPlayer.seekTo(pauseposition);
        isplaying = true;
        mediaPlayer.start();
    }

    /**
     * 暂停播放
     */
    public void pause(){
        mediaPlayer.pause();
        isplaying = false;
        pauseposition = mediaPlayer.getCurrentPosition();
    }

    /**
     * 停止播放
     */
    public void stop(){
        if(mediaPlayer!=null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }


    /**
     * 获取当前player播放状态
     */
    public boolean getPlayer(){
        return isplaying;
    }

    /**
     * 获取当前player的当前进度
     */
    public long getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    /**
     * 获取当前player的播放长度
     */
    public int getDuration(){
        return mediaPlayer.getDuration();
    }



    /**
     * 缓冲更新
     * @param mediaPlayer
     * @param i
     */
    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
          seekBar.setSecondaryProgress(i);
          int currentProgress = 100*mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration();
          Log.e(currentProgress+"% play",i + "buffer");
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
          mediaPlayer.start();
          Log.e("mediaPlayer","onPrepared");
    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
          Log.e("mediaPlayer","onCompletion");
    }

    @Override
    public void updateProgress(SeekBar seekBar) {
        /*int currentProgress = seekBar.getMax()*mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration();
        //Log.e(currentProgress+"% play",i + "buffer");
        //计算进度(进度条最大刻度*当前音乐播放位置/当前音乐时长)
        long pos = seekBar.getMax()*position/duration;
        seekBar.setProgress((int) pos);*/
    }
}
