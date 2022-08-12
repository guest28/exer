<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- HEADER -->
<%@ include file="member_head.jsp" %>

<!-- MODAL -->
<%@ include file="member_popup.jsp" %>

<!-- JAVA -->
<%@ page import="java.util.*" %>
<%@ page import="com.javateam.project.domain.*" %>

<!-- JAVASCRIPT -->
<script type="text/javascript" src=<c:url value="/js/admin_member_list.js" /> charset="UTF-8"></script>
<script type="text/javascript" src="<c:url value="/js/member_postcode.js" />" charset="UTF-8"></script>

<!-- daum 우편번호 서비스 -->
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>

<!-- CSS -->
<link href="<c:url value="/css/admin_member_list.css" />" rel="stylesheet">
	
	
<!-- 자바 : 언어 간 충돌이 생겨 자바를 끌어다 씀, ajax는 좀 무겁기 때문 -->
<script>
//java List => javascript Array
function getRoleFromMembers(num) {	//num = javascript의 i
	
	var i = 0;
	var arr = new Array();		//javascript 배열
	
	<%
		List<MemberRoleVO> memberList = (List<MemberRoleVO>)request.getAttribute("members");
	
		for (int i = 0; i < memberList.size(); i++) {
			
			MemberRoleVO roleVO = memberList.get(i);
	%>
			arr.push("<%= roleVO.getRole() %>");		//javascript 배열에 요소(바뀐 role) 추가
	<%
		}
	%>
	
	// java List => javascript Array 변환 완료
	
	console.log("arr : " + arr[num]);
	
	return arr[num];
}

//java List => javascript Array
//기존 이메일/연락처/주소정보(우편번호, 기본주소, 상세주소) => JS 배열 (ex. 최대 10개)
function getDefaultMemberFldsFromMembers(fld) {
	
	var i = 0;
	var arr = new Array(); // javascript 배열
	
	<%
	    List<MemberRoleVO> memberList2 = (List<MemberRoleVO>)request.getAttribute("members");
	
		for(int i = 0; i < memberList2.size(); i++) {
			
			MemberRoleVO roleVO = memberList.get(i);
	%>
	 		// javascript 배열에 요소 추가
	 		
			if (fld == '이메일') {
				arr.push("<%=roleVO.getEmail() %>");
			} else if (fld == '연락처') {
				arr.push("<%=roleVO.getPhone() %>");
			} else if (fld == '우편번호') {
				arr.push("<%=roleVO.getZipNum() %>");
			} else if (fld == '기본주소') {
				arr.push("<%=roleVO.getAddress1() %>");
			} else if (fld == '상세주소') {
				arr.push("<%=roleVO.getAddress2() %>");
			}
	<%
		}
	%>
	
	// java List => javascript Array 변환 완료
	console.log("arr : " + arr);
	
	return arr;
}

</script>

<%-- 
<!-- 페이지 관련 인자들  -->
<div>
	<table class="table table-striped w-100">
		 <thead>
	      <tr>
	        <th>인자</th>
	        <td>현재 페이지</td>
	        <td>처음 페이지</td>
	        <td>마지막 페이지</td>
	        <td>페이지당 게시글 수</td>
	        <td>총 페이지수</td>
	        <td>검색 VO</td>
	        <td>검색어(원본)</td>
			<td>검색어(수정)</td>
	    </thead>
	    <tbody>
	      <tr>
	        <th>값</th>
	        <td>${pageVO.currPage}</td>
	        <td>${pageVO.startPage}</td>
	        <td>${pageVO.endPage}</td>
	        <td>${pageVO.limit}</td>
	        <td>${pageVO.maxPage}</td>
	        <td>${searchVO}</td>
	        <td>${searchVO.searchWord}</td>
			<td>${fn:replace(searchVO.searchWord, '%', '')}</td>
	      </tr>
	    </tbody>
	</table>
</div>
--%>	
		
<article>

	<!-- 회원 정보 리스트 -->
	<div style="width:1300px; overflow-x:auto" class="pt-5"> 
		
		<div id="member_grid_title" style="width:3100px">
		
			<div class="member_list_grid_title" style="width:100px">&nbsp;</div>
			<div class="member_list_grid_title" style="width:60px">No.</div>
			<div class="member_list_grid_title" style="width:120px">ID</div>
			<div class="member_list_grid_title" style="width:100px">이름</div>
			<div class="member_list_grid_title" style="width:400px">이-메일</div>
			<div class="member_list_grid_title" style="width:100px">&nbsp;</div>
			<div class="member_list_grid_title" style="width:200px">연락처</div>
			<div class="member_list_grid_title" style="width:100px">&nbsp;</div>
			<div class="member_list_grid_title" style="width:120px">우편번호</div>
			<div class="member_list_grid_title" style="width:600px">기본 주소</div>
			<div class="member_list_grid_title" style="width:100px">&nbsp;</div>
			<div class="member_list_grid_title" style="width:400px">상세 주소</div>
			<div class="member_list_grid_title" style="width:100px">&nbsp;</div>
			<div class="member_list_grid_title" style="width:100px">상태</div>
			<div class="member_list_grid_title" style="width:250px">가입일</div>
			<div class="member_list_grid_title" style="width:100px">등급</div>
			<div class="member_list_grid_title" style="width:100px">회원 비활성</div>
		</div>
			
		<br><hr style="width:3100px"><br>
			
		<!-- 본문  -->
		<div id="grid_body" style="width:3100px;">
		
			<c:forEach var="member" items="${members}" varStatus="st">
			
				<div style="width:3100px;height:70px;">
				
					<input type="hidden" id="count_${member.id}" value="${st.index}" />
				
			   		<!-- checkbox -->
					<div style="float:left;width:100px;height:70px;">
						<div class="custom-control custom-checkbox">
							<input type="checkbox" class="custom-control-input" id="ckbox_${member.id}">
							<label class="custom-control-label" for="ckbox_${member.id}">
							
								<!-- 사람 모양 아이콘 -->
								<span class="material-icons">
									perm_identity
								</span>
								
							</label>
						</div>							
					</div>
					
					<!-- 글 번호(페이지별) -->
					<div style="float:left;width:60px;height:70px;">
						${pageVO.limit * (pageVO.currPage-1) + st.count}
					</div>
				
					<!-- ID -->
					<div style="float:left;width:120px;height:70px;">
						${member.id}
					</div>
					
					<!-- 이름 -->
					<div style="float:left;width:100px;height:70px;">
						${member.name}
					</div>
					
					<!-- 이메일 -->
					<div style="float:left;width:400px;height:70px;">
						<input type="text" onclick="alert('click text')" class="form-control"
			    		       id="email_${member.id}" value="${member.email}" />
					</div>
					
					<div style="float:left;width:100px;height:70px;">
						<a href="#" data-toggle="tooltip" id="email_update_btn_${member.id}" class="btn" title="이메일 수정">
	    					<span class="material-icons">
								check_circle_outline
							</span>
						</a>
					</div>
					
					<!-- 연락처 -->
					<div style="float:left;width:200px;height:70px;">
						<input type="text" class="form-control" id="phone_${member.id}" value="${member.phone}" />
					</div>
					
					<div style="float:left;width:100px;height:70px;">
						<a href="#" data-toggle="tooltip" id="phone_update_btn_${member.id}" class="btn" title="연락처 수정">
	    					<span class="material-icons">
								check_circle_outline
							</span>
						</a>
					</div>
					
					<!-- 우편번호 -->
					<div style="float:left;width:120px;height:70px;">
						<div id="zipNum_${member.id}" class="p-2">
							${empty member.zipNum ? "&nbsp;" : member.zipNum}
						</div>	   
							   
					</div>
					
					<!-- 기본 주소 -->
					<div style="float:left;width:600px;height:70px;">
						<div id="address1_${member.id}" class="p-2">
							${empty member.address1 ? "&nbsp;" : member.address1}
						</div>
					</div>	
					
					<div style="float:left;width:100px;height:70px;">
						<a href="#" data-toggle="tooltip" id="zip_address1_update_btn_${member.id}" class="btn" title="우편번호 및 기본 주소 수정">
	    					<span class="material-icons">
								check_circle_outline
							</span>
						</a>
					</div>
					
					<!-- 상세 주소 -->
					<div style="float:left;width:400px;height:70px;">
						<div id="address2_${member.id}" class="p-2" contenteditable="false"
						     onclick="this.setAttribute('contenteditable','true');">
							${member.address2}
						</div>  
					</div>	
					
					<div style="float:left;width:100px;height:70px;">
						<a href="#" data-toggle="tooltip" id="address2_update_btn_${member.id}" class="btn" title='상세 주소 수정'>
	    					<span class="material-icons">
								check_circle_outline
							</span>
						</a>
					</div>
					
					<!-- 상태  -->
					<div style="float:left;width:100px;height:70px;">
						${member.useyn == 'y' ? "회원" : "탈퇴"}
					</div>
					
					<!-- 가입일 -->
					<div style="float:left;width:250px;height:70px;">
						<fmt:formatDate value="${member.indate}" pattern="yyyy년  M월 d일 HH:mm:ss"/>
					</div>

					<!-- 등급 -->	
					<div style="float:left;width:100px;height:70px;">
						<select id="role${st.index}" title="${member.id}" name="role${st.index}" class="form-control">
							<option value="user">일반회원</option>
							<option value="admin">관리자</option>
							<option value="guest">게스트</option>
							<option selected>${member.role}</option>
						</select>	
					</div>	
					
					<!-- 회원 비활성 -->
					<div style="float:left;width:100px;height:70px;" class="ml-2">
						<div class="btn-group btn-group-sm mt-1">
						  <button type="button" id="member_inactive_btn_${member.id}" class="btn btn-light py-1">비활성</button>
						</div>
					</div>	
				</div>
			</c:forEach>
		</div> 
	</div>

	<!-- PAGING -->
	<%@ include file="member_paging.jsp" %>
	
	<!-- 검색 -->
	<form action="${contextPath}/admin/member/search_member_list">
		<div class="w-100 row mt-4 mb-3">
			<div class="col-3"></div>
			
			<div class="col-2 justify-content-center">
				<select name="search_field" class="form-select">
					<option value="id">아이디</option>
			  		<option value="name" selected>이름</option>
					<option value="address1">기본주소</option>				  
					<option value="address2">상세주소</option>				  
				</select>
			</div>
			
			<div class="col-3">
				<input type="text" name="search_word" class="form-control">
			</div>
			<div class="col-1">
				<button class="btn btn-secondary pt-2 pb-0">
				<span class="material-icons">search</span>
				</button>
			</div>
			<div class="col-3">
				<a class="btn btn-secondary" href="${contextPath}/admin/member/member_list">전체 회원 리스트</a>
			</div>
			
			<!-- hidden field -->
			<input type="hidden" name="page" value="1" />
			
			<input type="hidden" name="limit" value="${empty searchVO.limit ? pageVO.limit : searchVO.limit}" />
		</div>
	</form>
</article>

<br>
<br>

<%@ include file="../../footer.jsp" %>