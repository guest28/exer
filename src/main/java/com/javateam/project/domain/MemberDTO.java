package com.javateam.project.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

	private String id;
	
	@Pattern(regexp="\\s{1,20}||(?=.*[a-zA-Z])((?=.*\\d)(?=.*\\W)).{8,20}",		//공백일 경우도 통과
			 message="비밀번호는 영문/숫자 조합에 특수문자 1개 이상을 넣어 8~20글자로 입력하시오.")
	private String pwd;
	
	private String pwdCheck;
	private String name;
	
	@Email(message="올바른 이메일 형식이 아닙니다.")
	private String email;
	
	@Pattern(regexp="01\\d{1}-\\d{3,4}-\\d{4}",
			 message="010-1234-5678 형식으로 입력하시오.")
	private String phone;
	
	private String zipNum;
	private String address1;
	private String address2;
	private String useyn;
	private Timestamp indate;
	
}
