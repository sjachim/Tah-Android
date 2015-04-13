package com.revealing.tah;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import bleservice.BluetoothLeService;
import util.Constant;
import util.NumberProgressBar;

/**
 * Created by shail on 25/03/15.
 */
public class AnalogControlFragment extends Fragment {
    NumberProgressBar pinA0, pinA1, pinA2, pinA3, pinA4, pinA5;
    public static boolean anolgfragment = true;
    Context context;
    ArrayList<String> arrayList;
    HashMap<String, Integer> demo = new HashMap<String, Integer>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.analog_cantrol_fragment, container, false);
        context = getActivity();
        arrayList = new ArrayList<String>();

        pinA0 = (NumberProgressBar) view.findViewById(R.id.anologpina0);
        pinA1 = (NumberProgressBar) view.findViewById(R.id.anologpina1);
        pinA2 = (NumberProgressBar) view.findViewById(R.id.anologpina2);
        pinA3 = (NumberProgressBar) view.findViewById(R.id.anologpina3);
        pinA4 = (NumberProgressBar) view.findViewById(R.id.anologpina4);
        pinA5 = (NumberProgressBar) view.findViewById(R.id.anologpina5);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Selector) getActivity()).writeDataRes("3,0,0R", true);
        anolgfragment = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        anolgfragment = false;
    }

    @Override
    public void onStop() {
        super.onStop();
        anolgfragment = false;


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        anolgfragment = false;

    }

    public void splitdata(final ArrayList<String> arraydata) {
      
            Thread t = new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < arraydata.size(); i++) {
                        try {
                            String subspit[] = arraydata.get(i).split(":");
                            demo.put(subspit[0].toString(), Integer.parseInt(subspit[1]));
                            subspit = null;
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        //data = null;
                    }

                    arraydata.clear();
                    uiUpdate(demo);
                    try {

                        Thread.sleep(1000);
                        ((Selector) getActivity()).writeDataRes("3,0,0R", true);

                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            t.start();
    }

    public void uiUpdate(final HashMap<String, Integer> valueSet) {

        if (!valueSet.isEmpty()) {
            Selector.activity.runOnUiThread(new Runnable() {
                @Override

                public void run() {
                    try {

                        pinA0.setProgress(valueSet.get("A0"));
                        pinA1.setProgress(valueSet.get("A1"));
                        pinA2.setProgress(valueSet.get("A2"));
                        pinA3.setProgress(valueSet.get("A3"));
                        pinA4.setProgress(valueSet.get("A4"));
                        pinA5.setProgress(valueSet.get("A5"));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }


}
