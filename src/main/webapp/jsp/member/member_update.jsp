<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- spring:hasBindErrors -->
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ include file="../header.jsp" %>
<%@ include file="../sub_menu_member.jsp" %>

<!-- css -->
<link href="<c:url value="/css/member_info.css" />" rel="stylesheet">

<!-- daum address/postcode searcher -->
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>

<!-- 회원 정보 수정 성공/실패 메시징 -->
<script>
var msg = "${update_success_msg}";

if(msg.trim() != '') {
	alert(msg);
}
</script>

<article id="view_box"  class="container">
	  
	<h2>회원 정보 수정</h2>
	
	<%-- 
	<!-- 에러 표시 패널 -->
	<div style="background:yellow; width:100%; height:200px; overflow:auto; font-size:9pt">
		<spring:hasBindErrors name="memberDTO">
			${errors.getFieldError("pwd").defaultMessage}<br>
			${errors.getFieldError("email").defaultMessage}<br>
			${errors.getFieldError("phone").defaultMessage}<br>
		</spring:hasBindErrors>
	</div>
	--%>
	
	<form id="update" action="${contextPath}/member/update_proc" method="post" name="formm">
	
		<!-- BASIC INFO -->
		<fieldset>
			<legend class="view_title">필수 정보</legend>
	  
			<!-- ID -->
			<div class="row my-2">
				<div class="col-3">
					<label class="form-label">ID</label>
				</div>
				<div class="col-8">
					<input type="text" id="id" name="id" class="form-control bg-light" readonly
					       value="${LOGIN_USER.id}">
				</div>	
			</div>
	
			<!-- PASSWORD -->
			<div class="row my-2">
				<div class="col-3">
	    			<label class="form-label">비밀번호</label>
	    		</div>
	    		<div class="col-8">
	        			<input type="password" name="pwd" id="pwd" class="form-control">
	        	</div>
	        </div>
	        
	        <!-- PWD ERROR MESSAGE1 -->
        	<div class="row text-danger">
        		<div class="col-3"></div>
        		<div class="col-8" id="pwd_err_msg1">
        			<spring:hasBindErrors name="memberDTO">
        				${errors.getFieldError("pwd").defaultMessage}
        			</spring:hasBindErrors>
        			${pwd_err_msg1}
        		</div>
        	</div>
		   
			<!-- PWD CHECK -->	
			<div class="row">
	    		<div class="col-3">
	    			<label class="form-label">비밀번호 확인</label>
	    		</div>
	    		<div class="col-8">
	        		<input type="password" name="pwdCheck" id="pwdCheck" class="form-control">
	        	</div>
	    	</div>

	    	<!-- PWD CHECK ERROR MESSAGE2 -->
			<div class="row text-danger">
	    		<div class="col-3"></div>
				<div class="col-8" id="pwd_err_msg2">
					<spring:hasBindErrors name="memberDTO">
						${errors.getFieldError("pwdCheck").defaultMessage}
						</spring:hasBindErrors>
						${pwd_err_msg2}	
				</div>    
	    	</div>

			<!-- NAME --> 
			<div class="row mb-2">
				<div class="col-3">
					<label class="form-label">이름</label>
				</div>
				<div class="col-8">
					<input type="text" id="name" name="name" class="form-control bg-light" readonly
				    	   value="${LOGIN_USER.name}">
				</div>	
			</div>
	
			<!-- E-MAIL -->     
			<div class="row my-2">
				<div class="col-3">
					<label class="form-label">이-메일</label>
				</div>
				<div class="col-8">
					<input type="text" name="email" id="email" class="form-control" required value="${LOGIN_USER.email}">
				</div>		
			</div>
	
			<!-- 회원 이메일 에러 메시지 -->
	    	<div class="row text-danger">
	    		<div class="col-3"></div>
				<div class="col-8" id="email_err_msg">
					<spring:hasBindErrors name="memberDTO">
						${errors.getFieldError("email").defaultMessage}
					</spring:hasBindErrors>
					${email_err_msg}
				</div>    
			</div>
	    
	    	<!-- PHONE -->
			<div class="row my-2">
				<div class="col-3">
					<label class="form-label">휴대폰 번호</label>
				</div>
				<div class="col-8">
				<input type="text" id="phone" name="phone" class="form-control" required
				   	   value="${LOGIN_USER.phone}">
				</div>	
			</div>
	
			<!-- PHONE ERROR MESSAGE -->
	    	<div class="row text-danger">
	    		<div class="col-3"></div>
				<div class="col-8" id="phone_err_msg">
					<spring:hasBindErrors name="memberDTO">
						${errors.getFieldError("phone").defaultMessage}
					</spring:hasBindErrors>
					${phone_err_msg}
				</div>    
	    	</div>
	    	
		</fieldset>
		
		<!-- OPTIONAL -->  
		<fieldset>
		    	<legend class="update_title">선택 정보</legend>
		    
		    <!-- ZIP CODE / SEARCH -->
			<div class="row my-2">
				<div class="col-3">
					<label class="form-label">우편 번호</label>
				</div>
				<div class="col-3">
					<input type="text" id="zipNum" name="zipNum" class="form-control bg-light" readonly
				    	   value="${LOGIN_USER.zipNum}">
				</div>	
				<div class="col-3">
					<input type="button" class="btn btn-outline-secondary"  onClick="getPostcodeAddress()" value="우편번호 검색">
				</div>
				<div class="col-2">
					<input type="button" class="btn btn-outline-secondary" onClick="addressInitBtn()" value="주소 초기화">
				</div>
		    </div>
		
			<!-- ADDRESS1 -->
			<div class="row my-2">
				<div class="col-3">
					<label class="form-label w-100">주소 (도로명)</label>
				</div>
				<div class="col-8">
					<input type="text" id="address1" name="address1" class="form-control bg-light" readonly
			       		   value="${LOGIN_USER.address1}">
				</div>	
			</div>

			<!-- ADDRESS2 -->
			<div class="row my-2">
				<div class="col-3">
					<label class="form-label">상세 주소</label>
				</div>
				<div class="col-8">
					<input type="text" id="address2" name="address2" class="form-control" 
						   value="${LOGIN_USER.address2}">
				</div>	
			</div>
		
			<!-- ADDRESS ERROR MESSAGE -->
			<div class="row text-danger">
				<div class="col-3"></div>
				<div class="col-8" id="address_err_msg">${address_err_msg}</div>   
			</div>
			  
		</fieldset>

		<div id="buttons" class="row mb-3">
			<div class="col-1"></div>
			<div class="col-3">
				<input type="submit" id="update_submit" class="btn btn-outline-secondary px-5" value="저장">
			</div>
			<div class="col-3"> 
				<input type="reset"  class="btn btn-outline-secondary px-5" value="취소">
			</div>
			<div class="col-3">
				<input type="button" class="btn btn-secondary px-5" value="회원 탈퇴 "
				       onclick="location.href='${contextPath}/member/member_inactive'">
			</div>
		</div>
	</form>
</article>

<%@ include file="../footer.jsp" %>