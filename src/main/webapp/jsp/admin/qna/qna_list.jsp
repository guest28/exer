<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="qna_head.jsp" %>
<%-- <%@ include file="sub_menu.jsp"%> --%>

<script type="text/javascript">
function go_view(qseq) {

	var theForm = document.frm;
	
	theForm.qseq.value = qseq;
	theForm.action = "${contextPath}/admin/qna/qna_detail";
	theForm.submit();
}
</script>

<article class="pt-5">

	<!-- <h1>Q&amp;A 게시글 리스트</h1> -->
	
	<form name="frm" method="post">
	
		<input type="hidden" name="qseq">
		
		<table id="orderList" class="table">
		
			<tr>
				<th style="width:60px">No.</th>
				<th>글 번호&nbsp;&nbsp;&nbsp;&nbsp;답변여부</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
			</tr>
			
			<c:forEach items="${qnaList}" var="qnaVO" varStatus="st">
				<tr>
					<td>${pageVO.limit * (pageVO.currPage-1) + st.count}</td>
					<td>&nbsp;&nbsp;&nbsp;${qnaVO.qseq}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<c:choose>
							<c:when test="${qnaVO.rep=='1'}">
								<span style="font-weight: bold; color: blue">
									미처리
								</span>
							</c:when>
							<c:otherwise>답변 완료</c:otherwise>
						</c:choose>
					</td>
					<td>
						<a href="javascript:go_view('${qnaVO.qseq}')">
							${qnaVO.subject} 
						</a>
					</td>
					<td>${qnaVO.id}</td>
					<td><fmt:formatDate value="${qnaVO.indate}" /></td>
				</tr>
			</c:forEach>
			
		</table>
		
	</form>
	
	<!-- 페이징 -->
	<%@ include file="qna_list_paging.jsp" %>
	
</article>

<!-- footer -->
<%@ include file="../../footer.jsp" %>