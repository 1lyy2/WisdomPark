package com.zykj.carfigure.fragment;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.zykj.carfigure.MainActivity;
import com.zykj.carfigure.R;
import com.zykj.carfigure.activity.FindCarActivity;
import com.zykj.carfigure.activity.MeNeedParkingActivity;
import com.zykj.carfigure.activity.MyReserveActivity;
import com.zykj.carfigure.activity.RechargActivity;
import com.zykj.carfigure.activity.ReserveParkingActivity;
import com.zykj.carfigure.activity.TestZyCloudActivity;
import com.zykj.carfigure.activity.WaitPayFareActivity;
import com.zykj.carfigure.adapter.IndexAdapter;
import com.zykj.carfigure.app.Constants;
import com.zykj.carfigure.base.BaseFragment;
import com.zykj.carfigure.entity.Banner;
import com.zykj.carfigure.entity.Function;
import com.zykj.carfigure.entity.IndexFragmentEntity;
import com.zykj.carfigure.eventbus.BindEventBus;
import com.zykj.carfigure.eventbus.Event;
import com.zykj.carfigure.eventbus.EventBusUtils;
import com.zykj.carfigure.eventbus.EventCode;
import com.zykj.carfigure.mvp.presenter.IndexPresenter;
import com.zykj.carfigure.mvp.view.IIndexView;
import com.zykj.carfigure.utils.StatusBarUtil;
import com.zykj.carfigure.utils.ToastManager;
import com.zykj.carfigure.utils.Utils;
import com.zykj.carfigure.widget.ActionSheetDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@BindEventBus
public class IndexFragment extends BaseFragment<IndexPresenter> implements OnItemClickListener, IndexAdapter.BannerOnItemClckListener, SwipeRefreshLayout.OnRefreshListener,
        IndexAdapter.IndexOnItemClickListener, IndexAdapter.IndexNearNavigationListener, IIndexView {

    @BindView(R.id.recycleview_index)
    RecyclerView recycleviewIndex;
    @BindView(R.id.list_swipe_refresh)
    SwipeRefreshLayout listSwipeRefresh;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.lin_title)
    LinearLayout lin_title;
    @BindView(R.id.img_location)
    ImageView imgLocation;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.lin_location)
    RelativeLayout linLocation;
    @BindView(R.id.refresh_location)
    RelativeLayout refreshLocation;
    @BindView(R.id.tv_index_item_title)
    TextView tvIndexItemTitle;
    private List<Object> list;
    private IndexAdapter indexAdapter;
    private boolean isLoading;
    private Handler handler = new Handler();
    private GridLayoutManager gridLayoutManager;
    private View statusView;
    private IndexPresenter indexPresenter;
    private ActionSheetDialog actionSheetDialog;

    @Override
    protected void initView(View rootView) {
        createData();
        //设置沉浸式
        statusView = rootView.findViewById(R.id.fake_status_bar);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.height = StatusBarUtil.getStatusBarHeight(getActivity());
        statusView.setLayoutParams(lp);
        statusView.setBackgroundColor(((MainActivity) getActivity()).getStatusBarColor());

        tvLocation.setText(MainActivity.cityName);
        indexAdapter = new IndexAdapter(getContext(), this, this, this, true, false);
        gridLayoutManager = new GridLayoutManager(getContext(), 4);
        recycleviewIndex.setLayoutManager(gridLayoutManager);
        indexAdapter.setList(list);
        recycleviewIndex.setAdapter(indexAdapter);
        listSwipeRefresh.setOnRefreshListener(this);
        loadMore();
        hideFABAnimation(fab);
        indexPresenter.getBannerList();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_index;
    }

    @Override
    protected String getFragmentName() {
        return getString(R.string.fragment_index);
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    public void onCreatePresenter() {
        indexPresenter = new IndexPresenter(this);
    }

    //构造数据
    private void createData() {
        list = new ArrayList<>();
        IndexFragmentEntity.IndexBanner indexBanner = new IndexFragmentEntity.IndexBanner();
        String cacheBanner = Utils.readJson(Constants.CacheBanner);
        Gson gson = new Gson();
        //解析本地轮播图数据
        JsonReader jsonReader = new JsonReader(new StringReader(cacheBanner));//其中jsonContext为String类型的Json数据
        jsonReader.setLenient(true);
        Banner banner = gson.fromJson(jsonReader, Banner.class);
        if(banner!=null){
            indexBanner.setList(banner.getData());
            list.add(indexBanner);
        }
        //解析本地功能列表数据
        String cacheFunction = Utils.readJson(Constants.CacheFunction);
        JsonReader jsonReader_fun = new JsonReader(new StringReader(cacheFunction));//其中jsonContext为String类型的Json数据
        jsonReader_fun.setLenient(true);
        Function function = gson.fromJson(jsonReader_fun, Function.class);
        if(function!=null){
            list.addAll(function.getData());
            list.add("附近优选");
            //附近优选列表
            list.addAll(createNear());
        }

    }

    //构造附近优选
    private List<IndexFragmentEntity.Near> createNear() {
        List<IndexFragmentEntity.Near> list = new ArrayList<>();
        list.add(new IndexFragmentEntity.Near(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533529287493&di=1d16ad2c879c7da31fca292362eb6b18&imgtype=0&src=http%3A%2F%2Ff6.topitme.com%2F6%2F2f%2F94%2F1125509999019942f6o.jpg",
                "相思湖畔A区",
                "相思湖北路100米",
                1.5,
                "首两小时收费2元,后每小时10元"
        ));
        list.add(new IndexFragmentEntity.Near(
                "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1533524802&di=6d3b62009086861358fc80e068bb9ce8&src=http://pic9.photophoto.cn/20081128/0033033999061521_b.jpg",
                "广西民族大学",
                "大学东路188号广西民族大学内",
                1.8,
                "首两小时收费2元,后每小时5元"
        ));
        list.add(new IndexFragmentEntity.Near(
                "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1533524802&di=6d3b62009086861358fc80e068bb9ce8&src=http://pic9.photophoto.cn/20081128/0033033999061521_b.jpg",
                "广西民族大学",
                "大学东路188号广西民族大学内",
                1.8,
                "首两小时收费2元,后每小时5元"
        ));
        list.add(new IndexFragmentEntity.Near(
                "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1533524802&di=6d3b62009086861358fc80e068bb9ce8&src=http://pic9.photophoto.cn/20081128/0033033999061521_b.jpg",
                "广西民族大学",
                "大学东路188号广西民族大学内",
                1.8,
                "首两小时收费2元,后每小时5元"
        ));
        list.add(new IndexFragmentEntity.Near(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1533524854&di=501ee35acf3c71f32ad6db2e9bfb950b&src=http://pic23.nipic.com/20120919/538424_132906693180_2.jpg",
                "广西民族大学",
                "大学东路188号广西民族大学内",
                1.8,
                "首两小时收费2元,后每小时5元"
        ));
        list.add(new IndexFragmentEntity.Near(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533535140881&di=70f83674cabf6e32457a70980d0bafe1&imgtype=0&src=http%3A%2F%2Fpic29.photophoto.cn%2F20131204%2F0034034451023840_b.jpg",
                "广西民族大学",
                "大学东路188号广西民族大学内",
                1.8,
                "首两小时收费2元,后每小时5元"
        ));
        return list;
    }

    @Override
    public void onItemClick(int position) {
        ToastManager.showShortToast(getContext(), position + "");
    }

    @Override
    public void onBannerItemClckListener(int position) {
        showToastMsgShort("点击bannner" + position);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (indexPresenter != null) {
                    indexPresenter.getBannerList();
                }
                listSwipeRefresh.setRefreshing(false);
                showToastMsgShort("刷新完成");
            }
        }, 2000);
    }

    //滑动加载更多
    private void loadMore() {
        recycleviewIndex.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                    int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
                    int lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
                    if (lastVisibleItemPosition + 1 == indexAdapter.getItemCount()) {
                        if (!isLoading) {
                            isLoading = true;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //  .remove(mEntity.size() - 1);
                                    list.addAll(createNear());
                                    indexAdapter.setShowFooter(true);
                                    indexAdapter.setNoData(false);
                                    indexAdapter.notifyDataSetChanged();
                                    isLoading = false;

                                }
                            }, 3000);

                        }
                    }
                    if (firstVisibleItemPosition < list.size()) {
                        Object o = list.get(firstVisibleItemPosition);
                        if (o instanceof IndexFragmentEntity.Near) {
                            showFABAnimation(fab);
                            lin_title.setVisibility(View.VISIBLE);
                        } else {
                            hideFABAnimation(fab);
                            lin_title.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });
    }

    /**
     * by moos on 2017.8.21
     * func:显示fab动画
     */
    public void showFABAnimation(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(400).start();
    }

    /**
     * by moos on 2017.8.21
     * func:隐藏fab的动画
     */

    public void hideFABAnimation(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 0f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 0f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(400).start();
    }


    /**
     * item点击回调
     *
     * @param object
     */
    @Override
    public void onRVItemClickListener(Object object) {
        if (object == null) return;
        if (object instanceof IndexFragmentEntity.Near) {
            //附近优选
            IndexFragmentEntity.Near near = (IndexFragmentEntity.Near) object;
            if (near == null) return;
            showToastMsgShort(near.getParkName());
        } else if (object instanceof Function.DataBean) {
            //宫格列表
            Function.DataBean content = (Function.DataBean) object;
            if (content == null) return;
            int nv_title_type = content.getNv_title_type();
            switch (nv_title_type) {
                case 1:
                    //我要停车
                    launchActivity(MeNeedParkingActivity.class);
                    break;
                case 2:
                    //反向寻车
                    launchActivity(FindCarActivity.class);
                    break;
                case 3:
                    //预定车位
                    launchActivity(ReserveParkingActivity.class);
                    break;
                case 4:
                    //我的预约
                    launchActivity(MyReserveActivity.class);
                    break;
                case 5:
                    //抢车位
                    showToastMsgShort(getString(R.string.doing));
                    break;
                case 6:
                    //待缴车费
                    launchActivity(WaitPayFareActivity.class);
                    break;
                case 7:
                    //快速充值
                    launchActivity(RechargActivity.class);
                    break;
                case 8:
                    //停车资讯
                    launchActivity(TestZyCloudActivity.class);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 附近优选导航
     *
     * @param object
     */
    @Override
    public void onNearNavigationListener(Object object) {
        showToastMsgShort("附近优选");
        //MapUtil.showNavigation(getContext(), object, actionSheetDialog, MapUtil.TYPE_NEAR);
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
            case EventCode.LOCATION:
                AMapLocation location = (AMapLocation) event.getData();
                if (tvLocation == null) return;
                if (location == null) return;
                tvLocation.setText(location.getCity());
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.refresh_location, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.refresh_location:
                EventBusUtils.sendStickyEvent(new Event(EventCode.REFRESH_LOCATION, "刷新定位"));
                break;
            case R.id.fab:
                recycleviewIndex.smoothScrollToPosition(0);
                break;
        }
    }

    //获取轮播图
    @Override
    public void getBannerListSuccess(Banner banner) {
        if (banner == null) return;
        IndexFragmentEntity.IndexBanner indexBanner = new IndexFragmentEntity.IndexBanner();
        if (banner.getStatus() == 200) {
            list.clear();
            List<Banner.DataBean> data = banner.getData();
            indexBanner.setList(data);
            list.add(indexBanner);
            indexPresenter.getFunctionList();
            String s = new Gson().toJson(banner);
            Utils.writeJson(s, Constants.CacheBanner);
        } else {
            showToastMsgShort(banner.getMessage());
        }
    }

    @Override
    public void getBannerListFailed() {
        showToastMsgShort("获取广告图失败");
    }

    //获取功能列表
    @Override
    public void getFunctionListSuccess(Function function) {
        if (function == null) return;
        if (function.getStatus() == 200) {
            List<Function.DataBean> data = function.getData();
            if (data == null) {
                showToastMsgShort("获取数据失败");
            } else {
                List<Function.DataBean> dataBeans = addResources(data);
                list.addAll(dataBeans);
                list.add("附近优选");
                //附近优选列表
                list.addAll(createNear());
                refreshAdapter();
                String s = new Gson().toJson(function);
                Utils.writeJson(s, Constants.CacheFunction);

            }
        } else {
            showToastMsgShort("获取数据失败");
        }
    }

    @Override
    public void getFunctionListFailed() {
        showToastMsgShort("获取数据失败");
    }

    private void refreshAdapter() {
        if (indexAdapter != null) {
            indexAdapter.notifyDataSetChanged();
        }
    }

    //构造数据
    private List<Function.DataBean> addResources( List<Function.DataBean> data){
        if(data==null) return data;
        for(int i=0;i<data.size();i++){
            Function.DataBean dataBean = data.get(i);
            int nv_title_type = dataBean.getNv_title_type();
            if(nv_title_type==1){
                dataBean.setImageResources(R.drawable.tingche);
            }else if(nv_title_type==2){
                dataBean.setImageResources(R.drawable.zhaoche);
            }else if(nv_title_type==3){
                dataBean.setImageResources(R.drawable.chewei);
            }else if(nv_title_type==4){
                dataBean.setImageResources(R.drawable.yuding);
            }else if(nv_title_type==5){
                dataBean.setImageResources(R.drawable.qiang);
            }else if(nv_title_type==6){
                dataBean.setImageResources(R.drawable.jiaofei);
            }else if(nv_title_type==7){
                dataBean.setImageResources(R.drawable.chongzhi);
            }else if(nv_title_type==8){
                dataBean.setImageResources(R.drawable.zixun);
            }
        }
        return data;
    }
}
