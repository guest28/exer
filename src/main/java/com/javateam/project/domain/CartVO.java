package com.javateam.project.domain;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartVO {

	private int cseq;
	private String id; 
	private int pseq;
	private String mname;
	private String pname;
	private int quantity;
	private int price2;
	private Timestamp indate;
	private int result;		//추가 : 주문 처리 결과(1 = 미처리, 2 = 처리)
	
	/*	
	//수업 내용
	@Override
	public boolean equals(Object obj) {
		
		int flag = 0;
		CartVO other = (CartVO) obj;
		
		// 동일한 주문자 인지 점검
		if (this.id.equals(other.id)) {
			flag ++;
		}
		
		// 동일한 상품 인지 점검
		if (this.pseq == other.pseq) {
			flag ++;
		}
		
		// 주문 미처리(미완료) 상태인지 점검
		if (this.result == 1 && other.result == 1)  {
			flag++;
		}
		
		return  flag == 3 ? true : false;
	}
	*/

	/*	
	//될까? => 된다
	@Override
	public boolean equals(Object obj) {
		
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		CartVO other = (CartVO) obj;
		
		if (this.id.equals(other.id)) {
			
			flag1 = true;
			
		} else {
			
			flag1 = false;
		}

		if(this.pseq == other.pseq) {
			
			flag2 = true;
		
		} else {
			
			flag2 = false;
		}
		
		if((this.result == 1) && (other.result == 1)) {
			
			flag3 = true;
		
		} else {
			
			flag3 = false;
		}
		
		if((flag1 == true) && (flag2 == true) && (flag3 = true)) {
		
			flag = true;
		}
		
		return flag;
	}
	*/
		

		
	//될까? => 된다
	@Override
	public boolean equals(Object obj) {
		
		boolean flag = false;
		CartVO other = (CartVO) obj;
		
		if(this.id.equals(other.id)) {
			
			if(flag = true) {
				
				if(this.pseq == other.pseq) {
					
					if(flag = true) {
						
						if((this.result == 1) && (other.result == 1)) {
							
							flag = true;
							
						} else {
							flag = false;
						}
					}
				} else {
					flag = false;
				}
			}
		} else {
			flag = false;
		}
		return flag;
	}
	
	
	

}
