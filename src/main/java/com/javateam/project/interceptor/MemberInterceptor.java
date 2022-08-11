package com.javateam.project.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MemberInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		log.info("member interceptor");
		
		boolean result = false;
		String errMsg = "";
		String movePath = "";
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("LOGIN_USER") == null) {
			
			log.info("로그인 인증X");
			
			errMsg = "로그인 이후 접근할 수 있습니다.";
			movePath = "login/login_form";
			request.setAttribute("err_msg", errMsg);
			request.setAttribute("move_path", movePath);
			
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/error/error.jsp");
			rd.forward(request, response);
			
			result = false;
			
		} else {
			
			log.info("로그인 인증 O");
			
			result = true;
		}
		
		return  result;
	}

}
