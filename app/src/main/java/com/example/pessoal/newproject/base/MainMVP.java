package com.example.pessoal.newproject.base;

import android.support.v4.app.Fragment;

import com.example.pessoal.newproject.model.Note;

/**
 * Created by ZUP on 25/09/2017.
 */

public interface MainMVP {

    //usado no presenter para acessar view
    //qualquer operação na user interface
    interface RequiredViewOperations {
        void showToast(String message);
        void showAlert(String message);
    }

    //usado pela view para comunicar o presenter
    //qualquer operação a ser chamada pela view
    interface PresenterOperations {
        void onConfigurationChanged(RequiredViewOperations view);
        void onDestroy(boolean isChangingConfig);
        void newNote(String noteTitle, String noteText);
        void deleteNote(Note note);
    }

    //usado pelo model para comunicar o presenter
    //qualquer operação para enviar informações (de sucesso ou erro) do model ao presenter
    interface RequiredPresenterOperations {
        void newNote(String noteTitle, String noteText);

        void onInsertedNote(Note newNote);
        void onRemovedNote(Note note);
        void onError(String errorMessage);
    }

    //usado pelo presenter para comunicar o model
    //qualquer operação referente a dados a ser chamado pelo presenter
    interface ModelOperations {
        void onDestroy();
        void insertNote(Note newNote);
        void removeNote(Note note);
    }

    interface RequiredActivityOperations {
        void replaceFragment(final Fragment fragment, final String tag, final boolean addToBackStack);
    }

}

