package com.vivenns.mypermissions;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vivenns.mypermissions.interfaces.PermissionActivities;
import com.vivenns.mypermissions.utility.PermissionManager;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    private Button mBT;
    private PermissionManager mPermissionManager;
    private String[] mMyPermissionCheckList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mBT = findViewById(R.id.second_button);
        mMyPermissionCheckList = new String[4];
        settingPermissions();
        mBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPermissionManager = new PermissionManager(SecondActivity.this);
                List<String> alreadyGrantedPermissionsList = mPermissionManager.makePermissions(mMyPermissionCheckList, new PermissionActivities() {
                    @Override
                    public void resultPermissions(String[] grantedPermissions, String[] permissionsNotGranted) {
                        for (int i = 0; i < grantedPermissions.length; i++) {
                            showToast("Temp Permission Granted for" + grantedPermissions[i]);
                        }
                    }
                });
                if (alreadyGrantedPermissionsList.size() > 0) {
                    for (int i = 0; i < alreadyGrantedPermissionsList.size(); i++) {
                        showToast("Already Granted permission " + alreadyGrantedPermissionsList.get(i));
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void settingPermissions() {
        mMyPermissionCheckList[0] = Manifest.permission.RECORD_AUDIO;
        mMyPermissionCheckList[1] = Manifest.permission.READ_SMS;
        mMyPermissionCheckList[2] = Manifest.permission.ACCESS_FINE_LOCATION;
        mMyPermissionCheckList[3] = Manifest.permission.READ_CONTACTS;
    }
}
