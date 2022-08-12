package com.javateam.project.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.javateam.project.domain.QnaVO;
import com.javateam.project.repository.QnaDAO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QnaService {

	@Inject
	QnaDAO qnaDAO;
	
	@Inject
	TransactionTemplate txTemplate;
	
	
	/**
	 * 개인 게시글 목록 조회
	 * 
	 * @param id
	 * @return 게시글 리스트
	 */
	public List<QnaVO> listQna(String id) {

		log.info("QnaService.listQna");
		
		return txTemplate.execute(txStatus-> { 
			return qnaDAO.listQna(id); 
		});
		
	}
	
	/**
	 * 회원 개별 게시글 조회
	 *
	 * @param qseq
	 * @return 게시글 VO 객체
	 */
	public QnaVO getQna(int qseq) {
	
		log.info("QnaService.getQna");
		
		return txTemplate.execute(txStatus-> { 
			return qnaDAO.getQna(qseq); 
		});
	}
	

	/**
	 * 회원 개별 게시글 작성
	 * 
	 * @param qnaVO
	 * @return 게시글 VO 객체
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public boolean insertQna(QnaVO qnaVO) {
		
		log.info("QnaService.insertQna");
	
		return txTemplate.execute(txStatus-> { 
			
			boolean flag = true;
			
			if(qnaDAO.insertQna(qnaVO) == true) {
				
				log.info("게시글 저장 성공");
				
				flag = true;
				
			} else {
				
				log.info("게시글 저장 실패");
				
				flag = false;
			}
			
			return flag; 
		});
	}
	
	/**
	 * 관리자 : 게시글 목록 조회(페이징)
	 * 
	 * @param page 현재 페이지
	 * @param limit 페이지 당 게시글 수
	 * @return 게시글 목록
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<QnaVO> listQnaByPaging(int page, int limit) {
		
		log.info("QnaService.listQnaByPaging");
		
		return txTemplate.execute(txStatus-> { 
			return qnaDAO.listQnaByPaging(page, limit); 
		});
	}
	
	/**
	 * 관리자 : 총 QnA 수
	 * 
	 * @return QnA 목록
	 */
	
	public int getTotalQnasCount() {
		
		log.info("QnaService.getTotalQnasCount");
		
		return txTemplate.execute(txStatus-> { 
			return qnaDAO.getTotalQnasCount(); 
		});
	}
	
	/**
	 * 관리자 : 개별 게시글 수정(댓글/답글 작성)
	 * 
	 * @param qseq
	 * @param reply
	 * @return
	 */
	public boolean updateQna(int qseq, String reply) {
		
		log.info("QnaService.updateQna");
		
		return txTemplate.execute(txStatus-> { 
			
			boolean flag = true;
			
			if(qnaDAO.updateQna(qseq, reply) == true) {
				
				log.info("답글 저장/수정 성공");
				
				flag = true;
				
			} else {
				
				log.info("답글 저장/수정 실패");
				
				flag = false;
			}
			
			return flag; 
		});
	}
	
}
	
