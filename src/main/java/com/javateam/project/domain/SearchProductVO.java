package com.javateam.project.domain;

import lombok.Data;

@Data
public class SearchProductVO {

	/** 검색 키워드 : kind */
	private String searchFld;
	
	/** 검색 키워드 값  : 1 */
	private String searchFldVal;
	
	/** 검색어 */
	private String searchWord;
	
	/** 페이지 */
	private int page;
	
	/** 페이지당 레코드 수 */
	private int limit;
	
}
