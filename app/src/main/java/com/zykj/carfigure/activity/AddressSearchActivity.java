package com.zykj.carfigure.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.AddressSearchAdapter;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;
import com.zykj.carfigure.app.Constants;
import com.zykj.carfigure.base.BaseActivity;
import com.zykj.carfigure.entity.AddressBean;
import com.zykj.carfigure.fragment.NearFragment;
import com.zykj.carfigure.widget.ClearEditText;
import com.zykj.carfigure.widget.CommonItemDecoration;
import com.zykj.carfigure.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//地址检索
public class AddressSearchActivity extends BaseActivity implements PoiSearch.OnPoiSearchListener{

    @BindView(R.id.img_return)
    ImageView returnImg;
    @BindView(R.id.edit_search)
    ClearEditText editSearch;
    @BindView(R.id.address_recyclerView)
    EmptyRecyclerView addressRecyclerView;
    private PoiSearch.Query query;
    private int page=1;
    private boolean isLoading;
    private PoiSearch poiSearch;
    private List<AddressBean> list = new ArrayList<>();
    private AddressSearchAdapter addressSearchAdapter;
    private String address;
    @BindView(R.id.img_cleanhistory)
    ImageView imgCleanhistory;
    @BindView(R.id.flowlayout_search)
    TagFlowLayout flowLayout;
    @BindView(R.id.lin_history)
    LinearLayout historyLayout;
    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_address_search;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
        init();
        initFlow();
    }

    @Override
    protected String getActivityName() {
        return "搜素地址";
    }

    @Override
    protected Context getContext() {
        return this;
    }
    private  void init(){
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str=editSearch.getText().toString();
                if (str.length()>0){
                    historyLayout.setVisibility(View.GONE);
                    addressRecyclerView.setVisibility(View.VISIBLE);
                    list.clear();
                    page=0;
                    address=str;
                    seach(str);
                }else if(str.length()==0||s==null){
                    historyLayout.setVisibility(View.VISIBLE);
                    addressRecyclerView.setVisibility(View.GONE);
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        CommonItemDecoration commonItemDecoration = new CommonItemDecoration(this,1,getResources().getColor(R.color.split_line));
        addressRecyclerView.addItemDecoration(commonItemDecoration);
        addressRecyclerView.setLayoutManager(linearLayoutManager);
        addressSearchAdapter = new AddressSearchAdapter(this);
        addressRecyclerView.setAdapter(addressSearchAdapter);
        addressSearchAdapter.setList(list);
        addressSearchAdapter.setOnItemClickListener(new BaseRecylerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object item, int position) {
                if (item == null) return;
                AddressBean tip = (AddressBean) item;
                Intent intent = new Intent();
                intent.putExtra(Constants.EXTRA_TIP, tip);
                setResult(NearFragment.RESULT_CODE_INPUTTIPS, intent);
                finish();
            }
        });
    }
    private void initFlow(){
        final String [] mVals ={"加油","德玛西亚","的打算大所大","大饼"};
        //历史搜索
        final LayoutInflater mInflater = LayoutInflater.from(AddressSearchActivity.this);

        flowLayout.setAdapter(new TagAdapter<String>(mVals)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) mInflater.inflate(R.layout.search_history_item,
                        flowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                showToastMsgShort(mVals[position]+"");
                return false;
            }
        });
    }

    //滑动加载更多
    private void seach(String address){
        page++;
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query=new PoiSearch.Query(address,"", Constants.DEFAULT_CITY);
        query.setPageSize(100);// 设置每页最多返回多少条poiitem
        query.setPageNum(page);//设置查询页码

        poiSearch = new PoiSearch(this, query);
        poiSearch.searchPOIAsyn();//调用 PoiSearch 的 searchPOIAsyn() 方法发送请求。

        poiSearch.setOnPoiSearchListener(this);

        //周边检索
        //   poiSearch.setBound(new SearchBound(new LatLonPoint(locationMarker.getPosition().latitude,
        //          locationMarker.getPosition().longitude), 1000));//设置周边搜索的中心点以及半径
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        //解析result获取POI信息
        if(i == 1000 && poiResult != null) {
            ArrayList<PoiItem> items = poiResult.getPois();
            for (PoiItem item : items) {
                //获取经纬度对象
                LatLonPoint llp = item.getLatLonPoint();
                double lon = llp.getLongitude();
                double lat = llp.getLatitude();

                //获取标题
                String title = item.getTitle();
                //获取内容
                String text = item.getSnippet();
                String name=item.getAdName();
                String cityName=item.getCityName();
                String area=item.getBusinessArea();
                String  provinceName=item.getProvinceName();

                String addressInfo=provinceName+cityName+name+text;

                list.add(new AddressBean(lon, lat, title, text,addressInfo));
            }
            addressSearchAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiResult, int i) {
    }
    @OnClick({R.id.img_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                finish();
                break;
        }
    }
}

