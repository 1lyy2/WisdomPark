package com.zykj.carfigure.activity;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.zykj.carfigure.R;
import com.zykj.carfigure.base.UserBaseActivity;
import com.zykj.carfigure.entity.FindMyCar;
import com.zykj.carfigure.eventbus.BindEventBus;
import com.zykj.carfigure.eventbus.Event;
import com.zykj.carfigure.eventbus.EventCode;
import com.zykj.carfigure.utils.MapUtil;
import com.zykj.carfigure.widget.ActionSheetDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 反向寻车
 */
@BindEventBus
public class FindCarActivity extends UserBaseActivity implements AMap.OnMyLocationChangeListener, AMap.OnMapTouchListener {

    @BindView(R.id.map)
    MapView mMapView;
    MyLocationStyle myLocationStyle;
    private AMap aMap;
    private boolean followMove = true;
    private ActionSheetDialog actionSheetDialog;
    private LatLng latLng;

    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_find_car;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
        initMap();
        latLng = new LatLng(22.808409, 108.401883);
        addCarMarker(latLng);
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.find_car_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        if (mMapView != null) {
            mMapView.onCreate(savedInstanceState);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (mMapView != null) {
            mMapView.onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        if (mMapView != null) {
            mMapView.onResume();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        if (mMapView == null) return;
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        if (mMapView == null) return;
        mMapView.onSaveInstanceState(outState);
    }

    private void initMap() {
        if (mMapView == null) return;
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(8000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        //myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.setOnMyLocationChangeListener(this);
        aMap.setOnMapTouchListener(this);
    }

    @Override
    public void onMyLocationChange(Location location) {
        if (location == null) return;
        if (followMove) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            aMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    @Override
    public void onTouch(MotionEvent motionEvent) {
        if (followMove) {
            //用户拖动地图后，不再跟随移动，直到用户点击定位按钮
            followMove = false;
        }
    }

    private void addCarMarker(LatLng latLng) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.draggable(false);//设置Marker可拖动
        markerOption.title("我的车辆");
        markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.user_car));
        aMap.addMarker(markerOption);
    }

    @OnClick({R.id.image_route, R.id.rel_navigation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_route:
                showToastMsgShort("路线");
                break;
            case R.id.rel_navigation:
                showToastMsgShort("导航");
                FindMyCar findMyCar = new FindMyCar(22.809259, 108.401983, "大学东路", "A123456");
                MapUtil.showNavigation(this, findMyCar, actionSheetDialog, MapUtil.TYPE_FINDCAR,EventCode.FINCAR_GAODU,EventCode.FINDCAR_BAIDU);
                break;
        }
    }
    /**
     * 接受eventbus 适配器的click事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(Event<Object> event) {
        // do something
        int code = event.getCode();
        switch (code) {
            case EventCode.FINCAR_GAODU:
                //高德地图导航
                FindMyCar findMyCar = (FindMyCar) event.getData();
                if (findMyCar == null) return;
                MapUtil.goNavigationByGaode(findMyCar, getContext(), MapUtil.TYPE_FINDCAR);
                break;
            case EventCode.FINDCAR_BAIDU:
                //百度地图导航
                FindMyCar findMyCar_baidu = (FindMyCar) event.getData();
                if (findMyCar_baidu == null) return;
                MapUtil.goToBaidu(findMyCar_baidu, getContext(), MapUtil.TYPE_FINDCAR);
                break;
            default:
                break;
        }
    }

}
