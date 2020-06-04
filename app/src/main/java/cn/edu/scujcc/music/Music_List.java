package cn.edu.scujcc.music;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.net.URLConnection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Music_List extends AppCompatActivity {
    private RecyclerView musicRv;
    private MusicRvAdapter rvAdapter;
    private MusicLab lab = MusicLab.getInstance();
    //线程通讯第1步，在主线程创建Handler
    private Handler handler = new Handler() {
        //按快捷键Ctrl o
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                rvAdapter.notifyDataSetChanged();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music__list);

        this.musicRv = findViewById(R.id.music_rv);
        //lambda简化
        //适应handler，把适配器改为实例变量
        rvAdapter = new MusicRvAdapter(Music_List.this, p -> {
            //跳转到新界面，使用意图Intent
            Intent intent = new Intent(Music_List.this, player_layout.class);
            //通过位置p得到当前频道music，传递用户选中的频道到下一个界面
            Music c = lab.getMusic(p);
            intent.putExtra("music", c);
            startActivity(intent);
        });

        this.musicRv.setAdapter(rvAdapter);
        this.musicRv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //把主线程的handler传递给子线程使用
        lab.getData(handler);
    }
}