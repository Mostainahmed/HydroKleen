package com.ebs.hydrokleen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ebs.hydrokleen.R;
import com.ebs.hydrokleen.adapters.PhoneNumberAdapter;
import com.ebs.hydrokleen.databinding.ActivityClientDetailBinding;
import com.ebs.hydrokleen.models.DeviceItem;
import com.ebs.hydrokleen.models.StatusUpdate;
import com.ebs.hydrokleen.models.ServiceWorkList;
import com.ebs.hydrokleen.models.WorkType;
import com.ebs.hydrokleen.networkutils.AlertHelper;
import com.ebs.hydrokleen.networkutils.RetrofitClient;
import com.ebs.hydrokleen.networkutils.StatusUpdateCallBack;
import com.ebs.hydrokleen.networkutils.StatusUpdateNetworkCall;
import com.ebs.hydrokleen.utils.AlertDialogHelper;
import com.ebs.hydrokleen.utils.CustomAlertDialog;
import com.ebs.hydrokleen.utils.GPSTracker;
import com.ebs.hydrokleen.utils.GlobalVariables;
import com.ebs.hydrokleen.utils.LoadingDialog;
import com.ebs.hydrokleen.utils.NetworkCheck;
import com.ebs.hydrokleen.utils.RecyclerTouchListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientDetailActivity extends AppCompatActivity implements CustomAlertDialog.NoticeDialogListener{

    public ActivityClientDetailBinding binding;
    private LoadingDialog loadingDialog;
    private Context context = ClientDetailActivity.this;
    private String clientName;
    private String serviceid;
    private String teamID;
    private DeviceItem deviceItem;
    private String lat, lng;
    private Location location;
    private DialogFragment dialog = new CustomAlertDialog();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_client_detail);


        loadingDialog = new LoadingDialog(this);

        //taking teamid from sharedpreferences
        GlobalVariables globalVariables = new GlobalVariables();
        teamID = globalVariables.getTeamID(context);

        fetchClientDetails();
        getDeviceInfo();

        binding.swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(NetworkCheck.isNetworkAvailable(getApplicationContext())){

                    fetchClientDetails();
                    binding.swipeRefresh.setRefreshing(false);

                }else{
                    binding.swipeRefresh.setRefreshing(false);
                    Toast.makeText(ClientDetailActivity.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.startClientDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getLocation()){

                    if(NetworkCheck.isNetworkAvailable(getApplicationContext())){

                        AlertDialogHelper.updateService("activity",context, "Start", "You are about to start the job.", "start?", new AlertHelper() {
                            @Override
                            public void alert(boolean code, String comment) {
                                Log.d("TAG", "alert: "+comment);

                                if (code){

                                    final String statusCode = "3";
                                    final String status = "On Process";
                                    final String remarks = comment;

                                    loadingDialog.startLoadingDialog();

                                    new Thread(new Runnable() {
                                        public void run() {
                                            // a potentially time consuming task
                                            StatusUpdateNetworkCall.changeServiceStatus(lat, lng,statusCode, status, remarks, ClientDetailActivity.this, serviceid, new StatusUpdateCallBack() {
                                                @Override
                                                public void OnResult(boolean code) {

                                                    if (code){
                                                        loadingDialog.dismissDialog();
                                                        executeActivityTransition();
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
                        Toast.makeText(ClientDetailActivity.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(context, "Location Not Ready", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getLocation()){
                    if(NetworkCheck.isNetworkAvailable(getApplicationContext())){

                        AlertDialogHelper.updateService("activity",context, "Cancel!", "You are about to cancel the job.", "cancel?", new AlertHelper() {
                            @Override
                            public void alert(boolean code, String comment) {
                                Log.d("TAG", "alert: "+comment);

                                if (code){

                                    final String statusCode = "5";
                                    final String status = "Canceled";
                                    final String remarks = comment;
                                    loadingDialog.startLoadingDialog();

                                    new Thread(new Runnable() {
                                        public void run() {
                                            // a potentially time consuming task
                                            StatusUpdateNetworkCall.changeServiceStatus(lat, lng, statusCode, status, remarks, ClientDetailActivity.this, serviceid, new StatusUpdateCallBack() {
                                                @Override
                                                public void OnResult(boolean code) {

                                                    if (code){
                                                        loadingDialog.dismissDialog();
                                                        Toast.makeText(context, "Job Cancelled", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                        startActivity(getIntent());
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
                        Toast.makeText(ClientDetailActivity.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(context, "Location Not Ready", Toast.LENGTH_SHORT).show();
                }
    }
});

        binding.continueJobCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(NetworkCheck.isNetworkAvailable(getApplicationContext())){

                    executeActivityTransition();

                }else{
                    Toast.makeText(ClientDetailActivity.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getDeviceInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(GlobalVariables.sharedPref, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(GlobalVariables.mobileInfo, "");
        Gson gson = new Gson();
        deviceItem = gson.fromJson(json, DeviceItem.class);
    }

    private void fetchClientDetails(){
        if(getLocation()){
            if(NetworkCheck.isNetworkAvailable(getApplicationContext())){

                loadingDialog.startLoadingDialog(); // Added Loading Screen

                Call<ArrayList<WorkType>> call = RetrofitClient
                        .getInstance()
                        .getRetrofitApi()
                        .worklist(teamID,lat,lng);

                call.enqueue(new Callback<ArrayList<WorkType>>() {
                    @Override
                    public void onResponse(Call<ArrayList<WorkType>> call, Response<ArrayList<WorkType>> response) {
                        try{
                            if(response.code() == 200){
                                loadingDialog.dismissDialog();
                                ArrayList<WorkType> workTypeArrayList = response.body();
                                //Log.d("TAG", "onResponse: "+workTypeArrayList.get(1).getWorkList().get(0).getClientname());
                                assert workTypeArrayList != null;
                                bindDataToView(workTypeArrayList);

                            } else {
                                loadingDialog.dismissDialog();
                                Toast.makeText(ClientDetailActivity.this, "Response Code is no 200", Toast.LENGTH_SHORT).show();
                            }

                        }catch(Exception e){
                            loadingDialog.dismissDialog();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<WorkType>> call, Throwable t) {
                        Toast.makeText(ClientDetailActivity.this, "Something Went wrong! Please try again later!", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismissDialog();
                    }

                });
            }else{
                showNoticeDialog();
                //Toast.makeText(ClientDetailActivity.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
            }
        }else{
            showNoticeDialog();
        }

    }

    private void bindDataToView(ArrayList<WorkType> workTypeArrayList){

        String type = workTypeArrayList.get(1).getType();
        WorkType workType = workTypeArrayList.get(1);
        if (workType.getWorkList().size() !=0){

            ServiceWorkList currentServiceWork = workType.getWorkList().get(0);

            // This is the current running job.
            // Checking if it's a on going job or not

            if (currentServiceWork.getOrderstatuscode().equals("3")){
                // A job is on going. So will show continue button
                binding.startClientDetailsBtn.setVisibility(View.GONE);
                binding.continueJobCv.setVisibility(View.VISIBLE);

            }

            // Membership view manipulation

            if (currentServiceWork.getIsvip().equals("1") || !currentServiceWork.getMembership().equals("0")){
                binding.clientMembershipTv.setVisibility(View.VISIBLE);
            }
            if (currentServiceWork.getMembership().equals("1")){
                binding.memberSilver.setVisibility(View.VISIBLE);
            }
            if (currentServiceWork.getMembership().equals("2")){
                binding.memberGold.setVisibility(View.VISIBLE);
            }

            String typeCap = type.substring(0, 1).toUpperCase() + type.substring(1);
            String slot = currentServiceWork.getSlot();
            String slotCap = slot.substring(0, 1).toUpperCase() + slot.substring(1);

            clientName = currentServiceWork.getClientname();
            serviceid = currentServiceWork.getServiceid();
            String clientLocation = currentServiceWork.getAddress();
            List<String> clientPhonesList = currentServiceWork.getPhone(); //Phone array

            binding.clientNameClientDetailsTv.setText(clientName);
            binding.clientLocationClientDetailsTv.setText(clientLocation);
            binding.currentWorkTypeClientDetailsTv.setText(typeCap);
            binding.currentWorkSlotTv.setText(slotCap);
            configurePhoneRV(clientPhonesList);


        }
        else {
            loadingDialog.dismissDialog();
            Toast.makeText(context, "You have completed all you jobs", Toast.LENGTH_SHORT).show();

            //No job available to do. ALl jobs has been completed
            //Taking him back to Dashboard
            onBackPressed();
            ClientDetailActivity.this.finish();
        }
    }

    private void configurePhoneRV(final List<String> clientPhonesList) {

        PhoneNumberAdapter adapter = new PhoneNumberAdapter((ArrayList<String>) clientPhonesList,this);
        binding.phoneRv.setLayoutManager(new LinearLayoutManager(this));
        binding.phoneRv.setAdapter(adapter);

        binding.phoneRv.addOnItemTouchListener(new RecyclerTouchListener(this, binding.phoneRv, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //String phoneNumber = clientPhonesList.get(position);
                String number = "tel:"+ clientPhonesList.get(position);
                Intent call = new Intent (Intent.ACTION_DIAL, Uri.parse(number));
                startActivity(call);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void executeActivityTransition() {
        Intent intent = new Intent(ClientDetailActivity.this, RunningServiceActivity.class);
        intent.putExtra("name",clientName); // Must
        intent.putExtra("serviceId",serviceid); // Must
        startActivity(intent);
        ClientDetailActivity.this.finish();
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

    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    @Override
    public void onDialogRefreshClick(DialogFragment dialog) {
        fetchClientDetails();
    }

    @Override
    public void onCancelListener(DialogFragment dialog) {
        dialog.dismiss();
    }
}
