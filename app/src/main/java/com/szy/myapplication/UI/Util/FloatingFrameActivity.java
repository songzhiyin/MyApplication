package com.szy.myapplication.UI.Util;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;
import com.szy.myapplication.Utils.ToastUtils;
import com.szy.myapplication.service.FloatingButtonService;
import com.szy.myapplication.service.FloatingImageDisplayService;
import com.szy.myapplication.service.FloatingVideoService;

/**
 * 浮动框
 */
public class FloatingFrameActivity extends BaseActivity {

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_floating_frame;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTextTitleName("浮动框");

    }

    @Override
    protected void initEvents() {
        super.initEvents();
        setBackOnclickListner(mContext);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                    startService(new Intent(mContext, FloatingButtonService.class));
                }
            } else {
                startService(new Intent(mContext, FloatingButtonService.class));
//                ToastUtils.show_toast("手机安卓版本过低，必须>=android6.0才行");
            }
        } else if (requestCode == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                    startService(new Intent(mContext, FloatingImageDisplayService.class));
                }
            } else {
//                ToastUtils.show_toast("手机安卓版本过低，必须>=android6.0才行");
                startService(new Intent(mContext, FloatingImageDisplayService.class));
            }
        } else if (requestCode == 2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                    startService(new Intent(mContext, FloatingVideoService.class));
                }
            } else {
//                ToastUtils.show_toast("手机安卓版本过低，必须>=android6.0才行");
                startService(new Intent(mContext, FloatingVideoService.class));
            }
        }
    }

    public void startFloatingButtonService(View view) {
        if (FloatingButtonService.isStarted) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT);
                startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
            } else {
                startService(new Intent(mContext, FloatingButtonService.class));
            }
        } else {
//            ToastUtils.show_toast("手机安卓版本过低，必须>=android6.0才行");
            startService(new Intent(mContext, FloatingButtonService.class));
        }
    }

    public void stopFloatingButtonService(View view) {
        if (!FloatingButtonService.isStarted) {
            return;
        }
        stopService(new Intent(mContext, FloatingButtonService.class));
    }

    public void startFloatingImageDisplayService(View view) {
        if (FloatingImageDisplayService.isStarted) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT);
                startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 1);
            } else {
                startService(new Intent(mContext, FloatingImageDisplayService.class));
            }
        } else {
//            ToastUtils.show_toast("手机安卓版本过低，必须>=android6.0才行");
            startService(new Intent(mContext, FloatingImageDisplayService.class));
        }
    }

    public void stopFloatingImageDisplayService(View view) {
        if (!FloatingImageDisplayService.isStarted) {
            return;
        }
        stopService(new Intent(mContext, FloatingImageDisplayService.class));
    }

    public void startFloatingVideoService(View view) {
        if (FloatingVideoService.isStarted) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT);
                startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 2);
            } else {
                startService(new Intent(mContext, FloatingVideoService.class));
            }
        } else {
//            ToastUtils.show_toast("手机安卓版本过低，必须>=android6.0才行");
            startService(new Intent(mContext, FloatingVideoService.class));
        }
    }

    public void stopFloatingVideoService(View view) {
        if (!FloatingVideoService.isStarted) {
            return;
        }
        stopService(new Intent(mContext, FloatingVideoService.class));
    }
}

