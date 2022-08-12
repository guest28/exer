package com.javateam.project.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javateam.project.domain.MemberRoleVO;
import com.javateam.project.domain.MemberVO;
import com.javateam.project.domain.RoleVO;
import com.javateam.project.domain.SearchVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class MemberDAO {

	@Autowired
	SqlSession sqlSession;
	
	/**
	 * 아이디 존재 여부 점검
	 * 
	 * @param id
	 * @return 존재 여부
	 */	
	public boolean isMember(String id) {
		
		log.info("MemberDAO.isMember");
		return (int)sqlSession.selectOne("com.javateam.project.mapper.Member.isMember", id) == 1 ? true : false;
		
	}

	/**
	 * 이메일 존재 여부 점검
	 * 
	 * @param email
	 * @return 존재 여부
	 */	
	public boolean isEmail(String email) {
		
		log.info("MemberDAO.isEmail");
		return (int)sqlSession.selectOne("com.javateam.project.mapper.Member.isEmail", email) == 1 ? true : false;
		
	}
	
	/**
	 * 연락처 존재 여부 점검
	 * 
	 * @param phone
	 * @return 존재 여부
	 */	
	public boolean isPhone(String phone) {
		
		log.info("MemberDAO.isPhone");
		return (int)sqlSession.selectOne("com.javateam.project.mapper.Member.isPhone", phone) == 1 ? true : false;
		
	}
	
	/**
	 * 개별 회원 정보 생성(회원 가입)
	 * 
	 * @param memberVO 회원 정보 객체
	 */
	public void insertMember(MemberVO memberVO) {
		
		log.info("MemberDAO.insertMember");
		sqlSession.insert("com.javateam.project.mapper.Member.insertMember", memberVO);
	}
	
	/**
	 * 회원 정보 역할(role) 생성/삽입
	 * 
	 * @param roleVO 롤 정보 객체
	 */
	public void insertRole(RoleVO roleVO) {
		
		log.info("MemberDAO.insertRole");
		sqlSession.insert("com.javateam.project.mapper.Member.insertRole", roleVO);
	}
	
	/**
	 * 회원 아이디/비밀번호 일치 여부 점검 : ex)로그인
	 * 
	 * @param id
	 * @param pwd
	 * @return 아이디/비밀번호 일치 회원 여부
	 */
	public boolean isMemberByIdPwd(String id, String pwd) {
		
		log.info("MemberDAO.isMemberByIdPwd");
		
		MemberVO member = new MemberVO();
		member.setId(id);
		member.setPwd(pwd);
		
		return (int)sqlSession.selectOne("com.javateam.project.mapper.Member.isMemberByIdPwd", member) == 1 ? true : false;
	}
	
	/**
	 * 아이디에 따른 개별 회원 전체 정보 조회
	 * 
	 * @param id
	 * @return 회원 정보 객체
	 */
	public MemberVO getMember(String id) {
		
		log.info("MemberDAO.getMember");
		
		return sqlSession.selectOne("com.javateam.project.mapper.Member.getMember", id);
	}
	
	/**
	 * 개별 회원 정보 수정(갱신) : update
	 * 
	 * @param memberVO
	 */
	public void updateMember(MemberVO memberVO) {
		
		log.info("MemberDAO.updateMember");
		
		sqlSession.update("com.javateam.project.mapper.Member.updateMember", memberVO);
		
	}
	
	/**
	 * 이메일 중복 점검 : 기존의 이메일을 제외한 나머지 이메일과의 중복 점검 
	 * 
	 * @param id
	 * @param email
	 * @return 존재 여부
	 */
	public boolean isEmailOnUpdate(String id, String email) {
		
		log.info("MemberDAO.isEmailOnUpdate");
		
		HashMap<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("email", email);
		
		return (int)sqlSession.selectOne("com.javateam.project.mapper.Member.isEmailOnUpdate", map) == 1 ? true : false;
	}
	
	/**
	 * 연락처 중복 점검 : 기존의 연락처를 제외한 나머지 연락처와의 중복 점검 
	 * 
	 * @param id
	 * @param phone
	 * @return 존재 여부
	 */
	public boolean isPhoneOnUpdate(String id, String phone) {
		
		log.info("MemberDAO.isPhoneOnUpdate");
		
		HashMap<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("phone", phone);
		
		return (int)sqlSession.selectOne("com.javateam.project.mapper.Member.isPhoneOnUpdate", map) == 1 ? true : false;
	}
	
	/**
	 * 회원 탈퇴  처리 : 삭제가 아닌 휴면 상태로 => useyn = 'n'
	 * 
	 * @param id
	 */
	public void inactiveMember(String id) {
		
		log.info("MemberDAO.inactiveMember");
		
		sqlSession.update("com.javateam.project.mapper.Member.inactiveMember", id);
	}
	
	/**
	 * 회원 탈퇴  처리 : role = 'guest'
	 * 
	 * @param id
	 */
	public void inactiveRole(String id) {
		
		log.info("MemberDAO.inactiveRole");
		
		sqlSession.update("com.javateam.project.mapper.Member.inactiveRole", id);
	}
	
	/**
	 * 회원 활성화 여부 점검 : 활동/탈퇴 회원 점검
	 * 
	 * @param id
	 * @return 활동 회원("y") 여부 : true = 회원(user), false = 탈퇴 회원(guest)
	 */
	public boolean isActiveMember(String id) {
		
		log.info("MemberDAO.isActiveMember");
		
		return sqlSession.selectOne("com.javateam.project.mapper.Member.isActiveMember", id).equals("y") ? true : false;
	}
	
	/**
	 * 회원 활성  처리 : useyn = 'y'
	 * 
	 * @param id
	 */
	public void activeMember(String id, String useyn) {
		
		log.info("MemberDAO.activeMember");
		
		Map<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("useyn", useyn);
		
		sqlSession.update("com.javateam.project.mapper.Member.activeMember", map);
	}
	
	/**
	 * 회원 활성  처리 : role = 'guest'
	 * 
	 * @param id
	 */
	public void activeRole(String id, String role) {
		
		log.info("MemberDAO.activeRole");
		
		Map<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("role", role);
		
		sqlSession.update("com.javateam.project.mapper.Member.activeRole", map);
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
	public boolean checkInactiveMember(final MemberVO memberVO) {
		
		log.info("MemberDAO.checkInactiveMember");
		
		return (int)sqlSession.selectOne("com.javateam.project.mapper.Member.checkInactiveMember", memberVO) == 1 ? true : false;
	}
	
	/**
	 * 개별 회원 롤 정보 조회
	 * 
	 * @param id
	 * @return
	 */
	public String getRole(final String id) {
		
		log.info("MemberDAO.getRole");
		
		return sqlSession.selectOne("com.javateam.project.mapper.Member.getRole", id);
	}
	
	/**
	 * 전체 회원 조회(role 포함, 페이징 적용)
	 * 
	 * @param page
	 * @param lmilt
	 * @return 인원 정보 리스트
	 */
	public List<MemberRoleVO> getMembersByPaging(int page, int limit) {
		
		log.info("MemberDAO.getMembersByPaging");

		Map<String, Integer> map = new HashMap<>();
		map.put("page", page);
		map.put("limit", limit);
		
		return sqlSession.selectList("com.javateam.project.mapper.Member.getMembersByPaging", map);
	}
	
	/**
	 * 총 회원 수
	 * 
	 * @return 총 회원 수
	 */
	public int getTotalMembersCount() {
		
		log.info("MemberDAO.getTotalMembersCount");
		
		return sqlSession.selectOne("com.javateam.project.mapper.Member.getTotalMembersCount");
	}
	
	/**
	 * 검색(페이징 적용)
	 * 
	 * @param searchVO
	 * @return 검색 결과(회원 정보)
	 */
	public List<MemberRoleVO> searchMembersByPaging(SearchVO searchVO) {
		
		log.info("MemberDAO.searchMembersByPaging");
		
		//검색 키워드(구분) 
		//1) 아이디
		if (searchVO.getSearchFld().equals("id")) {
			
			searchVO.setIsLike("=");
			searchVO.setSearchFld("m.id");
		
		//2) 이름/기본주소/상세주소 검색
		} else {
			
			searchVO.setIsLike("like");
			searchVO.setSearchWord("%"+searchVO.getSearchWord()+"%");
		}
		
		return sqlSession.selectList("com.javateam.project.mapper.Member.searchMembersByPaging", searchVO);
	}
	
	/**
	 * 검색 (페이징) 총 인원수
	 * 
	 * @param searchVO
	 * @return 총 검색 결과 수
	 */
	public int getCountSearchMembersByPaging(SearchVO searchVO) {
		
		log.info("MemberDAO.getCountSearchMembersByPaging : " + searchVO);
		
		//검색 키워드(구분) 
		//1) 아이디
		if (searchVO.getSearchFld().equals("id")) {
			
			searchVO.setIsLike("=");
			searchVO.setSearchFld("m.id");
		
		//2) 이름/기본주소/상세주소 검색
		} else {
			
			searchVO.setIsLike("like");
			searchVO.setSearchWord("%"+searchVO.getSearchWord()+"%");
		}
		
		return (int)sqlSession.selectOne("com.javateam.project.mapper.Member.getCountSearchMembersByPaging", searchVO);
	}
	
	/**
	 * 관리자 모드(개별 회원 정보 수정 : 이메일 / 연락처 / 우편번호 / 기본주소 / 상세주소)
	 * 
	 * @param id
	 * @param fld 수정할 필드
	 * @param val 수정할 필드 값
	 */
	public void updateMemberByAdmin(String id, String fld, String val) {
		
		log.info("MemberDAO.updateMemberByAdmin : id={}, fld={}, val={}", id, fld, val );
		
		Map<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("fld", fld);
		map.put("val", val);		
		sqlSession.update("com.javateam.project.mapper.Member.updateMemberByAdmin", map);
	}
}