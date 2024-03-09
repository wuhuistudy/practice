package com.springbootexample.practice.web.mapper;

import com.springbootexample.practice.entity.vo.UserVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    UserVO getUserInfo(int id);
    /**
     * 新增用户
     * @param UserVO
     * @return
     */
    int save (UserVO UserVO);

    /**
     * 更新用户信息
     * @param UserVO
     * @return
     */
    int update (UserVO UserVO);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById (int id);

    /**
     * 查询所有用户信息
     * @return
     */
    List<UserVO> selectAll ();
}
