<%@ page isErrorPage="true" 
		 contentType="text/html; charset=UTF-8"
    	 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>403 patch</title>
<script>
	window.onload=function() {
		
		alert("권한이 없습니다.\n홈으로 이동하겠습니다.");
		
		setTimeout(function() {
			location.href= "${pageContext.request.contextPath}"; // 페이지 이동
		}, 100);
	} //
</script>
</head>
<body>
</body>
</html>