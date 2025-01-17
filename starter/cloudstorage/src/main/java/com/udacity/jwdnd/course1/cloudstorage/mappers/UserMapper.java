package com.udacity.jwdnd.course1.cloudstorage.mappers;




import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("Select * from USERS WHERE username = #{username}")
    User getUser(String username);

    @Insert("INSERT INTO USERS (username, salt, password, firstName, lastName) VALUES (#{username},#{salt},#{password},#{firstName},#{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(User user);
}


