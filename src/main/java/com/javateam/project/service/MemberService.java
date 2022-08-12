package com.javateam.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.javateam.project.domain.MemberRoleVO;
import com.javateam.project.domain.MemberVO;
import com.javateam.project.domain.RoleVO;
import com.javateam.project.domain.SearchVO;
import com.javateam.project.repository.MemberDAO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService {

	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	TransactionTemplate txTemplate;
	
	/**
	 * 아이디 존재 여부 점검
	 * 
	 * @param id
	 * @return 존재 여부
	 */	
	@Transactional(readOnly=true)
	public boolean isMember(String id) {
		
		log.info("MeberService.isMember");
		
			return txTemplate.execute(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
				return memberDAO.isMember(id);
				}	
			});
//			return txTemplate.execute(status -> {return memberDAO.isMember(id);});	//람다
	}
	
	/**
	 * 이메일 존재 여부 점검
	 * 
	 * @param email
	 * @return 존재 여부
	 */	
	@Transactional(readOnly=true)
	public boolean isEmail(String email) {
		
		log.info("MeberService.isEmail");
		
		return txTemplate.execute(status -> {return memberDAO.isEmail(email);});
	}
 		
	/**
	 * 연락처 존재 여부 점검
	 * 
	 * @param phone
	 * @return 존재 여부
	 */	
	@Transactional(readOnly=true)
	public boolean isPhone(String phone) {
		
		log.info("MeberService.isEmail");
		
		return txTemplate.execute(status -> {return memberDAO.isPhone(phone);});
	}
	
	/**
	 * 개별 회원 정보 생성(회원 가입) + 회원 정보 역할(role) 생성/삽입
	 * 
	 * @param memberVO
	 * @param roleVO
	 * @return 회원 정보 생성 여부
	 */
	//기본형 : try ~ catch를 쓰면 error 메세지를 띄울 수 있다
	@Transactional(propagation=Propagation.REQUIRED)
	//boolean : 회원 가입 폼과 관계 없이, 회원 가입(인자 전달) 실패 시 rollback을 하도록
	public boolean insertMember(final MemberVO memberVO, final RoleVO roleVO) {	//final : 정보를 안 건드리고 데이타를 심는 것에만 집중하겠다! => 보호
		
		log.info("MemberService.insertMember");
		
		return txTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				
				boolean flag = false;
				
				try {
					
					memberDAO.insertMember(memberVO);
					log.info("회원 정보 저장 성공");
					
					memberDAO.insertRole(roleVO);
					log.info("회원 정보 역할(role) 저장 성공");
					
					flag = true;
				
				} catch (Exception e) {
					
					status.setRollbackOnly();
					log.error("MemberService.insertMember error : " + e.getMessage());
					flag = false;
				}
				
				return flag;
			}
		});
	}
	
	/**
	 * 회원 아이디/비밀번호 일치 여부 점검 : ex)로그인, isMemberByIdPwd
	 * 
	 * @param id
	 * @param pwd
	 * @return 아이디/비밀번호 일치 회원 여부 메세지
	 */
	@Transactional(readOnly=true)
	public String checkLogin(String id, String pwd) {
		
		log.info("MemberService.checkLogin");
		
		String msg = "";
//		case1 : id 없음
//		case2 : id 있으나 pwd와 불일치
//		case3 : id 있으며 pwd와 일치 => 로그인
		
		if(memberDAO.isMember(id) == true) {
			
			if(memberDAO.isMemberByIdPwd(id, pwd) == true) {	//case3
			
				msg = "로그인 성공.";
						
			} else {	//case2
				
				msg = "비밀번호가 틀렸습니다.";
			}
		
		} else {	//case1
			
			msg = "존재하지 않는 ID입니다.";
		}
		
		return msg;
	}
	
	/**
	 * 아이디에 따른 개별 회원 전체 정보 조회
	 * 
	 * @param id
	 * @return 회원 정보 객체
	 */
	@Transactional(readOnly=true)
	public MemberVO getMember(String id) {
		
		log.info("MemberService.getMember");
		
		return txTemplate.execute(status -> {return memberDAO.getMember(id);});
	}
	
	/**
	 * 개별 회원 정보 수정(갱신) : update
	 * 
	 * @param memberVO
	 * @return 회원 정보 수정 성공 여부
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean updateMember(final MemberVO memberVO) {
		
		log.info("MemberService.updateMember");
		
		return txTemplate.execute(status -> {
			
			boolean flag = false;
			
			try {
				
				memberDAO.updateMember(memberVO);
				
				log.info("회원 정보 수정 성공");
				
				flag = true;
				
			} catch(Exception e) {
				
				status.setRollbackOnly();
				
				log.error("MemberService.updateMember error : " + e.getMessage());
				
				flag = false;
			}
			
			return flag;
		});
	}
	
	/**
	 * 이메일 중복 점검 : 기존의 이메일을 제외한 나머지 이메일과의 중복 점검 
	 * 
	 * @param email
	 * @return 존재 여부
	 */	
	@Transactional(readOnly=true)
	public boolean isEmailOnUpdate(String id, String email) {
		
		log.info("MeberService.isEmailOnUpdate");
		
		return txTemplate.execute(status -> {return memberDAO.isEmailOnUpdate(id, email);});
	}
	
	/**
	 * 연락처 중복 점검 : 기존의 연락처를 제외한 나머지 연락처와의 중복 점검 
	 * 
	 * @param phone
	 * @return 존재 여부
	 */	
	@Transactional(readOnly=true)
	public boolean isPhoneOnUpdate(String id, String phone) {
		
		log.info("MeberService.isPhoneOnUpdate");
		
		return txTemplate.execute(status -> {return memberDAO.isPhoneOnUpdate(id, phone);});
	}
	
	/**
	 * 회원 탈퇴  처리 : useyn = 'n', role = 'guest'
	 * 
	 * @param id
	 * @return 회원 탈퇴 처리 성공 여부
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean inactiveMemberRole(final String id) {
		
		log.info("MemberService.inactiveMemberRole");
		
		return txTemplate.execute(status -> {
			
			boolean flag = false;
			
			try {
				
				memberDAO.inactiveMember(id);
				log.info("회원 탈퇴(정보 비활성화) 처리 성공");

				memberDAO.inactiveRole(id);
				log.info("회원 탈퇴(롤 비활성화) 처리 성공");
				
				flag = true;
				
			} catch(Exception e) {
				
				status.setRollbackOnly();
				log.error("MemberService.inactiveMemberRole error : " + e.getMessage());
				
				flag = false;
			}
			
			return flag;
		});
	
	}
	
	/**
	 * 회원 활성화 여부 점검 : 활동/탈퇴 회원 점검
	 * 
	 * @param id
	 * @return 활동 회원("y") 여부 : true = 회원(user), false = 탈퇴 회원(guest)
	 */
	@Transactional(readOnly=true)
	public boolean isActiveMember(String id) {
		
		log.info("MeberService.isActiveMember");
		
		return txTemplate.execute(status -> {return memberDAO.isActiveMember(id);});
	}
	
	/**
	 * 개별 회원 정보 : 휴면 => 활성 시 점검
	 * 
	 * @param id
	 * @param pwd
	 * @param email
	 * @param phone
	 * @return 존재 여부
	 */
	@Transactional(readOnly=true)
	public String checkInactiveMember(final MemberVO memberVO) {
		
		log.info("MeberService.activeMember");
		
		String msg = "";
			
		if(memberDAO.checkInactiveMember(memberVO) == true) {
			
			msg = "인증 성공.";
						
		} else {
				
			msg = "인증 실패.";
		}
		
		return msg;
	}
	
	/**
	 * 회원 활성화
	 * 
	 * @param id
	 * @return 회원 활성화 : useyn = 'y', role = 'user'
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean activeMemberRole(final String id, final String role, final String useyn) {
		
		log.info("MemberService.activeMemberRole");
		
		return txTemplate.execute(status -> {
			
			boolean flag = false;
			
			try {
				
				memberDAO.activeMember(id, useyn);
				log.info("회원 활성(정보 활성화) 처리 성공");

				memberDAO.activeRole(id, role);
				log.info("회원 활성(롤 활성화) 처리 성공");
				
				flag = true;
				
			} catch(Exception e) {
				
				status.setRollbackOnly();
				log.error("MemberService.activeMemberRole error : " + e.getMessage());
				
				flag = false;
			}
			
			return flag;
		});
	}
	
	/**
	 * 개별 회원 롤 정보 조회
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public String getRole(final String id) {
		
		log.info("MemberService.getRole");	
	
		return txTemplate.execute(status -> {return memberDAO.getRole(id);});
	}
	
	/**
	 * 전체 회원 조회(role 포함, 페이징 적용)
	 * 
	 * @param page
	 * @param lmilt
	 * @return 인원 정보 리스트
	 */
	@Transactional(readOnly=true)
	public List<MemberRoleVO> getMembersByPaging(final int page, final int limit) {
		
		log.info("MemberService.getMembersByPaging");	
		
		return txTemplate.execute(status -> {return memberDAO.getMembersByPaging(page, limit);});
	}
	
	/**
	 * 총 회원 수
	 * 
	 * @return 총 회원 수
	 */
	@Transactional(readOnly=true)
	public int getTotalMembersCount() {
		
		log.info("MemberService.getTotalMembersCount");
		
		return txTemplate.execute(status -> { return memberDAO.getTotalMembersCount(); });
	}
	
	/**
	 * 검색(페이징 적용)
	 * 
	 * @param searchVO
	 * @return 검색 결과(회원 정보)
	 */
	@Transactional(readOnly=true)
	public List<MemberRoleVO> searchMembersByPaging(SearchVO searchVO) {
		
		log.info("MemberService.searchMembersByPaging");
		
		return txTemplate.execute(status -> { return memberDAO.searchMembersByPaging(searchVO); });
	}
	
	/**
	 * 검색 (페이징) 총 인원수
	 * 
	 * @param searchVO
	 * @return 총 검색 결과 수
	 */
	@Transactional(readOnly=true)
	public int getCountSearchMembersByPaging(SearchVO searchVO) {
		
		log.info("MemberService.getCountSearchMembersByPaging : " + searchVO);
		
		return txTemplate.execute(status -> { return memberDAO.getCountSearchMembersByPaging(searchVO); });
	}
	
	/**
	 * 관리자 모드(개별 회원 정보 수정 : 이메일 / 연락처 / 우편번호 / 기본주소 / 상세주소)
	 * 
	 * @param id
	 * @param fld 수정할 필드
	 * @param val 수정할 필드값
	 * @return 회원 개별 필드 수정 성공 여부
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean upateMemberByAdmin(final String id, final String fld, final String val) {
		
		log.info("MemberService.upateMemberByAdmin");
		
		return txTemplate.execute(status -> {
				
				boolean flag = false;
				
				try {
					memberDAO.updateMemberByAdmin(id, fld, val);
					log.info("회원 정보 개별 필드 수정 성공");
					
					flag = true;
				} catch (Exception e) {					
					status.setRollbackOnly();
					flag = false;
					log.error("MemberService.upateMemberByAdmin error : " + e.getMessage());
				} //
				
				return flag;
		});
	}
}