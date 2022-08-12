//플래그 변수
var id_check_flag = false;
var pwd_check_flag1 = false;
var pwd_check_flag2 = false;
var name_check_flag = false;
var email_check_flag = false;
var phone_check_flag = false;


window.onload = function() {
	
	//필수 항목 변수
	var join_id = document.querySelector("form#join input#id");
	var join_pwd = document.querySelector("form#join input#pwd");
	var join_pwdCheck = document.querySelector("form#join input#pwdCheck");
	var join_name = document.querySelector("form#join input#name");
	var join_email = document.querySelector("form#join input#email");
	var join_phone = document.querySelector("form#join input#phone");
	
	//선택 항목 변수
	var join_address1 = document.querySelector("form#join input#address1");
	var join_address2 = document.querySelector("form#join input#address2");
	var join_zipNum = document.querySelector("form#join input#zipNum");
	
	join_id.onblur = function() {
		
		console.log("ID 값 : " + join_id.value);
		
		var id = document.getElementById("id");
		
		var regex_id = new RegExp(/^[a-zA-Z]{1}\w{7,19}$/);
		
		//유효성 점검
		if(regex_id.test(join_id.value) == true) {
			
			console.log("아이디 점검 통과")
			
			document.getElementById("id_err_msg").innerHTML = "";

			id_check_flag = true;
			//document.getElementById("id_check_flag").innerHTML = "true";
			
			//중복 점검
			$.ajax( {
				url : "../member/id_check",
				type : "get",
				data : { id : $("form#join input#id").val() },
				dataType : "text",
				success : function(data) {
					
					console.log("data : " + data);
					
					if (data.trim() == '있는 아이디') {
						
						alert("이미 존재하는 ID입니다.")
						
						id_check_flag = false;
						//$("#id_check_flag").html("false");	//플래그 모니터링에 반영
						
						console.log("재입력");
						join_id.value = "";
						
					} else {
						
						//alert("사용할 수 있는ID입니다.")
						
						id_check_flag = true;
						//$("#id_check_flag").html("true");	//플래그 모니터링에 반영
					}
				},
				error : function(xhr, status, error) {
					console.error("xhr : " + xhr);
					console.error("status : " + status);
					console.error("error : " + error);
				}
			} )
		
		} else {
			console.log("아이디 점검 오류")
			
			document.getElementById("id_err_msg").innerHTML = join_id.title;
			
			id_check_flag = false;
			//document.getElementById("id_check_flag").innerHTML = "false";
			
			console.log("재입력");
			join_id.value = "";
		}
	}
	
	//비밀번호 유효성 점검
	join_pwd.onblur = function() {
		
		console.log("비밀번호 값(유효성) : " + join_pwd.value);
		console.log("비밀번호 확인 값(유효성) : " + join_pwdCheck.value);
		
		var regex_pwd = new RegExp(/^(?=.*[a-zA-Z])((?=.*\d)(?=.*\W)).{8,20}$/);
		
		if (regex_pwd.test(join_pwd.value) == false) {
			
			console.log("비밀번호 무효");
			
			document.getElementById("pwd_err_msg1").innerHTML = join_pwd.title;	//에러 메세징
			
			pwd_check_flag1 = false;
			//document.getElementById("pwd_check_flag1").innerHTML = "false";	//플래그 모니터링에 반영
			
			console.log("재입력");
			join_pwd.value = "";
		
		} else {
			
			console.log("비밀번호 유효")
			
			document.getElementById("pwd_err_msg1").innerHTML = "";	//에러 메세징 초기화
			
			pwd_check_flag1 = true;
			//document.getElementById("pwd_check_flag1").innerHTML = "true";	//플래그 모니터링에 반영
		}
	}
	
	//비밀번호 일치 점검
	join_pwdCheck.onblur = function() {
		
		console.log("비밀번호 값(일치성) : " + join_pwd.value);
		console.log("비밀번호 확인 값(일치성) : " + join_pwdCheck.value);
		
		
		if(join_pwd.value == join_pwdCheck.value) {
			
			console.log("비밀번호 일치")
			
			document.getElementById("pwd_err_msg2").innerHTML = "";	//에러 메세징 초기화
			
			pwd_check_flag2 = true;
			//document.getElementById("pwd_check_flag2").innerHTML = "ture";	//플래그 모니터링에 반영
			
		} else {
			
			console.log("비밀번호 불일치")
			
			document.getElementById("pwd_err_msg2").innerHTML = "비밀번호가 다릅니다.";		//에러 메세징
			
			pwd_check_flag2 = false;
			//document.getElementById("pwd_check_flag2").innerHTML = "false";	//플래그 모니터링에 반영
			
			console.log("재입력");
			join_pwd.value = "";
			join_pwdCheck.value = "";
		}
	}
	
	//이름 유효성 점검
	join_name.onblur = function() {
		
		console.log("이름 값 : " + join_name.value);
		
		var regex_name = new RegExp(/^[가-힣]{2,13}$/);
		
		if(regex_name.test(join_name.value) == true) {
			
			console.log("이름 점검 통과")
			
			document.getElementById("name_err_msg").innerHTML = "";

			name_check_flag = true;
			//document.getElementById("name_check_flag").innerHTML = "true";
		
		} else {
			
			console.log("이름 점검 오류")
			
			document.getElementById("name_err_msg").innerHTML = join_name.title;
			
			name_check_flag = false;
			//document.getElementById("name_check_flag").innerHTML = "false";
			
			console.log("재입력");
			join_name.value = "";
		}
		
	}
	
	//이메일 점검	
	join_email.onblur = function() {
		
		console.log("이메일 값 : " + join_email.value);
		
		var email = document.getElementById("email");
		
		const regex_email = new RegExp(/^[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/);
		//bar or let 써도 되지만 const는 상수화가 됨
		
		//유효성 점검
		if(regex_email.test(join_email.value) == true) {
			
			console.log("이메일 점검 통과")
			
			document.getElementById("email_err_msg").innerHTML = "";

			email_check_flag = true;
			//document.getElementById("email_check_flag").innerHTML = "true";
			
			//중복 점검
			$.ajax( {
				url : "../member/email_check",
				type : "get",
				data : { email : $("form#join input#email").val() },
				dataType : "text",
				success : function(data) {
					
					console.log("data : " + data);
					
					if (data.trim() == '있는 이메일') {
						
						alert("이미 존재하는 이-메일입니다.")
						
						email_check_flag = false;
						//$("#email_check_flag").html("false");	//플래그 모니터링에 반영
						
						console.log("재입력");
						join_email.value = "";
						
					} else {
						
						//alert("사용할 수 있는 이-메일입니다.")
						
						email_check_flag = true;
						//$("#email_check_flag").html("true");	//플래그 모니터링에 반영
					}
				},
				error : function(xhr, status, error) {
					console.error("xhr : " + xhr);
					console.error("status : " + status);
					console.error("error : " + error);
				}
			} )
		
		} else {
			
			console.log("이메일 점검 오류")
			
			document.getElementById("email_err_msg").innerHTML = join_email.title;
			
			email_check_flag = false;
			//document.getElementById("email_check_flag").innerHTML = "false";
			
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
			//document.getElementById("phone_check_flag").innerHTML = "true";
			
			//중복 점검
			$.ajax( {
				url : "../member/phone_check",
				type : "get",
				data : { phone : $("form#join input#phone").val() },
				dataType : "text",
				success : function(data) {
					
					console.log("data : " + data);
					
					if (data.trim() == '있는 전화번호') {
						
						alert("이미 존재하는 전화번호입니다.")
						
						phone_check_flag = false;
						//$("#phone_check_flag").html("false");	//플래그 모니터링에 반영
						
						console.log("재입력");
						join_phone.value = "";
						
					} else {
						
						//alert("사용할 수 있는 전화번호입니다.")
						
						phone_check_flag = true;
						//$("#phone_check_flag").html("true");	//플래그 모니터링에 반영
					}
				},
				error : function(xhr, status, error) {
					console.error("xhr : " + xhr);
					console.error("status : " + status);
					console.error("error : " + error);
				}
			} )
			
		} else {
			
			console.log("전화번호 점검 오류")
			
			document.getElementById("phone_err_msg").innerHTML = join_phone.title;
			
			phone_check_flag = false;
			//document.getElementById("phone_check_flag").innerHTML = "false";
			
			console.log("재입력");
			join_phone.value = "";
		}
	}
	
	//주소 점검 : 기본+상세 모두 기입 or 모두 미기입만 통과
	join_address2.onblur = function() {
		
		console.log("주소 값 : " + join_address2.value);
		
		if ((join_zipNum.value == '' && join_address1.value =='' && join_address2.value.trim() == '') ||
			(join_zipNum.value != '' && join_address1.value !='' && join_address2.value.trim() != '')) {
			
			console.log("상세 주소 필드 유효성 점검 통과")
			
			document.getElementById("address_err_msg").innerHTML = "";

		} else {
			
			console.log("상세 주소 필드 유효성 점검 오류")
			
			if (join_zipNum.value != '' && join_address1.value !='' && join_address2.value.trim() == '') {
		
				console.log("주소 점검 오류")
				document.getElementById("address_err_msg").innerHTML = "상세 주소를 입력하시오.";

			} else if (join_zipNum.value == '' && join_address1.value =='' && join_address2.value.trim() != '') {
				
				console.log("주소 점검 오류")
				document.getElementById("address_err_msg").innerHTML = "우편번호 검색을 통해 주소(도로명)를 입력하시오.";
			
			}	
			
			document.getElementById("address2").innerHTML = "";
			
		}
	}
		
	//전체 플래그 상태
	console.log("아이디 필드 점검 플래그 : " + id_check_flag);
	console.log("패쓰워드 필드 유효성 점검 플래그 : " + pwd_check_flag1);
	console.log("패쓰워드 필드 일치 점검 플래그 : " + pwd_check_flag2);
	console.log("이름 필드 점검 플래그 : " + name_check_flag);
	console.log("메일 필드 점검 플래그 : " + email_check_flag);
	console.log("전화번호 필드 점검 플래그 : " + phone_check_flag);
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	//회원 정보 전송(가입)
	join_submit.onclick = function() {
		
		console.log("회원 정보 전송(가입)");
		console.log("아이디 필드 점검 플래그 : " + id_check_flag);
		console.log("패쓰워드 필드 유효성 점검 플래그 : " + pwd_check_flag1);
		console.log("패쓰워드 필드 일치 점검 플래그 : " + pwd_check_flag2);
		console.log("이름 필드 점검 플래그 : " + name_check_flag);
		console.log("메일 필드 점검 플래그 : " + email_check_flag);
		console.log("전화번호 필드 점검 플래그 : " + phone_check_flag);	
		
		//필수 항목 점검
		if (id_check_flag == true &&
			pwd_check_flag1 == true &&
			pwd_check_flag2 == true &&
			name_check_flag == true &&
			email_check_flag == true &&
			phone_check_flag == true) {
		
			console.log("폼 점검 완료");
			
			//부가 항목 점검 : 주소 등
			if ( (join_zipNum.value == '' && join_address1.value == '' && join_address2.value.trim() == '') ||
				 (join_zipNum.value != '' && join_address1.value != '' && join_address2.value.trim() != '') ) {
				
				//전송 여부
				console.log("가입/전송 성공");
				
				document.getElementById("join").submit();
			
			} else {
				
				//전송 여부
				console.log("가입/전송 실패");
				document.getElementById("address2").innerHTML = "";
				
				if (join_zipNum.value == '' && join_address1.value =='' && join_address2.value.trim() != '') {
					
					document.getElementById("address_err_msg").innerHTML = "상세 주소를 입력하시오.";
					
				} else if (join_zipNum.value != '' && join_address1.value !='' && join_address2.value.trim() == '') {
					
					document.getElementById("address_err_msg").innerHTML = "우편번호 검색을 통해 주소(도로명)를 입력하시오.";
				}
			}
		} else
			
			console.log("폼 점검 미완료");

		
	}
}//window.onload = function() 