<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- <%@ include file="order_head.jsp" %> --%>

<article>

	<!-- 검색 -->
	<form name="frm" class="form-inline" method="post" action="${contextPath}/admin/order/order_list">
	
		<!-- 검색 -->
		<div class="row my-5">
		
			<div class="col-1">
			</div>
			<div class="col-2">
			</div>
			<div class="col-1">
				<b>주문자 이름</b>
			</div>
			<div class="col-3">
					<input type="text" class="form-control" name="key">
			</div>
			<div class="col-1">
				<button id="search_product_btn" class="btn btn-secondary pt-2 pb-0">
					<span class="material-icons">
						search
					</span>
				</button>
			</div>
			<div class="col-5">
			</div>
		</div>	

	
		<!-- 주문 정보 리스트 -->
		<div style="width:1300px; overflow-x:auto" class="row pt-5"> 
			
			<div id="member_grid_title" style="width:2070px" class="row pt-5">
			
				<div class="member_list_grid_title" style="width:200px">no. 처리 현황</div>
				<div class="member_list_grid_title" style="width:120px">주문자</div>
				<div class="member_list_grid_title" style="width:400px">상품명</div>
				<div class="member_list_grid_title" style="width:80px">수량</div>
				<div class="member_list_grid_title" style="width:120px">우편번호</div>
				<div class="member_list_grid_title" style="width:800px">배송지</div>
				<div class="member_list_grid_title" style="width:200px">연락처</div>
				<div class="member_list_grid_title" style="width:150px">주문일</div>
			</div>
				
			<br><hr style="width:2070px"><br>
				
			<!-- 본문 -->
			<div id="grid_body" style="width:2070px;">
			
				<c:forEach items="${orderList}" var="orderVO">
				
					<div style="width:2070px;height:70px;">
			
						<!-- No. / 처리 현황 -->
						<div style="float:left;width:200;height:70px;">
							<c:choose>
								<c:when test="${orderVO.result=='1'}">
								
									<div class="row" style="width:180px;">
									
										<div class="col-3 mt-0">
											<span style="font-weight: bold; color: blue">${orderVO.odseq}</span>
										</div>
										
										<div class="col-8">
											<div class="custom-control custom-checkbox">
												<input type="checkbox" id="result_${orderVO.odseq}" name="result"
													   class="custom-control-input" value="${orderVO.odseq}">
												<label class="custom-control-label mt-0" for="result_${orderVO.odseq}">미처리</label>       
											</div>
										</div>
									</div>
								</c:when>
								
								<c:otherwise>
									<div class="custom-control custom-checkbox mb-3" style="width:180px;">
									
										<input type="checkbox" class="custom-control-input" checked="checked" disabled="disabled">
										
										<label class="custom-control-label">${orderVO.odseq} 처리완료</label>       
									</div>
																	
								</c:otherwise>
							</c:choose>	
						</div>
						
						<!-- 주문자 -->
						<div style="float:left;width:120px;height:70px;">
							${orderVO.mname}
						</div>
					
						<!-- 상품명 -->
						<div style="float:left;width:400px;height:70px;">
							${orderVO.pname}
						</div>
						
						<!-- 수량 -->
						<div style="float:left;width:80px;height:70px;">
							${orderVO.quantity}
						</div>
						
						<!-- 우편번호 -->
						<div style="float:left;width:120px;height:70px;">
							${orderVO.zipNum}
						</div>
						
						<!-- 배송지 -->
						<div style="float:left;width:800px;height:70px;">
							${orderVO.address1}&nbsp;
							${orderVO.address2} 
						</div>
						
						<!-- 연락처 -->
						<div style="float:left;width:200px;height:70px;">
							${orderVO.phone}
						</div>
						
						<!-- 주문일 -->
						<div style="float:left;width:150px;height:70px;">
							<fmt:formatDate value="${orderVO.indate}" />
						</div>	
					</div>
				</c:forEach>
			</div> 
		</div>
		
		<input type="button" class="btn btn-secondary" value="주문처리(입금확인)" onClick="go_order_save()">
		
		<br>
	</form>
	
	
</article>

<br>
<br>

<%-- <%@ include file="../../footer.jsp" %> --%>