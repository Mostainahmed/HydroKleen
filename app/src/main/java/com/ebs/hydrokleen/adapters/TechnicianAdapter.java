package com.ebs.hydrokleen.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ebs.hydrokleen.R;
import com.ebs.hydrokleen.models.Technician;

import java.util.ArrayList;
import java.util.List;

public class TechnicianAdapter extends RecyclerView.Adapter<TechnicianAdapter.TechnicianViewHolder>{

    private Context mCtx;
    private List<Technician> technicianList;

    public TechnicianAdapter(Context mCtx, List<Technician> technicianList) {
        this.mCtx = mCtx;
        this.technicianList = technicianList;
    }


    @NonNull
    @Override
    public TechnicianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.technician_layout, null);
        return new TechnicianViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TechnicianViewHolder holder, int position) {
        Technician technician = technicianList.get(position);

        holder.technicianName.setText(technician.getName());
        holder.teamDesignation.setText(technician.getDesignation());

    }

    @Override
    public int getItemCount() {
        return technicianList.size();
    }

    public class TechnicianViewHolder extends RecyclerView.ViewHolder{

        TextView technicianName, teamDesignation;

        public TechnicianViewHolder(@NonNull View itemView) {
            super(itemView);

            technicianName = itemView.findViewById(R.id.tech_name_tv);
            teamDesignation = itemView.findViewById(R.id.tech_designation_tv);
        }
    }
}
