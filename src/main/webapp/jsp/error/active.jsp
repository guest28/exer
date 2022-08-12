<%@ page isErrorPage="true" 
		 contentType="text/html; charset=UTF-8"
    	 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>active</title>

<script>
	window.onload=function() {
		
		var msg = "${err_msg}";
		
		if(msg.trim() != '') {
			
			if(confirm("${err_msg} 휴면 계정을 활성하시겠습니까?")) {
				
				console.log("휴면 계정 활성 페이지로 이동");
				location.href = "${pageContext.request.contextPath}/${move_path}";
				
			} else {
				
				console.log("휴면 계정 활성 페이지로 이동 취소");
				location.href = "${pageContext.request.contextPath}/login/login_form";
			}
		}
		
	} //
</script>

</head>
<body>
</body>
</html>