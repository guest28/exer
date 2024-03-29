package com.javateam.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.javateam.project.domain.ProductVO;
import com.javateam.project.domain.SearchProductVO;
import com.javateam.project.repository.ProductDAO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {
	
	@Autowired
	ProductDAO dao;
	
	@Autowired
	TransactionTemplate txTemplate;

	/**
	 * 베스트 상품 리스트 출력
	 * 
	 * @return 상품 객체 리스트
	 */

	@Transactional(readOnly=true)
	public List<ProductVO> listBestProduct() {
		
		return txTemplate.execute(txStatus-> { 
			return dao.listBestProduct(); 
		});
	}
	
	/**
	 * 신상품 리스트 출력
	 * 
	 * @return 상품 객체 리스트
	 */
	@Transactional(readOnly=true)
	public List<ProductVO> listNewProduct() {
			
		return txTemplate.execute(txStatus-> { 
			return dao.listNewProduct(); 
		});			
	}
	
	/**
	 * 종류별 상품 리스트 출력
	 * 
	 * @param kind 상품 종류
	 * @return 상품 객체 리스트
	 */
	@Transactional(readOnly=true)
	public List<ProductVO> listKindProduct(int kind) {
		
		return txTemplate.execute(txStatus-> { 
			return dao.listKindProduct(kind); 
		});			
	}
	
	/**
	 * 개별 상품 출력(조회)
	 * 
	 * @param pseq 상품 아이디
	 * @return 상품 객체
	 */
	@Transactional(readOnly=true)
	public ProductVO getProduct(int pseq) {
		
		return txTemplate.execute(txStatus-> {
			return dao.getProduct(pseq); 
		});			
	}
	
	/**
	 * 개별 상품 등록
	 * 
	 * @param productVO 상품 객체
	 * @return 등록 성공 여부
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean insertProduct(final ProductVO productVO) {
		
		log.info("ProductService.insertProduct");
		
		return txTemplate.execute(status -> {
				
				boolean flag = false;
				
				try {
					
					if (dao.insertProduct(productVO) == true) {
						log.info("상품 정보 저장 성공");
						flag = true;
					} else {
						log.info("상품 정보 저장 실패");
						throw new Exception("상품 정보 저장 실패");
					}
					
				} catch (Exception e) {					
					status.setRollbackOnly();
					flag = false;
					log.error("ProductService.insertProduct error : " + e.getMessage());
				} //
				
				return flag;
		});
		
	} //
	
	/**
	 * 최근 등록 상품 아이디 조회
	 * 
	 * @return 최근 등록 상품 아이디
	 */
	@Transactional(readOnly=true)
	public int getMaxPseq() {
		
		return txTemplate.execute(txStatus-> { 
			return dao.getMaxPseq(); 
		});
		
	}
	
	/**
	 * 개별 상품 수정
	 * 
	 * @param productVO 상품 객체
	 * @return 수정 성공 여부
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean updateProduct(final ProductVO productVO) {
		
		log.info("ProductService.updateProduct");
		
		return txTemplate.execute(status -> {
				
				boolean flag = false;
				
				try {
					
					if (dao.updateProduct(productVO) == true) {
						log.info("상품 정보 수정 성공");
						flag = true;
					} else {
						log.info("상품 정보 수정 실패");
						throw new Exception("상품 정보 수정 실패");
					}
					
				} catch (Exception e) {					
					status.setRollbackOnly();
					flag = false;
					log.error("ProductService.updateProduct error : " + e.getMessage());
				} //
				
				return flag;
		});
		
	} //
	
	/**
	 * 전체 상품 조회 (페이징 적용)
	 * 
	 * @param page 현재 페이지
	 * @param limit 페이지당 자료(상품) 수
	 * @return 상품 정보 리스트
	 */
	@Transactional(readOnly=true)
	public List<ProductVO> getProductsbyPaging(int page, int limit) {
		
		log.info("ProductService.getproductsByPaging");
		
		return txTemplate.execute(txStatus-> { 
			return dao.getProductsByPaging(page, limit); 
		});
	}
	
	/**
	 * 총 상품 수 조회
	 * 
	 * @return 총 상품 수
	 */
	@Transactional(readOnly=true)
	public int getTotalProductsCount() {
		 
		log.info("ProductService.getTotalProductsCount");
		
		return txTemplate.execute(txStatus-> { 
			return dao.getTotalProductsCount();
		});
	}
	
	/**
	 * 관리자 모드에서 개별 상품 상타(사용/베스트 상품 여부) 수정
	 * 
	 * @param fld 수정할 필드 : useyn
	 * @param fldVal 수정할 필드 값 : n
	 * @param pseq 상품 시퀀스
	 * @return 수정 성공 여부
	 */
	public boolean updateFldByPseq(String fld, String fldVal, int pseq) {

		log.info("ProductService.updateFldByPseq");
		
		return txTemplate.execute(status -> {
				
				boolean flag = false;
				
				try {
					
					if (dao.updateFldByPseq(fld, fldVal, pseq) == true) {
						log.info("상품 정보(사용/베스트 여부) 수정 성공");
						flag = true;
						
					} else {
						log.info("상품 정보(사용/베스트 여부) 수정 실패");
						throw new Exception("상품 정보(사용/베스트 여부) 수정 실패");
					}
					
				} catch (Exception e) {					
					status.setRollbackOnly();
					flag = false;
					log.error("ProductService.updateFldByPseq error : " + e.getMessage());
				} //
				
				return flag;
		});
	}
	
	/**
	 * 상품 검색 (페이징)
	 * 
	 * @param seachProductVO 검색 VO
	 * @return 검색 상품들
	 */
	@Transactional(readOnly=true)
	public List<ProductVO> searchProductsByPaging(SearchProductVO searchProductVO) {
		
		log.info("ProductService.getproductsByPaging");
		
		return txTemplate.execute(txStatus-> { 
			return dao.searchProductsByPaging(searchProductVO); 
		});
	}
	
	/**
	 * 검색 (페이징) 총 상품수
	 * 
	 * @param searchProductVO 검색 VO
	 * @return 총 검색 상품수
	 */
	@Transactional(readOnly=true)
	public int getCountSearchProductsByPaging(SearchProductVO searchProductVO) {
		
		log.info("ProductService.getCountSearchProductsByPaging");
		
		return txTemplate.execute(txStatus-> { 
			return dao.getCountSearchProductsByPaging(searchProductVO);
		});
	}
	
	
}
