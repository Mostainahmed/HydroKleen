package com.ebs.hydrokleen.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import com.ebs.hydrokleen.models.DeviceItem;
import com.ebs.hydrokleen.networkutils.AlertHelper;
import com.ebs.hydrokleen.networkutils.StatusUpdateCallBack;
import com.ebs.hydrokleen.networkutils.StatusUpdateNetworkCall;
import com.ebs.hydrokleen.utils.AlertDialogHelper;
import com.ebs.hydrokleen.utils.GPSTracker;
import com.ebs.hydrokleen.utils.GlobalVariables;
import com.ebs.hydrokleen.utils.LoadingDialog;
import com.ebs.hydrokleen.adapters.ServiceItemAdapter;
import com.ebs.hydrokleen.databinding.ActivityRunningServiceBinding;

import com.ebs.hydrokleen.models.ServiceDetails;
import com.ebs.hydrokleen.models.Serviceitem;
import com.ebs.hydrokleen.networkutils.RetrofitClient;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;

import android.view.View;
import android.widget.Toast;

import com.ebs.hydrokleen.R;
import com.ebs.hydrokleen.utils.NetworkCheck;
import com.google.gson.Gson;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RunningServiceActivity extends AppCompatActivity implements ServiceItemAdapter.ClickListener{

    private ActivityRunningServiceBinding binding;
    private Context context;
    private ArrayList<Serviceitem> serviceitemArrayList;
    private ServiceItemAdapter serviceItemAdapter;
    private LoadingDialog loadingDialog;
    private String serviceid;
    private String teamId;
    private String lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_running_service);

        init();
        fetchServiceDetails();
        Log.d("TAG", "onCreate: Service id " + serviceid);
        getLocation();

        binding.included.swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark), getResources().getColor(R.color.kelly_green));
        binding.included.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(NetworkCheck.isNetworkAvailable(getApplicationContext())){
                    binding.included.swipeRefresh.setRefreshing(false);
                    fetchServiceDetails();

                }else{
                    binding.included.swipeRefresh.setRefreshing(false);
                    Toast.makeText(RunningServiceActivity.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.included.completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getLocation()){
                    if(NetworkCheck.isNetworkAvailable(getApplicationContext())){

                        AlertDialogHelper.updateService( "activity",context, "Complete!", "You are about to complete the job.", "complete?", new AlertHelper() {
                            @Override
                            public void alert(boolean code, String comment) {

                                if (code){
                                    loadingDialog.startLoadingDialog();
                                    final String statusCode = "8";
                                    final String status = "Complete";
                                    final String remarks = comment;
                                    new Thread(new Runnable() {
                                        public void run() {
                                            // a potentially time consuming task
                                            StatusUpdateNetworkCall.changeServiceStatus(lat, lng, statusCode, status, remarks, RunningServiceActivity.this, serviceid, new StatusUpdateCallBack() {
                                                @Override
                                                public void OnResult(boolean code) {

                                                    if (code){

                                                        //Taking him back on the Client Details activity
                                                        loadingDialog.dismissDialog();
                                                        /*Intent intent = new Intent(RunningServiceActivity.this,ClientDetailActivity.class);
                                                        startActivity(intent);
                                                        RunningServiceActivity.this.finish();*/
                                                        onBackPressed();

                                                    }
                                                    else {
                                                        loadingDialog.dismissDialog();
                                                    }

                                                }
                                            });
                                        }
                                    }).start();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(RunningServiceActivity.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(context, "Location Not Ready", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.included.partiallyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getLocation()){
                    if(NetworkCheck.isNetworkAvailable(getApplicationContext())){

                        AlertDialogHelper.updateService("activity",context, "Partially Done!", "You are about to partially Done the job.", "partially Done?", new AlertHelper() {
                            @Override
                            public void alert(boolean code, String comment) {

                                if (code){
                                    loadingDialog.startLoadingDialog();
                                    final String statusCode = "4";
                                    final String status = "Partially Done";
                                    final String remarks = comment;
                                    new Thread(new Runnable() {
                                        public void run() {
                                            // a potentially time consuming task
                                            StatusUpdateNetworkCall.changeServiceStatus(lat, lng, statusCode, status, remarks, RunningServiceActivity.this, serviceid, new StatusUpdateCallBack() {
                                                @Override
                                                public void OnResult(boolean code) {

                                                    if (code){

                                                        //Taking him back on the Client Details activity

                                                        loadingDialog.dismissDialog();
                                                        /*Intent intent = new Intent(RunningServiceActivity.this,ClientDetailActivity.class);
                                                        startActivity(intent);
                                                        RunningServiceActivity.this.finish();*/
                                                        onBackPressed();

                                                    }
                                                    else {
                                                        loadingDialog.dismissDialog();
                                                    }

                                                }
                                            });
                                        }
                                    }).start();
                                }
                            }
                        });

                    }else{
                        Toast.makeText(RunningServiceActivity.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(context, "Location Not Ready", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.included.clientPhoneRunningActivityTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "tel:"+ binding.included.clientPhoneRunningActivityTV.getText().toString();
                Intent call = new Intent (Intent.ACTION_DIAL, Uri.parse(number));
                startActivity(call);
            }
        });

    }

    private boolean getLocation() {
        GPSTracker gps = new GPSTracker(getApplicationContext());
        Location location = gps.getLocation();
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


    private void fetchServiceDetails() {
        if (getLocation()){
            if(NetworkCheck.isNetworkAvailable(getApplicationContext())){

                loadingDialog.startLoadingDialog(); // Starting the Progress Dialog
                Call<ServiceDetails> call = RetrofitClient
                        .getInstance()
                        .getRetrofitApi()
                        .serviceDetails(serviceid,lat,lng);

                call.enqueue(new Callback<ServiceDetails>() {


                    @Override
                    public void onResponse(Call<ServiceDetails> call, Response<ServiceDetails> response) {

                        try {
                            if(response.code() == 200){

                                ServiceDetails currentService = response.body();
                                Log.d("TAG", "onResponse: "+currentService.getClientname());
                                bindDataOnView(currentService);
                            }
                            else {
                                loadingDialog.dismissDialog();
                                Log.d("TAG", "onResponse: "+response.code());
                                Toast.makeText(context, "Error Code: "+response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            loadingDialog.dismissDialog();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ServiceDetails> call, Throwable t) {
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                        loadingDialog.dismissDialog();

                    }

                    private void bindDataOnView(ServiceDetails currentService) {

                        if (currentService.getPending() == 0){
                            binding.included.partiallyBtn.setVisibility(View.GONE);
                            binding.included.completeBtn.setVisibility(View.VISIBLE);
                        }

                        if (currentService.getShowpartialbutton() == 1){
                            binding.included.partiallyBtn.setVisibility(View.VISIBLE);
                        }
                        else {
                            binding.included.partiallyBtn.setVisibility(View.GONE);
                        }


                        String totalAmmount = currentService.getTotalbillamount();
                        String phone = currentService.getPhone1();
                        String status = currentService.getStatus();
                        serviceitemArrayList = (ArrayList<Serviceitem>) currentService.getServiceitems();
                        binding.included.clientPhoneRunningActivityTV.setText("+"+phone);
                        binding.included.totalAmountRunningActivityTV.setText(totalAmmount);
                        binding.included.workStatusStringRunning.setText(status);

                        configureRecyclearView();
                        imageViewConfigure(status);
                        loadingDialog.dismissDialog(); // Dismissing the Progress Dialog

                    }

                    private void imageViewConfigure(String status) {

                        switch(status)
                        {
                            case "On Process":
                            case "Partially Done":
                                binding.included.statusImageBtn.setImageResource(R.drawable.status_blue);
                                break;
                            case "Canceled":
                            case "Refused Hydrokleen":
                                binding.included.statusImageBtn.setImageResource(R.drawable.status_red);
                                break;

                            case "Completed":
                                binding.included.statusImageBtn.setImageResource(R.drawable.status_green);
                                break;
                            case "Re-Scheduled":
                                binding.included.statusImageBtn.setImageResource(R.drawable.status_pest_colour);
                                break;
                            case "Complain Requested":
                                binding.included.statusImageBtn.setImageResource(R.drawable.status_brown);
                                break;
                            default: //Hold
                                binding.included.statusImageBtn.setImageResource(R.drawable.status_yellow);
                        }

                    }

                    private void configureRecyclearView() {

                        binding.included.serviceItemsRv.setLayoutManager(new LinearLayoutManager(RunningServiceActivity.this));
                        serviceItemAdapter = new ServiceItemAdapter(serviceitemArrayList,RunningServiceActivity.this);
                        binding.included.serviceItemsRv.setAdapter(serviceItemAdapter);
                        serviceItemAdapter.notifyDataSetChanged();
                    }
                });
            }else{
                Toast.makeText(RunningServiceActivity.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(context, "Location Not Ready", Toast.LENGTH_SHORT).show();
        }

    }

    private void init() {

        context = RunningServiceActivity.this;
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        serviceid = intent.getStringExtra("serviceId");


        GlobalVariables globalVariables = new GlobalVariables();
        teamId = globalVariables.getTeamID(context);
        Log.d("TAG", "init: "+teamId);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(name);

        loadingDialog = new LoadingDialog(RunningServiceActivity.this);
        serviceitemArrayList = new ArrayList<>();

    }


    @Override
    public void OnCompleteClicked(int position) {

        if (getLocation()){
            if(NetworkCheck.isNetworkAvailable(getApplicationContext())){

                final String statusCode = "8";
                final String serviceitemid = serviceitemArrayList.get(position).getServiceitemid();

                AlertDialogHelper.updateService("adapter", RunningServiceActivity.this, "Complete!", "You are about to complete the service.", "complete?", new AlertHelper() {
                    @Override
                    public void alert(boolean code, String comment) {

                        if (code){
                            loadingDialog.startLoadingDialog();

                            new Thread(new Runnable() {
                                public void run() {
                                    // a potentially time consuming task
                                    StatusUpdateNetworkCall.changeItemStatus(lat, lng, statusCode, serviceitemid, RunningServiceActivity.this, new StatusUpdateCallBack() {
                                        @Override
                                        public void OnResult(boolean code) {

                                            if (code){

                                                //TODO have to manipulate the view
                                                Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
                                                loadingDialog.dismissDialog();
                                                fetchServiceDetails();

                                            }
                                            else {
                                                loadingDialog.dismissDialog();
                                            }

                                        }
                                    });
                                }
                            }).start();

                        }
                    }
                });

            }else{
                Toast.makeText(RunningServiceActivity.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(context, "Location Not Ready", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void OnCancelClicked(int position) {

        if (getLocation()){
            if(NetworkCheck.isNetworkAvailable(getApplicationContext())){

                final String statusCode = "5";
                final String serviceitemid = serviceitemArrayList.get(position).getServiceitemid();

                AlertDialogHelper.updateService("adapter", RunningServiceActivity.this, "Cancel!", "You are about to cancel the service.", "cancel?", new AlertHelper() {
                    @Override
                    public void alert(boolean code, String comment) {

                        if (code){
                            loadingDialog.startLoadingDialog();

                            new Thread(new Runnable() {
                                public void run() {
                                    // a potentially time consuming task
                                    StatusUpdateNetworkCall.changeItemStatus(lat, lng, statusCode, serviceitemid, RunningServiceActivity.this, new StatusUpdateCallBack() {
                                        @Override
                                        public void OnResult(boolean code) {

                                            if (code){

                                                //TODO have to manipulate the view

                                                Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show();
                                                loadingDialog.dismissDialog();
                                                fetchServiceDetails();
                                            }
                                            else {
                                                loadingDialog.dismissDialog();
                                            }
                                        }
                                    });
                                }
                            }).start();
                        }
                    }
                });

            }else{
                Toast.makeText(RunningServiceActivity.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(context, "Location Not Ready", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnStartClicked(int position) {

        if (getLocation()){
            if(NetworkCheck.isNetworkAvailable(getApplicationContext())){
                final String statusCode = "3";
                final String serviceitemid = serviceitemArrayList.get(position).getServiceitemid();

                AlertDialogHelper.updateService("adapter", RunningServiceActivity.this, "Start!", "You are about to start the service.", "start?", new AlertHelper() {
                    @Override
                    public void alert(boolean code, String comment) {
                        loadingDialog.startLoadingDialog();
                        if (code){

                            new Thread(new Runnable() {
                                public void run() {
                                    // a potentially time consuming task
                                    StatusUpdateNetworkCall.changeItemStatus(lat,lng,statusCode, serviceitemid, RunningServiceActivity.this, new StatusUpdateCallBack() {
                                        @Override
                                        public void OnResult(boolean code) {

                                            if (code){

                                                //TODO have to manipulate the view

                                                Toast.makeText(context, "Started", Toast.LENGTH_SHORT).show();
                                                loadingDialog.dismissDialog();
                                                fetchServiceDetails();
                                            }
                                            else {
                                                loadingDialog.dismissDialog();
                                            }
                                        }
                                    });
                                }
                            }).start();
                        }
                    }
                });

            }else{
                Toast.makeText(RunningServiceActivity.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(context, "Location Not Ready", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnRemarkClicked(int position) {

        if(NetworkCheck.isNetworkAvailable(getApplicationContext())){
            Intent intent = new Intent(RunningServiceActivity.this, UpdateAcInfoActivity.class);
            intent.putExtra("client code",serviceitemArrayList.get(position).getClientaccode());
            startActivity(intent);
        }else{
            Toast.makeText(RunningServiceActivity.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
        }
    }
}
