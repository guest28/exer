<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 
<%@ include file="../header.jsp" %>
<%@ include file="sub_menu_member.jsp" %>
 --%>
<!-- css -->
<link href="<c:url value="/css/member_info.css" />" rel="stylesheet">

<script type="text/javascript" src="<c:url value="/js/member_update.js" />" charset="UTF-8"></script>

<!-- 회원 정보 수정 성공/실패 메시징 -->
<script>
var msg = "${update_success_msg}";

if (msg.trim() != '') {
	alert(msg);
}
</script>

<article id="view_box"  class="container">
	  
	<h2>비밀번호 변경</h2>
	  
	<form id="pwd_update" action="${contextPath}/member/update_proc" method="post" name="formm">
	
		<fieldset>
		
			<div id="login_box" class="my-5">
				
				<!-- ID -->
				<div class="row">
					<div class="col-4 pt-2 my-1">
						<label class="form-label">ID</label>
					</div>
					<div class="col-8 my-1">
						<input type="text" name="id"  class="form-control" value="${LOGIN_USER.id}" readonly>
					</div>			
				</div>
				
				<!-- PASSWORD -->
				<div class="row">
					<div class="col-4 pt-2 my-1">
						<label class="form-label">비밀번호</label>
					</div>
					<div class="col-8 my-1">
						<input type="password" id="beforePwd" name="beforePwd"  class="form-control" required>
					</div>
				</div>
				
		        <br>
		        
				<!-- PWD ERROR MESSAGE1 -->
				<div class="row">
					<div class="col-4 pt-2 my-1">
		    			<label class="form-label">새 비밀번호</label>
		    		</div>
		    		<div class="col-8 my-1">
		        			<input type="password" name="afterPwd" id="afterPwd" class="form-control" required
		        				   pattern="(?=.*[a-zA-Z])((?=.*\d)(?=.*\W)).{8,20}"
		        				   title="비밀번호는 영문/숫자 조합에 특수문자 1개 이상을 넣어 8~20글자로 입력하시오.">
		        	</div>
		        </div>
		   
				<!-- PWD CHECK -->		
				<div class="row">
		    		<div class="col-4 pt-2 my-1">
		    			<label class="form-label">비밀번호 확인</label>
		    		</div>
		    		<div class="col-8 my-1">
		        			<input type="password" name="afterPwdCheck" id="afterPwdCheck" class="form-control" required>
		        	</div>
		    	</div>
			
		
				<!-- PWD ERROR MESSAGE2 -->
				<div class="row text-danger">
		    		<div class="col-3"></div>
					<div class="col-8" id="pwd_check_err_msg2">
						<spring:hasBindErrors name="memberDTO">
							${errors.getFieldError("pwdCheck").defaultMessage}
							</spring:hasBindErrors>
							${pwd_err_msg}		
					</div>    
		    	</div>
		    </div>
		    
	    	<!-- 저장 버튼 -->  
			<div class="clear"></div>
			<div id="login_buttons" class="row my-3">
				<div class="col-4"></div>
				<div class="col-3 ps-4">
					<input type="submit" class="btn btn-outline-secondary" value="저장" >
				</div>	
	        	<div class="col-3">
		        	<input type="reset" class="btn btn-outline-secondary"  value="취소"
		        		   onclick="location='${contextPath}/member/member_info'">
		        </div>         
	        	<div class="col-2">
	            </div>          
	        </div>
	    	
	    </fieldset>
	</form>
</article>
<%-- 
<%@ include file="../footer.jsp" %>
 --%>