package cn.music.musiconline.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import cn.music.musiconline.R;
import cn.music.musiconline.bean.CloudSongs;

/**
 * Created by Administrator on 2016/7/5.
 */
public class SongAdapter extends BasicAdapter<CloudSongs.Result.Song> implements SectionIndexer {

    private final int NOSUCHPOINT = -999;

    public SongAdapter(List<CloudSongs.Result.Song> lists, Context context) {
        super(lists, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        CloudSongs.Result.Song song = getLists().get(position);
        if(convertView==null) {
            convertView = getInflater().inflate(R.layout.layout_song,null);
            holder = new ViewHolder();
            holder.tvSongFirstLetter = (TextView) convertView.findViewById(R.id.song_tv_firstletter);
            holder.ivSongPic = (ImageView) convertView.findViewById(R.id.song_iv_header);
            holder.tvSongName = (TextView) convertView.findViewById(R.id.song_tv_songname);
            holder.tvSongName = (TextView) convertView.findViewById(R.id.song_tv_songartists);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvSongFirstLetter.setText(song.getAlbum().getName().toLowerCase(Locale.CHINA));
        holder.ivSongPic.setImageResource(R.mipmap.ic_launcher);
        holder.tvSongName.setText(song.getAlbum().getName());
        holder.tvSongArtists.setText(song.getArtists().get(position).getName());

        int section = getPositionForSection(position);
        int pos = getSectionForPosition(section);
        if(pos == position)
        {
            holder.tvSongFirstLetter.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.tvSongFirstLetter.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder{
        TextView tvSongFirstLetter;
        ImageView ivSongPic;
        TextView tvSongName;
        TextView tvSongArtists;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int position) {
        return getLists().get(position).getAlbum().getName().charAt(0);
    }

    @Override
    public int getSectionForPosition(int section) {
        for(int i = 0;i<getLists().size()-1;i++)
        {
            char ch = getLists().get(i).getAlbum().getName().charAt(0);
            if(ch==section)
            {
                return i;
            }
        }
        return NOSUCHPOINT;
    }
}
