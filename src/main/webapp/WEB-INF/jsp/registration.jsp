<%--
  Created by IntelliJ IDEA.
  User: kostya-skorik
  Date: 4/8/25
  Time: 7:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/registration" method="post">
  <label for="name">Name:
    <input type="text" name="name" id="name">
  </label><br/>
  <label for="birthday">Birthday:
    <input type="date" name="birthday" id = birthday>
  </label><br/>
  <label for="email">Email:
    <input type="text" name="email" id="email">
  </label><br/>
  <label for="pwd">Password
    <input type="password" name="pwd" id="pwd">
  </label><br/>
  <label>
    <select>
      <c:forEach var="role" items="${requestScope.roles}">
        <option label="${role}">${role}</option><br>
      </c:forEach>
    </select>
  </label><br/>

    <c:forEach var="gender" items="${requestScope.genders}">
      <label>
        <input type="radio" name="gender" VALUE="${gender}">
      </label>${gender}<br>
    </c:forEach>
  <input type="submit" value="Send">
</form>
</body>
</html>
