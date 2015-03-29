package com.revealing.tah;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import util.PreferenceHelper;

/**
 * Created by shail on 29/03/15.
 */
public class SettingFragment extends Fragment {
    EditText edtDeviceName, edtPassword;
    ToggleButton tgOpenSecure;
    Button btnUpdate;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment, container, false);
        context = getActivity();
        btnUpdate = (Button) view.findViewById(R.id.btnupdatesetting);
        tgOpenSecure = (ToggleButton) view.findViewById(R.id.swopensecure);
        edtPassword = (EditText) view.findViewById(R.id.edtdevicepass);
        edtDeviceName = (EditText) view.findViewById(R.id.edtdvicenam);
        edtDeviceName.setText(PreferenceHelper.getTahName(context));

        edtPassword.setEnabled(false);
//change toggle i.e open to secure.
        tgOpenSecure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edtPassword.setEnabled(true);
                   // edtPassword.setFocusable(true);
                    ((Selector) getActivity()).writeData("AT+TYPE2", false);

                } else {
                    edtPassword.setEnabled(false);
                    //edtPassword.setFocusable(false);
                    ((Selector) getActivity()).writeData("AT+TYPE0", false);

                }
            }
        });
//update button click listener
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tgOpenSecure.isChecked()) {
                    if (edtDeviceName.getText().toString() != null && !edtDeviceName.getText().toString().equals("")) {
                        String devicename = edtDeviceName.getText().toString();
                        updateSetting(devicename, "", false);
                    } else {
                        Toast.makeText(getActivity(), "Please add device name...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String pass = edtPassword.getText().toString();
                    String devicename = edtDeviceName.getText().toString();
                    if ((pass != null && !equals("")) && (pass.length() < 6)) {
                        Toast.makeText(getActivity(), "Please add 6 digit password...", Toast.LENGTH_SHORT).show();

                    } else {
                        if (devicename == null || devicename.equals("")) {

                            Toast.makeText(getActivity(), "Please add device name...", Toast.LENGTH_SHORT).show();
                        } else {
                            updateSetting(devicename, pass, true);

                        }
                    }
                }
            }
        });
        return view;
    }
//method to update settings
    public void updateSetting(String deviceName, String password, boolean passornaot) {

        if (passornaot) {
            ((Selector) getActivity()).writeData("AT+NAME" + deviceName, false);
            //1000 milliseconds is one second.
            try {
                Thread.sleep(100);
                ((Selector) getActivity()).writeData("AT+PASS"+password, false);
                Thread.sleep(100);
                ((Selector) getActivity()).writeData("AT+RESET", false);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        } else {
            ((Selector) getActivity()).writeData("AT+NAME" + deviceName, false);
            //1000 milliseconds is one second.
            try {
                Thread.sleep(100);
                ((Selector) getActivity()).writeData("AT+RESET", false);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        Toast.makeText(getActivity(), "Changes Updated...", Toast.LENGTH_SHORT).show();
       // getActivity().finish();
    }
}
