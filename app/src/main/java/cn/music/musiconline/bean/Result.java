package cn.music.musiconline.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2016/7/5.
 */
public class Result implements Parcelable {

    private int songCount;
    private List<Song> songs;

    protected  Result(Parcel in)
    {
        songCount = in.readInt();
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel parcel) {
            return new Result(parcel);
        }

        @Override
        public Result[] newArray(int i) {
            return new Result[i];
        }
    };

    public int getSongCount(){
        return songCount;
    }

    public void setSongCount(int songCount){
        this.songCount = songCount;
    }

    public List<Song> getSongs(){
        return songs;
    }

    public void setSongs(List<Song> songs){
        this.songs = songs;
    }

    public static class Song {
        long id;
        String name;
        Artists artists;
        Album album;
        URL audio;
        int djProgramId;


        public Artists getArtists() {
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

        public void setArtists(Artists artists) {
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

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
         parcel.writeInt(songCount);
    }
}
