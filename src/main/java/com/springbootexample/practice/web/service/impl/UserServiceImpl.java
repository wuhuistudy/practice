package com.springbootexample.practice.web.service.impl;

import com.springbootexample.practice.entity.vo.UserVO;
import com.springbootexample.practice.web.mapper.UserMapper;
import com.springbootexample.practice.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public UserVO getUserInfo(int id) {
        return userMapper.getUserInfo(id);
    }


    public int deleteById(int id) {
        return userMapper.deleteById(id);
    }

    public int Update(UserVO user) {
        return userMapper.update(user);
    }

    public UserVO save(UserVO user) {
        int save = userMapper.save(user);
        return user;
    }

    public List<UserVO> selectAll() {
        return userMapper.selectAll();
    }
}
