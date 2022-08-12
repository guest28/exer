<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- HEADER -->
<%@ include file="../header.jsp" %>

<%@ include file="sub_img.jsp"%>
<%@ include file="../sub_menu_member.jsp"%>

<!-- JAVASCRIPT : 외장화 실패 -->
<%-- 
<script type="text/javascript" src="<c:url value="/js/mypage.js" />" charset="UTF-8"></script> 
--%>

<script>

//장바구니 : 삭제
function go_cart_delete() {
	
	var count = 0;

	if(document.formm.cseq.length == undefined) {
	  
		if(document.formm.cseq.checked == true) {
    	
			count++;
		}
	}

	for(var i = 0; i < document.formm.cseq.length; i++) {
	  
		if(document.formm.cseq[i].checked == true) {
    	
			count++;
			
		}
	}
	
	if (count == 0) {
	  
		alert("삭제할 항목을 선택하시오.");

	} else {
	  
		document.formm.action = "/project/cart/cart_delete";
		document.formm.submit();
	}
}

//장바구니 : 수정
function go_cart_update() {
	
	var count = 0;
	
	if(document.formm.cseq.length == undefined) {
		
		if(document.formm.cseq.checked == true) {
			
			count++;
		}
	}
	
	for(var i = 0; i < document.formm.cseq.length; i++) {
		
		if(document.formm.cseq[i].checked == true) {
		
			count++;
		
			console.log("" + count);
		}
	}
	
	if(count == 0) {
	
		alert("수정할 항목을 선택하시오.");
		
	} else {
		
		document.formm.action = "/project/cart/cart_update";
		document.formm.submit();
	}
}

//주문하기
function go_order_insert() {
	
	var count = 0;

	if(document.formm.cseq.length == undefined) {
	  
		if(document.formm.cseq.checked == true) {
    	
			count++;
		}
	}

	for(var i = 0; i < document.formm.cseq.length; i++) {
	  
		if (document.formm.cseq[i].checked == true) {
    	
			count++;
		}
	}
	
	if (count == 0) {
	  
		alert("주문할 항목을 선택하시오.");

	} else {
	  
		document.formm.action = "/project/order/order_insert";
		document.formm.submit();
	}
}
</script>

<article>
	
	<h2>Cart List</h2>
	
	<form name="formm" id="cartList" method="post">
		
		<c:choose>
		
			<c:when test="${cartList.size() == 0}">
				<h5 style="padding-top:30px; text-align: center;">카트가 비었습니다.</h5>
			</c:when>
			
			<c:otherwise>
			
				<table id="cartList" class="table container">
					<tr>
						<th>No.</th>
						<th>상품명</th>
						<th>수량</th>
						<th>가격(원)</th>
						<th>주문일</th>
						<th>선택</th>
					</tr>

					<c:forEach items="${cartList}" var="cartVO" varStatus="st">
					
						<!-- 상품 시퀀스(히든 처리) -->
						<input type="hidden" id="pseq_${cartVO.cseq}" name="pseq" value="${cartVO.pseq}" />
						
						<tr>
							<!-- 상품 번호(pseq) -->
							<td class="pt-3 pb-0" style="width:70px">
								${cartVO.pseq}
							</td>
							
							<!-- 상품명 -->
							<td class="pt-3 pb-0">
								<a href="${contextPath}/product/product_detail?pseq=${cartVO.pseq}">
									${cartVO.pname}
								</a>
							</td>
							
							<!-- 수량 -->
							<td style="width:80px" class="pt-2 pb-0">
								<!-- 주문 수량 변동 조정토록 조치 -->
								<div class="form-group">
									<input type="number" id="quantity_${cartVO.cseq}" name="quantity"
									       class="form-control form-control-sm" value="${cartVO.quantity}" min="1" 
									       style="text-align:right" />
								</div>       
							</td>
							
							<!-- 가격 -->
							<td style="text-align:right" class="pt-3 pb-0">
								<fmt:formatNumber value="${cartVO.price1 * cartVO.quantity}" type="number" />
							</td>
							
							<!-- 주문일 -->
							<td class="pt-3 pb-0">
								<fmt:formatDate value="${cartVO.indate}" type="date" />
							</td>
							
							<!-- 체크박스 -->
							<td class="pt-3 pb-0" style="width:100px">
								<div class="custom-control custom-checkbox mb-3">
									<input type="checkbox" id="cseq_${cartVO.cseq}" name="cseq"
									       class="custom-control-input" value="${cartVO.cseq}" />
									         
									<input type="hidden" name="cseq_idx" value="${cartVO.cseq}_${st.index}" />     
								</div>	       
							</td>
						</tr>
					</c:forEach>
					
					<!-- 버튼 -->
					<tr>
						<td></td>
						<td></td>
						<td>
							<a href="#" class="btn btn-outline-secondary my-2" onclick="go_cart_update()">
								수정
							</a>
						</td>
						<td></td>
						<td></td>
						<td>
							<a href="#" class="btn btn-outline-secondary my-2" onclick="go_cart_delete()">
								삭제
							</a>
						</td>
					</tr>
					
					<!-- 총액/주문하기 -->
					<tr>
						<th class="pt-4">
							<h5>총액</h5>
						</th>
						<th class="pt-4">
							<h5><fmt:formatNumber value="${totalPrice}" currencySymbol="﻿￦" type="currency" /></h5>
						</th>
						
						<th></th>
						<th></th>
						<th></th>
						<th>
							<a href="#" class="btn btn-secondary my-2" onclick="go_order_insert()">
								주문
							</a>
							<%-- 
							<c:if test="${cartList.size() != 0}">
								<input type="button" class="btn btn-secondary" value="주문하기" class="submit"
									   onclick="go_order_insert()">
							</c:if>
							 --%>
						</th>
						
						
					</tr>
				</table>
			</c:otherwise>
		</c:choose>

		<div class="clear"></div>

		<!-- 버튼 -->
		<div id="buttons" class="row col-10 float-end">
		
		 	<div class="col-3"></div>
          	<div class="col-4 pr-1">
				<input type="button" class="btn btn-outline-secondary" value="쇼핑 계속하기" class="cancel"
					   onclick="location.href='${contextPath}/'">
			</div>
         	<div class="col-2">
			</div>
			<div class="col-1"></div>	
		</div>
	</form>

</article>

<!-- footer -->
<%@ include file="../footer.jsp" %>