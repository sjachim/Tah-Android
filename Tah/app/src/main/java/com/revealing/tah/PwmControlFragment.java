package com.revealing.tah;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by shail on 19/03/15.
 */
public class PwmControlFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    Context context;
    SeekBar mSeekBar,mSeekBar11,mSeekBar10,mSeekBar9,mSeekBar6,mSeekBar5,mSeekBar3;
    TextView mTxtSeekValue,mTxtSeekValue11,mTxtSeekValue10,mTxtSeekValue9,mTxtSeekValue6,mTxtSeekValue5,mTxtSeekValue3;
    ImageView mImgViewLed,mImgViewLed11,mImgViewLed10,mImgViewLed9,mImgViewLed6,mImgViewLed5,mImgViewLed3;
    SwitchCompat sw;

    //anolog=max=0-255===1,13,15R
//servo=0-180====2,13,15R set seek to mid that is mid 90
//3,0,0R to read anolog values a0;value
    //chk alpha for low android version
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pwm_control, container, false);
        sw= (SwitchCompat) view.findViewById(R.id.swanologservo);
        //seek bar
        mSeekBar= (SeekBar) view.findViewById(R.id.seekBar13);
        mSeekBar.setMax(255);
        mSeekBar.setOnSeekBarChangeListener(this);
        mSeekBar11= (SeekBar) view.findViewById(R.id.seekBar11);
        mSeekBar11.setMax(255);
        mSeekBar11.setOnSeekBarChangeListener(this);
        mSeekBar10= (SeekBar) view.findViewById(R.id.seekBar10);
        mSeekBar10.setMax(255);
        mSeekBar10.setOnSeekBarChangeListener(this);
        mSeekBar9= (SeekBar) view.findViewById(R.id.seekBar9);
        mSeekBar9.setMax(255);
        mSeekBar9.setOnSeekBarChangeListener(this);
        mSeekBar6= (SeekBar) view.findViewById(R.id.seekBar6);
        mSeekBar6.setMax(255);
        mSeekBar6.setOnSeekBarChangeListener(this);
        mSeekBar5= (SeekBar) view.findViewById(R.id.seekBar5);
        mSeekBar5.setMax(255);
        mSeekBar5.setOnSeekBarChangeListener(this);
        mSeekBar3= (SeekBar) view.findViewById(R.id.seekBar3);
        mSeekBar3.setMax(255);
        mSeekBar3.setOnSeekBarChangeListener(this);

        //txt value of seek bar
        mTxtSeekValue= (TextView) view.findViewById(R.id.txt13value);
        mTxtSeekValue11= (TextView) view.findViewById(R.id.txt11value);
        mTxtSeekValue10= (TextView) view.findViewById(R.id.txt10value);
        mTxtSeekValue9= (TextView) view.findViewById(R.id.txt9value);
        mTxtSeekValue6= (TextView) view.findViewById(R.id.txt6value);
        mTxtSeekValue5= (TextView) view.findViewById(R.id.txt5value);
        mTxtSeekValue3= (TextView) view.findViewById(R.id.txt3value);

        //img view
        mImgViewLed= (ImageView) view.findViewById(R.id.img13);
        mImgViewLed.setAlpha((float) 0.1);
        mImgViewLed11= (ImageView) view.findViewById(R.id.img11);
        mImgViewLed11.setAlpha((float) 0.1);
        mImgViewLed10= (ImageView) view.findViewById(R.id.img10);
        mImgViewLed10.setAlpha((float) 0.1);
        mImgViewLed9= (ImageView) view.findViewById(R.id.img9);
        mImgViewLed9.setAlpha((float) 0.1);
        mImgViewLed6= (ImageView) view.findViewById(R.id.img6);
        mImgViewLed6.setAlpha((float) 0.1);
        mImgViewLed5= (ImageView) view.findViewById(R.id.img5);
        mImgViewLed5.setAlpha((float) 0.1);
        mImgViewLed3= (ImageView) view.findViewById(R.id.img3);
        mImgViewLed3.setAlpha((float) 0.1);



        context=getActivity();

        sw.setOnCheckedChangeListener(this);

        return  view;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        switch (seekBar.getId()){
            case R.id.seekBar13:
                updateConnectionState(mImgViewLed,mTxtSeekValue,String.valueOf(progress));

                break;
            case R.id.seekBar11:
                updateConnectionState(mImgViewLed11,mTxtSeekValue11,String.valueOf(progress));

                break;
            case R.id.seekBar10:
                updateConnectionState(mImgViewLed10,mTxtSeekValue10,String.valueOf(progress));
                break;
            case R.id.seekBar9:
                updateConnectionState(mImgViewLed9,mTxtSeekValue9,String.valueOf(progress));
                break;
            case R.id.seekBar6:
                updateConnectionState(mImgViewLed6,mTxtSeekValue6,String.valueOf(progress));
                break;
            case R.id.seekBar5:
                updateConnectionState(mImgViewLed5,mTxtSeekValue5,String.valueOf(progress));
                break;
            case R.id.seekBar3:
                updateConnectionState(mImgViewLed3,mTxtSeekValue3,String.valueOf(progress));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    //set status of connection using UI Thread
    private void updateConnectionState(final ImageView img, final TextView txtview,final String progressval) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((Selector)getActivity()).writeData("1,13,"+String.valueOf(progressval)+"R"+"");
                img.setAlpha((float) (Float.parseFloat(String.valueOf(progressval))/255.0));
                txtview.setText(String.valueOf(progressval));
            }

        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.swanologservo:
                if (isChecked) {
                    mSeekBar.setMax(180);
                    mSeekBar11.setMax(180);
                    mSeekBar10.setMax(180);
                    mSeekBar9.setMax(180);
                    mSeekBar6.setMax(180);
                    mSeekBar5.setMax(180);
                    mSeekBar3.setMax(180);

                    mSeekBar.setProgress(90);
                    mSeekBar11.setProgress(90);
                    mSeekBar10.setProgress(90);
                    mSeekBar9.setProgress(90);
                    mSeekBar6.setProgress(90);
                    mSeekBar5.setProgress(90);
                    mSeekBar3.setProgress(90);


                } else {
                    mSeekBar.setMax(255);
                    mSeekBar11.setMax(255);
                    mSeekBar10.setMax(255);
                    mSeekBar9.setMax(255);
                    mSeekBar6.setMax(255);
                    mSeekBar5.setMax(255);
                    mSeekBar3.setMax(255);


                    mSeekBar.setProgress(0);
                    mSeekBar11.setProgress(0);
                    mSeekBar10.setProgress(0);
                    mSeekBar9.setProgress(0);
                    mSeekBar6.setProgress(0);
                    mSeekBar5.setProgress(0);
                    mSeekBar3.setProgress(0);
                }
                break;
        }
    }
}
