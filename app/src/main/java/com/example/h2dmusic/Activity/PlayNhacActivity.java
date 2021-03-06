package com.example.h2dmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.h2dmusic.Adapter.ViewPagerDiaNhac;
import com.example.h2dmusic.Fragment.Fragment_dia_nhac;
import com.example.h2dmusic.Model.BaiHat;
import com.example.h2dmusic.R;
import com.example.h2dmusic.Service.APIService;
import com.example.h2dmusic.Service.Dataservice;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayNhacActivity extends AppCompatActivity {


//region     /*Khai báo biến*/

    private MediaPlayer mediaPlayer;
    androidx.appcompat.widget.Toolbar toolbarplaynhac;
    SeekBar seekBarnhac;
    ImageView imageViewtim;
    TextView textViewtennhac, textViewcasi, textViewrunrime, textViewtatoltime;
    ImageButton imageButtontronnhac, imageButtonpreviewnhac, imageButtonplaypausenhac, imageButtonnexnhac,
    imageButtonlapnhac, share;
    ViewPager viewPagerplaynhac;
    int dem = 0;
    int position = 0;
    boolean repeat = false;
    boolean checkrandom = false;
    boolean next = false;
    public static ArrayList<BaiHat> mangbaihat = new ArrayList<>();

    Fragment_dia_nhac fragment_dia_nhac;
    public static ViewPagerDiaNhac adapternhac;

    ShareDialog shareDialog;
    ShareLinkContent shareLinkContent;

    NotificationManager notificationManager;
    boolean isPlaying = false;
//endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);
        GetDataFromIntent();
        AnhXa();
        overridePendingTransition(R.anim.anim_intent_in, R.anim.anim_intent_out);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        enventClick();

//region        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            createChannel();
//            registerReceiver(broadcastReceiver,new IntentFilter("TRACKS_TRACKS"));
//            startService(new Intent(getBaseContext(), OnClearFromRecentServive.class));
//endregion       }

        imageViewtim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = mangbaihat.get(position).getIdBaiHat();
                imageViewtim.setImageResource(R.drawable.iconloved);
                Dataservice dataservice = APIService.getService();
                Call<String> callback = dataservice.Updateluotthich("1",id);
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String ketqua = response.body();
                        if(ketqua.equals("Success")){
                            Toast.makeText(PlayNhacActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(PlayNhacActivity.this, "Đã thích", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                    }
                });
            }
        });
//region Error
//       // Share fb
//        shareDialog = new ShareDialog(PlayNhacActivity.this);
//        share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(ShareDialog.canShow(ShareLinkContent.class)){
//                    shareLinkContent = new ShareLinkContent.Builder()
//                            .setContentTitle(mangbaihat.get(position).getTenBaiHat())
//                            .setContentDescription(mangbaihat.get(position).getTenCaSi())
//                            .setContentUrl(Uri.parse(mangbaihat.get(position).getLinkBaiHat()))
//                            .build();
//                }
//                shareDialog.show(shareLinkContent);
//            }
//        });
//endregion
    }
    //region Error
//
//    private void createChannel() {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            NotificationChannel channel = new NotificationChannel(CreateNotificationAdapter.CHANNEL_ID,
//                    "KOD Dev", NotificationManager.IMPORTANCE_LOW);
//
//            notificationManager = getSystemService(NotificationManager.class);
//            if(notificationManager != null){
//                notificationManager.createNotificationChannel(channel);
//            }
//        }
//    }
//
//    private void popluateBaihat(){
//        tracks = new ArrayList<>();
//
//        tracks.add(new Tracks("Track1","Artist1",R.drawable.ic_haha));
//        tracks.add(new Tracks("Track2","Artist2",R.drawable.ic_thuongthuong));
//        tracks.add(new Tracks("Track3","Artist3",R.drawable.ic_hoa));
//        tracks.add(new Tracks("Track4","Artist4",R.drawable.ic_wow));
//    }
//    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getExtras().getString("actionname");
//
//            switch (action){
//                case  CreateNotificationAdapter.ACTIONPREVIOUS:
//                    onTrackPrevious();
//                    break;
//                case CreateNotificationAdapter.ACTIONPLAY:
//                    if(isPlaying){
//                        onTrackPause();
//                    }
//                    else {
//                        onTrackPlay();
//                    }
//                    break;
//                case  CreateNotificationAdapter.ACTIONNEXT:
//                    onTrackNext();
//                    break;
//            }
//        }
//    };
//
//    @Override
//    public void onTrackPrevious() {
//        position--;
//        CreateNotificationAdapter.createNotification(PlayNhacActivity.this,tracks.get(position),R.drawable.iconpause,position,tracks.size() -1);
//    }
//
//    @Override
//    public void onTrackPause() {
//        CreateNotificationAdapter.createNotification(PlayNhacActivity.this,tracks.get(position),R.drawable.iconplay,position,tracks.size() -1);
//        isPlaying = false;
//    }
//
//    @Override
//    public void onTrackPlay() {
//        CreateNotificationAdapter.createNotification(PlayNhacActivity.this,tracks.get(position),R.drawable.iconpause,position,tracks.size() -1);
//        isPlaying = true;
//    }
//
//    @Override
//    public void onTrackNext() {
//        position++;
//        CreateNotificationAdapter.createNotification(PlayNhacActivity.this,tracks.get(position),R.drawable.iconpause,position,tracks.size() -1);
//    }
//
//    protected void onDestroy(){
//        super.onDestroy();
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            notificationManager.cancelAll();
//        }
//        unregisterReceiver(broadcastReceiver);
//    }
//endregion
    /*Vùng các nút điều khiển*/
//region control
    private void enventClick() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mangbaihat.size() > 0){
                    fragment_dia_nhac.PlayNhac(mangbaihat.get(position).getHinhBaiHat());
                    handler.removeCallbacks(this);
                }else {
                    handler.postDelayed(this, 300);
                }
            }
        }, 500);
        imageButtonplaypausenhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    isPlaying =false;
                    imageButtonplaypausenhac.setImageResource(R.drawable.nutpause);
                }else {
                    mediaPlayer.start();
                    isPlaying = true;
                    imageButtonplaypausenhac.setImageResource(R.drawable.nutplay);
                }
            }
        });
        imageButtonlapnhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (repeat == false){
                    if (checkrandom == true){
                        checkrandom = false;
                        imageButtonlapnhac.setImageResource(R.drawable.iconsyned);
                        imageButtontronnhac.setImageResource(R.drawable.iconsuffle);
                        repeat = true;
                    }else {
                        imageButtonlapnhac.setImageResource(R.drawable.iconsyned);
                        repeat = true;
                    }
                }else {
                    imageButtonlapnhac.setImageResource(R.drawable.iconrepeat);
                    repeat = false;
                }
            }
        });
        imageButtontronnhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkrandom == false){
                    if (repeat == true){
                        repeat = false;
                        imageButtontronnhac.setImageResource(R.drawable.iconshuffled);
                        imageButtonlapnhac.setImageResource(R.drawable.iconrepeat);
                        checkrandom = true;
                    }else {
                        imageButtontronnhac.setImageResource(R.drawable.iconshuffled);
                        checkrandom = true;
                    }
                }else {
                    imageButtontronnhac.setImageResource(R.drawable.iconsuffle);
                    checkrandom = false;
                }
            }
        });
        seekBarnhac.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });


        imageButtonnexnhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mangbaihat.size() > 0){
                    if (mediaPlayer.isPlaying() || mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < (mangbaihat.size())){
                        imageButtonplaypausenhac.setImageResource(R.drawable.nutplay);
                        position++;
                        if (repeat == true){
                            if (position == 0){
                                position = mangbaihat.size();
                            }
                            position -= 1;
                        }
                        if (checkrandom == true){
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if (index == position){
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > mangbaihat.size() - 1){
                            position = 0;
                        }
                        fragment_dia_nhac.PlayNhac(mangbaihat.get(position).getHinhBaiHat());
                        new playMP3().execute(mangbaihat.get(position).getLinkBaiHat());
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
                        UpdateTime();
                    }
                }
                imageButtonpreviewnhac.setClickable(false);
                imageButtonnexnhac.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageButtonpreviewnhac.setClickable(true);
                        imageButtonnexnhac.setClickable(true);
                    }
                }, 3000);
            }
        });
        imageButtonpreviewnhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mangbaihat.size() > 0) {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < (mangbaihat.size())) {
                        imageButtonplaypausenhac.setImageResource(R.drawable.nutplay);
                        position--;
                        if (position < 0) {
                            position = mangbaihat.size() - 1;
                        }
                        if (repeat == true) {
                            position += 1;
                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        fragment_dia_nhac.PlayNhac(mangbaihat.get(position).getHinhBaiHat());
                        new playMP3().execute(mangbaihat.get(position).getLinkBaiHat());
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
                        UpdateTime();
                    }
                }
                imageButtonpreviewnhac.setClickable(false);
                imageButtonnexnhac.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageButtonpreviewnhac.setClickable(true);
                        imageButtonnexnhac.setClickable(true);
                    }
                }, 3000);
            }
        });
    }
//endregion
    private void GetDataFromIntent() {
        Intent intent = getIntent();
        mangbaihat.clear();
        if (intent != null){
            if (intent.hasExtra("cakhuc")){
                BaiHat baiHat = intent.getParcelableExtra("cakhuc");
                mangbaihat.add(baiHat);
            }
            if (intent.hasExtra("cacbaihat")){
                ArrayList<BaiHat> baiHatArrayList = intent.getParcelableArrayListExtra("cacbaihat");
                mangbaihat = baiHatArrayList;

            }
        }
    }

    private void AnhXa() {
        //mVisualizer = findViewById(R.id.blob);
        toolbarplaynhac = findViewById(R.id.toolbarplaynhac);
        seekBarnhac = findViewById(R.id.seekBartime);
        viewPagerplaynhac = findViewById(R.id.viewPagerdianhac);
        imageViewtim = findViewById(R.id.imageViewtimplaynhac);
        imageButtontronnhac = findViewById(R.id.imageButtontron);
        imageButtonpreviewnhac = findViewById(R.id.imageButtonpreview);
        imageButtonplaypausenhac = findViewById(R.id.imageButtonplaypause);
        imageButtonnexnhac = findViewById(R.id.imageButtonnext);
        imageButtonlapnhac = findViewById(R.id.imageButtonlap);
        textViewtatoltime = findViewById(R.id.textViewtimetotal);
        textViewcasi = findViewById(R.id.textViewtencasiplaynhac);
        textViewtennhac = findViewById(R.id.textViewtenbaihatplaynhac);
        textViewrunrime = findViewById(R.id.textViewruntime);
        share = findViewById(R.id.imageBtnshare);

        fragment_dia_nhac = new Fragment_dia_nhac();
        adapternhac = new ViewPagerDiaNhac(getSupportFragmentManager());
        adapternhac.AddFragment(fragment_dia_nhac);
        viewPagerplaynhac.setAdapter(adapternhac);
        setSupportActionBar(toolbarplaynhac);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarplaynhac.setTitleTextColor(Color.BLACK);

        fragment_dia_nhac = (Fragment_dia_nhac) adapternhac.getItem(position);
        if (mangbaihat.size() > 0){
            getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
            new playMP3().onPostExecute(mangbaihat.get(position).getLinkBaiHat());
            imageButtonplaypausenhac.setImageResource(R.drawable.nutplay);
        }
        toolbarplaynhac.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mangbaihat.clear();
                finish();
            }
        });

    }

    class playMP3 extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.setDataSource(baihat);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            TimeSong();
            UpdateTime();
/*            try {
                mVisualizer.setAudioSessionId(mediaPlayer.getAudioSessionId());
            }catch (Exception e){

            }*/
        }
    }

    private void TimeSong(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        textViewtatoltime.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekBarnhac.setMax(mediaPlayer.getDuration());
        textViewtennhac.setText(mangbaihat.get(position).getTenBaiHat());
        textViewcasi.setText(mangbaihat.get(position).getTenCaSi());
    }

    private void UpdateTime(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null){
                    seekBarnhac.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    textViewrunrime.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 300);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            next = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }, 300);
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (next == true){
                    if (position < (mangbaihat.size())) {
                        //imageButtonplaypausenhac.setImageResource(R.drawable.nutpause);
                        position++;
                        if (repeat == true) {
                            position --;
                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > mangbaihat.size() - 1) {
                            position = 0;
                        }
                        try {
                            fragment_dia_nhac.PlayNhac(mangbaihat.get(position).getHinhBaiHat());
                            new playMP3().execute(mangbaihat.get(position).getLinkBaiHat());
                            getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    imageButtonpreviewnhac.setClickable(false);
                    imageButtonnexnhac.setClickable(false);
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageButtonpreviewnhac.setClickable(true);
                            imageButtonnexnhac.setClickable(true);
                        }
                    }, 3000);
                    next = false;
                    handler1.removeCallbacks(this);
                }else {
                    handler1.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }
}
