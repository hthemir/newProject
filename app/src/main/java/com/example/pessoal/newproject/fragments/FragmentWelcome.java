package com.example.pessoal.newproject.fragments;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pessoal.newproject.MainActivity;
import com.example.pessoal.newproject.R;

/**
 * Created by ZUP on 25/09/2017.
 */

public class FragmentWelcome extends Fragment {
    public static final long DEFAULT_ANIMATION_DURATION = 2500L;
    protected View mFrameLayout;
    protected TextView mTextViewWelcome;
    protected float mStartSize = 0;
    protected float mEndSize = 24;
    protected float mScreenHeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();
        mFrameLayout = v.findViewById(R.id.container);
        mTextViewWelcome = (TextView) v.findViewById(R.id.textViewWelcome);

        mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEndAnimation();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenHeight = displayMetrics.heightPixels/(displayMetrics.ydpi/DisplayMetrics.DENSITY_DEFAULT);

        onStartAnimation();
    }

    public static Fragment newInstance() {
        return new FragmentWelcome();
    }

    protected void onStartAnimation() {
        /*AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.slide_bottom_to_center);
        set.setTarget(mTextViewWelcome);
        set.start();*/

        ValueAnimator animatorSize = ValueAnimator.ofFloat(mStartSize, mEndSize);
        animatorSize.setDuration(DEFAULT_ANIMATION_DURATION);
        animatorSize.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                mTextViewWelcome.setTextSize(animatedValue);
            }
        });

        ValueAnimator animatorPosition = ValueAnimator.ofFloat(mScreenHeight, 0);
        animatorPosition.setDuration(DEFAULT_ANIMATION_DURATION);
        animatorPosition.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                mTextViewWelcome.setTranslationY(animatedValue);
            }
        });

        AnimatorSet animatorAll = new AnimatorSet();
        animatorAll.playTogether(animatorPosition, animatorSize);
        animatorAll.start();
    }

    protected void onEndAnimation() {
        ValueAnimator animatorSize = ValueAnimator.ofFloat(mEndSize, mStartSize);
        animatorSize.setDuration(DEFAULT_ANIMATION_DURATION);
        animatorSize.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                mTextViewWelcome.setTextSize(animatedValue);
            }
        });

        ValueAnimator animatorPosition = ValueAnimator.ofFloat(0, -mScreenHeight);
        animatorPosition.setDuration(DEFAULT_ANIMATION_DURATION);
        animatorPosition.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                mTextViewWelcome.setTranslationY(animatedValue);
            }
        });

        AnimatorSet animatorAll = new AnimatorSet();
        animatorAll.playTogether(animatorPosition, animatorSize);
        animatorAll.start();
        animatorAll.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((MainActivity)getActivity()).replaceFragment(FragmentNewNote.newInstance(), "tag", true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


}
