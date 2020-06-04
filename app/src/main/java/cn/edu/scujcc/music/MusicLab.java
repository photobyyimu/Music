package cn.edu.scujcc.music;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MusicLab {
    //单例第1步
    private static MusicLab INSTANCE = null;
    private List<Music> data;
    //单例第2步
    private MusicLab() {
        //初始化空白列表
        data = new ArrayList<>();
    }

    //单例第3步
    public static MusicLab getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MusicLab();
        }
        return INSTANCE;
    }

    public int getSize() {
        return data.size();
    }


    public Music getMusic(int position) {
        return this.data.get(position);
    }

    public void getData(Handler handler) {
        //调用单例
        Retrofit retrofit = RetrofitClient.getInstance();

        MusicApi api = retrofit.create(MusicApi.class);
        Call<List<Music>> call = api.getAllMusics();
        //enqueue会自己生成子线程， 去执行后续代码
        call.enqueue(new Callback<List<Music>>() {
            @Override
            public void onResponse(Call<List<Music>> call,
                                   Response<List<Music>> response) {
                if (null != response && null != response.body()) {
                    Log.d("lala", "从服务器得到的数据是：");
                    Log.d("lala", response.body().toString());
                    data = response.body();
                    //发出通知
                    Message msg = new Message();
                    msg.what = 1;  //自己规定1代表从阿里云获取数据完毕
                    handler.sendMessage(msg);
                } else {
                    Log.w("lala", "response没有数据！");
                }
            }

            @Override
            public void onFailure(Call<List<Music>> call, Throwable t) {
                Log.e("lala", "访问网络失败！", t);
            }
        });
    }
}
