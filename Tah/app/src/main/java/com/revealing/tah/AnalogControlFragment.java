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
    NumberProgressBar pinA0, pinA1, pinA2, pinA3, pinA4, pinA5;
    public static boolean anolgfragment = true;
    Context context;
    String[] value;
    ArrayList<String> arrayList;
    Button showdata;
    int methodcall = 0;

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
        showdata = (Button) view.findViewById(R.id.showdata);

//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        pinA0.incrementProgressBy(1);
//                        counter++;
//                        if (counter == 110) {
//                            pinA0.setProgress(0);
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
                splitdata();

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
        methodcall++;
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

    public void splitdata() {

        ((Selector) getActivity()).writeData("AT+NAMEios", false);
        try {
            Thread.sleep(1000);
            ((Selector) getActivity()).writeData("AT+RESET", false);
        } catch (InterruptedException ie) {
            //Handle exception
        }

        //"AT+RESET"

        //AT+TYPE0  to open security
        //AT+TYPE2 to secure

        //AT+PASS6digit


//"A0:234\r\n\n41 30 3A 32 34 33 0D 0A"
//        System.out.print("====methodcall====" + methodcall);
//        String data[] = arrayList.get(0).split("\r");
//        System.out.print("====index 0 data====" + data[0]);
//        System.out.print("====index 1 data====" + data[1]);
//        String subspit[] = data[0].split(":");
//        System.out.print("====subsplit index 0 data====" + subspit[0]);
//        System.out.print("====subsplit index 1 data====" + subspit[1]);




//        System.out.print("====arrayListara   size====" + arrayList.size());
//        for (int i = 0; i < arrayList.size(); i++) {
//            String data[] = arrayList.get(i).split("\r");
//
//            String subspit[] = data[0].split(":");
//            System.out.print("====subsplit index 0 data====" + subspit[0]);
//            System.out.print("====subsplit index 1 data====" + subspit[1]);
//            pinA0.setProgress(Integer.parseInt(subspit[1]));
//        }

    }

}
