package com.springbootexample.practice.entity;

import lombok.Data;

import java.util.List;

@Data
public class Root {
    private List<List<DataInfo>> DataInfo;
}
