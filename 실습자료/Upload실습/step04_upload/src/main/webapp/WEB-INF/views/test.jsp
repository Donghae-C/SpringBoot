<%--
  Created by IntelliJ IDEA.
  User: KOSTA
  Date: 2024-05-21
  Time: 오후 3:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>spring boot Test 22= ${message} </h1>
<c:forEach items="${list}" var="i">
    ${i}<br>

</c:forEach>
</body>
</html>
