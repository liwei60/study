package com.lenovo.filter;

import com.lenovo.util.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 接口访问签名验证
 */
//@Configuration
//@WebFilter(filterName = "HelloFilter" , urlPatterns = "/hello")
public class HelloFilter implements  Filter{
    private final static Log logger = LogFactory.getLog(HelloFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        String originSignString = request.getParameter("sign");
        String message = ValidAndResponseHelper.getParameterMapToString(request.getParameterMap());

        if(ValidAndResponseHelper.validAndReturn(originSignString,message, Constants.SECRET_KEY)){
            return;
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
