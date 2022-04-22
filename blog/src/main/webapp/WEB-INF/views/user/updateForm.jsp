<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ include file="../layout/header.jsp"%>
<div class="container">

	<form>
		<input type="hidden" id="id" value="${principal.user.id}" />
		<div class="form-group">
			<label for="username">User name : </label> <input type="text" value="${principal.user.username}" class="form-control" placeholder="Enter Username" id="username" readonly>
		</div>

		<!-- oauth 값이 비어 있으면 비번 수정 가능 -->
		<c:if test="${empty principal.user.oauth }">
			<div class="form-group">
				<label for="password">Password : </label> <input type="password" class="form-control" placeholder="Enter password" id="password">
			</div>
		</c:if>
		<div class="form-group">
			<label for="email">Email :</label> <input type="email" value="${principal.user.email}" class="form-control" placeholder="Enter email" id="email" readonly>
		</div>





	</form>

	<button id="btn-update" class="btn btn-primary">회원정보 수정 완료</button>


</div>

<script src="/js/user.js"></script>

<%@ include file="../layout/footer.jsp"%>



