package cn.edu.scujcc.music;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MusicApi {
    @GET("/music")
    Call<List<Music>> getAllMusics();
}
