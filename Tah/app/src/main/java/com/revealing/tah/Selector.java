package com.revealing.tah;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import bleservice.BluetoothLeService;
import util.CircularButton;
import util.Constant;

/**
 * Created by shail on 12/03/15.
 */

public class Selector extends FragmentActivity implements FragmentManager.OnBackStackChangedListener, View.OnClickListener {

    private String mDeviceName;
    private String mDeviceAddress;
    TextView mConnectionStatus;
    private boolean mConnected = false;
    private FragmentManager fragmentManager;
    private BluetoothLeService mBluetoothLeService;
    private CircularButton mButtonIoController,mButtonPw;
    private TextView mTextHeader;
    private View mViewConnectionStatus;
    private ImageView mImgHeaderBack;
    final ServiceConnection mServiceConnection = new ServiceConnection() {
//chek all fragment view clickable
//http://stackoverflow.com/questions/10389620/fragment-over-another-fragment-issue
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector_layout);
        mImgHeaderBack= (ImageView) findViewById(R.id.imghederback);
        mViewConnectionStatus=findViewById(R.id.viewhconstatus);
        mTextHeader= (TextView) findViewById(R.id.view_actionbar_title);
        mTextHeader.setText("Home");
        mConnectionStatus= (TextView) findViewById(R.id.txtconnectionstatus);
        mButtonIoController= (CircularButton) findViewById(R.id.btniocantroller);
        mButtonIoController.setOnClickListener(this);
        mButtonPw= (CircularButton) findViewById(R.id.btnpwcontoller);
        mButtonPw.setOnClickListener(this);
        mImgHeaderBack.setOnClickListener(this);
//        ActionBar actionBar=getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        if(intent!=null){
            mDeviceName = intent.getStringExtra(Constant.EXTRAS_DEVICE_NAME);
            mDeviceAddress = intent.getStringExtra(Constant.EXTRAS_DEVICE_ADDRESS);
        }
        //connect to service
        Intent gattServiceIntent = new Intent(Selector.this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        //set fragment
        //setFragment(new MenuFragment());

    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getSupportFragmentManager();
        System.out.println("Fragment in stack======================================================="+ fragmentManager.getBackStackEntryCount());
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            //Log.d(TAG, "Connect request result=" + result);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(mGattUpdateReceiver);
    }

//write data method of main activity
    public boolean writeData(String value) {
        if(mConnected) {
         try {
        return mBluetoothLeService.writeCharacteristic(Constant.ServiceUid, Constant.CharaUid, value);
            } catch (Exception e) {
        e.printStackTrace();
        return false;
        }
        } else {
    Toast.makeText(getApplicationContext(),"Please check device connection..",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                updateConnectionState(mConnected,R.string.connected,"#66bb6a");
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                updateConnectionState(mConnected,R.string.disconnected,"#ef5350");
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
               // displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
               // displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };

    //set status of connection using UI Thread
    private void updateConnectionState(final boolean connectStat,final int resourceId, final String color) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mViewConnectionStatus.setBackgroundColor(Color.parseColor(color));
                mConnectionStatus.setText(resourceId);
                if(!connectStat){
//                    mButtonIoController.setButtonColor(Color.parseColor("#b71c1c"));
//                    mButtonPw.setButtonColor(Color.parseColor("#b71c1c"));
                }
            }
        });
    }



    //method to set fragment
    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.EXTRAS_DEVICE_NAME,mDeviceName);
        bundle.putString(Constant.EXTRAS_DEVICE_ADDRESS,mDeviceAddress);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }
    //change fragment
    public void changeFragment(Fragment targetFragment,String headerText) {
mTextHeader.setText(headerText);
        if (targetFragment != null) {
            fragmentManager = getSupportFragmentManager();
            fragmentManager.addOnBackStackChangedListener(this);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            String packageNameAsTag = ((Object) targetFragment).getClass().getCanonicalName();
            System.out.println("package name is :: " + packageNameAsTag);

            if (fragmentManager.findFragmentByTag(packageNameAsTag) == null) {
                fragmentTransaction.add(R.id.mainfragment, targetFragment, packageNameAsTag);
                fragmentTransaction.addToBackStack(packageNameAsTag);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.EXTRAS_DEVICE_NAME,mDeviceName);
                bundle.putString(Constant.EXTRAS_DEVICE_ADDRESS,mDeviceAddress);
                targetFragment.setArguments(bundle);
                System.out.println(((Object) targetFragment).getClass().getSimpleName() + " added to backstack");
                fragmentTransaction.commit();
            } else {
                System.out.println("this fragment is already in the backstack");
            }
        } else {
//            ToastUtil.displayToast(this, this.getString(R.string.toast_working));

        }
    }
    //actionbar home click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:

               onBackPressed();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
    //need to impliment logic here
    @Override
    public void onBackStackChanged() {

    }
    //button on click
    @Override
    public void onClick(View v) {
    switch (v.getId()){
    case R.id.btnpwcontoller:
        changeFragment(new PwmControlFragment(),"PWM Control");
        break;
    case R.id.btniocantroller:
        changeFragment(new IoCantrolFragment(),"IO Control");
        break;
    case R.id.imghederback:
        onBackPressed();
        break;
    }
    }
    //method to clear fragment
    /**
     * Function to clear fragment backstack but one
     */
    public void clearFragmentBackStack() {

        int i = fragmentManager.getBackStackEntryCount();
        for (int j = 0; j < i - 1; j++) {
            fragmentManager.popBackStackImmediate();
        }
    }

//    FragmentManager fm = getSupportFragmentManager();
//    int count = fm.getBackStackEntryCount();
//    for(int i = 0; i < count; ++i) {
//        fm.popBackStackImmediate();
//    }
}
