package com.zykj.carfigure.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.MyReserveAdapter;
import com.zykj.carfigure.app.Constants;
import com.zykj.carfigure.base.BaseFragment;
import com.zykj.carfigure.entity.ReservedParking;
import com.zykj.carfigure.mvp.presenter.MyOrderParkingPresenter;
import com.zykj.carfigure.mvp.view.IMyOrderParkingView;
import com.zykj.carfigure.widget.EmptyRecyclerView;

import java.util.List;

import butterknife.BindView;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/1611:59
 * desc   : 我的预约fragmnet
 * version: 1.0
 */
public class ReserveFragment extends BaseFragment implements IMyOrderParkingView {
    @BindView(R.id.recycleview_reserve)
    EmptyRecyclerView recycleviewReserve;
    @BindView(R.id.reserve_refresh)
    SwipeRefreshLayout reserveRefresh;
    private MyReserveAdapter myReserveAdapter;
    private int type = 0;//预约类型 0为取消，1为预约成功，2为预约过期
    private MyOrderParkingPresenter myOrderParkingPresenter;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected void initView(View rootView) {
        init();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.reserve_fragment;
    }

    @Override
    protected String getFragmentName() {
        return "";
    }

    @Override
    protected void onLazyLoad() {
        myOrderParkingPresenter.getMyOrderParkingList(Constants.user_id, type);
        showLoadingView(null, null);
    }

    @Override
    public void onCreatePresenter() {
        myOrderParkingPresenter = new MyOrderParkingPresenter(this);
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleviewReserve.setLayoutManager(linearLayoutManager);
        myReserveAdapter = new MyReserveAdapter(getActivity(), getType());
        recycleviewReserve.setAdapter(myReserveAdapter);
    }

    @Override
    public void getMyOrderParkingListSuccess(ReservedParking reservedParking) {
        hideLoadingView();
        if(reservedParking.getStatus()==200){
            List<ReservedParking.OrderParking> data = reservedParking.getData();
            myReserveAdapter.setList(data);
        }else{
            String message = reservedParking.getMessage();
            showToastMsgShort(message+",请刷新");
        }
    }


    @Override
    public void getMyOrderParkingListFailed() {
        hideLoadingView();
        showToastMsgShort("获取预约信息失败，请刷新");
    }
}
