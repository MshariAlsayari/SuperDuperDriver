package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public boolean isExist(Integer noteId){
        return noteMapper.getNote(noteId) != null;
    }
    public int createNote(Note note) {
        return noteMapper.insertNote(new Note(null, note.getNoteTitle(), note.getNoteDescription(), note.getUserid()));
    }

    public void updateNote(Note note){
        noteMapper.updateNote(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription());
    }

    public void deleteNote(Integer noteId) {
         noteMapper.deleteNote(noteId);
    }

    public List<Note> getAllNotes(){
        return noteMapper.getAllNotes();
    }
}
