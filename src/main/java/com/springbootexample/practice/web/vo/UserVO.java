package com.springbootexample.practice.web.vo;

import lombok.Data;

/**
 * @author wuhui
 * @date 2021年07月02日 下午 03:18
 */
@Data
public class UserVO {
    private String userId;
    private String userName;
    private Integer age;
    private String address;
    private String phone;
}
