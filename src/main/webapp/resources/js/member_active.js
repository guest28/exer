//플래그 변수
var id_check_flag = false;
var pwd_check_flag1 = false;
var pwd_check_flag2 = false;
var name_check_flag = false;
var email_check_flag = false;
var phone_check_flag = false;


window.onload = function() {
	
	//필수 항목 변수
	var join_id = document.querySelector("form#member_active input#id");
	var join_pwd = document.querySelector("form#member_active input#pwd");
	var join_email = document.querySelector("form#member_active input#email");
	var join_phone = document.querySelector("form#member_active input#phone");
	
	//비밀번호 유효성 점검
	join_pwd.onblur = function() {
		
		console.log("비밀번호 유효성 점검")
		
		console.log("pwd : " + join_pwd.value);
		
		var regex_pwd = new RegExp(/^(?=.*[a-zA-Z])((?=.*\d)(?=.*\W)).{8,20}$/);
		
		if (regex_pwd.test(join_pwd.value) == false) {
			
			console.log("비밀번호 무효");
			
			document.getElementById("pwd_err_msg1").innerHTML = join_pwd.title;	//에러 메세징
			
			pwd_check_flag1 = false;
			
			console.log("재입력");
			join_pwd.value = "";
		
		} else {
			
			console.log("비밀번호 유효")
			
			document.getElementById("pwd_err_msg1").innerHTML = "";	//에러 메세징 초기화
			
			pwd_check_flag1 = true;
		}
	}
	//이메일 점검	
	join_email.onblur = function() {
		
		console.log("이메일 : " + join_email.value);
		
		var email = document.getElementById("email");
		
		const regex_email = new RegExp(/^[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/);
		//bar or let 써도 되지만 const는 상수화가 됨
		
		//유효성 점검
		if(regex_email.test(join_email.value) == true) {
			
			console.log("이메일 점검 통과")
			
			document.getElementById("email_err_msg").innerHTML = "";

			email_check_flag = true;
			
		
		} else {
			
			console.log("이메일 점검 오류")
			
			document.getElementById("email_err_msg").innerHTML = join_email.title;
			
			email_check_flag = false;
			
			console.log("재입력");
			join_email.value = "";
		}
	}
	
	join_phone.onblur = function() {
		
		console.log("전화번호 값 : " + join_phone.value);
		
		const regex_phone = new RegExp(/^01\d{1}-\d{3,4}-\d{4}$/);
		//bar or let 써도 되지만 const는 상수화가 됨
		
		//유효성 점검
		if(regex_phone.test(join_phone.value) == true) {
			
			console.log("전화번호 점검 통과")
			
			document.getElementById("phone_err_msg").innerHTML = "";

			phone_check_flag = true;
			
			
		} else {
			
			console.log("전화번호 점검 오류")
			
			document.getElementById("phone_err_msg").innerHTML = join_phone.title;
			
			phone_check_flag = false;
			
			console.log("재입력");
			join_phone.value = "";
		}
	}
	
	
	//회원 정보 전송(가입)
	active_submit.onclick = function() {
		
		console.log("회원 정보 전송(가입)");
		console.log("아이디 필드 점검 플래그 : " + id_check_flag);
		console.log("패쓰워드 필드 유효성 점검 플래그 : " + pwd_check_flag1);
		console.log("메일 필드 점검 플래그 : " + email_check_flag);
		console.log("전화번호 필드 점검 플래그 : " + phone_check_flag);	
		
		//필수 항목 점검
		if (id_check_flag == true &&
			pwd_check_flag1 == true &&
			email_check_flag == true &&
			phone_check_flag == true) {
		
			console.log("폼 점검 완료");
			
			console.log("폼 점검 미완료");

		}
	}
}