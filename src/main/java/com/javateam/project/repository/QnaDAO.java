package com.javateam.project.repository;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.javateam.project.domain.QnaVO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class QnaDAO {
	
	private static final String MAPPER_NS = "com.javateam.project.mapper.QnA.";

	@Inject
	SqlSession sqlSession;
	
	
	/**
	 * 개인 게시글 목록 조회
	 * 
	 * @param id
	 * @return 게시글 리스트
	 */
	public List<QnaVO> listQna(String id) {
		
		log.info("QnaDAO.listQna ----- id : " + id);
		
		return sqlSession.selectList(MAPPER_NS + "listQna", id);
	}
	
	/**
	 * 회원 개별 게시글 조회
	 *
	 * @param qseq
	 * @return 게시글 VO 객체
	 */
	public QnaVO getQna(int qseq) {
		
		log.info("QnaDAO.getQna");
		
		return (QnaVO)sqlSession.selectOne(MAPPER_NS + "getQna", qseq);		//리턴 값이 object라 (QnaVO)를 붙인다
	}
	
	/**
	 * 회원 개별 게시글 작성
	 * 
	 * @param qnaVO
	 * @return 게시글 VO 객체
	 */
	public boolean insertQna(QnaVO qnaVO) {
		
		log.info("QnaDAO.insertQna");
		
		return sqlSession.insert(MAPPER_NS + "insertQna", qnaVO) ==1 ? true : false;
	}
	
	/**
	 * 관리자 : 게시글 목록 조회(페이징)
	 * 
	 * @param page 현재 페이지
	 * @param limit 페이지 당 게시글 수
	 * @return 게시글 목록
	 */
	public List<QnaVO> listQnaByPaging(int page, int limit) {
		
		log.info("QnaDAO.listQnaByPaging");
		
		HashMap<String, Integer> map = new HashMap<>();
		
		map.put("page", page);
		map.put("limit", limit);
		
		return sqlSession.selectList(MAPPER_NS +"listQnaByPaging", map);
	}
	
	/**
	 * 관리자 : 총 QnA 수
	 * 
	 * @return QnA 목록
	 */
	public int getTotalQnasCount() {
		
		log.info("QnaDAO.getTotalQnasCount");
		
		return sqlSession.selectOne(MAPPER_NS +"getTotalQnasCount");
	}
	
	/**
	 * 관리자 : 개별 게시글 수정(댓글/답글 작성)
	 * 
	 * @param qseq
	 * @param reply
	 * @return
	 */
	public boolean updateQna(int qseq, String reply) {
		
		log.info("updateQna");
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("qseq", qseq);
		map.put("reply", reply);
		
		
		
		return sqlSession.update(MAPPER_NS + "updateQna", map) == 1 ? true : false;
		
	}
}
