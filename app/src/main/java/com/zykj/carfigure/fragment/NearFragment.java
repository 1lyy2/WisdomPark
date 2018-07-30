package com.zykj.carfigure.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.EditText;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
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
import com.zykj.carfigure.activity.SearchActivity;
import com.zykj.carfigure.adapter.NearMapAdapter;
import com.zykj.carfigure.base.BaseFragment;
import com.zykj.carfigure.entity.Street;
import com.zykj.carfigure.eventbus.BindEventBus;
import com.zykj.carfigure.eventbus.Event;
import com.zykj.carfigure.eventbus.EventCode;
import com.zykj.carfigure.location.marker.MarkerOverlay;
import com.zykj.carfigure.log.Log;
import com.zykj.carfigure.utils.MapUtil;
import com.zykj.carfigure.utils.ToastManager;
import com.zykj.carfigure.utils.Utils;
import com.zykj.carfigure.views.NearMapItemDecoration;
import com.zykj.carfigure.views.popup.MapSelectPopup;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@BindEventBus
public class NearFragment extends BaseFragment implements LocationSource, AMapLocationListener, AMap.OnCameraChangeListener, AMap.OnMapLoadedListener, MarkerOverlay.OnMarkerOnClickListener {
    @BindView(R.id.mapview)
    public MapView mapView;
    @BindView(R.id.near_edit_search)
    public EditText search_edt;
    @BindView(R.id.recycleview_map)
    public RecyclerView mapRecyclerView;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption clientOption;
    private Marker locationMarker;
    private MarkerOverlay markerOverlay;
    private NearMapAdapter nearMapAdapter;
    private double test = 22.808286;
    private double test1 = 108.401713;
    private MapSelectPopup mapSelectPopup;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView(View rootView) {
        search_edt.setFocusable(false);//让EditText失去焦点，然后获取点击事件
        init();
        initAdapter();
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        //设置是否显示定位小蓝点，true 显示，false不显示。
        myLocationStyle.showMyLocation(false);
        //可以根据自己的需要更改图标图片
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        //设置缩放控件在右边中间位置
        aMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        aMap.setLocationSource(this);
        aMap.setOnCameraChangeListener(this);
        aMap.setOnMapLoadedListener(this);
        aMap.setMyLocationEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mapRecyclerView.setLayoutManager(linearLayoutManager);
        int leftRight = Utils.dip2px(getContext(), 6);
        int topBottom = Utils.dip2px(getContext(), 6);
        mapRecyclerView.addItemDecoration(new NearMapItemDecoration(leftRight, topBottom));
        nearMapAdapter = new NearMapAdapter(getContext());
        nearMapAdapter.setList(getStreetList());
        mapRecyclerView.setAdapter(nearMapAdapter);
        //1.RecyclerView实现广告轮播图 2.RecyclerView水平滑动整个item
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mapRecyclerView);
        /**
         * newState
         * 也就是说，newState=0时，RecyclerView在停止状态中
         newState=1和newState=2时，RecyclerView在滑动状态中
         不同的是,当由0—>1 ，2 时，RecyclerView由静止状态下变为滑动状态，然后1–0 滑动状态变为静止
         （调用方法 mHomeDateRecyclerView.smoothScrollToPosition(currentPostion); 等这一类方法所触发） ， 2–>0 滑动状态变为静止状态（左右滑动RecyclerView 动态的慢慢结束）
         */
        mapRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    if (newState == 0) {
                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                        //获取最后一个可见view的位置
                        int lastItemPosition = linearManager.findLastVisibleItemPosition();
                        //获取第一个可见view的位置
                        int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                        Log.i("RecyclerView", "firstItemPosition----------->" + firstItemPosition);
                        Log.i("RecyclerView", "lastItemPosition----------->" + lastItemPosition);
                        
                    }
                }
            }
        });
    }

    private List<Street> getStreetList() {
        List<Street> list = new ArrayList<>();
        list.add(new Street(new LatLng(22.833286, 108.200025), "相思湖学院", 100, 52, 520));
        list.add(new Street(new LatLng(22.808486, 108.401813), "桂雅路", 120, 12, 530));
        list.add(new Street(new LatLng(22.808386, 108.401913), "长岗路", 550, 82, 800));
        list.add(new Street(new LatLng(22.808296, 108.401783), "朝阳路", 80, 12, 200));
        return list;
    }

    private List<LatLng> getPointList() {
        List<LatLng> pointList = new ArrayList<LatLng>();
        pointList.add(new LatLng(22.808396, 108.401713));
        pointList.add(new LatLng(22.808486, 108.401813));
        pointList.add(new LatLng(22.808386, 108.401913));
        pointList.add(new LatLng(22.808296, 108.401783));
        pointList.add(new LatLng(test, test1));
        return pointList;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_near;
    }

    @Override
    protected String getFragmentName() {
        return getString(R.string.fragment_near);
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    public void onCreatePresenter() {

    }


    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mListener = null;
        if (locationClient != null) {
            locationClient.stopLocation();
            locationClient.onDestroy();
        }
        locationClient = null;
        //销毁资源
        markerOverlay.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }

    }

    @OnClick({R.id.near_search, R.id.near_edit_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.near_search:
                break;
            case R.id.near_edit_search:
                launchActivity(SearchActivity.class);
                break;
            default:
                break;
        }
    }

    //定位
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);
                LatLng curLatlng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatlng, 16f));
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (locationClient == null) {
            locationClient = new AMapLocationClient(getActivity().getApplicationContext());
            clientOption = new AMapLocationClientOption();
            //设置定位监听
            locationClient.setLocationListener(this);
            //设置为高精度定位模式
            clientOption.setOnceLocation(true);
            clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            locationClient.setLocationOption(clientOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            locationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {

    }

    private void addMarkerInScreenCenter(LatLng locationLatLng) {
        LatLng latLng = aMap.getCameraPosition().target;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        locationMarker = aMap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding)));
        //设置Marker在屏幕上,不跟随地图移动
        locationMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
        //设置Marker覆盖物的z轴值。
        locationMarker.setZIndex(1);
        locationMarker.setClickable(false);
    }

    /**
     * 屏幕中心marker 跳动
     */
    public void startJumpAnimation() {
        if (locationMarker != null) {
            //根据屏幕距离计算需要移动的目标点
            final LatLng latLng = locationMarker.getPosition();
            Point point = aMap.getProjection().toScreenLocation(latLng);
            point.y -= Utils.dip2px(getActivity(), 125);
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

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        // aMap.clear();
        //添加MarkerOnerlay
        if (markerOverlay != null) {
            markerOverlay.removeFromMap();
        }
        startJumpAnimation();
        markerOverlay = new MarkerOverlay(aMap, getStreetList(), getContext());
        markerOverlay.setOnMarkerOnClickListener(this);

    }

    //地图加载完成回调
    @Override
    public void onMapLoaded() {
        addMarkerInScreenCenter(null);
    }

    @Override
    public void onMarkerCnClick(Marker marker) {
        Street street = (Street) marker.getObject();
        int number = street.getId();
        ToastManager.showShortToast(getActivity(), "marker序列号为：" + number);
        smoothMoveToPosition(mapRecyclerView, number);

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
            case EventCode.NEARCARPARKDETAIL:
                //详情
                ToastManager.showShortToast(getContext(), "详情");
                break;
            case EventCode.ROUTE:
                //路线
                Street data = (Street) event.getData();
                goToBaidu(data.getmLatLng(), null);
                break;
            case EventCode.NAVIGATION:
                Street data1 = (Street) event.getData();
                Log.i("位置", "---------------------" + data1.toString());
               /* //导航
                if (mapSelectPopup == null) {
                    mapSelectPopup = new MapSelectPopup(getContext());
                }
                mapSelectPopup.setLatLng(latLng);
                mapSelectPopup.showAtLocation(mapRecyclerView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);*/
                goNavigationByGaode(data1);
                break;
            case EventCode.GAODEMAP:
                //高德地图导航
                //goNavigationByGaode();
                break;
            case EventCode.BAIDUMAP:
                //百度地图导航
                break;
            default:
                break;
        }
    }

    private void goNavigationByGaode(Street street) {
        if (street == null) return;
        LatLng latLng = street.getmLatLng();
        if (MapUtil.isGdMapInstalled()) {
            MapUtil.openGaoDeNavi(getContext(), latLng.latitude, latLng.longitude, 0, 2, 0);
        } else {
            ToastManager.showShortToast(getContext(), "您还未安装高德地图！");
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
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
    private void goToBaidu(LatLng mlatLng, String address) {
        if (mlatLng == null) return;
        if (MapUtil.isBaiduMapInstalled()) {
            //高德坐标转为百度地图坐标
            LatLng latLng = MapUtil.GCJ02ToBD09(mlatLng);
            StringBuffer stringBuffer = new StringBuffer("baidumap://map/navi?location=")
                    .append(latLng.latitude).append(",").append(latLng.longitude).append("&type=TIME");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuffer.toString()));
            intent.setPackage("com.baidu.BaiduMap");
            startActivity(intent);
        } else {
            ToastManager.showShortToast(getContext(), "您尚未安装百度地图！");
            Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }

}
