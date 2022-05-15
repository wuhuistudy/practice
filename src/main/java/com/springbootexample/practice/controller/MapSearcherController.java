package com.springbootexample.practice.controller;

import com.ejlchina.searcher.MapSearcher;
import com.ejlchina.searcher.SearchResult;
import com.ejlchina.searcher.util.MapUtils;
import com.springbootexample.practice.entity.City;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 测试 bean searcher
 */
@Slf4j
@RestController
@RequestMapping("/beanSearch")
public class MapSearcherController {

    @Autowired
    private MapSearcher mapSearcher;

    @GetMapping("/index")
    public SearchResult<Map<String, Object>> index(HttpServletRequest request) {
        // 一行代码，实现一个用户检索接口（MapUtils.flat 只是收集前端的请求参数）
        // City对应数据库表名要一致
        return mapSearcher.search(City.class, MapUtils.flat(request.getParameterMap()));
    }

}
