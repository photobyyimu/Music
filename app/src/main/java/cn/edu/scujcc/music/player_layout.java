package cn.edu.scujcc.music;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class player_layout extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_cover;
    private static SeekBar sb;
    private Button back;
    private static TextView tv_progres,tv_total;
    private ImageView btn_play,btn_pause,btn_continue;
    private MusicService.MusicControl musicControl;//音乐服务控制器Binder实例
    private MyServiceConn myServiceConn;//连接实例
    private Intent intent;//全局的意图对象
    private  boolean isUnbind=false;//记录服务是否被解绑
    public ObjectAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_layout);
        initView();
    }
    private  void initView(){
      iv_cover=findViewById(R.id.iv);
      animator=ObjectAnimator.ofFloat(iv_cover,"rotation", 0f,360.0f);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);

        sb=findViewById(R.id.sb);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //滑动时的处理
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
             if(progress==seekBar.getMax()){   //滑动到最大值的时，结束动画
                 animator.pause();//停止动画播放
             }
            }
            //开始滑动时的处理
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            //停止滑动时的处理
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress=seekBar.getProgress();//获取seekbra的进度
                //调用服务的seekTo方法改进音乐的进度
            }
        });
        tv_progres=findViewById(R.id.tv_progress);
        tv_total=findViewById(R.id.tv_totla);
        btn_play=findViewById(R.id.btn_play);
        btn_pause=findViewById(R.id.btn_pause);
        btn_continue=findViewById(R.id.btn_continue);
        back=findViewById(R.id.back);

        intent =new Intent(this, MusicService.class);//打开服务的意图
        myServiceConn=new MyServiceConn();//实例化服务连接对象
        bindService(intent,myServiceConn,BIND_AUTO_CREATE);//绑定服务

        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_continue.setOnClickListener(this);
        back.setOnClickListener(this);
    }



    //创建消息处理的主线程对象
    public static Handler handler=new Handler(){
        //处理子线程传来的消息
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            Bundle bundle=msg.getData();
            int duration=bundle.getInt("duration");//总时长
            int currentDuration=bundle.getInt("currentDuration");//当前的播放时长
            sb.setMax(duration);//设置总长度
            sb.setProgress(currentDuration);
            //显示总时长开始
            int minute=duration / 1000 / 60;//分钟
            int second=duration / 1000 % 60;//秒
            String strMinute="";
            String strSecond="";
            if(minute<10){
                strMinute="0"+minute;
            }else{
                strMinute=minute+"";
            }
            if(second<10){
                strSecond="0"+second;
            }else{
                strSecond=second+"";
            }
            tv_total.setText(strMinute+":"+strSecond);
            //显示总时长结束

            //显示播放时长开始
            minute=currentDuration / 1000 / 60;//分钟
            second=currentDuration / 1000 % 60;//秒
            if(minute<10){
                strMinute="0"+minute;
            }else{
                strMinute=minute+"";
            }
            if(second<10){
                strSecond="0"+second;
            }else{
                strSecond=second+"";
            }
            tv_progres.setText(strMinute+":"+strSecond);
            //显示播放时长结束
        }
    };
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_play://播放
                musicControl.play();
                animator.start();
                break;
            case R.id.btn_pause://暂停
                musicControl.pausePlay();
                animator.pause();
                break;
            case R.id.btn_continue://继续
                musicControl.continuePlay();
                animator.start();
                break;
            case R.id.back://继续
                myUnbind(isUnbind);
                finish();
                break;
        }
    }
    //自定义解绑方法
    private  void myUnbind(boolean isUnbind){
        if(!isUnbind){
            isUnbind=true;
            musicControl.pausePlay();//暂停播放
            unbindService(myServiceConn);//解绑服务
            stopService(intent);//停止服务
        }

    }
class MyServiceConn implements ServiceConnection{


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        musicControl=(MusicService.MusicControl)service;

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
}