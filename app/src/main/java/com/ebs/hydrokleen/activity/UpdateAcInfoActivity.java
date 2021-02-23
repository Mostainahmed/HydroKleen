package com.ebs.hydrokleen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.ebs.hydrokleen.R;
import com.ebs.hydrokleen.adapters.AcTonsAdapter;
import com.ebs.hydrokleen.databinding.ActivityRemarksBinding;
import com.ebs.hydrokleen.models.AcDetails;
import com.ebs.hydrokleen.models.DeviceItem;
import com.ebs.hydrokleen.models.Serviceitem;
import com.ebs.hydrokleen.models.Ton;
import com.ebs.hydrokleen.networkutils.AcInfoCallBack;
import com.ebs.hydrokleen.networkutils.StatusUpdateCallBack;
import com.ebs.hydrokleen.networkutils.StatusUpdateNetworkCall;
import com.ebs.hydrokleen.utils.GPSTracker;
import com.ebs.hydrokleen.utils.GlobalVariables;
import com.ebs.hydrokleen.utils.LoadingDialog;
import com.ebs.hydrokleen.utils.NetworkCheck;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UpdateAcInfoActivity extends AppCompatActivity {

    private ActivityRemarksBinding binding;
    private String acCode;
    private DeviceItem deviceItem;
    private Spinner brandSpinner;
    private LoadingDialog loadingDialog;
    private String lat, lng;
    private LocationManager locationManager;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_remarks);
        loadingDialog = new LoadingDialog(UpdateAcInfoActivity.this);
        getIntentData();
        getAcData();
        binding.swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(NetworkCheck.isNetworkAvailable(getApplicationContext())){

                    getAcData();
                    binding.swipeRefresh.setRefreshing(false);
                }else{
                    binding.swipeRefresh.setRefreshing(false);
                    Toast.makeText(UpdateAcInfoActivity.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        binding.updateAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (getLocation()){

                    if(NetworkCheck.isNetworkAvailable(UpdateAcInfoActivity.this)){

                        String newAcBrand = binding.acBrands.getSelectedItem().toString();
                        String newAcTon = binding.acTons.getSelectedItem().toString();
                        String newAcType = binding.acTypes.getSelectedItem().toString();
                        String newAcTech = binding.acTechs.getSelectedItem().toString();
                        String newInstallationDate = binding.acInstallationDataPicker.getText().toString();
                        String newExtraInfo = binding.extraInfo.getText().toString();

                        StatusUpdateNetworkCall.postAcInfo(lat,lng,acCode,newAcBrand, newAcTon, newAcType,
                                newAcTech, newInstallationDate, newExtraInfo, UpdateAcInfoActivity.this,
                                new StatusUpdateCallBack() { // Have to pass required value
                                    @Override
                                    public void OnResult(boolean code) {
                                        if (code){
                                            Toast.makeText(getApplicationContext(), "AC information updated successfully", Toast.LENGTH_SHORT).show();
                                        }
                                        else {

                                        }
                                    }
                                });

                    }else{
                        Toast.makeText(UpdateAcInfoActivity.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(UpdateAcInfoActivity.this, "Location Not Ready", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getAcData() {


        if (getLocation()){
            if(NetworkCheck.isNetworkAvailable(UpdateAcInfoActivity.this)){

                loadingDialog.startLoadingDialog();

                new Thread(new Runnable() {
                    public void run() {
                        // a potentially time consuming task
                        StatusUpdateNetworkCall.getAcInfo(lat,lng,acCode, UpdateAcInfoActivity.this, new AcInfoCallBack() {
                            Boolean code;

                            @Override
                            public void OnResult(boolean code) {
                                this.code = code;
                                if (!code){
                                    loadingDialog.dismissDialog();
                                }
                            }

                            @Override
                            public void AcDetails(AcDetails acDetails) {
                                if (code){

                                    Log.d("TAG", "AcDetails: "+acDetails.getData().getAcinfo().getClientaccode());
                                    Log.d("TAG", "User Brand: "+acDetails.getData().getAcinfo().getClientacbrand());

                                    //Getting User's Previous AC Information
                                    String userBrand = acDetails.getData().getAcinfo().getClientacbrand();
                                    String userAcTon = acDetails.getData().getAcinfo().getClientacton();
                                    String userAcType = acDetails.getData().getAcinfo().getClientactype();
                                    String userAcTech = acDetails.getData().getAcinfo().getClientacelectricity();
                                    String userInstallationDate = acDetails.getData().getAcinfo().getInstallationdate();
                                    String userExtraInfo = acDetails.getData().getAcinfo().getClientacextrainfo();

                                    //Brands Spinner
                                    ArrayList<String> brands = (ArrayList<String>) acDetails.getData().getBrands();
                                    ArrayAdapter<String> brandsAdapter = new ArrayAdapter<>(getApplicationContext(),
                                            android.R.layout.simple_spinner_dropdown_item, brands);
                                    binding.acBrands.setAdapter(brandsAdapter);
                                    if(userBrand!=null){
                                        int spinnerPosition = brands.indexOf(userBrand);
                                        binding.acBrands.setSelection(spinnerPosition);
                                    }

                                    //types Spinner
                                    ArrayList<String> types = (ArrayList<String>) acDetails.getData().getTypes();
                                    ArrayAdapter<String> typesAdapter = new ArrayAdapter<>(getApplicationContext(),
                                            android.R.layout.simple_spinner_dropdown_item, types);
                                    binding.acTypes.setAdapter(typesAdapter);
                                    if(userAcType!=null){
                                        int spinnerPosition = brands.indexOf(userAcType);
                                        binding.acTypes.setSelection(spinnerPosition);
                                    }

                                    //techs Spinner
                                    ArrayList<String> techs = (ArrayList<String>) acDetails.getData().getTechs();
                                    ArrayAdapter<String> techsAdapter = new ArrayAdapter<>(getApplicationContext(),
                                            android.R.layout.simple_spinner_dropdown_item, techs);
                                    binding.acTechs.setAdapter(techsAdapter);
                                    if(userAcTech!=null){
                                        int spinnerPosition = brands.indexOf(userAcTech);
                                        binding.acTechs.setSelection(spinnerPosition);
                                    }

                                    //AC Tons Spinner
                                    ArrayList<String> tons = (ArrayList<String>) acDetails.getData().getTons();
                                    ArrayAdapter<String> tonsAdapter = new ArrayAdapter<>(getApplicationContext(),
                                            android.R.layout.simple_spinner_dropdown_item, tons);
                                    binding.acTons.setAdapter(tonsAdapter);
                                    if(userAcTon!=null){
                                        int spinnerPosition = brands.indexOf(userAcTon);
                                        binding.acTons.setSelection(spinnerPosition);
                                    }

                                    binding.acInstallationDataPicker.setText(userInstallationDate);
                                    binding.extraInfo.setText(userExtraInfo);
                                    loadingDialog.dismissDialog();
                                    //setUserPreviousDataToView(userBrand, userAcTech, userAcTon, userAcType, userInstallationDate, userExtraInfo);
                                }
                            }
                        });
                    }
                }).start();

            }else{
                Toast.makeText(UpdateAcInfoActivity.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(UpdateAcInfoActivity.this, "Location Not Ready", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean getLocation(){
        GPSTracker gps = new GPSTracker(getApplicationContext());
        location = gps.getLocation();
        if(location!=null){
            lat = String.valueOf(location.getLatitude());
            lng = String.valueOf(location.getLongitude());
            return true;
        }else {
            lat = " ";
            lng = " ";
            return true;
        }
    }


    private void getIntentData() {
        Intent intent = getIntent();
        acCode = intent.getStringExtra("client code");
        Log.d("TAG", "getIntentData: "+acCode);
    }
}
