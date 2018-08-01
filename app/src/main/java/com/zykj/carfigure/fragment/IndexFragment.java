package com.zykj.carfigure.fragment;

import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseFragment;
import com.zykj.carfigure.entity.Banner;
import com.zykj.carfigure.utils.ToastManager;
import com.zykj.carfigure.views.ImageHolderView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class IndexFragment extends BaseFragment implements OnItemClickListener {


    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;

    @Override
    protected void initView(View rootView) {
     initBanner();
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

    /**
     * 初始化轮播图
     */
    private void initBanner() {
        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        convenientBanner.setPages(
                new CBViewHolderCreator() {
                    @Override
                    public ImageHolderView createHolder(View itemView) {
                        return new ImageHolderView(itemView,getContext());
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.item_localimage;
                    }
                }, createBannerList())
                //设置自动切换（同时设置了切换时间间隔）
                .startTurning(2000)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                .setOnItemClickListener(this);
                //设置指示器的方向
//                .setOnPageChangeListener(this)//监听翻页事件
        ;
    }
    private List<Banner> createBannerList(){
        List<Banner> list = new ArrayList<>();
        list.add(new Banner("http://i2.hdslb.com/bfs/archive/6729367567981003e90266972f491ff3588a1c94.jpg","刘亦菲"));
        list.add(new Banner("http://img.zcool.cn/community/01b07d595c88e2a8012193a370edbc.jpg","校园"));
        list.add(new Banner("http://imgsrc.baidu.com/imgad/pic/item/83025aafa40f4bfbfbba4380094f78f0f63618ff.jpg","风景"));
        return list;
    }

    @Override
    public void onItemClick(int position) {
        ToastManager.showShortToast(getContext(),position+"");
    }
}
