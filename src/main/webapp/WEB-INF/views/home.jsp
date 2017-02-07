<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2017-1-10
  Time: 9:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Spittr</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />">
    </head>
    <body>
        <h1><s:message code="spittr.welcome" /></h1>

        <s:url value="/spitter/register" var="registerUrl" />
        <s:url value="/spittles" var="spittlesUrl">
            <s:param name="max" value="60" />
            <s:param name="count" value="20" />
        </s:url>
        <a href="${spittlesUrl}">Spittles</a>|
        <a href="${registerUrl}">Register</a>
    </body>
</html>