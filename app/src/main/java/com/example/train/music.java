package com.example.train;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import java.io.File;
import java.util.ArrayList;

public class music extends AppCompatActivity {

    ListView l;
    TextView songName;
    ImageView imageSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        runtimeperimision();


    }

    public void runtimeperimision(){
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                        disply();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                        System.out.println("fdfddfdfdfdfdfdf");
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }


    public ArrayList<File> songs(File file){
        File[] fl=file.listFiles();
        ArrayList<File> a=new ArrayList<File>();

        for(File mufile : fl){
           if(mufile.isDirectory() && !mufile.isHidden() && mufile.canRead() && mufile.canExecute() ){
             a.addAll(songs(mufile)) ;
           }
           else {
               if (mufile.getName().endsWith(".mp3") || mufile.getName().endsWith(".wav")) {
                   a.add(mufile);
               }
           }
        }
        return a;
    }

    String[] name;
    public void disply(){
       final ArrayList<File> songslist = songs(Environment.getExternalStorageDirectory());

        l =  findViewById(R.id.listview);
        name=new String[songslist.size()];

        for( int q=0 ; q < songslist.size() ;q++){

            name[q]=songslist.get(q).getName().toString().replace(".mp3", "").replace(".wav", "");
        }

        customViewList adapter=new customViewList();
        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getBaseContext(),playMusic.class);
                intent.putExtra("item",position);
                intent.putExtra("songName",name[position]);
                intent.putExtra("songs",songslist);
                startActivity(intent);

            }
        });
    }

     class customViewList extends BaseAdapter {
        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = getLayoutInflater().inflate(R.layout.custom_view_list,null);
            songName = v.findViewById(R.id.songName);
            imageSongs = v.findViewById(R.id.imageSong);
            songName.setText(name[position]);
            songName.setSelected(true);

            return v;
        }
    }
}