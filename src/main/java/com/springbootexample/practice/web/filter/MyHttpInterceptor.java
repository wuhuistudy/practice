package com.springbootexample.practice.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.springbootexample.practice.web.wrapper.MyRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.UUID;

/**
 * @author wuhui
 * @date 2021年07月02日 下午 01:41
 */
@Component("myHttpInterceptor")
public class MyHttpInterceptor extends HandlerInterceptorAdapter {

    private static final String UNIQUE_ID = "TRACE_ID";

    protected static Logger logger = LoggerFactory.getLogger(MyHttpInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put(UNIQUE_ID, UUID.randomUUID().toString());
        MyRequestWrapper requestWrapper = new MyRequestWrapper(request);
        String body = requestWrapper.getBody();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        if (logger.isDebugEnabled())
        {
            logger.debug(String.format("%s,-----请求参数-----, url: %s, method: %s, params: %s", MDC.get(UNIQUE_ID), url, method, body));
        }
        // 这里可以对请求做通用的处理
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex)
            throws Exception {
        MDC.clear();
    }
}
