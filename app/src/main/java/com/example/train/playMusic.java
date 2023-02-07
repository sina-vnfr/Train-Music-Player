package com.example.train;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class playMusic extends AppCompatActivity {

    ImageView musicPic;
    ImageButton privous;
    ImageButton playStop;
    ImageButton next;
    TextView nowPos;
    TextView time;
    SeekBar seekbar;
    TextView musicName;
    ArrayList<File> songs;
    Thread update;
    int item;
    int ss=0;

    String name;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        privous=findViewById(R.id.privous);
        next=findViewById(R.id.next);
        musicPic=findViewById(R.id.musicPic);
        playStop=findViewById(R.id.playStop);
        nowPos=findViewById(R.id.nowPos);
        time=findViewById(R.id.time);
        seekbar=findViewById(R.id.seekbar);
        musicName=findViewById(R.id.musicName);

        if(mp!=null){
            mp.stop();
            mp.release();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = getIntent();
        Bundle b = i.getExtras();

        songs=(ArrayList) b.getParcelableArrayList("songs");
        name=  i.getStringExtra("songName");
        item=(int) b.getInt("item",0);
        Uri uri=Uri.parse(songs.get(item).toString());

        musicName.setText(name);

        mp=MediaPlayer.create(this,uri );

        String d=Integer.toString( mp.getDuration());
        time.setText(d);
        musicPicture(item);

        playStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mp.isPlaying() || ss==1){
                    playStop.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                    mp.start();
                    ss=0;
                } else {
                    playStop.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                    mp.pause();
                    ss=1;
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                mp.release();

                playStop.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                item=(item+1 <= songs.size()-1)? (item+1) : (item=0);
                Uri ur=Uri.parse(songs.get(item).toString());
                mp=MediaPlayer.create(getApplicationContext(),ur );

              //  time.setText(mp.getDuration());
                musicPicture(item);
                name=songs.get(item).getName().toString().replace(".mp3", "").replace(".wav", "");
                musicName.setText(name);
            }
        });

        privous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                mp.release();

                playStop.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                item=(item-1 < 0)? (item=songs.size()-1) : (item-1);
                Uri ur=Uri.parse(songs.get(item).toString());
                mp=MediaPlayer.create(getApplicationContext(),ur );

               // time.setText(mp.getDuration());
                musicPicture(item);
                name=songs.get(item).getName().toString().replace(".mp3", "").replace(".wav", "");
                musicName.setText(name);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
        mp.release();

    }

    public void musicPicture(int item){
        android.media.MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(songs.get(item).getPath());
        byte [] data = mmr.getEmbeddedPicture();
        if(data != null)
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            musicPic.setImageBitmap(bitmap); //associated cover art in bitmap
        }
        else
        {
            musicPic.setImageResource(R.drawable.ic_baseline_audiotrack_24); //any default cover resourse folder
        }
    }





}