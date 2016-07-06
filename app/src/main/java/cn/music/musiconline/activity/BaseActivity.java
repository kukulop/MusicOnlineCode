/**
 * 这是一个Activity基本类，用于将Actvity中一些有用的方法集中起来
 * @Author：Zalos
 * @DateTime:20160704
 * @Version:Ver0.1
 */
package cn.music.musiconline.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.music.musiconline.R;

/**
 * Created by Administrator on 2016/7/4.
 */
public class BaseActivity extends AppCompatActivity {
    Toast toast;
    enum Position {
        LEFT, CENTER, RIGHT
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 提示信息
     * @param text
     */
    public void toast(String text) {
        toast.setText(text);
        toast.show();
    }

    public void log(String log) {
        Log.d("TAG", this.getClass().getName() + "Êä³ö£º" + log);
    }

    public void toastAndLog(String text, String log) {
        toast(text);
        log(log);
    }


    /**
     * 修改标题中的文字，位置为正中间
     * @param headerView
     * @param text
     */
    public void setHeaderTitle(View headerView, String text) {
        TextView tv = (TextView) headerView.findViewById(R.id.tv_show);

        if (text == null) {
            tv.setText("");
        } else {
            tv.setText(text);
        }
        setHeaderTitle(headerView, text, Position.CENTER);
    }

    /**
     * 修改标题中的文字以及位置
     * @param headerView
     * @param text
     * @param pos
     */
    public void setHeaderTitle(View headerView, String text, Position pos) {
        TextView tv = (TextView) headerView.findViewById(R.id.tv_show);
        switch (pos) {
            case LEFT:
                tv.setGravity(Gravity.LEFT);
                break;
            case RIGHT:
                tv.setGravity(Gravity.RIGHT);
                break;
            case CENTER:
                tv.setGravity(Gravity.CENTER);
                break;
        }

        if (text == null) {
            tv.setText("");
        } else {
            tv.setText(text);
        }
    }

    /**
     * 用于修改标题中左图标与右图标，并设置监听器
     *
     * @param headerView
     * @param resId
     * @param pos
     * @param listener
     */
    public void setHeaderImage(View headerView, int resId, Position pos,
                               View.OnClickListener listener) {
        ImageView iv = null;
        switch (pos) {
            case LEFT:
                iv = (ImageView) headerView.findViewById(R.id.iv_left_icon);
                break;

            case CENTER:
            case RIGHT:
                iv = (ImageView) headerView.findViewById(R.id.iv_right_icon);
                break;

        }
        iv.setImageResource(resId);
        iv.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        if (listener != null) {
            iv.setOnClickListener(listener);
        }
    }
}
