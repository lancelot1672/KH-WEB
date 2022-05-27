<%@page import="board.dto.Attachment"%>
<%@page import="java.util.List"%>
<%@page import="board.dto.BoardExt"%>
<%@ page import="board.dto.BoardComment" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%
	BoardExt board = (BoardExt) request.getAttribute("board");
	List<BoardComment> comments = (List<BoardComment>) request.getAttribute("comments");


%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/board.css" />
<section id="board-container">
	<h2>게시판</h2>
	<table id="tbl-board-view">
		<tr>
			<th>글번호</th>
			<td><%= board.getNo() %></td>
		</tr>
		<tr>
			<th>제 목</th>
			<td><%= board.getTitle() %></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><%= board.getMemberId() %></td>
		</tr>
		<tr>
			<th>조회수</th>
			<td><%= board.getReadCount() %></td>
		</tr>
		<% 
			List<Attachment> attachments = board.getAttachments();
			if(attachments != null && !attachments.isEmpty()){
				for(Attachment attach : attachments){
		%>
		<tr>
			<th>첨부파일</th>
			<td>
				<%-- 첨부파일이 있을경우만, 이미지와 함께 original파일명 표시 --%>
				<img alt="첨부파일" src="<%=request.getContextPath() %>/images/file.png" width=16px>
				<a href="<%= request.getContextPath() %>/board/fileDownload?no=<%= attach.getNo() %>"><%= attach.getOriginalFilename() %></a>
			</td>
		</tr>
		<%
				}
			}
		%>
		<tr>
			<th>내 용</th>
			<td><%= board.getContent() %></td>
		</tr>
		<% if(loginMember != null 
			&& (loginMember.getMemberId().equals(board.getMemberId()) 
					|| loginMember.getMemberRole() == MemberRole.A)){ %>
		<tr>
			<%-- 작성자와 관리자만 마지막행 수정/삭제버튼이 보일수 있게 할 것 --%>
			<th colspan="2">
				<input type="button" value="수정하기" onclick="updateBoard()">
				<input type="button" value="삭제하기" onclick="deleteBoard()">
			</th>
		</tr>
		<script>
		/**
		 * POST /board/boardDelete
		 * - no전송
		 * - 저장된 파일 삭제 : java.io.File 
		 */
		const deleteBoard = () => {
			
		};	
		</script>
		<% } %>
	</table>
	<hr style="margin-top: 30px;" />

	<div class="comment-container">
		<div class="comment-editor">
			<form
					action="<%=request.getContextPath()%>/board/boardCommentEnroll" method="post" name="boardCommentFrm">
				<input type="hidden" name="boardNo" value="<%= board.getNo() %>" />
				<input type="hidden" name="memberId" value="<%= loginMember != null ? loginMember.getMemberId() : "" %>" />
				<input type="hidden" name="commentLevel" value="1" />
				<input type="hidden" name="commentRef" value="0" />
				<textarea name="content" cols="60" rows="3"></textarea>
				<button type="submit" id="btn-comment-enroll1">등록</button>
			</form>
		</div>
		<!--table#tbl-comment-->
		<% if(comments != null && !comments.isEmpty()) {%>
		<table id="tbl-comment">
			<tbody>
			<%
				for(BoardComment bc : comments) {
					boolean canDelete = loginMember != null
							&& (loginMember.getMemberId().equals(bc.getMemberId()) || loginMember.getMemberId().equals("admin"));
					if(bc.getCommentLevel() == 1){

			%>
				<tr class="Level1">
					<td>
						<sub class="comment-writer"><%= bc.getMemberId() %></sub>
						<sub class="comment-date"><%= bc.getRegDate()%></sub>
						<br/>
						<%= bc.getContent() %>
					</td>
					<td>
						<button class="btn-reply" value="<%= bc.getNo() %>">답글</button>
						<% if(canDelete){%>
							<button class="btn-delete" value="<%= bc.getNo() %>">삭제</button>
						<% } %>
					</td>
				</tr>
			<% }else {%>
				<tr class="level2">
					<td>
						<sub class="comment-writer"><%= bc.getMemberId() %></sub>
						<sub class="comment-date"><%= bc.getRegDate()%></sub>
						<br/>
						<%= bc.getContent() %>
					</td>
					<td>
						<% if(canDelete){%>
							<button class="btn-delete" value="<%= bc.getNo() %>">삭제</button>
						<% } %>
					</td>
				</tr>
			<%
					}
				}
			%>
			</tbody>
		</table>
		<% } %>
	</div>
</section>
<%-- @실습문제 .btn-delete 클릭핸들러를 통해 boardCommentDelFrm 동적으로 전송 --%>
<script>
	document.querySelectorAll(".btn-delete").forEach((button) =>{
		button.onclick = (e) =>{
			const {value : commentNo} = e.target;

			const frm = document.createElement("form");
			frm.name = "commentDeleteFrm";
			frm.action = "<%=request.getContextPath()%>/board/boardCommentDelete";
			frm.method = "POST";
			// frm.onsubmit = ;

			// 게시글 번호
			const inputBoardNo = document.createElement("input");
			inputBoardNo.type = "hidden";
			inputBoardNo.value = "<%= board.getNo() %>";

			// 댓글 번호
			const inputCommentNo = document.createElement("input");
			inputCommentNo.type = "hidden";
			inputCommentNo.value = commentNo;

			// form에 탑재
			frm.append(inputBoardNo);
			frm.append(inputCommentNo);

			if(confirm('정말 삭제하시겠습니까??')){
				frm.submit();
			}
		}
	});
	document.querySelectorAll(".btn-reply").forEach((button) =>{
		button.onclick = (e) =>{
			if(<%= loginMember == null %>){
				loginAlert();
				return;
			}

			const {value : commentRef} = e.target;
			console.log(commentRef);

			// tr > td > form
			const tr = document.createElement("tr");
			const td = document.createElement("td");
			td.colSpan = "2";
			td.style.textAlign = "left";

			const frm = document.createElement("form");
			frm.name = "boardCommentFrm";
			frm.action = "<%=request.getContextPath()%>/board/boardCommentEnroll";
			frm.method = "POST";
			frm.onsubmit = commentSubmitHandler;	//동적생성한 요소는 핸들러바인딩도 새로 해야한다.

			const inputBoardNo = document.createElement("input");
			inputBoardNo.type = "hidden";
			inputBoardNo.name = "boardNo";
			inputBoardNo.value = "<%= board.getNo() %>";

			const inputMemberId = document.createElement("input");
			inputMemberId.type = "hidden";
			inputMemberId.name = "memberId";
			inputMemberId.value = "<%= loginMember != null ? loginMember.getMemberId() : "" %>";

			const inputCommentLevel = document.createElement("input");
			inputCommentLevel.type = "hidden";
			inputCommentLevel.name = "commentLevel";
			inputCommentLevel.value = "2";

			const inputCommentRef = document.createElement("input");
			inputCommentRef.type = "hidden";
			inputCommentRef.name = "commentRef";
			inputCommentRef.value = commentRef;

			const textarea = document.createElement("textarea");
			textarea.name = "content";
			textarea.cols = "60";
			textarea.rows = "1";

			const button = document.createElement("button");
			button.className = "btn-comment-enroll2";
			button.innerText = "등록";

			frm.append(inputBoardNo);
			frm.append(inputMemberId);
			frm.append(inputCommentLevel);
			frm.append(inputCommentRef);
			frm.append(textarea);
			frm.append(button);
			td.append(frm);
			tr.append(td);

			console.log(tr);

			// 1. 부모 요소 tbody
			const parent = e.target.parentElement.parentElement.parentElement;
			console.log(parent);	//td - tr - tbody

			// 2. 기준 요소 다음 tr 태그
			const ref = e.target.parentElement.parentElement.nextElementSibling;

			// 생성된 tr 추가
			parent.insertBefore(tr, ref);
			// 이벤트핸들링은 1회만 허용.
			e.target.onclick = null;
		};
	})
	document.querySelector("textarea[name=content]").onfucus = (e) =>{
		if(<%= loginMember == null %>){
			loginAlert();
		}
	};

	const commentSubmitHandler = (e) =>{
		if(<%= loginMember == null %>){
			loginAlert();
			return false;
		}
		const contentVal = e.target.content.value.trim();
		if(!/^(.|\n)+$/.test(contentVal)){
			alert("댓글 내용을 작성해주세요.");
			e.target.content.focus();
			return false;
		}
	}
	document.boardCommentFrm.onsubmit = commentSubmitHandler;

	const loginAlert = () =>{
		alert("로그인 후 이용할 수 있습니다.");
		document.querySelector("#memberId").focus();
	}
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
