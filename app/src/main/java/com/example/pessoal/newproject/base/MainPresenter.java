package com.example.pessoal.newproject.base;

import com.example.pessoal.newproject.model.Note;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZUP on 25/09/2017.
 */

public class MainPresenter implements MainMVP.RequiredPresenterOperations, MainMVP.PresenterOperations {

    //referencia para a view
    private WeakReference<MainMVP.RequiredViewOperations> mView;
    //referencia para o model
    private MainMVP.ModelOperations mModel;
    //estado de configuracao em mudanca
    private boolean mIsChangingConfig;

    public MainPresenter(MainMVP.RequiredViewOperations mView) {
        this.mView = new WeakReference<MainMVP.RequiredViewOperations>(mView);
        this.mModel = new MainModel(this);
    }

    //Disparado por activity apos mudanca de configuracao
    //view eh referencia aa view em mudanca
    @Override
    public void onConfigurationChanged(MainMVP.RequiredViewOperations view) {
        mView = new WeakReference<>(view);
    }

    //recebe evento de destroy
    //isChangingConfig representa "se esta mudando a configuracao"
    @Override
    public void onDestroy(boolean isChangingConfig) {
        mView = null;
        mIsChangingConfig = isChangingConfig;
        if (!isChangingConfig)
            mModel.onDestroy();
    }

    //chamado por MainActivity no evento de interacao do usuario ao pedir insercao de nova nota
    @Override
    public void newNote(String noteText) {
        Note note = new Note();
        note.setNoteText(noteText);
        note.setDate(getDate());
        mModel.insertNote(note);
    }

    //chamado por MainActivity no evento de interacao do usuario ao pedir remocao de nota
    @Override
    public void deleteNote(Note note) {
        mModel.removeNote(note);
    }

    //chamado por MainModel quando nota eh inserida com sucesso
    @Override
    public void onInsertedNote(Note newNote) {
        mView.get().showToast("New register " + newNote.getDate());
    }

    //chamado por MainModel quando nota eh removida com sucesso
    @Override
    public void onRemovedNote(Note note) {
        mView.get().showToast("Note from " + note.getDate() + " removed");
    }

    //chamado por MainModel quando ocorre eventual erro
    //repassa a mensagem para o usuario
    @Override
    public void onError(String errorMessage) {
        mView.get().showAlert(errorMessage);
    }

    //retorna data atual
    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}

