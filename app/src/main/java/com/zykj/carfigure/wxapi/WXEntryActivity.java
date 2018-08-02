package com.zykj.carfigure.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zykj.carfigure.app.AppConfig;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler{
	private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, AppConfig.WX_APPID, false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }

    @Override
    public void onReq(BaseReq baseReq) {
    	switch (baseReq.getType()) {
		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
			finish();// 原来这里会调用微信的信息接收方法
			break;
		default:
			break;
		}
    }

    @Override
    public void onResp(BaseResp baseResp) {
        String result = "";
        if(baseResp.getType()==ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {//分享
            switch (baseResp.errCode){
                case BaseResp.ErrCode.ERR_OK:
                    Toast.makeText(WXEntryActivity.this, "分享成功!", Toast.LENGTH_SHORT).show();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                   Toast.makeText(WXEntryActivity.this, "分享取消!", Toast.LENGTH_SHORT).show();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    Toast.makeText(this, "授权被拒绝", Toast.LENGTH_LONG).show();
                    break;
                default:break;
            }
            finish();
        }else if(baseResp.getType()==ConstantsAPI.COMMAND_SENDAUTH) {//登录发送广播
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    String code = ((SendAuth.Resp) baseResp).code;
                    Intent intent = new Intent();
                    intent.setAction("authlogin");
                    intent.putExtra("code", code);
                    WXEntryActivity.this.sendBroadcast(intent);
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "取消授权";
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = "授权被拒绝";
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                    finish();
                    break;
                default:
                    result = "授权失败返回";
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                    finish();
                    break;
            }
        }
    }
	
}