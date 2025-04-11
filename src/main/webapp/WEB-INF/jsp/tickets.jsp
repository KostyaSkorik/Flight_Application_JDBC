<%@ page contentType="text/html;charset=UTF-8"%>
<%--
  Created by IntelliJ IDEA.
  User: kostya-skorik
  Date: 4/8/25
  Time: 5:53 PM
  To change this template use File | Settings | File Templates.
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Tickets</title>
</head>
<body>

<h1>Hello JSP</h1>
<h1>Купленные билеты</h1>
<ul>
    <c:if test="${not empty requestScope.tickets}">
        <c:forEach var="ticket" items="${requestScope.tickets}">
            <li>${fn:toLowerCase(ticket.getSeatNo())}</li>
        </c:forEach>
    </c:if>
    <c:if test="${empty requestScope.tickets}">

        <h2>Нет актуальных билетов</h2>

    </c:if>

</ul>
<%@include file="header.jsp"%>
</body>
</html>
