package cn.edu.scujcc.music;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;
public class MusicService extends Service {
    private MediaPlayer player;
    private Timer timer;
    private Context context;
    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player=new MediaPlayer();
    }
    //添加计时器，用于设置音乐播放器中的播放进度条的信息
    public void addTimer(){
        if (timer==null){
            timer=new Timer();
            TimerTask task=new TimerTask(){
                @Override
                public void run(){
                    if(player==null) return;
                    int duration=player.getDuration();
                    //获取播放进度
                    int currentDuration=player.getCurrentPosition();
                    Message msg=player_layout.handler.obtainMessage();
                    //将音乐的总时长，播放时长封装到消息对象里
                    Bundle bundle=new Bundle();
                    bundle.putInt("duration",duration);
                    bundle.putInt("currentDuration",currentDuration);
                    msg.setData(bundle);
                    //将消息添加到主线程中
                    player_layout.handler.sendMessage(msg);
                }
            };
            //开始计时任务后5ms执行第一次任务，以后每500ms执行一次任务
            timer.schedule(task,  5, 500);

        }

    }
    class  MusicControl extends Binder {
        public void play(){
            try{
                player.reset();


                player=MediaPlayer.create(getApplicationContext(), R.raw.a1);//资源常量


                player.start();//开始播放音乐
                addTimer();//添加一个计时器

            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    public void pausePlay(){
            player.pause();//播放暂停
    }
    public void continuePlay(){
            player.start();//继续播放

    }
    public void seekTo(int progress){
            player.seekTo(progress);
    }
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return new MusicControl();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(player==null)return;
        if(player.isLooping())player.stop();//停止播放音乐
        player.release();//释放资源
        player=null;
    }
}
