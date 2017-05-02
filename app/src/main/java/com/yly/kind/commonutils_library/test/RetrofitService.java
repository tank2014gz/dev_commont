package com.yly.kind.commonutils_library.test;

import com.yly.kind.commonutils_library.modle.Book;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lielvwang on 2017/2/16.
 */

public interface RetrofitService {

    @GET("book/search")
    Call<Book> getSearchBook(@Query("q") String name, @Query("tag") String tag, @Query("start") int start, @Query("count") int count);
}
