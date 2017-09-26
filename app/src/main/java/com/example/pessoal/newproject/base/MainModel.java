package com.example.pessoal.newproject.base;

import com.example.pessoal.newproject.model.Note;

/**
 * Created by ZUP on 25/09/2017.
 */

public class MainModel implements MainMVP.ModelOperations {

    //referencia para Presenter
    private MainMVP.RequiredPresenterOperations mPresenter;

    public MainModel(MainMVP.RequiredPresenterOperations mPresenter) {
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
        //logica de insercao AQUI
        //

        //se sucesso
        if (true)
            mPresenter.onInsertedNote(newNote);
            //se falha
        else
            mPresenter.onError("error message");
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

