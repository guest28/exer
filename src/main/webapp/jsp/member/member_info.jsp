<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>
<%@ include file="../sub_menu_member.jsp" %>

<!-- css -->
<link href="<c:url value="/css/member_info.css" />" rel="stylesheet"> 


<article id="view_box"  class="container">


	<h2>회원 정보</h2>
    
	<form id="view">
	    
	    <!-- BASIC INFO -->
	    <fieldset>
			<legend class="view_title">필수 정보</legend>
	      
			<!-- ID -->
			<div class="row my-2">
	      		<div class="col-3">
					<label class="form-label">ID</label>
	      		</div>
	      		<div class="col-8">
	      			${LOGIN_USER.id}
	      		</div>	
			</div>
	      
			<!-- PASS WORD -->
			<div class="row my-2">
				<div class="col-3">
	      			<label class="form-label">비밀번호</label>
	      		</div>
		      	<div class="col-8">
		      		**********
				</div>	
			</div>
	     	      
			<!-- NAME --> 
			<div class="row my-2">
	      		<div class="col-3">
	      			<label class="form-label">이름</label>
	      		</div>
				<div class="col-8 mt-1">
		      		${LOGIN_USER.name}
		      	</div>	
			</div>
	      
			<!-- E-MAIL -->  
			<div class="row my-2">
	      		<div class="col-3">
	      			<label class="form-label">이-메일</label>
	      		</div>
	      		<div class="col-8 mt-1">
	      			${LOGIN_USER.email}
	      		</div>	
			</div>
	      
			<!-- PHONE -->
			<div class="row my-2">
				<div class="col-3">
	      			<label class="form-label">휴대폰 번호</label>
	      		</div>
	      		<div class="col-8 mt-1">
	      			${LOGIN_USER.phone}
	      		</div>	
			</div>
		</fieldset>
	    
	    <!-- OPTIONAL -->
	    <fieldset>
			<legend class="view_title">선택 정보</legend>
	      
			<!-- ZIP CODE / SEARCH -->
			<div class="row my-2">
				<div class="col-3">
					<label class="form-label">우편 번호</label>
				</div>
				<div class="col-3">
					${LOGIN_USER.zipNum}
				</div>	
				<div class="col-5"></div>
			</div>

			<!-- ADDRESS1 -->
			<div class="row my-2">
				<div class="col-3">
					<label class="form-label">주소 (도로명)</label>
				</div>
				<div class="col-8">
					${LOGIN_USER.address1}
				</div>	
			</div>

			<!-- ADDRESS2 -->
			<div class="row my-2">
				<div class="col-3">
					<label class="form label">상세 주소</label>
				</div>
				<div class="col-8">
					${LOGIN_USER.address2}
	    		</div>	
			</div>
		</fieldset>
		
		<div id="buttons" class="row mb-3">
			<div class="col-3"></div>
			<div class="col-4 ps-5 pe-3">
				<input type="button" class="btn btn-outline-secondary px-5" value="회원 정보 변경 "
					  onclick="location='${contextPath}/member/member_check'">
			</div>
			<div class="col-5"></div>
        </div>
	</form>
</article>    

<%@ include file="../footer.jsp" %>