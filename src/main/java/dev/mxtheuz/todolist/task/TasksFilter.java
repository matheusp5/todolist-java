package dev.mxtheuz.todolist.task;

import dev.mxtheuz.todolist.utils.JwtTokenService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class TasksFilter implements Filter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        System.out.println("Intercepting: " + request.getRequestURI());
        if(request.getRequestURI().contains("/api/task")) {
            var authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null) {
                response.sendError(401);
            } else {
                String token = authorizationHeader.substring("Bearer ".length());
                if(token.isEmpty()) {
                    response.sendError(401);
                } else {
                    String userId = jwtTokenService.DecodeToken(token);
                    request.setAttribute("userId", userId);
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }
}
