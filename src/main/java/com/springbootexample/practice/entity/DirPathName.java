package com.springbootexample.practice.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class DirPathName implements Serializable {
    private static final long serialVersionUID = 2640467980561549764L;
    /** 读取的文件夹路径 */
    String dirName;
}
