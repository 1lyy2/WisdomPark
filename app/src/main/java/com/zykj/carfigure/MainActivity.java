package com.zykj.carfigure;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.zykj.carfigure.adapter.BottomTabFragmentPagerAdapter;
import com.zykj.carfigure.base.BaseActivity;
import com.zykj.carfigure.eventbus.BindEventBus;
import com.zykj.carfigure.eventbus.Event;
import com.zykj.carfigure.eventbus.EventBusUtils;
import com.zykj.carfigure.eventbus.EventCode;
import com.zykj.carfigure.fragment.IndexFragment;
import com.zykj.carfigure.fragment.MeFragment;
import com.zykj.carfigure.fragment.NearFragment;
import com.zykj.carfigure.helper.requestpermissions.PermissionsManager;
import com.zykj.carfigure.helper.requestpermissions.PermissionsResultAction;
import com.zykj.carfigure.location.Location;
import com.zykj.carfigure.location.Utils;
import com.zykj.carfigure.log.Log;
import com.zykj.carfigure.mvp.presenter.UserLoginPresenter;
import com.zykj.carfigure.views.BottomTabView;
import com.zykj.carfigure.views.MyViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@BindEventBus
public class MainActivity extends BaseActivity implements AMapLocationListener {
    @BindView(R.id.myviewpager)
    public  MyViewPager          viewPager;
    @BindView(R.id.bottomTabView)
    public  BottomTabView        bottomTabView;
    private FragmentPagerAdapter adapter;
    private boolean              canExit;

    private AMapLocationClient       locationClient = null;
    private AMapLocationClientOption locationOption = null;
    public static  String cityName ="";
    private UserLoginPresenter userLoginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setDisableStatusBar(true);
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            canExit = savedInstanceState.getBoolean("canExit");
        }
        requestPermissions();
        adapter = new BottomTabFragmentPagerAdapter(getSupportFragmentManager(), getFragments());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        bottomTabView.setTabItemViews(getTabViews());
        bottomTabView.setUpWithViewPager(viewPager);
        //初始化定位
        initLocation();
        startLocation();
    }
    /**
     * 初始化定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = Location.getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(this);
    }

    //增加fragment
    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new IndexFragment());
        fragments.add(new NearFragment());
        fragments.add(new MeFragment());
        return fragments;
    }

    protected List<BottomTabView.TabItemView> getTabViews() {
        List<BottomTabView.TabItemView> tabItemViews = new ArrayList<>();
        tabItemViews.add(new BottomTabView.TabItemView(this, "首页", R.color.tab_unsel_color,
                R.color.tab_sel_color, R.mipmap.tab_home_page_un_selected, R.mipmap.tab_home_page_selected));
        tabItemViews.add(new BottomTabView.TabItemView(this, "附近", R.color.tab_unsel_color,
                R.color.tab_sel_color, R.mipmap.tab_circle_un_selected, R.mipmap.tab_circle_selected));
        tabItemViews.add(new BottomTabView.TabItemView(this, "我的", R.color.tab_unsel_color,
                R.color.tab_sel_color, R.mipmap.tab_my_un_selected, R.mipmap.tab_my_selected));
        return tabItemViews;
    }

    @Override
    public void onCreatePresenter() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected String getActivityName() {
        return getString(R.string.index);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_main;
    }

    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        if (canExit) {
            app.exit();
            super.onBackPressed();
        } else {
            showToastMsgShort(getString(R.string.str_double_click_exit));
            new AsyncTask<Integer, Integer, Integer>() {

                @Override
                protected Integer doInBackground(Integer... params) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    canExit = false;
                    return null;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    canExit = true;
                }

                @Override
                protected void onPostExecute(Integer result) {
                    super.onPostExecute(result);

                }

            }.execute(0);
        }
    }

    @Override
    public void onLocationChanged(AMapLocation location) {
        if (null != location) {

            StringBuffer sb = new StringBuffer();
            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if (location.getErrorCode() == 0) {
                sb.append("定位成功" + "\n");
                sb.append("定位类型: " + location.getLocationType() + "\n");
                sb.append("经    度    : " + location.getLongitude() + "\n");
                sb.append("纬    度    : " + location.getLatitude() + "\n");
                sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                sb.append("提供者    : " + location.getProvider() + "\n");

                sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                sb.append("角    度    : " + location.getBearing() + "\n");
                // 获取当前提供定位服务的卫星个数
                sb.append("星    数    : " + location.getSatellites() + "\n");
                sb.append("国    家    : " + location.getCountry() + "\n");
                sb.append("省            : " + location.getProvince() + "\n");
                sb.append("市            : " + location.getCity() + "\n");
                sb.append("城市编码 : " + location.getCityCode() + "\n");
                sb.append("区            : " + location.getDistrict() + "\n");
                sb.append("区域 码   : " + location.getAdCode() + "\n");
                sb.append("地    址    : " + location.getAddress() + "\n");
                sb.append("兴趣点    : " + location.getPoiName() + "\n");
                //定位完成的时间
                sb.append("定位时间: " + Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                Event<AMapLocation> event=new Event<>(EventCode.LOCATION,location);
                EventBusUtils.sendStickyEvent(event);
            } else {
                //定位失败
                sb.append("定位失败" + "\n");
                sb.append("错误码:" + location.getErrorCode() + "\n");
                sb.append("错误信息:" + location.getErrorInfo() + "\n");
                sb.append("错误描述:" + location.getLocationDetail() + "\n");
            }
            sb.append("***定位质量报告***").append("\n");
            sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启" : "关闭").append("\n");
            sb.append("* GPS状态：").append(Location.getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
            sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
            sb.append("* 网络类型：" + location.getLocationQualityReport().getNetworkType()).append("\n");
            sb.append("* 网络耗时：" + location.getLocationQualityReport().getNetUseTime()).append("\n");
            sb.append("****************").append("\n");
            //定位之后的回调时间
            sb.append("回调时间: " + Utils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");
            //解析定位结果，
            Log.i(TAG,sb.toString());
        } else {
            Event<String> event=new Event<>(0,"定位失败，loc is null");
           // EventBusUtils.sendEvent(event);
        }
    }

    /**
     * 开始定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void startLocation() {
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }
    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void destroyLocation(){
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }
    /**
     * 接受eventbus 适配器的click事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(Event<Object> event) {
        int code = event.getCode();
        switch (code) {
            case EventCode.REFRESH_LOCATION:
                startLocation();
                showToastMsgShort("刷新定位");
                break;
            default:
                break;
        }
    }

}
