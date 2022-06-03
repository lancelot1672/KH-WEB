<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%
	int totalPage = (int) request.getAttribute("totalPage");
%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/photo.css" />
<section id="photo-wrapper">
	<h2>사진게시판 </h2>
	<div id="photo-container"></div>
	<hr />
	<div id='btn-more-container'>
		<button id="btn-more" value="" >더보기(<span id="cPage">1</span>/<span id="totalPage"><%=totalPage%></span>)</button>
	</div>
</section>
<script>
	const getPage = (cPage) =>{
		$.ajax({
			url : "<%=request.getContextPath()%>/photo/morePage",
			data : {cPage : cPage},
			success(response){
				console.log(response);
			},
			error : console.log
		});
	}

	window.addEventListener('load', ()=>{
		// 페이지 로딩시
		// 첫 페이지 요청
		getPage(1);

	});


</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
