package com.example.ducnguyen.demoandroidadvanceapp;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Objects;

public class MusicPlayerActivity extends AppCompatActivity implements View.OnClickListener {
    private MusicPlayerService mMusicPlayerService;
    private ImageView mImagePlayPause;
    private PlaybackStatusReceiver mPlaybackStatusReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        setUpView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MusicPlayerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        if (mPlaybackStatusReceiver == null) mPlaybackStatusReceiver = new PlaybackStatusReceiver();
        IntentFilter intentFilter = new IntentFilter(
                MusicPlayerService.ACTION_PLAYBACK_STATUS_CHANGED);
        registerReceiver(mPlaybackStatusReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mServiceConnection);
        if (mPlaybackStatusReceiver != null) unregisterReceiver(mPlaybackStatusReceiver);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_play_pause:
                mMusicPlayerService.controlPlayPauseMusic();
                break;
            case R.id.image_previous:
                mMusicPlayerService.controlPreviousSong();
                break;
            case R.id.image_next:
                mMusicPlayerService.controlNextSong();
                break;
            default:
                break;

        }

    }

    private void setUpView() {
        mImagePlayPause = findViewById(R.id.image_play_pause);
        mImagePlayPause.setOnClickListener(this);
        ImageView imagePrevious = findViewById(R.id.image_previous);
        imagePrevious.setOnClickListener(this);
        ImageView imageNext = findViewById(R.id.image_next);
        imageNext.setOnClickListener(this);
    }

    private void setUpPlayPauseImage(boolean isPlaying) {
        if (isPlaying) mImagePlayPause.setImageResource(R.drawable.ic_pause_music);
        else mImagePlayPause.setImageResource(R.drawable.ic_play_music);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicPlayerService.LocalBinder binder = (MusicPlayerService.LocalBinder) iBinder;
            mMusicPlayerService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    private class PlaybackStatusReceiver extends BroadcastReceiver {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(),
                    MusicPlayerService.ACTION_PLAYBACK_STATUS_CHANGED)) {
                setUpPlayPauseImage(intent.getBooleanExtra(MusicPlayerService.EXTRA_PLAYBACK_STATUS
                        , false));
            }
        }
    }
}
