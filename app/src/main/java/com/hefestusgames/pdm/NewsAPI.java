package com.hefestusgames.pdm;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Felipe on 09/11/2016.
 */

public interface NewsAPI {
    @GET("/v1/articles?source=techcrunch&apiKey=cc3a8b23452d417db2dae359a11539d2")
    Call<NewsMaterias> carregarmaterias(@Query("tagged") String tags);
}
