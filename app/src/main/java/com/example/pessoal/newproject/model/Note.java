package com.example.pessoal.newproject.model;

/**
 * Created by ZUP on 25/09/2017.
 */

public class Note {
    /*se o contain falhar na hora de editar/remover, utilizar id, dando override no metodo necessario
    private int id;*/
    private String noteTitle;
    private String noteText;
    private String date;

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return noteTitle;
    }

    public String getContent() {
        return noteText;
    }
}
