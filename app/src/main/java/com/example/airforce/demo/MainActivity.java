package com.example.airforce.demo;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.widget.FrameLayout;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="MainActivity";
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.activity_main);
        VideoView videoView = new VideoView(this);

        initPlayer();
        videoView.setOnSurfaceCreatedListener(new VideoView.ISurfaceCreated() {
            @Override
            public void onSurfaceCreated(Surface surface) {

                mediaPlayer.setSurface(surface);
                try {
                    mediaPlayer.setDataSource("http://192.168.0.100/guanyu.mp4");

                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }) ;
        frameLayout.addView(videoView);

    }


    private void initPlayer()
    {
         mediaPlayer= new MediaPlayer();
         mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
             @Override
             public boolean onError(MediaPlayer mp, int what, int extra) {
                 Log.e(TAG,"what is "+what+"    "+extra);
                 return false;
             }
         });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.d(TAG,"onPrepared");
                mediaPlayer.start();
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });

    }
}
