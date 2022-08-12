<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  

<!--  contextPath -->
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Project Shop</title>
	
	<!-- jquery -->
	<script src="<c:url value="/jquery/3.6.0/jquery.min.js" />"></script> 

	<!-- bootstrap -->
	<link href="<c:url value="/bootstrap/5.1.3/css/bootstrap.min.css" />" rel="stylesheet" />
	<script src="<c:url value="/bootstrap/5.1.3/js/bootstrap.bundle.min.js" />"></script>
	
	<!-- google material(icon) -->
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	
	<!-- css -->
	<link href="<c:url value="/css/shopping01.css" />" rel="stylesheet">
	<link href="<c:url value="/css/slide_main01.css" />" rel="stylesheet"> 
	
	<!--
	<style>
	table#flag_tbl td {
		text-align:left;
	}
	</style>
	-->
</head>

<body>

<!-- 플래그 변수 모니터링 패널 -->
<!-- 
<div id="flag_box" class="offcanvas offcanvas-start">
	<div class="offcanvas-header">
	    <h4 class="offcanvas-title">플래그 변수 현황</h4>
	    <button type="button" class="btn-close" data-bs-dismiss="offcanvas"></button>
	</div>
	<div class="offcanvas-body">
		<table id="flag_tbl" class="table table-striped">
			<tr>
				<th>플래그 변수명</th>
				<th>값</th>
			</tr>
			<tr>
				<td>아이디 중복 점검</td>
				<td id="id_check_flag">false</td>
			</tr>
			<tr>
				<td>비밀번호 유효성 점검</td>
				<td id="pwd_check_flag1">false</td>
			</tr>
			<tr>
				<td>비밀번호 일치 점검</td>
				<td id="pwd_check_flag2">false</td>
			</tr>
			<tr>
				<td>이름 유효성 점검</td>
				<td id="name_check_flag">false</td>
			</tr>
			<tr>
				<td>이메일 점검</td>
				<td id="email_check_flag">false</td>
			</tr>
			<tr>
				<td>휴대폰 점검</td>
				<td id="phone_check_flag">false</td>
			</tr>
			
			
			
		</table>
	
	
	</div>
</div>
 -->

<div id="wrap">

	<header>
	
    <!-- 로고 들어가는 곳 -->  
		<div id="logo">
			<a href="${contextPath}?command=index">
				<img src="${contextPath}/img/logo1.gif" width="180" height="100" alt="projectmall">
			</a>  
    	</div>
		
		<nav id="catagory_menu">
			<ul>
				<!-- 로그인/로그아웃 상태 -->
					<!-- Role에 따른 로그인/로그아웃 상태 -->
				<c:choose>
					<c:when test="${empty sessionScope.LOGIN_USER}">
						<li>         
							<a href="${contextPath}/login/login_form" style="width:100px;">LOGIN</a> 
						</li>								       
						<li>/</li>						
						<li><a href="${contextPath}/member/contract">JOIN</a></li>
					</c:when>
					
					<c:otherwise>
						<li style="color:orange">
							<a href="${contextPath}/member/member_info" style="width:120px;">
		         				${sessionScope.LOGIN_USER.name}(${sessionScope.LOGIN_USER.id})
		         			</a>
		         			<c:if test="${sessionScope.LOGIN_ROLE_USER.role eq 'admin'}">
			     				<a href="${contextPath}/admin/home" style="width:100px;">ADMIN</a>
			    			</c:if>
			    			<c:if test="${sessionScope.LOGIN_ROLE_USER.role eq 'guest'}">
			    			환영합니다
			    			</c:if>
						</li>
						<li><a href="${contextPath}/login/logout">LOGOUT</a></li>
					</c:otherwise>       
				</c:choose>
				<li>/</li>
				<li>
					<a href="${contextPath}/cart/cart_list">CART</a>
				</li>
				<li>/</li>
				<li>
					<a href="${contextPath}/member/member_info">MY PAGE</a>
				</li>
				<li>/</li>
				<li>
  					<a href="${contextPath}/qna/qna_list">Q&amp;A(1:1)</a>
				</li>
				<!-- 플래그 변수 확인창 버튼 -->
				<!--
				<li>/</li>
				<li>
					<a href="#" data-bs-toggle="offcanvas" data-bs-target="#flag_box">
						플래그 변수
					</a>
				</li>
				 -->
			</ul>
		</nav>

		<nav id="top_menu">
			<ul>
 				<li><a href="${contextPath}/product/category?kind=1">Heels</a></li>
		        <li><a href="${contextPath}/product/category?kind=2">Boots</a></li>  
			  	<li><a href="${contextPath}/product/category?kind=3">Sandals/Slippers</a></li> 
			  	<li><a href="${contextPath}/product/category?kind=4">Bloafer/Mule</a></li> 
			  	<li><a href="${contextPath}/product/category?kind=5">Slingback</a></li>  
			</ul>
		</nav>
		<div class="clear"></div>
		<hr>
	</header>
