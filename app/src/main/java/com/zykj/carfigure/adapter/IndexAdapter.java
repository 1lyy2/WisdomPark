package com.zykj.carfigure.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;
import com.zykj.carfigure.entity.IndexFragmentEntity;
import com.zykj.carfigure.utils.GlideUtil;
import com.zykj.carfigure.views.ImageHolderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/217:44
 * desc   :
 * version: 1.0
 */
public class IndexAdapter extends BaseRecylerAdapter<Object> {
    //头部标记
    private static final int TYPE_BANNER = 0;
    //宫格布局
    private static final int TYPE_FUNTION = 1;
    //Item 头部
    private static final int TYPE_TITLE = 2;
    //附近停车位优选
    private static final int TYPE_NEAR = 3;
    //底部
    private static final int TYPE_FOOTER = 4;

    private Context mContext;
    private BannerOnItemClckListener bannerOnItemClckListener;
    private boolean isShowFooter;
    private boolean isNoData;
    private IndexOnItemClickListener onItemClickListener;
    private IndexNearNavigationListener nearNavigationListener;



    public IndexAdapter(Context context, BannerOnItemClckListener onItemClckListener,IndexOnItemClickListener onItemClickListener,IndexNearNavigationListener nearNavigationListener, boolean isShowFooter, boolean isNoData) {
        super(context);
        this.mContext = context;
        this.isShowFooter = isShowFooter;
        this.isNoData = isNoData;
        this.bannerOnItemClckListener = onItemClckListener;
        this.onItemClickListener = onItemClickListener;
        this.nearNavigationListener =nearNavigationListener;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = (GridLayoutManager) layoutManager;
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case TYPE_BANNER:
                            return 4; // 宽度为4， item满屏
                        case TYPE_FUNTION:
                            return 1;// 宽度为6， item满屏
                        case TYPE_TITLE:
                            return 4;// 宽度为4，item满屏
                        case TYPE_NEAR:
                            return 4;// 宽度为4，item满屏
                        case TYPE_FOOTER:
                            return 4;
                        default:
                            return -1;
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mList.size()) {
            Object obj = mList.get(position);
            if (obj instanceof IndexFragmentEntity.IndexBanner) {
                return TYPE_BANNER;
            } else if (obj instanceof IndexFragmentEntity.Content) {
                return TYPE_FUNTION;
            } else if (obj instanceof String) {
                return TYPE_TITLE;
            } else if (obj instanceof IndexFragmentEntity.Near) {
                return TYPE_NEAR;
            }
        } else if ((isShowFooter && position + 1 == getItemCount())) {
            return TYPE_FOOTER;
        }

        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_BANNER:
                //广告
                holder = new BannerHolder(inflate(parent, R.layout.index_item_banner));
                break;
            case TYPE_FUNTION:
                //宫格列表
                holder = new FuntionHolder(inflate(parent, R.layout.index_item_funtion));
                break;
            case TYPE_TITLE:
                //item 头部
                holder = new IndexTitleHolder(inflate(parent, R.layout.index_item_title));
                break;
            case TYPE_NEAR:
                //附近优选
                holder = new NearHolder(inflate(parent, R.layout.index_item_near));
                break;
            case TYPE_FOOTER:
                holder = new FooterHolder(inflate(parent, R.layout.index_item_foot));
                break;
            default:
                break;
        }
        if (holder == null) {
            throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        }
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        int itemViewType = holder.getItemViewType();
        if (position < mList.size()) {
            Object obj = mList.get(position);
            switch (itemViewType) {
                case TYPE_BANNER:
                    //广告
                    setBanner((BannerHolder) holder, (IndexFragmentEntity.IndexBanner) obj);
                    break;
                case TYPE_FUNTION:
                    //宫格列表
                    setFuntion((FuntionHolder) holder, (IndexFragmentEntity.Content) obj);
                    break;
                case TYPE_TITLE:
                    //item 头部
                    setupTitle((IndexTitleHolder) holder, (String) obj);
                    break;
                case TYPE_NEAR:
                    //附近优选
                    setNearChoose((NearHolder) holder, (IndexFragmentEntity.Near) obj);
                    break;
                default:
                    break;
            }
        } else if (position + 1 == getItemCount()) {
            setFooter((FooterHolder) holder);
        }


    }

    private View inflate(ViewGroup parent, int resId) {
        return LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
    }

    // 轮播图
    class BannerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.index_banner)
        ConvenientBanner indexBanner;

        public BannerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //宫格列表
    class FuntionHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_index_item_funtion)
        ImageView imgIndexItemFuntion;
        @BindView(R.id.tv_index_item_name)
        TextView tvIndexItemName;

        public FuntionHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //item标题
    class IndexTitleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_index_item_title)
        TextView tvIndexItemTitle;

        public IndexTitleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //附近优选
    class NearHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_near_item)
        ImageView imgNearItem;
        @BindView(R.id.lin_left)
        LinearLayout linLeft;
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        @BindView(R.id.lin_navigation)
        LinearLayout linNavigation;
        @BindView(R.id.lin_right)
        LinearLayout linRight;
        @BindView(R.id.tv_near_parkanme)
        TextView tvNearParkanme;
        @BindView(R.id.tv_near_description)
        TextView tvNearDescription;
        @BindView(R.id.tv_rates)
        TextView tvRates;

        public NearHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //底部
    static class FooterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_footer_progress)
        ProgressBar itemFooterProgress;
        @BindView(R.id.item_footer_message)
        TextView itemFooterMessage;

        FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    //绑定控件

    private void setBanner(BannerHolder bannerHolder, IndexFragmentEntity.IndexBanner object) {
        ConvenientBanner indexBanner = bannerHolder.indexBanner;
        List<IndexFragmentEntity.IndexBanner.Banner> list = object.getList();
        initBanner(indexBanner, list);
    }

    /**
     * 初始化轮播图
     */
    private void initBanner(ConvenientBanner convenientBanner, List<IndexFragmentEntity.IndexBanner.Banner> list) {
        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        convenientBanner.setPages(
                new CBViewHolderCreator() {
                    @Override
                    public ImageHolderView createHolder(View itemView) {
                        return new ImageHolderView(itemView, mContext);
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.item_localimage;
                    }
                }, list)
                //设置自动切换（同时设置了切换时间间隔）
                .startTurning(8000)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                .setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        bannerOnItemClckListener.onBannerItemClckListener(position);
                    }
                });
        //设置指示器的方向
//                .setOnPageChangeListener(this)//监听翻页事件
        ;
    }

    public interface BannerOnItemClckListener {
        void onBannerItemClckListener(int position);
    }

    //设置item的头部
    private void setupTitle(IndexTitleHolder holder, String object) {
        holder.tvIndexItemTitle.setText(object);
    }

    //附近优选
    private void setNearChoose(NearHolder holder, final IndexFragmentEntity.Near object) {
        if (object == null) return;
        String url = object.getUrl();
        //设置图片
        if (holder.imgNearItem == null) {
        } else {
            GlideUtil.loadNoOptions(mContext, url, holder.imgNearItem);
        }
        holder.tvNearParkanme.setText(object.getParkName());
        holder.tvNearDescription.setText(object.getDescription());
        holder.tvDistance.setText(object.getDistance() + "KM");
        holder.tvRates.setText(object.getCharge());
        if(onItemClickListener !=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onRVItemClickListener(object);
                }
            });
        }
        holder.linNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nearNavigationListener.onNearNavigationListener(object);
            }
        });
    }

    //设置宫格列表
    private void setFuntion(FuntionHolder holder, final IndexFragmentEntity.Content object) {
        if (object == null) return;
        holder.tvIndexItemName.setText(object.getmTitle());
        if(onItemClickListener !=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onRVItemClickListener(object);
                }
            });
        }
    }

    //设置底部
    private void setFooter(FooterHolder holder) {
        FooterHolder footerHolder = (FooterHolder) holder;
        footerHolder.itemFooterProgress.setVisibility(isNoData ? View.GONE : View.VISIBLE);
        footerHolder.itemFooterMessage.setText(isNoData ? "--- 扯到底了 ---" : "加载中……");
    }

    public void setShowFooter(boolean flag) {
        this.isShowFooter = flag;
        this.notifyDataSetChanged();
    }

    public void setNoData(boolean flag) {
        this.isNoData = flag;
        this.notifyDataSetChanged();
    }

    /**
     * 点击事件
     */
    public interface  IndexOnItemClickListener{
        void onRVItemClickListener(Object object);
    }

    /**
     * 导航事件
     */
    public interface  IndexNearNavigationListener{
       void onNearNavigationListener(Object object);
    }

}
