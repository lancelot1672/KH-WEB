<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%
	int totalPage = (int) request.getAttribute("totalPage");
%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/photo.css" />
<section id="photo-wrapper">
	<h2>사진게시판 </h2>
	<%
		if(loginMember != null){
	%>
	<input type="button" value="글쓰기" id="btn-add"
		onclick="location.href='<%=request.getContextPath()%>/photo/photoEnroll';" />
	<%
		}
	%>
	<div id="photo-container">
		<div class="polaroid">
			<img src="" alt="" />
			<p class="info">
				<span class="writer"></span>
				<span class="photoDate"></span>
			</p>
			<p class="caption"></p>
		</div>
	</div>
	<hr />
	<div id='btn-more-container'>
		<button id="btn-more" value="" >더보기(<span id="cPage">1</span>/<span id="totalPage"><%=totalPage%></span>)</button>
	</div>
</section>
<script>
	document.querySelector("#btn-more").onclick = () => {
		const cPageVal = Number(document.querySelector("#cPage").innerText) + 1;
		getPage(cPageVal);
	};
	const getPage = (cPage) =>{
		$.ajax({
			url : "<%=request.getContextPath()%>/photo/morePage",
			data : {cPage : cPage},
			success(response){
				console.log(response);
				const container = document.querySelector("#photo-container");

				response.forEach((photo)=>{
					//구조 분해 할당
					const {no, memberId, content, regDate, renamedFileName} = photo;
					console.log(no,memberId,content,regDate,renamedFileName);

					// image 높이 동적 계산
					const img = new Image();
					img.src = `<%= request.getContextPath()%>/upload/photo/\${renamedFileName}`;
					const height = Math.round(img.height * 300 / img.width * 100) / 100;
					console.log(img.src, img.height, img.width);

					// image onload 된 이후에 처리
					img.onload = () =>{
						// html 만들기
						const html = `
							<div class="polaroid">
								<img src="\${img.src}" alt="" height="\${height}px"/>
								<p class="info">
									<span class="writer">\${memberId}</span>
									<span class="photoDate">\${regDate}</span>
								</p>
								<p class="caption">\${content}</p>
							</div>
							`;
						container.insertAdjacentHTML('beforeend',html);	//자식요소로 맨 뒤에 추가
					};
				});
			},
			error : console.log,
			complete(){
				// 더보기 되면 cPage 변환 ( +1 )
				document.querySelector("#cPage").innerText = cPage;

				// 총 페이지와 같으면(마지막 페이지라면) disabled
				if(cPage === <%=totalPage%>){
					const btn = document.querySelector("#btn-more");
					btn.disabled = "disabled";
					btn.style.cursor = "not-allowed";
				}
			}
		});
	}

	window.addEventListener('load', ()=>{
		// 페이지 로딩시
		// 첫 페이지 요청
		getPage(1);

	});


</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
