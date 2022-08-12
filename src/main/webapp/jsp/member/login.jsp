<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>  
<%@ include file="sub_img.jsp"%> 
<%@ include file="sub_menu.jsp" %>       

<style>
/* 로그인 박스 */
#login_box {
	width:500px;
	font-size:14pt;
}

/* 로그인 버튼 */
#login_buttons {
	width:500px;
}
</style>

<!-- 로그인 에러 메세징 -->
<script>
	var msg = "${login_err_msg}";

	console.log("msg : " + msg);
	
	if(msg != '') {
		
		alert(msg);
	}
</script>

<article>
	<h1>Login</h1>
	
	<form method="post" action="${contextPath}/login/login_proc">
	
 		<!-- 로그인 박스 -->
		<div id="login_box" class="my-5">
			
			<!-- ID -->
			<div class="row">
				<div class="col-4 pt-2 my-1">
					<label class="form-label">ID</label>
				</div>
				<div class="col-8 my-1">
					<input name="id" type="text" class="form-control" pattern="[a-zA-Z]{1}\w{7,19}"
						   title="ID는 영문으로 시작하여 영문/숫자 조합의 8~20글자로 입력하시오." maxlength="20">
				</div>			
			</div>
			
			<!-- PASSWORD -->
			<div class="row">
				<div class="col-4 pt-2 my-1">
					<label class="form-label">비밀번호</label>
				</div>
				<div class="col-8 my-1">
					<input name="pwd" type="password" class="form-control" pattern="(?=.*[a-zA-Z])((?=.*\d)(?=.*\W)).{8,20})"
						   title="비밀번호는 영문/숫자 조합에 특수문자 1개 이상을 넣어 8~20글자로 입력하시오." maxlength="20">
				</div>
			</div>
		</div>
		
		<div class="clear"></div>
		
		<!-- 로그인 버튼 -->  
		<div id="login_buttons" class="row my-3">
			<div class="col-1"></div>
			<div class="col-3 ps-4">
				<input type="submit" class="btn btn-outline-secondary" value="로그인">
			</div>	
        	<div class="col-3">
	        	<input type="button" class="btn btn-outline-secondary" onclick="location='${contextPath}/member/contract'" value="회원 가입">
	        </div>         
        	<div class="col-5 ps-3">
            	<input type="button" class="btn btn-secondary" onclick="location='NonageServlet?command=find_id_form'" value="아이디/비밀번호 찾기">    
            </div>          
        </div>
        
	</form>  
</article>

<%@ include file="../footer.jsp" %>      