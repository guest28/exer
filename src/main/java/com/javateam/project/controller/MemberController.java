package com.javateam.project.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javateam.project.domain.MemberDTO;
import com.javateam.project.domain.MemberVO;
import com.javateam.project.domain.RoleVO;
import com.javateam.project.service.MemberService;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("member")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	

	@RequestMapping("contract")
	public String contract() {
		
		log.info("회원 가입 약관 동의 : contract");
		
		return "/member/contract";
	}
	
	@RequestMapping("join_form")
	public String joinForm(@RequestParam(value="contractOK", defaultValue="false") String contractOK) {
		
		log.info("회원 가입폼");
		String page = "";
		
		// 약관에 동의했으면...
		if (contractOK.equals("join_form")) {
			
			log.info("약관 동의 : " + contractOK);
			page="/member/contract";
			
		} else {
			
			log.info("약관 미동의 : " + contractOK);
			page="/member/join";			// 약관 페이지 이동
			//page="redirect:/member/contract";	// 약관 페이지(주소) 이동
		}
		
		return page;
	}
	
	@PostMapping("join_proc")
	public String joinProc(MemberVO memberVO, HttpServletResponse response) throws Exception {
		
		log.info("회원 가입 처리" + memberVO);
		
		String page = "";

		memberVO.setUseyn("y");		//회원 활동 여부 : 가입 양식에 없는 부분이라 이 것이 빠지면 인자(memberVO)가 넘어가지 않음 => 404에러
		
		RoleVO roleVO = new RoleVO();	//joinProc에서 인자로 사용하는 것이 아님! 
		roleVO.setId(memberVO.getId());
		roleVO.setRole("user");		//기본 role => 회원(user)
		
		if (memberService.insertMember(memberVO, roleVO)) {
			
			log.info("회원 가입 성공");
			
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>alert('회원 가입 성공.'); location.href='/project/login/login_form';</script>");
			out.flush();
			
			//page = "redirect:/login/login_form";	//해당 페이지의 부가적인 db와의 연결 부분(login_form의 컨트롤러에 있는...)을 해치지 않기 위해 redirect
		
		} else {
			
			log.info("회원 가입 실패");
			
			page = "redirect:/member/join_from?contractOK=ture";	//약관 동의를 한 상태에서 join_form으로 가도록...
		}
		
		return page;
	}
	
	@GetMapping("member_info")
	public String memberInfo() {
		
		log.info("개별 회원 정보 조회  페이지");
	
		return "/member/member_info";
		
	}
	
	@GetMapping("member_update")
	public String memberUpdate() {
		
		log.info("개별 회원 정보 수정/탈퇴 페이지");
		return "/member/member_update";
	}
	
	@GetMapping("member_check")
	public String memberCheck() {
		
		log.info("개별 회원 확인 페이지");
		return "/member/member_check";
	}
	
	@RequestMapping(value="check_proc", method=RequestMethod.POST)
	public String checkProc(@RequestParam("id") String id, 
							@RequestParam("pwd") String pwd,
							HttpServletResponse response) throws Exception {
		
		log.info("회원 확인 : 아이디 {}, 비밀번호 {}", id, pwd);
		
		String msg = memberService.checkLogin(id, pwd);
		String page = "";
		
		if(msg.equals("로그인 성공.")) {	
			
			page = "/member/member_update";
			
		} else if(msg.equals("비밀번호가 틀렸습니다.")) {	
			
			response.setContentType("text/html; charset=UTF-8");	
			PrintWriter out = response.getWriter();
			out.println("<script>alert('비밀번호가 틀렸습니다.');</script>");
			out.flush();
			
			page = "/member/member_check";
		}
		
		return page;
	}
	
	@PostMapping("update_proc")
	public String updateProc(@Valid MemberDTO memberDTO,
							 BindingResult result,
							 RedirectAttributes ra,
							 HttpSession session) {
		
		log.info("개별 회원 정보 수정 처리");
		
		String page = "";
		
		//신규 비밀번호 일치 여부 점검
		log.info("신규 비밀번호 일치 여부 점검 : " + memberDTO.getPwd().contentEquals(memberDTO.getPwdCheck()));
		
		String pwd = memberDTO.getPwd();
		String pwdCheck = memberDTO.getPwdCheck();
		
		//신규 비밀번호 일치 여부 점검 실패 시
		if(!pwd.equals(pwdCheck)) {
			
			log.info("비밀번호 불일치");
			
			ra.addFlashAttribute("pwd_err_msg2", "비밀번호가 다릅니다.");
		}
		
		//주소 필드 폼 점검 : join과 같은 기준
		if(!memberDTO.getZipNum().equals("") &&
		   !memberDTO.getAddress1().equals("") &&
		    memberDTO.getAddress2().trim().equals("")) {
			
			log.info("상세주소 미입력 오류");
			
			ra.addFlashAttribute("address_err_msg", "상세 주소를 입력하시오.");
		}
		
		if( memberDTO.getZipNum().equals("") &&
			memberDTO.getAddress1().equals("") &&
		   !memberDTO.getAddress2().trim().equals("")) {
			
			log.info("기본주소 미입력 오류");
			
			ra.addFlashAttribute("address_err_msg", "우편번호 검색을 통해 주소(도로명)를 입력하시오.");
		}
		
		//이메일 중복 점검
		if(memberService.isEmailOnUpdate(memberDTO.getId(), memberDTO.getEmail()) == true) {
			
			log.info("이메일 중복");
			
			ra.addFlashAttribute("email_err_msg", "이미 존재하는 이-메일입니다.");
		}
		
		//연락처 중복 점검
		if(memberService.isPhoneOnUpdate(memberDTO.getId(), memberDTO.getPhone()) == true) {
			
			log.info("이메일 중복");
			
			ra.addFlashAttribute("email_err_msg", "이미 존재하는 이-메일입니다.");
		}
		
		if(result.hasErrors()) {
			
			log.error("폼 점검 오류");
			
			result.getAllErrors().forEach(x->{log.error(x);});
			
			ra.addFlashAttribute("org.springframework.validation.BindingResult.memberDTO", result);
			
			page = "redirect:/member/member_update";	//에러 페널을 보기 위한 조치
			//page = "/member/member_update";				//단순히 member_update.jsp로 간다
			
		} else {
			
			//폼 점검 완료 => memberDTO의 정보를 memberVO에 담기 => memberVO에서 복사 생성자를 이용...
				
			log.info("폼 점검 완료");
			log.info("MemberDTO : {}", memberDTO);
			
//세션 get		
			MemberVO memberVO = (MemberVO)session.getAttribute("LOGIN_USER");
			
			//비밀번호 변경 반영 => 공백이 아닐 때
			if(!pwd.equals("")) {
				
				memberVO.setPwd(pwd);
			}
			
			//이메일 변경 반영 => 조건 없이 덮어 써도 괜찮을 듯?
			if(!memberDTO.getEmail().equals(memberVO.getEmail())) {
				
				memberVO.setEmail(memberDTO.getEmail());
			}
			
			//연락처 변경 반영
			memberVO.setPhone(memberDTO.getPhone());

			//주소 변경 반영
			if((memberDTO.getZipNum().equals("") &&
			    memberDTO.getAddress1().equals("") &&
			    memberDTO.getAddress2().trim().equals("")) ||
			   (!memberDTO.getZipNum().equals("") &&
			    !memberDTO.getAddress1().equals("") &&
			    !memberDTO.getAddress2().trim().equals(""))) {
				
					memberVO.setZipNum(memberDTO.getZipNum());
					memberVO.setAddress1(memberDTO.getAddress1());
					memberVO.setAddress2(memberDTO.getAddress2());
			}
			
			log.info("업데이트(수정) memberVO : {}", memberVO);
			
			
			//메세징
			String updateMsg = "";
			
			if (memberService.updateMember(memberVO) == true) {
				
				log.info("회원 정보 수정 성공");
				updateMsg = "회원 정보 수정에 성공하였습니다";
				
			} else {
				
				log.info("회원 정보 수정 실패");
				updateMsg = "회원 정보 수정에 실패하였습니다";
			}
			
			ra.addFlashAttribute("update_success_msg", updateMsg);
			
//세션 set	=> 기존 정보(session) 변경 , 이 놈을 해줘야 DB에 들어간다
			session.setAttribute("LOGIN_USER", memberVO);
			
			page = "redirect:/member/member_update";
		}
		
		return page;
	}

	@GetMapping("member_inactive")
	public String memberInactive() {
		
		log.info("회원 탈퇴 페이지");
		return "/member/member_inactive";
	}
	
	@RequestMapping("member_inactive_proc")
	public String memberInactiveProc(HttpSession session, Model model) {
		
		//탈퇴 : 회원 정보 삭제가 아닌 휴면 상태로 => useyn = 'n'
		log.info("회원 탈퇴 처리");

		String errMsg = "";
		String path = "";
		String id = ((MemberVO)session.getAttribute("LOGIN_USER")).getId();
		
		if(memberService.inactiveMemberRole(id) == true) {
			
			session.invalidate();	//세션 종료(로그아웃)
			errMsg = "회원에 탈퇴하였습니다.";
			path = "/";
			
		} else {
			
			errMsg = "회원 탈퇴 처리 실패.";
			path = "member/member_info";
		}
		
		model.addAttribute("err_msg", errMsg);
		model.addAttribute("move_path", path);
		
		return "/error/error";
	}
	
	@GetMapping("member_active")
	public String memberActive() {
		
		log.info("휴면 계정 활성 페이지");
		return "/member/member_active";
	}
	
	@RequestMapping(value="active_proc", method=RequestMethod.POST)
	public String activeProc(@ModelAttribute MemberVO memberVO, Model model) {
		
		log.info("탈퇴한 회원 여부 점검 처리 : {}", memberVO);
		
		String msg = memberService.checkInactiveMember(memberVO);
		String errMsg = "";
		String path = "";
		
		
		
		if(msg.equals("인증 성공.")) {
			
			if(memberService.activeMemberRole(memberVO.getId(), "user", "y") == true) {
				
				errMsg = "휴면 계정 활성화에 성공했습니다.";
				path = "login/login_form";
				
			} else {
				
				errMsg = "휴면 계정 활성화에 실패했습니다.";
				path = "member/member_active";
			}
			
		} else if(msg.equals("인증 실패.")) {	
			
			errMsg = "휴면 계정 인증에 실패했습니다.";
			path = "member/member_active";
			
		}
		
		model.addAttribute("err_msg", errMsg);
		model.addAttribute("move_path", path);
		
		return "/error/error";
	}
	
		
	


}