/*
 * Copyright (C) 2004 - 2016 UCWeb Inc. All Rights Reserved.
 * Description : 已定义音乐播放器的Application，用于预先加载一些网络访问请求
 * line, otherwise ... ^_*
 *
 * Creation    : 2015-07-05
 * Author      : feng_wb@ucweb.com
 */
package cn.music.musiconline.bean;

import android.graphics.Interpolator;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/7/5.
 */
public class MusicResponse implements Parcelable {

    private Result result;
    private int code;
    protected  MusicResponse(Parcel in)
    {
        result = in.readParcelable(Result.class.getClassLoader());
        code = in.readInt();
    }

    public static final Creator<MusicResponse> CREATOR = new Creator<MusicResponse>() {
        @Override
        public MusicResponse createFromParcel(Parcel parcel) {
            return new MusicResponse(parcel);
        }

        @Override
        public MusicResponse[] newArray(int i) {
            return new MusicResponse[i];
        }
    };

    public Result getResult(){
        return result;
    }

    public void setResult(Result result){
        this.result = result;
    }

    public int getCode(){
        return code;
    }

    public void setCode(int code){
        this.code = code;
    }



    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(result,i);
        parcel.writeInt(code);
    }
}
