package com.javateam.project.repository;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.javateam.project.domain.CartVO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class CartDAO {

	@Inject
	SqlSession sqlSession;
	
	private static final String MAPPER_NS = "com.javateam.project.mapper.Cart.";
	
	/**
	 * 장바구니에 상품 담기 
	 * 
	 * @param cartVO 장바구니 VO객체
	 * @return 장바구니 담기 성공 여부
	 */
	public boolean insertCart(CartVO cartVO) {
		
		
		log.info("CartDAO.insertCart");
		
		return sqlSession.insert(MAPPER_NS + "insertCart", cartVO) == 1? true : false;
	}
	
	/**
	 * 전체 장바구니 목록 조회
	 * 
	 * @param id 주문자(사용자) ID
	 * @return 카트 목록
	 */
	public List<CartVO> listCart(String id) {
		
		log.info("CartDAO.listCart");
		
		return sqlSession.selectList(MAPPER_NS + "listCart", id);
	}
	
	/**
	 * 장바구니 수정
	 * 
	 * @param cartVO 장바구니 VO 객체
	 * @return 장바구니 수정 성공 여부
	 */
	public boolean updateCart(CartVO cartVO) {
		
		log.info("CartDAO.updateCart");
		
		return sqlSession.update(MAPPER_NS + "updateCart", cartVO) == 1 ? true : false;
	}
	
	/**
	 * 장바구니 삭제
	 * 
	 * @param cseq
	 * @return 장바구니 삭제 성공 여부
	 */
	public boolean deleteCart(int cseq) {
		
		log.info("CartDAO.deleteCart");
		
		return sqlSession.update(MAPPER_NS + "deleteCart", cseq) == 1 ? true : false;
	}
}
