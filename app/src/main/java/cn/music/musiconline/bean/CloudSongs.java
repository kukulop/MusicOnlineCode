/*
 * Copyright (C) 2004 - 2016 UCWeb Inc. All Rights Reserved.
 * Description : 这是一个音乐对象，用于给Gson进行解析
 * line, otherwise ... ^_*
 *
 * Creation    : 2015-07-05
 * Author      : feng_wb@ucweb.com
 */
package cn.music.musiconline.bean;

import java.net.URL;
import java.util.List;

public class CloudSongs {

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

    public static class Result {
        int songCount;
        List<Song> songs;

        public int getSongCount() {
            return songCount;
        }

        public List<Song> getSongs() {
            return songs;
        }

        public void setSongCount(int songCount) {
            this.songCount = songCount;
        }

        public void setSongs(List<Song> songs) {
            this.songs = songs;
        }

        public static class Song {
            long id;
            String name;
            List<Artists> artists;
            Album album;
            URL audio;
            int djProgramId;


            public List<Artists> getArtists() {
                return artists;
            }

            public Album getAlbum() {
                return album;
            }

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

            public void setArtists(List<Artists> artists) {
                this.artists = artists;
            }

            public void setAlbum(Album album) {
                this.album = album;
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

            public static class Artists{
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

            public static class Album{
                long id;
                String name;
                Artist artist;
                URL picUrl;

                public static class Artist
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

                public Artist getArtist() {
                    return artist;
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

                public void setArtist(Artist artist) {
                    this.artist = artist;
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


