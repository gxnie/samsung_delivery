package com.example.samsung_delivery.config;

import com.example.samsung_delivery.dto.login.LoginResponseDto;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class AdminFilter implements Filter {

    private static final String[] BLACK_LIST = {"/dashboard"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String requestURL = request.getRequestURI();

        HttpServletResponse response = (HttpServletResponse) servletResponse;


            HttpSession session = request.getSession(false);

            LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute(Const.LOGIN_USER);

            String role = String.valueOf(loginUser.getUserRole());

            if (!role.equals("ADMIN")) {

                log.warn("you are not admin {}", requestURL);

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("only use admin");

                return;
            }
            log.info("관리자 입장");
        filterChain.doFilter(servletRequest, servletResponse);

    }

}
