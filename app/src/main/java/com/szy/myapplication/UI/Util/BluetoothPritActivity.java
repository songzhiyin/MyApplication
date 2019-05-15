package com.szy.myapplication.UI.Util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.lib.szy.pullrefresh.ItemDecoration.MyDeviderDecoration;
import com.lib.szy.pullrefresh.PullreFresh.BaseRecyAdapter;
import com.szy.lib.network.Retrofit.Util.Dialog_util;
import com.szy.myapplication.Adapter.ItemBluetoothMessageAdapter;
import com.szy.myapplication.Base.BaseActivity;
import com.szy.myapplication.R;
import com.szy.myapplication.Utils.BluetoothUtil.BluetoothUtil;
import com.szy.myapplication.Utils.BluetoothUtil.PrintUtils;
import com.szy.myapplication.Utils.PreferenManager;
import com.szy.myapplication.Utils.ToastUtils;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 蓝牙界面
 * 只支持热成敏标准ESC/POS蓝牙打印机
 */
public class BluetoothPritActivity extends BaseActivity {
    private ItemBluetoothMessageAdapter adapter;
    private BluetoothSocket mSocket;
    private BluetoothDevice device;
    private TextView tvDeviceName;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_bluetooth;
    }

    @Override
    protected void initViews() {
        super.initViews();
        tvDeviceName = $(R.id.tvDeviceName);
        recyclerView = $(R.id.recyBlueList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ItemBluetoothMessageAdapter(mContext);
        recyclerView.addItemDecoration(new MyDeviderDecoration(mContext, R.color.main_line_color, 1));
        recyclerView.setAdapter(adapter);
        setTextTitleName("蓝牙打印");
        setTextRightName("打印");
    }

    @Override
    protected void initdatas() {
        super.initdatas();
        List<BluetoothDevice> printerDevices = BluetoothUtil.getPairedDevices();
        adapter.getData().clear();
        adapter.setDataList(printerDevices);
        getPrefremanageBluetooth();

    }

    private void getPrefremanageBluetooth() {
        if (PreferenManager.getBluetoothAddress() == null || PreferenManager.getBluetoothAddress().length() == 0) {
            return;
        }
        try {
            device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(PreferenManager.getBluetoothAddress());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (mSocket != null) {
                        try {
                            mSocket.close();
                        } catch (IOException e) {
                            mSocket = null;
                            e.printStackTrace();
                        }
                    }
                    mSocket = BluetoothUtil.connectDevice(device);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvDeviceName.setText("连接的蓝牙：" + device != null ? device.getName() : "无");
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        adapter.setOnItemClickListener(onItemClickListener);
        setBackOnclickListner(mContext);
        setTextRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textRightOnclick();
            }
        });

    }

    private BaseRecyAdapter.OnItemClickListener onItemClickListener = new BaseRecyAdapter.OnItemClickListener() {
        @Override
        public void Onclick(int position, Object data) {
            device = adapter.getItemData(position);
            ToastUtils.show_toast("开始连接");
            getSocket();
        }
    };

    private void textRightOnclick() {
        if (checkBluetoothState() && mSocket != null) {
            pritOrderMessage(mSocket);
        } else {
            ToastUtils.show_toast("请先连接打印机");
        }
    }

    private void getSocket() {
        Dialog_util.start_NetworkRequests_diolog(mContext);
        Observable.just(BluetoothUtil.connectDevice(device)==null?false:true).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                ToastUtils.show_toast(aBoolean ? "连接成功" : "连接失败");
                tvDeviceName.setText("连接的蓝牙：" + device != null && aBoolean ? device.getName() : "无");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Dialog_util.close_NetworkRequests_diolog();
            }
        });

    }



    /**
     * 打印订单信息
     */
    private void pritOrderMessage(BluetoothSocket socket) {
        try {
            PrintUtils.setOutputStream(socket.getOutputStream());
            PrintUtils.selectCommand(PrintUtils.RESET);
            PrintUtils.selectCommand(PrintUtils.LINE_SPACING_DEFAULT);
            PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
            PrintUtils.printText("美食餐厅\n\n");
            PrintUtils.selectCommand(PrintUtils.DOUBLE_HEIGHT_WIDTH);
            PrintUtils.printText("桌号：1号桌\n\n");
            PrintUtils.selectCommand(PrintUtils.NORMAL);
            PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
            PrintUtils.printText(PrintUtils.printTwoData("订单编号", "201507161515\n"));
            PrintUtils.printText(PrintUtils.printTwoData("点菜时间", "2016-02-16 10:46\n"));
            PrintUtils.printText(PrintUtils.printTwoData("上菜时间", "2016-02-16 11:46\n"));
            PrintUtils.printText(PrintUtils.printTwoData("人数：2人", "收银员：张三\n"));

            PrintUtils.printText("--------------------------------\n");
            PrintUtils.selectCommand(PrintUtils.BOLD);
            PrintUtils.printText(PrintUtils.printThreeData("项目", "数量", "金额\n"));
            PrintUtils.printText("--------------------------------\n");
            PrintUtils.selectCommand(PrintUtils.BOLD_CANCEL);
            PrintUtils.printText(PrintUtils.printThreeData("苏打水", "1", "0.00\n"));
            PrintUtils.printText(PrintUtils.printThreeData("米饭", "1", "6.00\n"));
            PrintUtils.printText(PrintUtils.printThreeData("铁板烧饭", "1", "26.00\n"));
            PrintUtils.printText(PrintUtils.printThreeData("可口可乐", "1", "226.00\n"));
            PrintUtils.printText(PrintUtils.printThreeData("香肠", "1", "2226.00\n"));
            PrintUtils.printText(PrintUtils.printThreeData("水", "888", "98886.00\n"));

            PrintUtils.printText("--------------------------------\n");
            PrintUtils.printText(PrintUtils.printTwoData("合计", "53.50\n"));
            PrintUtils.printText(PrintUtils.printTwoData("抹零", "3.50\n"));
            PrintUtils.printText("--------------------------------\n");
            PrintUtils.printText(PrintUtils.printTwoData("应收", "50.00\n"));
            PrintUtils.printText("--------------------------------\n");

            PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
            PrintUtils.printText("备注：不要辣、不要香菜");
            PrintUtils.printText("\n\n\n\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查蓝牙状态，如果已打开，则查找已绑定设备
     *
     * @return
     */
    public boolean checkBluetoothState() {
        if (BluetoothUtil.isBluetoothOn()) {
            return true;
        } else {
            BluetoothUtil.openBluetooth(this);
            return false;
        }
    }
}
