package com.springbootexample.practice.mapper;

import com.springbootexample.practice.entity.CityEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CityMapper {

    public List<CityEntity> listCities();

    public CityEntity getCityById(Integer id);

}
