package com.logistics.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * 字符编码过滤器
 * 确保所有请求和响应都使用UTF-8编码
 */
public class CharacterEncodingFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化方法
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // 设置请求编码
        request.setCharacterEncoding("UTF-8");
        
        // 设置响应编码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 继续执行过滤器链
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        // 销毁方法
    }
}
