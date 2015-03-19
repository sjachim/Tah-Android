package com.revealing.tah;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
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
public class IoCantrolFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    @Nullable
    Context context;
    SwitchCompat mSwD13,mSwD12,mSwD11,mSwD10,mSwD9,mSwD8,mSwD7,mSwD6,mSwD5,mSwD4,mSwD3,mSwD2;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.iocantrol, container, false);
        mSwD13= (SwitchCompat) view.findViewById(R.id.swd13);
        mSwD13.setOnCheckedChangeListener(this);
        mSwD12= (SwitchCompat) view.findViewById(R.id.swd12);
        mSwD12.setOnCheckedChangeListener(this);
        mSwD11= (SwitchCompat) view.findViewById(R.id.swd11);
        mSwD11.setOnCheckedChangeListener(this);
        mSwD10= (SwitchCompat) view.findViewById(R.id.swd10);
        mSwD10.setOnCheckedChangeListener(this);
        mSwD9= (SwitchCompat) view.findViewById(R.id.swd9);
        mSwD9.setOnCheckedChangeListener(this);
        mSwD8= (SwitchCompat) view.findViewById(R.id.swd8);
        mSwD8.setOnCheckedChangeListener(this);
        mSwD7= (SwitchCompat) view.findViewById(R.id.swd7);
        mSwD7.setOnCheckedChangeListener(this);
        mSwD6= (SwitchCompat) view.findViewById(R.id.swd6);
        mSwD6.setOnCheckedChangeListener(this);
        mSwD5= (SwitchCompat) view.findViewById(R.id.swd5);
        mSwD5.setOnCheckedChangeListener(this);
        mSwD4= (SwitchCompat) view.findViewById(R.id.swd4);
        mSwD4.setOnCheckedChangeListener(this);
        mSwD3= (SwitchCompat) view.findViewById(R.id.swd3);
        mSwD3.setOnCheckedChangeListener(this);
        mSwD2= (SwitchCompat) view.findViewById(R.id.swd2);
        mSwD2.setOnCheckedChangeListener(this);

        context=getActivity();

        return  view;

    }
    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.swd13:
                if(isChecked){
                    ((Selector)getActivity()).writeData("0,13,1R");
                }else{
                    ((Selector)getActivity()).writeData("0,13,0R");
                }
                break;
            case R.id.swd12:
                if(isChecked){
                    ((Selector)getActivity()).writeData("0,12,1R");
                }else{
                    ((Selector)getActivity()).writeData("0,12,0R");
                }
                break;
            case R.id.swd11:
                if(isChecked){
                    ((Selector)getActivity()).writeData("0,11,1R");
                }else{
                    ((Selector)getActivity()).writeData("0,11,0R");
                }
                break;
            case R.id.swd10:
                if(isChecked){
                    ((Selector)getActivity()).writeData("0,10,1R");
                }else{
                    ((Selector)getActivity()).writeData("0,10,0R");
                }
                break;
            case R.id.swd9:
                if(isChecked){
                    ((Selector)getActivity()).writeData("0,9,1R");
                }else{
                    ((Selector)getActivity()).writeData("0,9,0R");
                }
                break;
            case R.id.swd8:
                if(isChecked){
                    ((Selector)getActivity()).writeData("0,8,1R");
                }else{
                    ((Selector)getActivity()).writeData("0,8,0R");
                }
                break;
            case R.id.swd7:
                if(isChecked){
                    ((Selector)getActivity()).writeData("0,7,1R");
                }else{
                    ((Selector)getActivity()).writeData("0,7,0R");
                }
                break;
            case R.id.swd6:
                if(isChecked){
                    ((Selector)getActivity()).writeData("0,6,1R");
                }else{
                    ((Selector)getActivity()).writeData("0,6,0R");
                }
                break;
            case R.id.swd5:
                if(isChecked){
                    ((Selector)getActivity()).writeData("0,5,1R");
                }else{
                    ((Selector)getActivity()).writeData("0,5,0R");
                }
                break;
            case R.id.swd4:
                if(isChecked){
                    ((Selector)getActivity()).writeData("0,4,1R");
                }else{
                    ((Selector)getActivity()).writeData("0,4,0R");
                }
                break;
            case R.id.swd3:
                if(isChecked){
                    ((Selector)getActivity()).writeData("0,3,1R");
                }else{
                    ((Selector)getActivity()).writeData("0,3,0R");
                }
                break;
            case R.id.swd2:
                if(isChecked){
                    ((Selector)getActivity()).writeData("0,2,1R");
                }else{
                    ((Selector)getActivity()).writeData("0,2,0R");
                }
                break;


        }
    }
}
