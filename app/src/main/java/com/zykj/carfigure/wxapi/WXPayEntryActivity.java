package com.zykj.carfigure.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zykj.carfigure.R;
import com.zykj.carfigure.app.AppConfig;
import com.zykj.carfigure.log.Log;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler{
	

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, AppConfig.WX_APPID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int code = resp.errCode;
            switch (code) {
                case 0:
                    Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case -1:
                    Toast.makeText(this, "支付失败", Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case -2:
                    Toast.makeText(this, "支付取消", Toast.LENGTH_LONG).show();
                    finish();
                    break;
                default:
                    break;
              }
        }
	}
}