package com.example.pessoal.newproject.repository;

import com.example.pessoal.newproject.base.MainMVP;
import com.example.pessoal.newproject.model.Note;

/**
 * Created by ZUP on 26/09/2017.
 */

public class NoteRepository implements MainMVP.ModelOperations {

    //referencia para Presenter
    private MainMVP.RequiredPresenterOperations mPresenter;

    public NoteRepository(MainMVP.RequiredPresenterOperations mPresenter) {
        this.mPresenter = mPresenter;
    }

    //disparado por presenter em onDestroy
    //para as operações que estiverem executando em background
    @Override
    public void onDestroy() {
        //acoes para destruir objeto
    }

    //insere nota
    @Override
    public void insertNote(Note newNote) {
        if (isValid(newNote))
            mPresenter.onInsertedNote(newNote);
        else
            mPresenter.onError("error message");
    }

    private boolean isValid(Note newNote) {
        if (newNote==null || newNote.getTitle().equals("") || newNote.getContent().equals(""))
            return false;
        return true;
    }

    //remove nota
    @Override
    public void removeNote(Note note) {
        //logica de remocao AQUI
        //

        //se sucesso
        if (true)
            mPresenter.onRemovedNote(note);
            //se falha
        else
            mPresenter.onError("error message");
    }

}

