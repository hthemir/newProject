package com.example.pessoal.newproject.fragments;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pessoal.newproject.R;
import com.example.pessoal.newproject.activities.ActivityHome;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by ZUP on 25/09/2017.
 */

public class FragmentWelcome extends Fragment {
    @BindView(R.id.container) View mFrameLayout;
    @BindView(R.id.textViewWelcome) TextView mTextViewWelcome;

    protected AnimatorSet animatorAll;
    protected boolean firstClick;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        animatorAll = new AnimatorSet();
        firstClick = true;

        onStartAnimation();
    }

    public static FragmentWelcome newInstance() {
        return new FragmentWelcome();
    }

    @OnClick(R.id.container)
    public void frameLayoutClick() {
        if (firstClick) {
            onEndAnimation();
            firstClick = false;
        } else {
            animatorAll.cancel();
        }
    }

    protected void onStartAnimation() {
        AnimatorSet setScale = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.zoom_in);
        setScale.setTarget(mTextViewWelcome);
        AnimatorSet setTranslation = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.translate_bottom_to_center);
        setTranslation.setTarget(mTextViewWelcome);

        animatorAll.playTogether(setScale, setTranslation);
        animatorAll.start();
    }

    protected void onEndAnimation() {
        AnimatorSet setScale = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.zoom_out);
        setScale.setTarget(mTextViewWelcome);
        AnimatorSet setTranslation = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.translate_center_to_top);
        setTranslation.setTarget(mTextViewWelcome);

        animatorAll.playTogether(setScale, setTranslation);
        animatorAll.start();

        animatorAll.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((ActivityHome)getActivity()).replaceFragment(FragmentNewNote.newInstance(), "tag", true);
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
    }
}
