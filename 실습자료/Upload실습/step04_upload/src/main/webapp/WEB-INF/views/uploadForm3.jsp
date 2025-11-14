<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>spring boot UPload3 </h1>
<form  action="/upload3"  enctype="multipart/form-data" method="post">
    제목 <input type="text" name="subject" /></p>
    <input type="file" name="uploadFile" />
    <input type="submit" value="업로드"/>
</form>

<c:if test="${not empty msg}" > ${msg}</c:if>
</body>
</html>