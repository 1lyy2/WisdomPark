package com.zykj.carfigure.location.marker;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.zykj.carfigure.R;
import com.zykj.carfigure.entity.Street;
import com.zykj.carfigure.log.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by my on 2016/12/19.
 */

public class MarkerOverlay implements AMap.OnMarkerClickListener {
    private AMap    aMap;
    private Context mContext;
    private ArrayList<Marker> mMarkers             = new ArrayList<Marker>();
    private HandlerThread     mMarkerHandlerThread = new HandlerThread("addMarker");
    private Handler                 mMarkerhandler;
    private OnMarkerOnClickListener mOnMarkerOnClickListener;
    private Marker                  lastClickMarker;
    private long lastClickTime = 0;
    private Street curUseRegcode;
    private Map<Integer, Integer> mBackDrawAbles = new HashMap<Integer, Integer>();
    private BitmapDescriptor bitmapDescriptor;
    private BitmapDescriptor bigBitmapDescriptor;

    public MarkerOverlay(AMap amap, List<Street> points, Context context) {
        this.aMap = amap;
        this.mContext = context;
        initThreadHandler();
        aMap.setOnMarkerClickListener(this);
        getBigBitmapDescriptor();
        getDefaultBitmapDescriptor();
        initPointList(points);


    }


    public void setOnMarkerOnClickListener(OnMarkerOnClickListener markerOnClickListener) {
        this.mOnMarkerOnClickListener = markerOnClickListener;
    }

    //初始化list
    private void initPointList(List<Street> points) {
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
    public void addToMap(List<Street> pointList) {
        try {
            for (int i = 0; i < pointList.size(); i++) {
                Street street = pointList.get(i);
                Marker marker = aMap.addMarker(new MarkerOptions()
                        .position(street.getmLatLng())
                        .icon( bitmapDescriptor));
           /*     Marker marker = aMap.addMarker(new MarkerOptions()
                        .position(pointList.get(i))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.qq_red)));*/
                street.setId(i);
                marker.setObject(street);
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
            Street useRegCode;
            if (obj != null) {
                useRegCode = (Street) obj;
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
                    List<Street> points = (List<Street>) message.obj;
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
        marker.getIcons().remove(marker.getOptions().getIcon());
        //获取原设置参数
        MarkerOptions options = marker.getOptions();
        //回收原图片
        //options.getIcon().recycle();
        if (showLarge) {
           // options.icon(bigBitmapDescriptor);
            marker.setIcon(bigBitmapDescriptor);
        } else {
            //options.icon(bitmapDescriptor);
            marker.setIcon(bitmapDescriptor);
        }
        //marker.setMarkerOptions(options);
        return marker;
    }

    /**
     * 点击地图
     */
    private void resetMarker() {
        try {
            Street lastUseRegcode=null ;
            if (lastClickMarker != null) {
                lastUseRegcode =(Street) lastClickMarker.getObject();
            }
            if (lastClickMarker != null && !curUseRegcode.equals(lastUseRegcode))
                changeMarkerImg(lastClickMarker, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到默认的
     * @return
     */

    private BitmapDescriptor getDefaultBitmapDescriptor() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_marker, null);
        ((ImageView) view.findViewById(R.id.marker_icon)).setImageResource(R.drawable.qq_red);
          bitmapDescriptor = BitmapDescriptorFactory.fromView(view);
        return bitmapDescriptor;
    }
    /**
     * 得到点击放大的
     * @return
     */

    private BitmapDescriptor getBigBitmapDescriptor() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_marker_select, null);
        ((ImageView) view.findViewById(R.id.marker_icon_select)).setImageResource(R.mipmap.ic_launcher_round);
        bigBitmapDescriptor = BitmapDescriptorFactory.fromView(view);
        return bigBitmapDescriptor;
    }
}
