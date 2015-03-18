package com.revealing.tah;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import util.Constant;

//http://www.andevcon.com/news/46-android-developer-libraries-by-category
//https://github.com/snowdream/awesome-android
//http://snowdream.github.io/awesome-android/
public class MainActivity extends Activity {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LeDeviceListAdapter mLeDeviceListAdapter;
    //bluetooth things
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;
    private Handler mHandler;
    private boolean mScanning;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;
    private ListView mDeviceList;
    private TextView mTextHeader;
    private ImageView mImgHeaderBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDeviceList= (ListView) findViewById(R.id.listView);
        mTextHeader= (TextView) findViewById(R.id.view_actionbar_title);
        mImgHeaderBack= (ImageView) findViewById(R.id.imghederback);
        mImgHeaderBack.setVisibility(View.INVISIBLE);
        mTextHeader.setText("Discover");
        mHandler = new Handler();
//        ActionBar actionBar=getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        //init bluetooth adapter
        final BluetoothManager bluetoothManager =(BluetoothManager) getSystemService(this.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        //first check bluetooth is enable or not
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            showAlert(MainActivity.this);
        }else{
            mLeDeviceListAdapter = new LeDeviceListAdapter();
            mDeviceList.setAdapter(mLeDeviceListAdapter);
            scanLeDevice(true);
        }

         //swipe to refresh
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mBluetoothAdapter.isDiscovering()){
                            Toast.makeText(MainActivity.this,"Already in process..",Toast.LENGTH_SHORT).show();
                        }else {
                            mLeDeviceListAdapter = new LeDeviceListAdapter();
                            mDeviceList.setAdapter(mLeDeviceListAdapter);
                            scanLeDevice(true);
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 2500);
            }
        });

        mDeviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final BluetoothDevice device = mLeDeviceListAdapter.getDevice(position);
                Toast.makeText(MainActivity.this,""+device.getName(),Toast.LENGTH_LONG).show();

                if (device == null) return;
                final Intent intent = new Intent(MainActivity.this, Selector.class);
                intent.putExtra(Constant.EXTRAS_DEVICE_NAME, device.getName());
                intent.putExtra(Constant.EXTRAS_DEVICE_ADDRESS, device.getAddress());
                if (mScanning) {
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    mScanning = false;
                }
                startActivity(intent);
//
//                Intent i=new Intent(MainActivity.this,Selector.class);
//                startActivity(i);
            }
        });
    }


    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();
    }
    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLeDeviceListAdapter.addDevice(device);
                            mLeDeviceListAdapter.notifyDataSetChanged();
                        }
                    });
                }
            };
    //alert dialog if bluetooth disable
    public void showAlert(Context context){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Please enable bluetooth..");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    //on activity result used after bluetooth start
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(MainActivity.this, "Please turn on bluetooth...", Toast.LENGTH_SHORT).show();
            //finish();
            return;
        }else{
            mLeDeviceListAdapter = new LeDeviceListAdapter();
            mDeviceList.setAdapter(mLeDeviceListAdapter);
            scanLeDevice(true);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //holder class for adapter
    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
    }
    // Adapter class  for holding devices found through scanning.
    private class LeDeviceListAdapter extends BaseAdapter {
        private ArrayList<BluetoothDevice> mLeDevices;
        private LayoutInflater mInflator;

        public LeDeviceListAdapter() {
            super();
            mLeDevices = new ArrayList<BluetoothDevice>();
            mInflator = MainActivity.this.getLayoutInflater();
        }

        public void addDevice(BluetoothDevice device) {
            if (!mLeDevices.contains(device)) {
                mLeDevices.add(device);
            }
        }

        @Override
        public int getCount() {
            return mLeDevices.size();
        }

        @Override
        public Object getItem(int i) {
            return mLeDevices.get(i);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            // General ListView optimization code.
            if (view == null) {
                view = mInflator.inflate(R.layout.devicelist, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.dvname);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.dvid);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            BluetoothDevice device = mLeDevices.get(i);
            final String deviceName = device.getName();
            if (deviceName != null && deviceName.length() > 0) {

                viewHolder.deviceName.setText(deviceName);
            }
            else {
                viewHolder.deviceName.setText("Unknown Device");

            }
            viewHolder.deviceAddress.setText(device.getAddress());
            return view;
        }


        public BluetoothDevice getDevice(int position) {
            return mLeDevices.get(position);
        }

        public void clear() {
            mLeDevices.clear();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:

        finish();
            break;

        }

        return super.onOptionsItemSelected(item);
    }
}
