/**
 * 这是一个工具静态类，用于将长整形的数据类型转化为字符串类型
 * @Author:Zalos
 * @DateTime:20160705
 * @Version:Ver0.1
 */
package cn.music.musiconline.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2016/7/5.
 */
public class CommonUtil {

    private static SimpleDateFormat spf = new SimpleDateFormat("mm:ss", Locale.CHINA);
    private static Date date  = new Date();

    public static String toSimpleDate(long millions)
    {
        date.setTime(millions);
        return spf.format(date);
    }

}
