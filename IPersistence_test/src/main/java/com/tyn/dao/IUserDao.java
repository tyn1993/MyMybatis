package com.tyn.dao;

import com.tyn.pojo.User;

import java.util.List;

public interface IUserDao {
    //查询所有用户
    public List<User> findAll();
    //根据条件尽心用户查询
    public User findUser(User user);
}
