package com.example.pessoal.newproject.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pessoal.newproject.R;
import com.example.pessoal.newproject.adapters.AdapterNoteRecyclerView;
import com.example.pessoal.newproject.base.MainMVP;
import com.example.pessoal.newproject.base.StateMaintainer;
import com.example.pessoal.newproject.model.Note;
import com.example.pessoal.newproject.presenter.NotePresenter;
import com.example.pessoal.newproject.repository.NoteRepository;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZUP on 02/10/2017.
 */

public class FragmentNotes extends Fragment implements MainMVP.RequiredViewOperations {
    protected final String TAG = getClass().getSimpleName();

    private StateMaintainer mStateMaintainer;
    private MainMVP.PresenterOperations mPresenter;

    @BindView(R.id.rv_notes)
    RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        ButterKnife.bind(this,view);
        mStateMaintainer = new StateMaintainer(getActivity().getFragmentManager(), TAG);
        startMVPOperations();
        setupView();
        return view;
    }

    private void setupView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        AdapterNoteRecyclerView adapter = new AdapterNoteRecyclerView(NoteRepository.getList(), getActivity(),this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public static FragmentNotes newInstance(){
        return new FragmentNotes();
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

    public void removeNote(Note note) {
        mPresenter.deleteNote(note);
    }

    public void editNote(Note note) {
        mPresenter.editNote(note);
    }
}
