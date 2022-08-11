package com.javateam.project.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor		//Generate Constructor Using Filed를 쓰려면 잠시 생략했다가 다시 복구시키자??
@NoArgsConstructor
public class MemberVO {

	private String id;
	private String pwd;
	private String name;
	private String email;
	private String phone;
	private String zipNum;
	private String address1;
	private String address2;
	private String useyn;
	private Timestamp indate;
	
	//복사 생성자 기법 : DTO => VO
	//Source => Generate Constructor Using Filed를 쓰면 편함
	public MemberVO(MemberDTO memberDTO) {
		
		this.id = memberDTO.getId();
		this.pwd = memberDTO.getPwd();
		this.name = memberDTO.getName();
		this.email = memberDTO.getEmail();
		this.phone = memberDTO.getPhone();
		this.zipNum = memberDTO.getZipNum();
		this.address1 = memberDTO.getAddress1();
		this.address2 = memberDTO.getAddress2();
		this.useyn = memberDTO.getUseyn();
		this.indate = memberDTO.getIndate();
	}
}
