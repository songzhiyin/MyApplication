package com.szy.myapplication.Adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lib.szy.pullrefresh.PullreFresh.BaseRecyAdapter;
import com.szy.myapplication.R;

/**
 * 蓝牙列表的适配器
 */
public class ItemBluetoothMessageAdapter extends BaseRecyAdapter<ItemBluetoothMessageAdapter.HoderView, BluetoothDevice> {
    public ItemBluetoothMessageAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HoderView(mInflater.inflate(R.layout.item_bluetooth_message_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BluetoothDevice device = getItemData(position);
        HoderView hoderView = (HoderView) holder;
        hoderView.tvBlueName.setText(device.getName());
        hoderView.tvBlueAddress.setText(device.getAddress());
        hoderView.tvBlueConnect.setText(getBoodStateString(device.getBondState()));
        hoderView.lineBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.Onclick(position, device);
                }
            }
        });
    }

    private String getBoodStateString(int code) {
        String status = "";
        switch (code) {
            case BluetoothDevice.BOND_BONDED://已绑定
                status = "已绑定";
                break;
            case BluetoothDevice.BOND_BONDING://绑定中
                status = "绑定中";
                break;
            case BluetoothDevice.BOND_NONE://未匹配
                status = "未匹配";
                break;
        }

        return status;
    }

    class HoderView extends RecyclerView.ViewHolder {
        private TextView tvBlueName;
        private TextView tvBlueConnect;
        private TextView tvBlueAddress;
        private LinearLayout lineBluetooth;

        public HoderView(View itemView) {
            super(itemView);
            tvBlueName = itemView.findViewById(R.id.tvBlueName);
            tvBlueConnect = itemView.findViewById(R.id.tvBlueConnect);
            tvBlueAddress = itemView.findViewById(R.id.tvBlueAddress);
            lineBluetooth = itemView.findViewById(R.id.lineBluetooth);
        }
    }
}
