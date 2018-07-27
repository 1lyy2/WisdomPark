package com.zykj.carfigure.http;

import retrofit2.http.GET;

//ApiServisce 接口类
public interface ApiService {
    //基类url
    //http://dict-mobile.iciba.com/interface/index.php?c=word&m=getsuggest&nums=10&client=6&is_need_mean=1&word=h
    public  static String BASE_URl = "http://dict-mobile.iciba.com/interface/index.php";
/*    @GET("http://dict-mobile.iciba.com/interface/index.php?c=word&m=getsuggest&nums=10&client=6&is_need_mean=1&word=h")
    Observable<List<ShouYeBean>> getShouData();*/



}
