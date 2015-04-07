package com.revealing.tah;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by shail on 04/04/15.
 */
public class MyBluetoothReceiver extends BroadcastReceiver {
    public static boolean pinavilable = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        // When discovery finds a device
        if (BluetoothDevice.ACTION_PAIRING_REQUEST.equals(action)) {
            pinavilable = true;

            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            // Get the BluetoothDevice object from the Intent
//            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//            int type = intent.getIntExtra(BluetoothDevice.EXTRA_PAIRING_VARIANT,
//                    BluetoothDevice.ERROR);
//            device.setPairingConfirmation( true );
            //you can get name by device.getName()

            showDialog(intent);


        }
    }

    public void showDialog(final Intent intent) {
        final Dialog mydialog = new Dialog(Selector.activity);
        mydialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mydialog.setContentView(R.layout.pindialog);
        mydialog.setCancelable(true);
        Button btnok = (Button) mydialog.findViewById(R.id.btnok);
        Button btncancel = (Button) mydialog.findViewById(R.id.btncancel);
        final EditText edtpin = (EditText) mydialog.findViewById(R.id.edtpin);
        try {
            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pairDevice(edtpin.getText().toString(), intent);
                    mydialog.cancel();
                }
            });

            btncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mydialog.cancel();
                    Selector.activity.finish();

                }
            });
            mydialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void pairDevice(String pinstrg, Intent intent) {

        String DEVICE_PIN = "654321";
        final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        device.createBond();
        device.setPairingConfirmation(true);
        String pind = "123456";
        byte pins[] = pind.getBytes();
        //device.setPin(pin);

        try {
            byte[] pin = (byte[]) BluetoothDevice.class.getMethod("convertPinToBytes", String.class).invoke(BluetoothDevice.class, pinstrg);
            BluetoothDevice.class.getMethod("setPin", byte[].class).invoke(device, pin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(Selector.activity, "Please wait,it will take some time...", Toast.LENGTH_SHORT).show();


    }
}
