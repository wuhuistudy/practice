package com.springbootexample.practice.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CityEntity implements Serializable {

    private static final long serialVersionUID = 7669898276493113415L;
    private Integer id;
    private String name;
    private String state;
    private String country;

    public CityEntity() {
    }

    public CityEntity(Integer id, String name, String state, String country) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.country = country;
    }
}
