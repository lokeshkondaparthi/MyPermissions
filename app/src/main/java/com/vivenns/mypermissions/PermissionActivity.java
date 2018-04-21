package com.vivenns.mypermissions;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.vivenns.mypermissions.interfaces.PermissionActivities;
import com.vivenns.mypermissions.utility.PermissionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PermissionActivity extends AppCompatActivity {
    private PermissionManager mPermissionManager;
    private List<String> permissionsNeedToAccept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        mPermissionManager = new PermissionManager(this);
        permissionsNeedToAccept = new ArrayList<>();
        permissionsNeedToAccept.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissionsNeedToAccept.add(Manifest.permission.READ_CONTACTS);

        List<String> permissionsAlreadyGrantedList = mPermissionManager.makePermissions(permissionsNeedToAccept.toArray(new String[permissionsNeedToAccept.size()]), new PermissionActivities() {
            @Override
            public void resultPermissions(String[] permissionsGranted, String[] permissionsNotGranted) {
                Log.d("PermissionActivity", "resultPermissions: ");
            }
        });
        Log.d("PermissionActivity", "onCreate: "+permissionsAlreadyGrantedList.size());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
