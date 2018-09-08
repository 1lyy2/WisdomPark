package com.zykj.carfigure.http;

import com.zykj.carfigure.entity.Banner;
import com.zykj.carfigure.entity.CarMessage;
import com.zykj.carfigure.entity.CarPark;
import com.zykj.carfigure.entity.CommonBack;
import com.zykj.carfigure.entity.Function;
import com.zykj.carfigure.entity.NearCarPark;
import com.zykj.carfigure.entity.ParkingRecord;
import com.zykj.carfigure.entity.Register;
import com.zykj.carfigure.entity.ReservedParking;
import com.zykj.carfigure.entity.Street;
import com.zykj.carfigure.entity.User;
import com.zykj.carfigure.entity.VerificationCode;
import com.zykj.carfigure.entity.ZyCloudStatus;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

//ApiServisce 接口类
public interface ApiService {
    //测试服务器
    String BASE_URl = "http://39.107.66.96:8018/zy/";

    //韦金
    //String BASE_URl = "http://192.168.1.129:8018/zy/";

    //陆道斌
    //String BASE_URl = "http://192.168.1.103:8018/zy/";

    //初始化治业云
    @FormUrlEncoded
    @POST("zyCloud/initdev")
    Observable<CommonBack> initZyCloud(@Field("devId") String devId, @Field("initType") int status);

    //治业云控制(发送关灯，开灯)
    @FormUrlEncoded
    @POST("zyCloud/sendCommand")
    Observable<CommonBack> senCommand(@Field("devId") String devId, @Field("status") int status);

    //治业云车位状态
    @FormUrlEncoded
    @POST("zyCloud/querydev")
    Observable<ZyCloudStatus> getCarParkingStatus(@Field("devId") String devId);

    //获取验证码
    @FormUrlEncoded
    @POST("user/getCode")
    Observable<VerificationCode> getVerificationCode(@Field("username") String phone);

    //用户注册
    @FormUrlEncoded
    @POST("user/registerForm")
    Observable<Register> userRegister(@Field("username") String username, @Field("password") String password, @Field("code") String code);

    //用户登录
    @FormUrlEncoded
    @POST("user/loginForm")
    Observable<User> login(@Field("username") String username, @Field("password") String password);

    //重置密码
    @FormUrlEncoded
    @POST("user/resetPasswords")
    Observable<Register> resetPassword(@Field("username") String username, @Field("password") String password);

    //退出登录
    @POST("user/logOut")
    Observable<CommonBack> logOut();

    //用户

    //获取首页banner
    @POST("homeAdm/findByAllAd")
    Observable<Banner> getBannerList();

    //获取首页功能列表
    @POST("homeAdm/findByAllNv")
    Observable<Function> getFunctionList();

    //获取当前用户的车辆信息
    @FormUrlEncoded
    @POST("vehile/findByUserId")
    Observable<CarMessage> getUserCarList(@Field("user_id") int user_id);

    //增加车辆 --json当参数传递
    @Headers({"Content-Type: application/json;charset=UTF-8", "Accept: application/json"})//需要添加头
    @POST("vehile/createJson")
    Observable<CommonBack> addCar(@Body RequestBody requestBody);

    //删除车辆
    @FormUrlEncoded
    @POST("vehile/del")
    Observable<CommonBack> deleteCar(@Field("id") int carId);
    //更新车辆（传入json数据格式）
    /*@Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST ("vdyweb/ws/rest/Login")
    Call<ResponseBody> getMessage(@Body RequestBody info);   // 请求体味RequestBody 类型*/

    //获取当前位置附近街道的列表数据
    @FormUrlEncoded
    @POST("street/findDistance")
    Observable<Street> getStreetList(@Field("latitude") double latitude, @Field("longitude") double longitude, @Field("scopen") int scopen);

    //获取某个街道空闲车位的列表数据
    @FormUrlEncoded
    @POST("parking/findByWayIdFreePark")
    Observable<CarPark> getFreeCarParkList(@Field("id") int streetId, @Field("statu") int state);

    //获取某个街道所有车位的列表数据
    @FormUrlEncoded
    @POST("parking/findByWayIdParkNum")
    Observable<CarPark> getAllCarParkList(@Field("id") int streetId);

    //查询当前位置最近的车位
    @FormUrlEncoded
    @POST("parking/findParkAddress")
    Observable<NearCarPark> findNearCarPark(@Field("userLatitude") double userLatitude, @Field("userLongitude") double userLongitude);

    //我要停车
    @FormUrlEncoded
    @POST("parking/getStopCar")
    Observable<CommonBack> stopCarPark(@Field("user_id") int user_id, @Field("carnumber") String carnumber, @Field("licensePlateNumber") String licensePlateNumber);

    //停车记录
    @FormUrlEncoded
    @POST("parking/getParkingRecord")
    Observable<ParkingRecord> getParkingRecordList(@Field("user_id") int user_id);

    //预约车位
    @FormUrlEncoded
    @POST("carorder/OrderParking")
    Observable<CommonBack> reserveParking(@Field("user_id") int user_id, @Field("license_number") String licensePlateNumber,
                                          @Field("street_id") int street_id, @Field("order_carnumber") String parkNumber, @Field("order_time") String orderTime,
                                          @Field("username") String linkman, @Field("user_phone") String linkPhone, @Field("street_name") String streetName);

    //我的预约
    @FormUrlEncoded
    @POST("carorder/findByParkingOrderstatu")
    Observable<ReservedParking> getMyOrderParkingList(@Field("user_id") int user_id, @Field("order_statu") int order_statu);
}
