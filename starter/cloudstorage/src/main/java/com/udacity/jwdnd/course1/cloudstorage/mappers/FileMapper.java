package com.udacity.jwdnd.course1.cloudstorage.mappers;


import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("Select * from FILES WHERE filename = #{filename}")
    File getFile(String filename);

    @Select("Select * from FILES WHERE userid = #{userid}")
    List<File> getAllFile(Integer userid);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deleteFile(Integer fileId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename},#{contentType},#{fileSize},#{userid},#{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);
}
