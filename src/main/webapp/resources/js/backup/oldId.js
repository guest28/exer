//아이디 점검
	join_id.onblur = function() {
		
		console.log("아이디 값 : " + join_id.value);
		
		//유효성 점검
		console.log("아이디 유효성 점검 : " + new RegExp(/^[a-zA-Z]{1}\w{7,19}$/).test(join_id.value));
		
		let join_id_valid = new RegExp(/^[a-zA-Z]{1}\w{7,19}$/).test(join_id.value);
		
		// ajax 전송 여부
		if(join_id_valid == true) {	
			
			console.log("ajax 전송")
			
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
						
						join_id.value = "";
						//join_id.focus();
						
					} else {
						
						alert("사용할 수 있는 ID입니다.")
						
						id_check_flag = true;
						
						$("#id_check_flag").html("true");	//플래그 모니터링에 반영
					}
				},
				error : function(xhr, status, error) {
					console.error("xhr : " + xhr);
					console.error("status : " + status);
					console.error("error : " + error);
				}
			} )
		} else {
		
		// 필드 초기화 + 재입력
		console.log("재입력");
		
		join_id.value = "";
		//join_id.focus();
		}
	}
	