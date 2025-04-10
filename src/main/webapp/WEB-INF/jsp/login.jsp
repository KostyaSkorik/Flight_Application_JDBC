<%--
  Created by IntelliJ IDEA.
  User: kostya-skorik
  Date: 4/10/25
  Time: 12:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    <label for="email">Email:
    <input type="text" name="email" id="email" value="${param.email}" required>
    </label><br>
    <label for="pwd">Password:
    <input type="password" name="pwd" id="pwd" required>
    </label><br>
    <button type="submit">Login</button>
    <a href="${pageContext.request.contextPath}/registration">
        <button type="button">Register</button>
    </a>
    <c:if test="${param.error!=null}">
        <div style="color: red">
            <span>Email(${param.mail}) or password is not correct</span>
        </div>
    </c:if>
</form>
</body>
</html>
