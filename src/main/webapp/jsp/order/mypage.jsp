<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- header -->
<%@ include file="../header.jsp" %>

<%@ include file="sub_img.jsp"%>
<%@ include file="../sub_menu_member.jsp"%>

<article>

	<h2>Order List</h2>
	
	<form name="formm" method="post">
	
		<table id="cartList" class="table">
			<tr>
				<th>주문일자</th>
				<th>주문번호</th>
				<th>상품명</th>
				<th>결제 금액(원)</th>
				<th>주문 상세</th>
			</tr>
			
			<c:forEach items="${orderList}" var="orderVO">
				<tr>
					<td>
						<fmt:formatDate value="${orderVO.indate}" type="date" />
					</td>
					<td>${orderVO.oseq}</td>
					<td>${orderVO.pname}</td>
					<td>
						<fmt:formatNumber value="${orderVO.price1 * orderVO.quantity}" type="number" />
					</td>
					<td>
						<a href="${contextPath}/order/order_detail?oseq=${orderVO.oseq}">
							조회 
						</a>
					</td>
				</tr>
			</c:forEach>
		</table>

		<div class="clear"></div>
		
		<div id="buttons" style="float: right">
			<input type="button" class="btn btn-secondary" value="쇼핑 계속하기" 
				   class="cancel" onclick="location.href='${contextPath}/'">
		</div>
	</form>
		
</article>

<!-- footer -->
<%@ include file="../footer.jsp" %>