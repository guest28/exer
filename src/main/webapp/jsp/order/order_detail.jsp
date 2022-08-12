<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>
<%@ include file="sub_img.jsp"%>
<%@ include file="../sub_menu_member.jsp"%>

<article>

	<h2>Order List</h2>
	
	<form name="formm" method="post">
	
		<table id="cartList" class="table">
			<tr>
				<th>상품명</th>
				<th>수량</th>
				<th>가격(원)</th>
				<th>주문일</th>
				<th>진행 상태</th>
			</tr>
			
			<c:forEach items="${orderList}" var="orderVO">
				<tr>
					<td>
						<a href="${contextPath}/product/product_detail?pseq=${orderVO.pseq}">
							${orderVO.pname}
						</a>
					</td>
					<td>
						${orderVO.quantity}
					</td>
					<td>
						<fmt:formatNumber value="${orderVO.price1 * orderVO.quantity}" type="number" />
					</td>
					<td><fmt:formatDate value="${orderVO.indate}" type="date" /></td>
					<td>처리 진행 중</td>
				</tr>
			</c:forEach>
			
			<tr>
				<th colspan="2">총액</th>
				<th colspan="2">
					<fmt:formatNumber value="${totalPrice}" currencySymbol="﻿￦"  type="currency" />
				</th>
				<th>주문 완료.</th>
			</tr>
		</table>

		<div class="clear"></div>
		
		<div id="buttons" style="float: right">
			<input type="button" class="btn btn-secondary" value="쇼핑 계속하기" class="cancel"
				onclick="location.href='${contextPath}/'">
		</div>
		
		</form>
		
</article>

<!-- footer -->
<%@ include file="../footer.jsp" %>