/*
 * Copyright (C) 2004 - 2016 UCWeb Inc. All Rights Reserved.
 * Description : 这是一个基本适配器，用于给其他适配器继承的
 * line, otherwise ... ^_*
 *
 * Creation    : 2015-07-05
 * Author      : feng_wb@ucweb.com
 */
package cn.music.musiconline.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;



/**
 * Created by Administrator on 2016/7/6.
 */
public abstract class BasicAdapter<T> extends BaseAdapter {

    private List<T> lists;
    private Context context;
    private LayoutInflater inflater;

    public BasicAdapter(List<T> lists, Context context) {
        this.lists = lists;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public List<T> getLists() {
        return lists;
    }

    public Context getContext() {
        return context;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setLists(List<T> lists) {
        if(lists == null)
        {
            lists = new ArrayList<T>();
        }
        this.lists = lists;
    }

    public void setContext(Context context) {
        if(context==null)
        {
            throw new IllegalArgumentException("上下文对象不能为空！！");
        }
        this.context = context;
    }

    public void setInflater(LayoutInflater inflater) {
        if(context==null)
        {
            throw new IllegalArgumentException("上下文对象不能为空！！");
        }
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
