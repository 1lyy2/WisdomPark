package com.zykj.carfigure.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;

import com.amap.api.maps.model.LatLng;
import com.zykj.carfigure.R;
import com.zykj.carfigure.entity.IndexFragmentEntity;
import com.zykj.carfigure.entity.Street;
import com.zykj.carfigure.views.popup.MapSelectPopup;

import java.io.File;

public class MapUtil {
    public static String[] paks = new String[]{"com.baidu.BaiduMap",        //百度
            "com.autonavi.minimap"};     //高德
    public static final String PN_GAODE_MAP = "com.autonavi.minimap"; // 高德地图包名
    public static final String PN_BAIDU_MAP = "com.baidu.BaiduMap"; // 百度地图包名
    public static final String DOWNLOAD_GAODE_MAP = "http://www.autonavi.com/"; // 高德地图下载地址
    public static final String DOWNLOAD_BAIDU_MAP = "http://map.baidu.com/zt/client/index/"; // 百度地图下载地址
    public static final String TYPE_NEAR = "near";  //附近到导航
    public static final String TYPE_STREET = "street";//街道的导航

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


    public static void goNavigationByGaode(Object object, Context context,String type) {
        LatLng mlatLng = null;
        if (type.equals(TYPE_NEAR)) {
            IndexFragmentEntity.Near near = (IndexFragmentEntity.Near) object;
            mlatLng = near.getLatLng();
        } else if (type.equals(TYPE_STREET)) {
            Street street = (Street) object;
            mlatLng = street.getmLatLng();
        }
        if (mlatLng == null) return;
        if (MapUtil.isGdMapInstalled()) {
            MapUtil.openGaoDeNavi(context, mlatLng.latitude, mlatLng.longitude, 0, 2, 0);
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
            Street street = (Street) object;
            mlatLng = street.getmLatLng();
        }
        if (mlatLng == null) return;
        if (MapUtil.isBaiduMapInstalled()) {
            //高德坐标转为百度地图坐标
            LatLng latLng = MapUtil.GCJ02ToBD09(mlatLng);
            StringBuffer stringBuffer = new StringBuffer("baidumap://map/navi?location=")
                    .append(latLng.latitude).append(",").append(latLng.longitude).append("&type=TIME");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuffer.toString()));
            intent.setPackage("com.baidu.BaiduMap");
            context.startActivity(intent);
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
    public static void openGaoDeNavi(Context context, double lat, double lon, int dev, int style, int t) {
        String uriString = null;
        StringBuilder builder = new StringBuilder("androidamap://navi?sourceApplication=" + context.getResources().getString(R.string.app_name));
        builder.append("&lat=").append(lat)
                .append("&lon=").append(lon)
                .append("&dname=").append("我的位置")
                .append("&dev=").append(dev)
                .append("&t=").append(t)
                .append("&style=").append(style);

        uriString = builder.toString();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage(MapUtil.PN_GAODE_MAP);
        intent.setData(Uri.parse(uriString));
        context.startActivity(intent);
    }

    public static void openBaiDuNavi(Context context, double slat, double slon, String sname, double dlat, double dlon, String dname) {
        String uriString = null;
        //终点坐标转换
        LatLng destination = new LatLng(dlat, dlon);
        LatLng destinationLatLng = GCJ02ToBD09(destination);
        dlat = destinationLatLng.latitude;
        dlon = destinationLatLng.longitude;
        StringBuilder builder = new StringBuilder("baidumap://map/direction?mode=driving&");
        if (slat != 0) {
            //起点坐标转换
            LatLng origin = new LatLng(slat, slon);
            LatLng originLatLng = GCJ02ToBD09(origin);
            slat = originLatLng.latitude;
            slon = originLatLng.longitude;
            builder.append("origin=latlng:")
                    .append(slat)
                    .append(",")
                    .append(slon)
                    .append("|name:")
                    .append(sname);
        }
        builder.append("&destination=latlng:")
                .append(dlat)
                .append(",")
                .append(dlon)
                .append("|name:")
                .append(dname);
        uriString = builder.toString();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage(PN_BAIDU_MAP);
        intent.setData(Uri.parse(uriString));
        context.startActivity(intent);
    }

    /**
     *
     * @param context
     * @param object  数据源
     * @param view    绑定的view
     * @param mapSelectPopup  弹出选择的框
     * @param type    类型
     */
    public static void showNavigation(Context context, Object object, View view, MapSelectPopup mapSelectPopup,String type) {
        if (object == null) return;
        if (MapUtil.isGdMapInstalled() && MapUtil.isBaiduMapInstalled()) {
            //双地图
            if (mapSelectPopup == null) {
                mapSelectPopup = new MapSelectPopup(context);
            }
            mapSelectPopup.setObject(object);
            mapSelectPopup.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else if (MapUtil.isBaiduMapInstalled() && !MapUtil.isGdMapInstalled()) {
            //百度地图
            MapUtil.goToBaidu(object, context,type);
        } else if (MapUtil.isGdMapInstalled() && !MapUtil.isBaiduMapInstalled()) {
            //高德地图
            MapUtil.goNavigationByGaode(object, context,type);
        } else {
            ToastManager.showShortToast(context, "您还未安装高德地图！");
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }


}
