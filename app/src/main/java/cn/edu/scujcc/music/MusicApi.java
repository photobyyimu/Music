package cn.edu.scujcc.music;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MusicApi {
    @GET("/music")
    Call<List<Music>> getAllMusics();

}
