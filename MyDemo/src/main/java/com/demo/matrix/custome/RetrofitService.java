package com.demo.matrix.custome;

import com.demo.matrix.Node;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by seasonfif on 2017/5/21.
 */
public interface RetrofitService {

    @GET("nodes.json")
    Call<Node> getNodes();
}
