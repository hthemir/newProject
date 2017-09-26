package com.example.pessoal.newproject.presenter;

import com.example.pessoal.newproject.base.MainMVP;
import com.example.pessoal.newproject.model.Note;
import com.example.pessoal.newproject.repository.NoteRepository;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZUP on 26/09/2017.
 */

public class NotePresenter implements MainMVP.RequiredPresenterOperations, MainMVP.PresenterOperations {

    private WeakReference<MainMVP.RequiredViewOperations> mView;
    private MainMVP.ModelOperations mModel;
    private boolean mIsChangingConfig;

    public NotePresenter(MainMVP.RequiredViewOperations mView) {
        this.mView = new WeakReference<MainMVP.RequiredViewOperations>(mView);
        this.mModel = new NoteRepository(this);
    }

    @Override
    public void onConfigurationChanged(MainMVP.RequiredViewOperations view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void onDestroy(boolean isChangingConfig) {
        mView = null;
        mIsChangingConfig = isChangingConfig;
        if (!isChangingConfig) mModel.onDestroy();
    }

    @Override
    public void newNote(String noteText) {
        Note note = new Note();
        note.setNoteText(noteText);
        note.setDate(getDate());
        mModel.insertNote(note);
    }

    @Override
    public void deleteNote(Note note) {
        mModel.removeNote(note);
    }

    @Override
    public void onInsertedNote(Note newNote) {
        mView.get().showToast("New register " + newNote.getDate());
    }

    @Override
    public void onRemovedNote(Note note) {
        mView.get().showToast("Note from " + note.getDate() + " removed");
    }

    @Override
    public void onError(String errorMessage) {
        mView.get().showAlert(errorMessage);
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
