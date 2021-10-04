package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("Select * from NOTES WHERE noteid = #{noteId}")
    Note getNote(Integer noteId);

    @Select("Select * from NOTES")
    List<Note> getAllNotes();

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    void deleteNote(Integer noteId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle},#{noteDescription},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);
}
