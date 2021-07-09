package com.springbootexample.practice.web.controller;

import com.alibaba.fastjson.JSON;
import com.springbootexample.practice.web.common.util.RedisUtil;
import com.springbootexample.practice.web.common.util.StringUtil;
import com.springbootexample.practice.web.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuhui
 * @date 2021年07月01日 下午 07:08
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    protected static Logger logger = LoggerFactory.getLogger(TestController.class);

    private static final String UNIQUE_ID = "TRACE_ID";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @ResponseBody
    @PostMapping(value = "/testHello")
    private String testDemo(@RequestBody String jsonStr){
        String mdcId = MDC.get(UNIQUE_ID);
        UserVO userVO = JSON.parseObject(jsonStr, UserVO.class);
        String key = userVO.getUserId() + "_" + userVO.getPhone();
        if (logger.isDebugEnabled()){
            logger.debug("{}, key = {}", mdcId, key);
        }
        String result = (String) RedisUtil.get(this.redisTemplate, key);
        if (logger.isDebugEnabled()){
            logger.debug("{}, result = {}", mdcId, result);
        }
        if (StringUtil.isNullOrEmpty(result))
        {
            if (logger.isDebugEnabled()){
                logger.debug("---result is null---");
            }
            RedisUtil.set(this.redisTemplate, key, jsonStr);
            result = (String) RedisUtil.get(this.redisTemplate, key);
        }
        else {
            if (logger.isDebugEnabled()){
                logger.debug("---result is not null---");
            }
        }
        if (logger.isDebugEnabled()){
            logger.debug("{}, afterSet = {}", mdcId, result);
        }
        return result;
    }
}
