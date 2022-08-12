<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
 
<%@ include file="../header.jsp" %>   
<%@ include file="sub_img.jsp"%> 
<%@ include file="sub_menu.jsp" %>

<!-- javascript -->
<script type="text/javascript" src="<c:url value="/js/member_join.js" />" charset="UTF-8"></script>
<script type="text/javascript" src="<c:url value="/js/member_postcode.js" />" charset="UTF-8"></script>

<!-- daum address/postcode searcher -->
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
	
<style>
article#join_box {
    float:left;
    margin-left:50px;	
	width:680px;
	font-size:13pt;
	line-height:20pt;
}	

form fieldset legend.join_title {
	font-size: 17pt; 
	border-bottom: 1px dotted #ccc; 
	width: 680px; 
	padding: 10px 0; 
	margin:20px 0;
}
</style>

<article id="join_box" class="container">

	<h2 >회원 가입</h2>
	
	<form id="join"  method="post" action="${contextPath}/member/join_proc" name="formm">
	
		<!-- BASIC INFO -->
		<fieldset>
			<legend class="join_title">필수 정보</legend>
			
			<!-- ID -->
			<div class="row my-2">
				<div class="col-3">
        			<label class="form label">ID</label>
        		</div>
        		<div class="col-8">
        			<input type="text" id="id" name="id" class="form-control" required
        				   pattern="/^[a-zA-Z]{1}\w{7,19}$/"
        				   title="ID는 영문으로 시작하여 영문/숫자 조합의 8~20글자로 입력하시오.">
        		</div>
        	</div>
        	
        	<!-- ID ERROR MESSAGE -->
        	<div class="row text-danger">
        		<div class="col-3"></div>
        		<div class="col-8" id="id_err_msg"></div>
        	</div>
        	
        	
        	<!-- PASSWORD -->
        	<div class="row my-2">
				<div class="col-3">
        			<label class="form label">비밀번호</label>
        		</div>
        		<div class="col-8">
        			<input type="password" name="pwd" id="pwd" class="form-control" required
        				   pattern="(?=.*[a-zA-Z])((?=.*\d)(?=.*\W)).{8,20}"
        				   title="비밀번호는 영문/숫자 조합에 특수문자 1개 이상을 넣어 8~20글자로 입력하시오.">
        		</div>
        	</div>
        	
        	<!-- PWD ERROR MESSAGE1 -->
        	<div class="row text-danger">
        		<div class="col-3"></div>
        		<div class="col-8" id="pwd_err_msg1"></div>
        	</div>
        	
        	<!-- PWD CHECK -->
        	<div class="row my-2">
				<div class="col-3">
        			<label class="form label">비밀번호 확인</label>
        		</div>
        		<div class="col-8">
        			<input type="password" id="pwdCheck" name="pwdCheck" class="form-control" required>
        		</div>
        	</div>
        	
        	<!-- PWD ERROR MESSAGE2 -->
        	<div class="row text-danger">
        		<div class="col-3"></div>
        		<div class="col-8" id="pwd_err_msg2"></div>
        	</div>
        	
        	<!-- NAME --> 
        	<div class="row my-2">
				<div class="col-3">
        			<label class="form label">이름</label>
        		</div>
        		<div class="col-8">
        			<input type="text" id="name" name="name" class="form-control" required
        				   pattern="[가-힣]{2,13}"
        				   title="이름은 한글 2~13글자로 입력하시오.">
        		</div>
        	</div>
        	
        	<!-- NAME ERROR MESSAGE -->
        	<div class="row text-danger">
        		<div class="col-3"></div>
        		<div class="col-8" id="name_err_msg"></div>
        	</div>
        	
        	<!-- E-MAIL -->     	
        	<div class="row my-2">
				<div class="col-3">
        			<label class="form label">이-메일</label>
        		</div>
        		<div class="col-8">
        			<input type="text" name="email" id="email" class="form-control" required
        				   pattern="[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}"
        				   title="올바른 이메일 형식이 아닙니다.">
        		</div>
        	</div>
        	
        	<!-- E-MAIL ERROR MESSAGE -->
        	<div class="row text-danger">
        		<div class="col-3"></div>
        		<div class="col-8" id="email_err_msg"></div>
        	</div>
        	
        	<!-- PHONE -->
        	<div class="row my-2">
				<div class="col-3">
        			<label class="form label">휴대폰 번호</label>
        		</div>
        		<div class="col-8">
        			<input type="text" name="phone" id="phone" class="form-control" required
        				   pattern="01\d{1}-\d{3,4}-\d{4}"
        				   placeholder="010-1234-5678"
        				   title="010-1234-5678 형식으로 입력하시오.">
        		</div>
        	</div>
        	
        	<!-- PHONE ERROR MESSAGE -->
        	<div class="row text-danger">
        		<div class="col-3"></div>
        		<div class="col-8" id="phone_err_msg"></div>
        	</div>
		</fieldset>
		
		<!-- OPTIONAL -->
		<fieldset>
			<legend class="join_title">선택 정보</legend>
			
			<!-- ZIP CODE / SEARCH -->
			<div class="row my-2">
				<div class="col-3">
        			<label class="form label">우편 번호</label>
        		</div>
        		<div class="col-3">
        			<input type="text" id="zipNum" name="zipNum" class="form-control bg-light" readonly>
        		</div>
        		<div class="col-3">
        			<input type="button" class="btn btn-outline-secondary" onclick="getPostcodeAddress()" value="우편번호 검색">
        		</div>
        		<div class="col-2">
        			<input type="button" class="btn btn-outline-secondary" onclick="addressInitBtn()"	  value="주소 초기화">
        		</div>
        	</div>
        	
        	<!-- ADDRESS1 -->
        	<div class="row my-2">
				<div class="col-3">
        			<label class="form label">주소 (도로명)</label>
        		</div>
        		<div class="col-8">
        			<input type="text" id="address1" name="address1" class="form-control bg-light" readonly>
        		</div>
        	</div>
        	
        	<!-- ADDRESS2 -->
        	<div class="row my-2">
				<div class="col-3">
        			<label class="form label">상세 주소</label>
        		</div>
        		<div class="col-8">
        			<input type="text" id="address2" name="address2" class="form-control">
        		</div>
        	</div>
        	
        	<!-- ADDRESS ERROR MESSAGE -->
        	<div class="row text-danger">
        		<div class="col-3"></div>
        		<div class="col-8" id="address_err_msg"></div>
        	</div>
		</fieldset>
		
		<div id="buttons" class="row mb-3">
			<div class="col-3"></div>
			<div class="col-4 ps-5 pe-3">
				<input type="submit" id="join_submit" class="btn btn-outline-secondary px-5" value="회원 가입 ">
			</div>
			<div class="col-4">
				<input type="reset" class="btn btn-outline-secondary px-5" value="취소 ">
			</div>
			<div class="col-1"></div>
        </div>
	</form>
</article>

<%@ include file="../footer.jsp" %>
  