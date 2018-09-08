package com.zykj.carfigure.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.amap.api.maps.model.LatLng;
import com.zykj.carfigure.R;
import com.zykj.carfigure.entity.CarPark;
import com.zykj.carfigure.entity.FindMyCar;
import com.zykj.carfigure.entity.IndexFragmentEntity;
import com.zykj.carfigure.entity.Street;
import com.zykj.carfigure.eventbus.Event;
import com.zykj.carfigure.eventbus.EventBusUtils;
import com.zykj.carfigure.widget.ActionSheetDialog;

import java.io.File;
import java.net.URISyntaxException;

public class MapUtil {
    public static String[] paks = new String[]{"com.baidu.BaiduMap",        //百度
            "com.autonavi.minimap"};     //高德
    public static final String PN_GAODE_MAP = "com.autonavi.minimap"; // 高德地图包名
    public static final String PN_BAIDU_MAP = "com.baidu.BaiduMap"; // 百度地图包名
    public static final String DOWNLOAD_GAODE_MAP = "http://www.autonavi.com/"; // 高德地图下载地址
    public static final String DOWNLOAD_BAIDU_MAP = "http://map.baidu.com/zt/client/index/"; // 百度地图下载地址
    public static final String TYPE_NEAR = "near";  //附近到导航
    public static final String TYPE_STREET = "street";//街道的导航
    public static final String TYPE_CARPARKING="carparking";//车位导航
    public static final String TYPE_FINDCAR = "findcar";//寻找车辆

    /**
     * 检查应用是否安装
     *
     * @return
     */
    public static boolean isGdMapInstalled() {
        return isInstallPackage(PN_GAODE_MAP);
    }

    public static boolean isBaiduMapInstalled() {
        return isInstallPackage(PN_BAIDU_MAP);
    }

    private static boolean isInstallPackage(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 百度坐标系 (BD-09) 与 火星坐标系 (GCJ-02)的转换
     * 即 百度 转 谷歌、高德
     *
     * @param latLng
     * @returns
     */
    public static LatLng BD09ToGCJ02(LatLng latLng) {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double x = latLng.longitude - 0.0065;
        double y = latLng.latitude - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double gg_lat = z * Math.sin(theta);
        double gg_lng = z * Math.cos(theta);
        return new LatLng(gg_lat, gg_lng);
    }

    /**
     * 驾车
     *
     * @param object
     * @param context
     * @param type
     */
    public static void goNavigationByGaode(Object object, Context context, String type) {
        LatLng mlatLng = null;
        String name = "目标位置";
        if (type.equals(TYPE_NEAR)) {
            IndexFragmentEntity.Near near = (IndexFragmentEntity.Near) object;
            mlatLng = near.getLatLng();
            name = near.getParkName();
        } else if (type.equals(TYPE_STREET)) {

            Street.StreetDetail street = (Street.StreetDetail) object;
            double latitude = street.getLatitude();
            double longitude = street.getLongitude();
            mlatLng = new LatLng(latitude,longitude);
            name = street.getStreetName();
        }else if(type.equals(TYPE_CARPARKING)){
            CarPark.CarParkDetail carport = (CarPark.CarParkDetail) object;
            double latitude = carport.getLatitude();
            double longitude = carport.getLongitude();
            mlatLng = new LatLng(latitude,longitude);
            name = carport.getStreetName();
        }else if(type.equals(TYPE_FINDCAR)){
            //反向寻车
            FindMyCar findMyCar = (FindMyCar) object;
            double latitude = findMyCar.getLatitude();
            double longitude = findMyCar.getLongitude();
            mlatLng = new LatLng(latitude,longitude);
        }
        if (name == null || name.equals("")) {
            name = "目标位置";
        }
        if (mlatLng == null) return;
        if (MapUtil.isGdMapInstalled()) {
            MapUtil.openGaoDeNavi(context, mlatLng.latitude, mlatLng.longitude, 0, 2, 0, name);
        } else {
            ToastManager.showShortToast(context, "您还未安装高德地图！");
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }

    /**
     * 打开百度地图导航客户端
     * intent = Intent.getIntent("baidumap://map/navi?location=34.264642646862,108.95108518068&type=BLK&src=thirdapp.navi.you
     * location 坐标点 location与query二者必须有一个，当有location时，忽略query
     * query    搜索key   同上
     * type 路线规划类型  BLK:躲避拥堵(自驾);TIME:最短时间(自驾);DIS:最短路程(自驾);FEE:少走高速(自驾);默认DIS
     */
    //百度
    public static void goToBaidu(Object object, Context context, String type) {
        LatLng mlatLng = null;
        if (type.equals(TYPE_NEAR)) {
            IndexFragmentEntity.Near near = (IndexFragmentEntity.Near) object;
            mlatLng = near.getLatLng();
        } else if (type.equals(TYPE_STREET)) {
            Street.StreetDetail street = (Street.StreetDetail) object;
            double latitude = street.getLatitude();
            double longitude = street.getLongitude();
            mlatLng = new LatLng(latitude,longitude);
        }else if(type.equals(TYPE_CARPARKING)){
            CarPark.CarParkDetail carport = (CarPark.CarParkDetail) object;
            double latitude = carport.getLatitude();
            double longitude = carport.getLongitude();
            mlatLng = new LatLng(latitude,longitude);
           // name = carport.getStreetName();
        }else if(type.equals(TYPE_FINDCAR)){
            //反向寻车
            FindMyCar findMyCar = (FindMyCar) object;
            double latitude = findMyCar.getLatitude();
            double longitude = findMyCar.getLongitude();
            mlatLng = new LatLng(latitude,longitude);
        }
        if (mlatLng == null) return;
        if (MapUtil.isBaiduMapInstalled()) {
            //高德坐标转为百度地图坐标
            LatLng latLng = MapUtil.GCJ02ToBD09(mlatLng);
            Intent intent = null;
            try {
                //intent = Intent.getIntent("intent://map/direction?origin=latlng:"+latLng.latitude+","+latLng.longitude+"|name:万家丽国际Mall&destination=latlng:"+LATITUDE_ZHONGDIAN+","+LONGTITUDE_ZHONGDIAN+"|name:东郡华城广场|A座&mode=driving&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                intent = Intent.getIntent("intent://map/direction?origin=我的位置&destination=latlng:" + latLng.latitude + "," + latLng.longitude + "|name:目标位置&mode=driving&src=" + context.getResources().getString(R.string.app_name) + "#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                context.startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            ToastManager.showShortToast(context, "您尚未安装百度地图！");
            Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }

    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换
     * 即谷歌、高德 转 百度
     *
     * @param latLng
     * @returns
     */
    public static LatLng GCJ02ToBD09(LatLng latLng) {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double z = Math.sqrt(latLng.longitude * latLng.longitude + latLng.latitude * latLng.latitude) + 0.00002 * Math.sin(latLng.latitude * x_pi);
        double theta = Math.atan2(latLng.latitude, latLng.longitude) + 0.000003 * Math.cos(latLng.longitude * x_pi);
        double bd_lat = z * Math.sin(theta) + 0.006;
        double bd_lng = z * Math.cos(theta) + 0.0065;
        return new LatLng(bd_lat, bd_lng);
    }

    /**
     * 打开高德地图导航功能(当前定位位置导航)
     *
     * @param context
     * @param lat     终点纬度
     * @param lon     终点经度
     * @param dev     是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * @param style   导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵)
     * @param t：t     = 0（驾车）= 1（公交）= 2（步行）= 3（骑行）= 4（火车）= 5（长途客车）
     */
    public static void openGaoDeNavi(Context context, double lat, double lon, int dev, int style, int t, String dname) {
        try {
            Intent intent = Intent.getIntent("androidamap://route?sourceApplication=" + context.getResources().getString(R.string.app_name) + "&sname=我的位置&dlat=" + lat + "&dlon=" + lon + "&dname=" + dname + "&dev=0&m=0&t=0");
            context.startActivity(intent);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param object            数据源
     * @param actionSheetDialog 弹出选择的框
     * @param type              类型
     */
    public static void showNavigation(Context context, final Object object, ActionSheetDialog actionSheetDialog, String type, final int gaoduCode, final int baiduCode) {
        if (object == null) return;
        if (MapUtil.isGdMapInstalled() && MapUtil.isBaiduMapInstalled()) {
            //双地图
            if (actionSheetDialog == null) {
                actionSheetDialog = new ActionSheetDialog(context);
            }
            actionSheetDialog.builder()
                    .setCancelable(false)
                    .setCanceledOnTouchOutside(true)
                    .addSheetItem("高德地图", ActionSheetDialog.SheetItemColor.Blue,
                            new ActionSheetDialog.OnSheetItemClickListener() {
                                @Override
                                public void onClick(int which) {
                                    EventBusUtils.sendEvent(new Event(gaoduCode, object));
                                }
                            })
                    .addSheetItem("百度地图", ActionSheetDialog.SheetItemColor.Blue,
                            new ActionSheetDialog.OnSheetItemClickListener() {
                                @Override
                                public void onClick(int which) {
                                    EventBusUtils.sendEvent(new Event(baiduCode, object));
                                }
                            })
                    .show();
        } else if (MapUtil.isBaiduMapInstalled() && !MapUtil.isGdMapInstalled()) {
            //百度地图
            MapUtil.goToBaidu(object, context, type);
        } else if (MapUtil.isGdMapInstalled() && !MapUtil.isBaiduMapInstalled()) {
            //高德地图
            MapUtil.goNavigationByGaode(object, context, type);
        } else {
            ToastManager.showShortToast(context, "您还未安装高德地图！");
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }


}
