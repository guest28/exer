<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  

<script>
function move_inactive_member() {
	
	if (confirm("정말 회원 탈퇴하시겠습니까?")) {
		
		console.log("탈퇴");
		location.href="${contextPath}/member/member_inactive";
		
	} else {
		
		console.log("탈퇴 취소");
		alert("탈퇴 처리를 취소하였습니다");
	}
	
}
</script>

<nav id="sub_menu">
  <ul>
    <li><a href="${contextPath}/member/member_info">회원 정보 확인</a></li>
    <li><a href="${contextPath}/member/member_check">회원 정보 변경</a></li>
    <li><a href="${contextPath}/member/member_pwd">비밀번호 수정</a></li>
    <li><a href="">주문 내역</a></li>
    <li><a href="">문의 사항</a></li>
    <li><a href="#" onclick="move_inactive_member()">회원 탈퇴</a></li>
  </ul>
</nav>