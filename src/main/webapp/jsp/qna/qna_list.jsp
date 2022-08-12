<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>
<%@ include file="sub_img.jsp"%>
<%@ include file="../sub_menu_member.jsp"%>

<article>

	<h3 class="my-4">1:1 고객 게시판</h3>
	
	<h6 class="my-4">고객님의 질문에 대해서 운영자가 1:1 답변을 드립니다.</h6>
	
	<form name="formm" method="post">
	
		<table id="qnaList" class="table">
			<tr align="center">
				<th>No.</th>
				<th>글번호</th>
				<th>제목</th>
				<th>등록일</th>
				<th>답변 여부</th>
			</tr>
			
			<c:if test="${not empty qnaList}">
				<c:forEach items="${qnaList}" var="qnaVO" varStatus="st">
					<tr>
						<td>${st.count}</td>
						<td>${qnaVO.qseq}</td>
						<td>
							<a href="${contextPath}/qna/qna_view?qseq=${qnaVO.qseq}">
								${qnaVO.subject}
							</a> 
						</td>
						<td>
							<fmt:formatDate value="${qnaVO.indate}" type="date" />
						</td>
						<td>
							<c:choose>
								<c:when test="${qnaVO.rep==1}">X</c:when>
								<c:when test="${qnaVO.rep == 2}">Y</c:when>
							</c:choose>
						</td>
					</tr>
					
				</c:forEach>
			</c:if>	
			
			<c:if test="${empty qnaList}">
				<tr>
					<td colspan="5" class="py-4">
						<div align="center">게시글이 없습니다</div>
					</td>
				</tr>
			</c:if>
			
		</table>
		
		<div id="buttons" class="row col-10 float-end">
		
			<div class="col-2"></div>
			
          	<div class="col-4 pr-1">
          	
				<input type="button" class="btn btn-secondary" value="1:1 문의하기"
					onclick="location.href='${contextPath}/qna/qna_write_form'">
			</div>
			
			<div class="col-3">	
				<input type="button" class="btn btn-secondary" value="쇼핑 계속하기"
					onclick="location.href='${contextPath}/'">
			</div>
			
			<div class="col-1"></div>			
			
		</div>
		
	</form>
	
</article>

<!-- footer -->
<%@ include file="../footer.jsp" %>