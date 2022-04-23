package com.springbootexample.practice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import com.springbootexample.practice.entity.CityEntity;
import com.springbootexample.practice.mapper.CityMapper;
import com.springbootexample.practice.entity.vo.CityVO;
import com.springbootexample.practice.service.CityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Resource
    private CityMapper cityMapper;

    @Override
    public List<CityVO> listCities() {
        List<CityEntity> cityVOS = cityMapper.listCities();
        List<CityVO> voList = Lists.transform(cityVOS, (entity) -> {
            CityVO vo = new CityVO();
            vo.setId(entity.getId());
            vo.setName(entity.getName());
            vo.setCountry(entity.getCountry());
            vo.setState(entity.getState());
            return vo;
        });
        return voList;
    }

    @Override
    public CityVO getCityById(Integer id) {
        CityVO vo = new CityVO();
        CityEntity cityVO = cityMapper.getCityById(id);
        BeanUtil.copyProperties(cityVO, vo);
        return vo;
    }
}
