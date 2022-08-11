package com.javateam.project.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javateam.project.domain.RoleVO;
import com.javateam.project.service.MemberService;

import lombok.extern.log4j.Log4j2;


@Controller
@Log4j2
@RequestMapping("login")
public class LoginController {
	
	@Autowired
	MemberService memberService;
	
	@RequestMapping("login_form")
	public ModelAndView loginForm() {
		
		ModelAndView mv = new ModelAndView();
		
		log.info("loginForm");
		
		mv.setViewName("member/login");
		
		return mv;
	}

	@RequestMapping(value="login_proc", method=RequestMethod.POST)
	public String loginProc(@RequestParam("id") String id, 
							@RequestParam("pwd") String pwd,
							Model model,
							HttpSession session,
							RedirectAttributes ra) {
		
		log.info("로그인 처리 : 아이디 {}, 비밀번호 {}", id, pwd);
		
		String page = "";
		
		//탈퇴한 회원 여부 점검(usyn='n')
		//탈퇴한 회원이 로그인 할 경우
		if(memberService.isActiveMember(id) == false) {
			
			model.addAttribute("err_msg", "휴면 중인 회원입니다.");
			model.addAttribute("move_path", "member/member_active");
			
			page = "/error/active";
		
		} else {
		
			String msg = memberService.checkLogin(id, pwd);
			
			if(msg.equals("로그인 성공.")) {	
				
				//세션 변수 생성(로그인, MemberVO)
				session.setAttribute("LOGIN_USER", memberService.getMember(id));
				
				//세션 변수 생성(롤, RoleVO)
				session.setAttribute("LOGIN_ROLE_USER", new RoleVO(id, memberService.getRole(id)));
				
				page = "redirect:/";
				
			} else if(msg.equals("비밀번호가 틀렸습니다.")) {
				
				model.addAttribute("login_err_msg", msg);
				
	//			page = "redirect:/login/login_from";	//redirect : 인자 보내기 가능, 허나 model 불가능
				page = "/member/login";			//jsp에서 자바스크립트 사용해 에러 메세징
	//			리다이렉트 : 맵핑 주소, 그냥 포워드 : jsp 경로	
				
				
			} else {
				//"존재하지 않는 ID입니다." 다시 로그인 페이지로(혹은 회원가입 페이지나 id 찾기 페이지로)
				
				ra.addFlashAttribute("login_err_msg", msg);		//redirect 시 model을 못 쓰기에...
				
				page = "redirect:/login/login_form";
				//page = "redirect:/login/login_form?login_err_msg" + msg;	//get방식으로 메세징
			}
		}
		return page;
	}
	
	//세션만 종료시키면 되니 별다른 서비스, DAO, MAPPER 등은 필요 없다.
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		
		log.info("로그아웃");
		
		session.invalidate();	//모든 세션 제거
		
		return "redirect:/";
	}
	
}
