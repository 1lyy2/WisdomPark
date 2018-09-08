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

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.CarNumberSelectAdapter;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;
import com.zykj.carfigure.widget.CommonItemDecoration;

import java.util.List;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/1617:36
 * desc   : 车牌选择
 * version: 1.0
 */
public class CarNumberSelectPopup extends PopupWindow {
    private Context mContext;
    private View mPopView;
    private Object object;
    private RecyclerView recyclerView;
    private CarNumberSelectAdapter carNumberSelectAdapter;
    private List<Object> list;
    private SelectCarNumberInterface selectCarNumberInterface;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public CarNumberSelectPopup(Context context,List<Object> mList,SelectCarNumberInterface selectCarNumberInterface) {
        super(context);
        this.mContext = context;
        this.list = mList;
        this.selectCarNumberInterface = selectCarNumberInterface;
        initView(context);
        setPopupWindow();

    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mPopView = inflater.inflate(R.layout.car_number_select, null);
        recyclerView = (RecyclerView) mPopView.findViewById(R.id.car_number_input_recyc);
        LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        CommonItemDecoration itemDecoration =new CommonItemDecoration(mContext,1,mContext.getResources().getColor(R.color.white));
        recyclerView.addItemDecoration(itemDecoration);
        carNumberSelectAdapter = new CarNumberSelectAdapter(mContext);
        carNumberSelectAdapter.setOnItemClickListener(new BaseRecylerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object item, int position) {
                Object o = list.get(position);
                selectCarNumberInterface.selectCarNumber(o);
                dismiss();
            }
        });
        recyclerView.setAdapter(carNumberSelectAdapter);
        carNumberSelectAdapter.setList(list);
    }
    private void setPopupWindow(){
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setAnimationStyle(R.style.popupwindow_anim_style);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        mPopView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mPopView.findViewById(R.id.lin_pop_number).getTop();
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
    public interface SelectCarNumberInterface{
        void selectCarNumber(Object obj);
    }

}
