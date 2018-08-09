package com.zykj.carfigure.http;

import com.zykj.carfigure.entity.Simple;

import io.reactivex.Observable;
import retrofit2.http.GET;

//ApiServisce 接口类
public interface ApiService {
    //http://www.wanandroid.com/banner/json
    //基类url
    //http://dict-mobile.iciba.com/interface/index.php?c=word&m=getsuggest&nums=10&client=6&is_need_mean=1&word=h
    public static String BASE_URl = "http://www.wanandroid.com/";

    //案例
   /*   @GET("banner/json")
    Observable<List<XBannerBean>> getData(@Query("type") String type);*//**/
    @GET("banner/json")
    Observable<Simple> getData();
    
}
