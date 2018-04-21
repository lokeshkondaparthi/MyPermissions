package com.vivenns.mypermissions.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.vivenns.mypermissions.R;
import com.vivenns.mypermissions.interfaces.PermissionActivities;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PermissionManager {
    private Context context;
    PermissionActivities permissionActivities;


    public PermissionManager(Context context) {
        this.context = context;
    }

    /**
     * Asks permission which don't have enabled, return enabled permissions
     * @param requestSetOfPermissions All permissions which wants to ask permissions as well as get
     *                                accepting permissions instantly.
     * @param permissionActivities callbacks.
     * @return permissions which are accepted.
     */
    public List<String> makePermissions(String[] requestSetOfPermissions,
                                                    PermissionActivities permissionActivities) {
        this.permissionActivities = permissionActivities;
        List<String> grantedPermissionsList = new ArrayList<>();
        List<String> notGrantedPermissionsList  = new ArrayList<>();
        for (int permission = 0; permission < requestSetOfPermissions.length; permission++) {
            if (ActivityCompat.checkSelfPermission(context, requestSetOfPermissions[permission])
                    == PackageManager.PERMISSION_GRANTED) {
                // permission granted!
                grantedPermissionsList.add(requestSetOfPermissions[permission]);
            } else {
                notGrantedPermissionsList.add(requestSetOfPermissions[permission]);
            }
        }
        requestPermissions(notGrantedPermissionsList);
        return grantedPermissionsList;
    }

    public void requestPermissions(List<String> requestPermissionList) {
        for (int i = 1; i <= requestPermissionList.size(); i++) {
            ActivityCompat.requestPermissions((Activity) context,
                    requestPermissionList.toArray(new String[requestPermissionList.size()]), i);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        List<String> permissionGrantedList = new ArrayList<>();
        List<String>  permissionNotGrantedList = new ArrayList<>();
        if (permissions.length > 0) {
            permissionGrantedList = new ArrayList<>();
            permissionNotGrantedList = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                if( grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    permissionGrantedList.add(permissions[i]);
                }else{
                    permissionNotGrantedList.add(permissions[i]);
                }
            }
        }
        permissionActivities.resultPermissions(permissionGrantedList.toArray(new String[permissionGrantedList.size()]),
                 permissionNotGrantedList.toArray(new String[permissionNotGrantedList.size()]));

    }

}
