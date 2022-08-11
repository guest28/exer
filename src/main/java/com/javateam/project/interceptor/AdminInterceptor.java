package com.javateam.project.interceptor;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.javateam.project.domain.MemberVO;
import com.javateam.project.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AdminInterceptor extends HandlerInterceptorAdapter {
	
	@Inject
	MemberService memberService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		log.info("admin interceptor");
		
		//관리자 점검(인증) 절차 : 1이 인증 되었을 때 2로
		//1. 세션 점검 : 로그인
		//2. 롤 점검 : role = 'admin'
		
		boolean result = false;
		String errMsg = "";
		String movePath = "";
		
		HttpSession session = request.getSession();		//상속 클래스이기 때문에 인자로 못 넣고 서블렛 방식으로 세션을 부른다
		
		//1. 세션 점검 : 로그인
		if(session.getAttribute("LOGIN_USER") == null) {		//로그인을 안 했다면
			
			log.info("로그인 인증 X");
			
			errMsg = "관리자만 접근할 수 있습니다.";
			movePath = "login/login_form";		//스프링보단 서블렛과 비슷하기 떄문에 "/login/login_from"으로 쓰면 주소가 "...//login/login_form"으로 나온다
			request.setAttribute("err_msg", errMsg);
			request.setAttribute("move_path", movePath);
			
			//인터셉터는 컨트롤러가 아니기 때문에 서블릿을 이용해서 포워드
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/error/error.jsp");	//스프링보단 서블렛과 비슷하기 떄문에 "/error/error"로 쓰면 안 됨
			rd.forward(request, response);
			
			result = false;
		
		} else {	//로그인을 했다면
			
			log.info("로그인 인증 O");
			
			String id = ((MemberVO)session.getAttribute("LOGIN_USER")).getId();
			String role = memberService.getRole(id);
			
			//2. 롤 점검 : role = 'admin'
			if(!role.equals("admin")) {
				
				log.info("관리자 권한 X");
				
				response.sendError(HttpServletResponse.SC_FORBIDDEN);	//403 에러
				
				result = false;
			
			} else {
				
				log.info("관리자 권한 O");
				
				result = true;
			}
		
		
		
		}
		
		return result;
		//return super.preHandle(request, response, handler);
	}
	
	

}
