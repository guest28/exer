<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- contextPath -->
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko-kr">
<head>
<meta charset="UTF-8">
<title>JavaMall Admin</title>

	<!-- jquery -->
	<script src="<c:url value="/jquery/3.6.0/jquery.min.js" />"></script>
	
	<!-- bootstrap -->
	<link href="<c:url value="/bootstrap/5.1.3/css/bootstrap.min.css" />" rel="stylesheet" />
	<script src="<c:url value="/bootstrap/5.1.3/js/bootstrap.bundle.min.js" />"></script>
	
	<!-- google material(icon) -->
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	
	<!-- css -->
	<link href="<c:url value="/css/admin_header.css" />" rel="stylesheet">
</head>

<body>

<!-- 전체 레이아웃 -->
<div id="wrap">
	
	<header>
	 	<div style="margin:30px 0 0 0;">
	 	
			<!--로고 -->  
		    <div id="logo" >
				<a href="${contextPath}/">
					<img src="${contextPath}/img/logo1.gif" width="180" height="100" alt="projectmall">
				</a>  
			</div>
			
			<!-- 관리자 메인 메뉴 -->
			<nav>
				<ul>
					<li><a href="${contextPath}/admin/product/product_list">상품 리스트</a></li>
					<li><a href="${contextPath}/admin/order/order_list">주문 리스트</a></li>
					<li><a href="${contextPath}/admin/member/member_list">회원 리스트</a></li>
					<li><a href="${contextPath}/admin/qna/qna_list">Q&amp;A리스트</a></li>
					<li><a href="${contextPath}/login/logout">로그아웃</a></li>
				</ul>
			</nav>
		</div>
	</header>