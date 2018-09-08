package com.zykj.carfigure.location.marker;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.zykj.carfigure.R;
import com.zykj.carfigure.entity.Street;
import com.zykj.carfigure.log.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by my on 2016/12/19.
 */

public class MarkerOverlay implements AMap.OnMarkerClickListener {
    private AMap aMap;
    private Context mContext;
    private ArrayList<Marker> mMarkers = new ArrayList<Marker>();
    private HandlerThread mMarkerHandlerThread = new HandlerThread("addMarker");
    private Handler mMarkerhandler;
    private OnMarkerOnClickListener mOnMarkerOnClickListener;
    private Marker lastClickMarker;
    private Street.StreetDetail curUseRegcode;
    private BitmapDescriptor bitmapDescriptor;
    private BitmapDescriptor bigBitmapDescriptor;
    private LatLng centerPoint;
    private List<Street.StreetDetail> points;

    public MarkerOverlay(AMap amap, List<Street.StreetDetail> points, Context context) {
        this.aMap = amap;
        this.mContext = context;
        initThreadHandler();
        aMap.setOnMarkerClickListener(this);
        getBigBitmapDescriptor();
        getDefaultBitmapDescriptor();
        this.points = points;
        initPointList(points);


    }


    public void setOnMarkerOnClickListener(OnMarkerOnClickListener markerOnClickListener) {
        this.mOnMarkerOnClickListener = markerOnClickListener;
    }

    //初始化list
    public void initPointList(List<Street.StreetDetail> points) {
        Message message = Message.obtain();
        message.what = MarkerHandler.ADD_MARKER;
        message.obj = points;
        mMarkerhandler.sendMessage(message);

    }

    //初始化Handler
    private void initThreadHandler() {
        mMarkerHandlerThread.start();
        mMarkerhandler = new MarkerHandler(mMarkerHandlerThread.getLooper());
    }

    /**
     * 添加Marker到地图中。
     */
    public void addToMap(List<Street.StreetDetail> pointList) {
        try {
            for (int i = 0; i < pointList.size(); i++) {
                Street.StreetDetail streetDetail = pointList.get(i);
                Marker marker = null;
                //纬度
                double latitude = streetDetail.getLatitude();
                //经度
                double longitude = streetDetail.getLongitude();
                //构造一个经纬度
                LatLng latLng = new LatLng(latitude, longitude);
                if (streetDetail.getType() == 0) {
                    marker = aMap.addMarker(new MarkerOptions()
                            //设置覆盖物比例
                            .anchor(0.5f, 0.5f)
                            .position(latLng)
                            .icon(bitmapDescriptor));
                } else if (streetDetail.getType() == 1) {
                    marker = aMap.addMarker(new MarkerOptions()
                            .anchor(0.5f, 0.5f)
                            .position(latLng)
                            .icon(bitmapDescriptor));
                } else {
                    marker = aMap.addMarker(new MarkerOptions()
                            .anchor(0.5f, 0.5f)
                            .position(latLng)
                            .icon(bitmapDescriptor));
                }
                streetDetail.setList_id(i);

                marker.setObject(streetDetail);
                mMarkers.add(marker);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    /**
     * 添加Marker到地图中。
     */
    public void addSingMarkerToMap(LatLng latLng) {
        try {
            View view = LayoutInflater.from(mContext).inflate(R.layout.custom_marker_select, null);
            Marker marker = aMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromView(view)));
            mMarkers.add(marker);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void adddDefaultToMap(LatLng latLng) {
        try {
            View view = LayoutInflater.from(mContext).inflate(R.layout.custom_marker, null);
            Marker marker = aMap.addMarker(new MarkerOptions()
                    .anchor(0.5f, 0.5f)
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromView(view)));
            mMarkers.add(marker);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 去掉MarkerOverlay上所有的Marker。
     */
    public void removeFromMap() {
        for (Marker mark : mMarkers) {
            mark.remove();
        }
    }

    /**
     * 去掉MarkerOverlay上某个的Marker。
     *
     * @param marker
     */
    public void removeOneMarker(Marker marker) {
        mMarkers.remove(marker);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        try {
            Object obj = marker.getObject();
            Street.StreetDetail useRegCode;
            if (obj != null) {
                useRegCode = (Street.StreetDetail) obj;
                //更换图标
                Marker markerNew = changeMarkerImg(marker, true);
                curUseRegcode = useRegCode;
                //重置上次图标
                resetMarker();
                lastClickMarker = markerNew;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("MarkerOverlay", "出现错误，请刷新地图");
        }

        mOnMarkerOnClickListener.onMarkerCnClick(marker);
        return true;
    }

    /**
     * 添加一个Marker点
     *
     * @param latLng 经纬度
     */
    public void addPoint(LatLng latLng) {
        Message message = Message.obtain();
        message.what = MarkerHandler.ADD_SINGLE_MARKER;
        message.obj = latLng;
        mMarkerhandler.sendMessage(message);
    }

    /**
     * 恢复默认的布局
     *
     * @param latLng
     */
    public void addDefault(LatLng latLng) {
        Message message = Message.obtain();
        message.what = MarkerHandler.ADD_DEFAULT;
        message.obj = latLng;
        mMarkerhandler.sendMessage(message);
    }

    /**
     * 处理market添加，更新等操作
     */
    class MarkerHandler extends Handler {

        static final int ADD_DEFAULT = 0;

        static final int ADD_SINGLE_MARKER = 1;

        static final int UPDATE_SINGLE_CLUSTER = 2;

        static final int ADD_MARKER = 3;

        MarkerHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {

            switch (message.what) {
                case ADD_DEFAULT:
                    LatLng mlatLng = (LatLng) message.obj;
                    adddDefaultToMap(mlatLng);
                    break;
                case ADD_SINGLE_MARKER:
                    LatLng latLng = (LatLng) message.obj;
                    addSingMarkerToMap(latLng);
                    break;
                case UPDATE_SINGLE_CLUSTER:
                    break;
                case ADD_MARKER:
                    List<Street.StreetDetail> points = (List<Street.StreetDetail>) message.obj;
                    addToMap(points);
                    break;
            }
        }
    }

    public void onDestroy() {
        mMarkerhandler.removeCallbacksAndMessages(null);
        mMarkerHandlerThread.quit();
        for (Marker marker : mMarkers) {
            marker.remove();

        }
        mMarkers.clear();
        bigBitmapDescriptor.recycle();
        bitmapDescriptor.recycle();
    }

    public interface OnMarkerOnClickListener {
        void onMarkerCnClick(Marker marker);
    }

    private Marker changeMarkerImg(Marker marker, boolean showLarge) {
        //获取原设置参数
        MarkerOptions options = marker.getOptions();

        //回收原图片
        options.getIcon().recycle();
        options.getIcons().clear();
        int layoutId = showLarge ? R.layout.custom_marker_select : R.layout.custom_marker;
        View view = LayoutInflater.from(mContext).inflate(layoutId, null);
        if (showLarge) {
            ((ImageView) view.findViewById(R.id.iv_marker)).setBackgroundResource(R.drawable.street_car_big);
        } else {
            ((ImageView) view.findViewById(R.id.iv_marker)).setBackgroundResource(R.drawable.street_park);
        }
        options.icon(BitmapDescriptorFactory.fromView(view));
        marker.setMarkerOptions(options);
        return marker;
    }

    /**
     * 点击地图
     */
    private void resetMarker() {
        try {
            Street.StreetDetail lastUseRegcode = null;
            if (lastClickMarker != null) {
                lastUseRegcode = (Street.StreetDetail) lastClickMarker.getObject();
            }
            if (lastClickMarker != null && !curUseRegcode.equals(lastUseRegcode))
                changeMarkerImg(lastClickMarker, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到默认的
     *
     * @return
     */

    private BitmapDescriptor getDefaultBitmapDescriptor() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_marker, null);
        ((ImageView) view.findViewById(R.id.iv_marker)).setImageResource(R.drawable.street_park);
        bitmapDescriptor = BitmapDescriptorFactory.fromView(view);
        return bitmapDescriptor;
    }

    /**
     * 得到点击放大的
     *
     * @return
     */

    private BitmapDescriptor getBigBitmapDescriptor() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_marker_select, null);
        ((ImageView) view.findViewById(R.id.iv_marker)).setImageResource(R.mipmap.ic_launcher_round);
        bigBitmapDescriptor = BitmapDescriptorFactory.fromView(view);
        return bigBitmapDescriptor;
    }

    /**
     * 得到点击放大的
     *
     * @return
     */

    private BitmapDescriptor getBigBitmapDescriptor1() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_marker_select, null);
        ((ImageView) view.findViewById(R.id.iv_marker)).setImageResource(R.drawable.weibo);
        bigBitmapDescriptor = BitmapDescriptorFactory.fromView(view);
        return bigBitmapDescriptor;
    }

    /**
     * 缩放移动地图，保证所有自定义marker在可视范围中，且地图中心点不变。
     */

    public void zoomToSpanWithCenter(List<Street.StreetDetail> pointList) {

        if (pointList != null && pointList.size() > 0) {

            if (aMap == null)

                return;
            LatLngBounds bounds = getLatLngBounds(getCenterPoint(), pointList);

            aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));

        }

    }


    //根据中心点和自定义内容获取缩放bounds

    private LatLngBounds getLatLngBounds(LatLng centerpoint, List<Street.StreetDetail> pointList) {

        LatLngBounds.Builder b = LatLngBounds.builder();

        if (centerpoint != null) {

            for (int i = 0; i < pointList.size(); i++) {
                Street.StreetDetail streetDetail = pointList.get(i);
                double latitude = streetDetail.getLatitude();
                double longitude = streetDetail.getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);
                LatLng p1 = new LatLng((centerpoint.latitude * 2) - latLng.latitude, (centerpoint.longitude * 2) - latLng.longitude);
                b.include(latLng);
                b.include(p1);
            }

        }

        return b.build();

    }


    /**
     * 缩放移动地图，保证所有自定义marker在可视范围中。
     */

    public void zoomToSpan(List<Street.StreetDetail> pointList) {

        if (pointList != null && pointList.size() > 0) {

            if (aMap == null)

                return;
            LatLngBounds bounds = getLatLngBounds(pointList);

            aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));

        }

    }

    /**
     * 根据自定义内容获取缩放bounds
     */

    private LatLngBounds getLatLngBounds(List<Street.StreetDetail> pointList) {

        LatLngBounds.Builder b = LatLngBounds.builder();

        for (int i = 0; i < pointList.size(); i++) {
            Street.StreetDetail streetDetail = pointList.get(i);
            double latitude = streetDetail.getLatitude();
            double longitude = streetDetail.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            b.include(latLng);
        }

        return b.build();

    }

    public LatLng getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(LatLng centerPoint) {
        this.centerPoint = centerPoint;
    }

    public List<Street.StreetDetail> getPoints() {
        return points;
    }

    public void setPoints(List<Street.StreetDetail> points) {
        this.points = points;
    }
}
