package com.revealing.tah;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by shail on 19/03/15.
 */
public class PwmControlFragment extends Fragment{

    Context context;
    SeekBar mSeekBar;
    TextView mTxtSeekValue;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pwm_control, container, false);
        mSeekBar= (SeekBar) view.findViewById(R.id.seekBar);
        mSeekBar.setMax(255);
        mTxtSeekValue= (TextView) view.findViewById(R.id.txtseekbar);
        context=getActivity();

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            //anolog=max=0-255===1,13,15R
            //servo=0-180====2,13,15R set seek to mid that is   mid 90
            //3,0,0R to read anolog values a0;value

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
           ((Selector)getActivity()).writeData("1,13,"+String.valueOf(progress)+"R"+"");

            mTxtSeekValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return  view;
    }
}
