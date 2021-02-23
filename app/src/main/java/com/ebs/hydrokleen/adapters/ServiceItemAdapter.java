package com.ebs.hydrokleen.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ebs.hydrokleen.R;
import com.ebs.hydrokleen.activity.AcImageUploadActivity;
import com.ebs.hydrokleen.activity.UpdateAcInfoActivity;
import com.ebs.hydrokleen.models.Serviceitem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ServiceItemAdapter extends RecyclerView.Adapter<ServiceItemAdapter.ViewHolder> {

    private ArrayList<Serviceitem> serviceitemArrayList;
    private final ClickListener listener;



    public ServiceItemAdapter(ArrayList<Serviceitem> serviceitemArrayList,ClickListener listener) {
        this.serviceitemArrayList = serviceitemArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_service_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Serviceitem currentService = serviceitemArrayList.get(position);
        holder.itemId.setText(currentService.getServiceitemid());
        holder.acdetails.setText(currentService.getAcdetails());
        holder.serviceDetails.setText(currentService.getServicedetails());
        holder.billAmountl.setText(currentService.getBilledamount());

        if (serviceitemArrayList.get(position).getStatuscode().equals("3")){
            holder.startBtn.setVisibility(View.GONE);
        }

        if (serviceitemArrayList.get(position).getStatuscode().equals("5") || serviceitemArrayList.get(position).getStatuscode().equals("8")){
            holder.startBtn.setVisibility(View.GONE);
            holder.cancelBtn.setVisibility(View.GONE);
            holder.completeBtn.setVisibility(View.GONE);
        }

        if (serviceitemArrayList.get(position).getStatuscode().equals("0")){
            holder.completeBtn.setVisibility(View.GONE);
        }


    }

    public interface ClickListener {

        void OnCompleteClicked(int position);
        void OnCancelClicked(int position);
        void OnStartClicked(int position);
        void OnRemarkClicked(int position);

    }

    @Override
    public int getItemCount() {
        return serviceitemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView itemId,acdetails,serviceDetails,billAmountl;
        private CardView cancelBtn,remarksBtn, completeBtn,cameraBtn,startBtn;
        private WeakReference<ClickListener> listenerRef;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            listenerRef = new WeakReference<>(listener); // Click Listener Ref

            itemId = itemView.findViewById(R.id.service_item_id_running);
            acdetails = itemView.findViewById(R.id.service_ac_details_running);
            serviceDetails = itemView.findViewById(R.id.service_service_details_running);
            billAmountl = itemView.findViewById(R.id.service_bill_amount_running);
            cancelBtn = itemView.findViewById(R.id.cancel_btn);
            remarksBtn = itemView.findViewById(R.id.remarks_btn);
            completeBtn = itemView.findViewById(R.id.complete_btn);
            cameraBtn = itemView.findViewById(R.id.camera_btn);
            startBtn = itemView.findViewById(R.id.start_item_btn);

            cancelBtn.setOnClickListener(this);
            remarksBtn.setOnClickListener(this);
            completeBtn.setOnClickListener(this);
            cameraBtn.setOnClickListener(this);
            startBtn.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (v.getId() == cancelBtn.getId()){
                listenerRef.get().OnCancelClicked(getAdapterPosition());
            } else if (v.getId() ==remarksBtn.getId()){

                listenerRef.get().OnRemarkClicked(getAdapterPosition());

                /*Intent intent = new Intent(v.getContext(), UpdateAcInfoActivity.class);
                intent.putExtra("client code",serviceitemArrayList.get(getAdapterPosition()).getClientaccode());
                v.getContext().startActivity(intent);*/


            }
            else if (v.getId() == completeBtn.getId()){

                listenerRef.get().OnCompleteClicked(getAdapterPosition());
                //completeBtn.setVisibility(View.INVISIBLE);

            }
            else if (v.getId() == cameraBtn.getId()){

                Intent intent = new Intent(v.getContext(), AcImageUploadActivity.class);
                intent.putExtra("service item",serviceitemArrayList.get(getAdapterPosition()));
                v.getContext().startActivity(intent);

            }
            else if (v.getId() == startBtn.getId()){
                listenerRef.get().OnStartClicked(getAdapterPosition());
            }

        }
    }
}
