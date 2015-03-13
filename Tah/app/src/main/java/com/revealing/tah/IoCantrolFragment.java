package com.revealing.tah;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;


import bleservice.BluetoothLeService;
import util.Constant;

/**
 * Created by shail on 12/03/15.
 */
public class IoCantrolFragment extends Fragment{

    @Nullable
    Context context;
    Switch mSwD8;
    private String mDeviceName;
    private String mDeviceAddress;
    private BluetoothLeService mBluetoothLeService;

    final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
//                    Log.e(TAG, "Unable to initialize Bluetooth");
//                    finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.iocantrol, container, false);
        mDeviceName=(getArguments().getString(Constant.EXTRAS_DEVICE_NAME));
        mDeviceAddress=(getArguments().getString(Constant.EXTRAS_DEVICE_ADDRESS));
        mSwD8= (Switch) view.findViewById(R.id.switch1);
        context=getActivity();
        //create connection with service


        Intent gattServiceIntent = new Intent(getActivity(), BluetoothLeService.class);
        context.bindService(gattServiceIntent, mServiceConnection, context.BIND_AUTO_CREATE);
        mSwD8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
            if(mSwD8.isChecked()){
                mBluetoothLeService.writeCharacteristic(Constant.ServiceUid,Constant.CharaUid,"0,13,1R");
            }else{
              mBluetoothLeService.writeCharacteristic(Constant.ServiceUid,Constant.CharaUid,"0,13,0R");
              }

            }
        });
        return  view;

    }
    @Override
    public void onResume() {
        super.onResume();
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }


}
