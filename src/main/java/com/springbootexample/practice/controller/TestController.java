package com.springbootexample.practice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springbootexample.practice.entity.DataInfo;
import com.springbootexample.practice.entity.DirPathName;
import com.springbootexample.practice.entity.Root;
import com.springbootexample.practice.entity.vo.CityVO;
import com.springbootexample.practice.service.CityService;
import com.springbootexample.practice.web.common.util.RedisUtil;
import com.springbootexample.practice.web.common.util.StringUtil;
import com.springbootexample.practice.entity.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    @Resource
    private CityService cityService;

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

    @ResponseBody
    @GetMapping(value = "/getCityList")
    private List<CityVO> getCityList() {
        List<CityVO> cityVOS = cityService.listCities();
        return cityVOS;
    }

    @ResponseBody
    @GetMapping(value = "/getCityById")
    private CityVO getCityById(@RequestParam("id") Integer id) {
        CityVO cityVO = cityService.getCityById(id);
        return cityVO;
    }
    @ResponseBody
    @PostMapping(value = "/getPath")
    private void getPath(@RequestBody DirPathName dirPathName) {
        logger.info("dirName :{}", dirPathName.getDirName());
    }

    public static void main(String[] args) {
       delAllFiles(new File("E:\\测试文件夹1"),null);
    }

    public static void delAllFiles(File file,String singal){
        if(!file.exists()){
            System.out.println("不存在该路径: "+file);
            return;
        }
        if(singal==null||"".equals(singal)){
            singal="-";
        }
        System.out.println(singal+"目录 ["+file.getName()+"]中：");
        singal=singal+" -";
        File[] files=file.listFiles();
        if(files.length>0){
            for(File f:files){
                if(f.isDirectory()){//如果是目录
                    delAllFiles(f, singal); //递归
                    System.out.println(singal+"目录 ["+f.getName()+"]已删除");
                    f.delete();     //删除该文件夹
                }
                else{
                    System.out.println(singal+" 文件《"+f.getName()+"》已删除");
                    f.delete();
                }//else
            }//for
        }//if
        else{
            System.out.println("***该目录中无任何文件***");
        }
    }

}
