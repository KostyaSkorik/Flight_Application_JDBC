<%--
  Created by IntelliJ IDEA.
  User: kostya-skorik
  Date: 4/8/25
  Time: 5:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <span>CONTENT РУССКИЙ</span>
    <p>Size: ${requestScope.flights.size()}</p>
    <p>Description: ${requestScope.flights[0].getDescription()}</p>
    <p>ID: ${requestScope.flights[0].getId()}</p>
    <p>JSESSIONID: ${cookie.get("JSESSIONID")}</p>
    <p>PARAM REQ: ${param.flightId}</p>
    <p>HEADER: ${header["sec-ch-ua-platform"]}</p>
    <p>NOT EMPTY: ${not empty flights}</p>



</div>
</body>
</html>
