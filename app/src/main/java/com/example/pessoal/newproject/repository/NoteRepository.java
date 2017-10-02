package com.example.pessoal.newproject.repository;

import com.example.pessoal.newproject.base.MainMVP;
import com.example.pessoal.newproject.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZUP on 26/09/2017.
 */

public class NoteRepository implements MainMVP.ModelOperations {

    public static List<Note> mList;

    //referencia para Presenter
    private MainMVP.RequiredPresenterOperations mPresenter;

    public NoteRepository(MainMVP.RequiredPresenterOperations mPresenter) {
        this.mPresenter = mPresenter;

        if (mList == null)
            mList = new ArrayList<>();
    }

    public static List getList() {
        return mList;
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
        if (isValid(newNote)) {
            mList.add(newNote);
            mPresenter.onInsertedNote(newNote);
        } else
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
        if (mList.contains(note)) {
            mList.remove(note);
            mPresenter.onRemovedNote(note);
        } else
            mPresenter.onError("error message");
    }

}

