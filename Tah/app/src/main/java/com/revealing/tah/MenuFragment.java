package com.revealing.tah;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import util.Constant;

/**
 * Created by shail on 12/03/15.
 */
public class MenuFragment extends Fragment{
    Button mIoCant;
    private String mDeviceName;
    private String mDeviceAddress;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menufragment, container, false);

        mDeviceName=(getArguments().getString(Constant.EXTRAS_DEVICE_NAME));
        mDeviceAddress=(getArguments().getString(Constant.EXTRAS_DEVICE_ADDRESS));

        mIoCant= (Button) view.findViewById(R.id.btniocantroller);
        mIoCant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"on click"+mDeviceAddress,Toast.LENGTH_SHORT).show();
                Fragment newFragment = new IoCantrolFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString(Constant.EXTRAS_DEVICE_NAME,mDeviceName);
                bundle.putString(Constant.EXTRAS_DEVICE_ADDRESS,mDeviceAddress);
                newFragment.setArguments(bundle);
                transaction.replace(android.R.id.content, newFragment);
                transaction.addToBackStack(null);
                // Commit the transaction
                transaction.commit();
            }
        });
        return view;
    }
}
