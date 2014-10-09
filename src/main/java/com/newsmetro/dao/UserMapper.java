package com.newsmetro.dao;

import com.newsmetro.enumeration.UserStatus;
import com.newsmetro.po.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by lifeng.gao on 2014/10/8.
 */


public interface UserMapper {
    Long getUserCountByEmail(@Param("email") String email);

    List<User> getUserByEmail(@Param("email") String email);

    List<User> getUserByEmailAndCode(@Param("email") String email,@Param("code") String code);

    List<User> getUserByEmailAndStatus(@Param("email") String email,@Param("status") UserStatus status);

    List<Integer> getUserByStatusAndRegisterDate(@Param("status") UserStatus status,@Param("registerDate") Date registerDate);

    User getUserByEmailAndPassword(@Param("email") String email,@Param("password") String password);

    void deleteByIdList(@Param("idList") List<Integer> idList);

    void deleteById(@Param("id") Integer id);

    Integer save(@Param("user") User user);

    void update(@Param("user") User user);
}
