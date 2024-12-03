package com.example.springappdemo.config;

import com.example.springappdemo.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;

//@Component("customAuthFilter")
public class CustomAuthFilter implements Filter {

    UserService userService;

    public CustomAuthFilter(UserService userService) {
        this.userService = userService;
    }

//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        userService = WebApplicationContextUtils
//                .getRequiredWebApplicationContext(filterConfig.getServletContext())
//                .getBean(UserService.class);
//    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String token = request.getHeader("token");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
        } else {
            if(token == null || !userService.validateToken(token)){ // can include if needed || !token.startsWith("Bearer ")
                throw new ServletException("Unauthorized request");
            }
        }
//        Claims claims = Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody();
//        request.setAttribute("claims", claims);
//        request.setAttribute("blog", servletRequest.getParameter("id"));
        filterChain.doFilter(request, response);
    }
}
