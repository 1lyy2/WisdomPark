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
import com.zykj.carfigure.MainActivity;
import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.IndexAdapter;
import com.zykj.carfigure.base.BaseFragment;
import com.zykj.carfigure.entity.IndexFragmentEntity;
import com.zykj.carfigure.eventbus.BindEventBus;
import com.zykj.carfigure.eventbus.Event;
import com.zykj.carfigure.eventbus.EventCode;
import com.zykj.carfigure.utils.MapUtil;
import com.zykj.carfigure.utils.ToastManager;
import com.zykj.carfigure.views.popup.MapSelectPopup;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@BindEventBus
public class IndexFragment extends BaseFragment implements OnItemClickListener, IndexAdapter.BannerOnItemClckListener, SwipeRefreshLayout.OnRefreshListener,
        IndexAdapter.IndexOnItemClickListener, IndexAdapter.IndexNearNavigationListener {

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
    private List<Object> list;
    private IndexAdapter indexAdapter;
    private boolean isLoading;
    private Handler handler = new Handler();
    private GridLayoutManager gridLayoutManager;
    //滑动的标志
    private int distance;
    private boolean visible = true;
    private MapSelectPopup mapSelectPopup;

    @Override
    protected void initView(View rootView) {
        createData();
        tvLocation.setText(MainActivity.cityName);
        indexAdapter = new IndexAdapter(getContext(), this, this, this, true, false);
        gridLayoutManager = new GridLayoutManager(getContext(), 4);
        recycleviewIndex.setLayoutManager(gridLayoutManager);
        indexAdapter.setList(list);
        recycleviewIndex.setAdapter(indexAdapter);
        listSwipeRefresh.setOnRefreshListener(this);
        loadMore();
        hideFABAnimation(fab);
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

    }

    //构造数据
    private void createData() {
        list = new ArrayList<>();
        IndexFragmentEntity.IndexBanner indexBanner = new IndexFragmentEntity.IndexBanner();
        indexBanner.setList(createBannerList());
        //添加banner
        list.add(indexBanner);
        //添加宫格
        list.addAll(createFuntion());
        //item头部
        list.add("附近优选");
        //附近优选列表
        list.addAll(createNear());

    }

    //构造的首页的banner
    private List<IndexFragmentEntity.IndexBanner.Banner> createBannerList() {
        List<IndexFragmentEntity.IndexBanner.Banner> list = new ArrayList<>();
        list.add(new IndexFragmentEntity.IndexBanner.Banner("http://i2.hdslb.com/bfs/archive/6729367567981003e90266972f491ff3588a1c94.jpg", "刘亦菲"));
        list.add(new IndexFragmentEntity.IndexBanner.Banner("http://img.zcool.cn/community/01b07d595c88e2a8012193a370edbc.jpg", "校园"));
        list.add(new IndexFragmentEntity.IndexBanner.Banner("http://imgsrc.baidu.com/imgad/pic/item/83025aafa40f4bfbfbba4380094f78f0f63618ff.jpg", "风景"));
        return list;
    }

    //构造宫格的列表数据
    private List<IndexFragmentEntity.Content> createFuntion() {
        List<IndexFragmentEntity.Content> list = new ArrayList<>();
        list.add(new IndexFragmentEntity.Content("我要停车"));
        list.add(new IndexFragmentEntity.Content("顺风车"));
        list.add(new IndexFragmentEntity.Content("预定车位"));
        list.add(new IndexFragmentEntity.Content("续时车位"));
        list.add(new IndexFragmentEntity.Content("代缴车费"));
        list.add(new IndexFragmentEntity.Content("快速充值"));
        list.add(new IndexFragmentEntity.Content("我的账单"));
        list.add(new IndexFragmentEntity.Content("停车资讯"));
        return list;

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

    @OnClick(R.id.fab)
    public void onViewClicked() {
        recycleviewIndex.smoothScrollToPosition(0);
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
        } else if (object instanceof IndexFragmentEntity.Content) {
            //宫格列表
            IndexFragmentEntity.Content content = (IndexFragmentEntity.Content) object;
            if (content == null) return;
            showToastMsgShort(content.getmTitle());
        }
    }

    /**
     * 附近优选导航
     *
     * @param object
     */
    @Override
    public void onNearNavigationListener(Object object) {
        showToastMsgShort("附近优选导航");
        MapUtil.showNavigation(getContext(), object, recycleviewIndex, mapSelectPopup, MapUtil.TYPE_NEAR);
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
}
