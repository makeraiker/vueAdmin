package com.me.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.me.reggie.common.BaseContext;
import com.me.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器
 * 检查用户是否登录
 */
@Slf4j
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    //路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1.获取本次请求的url
        String requestURL = request.getRequestURI();
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };

        //2.判断本次请求是否需要处理
        boolean check = check(urls,requestURL);

        //3.如果不需要处理
        if(check){
            filterChain.doFilter(request,response);
            return;
        }

        //4-1.判断登录状态
        if (request.getSession().getAttribute("employee")!=null){

            Long empId = (Long) request.getSession().getAttribute("employee");
            if(BaseContext.getCurrentId()==null){
                BaseContext.setCurrentId(empId);
            }

            filterChain.doFilter(request,response);
            return;
        }

        //4-2.判断登录状态
        if (request.getSession().getAttribute("user")!=null){

            Long userId = (Long) request.getSession().getAttribute("user");
            if(BaseContext.getCurrentId()==null){
                BaseContext.setCurrentId(userId);
            }

            filterChain.doFilter(request,response);
            return;
        }

        //5.如果未登录，通过输出流向前端发送json数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 路径匹配，本次请求是否需要放行
     * @param urls
     * @param requestURL
     * @return
     */
    private boolean check(String[] urls,String requestURL) {
        for (String url : urls) {
            if (PATH_MATCHER.match(url,requestURL)){
                return true;
            }
        }
        return false;
    }
}
