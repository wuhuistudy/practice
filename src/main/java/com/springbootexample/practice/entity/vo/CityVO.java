package com.springbootexample.practice.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CityVO implements Serializable {
    private static final long serialVersionUID = -3346126126723682803L;
    private Integer id;
    private String name;
    private String state;
    private String country;
}
