package com.zykj.carfigure.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
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
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.zykj.carfigure.MainActivity;
import com.zykj.carfigure.R;
import com.zykj.carfigure.activity.AddressSearchActivity;
import com.zykj.carfigure.activity.StreetListActivity;
import com.zykj.carfigure.adapter.NearMapAdapter;
import com.zykj.carfigure.app.Constants;
import com.zykj.carfigure.base.BaseFragment;
import com.zykj.carfigure.entity.AddressBean;
import com.zykj.carfigure.entity.Street;
import com.zykj.carfigure.eventbus.BindEventBus;
import com.zykj.carfigure.eventbus.Event;
import com.zykj.carfigure.eventbus.EventCode;
import com.zykj.carfigure.location.marker.MarkerOverlay;
import com.zykj.carfigure.location.marker.PoiOverlay;
import com.zykj.carfigure.log.Log;
import com.zykj.carfigure.mvp.presenter.StreetPresenter;
import com.zykj.carfigure.mvp.view.IStreetView;
import com.zykj.carfigure.utils.MapUtil;
import com.zykj.carfigure.utils.StatusBarUtil;
import com.zykj.carfigure.utils.ToastManager;
import com.zykj.carfigure.utils.Utils;
import com.zykj.carfigure.widget.ActionSheetDialog;
import com.zykj.carfigure.widget.NearMapItemDecoration;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@BindEventBus
public class NearFragment extends BaseFragment implements AMap.OnMyLocationChangeListener, AMap.OnCameraChangeListener,
        AMap.OnMapLoadedListener, MarkerOverlay.OnMarkerOnClickListener, PoiSearch.OnPoiSearchListener, AMap.OnMapTouchListener, IStreetView {
    @BindView(R.id.mapview)
    public MapView mapView;
    @BindView(R.id.near_edit_search)
    public EditText search_edt;
    @BindView(R.id.recycleview_map)
    public RecyclerView mapRecyclerView;
    @BindView(R.id.show_parking_list)
    ImageView showParkingList;
    @BindView(R.id.near_search)
    LinearLayout nearSearch;
    @BindView(R.id.fake_status_bar)
    View statusView;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption clientOption;
    private Marker locationMarker;
    private MarkerOverlay markerOverlay;
    private NearMapAdapter nearMapAdapter;
    private ActionSheetDialog actionSheetDialog;
    //搜索部分
    private String mKeyWords = "";// 要输入的poi搜索关键字
    private ProgressDialog progDialog = null;// 搜索时进度条

    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 1;
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索

    public static final int REQUEST_CODE = 100;
    public static final int RESULT_CODE_INPUTTIPS = 101;
    public static final int RESULT_CODE_KEYWORDS = 102;
    private boolean followMove = true;
    private StreetPresenter streetPresenter;
    private LatLng latLng;//中心点经纬度

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView.onCreate(savedInstanceState);
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
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationClient != null) {
            locationClient.stopLocation();
            locationClient.onDestroy();
        }
        locationClient = null;
        //销毁资源
        if (markerOverlay != null) {
            markerOverlay.onDestroy();
        }

        if (mapView != null) {
            mapView.onDestroy();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView(View rootView) {
        search_edt.setFocusable(false);//让EditText失去焦点，然后获取点击事件
        initStatus();
        init();
        initAdapter();
    }

    private void initStatus() {
        statusView = rootView.findViewById(R.id.fake_status_bar);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.height = StatusBarUtil.getStatusBarHeight(getActivity());
        statusView.setLayoutParams(lp);
        statusView.setBackgroundColor(((MainActivity) getActivity()).getStatusBarColor());
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        //设置是否显示定位小蓝点，true 显示，false不显示。
        myLocationStyle.showMyLocation(false);
        myLocationStyle.interval(8000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        //可以根据自己的需要更改图标图片
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(Constants.getZoomB));
        //设置缩放控件在右边中间位置
        aMap.getUiSettings().setZoomControlsEnabled(false);
        // aMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        aMap.setOnCameraChangeListener(this);
        aMap.setOnMapLoadedListener(this);
        aMap.setMyLocationEnabled(true);
        aMap.setOnMyLocationChangeListener(this);
        aMap.setOnMapTouchListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mapRecyclerView.setLayoutManager(linearLayoutManager);
        int leftRight = Utils.dip2px(getContext(), 6);
        int topBottom = Utils.dip2px(getContext(), 6);
        mapRecyclerView.addItemDecoration(new NearMapItemDecoration(leftRight, topBottom));
        nearMapAdapter = new NearMapAdapter(getContext());
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
        streetPresenter = new StreetPresenter(this);
    }


    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @OnClick({R.id.near_edit_search, R.id.show_parking_list, R.id.near_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.show_parking_list:
                //展示当前地图所有街道的列表
                Intent intent_list = new Intent();
                double latitude = latLng.latitude;
                double longitude = latLng.longitude;
                intent_list.putExtra("street_latitude",latitude);
                intent_list.putExtra("street_longitude",longitude);
                intent_list.setClass(getActivity(),StreetListActivity.class);
                getActivity().startActivity(intent_list);
                break;
            case R.id.near_search:
                break;
            case R.id.near_edit_search:
                followMove = false;
                Intent intent = new Intent(getContext(), AddressSearchActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            default:
                break;
        }
    }

    /**
     * 输入提示activity选择结果后的处理逻辑
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CODE_INPUTTIPS && data
                != null) {
            AddressBean addressBean = (AddressBean) data.getSerializableExtra(Constants.EXTRA_TIP);
            LatLng markerPosition = new LatLng(addressBean.getLatitude(), addressBean.getLongitude());
            addSearchMarker(markerPosition);
            if(streetPresenter!=null){
                showLoadingView(null,null);
                streetPresenter.getStreetList(addressBean.getLatitude(),addressBean.getLongitude(),Constants.range);
            }
        }
    }

    @Override
    public void onMyLocationChange(Location location) {
        if (location == null) return;
        addMarkerInScreenCenter(latLng);
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

    private void addSearchMarker(LatLng latLng) {
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, Constants.getZoomB));
        addMarkerInScreenCenter();
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
        Log.i("nearFragmnet", "onCameraChange");
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        // aMap.clear();
        //添加MarkerOnerlay
        if(isPrepared){
            double latitude = cameraPosition.target.latitude;
            double longitude = cameraPosition.target.longitude;
            LatLng mlatLng = new LatLng(latitude, longitude);
            Log.i("地图选点纬度--------------------->", latitude + "");
            Log.i("地图选点经度--------------------->", longitude + "");
            if (latLng == null) {
                showLoadingView(null, null);
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
    }

    //地图加载完成回调
    @Override
    public void onMapLoaded() {
        addMarkerInScreenCenter();
    }

    @Override
    public void onMarkerCnClick(Marker marker) {
        Street.StreetDetail streetDetail = (Street.StreetDetail) marker.getObject();
        int number = streetDetail.getList_id();
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
                // ToastManager.showShortToast(getContext(), "详情");
                break;
            case EventCode.ROUTE:
                //预约
                Street data = (Street) event.getData();
                break;
            case EventCode.NAVIGATION:
                Street.StreetDetail street = (Street.StreetDetail) event.getData();
                Log.i("位置", "---------------------" + street.toString());
                MapUtil.showNavigation(getContext(), street, actionSheetDialog, MapUtil.TYPE_STREET,EventCode.GAODEMAP,EventCode.BAIDUMAP);
                break;
            case EventCode.GAODEMAP:
                //高德地图导航
                Street.StreetDetail mstreet = (Street.StreetDetail) event.getData();
                if (mstreet == null) return;
                MapUtil.goNavigationByGaode(mstreet, getContext(), MapUtil.TYPE_STREET);
                break;
            case EventCode.BAIDUMAP:
                //百度地图导航
                Street.StreetDetail baduStreet = (Street.StreetDetail) event.getData();
                if (baduStreet == null) return;
                MapUtil.goToBaidu(baduStreet, getContext(), MapUtil.TYPE_STREET);
                break;
            default:
                break;
        }
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(String keywords) {
        showProgressDialog();// 显示进度框
        currentPage = 1;
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query(keywords, "", Constants.DEFAULT_CITY);
        // 设置每页最多返回多少条poiitem
        query.setPageSize(10);
        // 设置查第一页
        query.setPageNum(currentPage);

        poiSearch = new PoiSearch(getContext(), query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(getContext());
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在搜索:\n" + mKeyWords);
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    /**
     * POI信息查询回调方法
     */
    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        dissmissProgressDialog();// 隐藏对话框
        if (rCode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if (poiItems != null && poiItems.size() > 0) {
                        aMap.clear();// 清理之前的图标
                        // addMarker();
                        PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    } else {
                        showToastMsgShort("没有匹配到相应的地点");
                    }
                }
            } else {
                showToastMsgShort("没有匹配到相应的地点");
            }
        } else {
            showToastMsgShort(rCode + "");
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                    + cities.get(i).getAdCode() + "\n";
        }
        showToastMsgShort(infomation);
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

    @Override
    public void getStreetListSuccess(Street street) {
        hideLoadingView();
        if (street == null) return;
        if (street.getStatus() == 200) {
            List<Street.StreetDetail> data = street.getData();
            nearMapAdapter.setList(data);
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
}
