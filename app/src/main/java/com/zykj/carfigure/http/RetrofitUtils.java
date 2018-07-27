package com.zykj.carfigure.http;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitUtils {
    private RetrofitUtils() {
    }

    private static ApiService apiService;

    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (RetrofitUtils.class) {
                if (apiService == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(OkHttp3Utils.getInstance())
                            .baseUrl(ApiService.BASE_URl)
                            .build();
                    apiService = retrofit.create(ApiService.class);
                }
            }
        }

        return apiService;
    }

}
