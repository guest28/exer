<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>  
<%@ include file="sub_img.jsp"%> 
<%@ include file="sub_menu.jsp" %>       

<!-- javascript -->
<script type="text/javascript" src="<c:url value="/js/member_active.js" />" charset="UTF-8"></script>

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

<article>
	<h1>휴면 ID 활성</h1>
	
	<form id="member_active" method="post" action="${contextPath}/member/active_proc" name="formm">
	
 		<!-- 로그인 박스 -->
		<div id="login_box" class="my-5">
		
			<!-- ID -->
			<div class="row">
				<div class="col-4 pt-2 my-1">
					<label class="form-label">ID</label>
				</div>
				<div class="col-8 my-1">
					<input type="text" id="id" name="id" class="form-control" required>
				</div>			
			</div>
			<!-- 간격 만들기용 -->
        	<div class="row text-danger">
        		<div class="col-4 pt-2 my-1"></div>
        		<div class="col-8 my-1"></div>
        	</div>
			
			<!-- PASSWORD -->
			<div class="row">
				<div class="col-4 pt-2 my-1">
					<label class="form-label">비밀번호</label>
				</div>
				<div class="col-8 my-1">
					<input id="pwd" name="pwd" type="password" class="form-control" pattern="(?=.*[a-zA-Z])((?=.*\d)(?=.*\W)).{8,20})"
						   title="비밀번호는 영문/숫자 조합에 특수문자 1개 이상을 넣어 8~20글자로 입력하시오." maxlength="20">
				</div>
			</div>
			<!-- PWD ERROR MESSAGE1 -->
        	<div class="row text-danger">
        		<div class="col-4 pt-2 my-1"></div>
        		<div class="col-8 my-1" id="pwd_err_msg1"></div>
        	</div>
        	
        	<!-- E-MAIL -->
			<div class="row">
				<div class="col-4 pt-2 my-1">
					<label class="form-label">이-메일</label>
				</div>
				<div class="col-8 my-1">
					<input type="text" id="email" name="email" class="form-control" required
        				   pattern="[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}"
        				   title="올바른 이메일 형식이 아닙니다.">
				</div>
			</div>
			
			<!-- E-MAIL ERROR MESSAGE -->
        	<div class="row text-danger">
        		<div class="col-4 pt-2 my-1"></div>
        		<div class="col-8 my-1" id="email_err_msg"></div>
        	</div>
        	
        	<!-- PHONE -->
			<div class="row">
				<div class="col-4 pt-2 my-1">
					<label class="form-label">연락처</label>
				</div>
				<div class="col-8 my-1">
					<input type="text" name="phone" id="phone" class="form-control" required
        				   pattern="01\d{1}-\d{3,4}-\d{4}"
        				   placeholder="010-1234-5678"
        				   title="010-1234-5678 형식으로 입력하시오.">
				</div>
			</div>
			
			<!-- PHONE ERROR MESSAGE -->
        	<div class="row text-danger">
        		<div class="col-4 pt-2 my-1"></div>
        		<div class="col-8 my-1" id="phone_err_msg"></div>
        	</div>
		</div>
		
		<div class="clear"></div>
		
		<!-- 로그인 버튼 -->  
		<div id="login_buttons" class="row my-3">
			<div class="col-1"></div>
			<div class="col-3 ps-4">
				<input type="submit" id="active_submit" class="btn btn-outline-secondary" value="확인 ">
			</div>	
        	<div class="col-3">
	        	<input type="reset" class="btn btn-outline-secondary" value="취소 ">
	        </div>         
        	<div class="col-5 ps-3">
            	<input type="button" value="아이디/비밀번호 찾기" class="btn btn-secondary" onclick="location='NonageServlet?command=find_id_form'">    
            </div>          
        </div>
	</form>  
</article>

<%@ include file="../footer.jsp" %>      
