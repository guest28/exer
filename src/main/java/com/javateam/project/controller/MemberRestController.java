package com.javateam.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javateam.project.domain.MemberVO;
import com.javateam.project.service.MemberService;

@RestController
@RequestMapping("member")
public class MemberRestController {

	private static final Logger log = LoggerFactory.getLogger(MemberRestController.class);
	
	@Autowired
	MemberService memberService;
	
	/*@GetMapping(value="id_check", produces="text/plain; charset=UTF-8")
	public String idCheck(@RequestParam("id") String id) {
		
		log.info("아이디 중복 점검 Rest Service id : " + id);
		
		String result = "";
		
		result = memberService.isMember(id) == true ? "있는 아이디" : "없는 아이디";
		
		return result;
	}*/
	@GetMapping(value="id_check", produces="text/plain; charset=UTF-8")
	public ResponseEntity<String> idCheck(@RequestParam("id") String id) {
		
		log.info("아이디 중복 점검 Rest Service id : " + id);
		
		try {
			
			if(memberService.isMember(id) == true) {
				
				return new ResponseEntity<String>("있는 아이디", HttpStatus.OK);
				//code 200 : 콘텐츠 있음!(성공했다는 뜻) 서버가 요청을 제대로 처리했고 서버가 요청한 페이지를 제공했다. success : 응담 메세지 있음
				
			} else {
				
				return new ResponseEntity<String>("없는 아이디", HttpStatus.OK);
				//return new ResponseEntity<String>("존재하지 않는 아이디", HttpStatus.NO_CONTENT);	
				//code 204 : 콘텐츠 없음! 서버가 요청을 성공적으로 처리했으나 콘텐츠를 제공하진 않는다. error : 응답 메세지 없음 허나 콘솔에서 확인차 ok를 넣어봤다
			}
			
		} catch(Exception e) {
			
			log.error("idCheck REST Service error : " + e.getMessage());
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
			//code 417 : 서버는 Expext 요청 헤더 입력란의 요구사항을 만족할 수 없다.(null...?) 즉, 예상 실패이니 error
		}
		
	
	}
	
	@GetMapping(value="email_check", produces="text/plain; charset=UTF-8")
	public ResponseEntity<String> emailCheck(@RequestParam("email") String email) {
		
		log.info("이메일 중복 점검 Rest Service email : " + email);
		
		try {
			
			if(memberService.isEmail(email) == true) {
				
				return new ResponseEntity<String>("있는 이메일", HttpStatus.OK);
				//code 200 : 성공했구나! 서버가 요청을 제대로 처리했고 서버가 요청한 페이지를 제공했다. success : 응담 메세지 있음
				
			} else {
				
				return new ResponseEntity<String>("없는 이메일", HttpStatus.OK);
				//return new ResponseEntity<String>("존재하지 않는 이메일", HttpStatus.NO_CONTENT);	
				//code 204 : 콘텐츠 없음! 서버가 요청을 성공적으로 처리했으나 콘텐츠를 제공하진 않는다. error : 응답 메세지 없음
			}
			
		} catch(Exception e) {
			
			log.error("emailCheck REST Service error : " + e.getMessage());
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
			//code 417 : 서버는 Expext 요청 헤더 입력란의 요구사항을 만족할 수 없다.(null...?) 즉, 예상 실패이니 error
		}
	}	
	
	@GetMapping(value="phone_check", produces="text/plain; charset=UTF-8")
	public ResponseEntity<String> phoneCheck(@RequestParam("phone") String phone) {
		
		log.info("전화번호 중복 점검 Rest Service phone : " + phone);
		
		try {
			if(memberService.isPhone(phone) == true) {
				
				return new ResponseEntity<String>("있는 전화번호", HttpStatus.OK);
				
			} else {
				
				return new ResponseEntity<String>("없는 전화번호", HttpStatus.OK);
			}
			
		} catch(Exception e) {
			
			log.error("phoneCheck REST Service error : " + e.getMessage());
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
			//code 417 : 서버는 Expext 요청 헤더 입력란의 요구사항을 만족할 수 없다.(null...?) 즉, 예상 실패이니 error
		}
	}	

	@GetMapping(value="update_role", produces="text/plain; charset=UTF-8")
	public ResponseEntity<String> changeRole(@RequestParam("id") String id,
											 @RequestParam("role") String role) {
		
		log.info("개별 회원 롤 수정 : id={}, role={}", id, role);
		
		try {
			if(memberService.activeMemberRole(id, role, "y") == true) {
				
				return new ResponseEntity<String>("role 정보 수정 성공", HttpStatus.OK);
				
			} else {
				
				return new ResponseEntity<String>("role 정보 수정 실패", HttpStatus.OK);
			}
			
		} catch(Exception e) {
			
			log.error("changeRole REST Service error : " + e.getMessage());
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@GetMapping(value="member_view", produces="application/json; charset=UTF-8")
	public ResponseEntity<MemberVO> memberView(@RequestParam("id") String id) {
		
		log.info("개별 회원 정보 조회 Rest Service email : " + id);
		MemberVO memberVO = null;
		
		try {
			
			memberVO = memberService.getMember(id);	
			
			if(memberVO != null) {
				
				return new ResponseEntity<MemberVO>(memberVO, HttpStatus.OK);
				
			} else {
				
				return new ResponseEntity<MemberVO>(memberVO, HttpStatus.OK);
			}
			
		} catch(Exception e) {
			
			log.error("emailCheck REST Service error : " + e.getMessage());
			return new ResponseEntity<MemberVO>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@GetMapping(value="email_check_update", produces="text/plain; charset=UTF-8")
	public ResponseEntity<String> emailCheckOnUpdate(@RequestParam("id") String id,
													 @RequestParam("email") String email) {
		
		log.info("이메일 중복 점검 (수정) Rest Service id={}, email={} : ", id, email);
		
		try {
			
			if (memberService.isEmailOnUpdate(id, email) == false) {
				
				return new ResponseEntity<String>("사용 가능한 이메일", HttpStatus.OK);
				
			} else {
			
				return new ResponseEntity<String>("중복 이메일 존재", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			
			log.error("emailCheckOnUpdate REST Service error : " + e.getMessage());
			
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
		}
		
	}
	
	@GetMapping(value="email_update", produces="text/plain; charset=UTF-8")
	public ResponseEntity<String> emailUpdate(@RequestParam("id") String id,
											  @RequestParam("email") String email) {
		
		log.info("이메일 수정 Rest Service id={}, email={} : ", id, email);
		
		try {
			
			if (memberService.upateMemberByAdmin(id, "email", email) == true) {
				
				return new ResponseEntity<String>("수정 완료.", HttpStatus.OK);
			
			} else {
				
				return new ResponseEntity<String>("수정 실패.", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			
			log.error("emailUpdate REST Service error : " + e.getMessage());
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
		}
		
	}
	
	@GetMapping(value="phone_check_update", produces="text/plain; charset=UTF-8")
	public ResponseEntity<String> phoneCheckOnUpdate(@RequestParam("id") String id,
													 @RequestParam("phone") String phone) {
		
		log.info("연락처 중복 점검 (수정) Rest Service id={}, phone={} : ", id, phone);
		
		try {
			
			if (memberService.isPhoneOnUpdate(id, phone) == false) {
				
				return new ResponseEntity<String>("사용 가능한 연락처", HttpStatus.OK);
				
			} else {
				
				return new ResponseEntity<String>("중복 연락처 존재", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			log.error("phoneCheckOnUpdate REST Service error : " + e.getMessage());
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
		}
		
	}
	
	@GetMapping(value="phone_update", produces="text/plain; charset=UTF-8")
	public ResponseEntity<String> phoneUpdate(@RequestParam("id") String id,
											  @RequestParam("phone") String phone) {
		
		log.info("연락처 수정 Rest Service id={}, phone={} : ", id, phone);
		
		try {
			
			if (memberService.upateMemberByAdmin(id, "phone", phone) == true) {
				
				return new ResponseEntity<String>("수정 완료.", HttpStatus.OK);
				
			} else {
				
				return new ResponseEntity<String>("수정 실패.", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			log.error("phoneUpdate REST Service error : " + e.getMessage());
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
		}
		
	}
	
	@GetMapping(value="zip_address_update", produces="text/plain; charset=UTF-8")
	public ResponseEntity<String> zipAddress1Update(@RequestParam("id") String id,
												    @RequestParam("zipNum") String zipNum,
												    @RequestParam("address1") String address1,
												    @RequestParam("address2") String address2) {
		
		log.info("우편번호/기본주소 수정 Rest Service id={}, zipNum={}, address1={}, address2={}: ", id, zipNum, address1, address2);
		
		try {
			
			if (memberService.upateMemberByAdmin(id, "zip_num", zipNum) == true && 
				memberService.upateMemberByAdmin(id, "address1", address1) == true && 
				memberService.upateMemberByAdmin(id, "address2", address2) == true) {
				
				return new ResponseEntity<String>("수정 완료.", HttpStatus.OK);
				
			} else {
				
				return new ResponseEntity<String>("수정 실패.", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			log.error("zipAddress1Update REST Service error : " + e.getMessage());
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
		}
		
	}
	
	@GetMapping(value="address2_update", produces="text/plain; charset=UTF-8")
	public ResponseEntity<String> address2Update(@RequestParam("id") String id,
												 @RequestParam("address2") String address2) 
	{
		log.info("상세주소 수정 Rest Service id={}, address2={} : ", id, address2);
		
		try {
			
			if (memberService.upateMemberByAdmin(id, "address2", address2) == true) {
				
				return new ResponseEntity<String>("수정 완료.", HttpStatus.OK);
				
			} else {
				
				return new ResponseEntity<String>("수정 실패.", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			
			log.error("address2Update REST Service error : " + e.getMessage());
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
		}
		
	}
	
	@GetMapping(value="member_inactive_admin", produces="text/plain; charset=UTF-8")
	public ResponseEntity<String> memberInactiveByAdmin(@RequestParam("id") String id) 
	{
		log.info("UpdateUseYN");
		String msg = "";
		
		try {
			
			if (memberService.inactiveMemberRole(id) == true) 
			{
				return new ResponseEntity<String>("비활성화 완료.", HttpStatus.OK);
				
			} else {
				
				return new ResponseEntity<String>("비활성화 실패.", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			
			log.error("memberInactiveByAdmin REST Service error : " + e.getMessage());
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
		}
		
	} //
}
