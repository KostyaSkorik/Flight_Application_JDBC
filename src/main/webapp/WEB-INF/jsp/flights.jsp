<%--
  Created by IntelliJ IDEA.
  User: kostya-skorik
  Date: 4/8/25
  Time: 6:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Flights</title>
</head>
<body>
<h1>Все перелеты</h1>
<ul>
  <c:forEach var="flight" items="${requestScope.flights}">
    <li>
      <a href="${pageContext.request.contextPath}/tickets?flightId=${flight.getId()}">${flight.getDescription()}</a>
    </li>
  </c:forEach>
</ul>
<%@include file="header.jsp"%>
</body>
</html>
