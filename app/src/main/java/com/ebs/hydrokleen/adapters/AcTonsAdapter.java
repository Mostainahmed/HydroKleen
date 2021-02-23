package com.ebs.hydrokleen.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ebs.hydrokleen.R;
import com.ebs.hydrokleen.models.Ton;

import java.util.ArrayList;
import java.util.List;

public class AcTonsAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    ArrayList<Ton> tons;

    public AcTonsAdapter(Context context, ArrayList<Ton> tons) {
        this.context = context;
        this.tons = tons;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tons.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.layout_ac_tons, null);
        TextView acTonTv = convertView.findViewById(R.id.ac_ton);
        //TextView acBtuTv = convertView.findViewById(R.id.ac_btu);

        acTonTv.setText(tons.get(position).getTon());
        //acBtuTv.setText(tons.get(position).getBtu());
        return convertView;
    }
}
