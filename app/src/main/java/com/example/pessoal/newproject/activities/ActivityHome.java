package com.example.pessoal.newproject.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.pessoal.newproject.R;
import com.example.pessoal.newproject.base.MainMVP;
import com.example.pessoal.newproject.fragments.FragmentNewNote;
import com.example.pessoal.newproject.fragments.FragmentWelcome;

/**
 * Created by ZUP on 26/09/2017.
 */

public class ActivityHome extends AppCompatActivity implements MainMVP.RequiredActivityOperations {

    private boolean justOpennedApplication = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (justOpennedApplication) {
            replaceFragment(FragmentWelcome.newInstance(), "tag", true);
            justOpennedApplication = false;
        } else
            replaceFragment(FragmentNewNote.newInstance(), "tag", true);
    }

    @Override
    public void replaceFragment(final Fragment fragment, final String tag, final boolean addToBackStack) {
        if (addToBackStack) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.homeContent, fragment, tag)
                    .addToBackStack(tag)
                    .commitAllowingStateLoss();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.homeContent, fragment, tag)
                    .commitAllowingStateLoss();
        }
    }
}
