package com.example.pessoal.newproject.fragments;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pessoal.newproject.R;
import com.example.pessoal.newproject.base.MainMVP;
import com.example.pessoal.newproject.base.StateMaintainer;
import com.example.pessoal.newproject.model.Note;
import com.example.pessoal.newproject.presenter.NotePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZUP on 25/09/2017.
 */

public class FragmentNewNote extends Fragment implements MainMVP.RequiredViewOperations{
    protected final String TAG = getClass().getSimpleName();

    private StateMaintainer mStateMaintainer;
    private MainMVP.PresenterOperations mPresenter;

    @BindView(R.id.container) View mFrameLayout;
    @BindView(R.id.et_note_title) EditText mNoteTitle;
    @BindView(R.id.et_note_content) EditText mNoteContent;
    @BindView(R.id.bt_save_new_note) Button mSaveButton;

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
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AppTheme));
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getActivity().getApplicationContext(), android.R.style.Theme.AppCompat.Light.DarkActionBar);
        } else {
            builder = new AlertDialog.Builder(getActivity().getApplicationContext());
        }*/
        builder.setTitle("Erro")
                .setMessage(message)
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(R.drawable.icon_alert_red)
                .show();
    }

    protected void onStartAnimation(){
        AnimatorSet fadeInAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity().getApplicationContext(), R.animator.fade_in);
        fadeInAnimator.setTarget(mFrameLayout);
        fadeInAnimator.start();
    }

    @OnClick(R.id.bt_save_new_note)
    public void saveNewNote(){
        mPresenter.newNote(mNoteTitle!=null ? mNoteTitle.getText().toString() : "" ,mNoteContent!=null? mNoteContent.getText().toString() : "");
    }

}
