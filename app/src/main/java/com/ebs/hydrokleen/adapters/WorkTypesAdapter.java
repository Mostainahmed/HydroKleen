package com.ebs.hydrokleen.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ebs.hydrokleen.R;
import com.ebs.hydrokleen.models.MaintainWorkList;
import com.ebs.hydrokleen.models.ServiceWorkList;
import com.ebs.hydrokleen.models.SurveyWorkList;
import com.ebs.hydrokleen.models.WorkType;

import java.util.ArrayList;
import java.util.List;

public class WorkTypesAdapter extends RecyclerView.Adapter<WorkTypesAdapter.ViewHolder> {

    private Context mCtx;
    private List<WorkType> workTypeList;
    private final static int survey = 1;
    private final static int service = 2;
    private final static int maintain = 3;

    public WorkTypesAdapter(Context mCtx, List<WorkType> modelServiceList) {
        this.mCtx = mCtx;
        this.workTypeList = modelServiceList;
    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_worktype, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        }







    @Override
    public int getItemCount() {
        return workTypeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView workType, workTypeCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            workType = itemView.findViewById(R.id.work_type_tv);
            workTypeCount = itemView.findViewById(R.id.work_type_count_tv);
        }
    }
}
