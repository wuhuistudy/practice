package com.springbootexample.practice.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class City implements Serializable {
    private static final long serialVersionUID = 1519868710852845474L;
    private Integer id;
    private String name;
    private String state;
    private String country;
}
