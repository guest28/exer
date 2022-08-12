<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>  
<%@ include file="sub_img.jsp"%> 
<%@ include file="sub_menu.html" %>

<!-- JAVASCRIPT -->
<%-- 
<script type="text/javascript" src="<c:url value="/js/mypage.js" />" charset="UTF-8"></script> 
--%>

<!-- JAVASCRIPT : 자바스크립트 외장화가 안 됨... -->
<script>
//cart
function go_cart() {
	if(document.formm.quantity.value == "") {
		
		alert("수량을 입력하여 주세요.");
		
		document.formm.quantity.focus();
		
	} else {
		
	    document.formm.action = "/project/cart/cart_insert";
	    document.formm.submit();
	}
}

//oder
function go_order() {
	
	document.formm.action = "/project/order/order_list";
	document.formm.submit();
}
</script>

<!-- 상품 이미지 확대 시(모달) -->
<div class="modal" id="myModal">
	<div class="modal-dialog modal-xl mx-auto d-block">
		<div class="modal-content">
		
			<!-- Modal Header -->
   			<div class="modal-header">
   				<h4 class="modal-title">${productVO.name}</h4>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>

			<!-- Modal body -->
			<div class="modal-body">
				<img src="${contextPath}/product_images/${productVO.image}" />
			</div>
		</div>
	</div>
</div>
     
<article>
	<h1>Item</h1>
	
    <div id="itemdetail" >
    	<form method="post" name="formm">    
        	<fieldset>
        		<legend>Item detail Info</legend>
        		
        		<!-- 상품 이미지(클릭 시 확대) -->       		
				<div class="container mt-3">
					<a href="#" data-bs-toggle="modal" data-bs-target="#myModal">						
						<img src="${contextPath}/product_images/${productVO.image}" />
					</a>
					<p>클릭 시 확대</p>
				</div>
            	
            	<br><br>  
            	 
            	<div>            	     
					<div class="row mb-2">
						<h4>${productVO.name}</h4>
					</div>
					<div class="row mb-2">
						<div class="col-3">   
							<label>가 격 : </label>
						</div>					
						<div class="col-9 mt-1">	 
							<p><fmt:formatNumber maxFractionDigits="3" value="${productVO.price1}" /> 원</p>  
						</div>
					</div>
					
					<div class="row mb-2">
						<div class="col-3">
							<label class="form-label">수 량 : </label>
						</div>					
						<div class="col-6">	 
							<input  type="number" class="form-control" name="quantity" size="2" value="1" min="1"><br>
						</div>
					</div>
					
					<input  type="hidden" name="pseq" value="${productVO.pseq}"><br>
				</div>
			</fieldset>
			
			<div id="product_content" class="row p-2 m-2">
				${productVO.content}
			</div>
			
			<div class="clear"></div>
			
			<div id="buttons" class="row col-10 float-end">
				<div class="col-1"></div>
				<div class="col-3">
					<input type="button" class="btn btn-outline-secondary"			 onclick="go_cart()" value="장바구니에 담기" >
				</div>
				<div class="col-3">			
					<input type="button" class="btn btn-outline-secondary ps-3 ms-4" onclick="go_order()" value="즉시 구매">
				</div>
				<div class="col-2">		
					<input type="reset"  class="btn btn-outline-secondary ps-3" 						  value="취소">
				</div>
          		<div class="col-3"></div>
			</div>
		</form> 
	</div>
</article>
 
<%@ include file="../footer.jsp" %>    