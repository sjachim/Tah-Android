package in.revealinghour.tah;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.CompoundButton;
import android.widget.ImageView;

import java.util.Timer;

import bleservice.BluetoothLeService;

/**
 * Created by shail on 12/03/15.
 */
public class IoCantrolFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    @Nullable
    Context context;
    SwitchCompat mSwD13, mSwD12, mSwD11, mSwD10, mSwD9, mSwD8, mSwD7, mSwD6, mSwD5, mSwD4, mSwD3, mSwD2;
    ImageView blueLed, ledPin13;
    private Timer timer;
    boolean onOff = true;
    public static boolean screenVisible = true;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.iocantrol, container, false);
        blueLed = (ImageView) view.findViewById(R.id.imgblueled);
        ledPin13 = (ImageView) view.findViewById(R.id.m13led);
        mSwD13 = (SwitchCompat) view.findViewById(R.id.swd13);
        mSwD13.setOnCheckedChangeListener(this);
        mSwD12 = (SwitchCompat) view.findViewById(R.id.swd12);
        mSwD12.setOnCheckedChangeListener(this);
        mSwD11 = (SwitchCompat) view.findViewById(R.id.swd11);
        mSwD11.setOnCheckedChangeListener(this);
        mSwD10 = (SwitchCompat) view.findViewById(R.id.swd10);
        mSwD10.setOnCheckedChangeListener(this);
        mSwD9 = (SwitchCompat) view.findViewById(R.id.swd9);
        mSwD9.setOnCheckedChangeListener(this);
        mSwD8 = (SwitchCompat) view.findViewById(R.id.swd8);
        mSwD8.setOnCheckedChangeListener(this);
        mSwD7 = (SwitchCompat) view.findViewById(R.id.swd7);
        mSwD7.setOnCheckedChangeListener(this);
        mSwD6 = (SwitchCompat) view.findViewById(R.id.swd6);
        mSwD6.setOnCheckedChangeListener(this);
        mSwD5 = (SwitchCompat) view.findViewById(R.id.swd5);
        mSwD5.setOnCheckedChangeListener(this);
        mSwD4 = (SwitchCompat) view.findViewById(R.id.swd4);
        mSwD4.setOnCheckedChangeListener(this);
        mSwD3 = (SwitchCompat) view.findViewById(R.id.swd3);
        mSwD3.setOnCheckedChangeListener(this);
        mSwD2 = (SwitchCompat) view.findViewById(R.id.swd2);
        mSwD2.setOnCheckedChangeListener(this);

        ledOnOff(true);
        context = getActivity();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        screenVisible = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        screenVisible = false;
    }

    @Override
    public void onStop() {
        super.onStop();
        screenVisible = false;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        screenVisible = false;
        try {
            if (timer != null) {
                timer.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.swd13:

                if (isChecked) {
                    ((Selector) getActivity()).writeData("0,13,1R", false);
                    ledPin13.setVisibility(View.VISIBLE);
                } else {
                    ((Selector) getActivity()).writeData("0,13,0R", false);
                    ledPin13.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.swd12:
                if (isChecked) {
                    ((Selector) getActivity()).writeData("0,12,1R", false);
                } else {
                    ((Selector) getActivity()).writeData("0,12,0R", false);
                }
                break;
            case R.id.swd11:
                if (isChecked) {
                    ((Selector) getActivity()).writeData("0,11,1R", false);
                } else {
                    ((Selector) getActivity()).writeData("0,11,0R", false);
                }
                break;
            case R.id.swd10:
                if (isChecked) {
                    ((Selector) getActivity()).writeData("0,10,1R", false);
                } else {
                    ((Selector) getActivity()).writeData("0,10,0R", false);
                }
                break;
            case R.id.swd9:
                if (isChecked) {
                    ((Selector) getActivity()).writeData("0,9,1R", false);
                } else {
                    ((Selector) getActivity()).writeData("0,9,0R", false);
                }
                break;
            case R.id.swd8:
                if (isChecked) {
                    ((Selector) getActivity()).writeData("0,8,1R", false);
                } else {
                    ((Selector) getActivity()).writeData("0,8,0R", false);
                }
                break;
            case R.id.swd7:
                if (isChecked) {
                    ((Selector) getActivity()).writeData("0,7,1R", false);
                } else {
                    ((Selector) getActivity()).writeData("0,7,0R", false);
                }
                break;
            case R.id.swd6:
                if (isChecked) {
                    ((Selector) getActivity()).writeData("0,6,1R", false);
                } else {
                    ((Selector) getActivity()).writeData("0,6,0R", false);
                }
                break;
            case R.id.swd5:
                if (isChecked) {
                    ((Selector) getActivity()).writeData("0,5,1R", false);
                } else {
                    ((Selector) getActivity()).writeData("0,5,0R", false);
                }
                break;
            case R.id.swd4:
                if (isChecked) {
                    ((Selector) getActivity()).writeData("0,4,1R", false);
                } else {
                    ((Selector) getActivity()).writeData("0,4,0R", false);
                }
                break;
            case R.id.swd3:
                if (isChecked) {
                    ((Selector) getActivity()).writeData("0,3,1R", false);
                } else {
                    ((Selector) getActivity()).writeData("0,3,0R", false);
                }
                break;
            case R.id.swd2:
                if (isChecked) {
                    ((Selector) getActivity()).writeData("0,2,1R", false);
                } else {
                    ((Selector) getActivity()).writeData("0,2,0R", false);
                }
                break;


        }

    }

//led animation
    public void ledOnOff(final boolean start) {
        Animation animation = new AlphaAnimation(2, 0);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        if (start && BluetoothLeService.sleepWakeup) {
            blueLed.startAnimation(animation);
        } else {
            blueLed.clearAnimation();
            blueLed.setVisibility(View.VISIBLE);
            animation.reset();
        }
    }
}
