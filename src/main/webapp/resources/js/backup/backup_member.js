function go_save() {
  if (document.m.id.value == "") {
    alert("아이디를 입력하여 주세요.");
    document.formm.id.focus();
  } else if (document.formm.id.value != document.formm.reid.value) {
    alert("중복확인을 클릭하여 주세요.");
    document.formm.id.focus();
  } else if (document.formm.pwd.value == "") {
    alert("비밀번호를 입력해 주세요.");
    document.formm.pwd.focus();
  } else if ((document.formm.pwd.value != document.formm.pwdCheck.value)) {
    alert("비밀번호가 일치하지 않습니다.");
    document.formm.pwd.focus();
  } else if (document.formm.name.value == "") {
    alert("이름을 입력해 주세요.");
    document.formm.name.focus();
  } else if (document.formm.email.value == "") {
    alert("전화번호을 입력해 주세요.");
    document.formm.email.focus();
  } else {
    document.formm.action = "NonageServlet?command=join";
    document.formm.submit();
  }
}

function idcheck() {
  if (document.formm.id.value == "") {
    alert('아이디를 입력하여 주십시오.');
    document.formm.id.focus();
    return;
  }
  var url = "NonageServlet?command=id_check_form&id=" 
+ document.formm.id.value;
  window.open( url, "_blank_1",
"toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=330, height=200");
}


/*function post_zip() {
  var url = "NonageServlet?command=find_zip_num";
  window.open( url, "_blank_1",
"toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=550, height=300, top=300, left=300, ");
}*/

/*function go_next() {
  if (document.formm.okon1[0].checked == true) {
    document.formm.action = "index";			//1
    document.formm.submit();
  } else if (document.formm.okon1[1].checked == true) {
    alert('약관에 동의하셔야만 합니다.');
  }
}*/

//도로명 주소 검색
function getPostcodeAddress() {
	new daum.Postcode({
    	oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
    		var fullAddr = ''; // 최종 주소 변수
            var extraAddr = ''; // 조합형 주소 변수

            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            /*
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                fullAddr = data.roadAddress;

            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                fullAddr = data.jibunAddress;
            }
            */
            
            fullAddr = data.roadAddress; // 지번/도로명 => 도로명 주소

            // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
            // if(data.userSelectedType === 'R'){
                //법정동명이 있을 경우 추가한다.
                if(data.bname !== ''){
                    extraAddr += data.bname;
                }
                // 건물명이 있을 경우 추가한다.
                if(data.buildingName !== ''){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
            // }
            
           // 주소 정보 전체 필드 및 내용 확인 : javateacher
           /*
            var output = '';
            for (var key in data) {
            	output += key + ":" +  data[key]+"\n";
            }
            
            console.log(output); 
			*/
			
            // 3단계 : 해당 필드들에 정보 입력
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('zipNum').value = data.zonecode; //5자리 새우편번호 사용
            document.getElementById('address1').value = fullAddr;

            // 커서를 상세주소 필드로 이동한다.
            document.getElementById('address2').focus();
        }
    }).open();
}

function go_next() {
	
	console.log("go_next");
	
	// 약관 동의(radio)
	if (document.formm.okon1[0].checked == true) {
		  
		console.log("약관 동의 여부 : " + document.formm.okon1[0].checked);
		
		location.href = "join_form?contractOK="+document.formm.okon1[0].checked;
	
	} else if (document.formm.okon1[1].checked == true) {
		alert('약관에 동의해야 가입할 수 있습니다.');
	}
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//회원가입 폼 점검(join form validation)

var id_check_flag = false;		// 아이디 점검 플래그 변수
var pwd_check_flag1 = false;	// 비밀번호 점검 플래그1 변수
var pwd_check_flag2 = false;	// 비밀번호 점검 플래그2 변수
var name_check_flag = false;	// 이름 점검 플래그 변수
var email_check_flag = false;	// 전화번호 점검 플래그 변수
var phone_check_flag = false;	// 전화번호 점검 플래그 변수


window.onload = function() {
	
	var join_id = document.querySelector("form#join input#id");
	var join_pwdCheck1 = document.querySelector("form#join input#pwd");
	var join_pwdCheck2 = document.querySelector("form#join input#pwdCheck");
	var join_name = document.querySelector("form#join input#name");
	var join_email = document.querySelector("form#join input#email");
	var join_phone = document.querySelector("form#join input#phone");
	
	
	//아이디 점검
	join_id.onblur = function() {
		
		console.log("아이디 값 : " + join_id.value);
		
		var id = document.getElementById("id");
		
		//유효성 점검
		console.log("아이디 유효성 점검 : " + new RegExp(/^[a-zA-Z]{1}\w{7,19}$/));
		let regex_id = new RegExp(/^[a-zA-Z]{1}\w{7,19}$/);
		
		if(regex_id.test(id.value) == true) {
			console.log("아이디 점검 통과")
			
			document.getElementById("id_err_msg").innerHTML = "";

			id_check_flag = true;
			document.getElementById("id_check_flag").innerHTML = "true";
			
			//중복 점검
			$.ajax( {
				url : "/project/id_check",
				type : "get",
				data : { id : $("form#join input#id").val() },
				dataType : "text",
				success : function(data) {
					
					console.log("data : " + data);
					
					if (data.trim() == '있는 아이디') {
						
						alert("이미 존재하는 ID입니다.")
						
						id_check_flag = false;
						
						$("#id_check_flag").html("false");	//플래그 모니터링에 반영
						
						// 필드 초기화 + 재입력
						console.log("재입력");
						id_check_flag = false;
						join_id.value = "";
						//join_id.focus();
						
					} else {
						
						alert("사용할 수 있는 ID입니다.")
						
						id_check_flag = true;
						
						$("#id_check_flag").html("true");	//플래그 모니터링에 반영
					}//else
				},//function(data)
				error : function(xhr, status, error) {
					console.error("xhr : " + xhr);
					console.error("status : " + status);
					console.error("error : " + error);
				}//error
			} )//$.ajax
			
		} else if (id.value == "") {
			
			document.getElementById("id_err_msg").innerHTML = "";	
			
		} else {
			
			console.log("아이디 점검 오류")
			
			document.getElementById("id_err_msg").innerHTML = id.title;
			
			name_check_flag = false;
			document.getElementById("id_check_flag").innerHTML = "false";
			
			// 필드 초기화 + 재입력
			console.log("재입력");
			id_check_flag = false;
			join_id.value = "";
			//join_id.focus();
		}
		
	}
	
	//비밀번호 유효성 점검
	join_pwdCheck1.onblur = function() {
		
		console.log("비밀번호 유효성 점검")
		
		var pwd = document.getElementById("pwd");
		
		console.log("pwd : " + pwd.value);
		console.log("pwdCheck : " + pwdCheck.value);
		
		var regex_pwd = new RegExp(/^(?=.*[a-zA-Z])((?=.*\d)(?=.*\W)).{8,20}$/);
		
		if(regex_pwd.test(pwd.value) == false) {
			
			console.log("비밀번호 무효");
			
			document.getElementById("pwd_err_msg1").innerHTML = pwd.title;	//에러 메세징
			
			pwd_check_flag1 = false;
			document.getElementById("pwd_check_flag1").innerHTML = "false";	//플래그 모니터링에 반영
			
			// 필드 초기화 + 재입력
			console.log("재입력");
			pwd_check_flag1 = false;
			pwd.value = "";
			//pwd.focus();
			
		} else if (pwd.value == "") {
			
			document.getElementById("pwd_err_msg1").innerHTML = "";	
			
		} else {
			
			console.log("비밀번호 유효")
			
			document.getElementById("pwd_err_msg1").innerHTML = "";	//에러 메세징 초기화
			
			pwd_check_flag1 = true;
			document.getElementById("pwd_check_flag1").innerHTML = "true";	//플래그 모니터링에 반영
		}
	}
	
	//비밀번호 일치 점검
	join_pwdCheck2.onblur = function() {
		
		console.log("비밀번호 일치 점검")
		
		var pwd = document.getElementById("pwd");
		var pwdCheck = document.getElementById("pwdCheck");
		
		console.log("pwd : " + pwd.value);
		console.log("pwdCheck : " + pwdCheck.value);
		

		if (pwd.value == pwdCheck.value) {
			
			console.log("비밀번호 일치")
			
			document.getElementById("pwd_err_msg2").innerHTML = "";	//에러 메세징 초기화
			
			pwd_check_flag2 = true;
			document.getElementById("pwd_check_flag2").innerHTML = "ture";	//플래그 모니터링에 반영
			
		} else if (pwdCheck.value == "") {
			
			document.getElementById("pwd_err_msg2").innerHTML = "";
			
		} else {
			
			console.log("비밀번호 불일치")
			
			document.getElementById("pwd_err_msg2").innerHTML = "비밀번호가 다릅니다.";		//에러 메세징
			
			pwd_check_flag2 = false;
			document.getElementById("pwd_check_flag2").innerHTML = "false";	//플래그 모니터링에 반영
			
			// 필드 초기화 + 재입력
			console.log("재입력");
			pwd_check_flag2 = false;
			pwd.value = "";
			pwdCheck.value = "";
			//pwd.focus();
		}
	}
	
	//이름 유효성 점검
	join_name.onblur = function() {
		
		console.log("이름 값 : " + join_name.value);
		
		var name = document.getElementById("name");
		
		var regex_name = new RegExp(/^[가-힣]{2,13}$/);
		
		if(regex_name.test(name.value) == true) {
			
			console.log("이름 점검 통과")
			
			document.getElementById("name_err_msg").innerHTML = "";

			name_check_flag = true;
			document.getElementById("name_check_flag").innerHTML = "true";
		
		} else if (name.value == "") {
			
			document.getElementById("name_err_msg").innerHTML = "";		
			
		} else {
			
			console.log("이름 점검 오류")
			
			document.getElementById("name_err_msg").innerHTML = name.title;
			
			name_check_flag = false;
			document.getElementById("name_check_flag").innerHTML = "false";
			
			// 필드 초기화 + 재입력
			console.log("재입력");
			join_name.value = "";
			//join_name.focus();
		}
		
	}
	
	//전화번호 점검	
	join_email.onblur = function() {
		
		console.log("전화번호 값 : " + join_email.value);
		
		var email = document.getElementById("email");
		
		const regex_email = new RegExp(/^[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/);
		//bar or let 써도 되지만 const는 상수화가 됨
		
		//유효성 점검
		if(regex_email.test(email.value) == true) {
			
			console.log("전화번호 점검 통과")
			
			document.getElementById("email_err_msg").innerHTML = "";

			email_check_flag = true;
			document.getElementById("email_check_flag").innerHTML = "true";
			
			//중복 점검
			$.ajax( {
				url : "/project/email_check",
				type : "get",
				data : { email : $("form#join input#email").val() },
				dataType : "text",
				success : function(data) {
					
					console.log("data : " + data);
					
					if (data.trim() == '있는 전화번호') {
						
						alert("이미 존재하는 이-메일입니다.")
						
						email_check_flag = false;
						
						$("#email_check_flag").html("false");	//플래그 모니터링에 반영
						
						// 필드 초기화 + 재입력
						console.log("재입력");
						join_email.value = "";
						//join_email.focus();
						
					} else {
						
						alert("사용할 수 있는 이-메일입니다.")
						
						email_check_flag = true;
						
						$("#email_check_flag").html("true");	//플래그 모니터링에 반영
					}
				},
				error : function(xhr, status, error) {
					console.error("xhr : " + xhr);
					console.error("status : " + status);
					console.error("error : " + error);
				}
			} )
		
		} else if (email.value == "") {
			
			document.getElementById("email_err_msg").innerHTML = "";		
			
		} else {
			
			console.log("전화번호 점검 오류")
			
			document.getElementById("email_err_msg").innerHTML = email.title;
			
			email_check_flag = false;
			document.getElementById("email_check_flag").innerHTML = "false";
			
			// 필드 초기화 + 재입력
			console.log("재입력");
			join_email.value = "";
			//join_email.focus();
		}
	}
	
	join_phone.onblur = function() {
		
		console.log("전화번호 값 : " + join_phone.value);
		
		var phone = document.getElementById("phone");
		
		const regex_phone = new RegExp(/^01\d{1}-\d{3,4}-\d{4}$/);
		//bar or let 써도 되지만 const는 상수화가 됨
		
		//유효성 점검
		if(regex_phone.test(phone.value) == true) {
			
			console.log("전화번호 점검 통과")
			
			document.getElementById("phone_err_msg").innerHTML = "";

			phone_check_flag = true;
			document.getElementById("phone_check_flag").innerHTML = "true";
			
			//중복 점검
			$.ajax( {
				url : "/project/phone_check",
				type : "get",
				data : { phone : $("form#join input#phone").val() },
				dataType : "text",
				success : function(data) {
					
					console.log("data : " + data);
					
					if (data.trim() == '있는 전화번호') {
						
						alert("이미 존재하는 전화번호입니다.")
						
						phone_check_flag = false;
						
						$("#phone_check_flag").html("false");	//플래그 모니터링에 반영
						
						// 필드 초기화 + 재입력
						console.log("재입력");
						join_phone.value = "";
						//join_phone.focus();
						
					} else {
						
						alert("사용할 수 있는 전화번호입니다.")
						
						phone_check_flag = true;
						
						$("#phone_check_flag").html("true");	//플래그 모니터링에 반영
					}
				},
				error : function(xhr, status, error) {
					console.error("xhr : " + xhr);
					console.error("status : " + status);
					console.error("error : " + error);
				}
			} )
			
		} else if (phone.value == "") {
			
			document.getElementById("phone_err_msg").innerHTML = "";		
		
		} else {
			
			console.log("전화번호 점검 오류")
			
			document.getElementById("phone_err_msg").innerHTML = phone.title;
			
			phone_check_flag = false;
			document.getElementById("phone_check_flag").innerHTML = "false";
			
			// 필드 초기화 + 재입력
			console.log("재입력");
			join_phone.value = "";
			//join_phone.focus();
		}
	}
	
	///// 전체 플래그 상태 //////
		
	console.log("아이디 필드 점검 플래그 : " + id_check_flag);
	console.log("패쓰워드 필드 유효성 점검 플래그 : " + pwd_check_flag1);
	console.log("패쓰워드 필드 일치 점검 플래그 : " + pwd_check_flag2);
	console.log("이름 필드 점검 플래그 : " + name_check_flag);
	console.log("메일 필드 점검 플래그 : " + email_check_flag);
	console.log("전화번호 필드 점검 플래그 : " + phone_check_flag);
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	//회원 정보 전송(가입)
	join_submit.onlcik = function() {
		
		console.log("회원 정보 전송(가입)");
		
		//필수 항목 점검
		if (id_check_flag == true &&		// 아이디 점검 플래그 변수
			pwd_check_flag1 == true &&	// 비밀번호 점검 플래그1 변수
			pwd_check_flag2 == true &&	// 비밀번호 점검 플래그2 변수
			name_check_flag == true &&	// 이름 점검 플래그 변수
			email_check_flag == true &&	// 전화번호 점검 플래그 변수
			phone_check_flag == true) {	// 전화번호 점검 플래그 변수
		
			console.log("폼 점검 완료");
			
			
			
		} else
		
		
		
		//부가 항목 점검 : 주소 등
		
		console.log("폼 점검 미완료");

		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}//window.onload = function() 