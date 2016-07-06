package cn.music.musiconline.dal;

import java.net.URL;


/**
 * MusicDao工厂
 */
public class MusicFactory {
    /**
     *
     * @return
     */
    public static IDao<URL> newInstance()
    {
        return new MusicDao();
    }
}
