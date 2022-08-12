<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>
<%@ include file="../sub_menu_member.jsp" %>

<!-- css -->
<link href="<c:url value="/css/member_info.css" />" rel="stylesheet">

<script>
function move_inactive_member() {
	
	var read = document.getElementById("inactive_read");
	var write = document.getElementById("inactive_write");
	
	if(read.value == write.value) {
		
		if(confirm("정말 회원 탈퇴하시겠습니까?")) {
			
			console.log("탈퇴");
			location.href="${contextPath}/member/member_inactive_proc";
			
		} else {
			
			console.log("탈퇴 취소");
			alert("탈퇴 처리를 취소하였습니다.");
		}
	} else {
		
		console.log("탈퇴 취소");
		alert("탈퇴 처리를 취소.");
	}
		
}
</script>


<article id="view_box"  class="container">

	<h2>회원 탈퇴</h2>
	
	<form>
		
		<fieldset>
		
	 		<!-- 확인 박스 -->
			<div id="login_box" class="my-5">
				<div class="row">
					<div class="col-2 pt-2 my-1">
					</div>
					<div class="col-8 my-1">
						아래의 글자와 똑같이 입력하시오.
					</div>	
					<div class="col-2 pt-2 my-1">
					</div>		
				</div>
				<div class="row">
					<div class="col-2 pt-2 my-1">
					</div>
					<div class="col-8 my-1">
						<input id="inactive_read" type="text" class="form-control" value="심사숙고" readonly>
					</div>	
					<div class="col-2 pt-2 my-1">
					</div>		
				</div>
				<div class="row">
					<div class="col-2 pt-2 my-1">
					</div>
					<div class="col-8 my-1">
						<input id="inactive_write" type="text" class="form-control">
					</div>
					<div class="col-2 pt-2 my-1">
					</div>
				</div>
			</div>
			
			<!-- 탈퇴 버튼 -->  
			<div class="clear"></div>
			<div id="login_buttons" class="row my-3">
				<div class="col-4"></div>
				<div class="col-3 ps-4">
					<input type="button" class="btn btn-secondary" value="회원 탈퇴"
						   onclick="move_inactive_member()">
				</div>	
	        	<div class="col-3">
		        	<input type="button" class="btn btn-outline-secondary"  value="취소"
		        		   onclick="location='${contextPath}/member/member_info'">
		        </div>         
	        	<div class="col-2">
	            </div>          
	        </div>
        </fieldset>
	</form>  
</article>


<!-- footer -->
<%@ include file="../footer.jsp" %>