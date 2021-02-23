package com.ebs.hydrokleen.Fragment.bottombarfragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebs.hydrokleen.R;
import com.ebs.hydrokleen.activity.LoginActivity;
import com.ebs.hydrokleen.databinding.FragmentSignOutBinding;
import com.ebs.hydrokleen.utils.GlobalVariables;

import static android.content.Context.MODE_PRIVATE;
import static com.ebs.hydrokleen.utils.GlobalVariables.teamId;
import static com.ebs.hydrokleen.utils.GlobalVariables.teamPassword;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignOutFragment extends Fragment {

    private FragmentSignOutBinding binding;

    public SignOutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sign_out,container,false);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(GlobalVariables.sharedPref, MODE_PRIVATE);
        String teamId = sharedPreferences.getString(GlobalVariables.teamId, "");
        binding.signOutTeamIdTv.setText(teamId);


        binding.signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO SIGNOUT
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(GlobalVariables.sharedPref, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.remove(GlobalVariables.teamId);
                editor.remove(GlobalVariables.teamPassword);
                editor.remove(GlobalVariables.teamObject);
                editor.remove(GlobalVariables.technicianArrayList);
                editor.putBoolean(GlobalVariables.savedDataStatus, false);

                editor.apply();
                getActivity().finish();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });



        return binding.getRoot();
    }
}
