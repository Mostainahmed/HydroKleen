package com.ebs.hydrokleen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.ebs.hydrokleen.R;
import com.ebs.hydrokleen.databinding.ActivityTestLocationBinding;
import com.ebs.hydrokleen.utils.LatLngProvider;
import com.ebs.hydrokleen.utils.MyBackGroundService;
import com.ebs.hydrokleen.utils.SendLocationActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

public class TestLocationActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private ActivityTestLocationBinding binding;
    public MyBackGroundService mService = null;
    boolean mBound = false;
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            MyBackGroundService.LocalBinder binder = (MyBackGroundService.LocalBinder) iBinder;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_location);

        Dexter.withActivity(this)
                .withPermissions(Arrays.asList(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ))
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        binding.updateLocationBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mService.requestLocationUpdates();
                            }
                        });

                        binding.removeLocationUpdatesBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mService.removeLocationUpdates();
                            }
                        });

                        setButtonState(LatLngProvider.requestingLocationUpdates(TestLocationActivity.this));
                        bindService(new Intent(TestLocationActivity.this, MyBackGroundService.class),
                                mServiceConnection, Context.BIND_AUTO_CREATE);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }
                }).check();
    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onStop() {
        if(mBound){
            unbindService(mServiceConnection);
            mBound = false;
        }
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        EventBus.getDefault().unregister(this);

        super.onStop();

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(LatLngProvider.KEY_REQUESTING_LOCATION_UPDATES)){
            setButtonState(sharedPreferences.getBoolean(LatLngProvider.KEY_REQUESTING_LOCATION_UPDATES, false));
        }
    }

    private void setButtonState(boolean isRequestEnable) {
        if(isRequestEnable){
            binding.updateLocationBtn.setEnabled(false);
            binding.removeLocationUpdatesBtn.setEnabled(true);
        }else{
            binding.updateLocationBtn.setEnabled(true);
            binding.removeLocationUpdatesBtn.setEnabled(false);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onListenLocation(SendLocationActivity event)
    {
        if(event != null){
            String data = new StringBuilder()
                    .append(event.getLocation().getLatitude())
                    .append("/")
                    .append(event.getLocation().getLongitude())
                    .toString();
            Toast.makeText(mService, data, Toast.LENGTH_SHORT).show();
        }
    }

}
