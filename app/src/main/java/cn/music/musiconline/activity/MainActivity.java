/*
 * Copyright (C) 2004 - 2016 UCWeb Inc. All Rights Reserved.
 * Description : 这是音乐播放器的主入口，在这里通过接收更改状态的广播
 * line, otherwise ... ^_*
 *
 * Creation    : 2015-07-05
 * Author      : feng_wb@ucweb.com
 */
package cn.music.musiconline.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.List;

import cn.music.musiconline.R;
import cn.music.musiconline.adapter.SongAdapter;
import cn.music.musiconline.bean.CloudSongs;
import cn.music.musiconline.service.MyMusicService;
import cn.music.musiconline.util.CommonUtil;
import cn.music.musiconline.util.Conts;
import cn.music.musiconline.util.HttpUtil;

public class MainActivity extends BaseActivity implements Conts, View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    /*
    GUI控件集合
     */
    private View header;
    private ListView lists;
    private TextView tvMusicPlayer;
    private SeekBar sbMusicPlayer;
    private TextView tvMusicPosition;
    private TextView tvMusicDuration;

    private EditText etMusicPlayerUrl;

    private ImageButton btnStartOrPause;
    private ImageButton btnNext;
    private ImageButton btnPrevious;

    /*
    注册广播监听器
     */
    private BroadReceiver receiver;


    /*
    用户是否在进行拖拽
     */
    private boolean isStartTracking;

    /*
    声明标题左菜单
     */
    private ImageView ivHeaderRight;
    private ImageView ivHeaderLeft;

    /*
    声明一个侧滑菜单
     */
    private SlidingMenu menu;
    private View SearchSlidingMenu;
    private EditText etSlidingMenu;
    private ImageView ivSlidingMenuDelete;
    private Button btnSlidingMenu;
    private ListView lvSlidingMenu;


    /*
    用来封装云音乐数据
     */
    private CloudSongs csMusics;
    private List<CloudSongs.Result.Song> listMusics;//数据源
    private SongAdapter adapter; //适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initSlidingMenu();
        initView();
        initListener();
        initBroadcast();

    }

    private void initSlidingMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu);
    }

    private void initBroadcast() {
         receiver = new BroadReceiver();
         IntentFilter filter = new IntentFilter();
         filter.addAction(ACTION_SET_PLAY_STATE);
         filter.addAction(ACTION_SET_PAUSE_STATE);
         filter.addAction(ACTION_PLAY);
         filter.addAction(ACTION_UPDATE_PROGRESS);
         registerReceiver(receiver,filter);
    }

    private void initView() {

        header = this.findViewById(R.id.musics_layout_header);
        lists = (ListView) this.findViewById(R.id.musics_lv_lists);
        tvMusicPlayer = (TextView) this.findViewById(R.id.tv_music);
        sbMusicPlayer = (SeekBar) this.findViewById(R.id.sb_music);
        tvMusicPosition = (TextView) this.findViewById(R.id.tv_currentposition);
        tvMusicDuration = (TextView) this.findViewById(R.id.tv_drations);
        etMusicPlayerUrl = (EditText)this.findViewById(R.id.musics_edittext_url);
        btnStartOrPause = (ImageButton) this.findViewById(R.id.ib_start_or_pause);
        btnNext = (ImageButton) this.findViewById(R.id.ib_next);
        btnPrevious = (ImageButton) this.findViewById(R.id.ib_previous);

        ivHeaderLeft = (ImageView) header.findViewById(R.id.iv_left_icon);
        ivHeaderRight = (ImageView) header.findViewById(R.id.iv_right_icon);

        SearchSlidingMenu = menu.findViewById(R.id.musics_layout_search);
        lvSlidingMenu = (ListView) menu.findViewById(R.id.musics_lv_lists);

        etSlidingMenu = (EditText) SearchSlidingMenu.findViewById(R.id.search_et_input);
        ivSlidingMenuDelete = (ImageView) SearchSlidingMenu.findViewById(R.id.search_iv_delete);
        btnSlidingMenu = (Button) SearchSlidingMenu.findViewById(R.id.search_btn_start);

        Intent intent = new Intent(this, MyMusicService.class);
        startService(intent);
    }

    private void initListener() {
        btnStartOrPause.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        sbMusicPlayer.setOnSeekBarChangeListener(this);

        ivHeaderLeft.setOnClickListener(this);
        ivHeaderRight.setOnClickListener(this);

        btnSlidingMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId())
            {
                case R.id.ib_start_or_pause:
                    intent.setAction(ACTION_PLAY_OR_PAUSE);
                    intent.putExtra(EXTRA_MUSIC_URL,etMusicPlayerUrl.getText().toString());
                    sendBroadcast(intent);
                    break;
                case R.id.ib_next:
                    intent.setAction(ACTION_NEXT);
                    sendBroadcast(intent);
                    break;
                case R.id.ib_previous:
                    intent.setAction(ACTION_PREVIOUS);
                    sendBroadcast(intent);
                    break;
                case R.id.iv_left_icon:
                    finish();
                    break;
                case R.id.iv_right_icon:
                    menu.showMenu();
                    break;
                case R.id.search_btn_start:
                    searchMusic();
                    break;
            }
    }




    /**
     * 广播接收器
     */
    private class BroadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if(action.equals(ACTION_SET_PLAY_STATE))
            {
                btnStartOrPause.setImageResource(android.R.drawable.ic_media_pause);
                int Duration = intent.getIntExtra(EXTRA_DURATION, 0);
                tvMusicDuration.setText(CommonUtil.toSimpleDate(Duration));
                tvMusicPlayer.setText(
                        etMusicPlayerUrl.getText().toString().split(".mp3")[0]);

            }
            else if(action.equals(ACTION_SET_PAUSE_STATE))
            {
                btnStartOrPause.setImageResource(android.R.drawable.ic_media_play);
            }
            else if(action.equals(ACTION_UPDATE_PROGRESS))
            {
                int CurrentPosition = intent.getIntExtra(EXTRA_CURRENTION, 0);
                tvMusicPosition.setText(CommonUtil.toSimpleDate(CurrentPosition));
                int i = intent.getIntExtra(EXTRA_SEEKBAR_PROGRESS,0);
                Log.i("SecondaryProgress",""+i);
                sbMusicPlayer.setSecondaryProgress(i);

                int Percent = intent.getIntExtra(EXTRA_PERCENT, 0);
                if (!isStartTracking) {
                    sbMusicPlayer.setProgress(Percent);
                }
            }
        }
    }


    /*
    重写进度条拖拽的方法
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
            isStartTracking = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {
        Intent intent = new Intent();
        intent.setAction(ACTION_PLAY_FROM_PRECENT);
        intent.putExtra(EXTRA_PERCENT, seekBar.getProgress() / 100F);
        sendBroadcast(intent);
        isStartTracking = false;
    }

    /**
     * 发起网络访问请求
     * @param name
     */
    private void getCloudMusic(String name){
        HttpUtil.getMusic(this, name, new HttpUtil.OnGetMusicListener() {
            @Override
            public void OnSuccess(CloudSongs cloudSongs) {
                csMusics = cloudSongs;
                listMusics = cloudSongs.getResult().getSongs();
                adapter = new SongAdapter(listMusics,MainActivity.this);
                lvSlidingMenu.setAdapter(adapter);
            }
        });
    }

    /**
     * 开始进行歌曲查找
     */
    private void searchMusic() {
        getCloudMusic(etSlidingMenu.getText().toString());
    }

    /*
    重写Activity的生命周期
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this,MyMusicService.class);
        stopService(intent);
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
