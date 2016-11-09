package com.hefestusgames.pdm;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by felipes on 11/9/2016.
 */

public interface IPAPI {
    @GET("/?format=json")
    Call<IP> carregarPerguntas(@Query("tagged") String tags);
}
