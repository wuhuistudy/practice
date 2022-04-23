package com.springbootexample.practice.service;


import com.springbootexample.practice.entity.vo.CityVO;

import java.util.List;

public interface CityService {

    public List<CityVO> listCities();

    public CityVO getCityById(Integer id);

}
