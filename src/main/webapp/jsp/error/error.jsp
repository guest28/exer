<%@ page isErrorPage="true" 
		 contentType="text/html; charset=UTF-8"
    	 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>error</title>
<script>
	window.onload=function() {
		
		var msg = "${err_msg}";
		
		if(msg.trim() != '') {
			
			alert(msg);
		}
		
		location.href = "${pageContext.request.contextPath}/${move_path}";	//페이지 이동
	} //
</script>
</head>
<body>
</body>
</html>