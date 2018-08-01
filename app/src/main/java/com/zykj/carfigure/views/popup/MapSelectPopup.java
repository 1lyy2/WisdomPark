package com.zykj.carfigure.views.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.zykj.carfigure.R;
import com.zykj.carfigure.entity.Street;
import com.zykj.carfigure.eventbus.Event;
import com.zykj.carfigure.eventbus.EventBusUtils;
import com.zykj.carfigure.eventbus.EventCode;

public class MapSelectPopup extends PopupWindow implements View.OnClickListener {
    private Context mContext;
    private View mPopView;
    private Street street;
    public MapSelectPopup(Context context) {
        super(context);
        this.mContext = context;
        initView(context);
        setPopupWindow();
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mPopView = inflater.inflate(R.layout.popup_map_select, null);
        mPopView.findViewById(R.id.btn_map_gaode).setOnClickListener(this);
        mPopView.findViewById(R.id.btn_map_cancel).setOnClickListener(this);
        mPopView.findViewById(R.id.btn_map_baidu).setOnClickListener(this);
    }
    private void setPopupWindow(){
        this.setContentView(mPopView);// 设置View
        this.setWidth(LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setAnimationStyle(R.style.popupwindow_anim_style);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        mPopView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mPopView.findViewById(R.id.ll_popup).getTop();
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
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_map_gaode:
                EventBusUtils.sendEvent(new Event(EventCode.GAODEMAP,street));
                break;
            case R.id.btn_map_baidu:
                EventBusUtils.sendEvent(new Event(EventCode.BAIDUMAP,street));
                break;
            case R.id.btn_map_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }
}
