package com.zykj.carfigure.widget.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.activity.WaitPayFareActivity;
import com.zykj.carfigure.adapter.RechargeAdapter;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;
import com.zykj.carfigure.entity.RechargeType;
import com.zykj.carfigure.entity.WaitPay;
import com.zykj.carfigure.utils.ToastManager;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/219:51
 * desc   :待交车费支付弹窗
 * version: 1.0
 */
public class PayMoneyPopup extends PopupWindow implements BaseRecylerAdapter.OnItemClickListener,View.OnClickListener{
    private Context mContext;
    private View mPopView;
    private WaitPay waitPay;
    private RecyclerView recyclerView;
    private TextView tv_paymoney;
    private RechargeAdapter rechargeAdapter;
    private TextView goPay;
    private int type=0;//付款方式
    private ToPayWaitMoneyListener toPayWaitMoneyListener;


    public PayMoneyPopup(Context context,WaitPay waitPay,ToPayWaitMoneyListener listener) {
        super(context);
        this.mContext = context;
        this.waitPay = waitPay;
        this.toPayWaitMoneyListener = listener;
        initView(context);
        setPopupWindow();
    }

    public WaitPay getWaitPay() {
        return waitPay;
    }

    public void setWaitPay(WaitPay waitPay) {
        this.waitPay = waitPay;
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mPopView = inflater.inflate(R.layout.pay_money_popup_layout, null);
        recyclerView = (RecyclerView) mPopView.findViewById(R.id.paytype_recycle);
        tv_paymoney = (TextView) mPopView.findViewById(R.id.tv_pay_money_pop);
        goPay = (TextView)mPopView.findViewById(R.id.tv_gopay);
        goPay.setOnClickListener(this);
        rechargeAdapter = new RechargeAdapter(mContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(rechargeAdapter);
        rechargeAdapter.setList(WaitPayFareActivity.initPayTypeData());
        rechargeAdapter.setOnItemClickListener(this);

        if(waitPay==null){
            tv_paymoney.setText("");
        }else {
            tv_paymoney.setText("￥"+waitPay.getPayMoney());
        }


    }
    private void setPopupWindow(){
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setAnimationStyle(R.style.popupwindow_anim_style);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        mPopView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mPopView.findViewById(R.id.lin_pay).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        //setBackgroundAlpha(0.5f);//设置屏幕透明度
        super.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void dismiss() {
        //setBackgroundAlpha(1.0f);
        super.dismiss();
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     *            屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity) mContext).getWindow().setAttributes(lp);

    }

    @Override
    public void onItemClick(View view, Object item, int position) {
        rechargeAdapter.setSelection(position);
        //获取到付款方式
        if (item==null) return;
        RechargeType rechargeType = (RechargeType) item;
        type = rechargeType.getType();
    }

    @Override
    public void onClick(View v) {
      if(type==0||waitPay==null){
          ToastManager.showLongToast(mContext,"请选择支付方式");
          return;
      }else{
          dismiss();
          toPayWaitMoneyListener.toPayWaitMoney(type,waitPay.getPayMoney());
      }
    }
    public interface  ToPayWaitMoneyListener{
        void toPayWaitMoney(int type,double money);
    }
}
