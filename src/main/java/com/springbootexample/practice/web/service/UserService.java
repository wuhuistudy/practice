package com.springbootexample.practice.web.service;

import com.springbootexample.practice.entity.vo.UserVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public UserVO getUserInfo(int id);

    public int deleteById(int id);

    public int Update(UserVO user);

    public UserVO save(UserVO user);

    public List<UserVO> selectAll();
}
