<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- contextPath -->
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko-kr">
<head>
<meta charset="UTF-8">

<!-- jQuery를 적용하는 방법들 -->
<script src="./jquery/3.6.0/jquery.min.js"></script>	<!-- 상대경로 -->
<script src="${pageContext.request.contextPath}/jquery/3.6.0/jquery.min.js"></script>
<script src="<c:url value="/jquery/3.6.0/jquery.min.js" />"></script>
<script src="<%=request.getContextPath() %>/jquery/3.6.0/jquery.min.js"></script>
<script src="${contextPath}/jquery/3.6.0/jquery.min.js"></script>

<!-- jQuery를 사용 해보자 -->
<script>
$(function(){
	$("body").css("background", "green");
});
</script>

<title>Insert title here</title>
</head>
<body>
demo
</body>
</html>