package com.example.pessoal.newproject.fragments;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pessoal.newproject.R;
import com.example.pessoal.newproject.base.MainMVP;
import com.example.pessoal.newproject.base.StateMaintainer;
import com.example.pessoal.newproject.presenter.NotePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZUP on 25/09/2017.
 */

public class FragmentNewNote extends Fragment implements MainMVP.RequiredViewOperations{
    protected final String TAG = getClass().getSimpleName();

    private StateMaintainer mStateMaintainer;
    private MainMVP.PresenterOperations mPresenter;

    @BindView(R.id.container)
    View mFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_note, container, false);
        ButterKnife.bind(this,view);
        mStateMaintainer = new StateMaintainer(getActivity().getFragmentManager(), TAG);
        startMVPOperations();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        onStartAnimation();
    }

    public static FragmentNewNote newInstance(){
        return new FragmentNewNote();
    }

    public void startMVPOperations() {
        try {
            if (mStateMaintainer.firstTimeIn()) {
                Log.d(TAG, "first time calling onCreate()");
                initialize(this);
            } else {
                Log.d(TAG, "not first time calling onCreate()");
                reinitialize(this);
            }
        } catch (Exception e) {
            Log.d(TAG, "onCreate() error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void initialize(MainMVP.RequiredViewOperations view) throws java.lang.InstantiationException, IllegalAccessException {
        mPresenter = new NotePresenter(view);
        mStateMaintainer.put(MainMVP.PresenterOperations.class.getSimpleName(), mPresenter);
    }

    private void reinitialize(MainMVP.RequiredViewOperations view) throws java.lang.InstantiationException, IllegalAccessException {
        mPresenter = mStateMaintainer.get(MainMVP.PresenterOperations.class.getSimpleName());
        if (mPresenter == null) {
            Log.w(TAG, "creating the presenter again for it was lost");
            initialize(view);
        } else {
            mPresenter.onConfigurationChanged(view);
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAlert(String message) {

    }

    protected void onStartAnimation(){
        AnimatorSet fadeInAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity().getApplicationContext(), R.animator.fade_in);
        fadeInAnimator.setTarget(mFrameLayout);
        fadeInAnimator.start();
    }

}
