window.onload = function() {
	
	//select 박스 초기 설정(상품 사용 여부)
	var useynSelectBox;
	var pseq;
	
	var useynSelectBoxes = document.querySelectorAll("select[id^=useyn]");
	
	console.log("한 페이지의 요소 개수 : " + useynSelectBoxes.length);
	
	var useynBoxesLen = useynSelectBoxes.length; 
	
	var selectedIdx;
	
	for(var i = 0; i < useynSelectBoxes.length; i++) {
		
		useynSelectBox = useynSelectBoxes[i];
		pseq = useynSelectBox.id.substring('useyn_'.length);
		
		console.log("select ID : " + useynSelectBox.id);
		console.log("pseq : " + pseq);
		
		//초기값 설정
		var pseqVal = document.getElementById("hidden_useyn_" + pseq).value;
		
		/* 여기서부터 안 되는 중 */
		console.log("pseq init val : " + pseqVal)
		
		for(var j = 0; j < useynSelectBox.options.length; j++) {
			
			if(useynSelectBox.options[j].valuee == pseqVal) {
				
				selectedIdx = j;
			}
		}
		
		useynSelectBox.selectedIdx == selectedIdx;
	}
	
	//select 박스 초기 설정(베스트 상품)
	var bestynSelectBoxId;
	var bestynSelectBox;
	var pseq;
	
	var bestynSelectBoxes = document.querySelectorAll("select[id^=bestyn]");
	
	console.log("한 페이지의 요소 개수 : " + bestynSelectBoxes.length);
	
	var bestynBoxesLen = bestynSelectBoxes.length; 
	
	var selectedIdx;
	
	for(var i = 0; i < bestynSelectBoxes.length; i++) {
		
		bestynSelectBox = bestynSelectBoxes[i];
		pseq = bestynSelectBox.id.substring('bestyn_'.length);
		
		console.log("select ID : " + bestynSelectBox.id);
		console.log("pseq" + pseq);
		
		//초기값 설정
		var pseqVal = document.getElementById("hidden_bestyn_" + pseq).value;
		
		console.log("pseq init val : " + pseqVal)
		
		for(var j = 0; j < bestynSelectBox.options.length; j++) {
			
			if(bestynSelectBox.options[j].valuee == pseqVal) {
				
				selectedIdx = j;
			}
		}
		
		bestynSelectBox.selectedIdx == selectedIdx;
	}

	//관리자가 상품 사용 여부 변경 시 변경 사항 반영 : ajax
	var roleSelectBoxes = document.querySelectorAll("select[id^=role]");

	for (var useynSelectBox of useynSelectBoxes) {

		useynSelectBox.onchange = function(e) {
			
			var tempId = e.currentTarget.id;
			
			// 상품 아이디
			var pseq =  tempId.substring("useyn_".length);
			
			//상품 사용 여부
			var useyn = document.getElementById(tempId).value;
			
			console.log("상품 아이디 : " + pseq);
			console.log("선택된 값 : " + useyn)
			
			// AJAX : 상품 사용 여부 => update => success
			$.ajax({
				url : "../product/update_useYN",
				type : "get",
				data : { 
					pseq : pseq,
					useyn : useyn
				},
				dataType: "text",
				success : function(data) {
					
					console.log("data : " + data);
					
					/*
					if (data.trim() == '회원 롤 정보가 정상적으로 수정되었음') {
						
						location.reload(); // 페이지 갱신
					} 
					*/
					
					alert(data.trim()); //  메시징
				},
				error : function(xhr, status, error) {
					
					console.error("xhr : " + xhr);
					console.error("status : " + status);
					console.error("error : " + error);
				}
			}); // ajax
		} //
	} // for

	//관리자가 베스트 상품 변경 시 변경 사항 반영 : ajax
	var roleSelectBoxes = document.querySelectorAll("select[id^=bestyn]");

	for (var bestynSelectBox of bestynSelectBoxes) {

		bestynSelectBox.onchange = function(e) {
			
			console.log("이벤트 발생");
			console.log("roleSelectBoxes onchange event : " + e.currentTarget.id);
			
			var tempId = e.currentTarget.id;
			
			// 상품 아이디
			var pseq =  tempId.substring("bestyn_".length);
			
			// 상품 사용 여부
			var bestyn = document.getElementById(tempId).value;
			
			console.log("상품 아이디 : " + pseq);
			console.log("선택된 값 : " + bestyn)
			
			// AJAX : 상품 사용 여부 => update => success
			$.ajax({
				url : "../product/update_bestYN",
				type : "get",
				data : { 
					pseq : pseq,
					bestyn : bestyn
				},
				dataType: "text",
				success : function(data) {
					
					console.log("data : " + data);
					alert(data.trim()); //  메시징
				},
				error : function(xhr, status, error) {
					
					console.error("xhr : " + xhr);
					console.error("status : " + status);
					console.error("error : " + error);
				}
			}); // ajax
		} //
	} // for
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	//상품 검색 폼 점검
	var search_product_btn = document.getElementById("search_product_btn");
	var search_field = document.getElementById("search_field");
	var search_word = document.getElementById("search_word");
	var search_product_frm = document.getElementById("search_product_frm");
	
	search_product_btn.onclick = function(e) {
		
		//상품 전체 검색
		//모든 카테고리 + 검색어 미입력
		if(search_field.value == 'all' && search_word.value.trim() == '') {					//됨
			
			alert("검색어를 입력하시오.")
			search_word.focus();
		
		//모든 카테고리 + 검색어 입력
		} else if((search_field.value == 'all') && (search_word.value.trim() != '')) {		//됨
			
			search_product_frm.submit();		
			
		//카태고리 선택 + 검색어 미입력 
		} else if((search_field.value != 'all') && (search_word.value.trim() == '')) {		//안됨 
			
			alert("상품 분류 내 모든 상품입니다.")
			search_product_frm.submit();
			
		//카테고리 선택 + 검색어 입력	
		} else if((search_field.value != 'all') && (search_word.value.trim() != '')) {		//안 됨
			
			search_product_frm.submit();
		}
	}
}
