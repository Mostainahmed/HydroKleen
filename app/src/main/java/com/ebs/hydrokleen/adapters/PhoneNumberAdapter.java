package com.ebs.hydrokleen.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ebs.hydrokleen.R;
import com.ebs.hydrokleen.utils.DateTimeHelper;

import java.util.ArrayList;

/**
 * Created by Amit on 02,April,2020
 * kundu.amit517@gmail.com
 */
public class PhoneNumberAdapter extends RecyclerView.Adapter<PhoneNumberAdapter.ViewHolder> {

    private ArrayList<String> phoneNumbersArrayList;
    private Context context;

    public PhoneNumberAdapter(ArrayList<String> phoneNumbersArrayList, Context context) {
        this.phoneNumbersArrayList = phoneNumbersArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_phone_number,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.phoneNumber.append(phoneNumbersArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return phoneNumbersArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView phoneNumber;
        ImageButton clientCallBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            phoneNumber = itemView.findViewById(R.id.client_number);
            clientCallBtn = itemView.findViewById(R.id.client_call_btn);
        }
    }

}
