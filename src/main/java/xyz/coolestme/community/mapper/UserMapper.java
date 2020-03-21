package xyz.coolestme.community.mapper;

import org.apache.ibatis.annotations.*;
import xyz.coolestme.community.model.User;

@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modifiled,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAcountId(@Param("accountId") String accountId);

    @Update("update user set name = #{name}, token = #{token}, gmt_modifiled = #{gmtModified}, avatar_url = #{avatarUrl} where id = #{accountId}")
    void update(User dbUser);
}
