<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<style>
 a {text-decoration: none}
 @import url("https://fonts.googleapis.com/css?family=Roboto:400,300");

body {
	color: #2c3e50;
	font-family: 'Roboto', sans-serif;
	font-weight: 400;
}

h1 {
	text-align: center;
	font-size: 2.5rem;
	font-weight: 300;
}

.pagination-container {
	margin: 100px auto;
	text-align: center;
}

.pagination {
	position: relative;
}

.pagination a {
	position: relative;
	display: inline-block;
	color: #2c3e50;
	text-decoration: none;
	font-size: 1.2rem;
	padding: 8px 16px 10px;
}

.pagination a:before {
	z-index: -1;
	position: absolute;
	height: 100%;
	width: 100%;
	content: "";
	top: 0;
	left: 0;
	background-color: #2c3e50;
	border-radius: 24px;
	-webkit-transform: scale(0);
	transform: scale(0);
	transition: all 0.2s;
}

.pagination a:hover, .pagination a .pagination-active {
	color: #fff;
}

.pagination a:hover:before, .pagination a .pagination-active:before {
	-webkit-transform: scale(1);
	transform: scale(1);
}

.pagination .pagination-active {
	color: #fff;
}

.pagination .pagination-active:before {
	-webkit-transform: scale(1);
	transform: scale(1);
}

.pagination-newer {
	margin-right: 50px;
}

.pagination-older {
	margin-left: 50px;
}

img {
	width: 200px;
	height: 350px
}


img{width:200px; height:350px}

th, td{text-align: center}
</style>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="container">
<table class="table table-hover">
<caption><h4 style="text-align: center;">자유게시판</h4></caption>

  <thead class="table-light">
    <tr>
      <th scope="col" style="text-align: center">번호</th>
      <th scope="col" style="text-align: center">제목</th>
      <th scope="col" style="text-align: center">작성자</th>
      <th scope="col" style="text-align: center">날짜</th>
      <th scope="col" style="text-align: center">조회수</th>
    </tr>
  </thead>
  <tbody class="table-group-divider ">
    <c:choose>
    <c:when test="${empty requestScope.freeList}">
    <%-- <c:when test="${empty requestScope.pageList}"> --%>
	<tr>
        <th colspan="5">
            등록된 게시물이 없습니다.
        </th>
    </tr>
    </c:when>
    <c:otherwise>
    <c:forEach items="${requestScope.freeList}" var="board">
	<%-- <c:forEach items="${requestScope.pageList.content}" var="board"> --%>
		    <tr>
		        <td>
		            ${board.bno}
		        </td>
		        <td>
					<a href="${pageContext.request.contextPath}/board/read/${board.bno}"> 
					  ${board.subject} /  Reply count : 
					  <b style="color:red">  ${board.repliesList.size() } </b>
					</a>
					
		        </td>
		        <td>
		            ${board.writer}
		        </td>
		        <td>
		            ${board.insertDate}
		        </td>
		         <td>
		            ${board.readnum}
		        </td>
		    </tr>
    </c:forEach>
	</c:otherwise>
 </c:choose>
  </tbody>
</table>


<%-- ${pageList.hasPrevious()}  /  ${pageList.hasNext()} --%>
<%-- 
<div style="text-align: center">
		<!--  블럭당  -->
 <nav class="pagination-container">
	<div class="pagination">
	<c:set var="doneLoop" value="false"/>
		
		 <c:if test="${(startPage-blockCount) > 0}"> <!-- (-2) > 0  -->
		      <a class="pagination-newer" href="${pageContext.request.contextPath}/board/list?nowPage=${startPage-1}">PREV</a>
		  </c:if> 
		  
		<span class="pagination-inner"> 
		  <c:forEach var='i' begin='${startPage}' end='${(startPage-1)+blockCount}'> 
		  
			    <c:if test="${(i-1)>=pageList.getTotalPages()}">
			       <c:set var="doneLoop" value="true"/>
			    </c:if> 
		    
		  <c:if test="${not doneLoop}" >
		         <a class="${i==nowPage?'pagination-active':page}" href="${pageContext.request.contextPath}/board/list?nowPage=${i}">${i}</a> 
		  </c:if>
		   
		</c:forEach>
		</span> 
				
		 <c:if test="${(startPage+blockCount)<=pageList.getTotalPages()}">
		     <a class="pagination-older" href="${pageContext.request.contextPath}/board/list?nowPage=${startPage+blockCount}">NEXT</a>
		 </c:if>
				 
		</div>
	</nav>  
</div>

 --%>
<div align=right>
<span style="font-size:9pt;">&lt;<a href="${pageContext.request.contextPath}/board/write">글쓰기</a>&gt;</span>
</div>

</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>	











