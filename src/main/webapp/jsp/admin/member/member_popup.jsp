<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- modal -->
<div id="member_modal">
    
	<!-- modal header -->
	<div id="member_modal_header">
		
		<!-- bullet -->
		<div id="member_modal_header_icon">
			<span class="material-icons">
				web_asset
			</span>		        
		</div>
	
		<!-- title -->
		<div id="member_modal_header_title">
		</div>
	
		<!-- closing button icon -->
		<div id="member_modal_header_close">
			<a href="#" id="member_modal_close_btn">
				<span class="material-icons" style="color:#333">
					clear
				</span>
			</a>
		</div>
	</div>

	<!-- modal content -->
	<div id="member_modal_body">
	 	  
		<!-- ID -->
		<div class="row">
			<div class="col-4">
				<label>ID</label>
			</div>
			<div id="user_id" class="col-8 mt-1">
			</div>	
		</div>
	 
		<!-- 비밀번호 -->
		<div class="row">
			<div class="col-4">
				<label>비밀번호</label>
			</div>
			<div id="user_pwd" class="col-8 mt-1">
				**********
			</div>	
		</div>
	
		<!-- 이름 -->
		<div class="row">
			<div class="col-4">
				<label>이름</label>
			</div>
			<div id="user_name" class="col-8 mt-1">
			</div>	
		</div>
	 
		<!-- 이-메일 -->
		<div class="row">
			<div class="col-4">
				<label>이-메일</label>
			</div>
			<div id="user_email" class="col-8 mt-1">
			</div>	
		</div>
	 
		<!-- 연락처 -->
		<div class="row">
			<div class="col-4">
				<label >연락처</label>
			</div>
			<div id="user_phone" class="col-8 mt-1">
			</div>	
		</div>
	 
		<!-- 우편번호 -->
		<div class="row">
			<div class="col-4">
				<label>우편번호</label>
			</div>
			<div id="user_zipNum" class="col-8 mt-1">
			</div>	
		</div>
	
		<!-- 주소 -->
		<div class="row">
			<div class="col-4">
				<label>주소</label>
			</div>
			<div class="col-8">
				<div id="user_address1">
				</div>
				<div id="user_address2">
				</div>
			</div>	
		</div>
	</div>

	<!-- modal footer -->
	<div id="member_modal_footer">
	</div>
</div>