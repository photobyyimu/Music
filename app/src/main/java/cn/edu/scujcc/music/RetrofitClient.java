package cn.edu.scujcc.music;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitClient {
    private static Retrofit INSTANCE=null;
    public static Retrofit getInstance(){
        if(INSTANCE == null){
            INSTANCE=new Retrofit.Builder()
                    .baseUrl("http://47.112.229.78:8080")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build();
        }
        return INSTANCE;
    }
}
