/**
 * 这是广播中需要注册的具体广播意图类型以及传递的数据名称
 * @Author:Zalos
 * @DateTime：20160705
 * @Version:Ver0.1
 */
package cn.music.musiconline.util;

public interface Conts {

    /**
     * Activity发出的广播：播放或者暂停
     */
    String ACTION_PLAY_OR_PAUSE = "cn.music.musiconline.action.PLAY_OR_PAUSE";
    /**
     * Activity发出的广播：播放上一首
     */
    String ACTION_PREVIOUS = "cn.music.musiconline.action.PREVIOUS";
    /**
     * Activity发出的广播：播放下一首
     */
    String ACTION_NEXT = "cn.music.musiconline.action.NEXT";
    /**
     * Activity发出的广播：播放指定的歌曲
     */
    String ACTION_PLAY = "cn.music.musiconline.action.PLAY";
    /**
     *Activity发出的广播：进行拖拽操作
     */
    String ACTION_PLAY_FROM_PRECENT = "cn.music.musiconline.action.PLAY_FROM_PERCENT";





    String ACTION_SET_PLAY_STATE = "cn.music.musiconline.action.SET_PLAY_STATE";
    String ACTION_SET_PAUSE_STATE = "cn.music.musiconline.action.SET_PAUSE_STATE";

    String ACTION_RUN = "cn.music.musiconline.action.RUN";




    String EXTRA_CURRENTINDEX = "cn.music.musiconline.extra.CURRENTINDEX";
    /**
     * Extra：歌曲的总时长
     */
    String EXTRA_DURATION = "cn.music.musiconline.extra.DURATION";
    /**
     * Extra：歌曲当前秒数
     */
    String EXTRA_CURRENTION = "cn.music.musiconline.extra.CURRENTION";
    /**
     * Extra：歌曲当前进度
     */
    String EXTRA_PERCENT = "cn.music.musiconline.extra.PERCENT";
    /**
     * Extra:當前歌曲的Url
     */
    String EXTRA_MUSIC_URL = "cn.music.musiconline.extra.URL";



    /**
     *  Extra:歌曲更新后的进度
     */
    String ACTION_UPDATE_PROGRESS= "cn.music.musiconline.action.UPDATE_PROGRESS";


    /**
     * Action:歌曲长度缓冲进行中
     */
    String ACTION_SEEKBAR_PROGRESS = "cn.music.musiconline.action.SEEKBAR_PROGRESS_DURATION";
    /**
     * Extra:歌曲长度缓冲的进度值
     */
    String EXTRA_SEEKBAR_PROGRESS = "cn.music.musiconline.extra.SEEKBAR_PROGRESS_DURATION";

}
