<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>
<%@ include file="../sub_menu_member.jsp" %>

<!-- css -->
<link href="<c:url value="/css/member_info.css" />" rel="stylesheet">

<article id="view_box"  class="container">

	<h2>본인 확인</h2>
	
	<form method="post" action="${contextPath}/member/check_proc">
		
		<fieldset>
		
	 		<!-- 로그인 박스 -->
			<div id="login_box" class="my-5">
				<div class="row">
					<div class="col-4 pt-2 my-1">
						<label class="form-label">ID</label>
					</div>
					<div class="col-8 my-1">
						<input name="id" type="text" class="form-control" value="${LOGIN_USER.id}" readonly>
					</div>			
				</div>
				<div class="row">
					<div class="col-4 pt-2 my-1">
						<label class="form-label">비밀번호</label>
					</div>
					<div class="col-8 my-1">
						<input name="pwd" type="password" class="form-control">
					</div>
				</div>
			</div>
			
			<!-- 로그인 버튼 -->  
			<div class="clear"></div>
			<div id="login_buttons" class="row my-3">
				<div class="col-4"></div>
				<div class="col-3 ps-4">
					<input type="submit" class="btn btn-outline-secondary" value="확인" >
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