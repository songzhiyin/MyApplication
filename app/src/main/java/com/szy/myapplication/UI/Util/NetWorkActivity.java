package com.szy.myapplication.UI.Util;

import android.view.View;
import android.widget.TextView;

import com.szy.lib.network.Retrofit.Util.Dialog_util;
import com.szy.lib.network.Retrofit.Util.RequestBody_Util;
import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.Base.BaseEntity;
import com.szy.myapplication.Entity.NetMessageEntity;
import com.szy.myapplication.R;
import com.szy.myapplication.Retrofit.HttpModel;
import com.szy.myapplication.Retrofit.OnObserverRetrofitResetCallBack;
import com.szy.myapplication.Utils.ToastUtils;

import org.reactivestreams.Subscription;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

/**
 * 网络请求
 */
public class NetWorkActivity extends BaseActivity {
    private TextView tv_network_post;
    private TextView tv_network_get;
    private TextView tv_network_file;
    private String sessionId = "dfad66a8dd66428da5ea672ef23df2b4";//密钥

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_network;
    }

    @Override
    protected void initViews() {
        super.initViews();
        tv_network_post = $(R.id.tv_network_post);
        tv_network_get = $(R.id.tv_network_get);
        tv_network_file = $(R.id.tv_network_file);
        httpModel = new HttpModel();
        setTextTitleName("网络请求");
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        tv_network_post.setOnClickListener(onClickListener);
        tv_network_get.setOnClickListener(onClickListener);
        tv_network_file.setOnClickListener(onClickListener);
        setBackOnclickListner(mContext);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_network_post:
                    login();
                    break;
                case R.id.tv_network_get:
                    getMessageList();
                    break;
                case R.id.tv_network_file:
                    uploadAvatar();
                    break;
            }
        }
    };


    /**
     * POST请求方式获取数据
     */
    private void login() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "Jennifer");
        map.put("password", "123456");
        httpModel.login_server(map, new OnObserverRetrofitResetCallBack() {
            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 上传文件
     */
    private void uploadAvatar() {
        Map<String, RequestBody> map = new HashMap<>();
        File file_head = new File("222");
        map.put(RequestBody_Util.parseImageMapKey("file", file_head.getName()), RequestBody_Util.parseRequestBody_File(file_head));
        map.put("sessionId", RequestBody_Util.parseRequestBody_String(sessionId));
        httpModel.uploadAvatar(map, new OnObserverRetrofitResetCallBack<BaseEntity>() {


            @Override
            public void onSuccess(BaseEntity model) {
                ToastUtils.show_toast(model.getMessage());
                Dialog_util.close_NetworkRequests_diolog();

            }

            @Override
            public void onFailure(String msg) {
            }
        });
    }

    /**
     * GET请求，获取数据列表
     */
    private void getMessageList() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNumber", pageNumber);
        map.put("pageSize", pageSize);
        map.put("sessionId", sessionId);
        httpModel.getMessageList(map, new OnObserverRetrofitResetCallBack<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity model) {
                super.onSuccess(model);
                NetMessageEntity entity = null;
                if (model instanceof NetMessageEntity) {
                    entity = (NetMessageEntity) model;
                }
                if (entity != null && entity.getData().getList() != null) {
                    ToastUtils.show_toast("获取到的消息数量:" + entity.getData().getList().size());
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }
}
