package com.zykj.carfigure.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.SelectAddressAdapter;
import com.zykj.carfigure.app.Constants;
import com.zykj.carfigure.base.BaseActivity;
import com.zykj.carfigure.entity.AddressBean;
import com.zykj.carfigure.entity.Street;
import com.zykj.carfigure.eventbus.BindEventBus;
import com.zykj.carfigure.eventbus.Event;
import com.zykj.carfigure.eventbus.EventCode;
import com.zykj.carfigure.fragment.NearFragment;
import com.zykj.carfigure.location.marker.MarkerOverlay;
import com.zykj.carfigure.log.Log;
import com.zykj.carfigure.mvp.presenter.StreetPresenter;
import com.zykj.carfigure.mvp.view.IStreetView;
import com.zykj.carfigure.utils.ToastManager;
import com.zykj.carfigure.utils.Utils;
import com.zykj.carfigure.widget.ClearEditText;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//选择地址
@BindEventBus
public class SelectAddressActivity extends BaseActivity implements AMap.OnMyLocationChangeListener, AMap.OnCameraChangeListener,
        AMap.OnMapLoadedListener, MarkerOverlay.OnMarkerOnClickListener, IStreetView ,AMap.OnMapTouchListener{


    @BindView(R.id.selectMapView)
    MapView mMapView;
    @BindView(R.id.edit_search)
    ClearEditText editSearch;
    @BindView(R.id.recycleview_map_select)
    RecyclerView recyclerView;
    private SelectAddressAdapter selectAddressAdapter;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption clientOption;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private Marker locationMarker;
    private MarkerOverlay markerOverlay;
    public static int requestCode = 1020;
    //街道列表
    private List<Street.StreetDetail> strettList;
    private LatLng latLng;//中心点经纬度
    private StreetPresenter streetPresenter;
    private boolean followMove = true;

    @Override
    public void onCreatePresenter() {
        streetPresenter = new StreetPresenter(this);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_select_address;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
        editSearch.setFocusable(false);//让EditText失去焦点，然后获取点击事件
        init();
        initRecycleview();
    }

    @Override
    protected String getActivityName() {
        return "选择预定地址";
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationClient != null) {
            locationClient.stopLocation();
            locationClient.onDestroy();
        }
        //销毁资源
        if (markerOverlay != null) {
            markerOverlay.onDestroy();
        }
        locationClient = null;
        if (mMapView != null) {
            mMapView.onDestroy();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    private void init() {
        strettList = new ArrayList<>();
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        //设置是否显示定位小蓝点，true 显示，false不显示。
        myLocationStyle.showMyLocation(false);
        //可以根据自己的需要更改图标图片
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(Constants.getZoomB));
        //设置缩放控件在右边中间位置
        aMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM);
        aMap.setOnCameraChangeListener(this);
        aMap.setOnMapLoadedListener(this);
        aMap.setOnMapTouchListener(this);
        aMap.setMyLocationEnabled(true);
        aMap.setOnMyLocationChangeListener(this);
    }

    private void initRecycleview() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //1.RecyclerView实现广告轮播图 2.RecyclerView水平滑动整个item
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        selectAddressAdapter = new SelectAddressAdapter(this);
        recyclerView.setAdapter(selectAddressAdapter);
        selectAddressAdapter.setList(strettList);
    }
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        // addMarker();
        double latitude = cameraPosition.target.latitude;
        double longitude = cameraPosition.target.longitude;
        LatLng mlatLng = new LatLng(latitude, longitude);
        Log.i("地图选点纬度--------------------->", latitude + "");
        Log.i("地图选点经度--------------------->", longitude + "");
        if (latLng == null) {
            showLoadingView(null, null, false);
            this.latLng = mlatLng;
            streetPresenter.getStreetList(latitude, longitude, Constants.range);
        } else {
            //就可以实现，地图缩放的时候不作为，而在位移的时候却可以做你想做的请求，同样，监听位移事件也可以这样实现，例如，在两点之间相距多远的时候，才开始请求数据，同样可以用这样的方法来判断实现
            if (Constants.getZoomB != cameraPosition.zoom) {
                Constants.getZoomB = cameraPosition.zoom;

            } else {
                showLoadingView(null, null, false);
                this.latLng = mlatLng;
                streetPresenter.getStreetList(latitude, longitude, Constants.range);
            }


        }
    }

    @Override
    public void onMapLoaded() {

        addMarkerInScreenCenter();
    }

    @Override
    public void onMarkerCnClick(Marker marker) {
        Street.StreetDetail streetDetail = (Street.StreetDetail) marker.getObject();
        int number = streetDetail.getList_id();
        ToastManager.showShortToast(this, "marker序列号为：" + number);
        smoothMoveToPosition(recyclerView, number);

    }

    /**
     * 滑动到指定位置
     *
     * @param mRecyclerView
     * @param position
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        mRecyclerView.smoothScrollToPosition(position);
    }


    private void addMarkerInScreenCenter(LatLng latLng) {
        if (latLng == null) return;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        if (locationMarker == null) {
            locationMarker = aMap.addMarker(new MarkerOptions()
                    .anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_mark)));
            //设置Marker覆盖物的z轴值。
            locationMarker.setZIndex(1);
            locationMarker.setClickable(false);
        }
        //设置Marker在屏幕上,不跟随地图移动
        locationMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
    }
    private void addMarkerInScreenCenter() {
        LatLng latLng = aMap.getCameraPosition().target;
        if (latLng == null) return;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        if (locationMarker == null) {
            locationMarker = aMap.addMarker(new MarkerOptions()
                    .anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_mark)));
            //设置Marker覆盖物的z轴值。
            locationMarker.setZIndex(1);
            locationMarker.setClickable(false);
        }
        //设置Marker在屏幕上,不跟随地图移动
        locationMarker.setPositionByPixels(screenPosition.x, screenPosition.y);

    }

    private void addMarker(List<Street.StreetDetail> list) {
        if (markerOverlay != null) {
            markerOverlay.removeFromMap();
        }
        startJumpAnimation();

        /********************************************请求获取当前当前位置***************************************************************************************************/
       /* if(markerOverlay==null){
            markerOverlay = new MarkerOverlay(aMap, list, getContext());
        }else{
            markerOverlay.setPoints(list);
            markerOverlay.initPointList(list);
        }*/
        markerOverlay = new MarkerOverlay(aMap, list, getContext());
        markerOverlay.setCenterPoint(latLng);
        markerOverlay.setOnMarkerOnClickListener(this);
    }

    /**
     * 屏幕中心marker 跳动
     */
    public void startJumpAnimation() {
        if (locationMarker != null) {
            //根据屏幕距离计算需要移动的目标点
            final LatLng latLng = locationMarker.getPosition();
            Point point = aMap.getProjection().toScreenLocation(latLng);
            point.y -= Utils.dip2px(this, 125);
            LatLng target = aMap.getProjection()
                    .fromScreenLocation(point);
            //使用TranslateAnimation,填写一个需要移动的目标点
            Animation animation = new TranslateAnimation(target);
            animation.setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float input) {
                    // 模拟重加速度的interpolator
                    if (input <= 0.5) {
                        return (float) (0.5f - 2 * (0.5 - input) * (0.5 - input));
                    } else {
                        return (float) (0.5f - Math.sqrt((input - 0.5f) * (1.5f - input)));
                    }
                }
            });
            //整个移动所需要的时间
            animation.setDuration(600);
            //设置动画
            locationMarker.setAnimation(animation);
            //开始动画
            locationMarker.startAnimation();

        } else {
            Log.e("ama", "screenMarker is null");
        }
    }

    @OnClick(R.id.edit_search)
    public void onViewClicked() {
        followMove = false;
        Intent intent = new Intent();
        intent.setClass(this, AddressSearchActivity.class);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        if (resultCode == NearFragment.RESULT_CODE_INPUTTIPS) {
            AddressBean tip = (AddressBean) data.getSerializableExtra(Constants.EXTRA_TIP);
            if (tip == null) return;
            LatLng markerPosition = new LatLng(tip.getLatitude(), tip.getLongitude());
            addSearchMarker(markerPosition);
        }
    }

    private void addSearchMarker(LatLng latLng) {
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        addMarkerInScreenCenter();
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
            case EventCode.SELECTCARDETAIL:
                //详情
                // ToastManager.showShortToast(getContext(), "详情");
                break;
            case EventCode.SELECTCARPARK:
                //预约
                Street.StreetDetail streetDetail = (Street.StreetDetail) event.getData();
                if(streetDetail==null) return;
                Intent intent = new Intent(SelectAddressActivity.this,FreeParkActivity.class);
                intent.putExtra("streetId",streetDetail.getId());
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void getStreetListSuccess(Street street) {
        hideLoadingView();
        if (street == null) return;
        if (street.getStatus() == 200) {
            List<Street.StreetDetail> data = street.getData();
            selectAddressAdapter.setList(data);
            addMarker(data);
            if (markerOverlay != null) {
                //markerOverlay.zoomToSpan(data);
                markerOverlay.zoomToSpanWithCenter(data);
            }

        }
    }

    @Override
    public void getStreetListFailed() {
        hideLoadingView();
        showToastMsgShort("获取附近街道信息失败");
    }

    @Override
    public void onTouch(MotionEvent motionEvent) {
        if (followMove) {
            //用户拖动地图后，不再跟随移动，直到用户点击定位按钮
            followMove = false;
        }
    }

    @Override
    public void onMyLocationChange(Location location) {
        if (location == null) return;
        if (followMove) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            addMarkerInScreenCenter(latLng);
            aMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }
}
