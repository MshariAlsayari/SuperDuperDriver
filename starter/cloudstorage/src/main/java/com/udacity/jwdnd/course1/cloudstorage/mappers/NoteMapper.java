package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("Select * from NOTES WHERE noteid = #{noteId}")
    Note getNote(Integer noteId);

    @Select("Select * from NOTES WHERE notetitle = #{noteTitle} AND notedescription = #{noteDescription} ")
    Note getNoteByTitleAndDescription(String noteTitle, String noteDescription);

    @Select("Select * from NOTES WHERE userid = #{userid} ")
    List<Note> getAllNotes(Integer userid);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    void deleteNote(Integer noteId);


    @Update("UPDATE NOTES SET notetitle = #{noteTitle},notedescription = #{noteDescription} WHERE noteid = #{noteId}")
    void updateNote(Integer noteId, String noteTitle, String noteDescription);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle},#{noteDescription},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);
}
