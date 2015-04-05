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
        //http://www.mopri.de/2010/timertask-bad-do-it-the-android-way-use-a-handler/
        context = getActivity();
        arrayList = new ArrayList<String>();

        pinA0 = (NumberProgressBar) view.findViewById(R.id.anologpina0);
        pinA1 = (NumberProgressBar) view.findViewById(R.id.anologpina1);
        pinA2 = (NumberProgressBar) view.findViewById(R.id.anologpina2);
        pinA3 = (NumberProgressBar) view.findViewById(R.id.anologpina3);
        pinA4 = (NumberProgressBar) view.findViewById(R.id.anologpina4);
        pinA5 = (NumberProgressBar) view.findViewById(R.id.anologpina5);


        //((Selector) getActivity()).writeDataRes("3,0,0R", true);

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
        //t.stop();
        //timer.cancel();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        anolgfragment = false;
        //            t.stop();
        //       timer.cancel();
    }

    public void splitdata(final ArrayList<String> arraydata) {
        //"A0:234\r\n\n41 30 3A 32 34 33 0D 0A"
        System.out.println("====arrayListara   size====" + arraydata.size());

        try {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < arraydata.size(); i++) {
                        String data[] = arraydata.get(i).split("\r");
                        String subspit[] = data[0].split(":");
                        demo.put(subspit[0].toString(), Integer.parseInt(subspit[1]));
                        subspit = null;
                        data = null;
                    }

                    arraydata.clear();
                    uiUpdate(demo);
                    try {

                        Thread.sleep(1500);
                        ((Selector) getActivity()).writeDataRes("3,0,0R", true);

                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uiUpdate(final HashMap<String, Integer> valueSet) {
        System.out.println("====valueSet   size====" + valueSet.size());
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
