package com.ebs.hydrokleen.Fragment.bottombarfragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ebs.hydrokleen.R;

import com.ebs.hydrokleen.activity.ClientDetailActivity;
import com.ebs.hydrokleen.adapters.TechnicianAdapter;
import com.ebs.hydrokleen.databinding.FragmentDashBoardBinding;
import com.ebs.hydrokleen.models.DeviceItem;
import com.ebs.hydrokleen.models.Team;
import com.ebs.hydrokleen.models.Technician;
import com.ebs.hydrokleen.networkutils.RetrofitClient;
import com.ebs.hydrokleen.networkutils.StatusUpdateCallBack;
import com.ebs.hydrokleen.networkutils.StatusUpdateNetworkCall;
import com.ebs.hydrokleen.utils.CustomAlertDialog;
import com.ebs.hydrokleen.utils.GPSTracker;
import com.ebs.hydrokleen.utils.GlobalVariables;
import com.ebs.hydrokleen.utils.LoadingDialog;
import com.ebs.hydrokleen.utils.NetworkCheck;
import com.ebs.hydrokleen.utils.RecyclerTouchListener;
import com.google.gson.Gson;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends Fragment implements CustomAlertDialog.NoticeDialogListener {

    private FragmentDashBoardBinding binding;
    private Context context;
    private ArrayList<Technician> technicianArrayList;
    private TechnicianAdapter technicianAdapter;
    private DeviceItem deviceItem;
    public String latitude = " ", lontitude = " ";
    private String teamId;
    private LoadingDialog loadingDialog;
    private String phone;
    Location location;
    private DialogFragment dialog = new CustomAlertDialog();




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context =context;
    }

    public DashBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();
        fetchTeamDetails();
    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        fetchTeamDetails();
//    }

//    @Override
//    public void onPause(){
//        super.onPause();
//        fetchTeamDetails();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_dash_board,container,false);

        init();
        getDeviceInfo();

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(NetworkCheck.isNetworkAvailable(context)){
                    fetchTeamDetails();
                    binding.swipeRefresh.setRefreshing(false);

                }else{
                    binding.swipeRefresh.setRefreshing(false);
                    Toast.makeText(context, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        binding.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(NetworkCheck.isNetworkAvailable(context)){
                    startActivity(new Intent(getContext(), ClientDetailActivity.class));
                }else{
                    Toast.makeText(context, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone!=null){
                    String number = "tel:"+phone;
                    Intent call = new Intent (Intent.ACTION_DIAL, Uri.parse(number));
                    startActivity(call);
                }else{
                    Toast.makeText(context, "Number is not available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.startDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(NetworkCheck.isNetworkAvailable(context)){
                    loadingDialog.startLoadingDialog();
                    final String statusCode = "1";
                    new Thread(new Runnable() {
                        public void run() {
                            // a potentially time consuming task
                            StatusUpdateNetworkCall.teamStatusUpdate(latitude, lontitude, statusCode, getActivity(), new StatusUpdateCallBack() {
                                @Override
                                public void OnResult(boolean code) {
                                    if (code){
                                        Toast.makeText(context, "You have successfully started your day", Toast.LENGTH_SHORT).show();
                                        binding.startDay.setVisibility(View.GONE);
                                        loadingDialog.dismissDialog();
                                        fetchTeamDetails();
                                    }
                                    else {
                                        loadingDialog.dismissDialog();
                                    }
                                }
                            });
                        }
                    }).start();

                }else{
                    Toast.makeText(context, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.endDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkCheck.isNetworkAvailable(context)){
                    loadingDialog.startLoadingDialog();
                    final String statusCode = "4";

                    new Thread(new Runnable() {
                        public void run() {
                            // a potentially time consuming task
                            StatusUpdateNetworkCall.teamStatusUpdate(latitude, lontitude, statusCode, getActivity(), new StatusUpdateCallBack() {
                                @Override
                                public void OnResult(boolean code) {

                                    if (code){
                                        Toast.makeText(context, "You have successfully complete your day.\nThanks for your service", Toast.LENGTH_SHORT).show();
                                        loadingDialog.dismissDialog();
                                        fetchTeamDetails();
                                    }
                                    else {
                                        loadingDialog.dismissDialog();
                                    }

                                }
                            });
                        }
                    }).start();

                }else{
                    Toast.makeText(context, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.startLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(NetworkCheck.isNetworkAvailable(context)){

                    loadingDialog.startLoadingDialog();
                    final String statusCode = "5";
                    new Thread(new Runnable() {
                        public void run() {
                            // a potentially time consuming task
                            StatusUpdateNetworkCall.teamStatusUpdate(latitude, lontitude, statusCode, getActivity(), new StatusUpdateCallBack() {
                                @Override
                                public void OnResult(boolean code) {

                                    if (code){
                                        Toast.makeText(context, "Lunch break started", Toast.LENGTH_SHORT).show();
                                        binding.startLunch.setVisibility(View.GONE);
                                        loadingDialog.dismissDialog();
                                        fetchTeamDetails();
                                    }
                                    else {
                                        loadingDialog.dismissDialog();
                                    }
                                }
                            });
                        }
                    }).start();

                }else{
                    Toast.makeText(context, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        binding.endLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getLocationFinal()){
                    if(NetworkCheck.isNetworkAvailable(context)){

                        loadingDialog.startLoadingDialog();
                        final String statusCode = "6";
                        new Thread(new Runnable() {
                            public void run() {
                                // a potentially time consuming task
                                StatusUpdateNetworkCall.teamStatusUpdate(latitude,lontitude,statusCode, getActivity(), new StatusUpdateCallBack() {
                                    @Override
                                    public void OnResult(boolean code) {

                                        if (code){
                                            Toast.makeText(context, "Lunch break finished", Toast.LENGTH_SHORT).show();
                                            binding.endLunch.setVisibility(View.GONE);
                                            loadingDialog.dismissDialog();
                                            fetchTeamDetails();
                                        }
                                        else {
                                            loadingDialog.dismissDialog();
                                        }
                                    }
                                });
                            }
                        }).start();

                    }else{
                        Toast.makeText(context, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(context, "Location Not Ready", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.wellDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "All of today's work has been Completed", Toast.LENGTH_LONG).show();
            }
        });

        return binding.getRoot();
    }

    private void init() {
        GlobalVariables globalVariables = new GlobalVariables();
        teamId = globalVariables.getTeamID(context);
        loadingDialog = new LoadingDialog(getActivity());

        binding.swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark),
                getResources().getColor(R.color.kelly_green));
    }

    private void getDeviceInfo() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariables.sharedPref, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(GlobalVariables.mobileInfo, "");
        Gson gson = new Gson();
        deviceItem = gson.fromJson(json, DeviceItem.class);
    }


    private void fetchTeamDetails() {

        if(getLocationFinal()){
            if(NetworkCheck.isNetworkAvailable(context)){
                loadingDialog.startLoadingDialog();
                new Thread(new Runnable() {
                    public void run() {

                        Call<Team> call = RetrofitClient
                                .getInstance()
                                .getRetrofitApi()
                                .dashboardInfo(teamId,deviceItem.deviceId,deviceItem.imsi,deviceItem.imei1,
                                        deviceItem.imei2,deviceItem.softwareVersion,deviceItem.simSerialNumber,
                                        deviceItem.brand,deviceItem.model,deviceItem.operator,deviceItem.operatorName,
                                        deviceItem.release,deviceItem.sdkVersion,deviceItem.versionCode, latitude, lontitude);

                        call.enqueue(new Callback<Team>() {
                            @Override
                            public void onResponse(Call<Team> call, Response<Team> response) {
                                try{
                                    if(response.code() == 200){
                                        Log.d("TAG", "onResponse: "+response.body().getStatus().getResponseCode());
                                        assert response.body() != null;
                                        if(response.body().getStatus().getResponseCode().equals("1")){
                                            loadingDialog.dismissDialog();
                                            Team team = response.body();
                                            dataBindIntoView(team);
                                        }
                                        else{
                                            loadingDialog.dismissDialog();
                                            Toast.makeText(getContext(), "Something Went wrong! Please try again later!", Toast.LENGTH_LONG).show();

                                        }
                                    }
                                    else {
                                        loadingDialog.dismissDialog();
                                        Toast.makeText(getContext(), "Something Went wrong! Please try again later!", Toast.LENGTH_SHORT).show();

                                    }

                                }catch(Exception e){
                                    loadingDialog.dismissDialog();
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<Team> call, Throwable t) {
                                loadingDialog.dismissDialog();
                                Toast.makeText(getContext(), "Something Went wrong! Please try again later!", Toast.LENGTH_SHORT).show();
                            }

                        });
                    }
                }).start();

            }else{
                showNoticeDialog();
            }
        }else{
            showNoticeDialog();
        }
    }

    private void dataBindIntoView(Team team) {

        //String teamId = team.getData().getTeamid();
        String teamName = team.getData().getTeamname();
        String vehicleModel = team.getData().getVehiclemodel();
        String vehicleRegNo = team.getData().getVehicleregno();
        //String driverId = team.getData().getDriverid();
        String driverName = team.getData().getDrivername();
        phone = team.getData().getPhone();

        String totalWork = String.valueOf(team.getData().getWorkqueue().getTotal());
        String pendingWork = String.valueOf(team.getData().getWorkqueue().getPending());
        String cancelledWork = String.valueOf(team.getData().getWorkqueue().getCancelled());
        String doneWork = String.valueOf(team.getData().getWorkqueue().getDone());

        //String status = String.valueOf(team.getData().getStatus());
        //String statusCode = String.valueOf(team.getData().getStatuscode());
        String startedWorking = String.valueOf(team.getData().getStartedworking());
        String finishedWorking = String.valueOf(team.getData().getFinishedworking());
        String tookLunchBreak = String.valueOf(team.getData().getTooklunchbreak());
        String finishedLunchBreak = String.valueOf(team.getData().getFinishedlunchbreak());


        if (startedWorking.equals("1")){

            binding.totalCount.setVisibility(View.VISIBLE);
            binding.startBtn.setVisibility(View.VISIBLE);
            binding.startLunch.setVisibility(View.VISIBLE);
            binding.startDay.setVisibility(View.GONE);

            binding.totalWorkTv.setText(totalWork);
            binding.cancelledWorkTv.setText(cancelledWork);
            binding.totalCompletedWorkTv.setText(doneWork);
            binding.pendingWorkTv.setText(pendingWork);
        }

        if (tookLunchBreak.equals("1")){
            binding.startLunch.setVisibility(View.GONE);
            binding.endLunch.setVisibility(View.VISIBLE);
            binding.startBtn.setVisibility(View.GONE);
        }

        if (finishedLunchBreak.equals("1")){
            binding.endLunch.setVisibility(View.GONE);
            binding.startBtn.setVisibility(View.VISIBLE);
        }
        if (pendingWork.equals("0")){
            binding.startBtn.setVisibility(View.GONE);
            binding.endDay.setVisibility(View.VISIBLE);
        }

        if (finishedWorking.equals("1") && pendingWork.equals("0")){
            Log.d("TAG", "dataBindIntoView: "+finishedWorking);
            binding.wellDone.setVisibility(View.VISIBLE);
            binding.endDay.setVisibility(View.GONE);
            binding.startLunch.setVisibility(View.GONE);
        }
        else {
            binding.wellDone.setVisibility(View.GONE);
        }

        if (Integer.parseInt(totalWork)>Integer.parseInt(pendingWork)){
            binding.startBtn.setText("Continue");
        }
        else {
            binding.startBtn.setText("Start");
        }

        binding.teamIdTv.setText(teamName);
        binding.teamVehicleTv.setText(vehicleModel);
        binding.driverNameTv.setText(driverName);
        binding.driverNoTv.setText(phone);
        binding.teamVehicleRegTv.setText(vehicleRegNo);
        technicianArrayList = (ArrayList<Technician>) team.getData().getTechnicians();

        Log.d("TAG", "dataBindingInView: "+teamName);
        setRecyclerView();
    }


    private void setRecyclerView(){
        binding.technicianListRv.setLayoutManager(new LinearLayoutManager(context));
        technicianAdapter = new TechnicianAdapter(context, technicianArrayList);
        binding.technicianListRv.setAdapter(technicianAdapter);

        binding.technicianListRv.addOnItemTouchListener(new RecyclerTouchListener(getContext(), binding.technicianListRv, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String number = "tel:"+ technicianArrayList.get(position).getPhoneNumber();
                Intent call = new Intent (Intent.ACTION_DIAL, Uri.parse(number));
                startActivity(call);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        technicianAdapter.notifyDataSetChanged();
    }


    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        dialog.show(getChildFragmentManager(), "NoticeDialogFragment");
    }

    public boolean getLocationFinal(){
        GPSTracker gps = new GPSTracker(getContext());
        location = gps.getLocation();
        if(location!=null){
            latitude = String.valueOf(location.getLatitude());
            lontitude = String.valueOf(location.getLongitude());
            return true;
        }
        else{
            latitude = " ";
            lontitude = " ";
            return true;
        }
    }

    @Override
    public void onDialogRefreshClick(DialogFragment dialog) {
        fetchTeamDetails();
    }
    @Override
    public void onCancelListener(DialogFragment dialog){
        dialog.dismiss();
    }
}
