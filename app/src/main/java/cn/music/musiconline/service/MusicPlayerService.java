package cn.music.musiconline.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import cn.music.musiconline.util.CommonUtil;
import cn.music.musiconline.util.Conts;
import cn.music.musiconline.util.Player;

/**
 * Created by Administrator on 2016/7/5.
 */
public class MusicPlayerService extends Service implements Conts{
    private Player player;
    private int currentMusicIndex;
    private int pauseposition;

    //播放地址转化为Url
    private String MusicUrl;

    //声明一个广播接收者
    private MusicServiceReceiver receiver;

    /*
    线程模块
     */
    private UpdateThread updateThread;
    private boolean isRunning;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        initBroadReceiver();


    }

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

    private class MusicServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(ACTION_PLAY_OR_PAUSE))
            {
                if(player.getPlayer()==true)
                {
                    pause();
                }
                else
                {
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
        }
    }

    /**
     * 音乐播放器进行播放
     */
    private void play(){
        player.playUrl(MusicUrl);
        player.play();
        Intent intent = new Intent();
        intent.setAction(ACTION_SET_PLAY_STATE);
        intent.putExtra(EXTRA_DURATION,player.getDuration());
        sendBroadcast(intent);
        startUpdateThread();
    }

    /**
     * 拖动音乐播放器进度条
     * @param progress
     */
    private void play(float progress) {
        pauseposition = (int)(player.getCurrentPosition()*progress /100 );
        play();
    }

    /**
     * 音乐播放器暂停的时候
     */
    private void pause(){
        if(player.getPlayer()==true)
        {
            player.pause();
            pauseposition = (int) player.getCurrentPosition();
            Intent intent = new Intent();
            intent.setAction(ACTION_SET_PAUSE_STATE);
            sendBroadcast(intent);
            startUpdateThread();
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

                intent.putExtra(EXTRA_CURRENTINDEX, CommonUtil.toSimpleDate(player.getCurrentPosition()));
                intent.putExtra(EXTRA_PERCENT,player.getDuration()==0?0:player.getDuration());

                sendBroadcast(intent);
                try{
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
