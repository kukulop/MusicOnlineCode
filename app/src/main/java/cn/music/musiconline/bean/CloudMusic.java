/*
 * Copyright (C) 2004 - 2016 UCWeb Inc. All Rights Reserved.
 * Description : 已定义音乐播放器的Application，用于预先加载一些网络访问请求
 * line, otherwise ... ^_*
 *
 * Creation    : 2015-07-05
 * Author      : feng_wb@ucweb.com
 */
package cn.music.musiconline.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;

/**
 * Created by Administrator on 2016/7/5.
 */
public class CloudMusic {
    Result result;
    int error_code;

    public Result getResult() {
        return result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class Result{
        int songCount;
        Songs songs;

        public int getSongCount() {
            return songCount;
        }

        public Songs getSongs() {
            return songs;
        }

        public void setSongCount(int songCount) {
            this.songCount = songCount;
        }

        public void setSongs(Songs songs) {
            this.songs = songs;
        }

        public static class Songs{
            long id;
            String name;
            URL audio;
            int djProgramId;

            public long getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public URL getAudio() {
                return audio;
            }

            public int getDjProgramId() {
                return djProgramId;
            }

            public void setId(long id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setAudio(URL audio) {
                this.audio = audio;
            }

            public void setDjProgramId(int djProgramId) {
                this.djProgramId = djProgramId;
            }

            public static class artists{
                long id;
                String name;
                URL picUrl;

                public long getId()
                {
                    return id;
                }
                public void setId(long id)
                {
                    this.id = id;
                }
                public String getName()
                {
                    return name;
                }
                public void setName(String name)
                {
                    this.name = name;
                }
                public URL getPicUrl()
                {
                    return picUrl;
                }
                public void setPicUrl(URL picUrl)
                {
                    this.picUrl = picUrl;
                }

                @Override
                public String toString() {
                    return "artists{" +
                            "id=" + id +
                            ", name='" + name + '\'' +
                            ", picUrl=" + picUrl +
                            '}';
                }
            }

            public static class album{
                long id;
                String name;
                URL picUrl;

                public static class artist
                {
                    long id;
                    String name;
                    URL picUrl;

                    public long getId() {
                        return id;
                    }

                    public String getName() {
                        return name;
                    }

                    public URL getPicUrl() {
                        return picUrl;
                    }

                    public void setId(long id) {
                        this.id = id;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public void setPicUrl(URL picUrl) {
                        this.picUrl = picUrl;
                    }

                    @Override
                    public String toString() {
                        return "artist{" +
                                "id=" + id +
                                ", name='" + name + '\'' +
                                ", picUrl=" + picUrl +
                                '}';
                    }
                }

                public long getId() {
                    return id;
                }

                public String getName() {
                    return name;
                }

                public URL getPicUrl() {
                    return picUrl;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public void setPicUrl(URL picUrl) {
                    this.picUrl = picUrl;
                }
            }
        }
    }
}
