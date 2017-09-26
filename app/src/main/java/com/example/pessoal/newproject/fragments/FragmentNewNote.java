package com.example.pessoal.newproject.fragments;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pessoal.newproject.R;
import com.example.pessoal.newproject.base.MainFragment;

/**
 * Created by ZUP on 25/09/2017.
 */

public class FragmentNewNote extends Fragment {
    protected View mFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_note, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        View v = getView();
        mFrameLayout = v.findViewById(R.id.container);

        onStartAnimation();
    }

    public static FragmentNewNote newInstance(){
        return new FragmentNewNote();
    }

    protected void onStartAnimation(){
        AnimatorSet fadeInAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity().getApplicationContext(), R.animator.fade_in);
        fadeInAnimator.setTarget(mFrameLayout);
        fadeInAnimator.start();
    }


}
