package com.example.pessoal.newproject;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pessoal.newproject.fragments.FragmentNewNote;
import com.example.pessoal.newproject.fragments.FragmentWelcome;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        replaceFragment(FragmentWelcome.newInstance(),"tag", false);
    }

    public void replaceFragment(final Fragment fragment, final String tag, final boolean addToBackStack) {
        if (addToBackStack) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.loginContent, fragment, tag)
                    .addToBackStack(tag)
                    .commitAllowingStateLoss();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.loginContent, fragment, tag)
                    .commitAllowingStateLoss();
        }
    }
}
