package com.ebs.hydrokleen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.ebs.hydrokleen.R;
import com.ebs.hydrokleen.Fragment.bottombarfragments.DashBoardFragment;
import com.ebs.hydrokleen.Fragment.bottombarfragments.SignOutFragment;
import com.ebs.hydrokleen.databinding.ActivityMainBinding;
import com.ebs.hydrokleen.utils.CustomAlertDialog;
import com.fxn.OnBubbleClickListener;

public class MainActivity extends AppCompatActivity implements CustomAlertDialog.NoticeDialogListener {

   public ActivityMainBinding binding;
    private Fragment contentFragment;
    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Request to show full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        pushFragment(new DashBoardFragment(),"dashboard");


        binding.bottomNavBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                Log.d("ERR", "onBubbleClick: "+i);

                switch (i){

                    case R.id.home:
                        pushFragment(new DashBoardFragment(),"dashboard");
                        break;
                        case R.id.account:
                        pushFragment(new SignOutFragment(), "signout");
                        break;
                    default:
                        // Nothing
                }

            }
        });

    }

    protected void pushFragment(Fragment fragment, String tag) {
        if (fragment == null)
            return;
        contentFragment = fragment;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frame_layout, fragment,tag);
        ft.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        doubleBackToExitPressedOnce = true;
    }

    @Override
    public void onBackPressed() {


        final FragmentManager fm = getSupportFragmentManager();

        if (contentFragment instanceof SignOutFragment){
            pushFragment(new DashBoardFragment(),"dashboard");
            binding.bottomNavBar.setSelected(0,true);
        }

        else if (fm.getBackStackEntryCount() > 0) {
            super.onBackPressed();
            if(fm.getBackStackEntryCount() != 0){
                return;
            }

        } else if (contentFragment instanceof DashBoardFragment || fm.getBackStackEntryCount() == 0) {

            if (doubleBackToExitPressedOnce) {
                this.doubleBackToExitPressedOnce = false;
                Toast.makeText(this,"Press again to exit", Toast.LENGTH_SHORT).show();
            } else {
                finish();
            }
        }
    }

    @Override
    public void onDialogRefreshClick(DialogFragment dialog) {
        pushFragment(new DashBoardFragment(), "dashboard");
    }

    @Override
    public void onCancelListener(DialogFragment dialog) {

    }
}
