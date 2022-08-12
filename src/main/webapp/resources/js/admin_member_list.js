//검색(페이징)
window.onload = function() {
	
	var limit = "${members.size()}"		//페이지 limit
	var selectBoxId;
	var selectBox;
	var role;
	
	//1. 모든 회원에게서 option 숫자 설정하기 => 몇 번 째 게시물에 있는 회원이냐?
	for(var i = 0; i < limit; i++) {
		
		selectBoxId = "role" + i;	//jsp에서의 role${st.index}
		selectBox = document.getElementById(selectBoxId);	//선택된 게시물(회원)
		
		//role = "${members[0].role}";	 // 언어 간 충돌이 생김
		role = getRoleFromMembers(i); 	// java List를  javascript Array로 치환
		
		console.log("개별 회원 role : " + role);
		
		//2. option 값 설정하기 => 여기선  0 = user, 1 = admin, 3 = guest
		for(var j=0; j < selectBox.options.length; j++) {
			
			if(selectBox.options[j].value == role) {
				selectedIdx = j;					
			}
		}
		
		console.log(j + "의 selectedIdx : " + selectedIdx);
		
		selectBox.selectedIndex = selectedIdx;		//role(selectedIdx) => 번호가 부여된 게시물에 반영
	}
	
	// 관리자가 변경시 변경 사항 반영 : ajax
	var roleSelectBoxes = document.querySelectorAll("select[id^=role]");	//getElementById를 쓰지 않는 이유 : id에 숫자를 먹이기 때문에 id가 난해하다...
	
	for(var roleSelectBox of roleSelectBoxes) {
	
		roleSelectBox.onchange = function(e) {
			
			console.log("이벤트 발생");
			console.log("roleSelectBoxes onchange event : " + e.currentTarget.id);
			
			var tempId = e.currentTarget.id;
			
			console.log("target title : " + document.getElementById(tempId).title);
			
			console.log("선택된 값 : " + document.getElementById(tempId).value);
			
			// 회원 아이디
			var memberId =  document.getElementById(tempId).title;
			// 회원 롤(role)
			var memberRole = document.getElementById(tempId).value;
			

			//ajax : 회원 id, 회원 role => update => success => 전체 현황 reload
			$.ajax({
				url : "../../member/update_role",
				type : "get",
				data : { 
					id : memberId,
					role : memberRole
				},
				
				dataType: "text",
				success : function(data) {
					
					console.log("data : " + data);
					
					alert(data.trim());
				},
				
				error : function(xhr, status, error) {
					
					console.error("xhr : " + xhr);
					console.error("status : " + status);
					console.error("error : " + error);
				}
				
			});

		}
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//	관리자 모드 : 개별 회원 정보 수정(이메일)
	
	var emails = document.querySelectorAll("input[type='text'][id^=email]");
	
	//기존 이메일 저장 변수(임시 배열)
	var default_emails = getDefaultMemberFldsFromMembers("이메일");
	
	console.log("emails update");
	
	//배열 확인
	default_emails.forEach(function (item, index, array) {
		
		console.log(item, index)
	});
	
	console.log("default emails[0] : " + default_emails[0]);
	
	for(var emailUpdate of emails) {
		
		console.log("email update");
		
		emailUpdate.onclick = function(e) {
			
			console.log("이메일 클릭");
			
			var id = e.currentTarget.id;
			console.log("focus id : " + id);
			
			document.getElementById(id).style.background = "#FFFCCC"; //입력 활성화 상태
			
			//기존 이메일 저장
			var count_email_id = "count_"+id.substring("email_".length);
			var count_email = document.getElementById(count_email_id).value;
			var default_email = default_emails[count_email];
			
			console.log("기존 이메일 : " + default_email);
			
			//emailUpdate.readOnly = false;		// 편집 가능 상태
		}
		
	}
	
	//var email_update_btns = document.querySelectorAll("button[id^=email_update_btn_]");
	var email_update_btns = document.querySelectorAll("[id^=email_update_btn_]");
	
	for(var email_update_btn of email_update_btns) {

		console.log("버튼들");
		
		//버튼 클릭 시
		email_update_btn.onclick = function(e) {
			
			console.log("클릭");
			
			var btnId = e.currentTarget.id;
			console.log("btn id : " + btnId);
			
			if(confirm('이-메일 주소를 변경하시겠습니까?')) {
				
				var userId = btnId.substring("email_update_btn_".length);
				var emailId = "email_"+userId;
				
				console.log("사용자 아이디 : " + userId);
				console.log("이메일 아이디 : " + emailId);
				
				var emailUpdate = document.getElementById(emailId);
				
				console.log("이메일 값(test) : " + emailUpdate.value);
				
				//폼점검
				const email_valid = new RegExp(/^[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/).test(emailUpdate.value);
				
				if(email_valid == true) {
					
					console.log("폼점검 성공");
					
					//이메일 중복 점검
					$.ajax({
						url : "../../member/email_check_update",
						type : "get",
						data : { 
							id : userId,
							email : emailUpdate.value 
						},
						dataType: "text",
						success : function(data) {
							
							console.log("data : " + data);
							
							alert(data);
							
							if(data.trim() == '사용 가능한 이메일') {
								
								console.log("update 전송");
								
								//개별 회원 이메일 수정
								//ajax(이메일 수정)
								$.ajax({
									url : "../../member/email_update",
									type : "get",
									data : { 
										id : userId,
										email : emailUpdate.value 
									},
									dataType: "text",
									success : function(data) {
										
										console.log("data : " + data);
										
										alert(data);	//이메일 수정 성공/실패 메시징
									},
									error : function(xhr, status, error) {
										 
										console.error("xhr : " + xhr);
										console.error("status : " + status);
										console.error("error : " + error);
									}
								});
							} else {	//중복일 경우
								
								console.log("중복");
								
								//실패시 : "중복 이메일 존재"
								if (data.trim() == '중복 이메일 존재') {
									
									//원래 화면으로 복구 : 화면 리로드(리프레쉬)
									//location.reload();
									
									//원래 이메일로 복구(초기화)
									emailUpdate.value = default_email;
									console.log("기존 이메일 : " + default_email);
								}
							}
						},
						error : function(xhr, status, error) {
							
							console.error("xhr : " + xhr);
							console.error("status : " + status);
							console.error("error : " + error);
						}
						
					}); // ajax (이메일 중복 점검)
					
				} else {
					console.log("폼점검 실패");
					
					alert("이메일 폼점검 실패");
					
					//원래 이메일로 복구(초기화)
					emailUpdate.value = default_email;
					console.log("기존 이메일 : " + default_email);
				}
				
			} else {
				
				console.log("전송 취소");
				return false;
			}
		}
	}

//	관리자 모드 : 개별 회원 정보 수정(연락처)		
	
	var phones = document.querySelectorAll("input[type='text'][id^=phone]");
	
	//기존 연락처 저장 변수(임시 배열)
	var default_phones = getDefaultMemberFldsFromMembers("연락처");
	
	console.log("phones update ------------------------");
	
	//배열 확인
	default_phones.forEach(function (item, index, array) {
		console.log(item, index)
	});
	
	console.log("default phones[0] : " + default_phones[0]);
	
	for(var phoneUpdate of phones) {
		
		console.log("phone update");
		
		phoneUpdate.onclick = function(e) {
			
			console.log("연락처 클릭 : " + count_phone);
			
			console.log("default phones[0]-2 : " + default_phones[0]);
			
			var id = e.currentTarget.id;
			console.log("focus id : " + id);
			
			document.getElementById(id).style.background = "#FFFCCC"; // 입력 활성화 상태
			
			//기존 연락처 저장
			var count_phone_id = "count_"+id.substring("phone_".length);
			var count_phone = document.getElementById(count_phone_id).value;
			
			console.log("count_phone : " + count_phone);
			
			var default_phone = default_phones[count_phone];
			console.log("기존 연락처 : " + default_phone);
			
			//phoneUpdate.readOnly = false; // 편집 가능 상태
		}
	}
	
	var phone_update_btns = document.querySelectorAll("[id^=phone_update_btn_]");
	
	for(var phone_update_btn of phone_update_btns) {

		console.log("버튼들");
		
		//버튼 클릭 시
		phone_update_btn.onclick = function(e) {
			
			console.log("클릭");
			
			var btnId = e.currentTarget.id;
			console.log("btn id : " + btnId);
			
			if(confirm('연락처를 변경하시겠습니까?')) {
				
				var userId = btnId.substring("phone_update_btn_".length);
				var phoneId = "phone_"+userId;
				
				console.log("사용자 아이디 : " + userId);
				console.log("연락처 아이디 : " + phoneId);
				
				var phoneUpdate = document.getElementById(phoneId);
				
				console.log("연락처 값(test) : " + phoneUpdate.value);
				
				//폼 점검
				const phone_valid = new RegExp(/^01\d{1}-\d{3,4}-\d{4}$/).test(phoneUpdate.value);
				
				if(phone_valid == true) {
					
					console.log("폼점검 성공");
					
					//연락처 중복 점검
					$.ajax({
						url : "../../member/phone_check_update",
						type : "get",
						data : { 
							id : userId,
							phone : phoneUpdate.value 
						},
						dataType: "text",
						success : function(data) {
							
							console.log("data : " + data);
							
							alert(data);
							
							if (data.trim() == '사용 가능한 연락처') {
								
								console.log("update 전송");
								
								// 개별 회원 연락처 수정
								// ajax (연락처 수정)
								$.ajax({
									url : "../../member/phone_update",
									type : "get",
									data : { 
										id : userId,
										phone : phoneUpdate.value 
									},
									dataType: "text",
									success : function(data) {
										
										console.log("data : " + data);
										
										alert(data);
										
									},
									error : function(xhr, status, error) {
										 
										console.error("xhr : " + xhr);
										console.error("status : " + status);
										console.error("error : " + error);
									}
									
								});  
								
							} else {
								
								console.log("중복");

								if (data.trim() == '중복 연락처 존재') {
									
									//location.reload();
									
									//원래 연락처로 복구(초기화)
									phoneUpdate.value = default_phone;
									console.log("기존 연락처 : " + default_phone);
								}
							} //
							
						},
						error : function(xhr, status, error) {
							
							console.error("xhr : " + xhr);
							console.error("status : " + status);
							console.error("error : " + error);
						}
						
					});
					
				} else {
					console.log("폼점검 실패");
					
					alert("연락처 폼점검 실패");
					
					//원래 연락처로 복구(초기화)
					phoneUpdate.value = default_phone;
					console.log("기존 연락처 : " + default_phone);
				}
				
			}else {
				
				console.log("전송 취소");
				return false;
			}
		}
	}
	
//	관리자 모드 : 개별 회원 정보 수정(우편번호, 기본 주소, 상세주소)
	
	var zipNums = document.querySelectorAll("div[id^=zipNum]");
	var address1s = document.querySelectorAll("div[id^=address1]");
	
	//기존 우편번호, 기본 주소, 상세 주소 저장 변수(임시 배열)
	var default_zipNums = getDefaultMemberFldsFromMembers("우편번호");
	var default_address1s = getDefaultMemberFldsFromMembers("기본주소");
	var default_address2s = getDefaultMemberFldsFromMembers("상세주소");
	
	console.log("zipNum/address1 update ------------------------");
	
	//배열 확인
	default_zipNums.forEach(function (item, index, array) {
		console.log(item, index)
	});
	
	console.log("default zipNums[0] : " + default_zipNums[0]);
	
	//우편번호 필드
	for(var zipNumUpdate of zipNums) {
		
		console.log("zipNum update");
		
		zipNumUpdate.onclick = function(e) {
			
			var id = e.currentTarget.id;
			console.log("focus id : " + id);
			
			console.log("우편번호 클릭 : ");
			
			console.log("default zipNums[0]-2 : " + default_zipNums[0]);
			
			//기존 우편번호 저장
			var count_zipNum_id = "count_"+id.substring("zipNum_".length);
			var count_zipNum = document.getElementById(count_zipNum_id).value;
			var userId = id.substring("zipNum_".length);
			
			console.log("count_zipNum : " + count_zipNum);
			
			var default_zipNum = default_zipNums[count_zipNum];
			console.log("기존 우편번호 : " + default_zipNum);
			
			//주소 검색 팝업 출력
			getPostcodeAddress(userId);
		};
	}	
	
	//기본주소 필드
	for(var address1Update of address1s) {
		
		console.log("address1 update");
		
		address1Update.onclick = function(e) {
			
			var id = e.currentTarget.id;
			console.log("focus id : " + id);
			
			console.log("기본주소 클릭 : ");
			
			console.log("default address1s[0]-2 : " + default_address1s[0]);
			
			//기존 기본주소 저장
			var count_address1_id = "count_"+id.substring("address1_".length);
			var count_address1 = document.getElementById(count_address1_id).value;
			var userId = id.substring("address1_".length);
			
			console.log("count_address1 : " + count_address1);
			
			var default_address1 = default_zipNums[count_address1];
			console.log("기존 기본주소 : " + default_address1);
			
			//주소 검색 팝업 출력
			getPostcodeAddress(userId);
		};
	}	
	
	//우편번호, 기본주소 이벤트 처리
	var zip_address1_update_btns = document.querySelectorAll("[id^=zip_address1_update_btn_]");
	
	for(var zip_address1_update_btn of zip_address1_update_btns) {

		console.log("버튼들");
		
		//버튼 클릭 시
		zip_address1_update_btn.onclick = function(e) {
			
			console.log("클릭");
			
			var btnId = e.currentTarget.id;
			console.log("btn id : " + btnId);
			
			if (confirm('주소를 변경하시겠습니까?')) {
				
				var userId = btnId.substring("zip_address1_update_btn_".length);
				var zipNumId = "zipNum_"+userId;
				var address1Id = "address1_"+userId;
				var address2Id = "address2_"+userId;
				
				console.log("사용자 아이디 : " + userId);
				console.log("우편번호 아이디 : " + zipNumId);
				console.log("기본주소 아이디 : " + address1Id);
				console.log("상세주소 아이디 : " + address2Id);
				
				var zipNumUpdate = document.getElementById(zipNumId);
				var address1Update = document.getElementById(address1Id);
				var address2Update = document.getElementById(address2Id);
				
				console.log("우편번호 값(test) : " + zipNumUpdate.innerText);
				console.log("기본주소 값(test) : " + address1Update.innerText);
				console.log("상세주소 값(test) : " + address2Update.innerText);
				
				//전송 전 상세 주소 입력 여부 점검
				//1. 상세 주소가 입력되었을 때
				var address2Val = address2Update.innerText;
				
				if(address2Val.trim() != '') { 
					
					console.log("폼점검(유효주소) 성공 : 상세주소 입력");
					
					$.ajax({
						url : "../../member/zip_address_update",
						type : "get",
						data : { 
							id : userId,
							zipNum : zipNumUpdate.innerText,
							address1 : address1Update.innerText,
							address2 : address2Val
						},
						dataType: "text",
						success : function(data) {
							
							console.log("data : " + data);
							
							alert(data);
							
						},
						error : function(xhr, status, error) {
							
							console.error("xhr : " + xhr);
							console.error("status : " + status);
							console.error("error : " + error);
						}
					});
				
				//2. 상세 주소가 입력되지 않았을 때	
				} else {
					
					console.log("폼점검(상세주소) 실패");
					
					alert("상세주소를 입력하십시오.");
					
					//기존 정보 복원
					var idx = document.getElementById("count_"+userId).value
					default_address2 = default_address2s[idx];
					
					address2Update.innerHTML = default_address2;
					console.log("기존 상세주소 : " + default_address2);
					
					address2Update.focus();
				}
				
			}else {
				
				console.log("전송 취소");
				
				var idx = document.getElementById("count_"+userId).value
				default_address2 = default_address2s[idx];
				
				address2Update.innerHTML = default_address2;
				console.log("기존 상세주소 : " + default_address2);
				
				return false;
			}
		}
	}
	
	//상세 주소 이벤트 처리
	var address2_update_btns = document.querySelectorAll("[id^=address2_update_btn_]");
	
	for(var address2_update_btn of address2_update_btns) {

		console.log("상세 주소 버튼들");
		
		//버튼 클릭 시
		address2_update_btn.onclick = function(e) {
			
			console.log("클릭");
			
			var btnId = e.currentTarget.id;
			console.log("btn id : " + btnId);
			
			if(confirm('주소를 변경하시겠습니까?')) {
				
				var userId = btnId.substring("address2_update_btn_".length);
				var zipNumId = "zipNum_"+userId;
				var address1Id = "address1_"+userId;
				var address2Id = "address2_"+userId;
				
				console.log("사용자 아이디 : " + userId);
				console.log("우편번호 아이디 : " + zipNumId);
				console.log("기본주소 아이디 : " + address1Id);
				console.log("상세주소 아이디 : " + address2Id);
				
				var zipNumUpdate = document.getElementById(zipNumId);
				var address1Update = document.getElementById(address1Id);
				var address2Update = document.getElementById(address2Id);
				
				console.log("우편번호 값(test) : " + zipNumUpdate.innerText);
				console.log("기본주소 값(test) : " + address1Update.innerText);
				console.log("상세주소 값(test) : " + address2Update.innerText);
				
				//전송 전 우편번호, 기본 주소, 상세 주소 입력 여부 점검
				//1. 상세주소가 입력되었을 때
				var address2Val = address2Update.innerText;
				
				if(zipNumUpdate.innerText.trim() == '' || 
					address1Update.innerText.trim() == '') 
				{
					//주소 검색 유도
					alert("우편번호와 기본 주소를 검색하십시오");
					zipNumUpdate.focus();
					
				} else if (zipNumUpdate.innerText.trim() != '' &&
						   address1Update.innerText.trim() != '' && 
						   address2Val.trim() != '') { 
					
					console.log("폼점검(유효주소) 성공 : 상세주소 입력");
					
					if (confirm('우편번호, 기본 주소, 상세 주소 모두를 수정하시겠습니까?')) {
					
						$.ajax({
							url : "../../member/zip_address_update",
							type : "get",
							data : { 
								id : userId,
								zipNum : zipNumUpdate.innerText,
								address1 : address1Update.innerText,
								address2 : address2Val
							},
							dataType: "text",
							success : function(data) {
								
								console.log("data : " + data);
								
								alert(data);

							},
							error : function(xhr, status, error) {
								
								console.error("xhr : " + xhr);
								console.error("status : " + status);
								console.error("error : " + error);
							}
							
						}); // 전송
					
					} else {
						
						alert("상세주소만 수정하겠습니다");
						
						$.ajax({
							url : "../../member/address2_update",
							type : "get",
							data : { 
								id : userId,
								address2 : address2Val
							},
							dataType: "text",
							success : function(data) {
								
								console.log("data : " + data);
								
								alert(data);
							},
							error : function(xhr, status, error) {
								
								console.error("xhr : " + xhr);
								console.error("status : " + status);
								console.error("error : " + error);
							}
							
						});
					}
					
				//2. 상세주소가 입력되지 않았을 때	
				} else {
					
					console.log("폼점검(상세주소) 실패");
					
					alert("상세주소를 입력하십시오.");
					
					//기존 정보 복원
					var idx = document.getElementById("count_"+userId).value
					default_address2 = default_address2s[idx];
					
					address2Update.innerHTML = default_address2;
					console.log("기존 상세주소 : " + default_address2);
					
					address2Update.focus();
				}
				

			} else {
				
				console.log("전송 취소");
				
				var idx = document.getElementById("count_"+userId).value
				default_address2 = default_address2s[idx];
				
				address2Update.innerHTML = default_address2;
				console.log("기존 상세주소 : " + default_address2);
				
				return false;
			}
		}
	}
	
//	관리자 모드 : 개별 회원 정보 수정(비활성화)
	var member_inactive_btns = document.querySelectorAll("[id^=member_inactive_btn_]");
	
	for (var member_inactive_btn of member_inactive_btns) {

		console.log("관리자 메뉴 개별 회원정보 비활성화(삭제) 버튼들");
		
		//버튼 클릭 시
		member_inactive_btn.onclick = function(e) {
			
			var btnId = e.currentTarget.id;
			var userId = btnId.substring("member_inactive_btn_".length);
			
			console.log("삭제할 ID : " + userId);
			
			if (confirm(userId + " 회원을 비활성화 하시겠습니까?")) {
				
				$.ajax({
					url : "../../member/member_inactive_admin",
					type : "get",
					data : { 
						id : userId
					},
					dataType: "text",
					success : function(data) {
						
						console.log("data : " + data);
						
						alert(data);
						
						//전체 화면 갱신 
						location.reload();
					},
					error : function(xhr, status, error) {
						
						console.error("xhr : " + xhr);
						console.error("status : " + status);
						console.error("error : " + error);
					}
				});
				
			} else {
				
				alert(userId + " 회원의 비활성화 취소.");
			}
		}
	}
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//뒤로 돌아가기 버튼
function page_href() {

	location.href = "link_page.html";

	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//모달 박스(modal) 제어
$(function() {
	
	$("[id^='ckbox_']").click(function(e){
	
		console.log("체크박스 체크");
		
		var member_id = e.currentTarget.id.substring('ckbox_'.length);
		console.log("member_id : " + member_id);
		
		//다른 체크 박스 비활성화
		var chboxes = document.querySelectorAll("input[id^=ckbox_]");
		for (var chbox of chboxes) {
			chbox.checked = false;
		}
		
		//자신의 체크박스는 활성화
		document.querySelector("input[id=ckbox_"+member_id+"]").checked = true;
					
		$("#member_modal").css({"left":"500px"});	//기본 값 및 닫기 시 : -500px

		//개별 회원 정보 가져오기
		$.ajax({
			url : "../../member/member_view",
			type : "get",
			data : { id : member_id },
			dataType: "text",
			success : function(data) {
				
				console.log("data : " + data);
				
				var json = JSON.parse(data);
				console.log("id : " + json.id);
				
				// HTML에 대입/출력
				$("#member_modal_header_title").html(json.id + "의 회원 정보");
				
				$("#member_modal #user_id").html(json.id);
				$("#member_modal #user_pwd").html(json.pwd);
				$("#member_modal #user_name").html(json.name);
				$("#member_modal #user_email").html(json.email);
				$("#member_modal #user_phone").html(json.phone);
				$("#member_modal #user_zipNum").html(json.zipNum);
				$("#member_modal #user_address1").html(json.address1);
				$("#member_modal #user_address2").html(json.address2);
				$("#member_modal #user_indate").html(json.indate);
								
			},
			error : function(xhr, status, error) {
				
				console.error("xhr : " + xhr);
				console.error("status : " + status);
				console.error("error : " + error);
			}
			
		});
		
	});
	
	//modal 팝업창 닫기
	$("#member_modal_close_btn").click(function() {
		
		$("#member_modal").css({"left":"-500px"});	//좌표를 이용해 안 보이도록 처리
		
		//체크 박스 비활성화
		console.log("member_id : " + $("#member_modal #user_id").html());
		//document.querySelector("input[id=ckbox_"+$("#member_modal #user_id").html()+"]").checked = false;
		
		var chboxes = document.querySelectorAll("input[id^=ckbox_]");
		for (var chbox of chboxes) {
			chbox.checked = false;
		}
		
	});
	
});

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//툴팁(tooltip : 가이드) 제어
$(function() {
	
	// 이메일/연락처/우편번호/기본주소 
	$("[data-toggle='tooltip']").tooltip();
});

