package com.revealing.tah;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import util.Constant;
import util.NumberProgressBar;

/**
 * Created by shail on 25/03/15.
 */
public class AnalogControlFragment extends Fragment {
    private int counter = 0;
    private Timer timer;
    Button changeprogress;
    boolean chk = true;
    NumberProgressBar bnp;
    public static boolean anolgfragment = true;
    Context context;
    String[] value;
    ArrayList<String> arrayList;
    Button showdata;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.analog_cantrol_fragment, container, false);
//http://www.mopri.de/2010/timertask-bad-do-it-the-android-way-use-a-handler/
        context = getActivity();
        arrayList = new ArrayList<String>();
        bnp = (NumberProgressBar) view.findViewById(R.id.anologpina1);
showdata= (Button) view.findViewById(R.id.showdata);

//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        bnp.incrementProgressBy(1);
//                        counter++;
//                        if (counter == 110) {
//                            bnp.setProgress(0);
//                            counter = 0;
//
//                        }
//                    }
//                });
//            }
//        }, 1000, 100);

        ((Selector) getActivity()).writeData("3,0,0R", true);


//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//
//                ((Selector) getActivity()).writeData("3,0,0R", true);
//                //onArticleSelected("shail");
//
//            }
//        }, 1000, 2000);


        showdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //anolgfragment=true;
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

    public void onArticleSelected(final String data) {

        System.out.print("====recived data====" + data.toString());

        getActivity().runOnUiThread(new Runnable() {
            @Override

            public void run() {
                try {

                    arrayList.add(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


}
