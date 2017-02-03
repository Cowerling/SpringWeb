<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2017-1-13
  Time: 15:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Spittr</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />">
    </head>
    <body>
        <h1>Register</h1>

        <sf:form method="post" commandName="spitter">
            <sf:errors path="*" element="div" cssClass="errors" />
            <sf:label path="firstName" cssErrorClass="error" />First Name: <sf:input path="firstName" cssErrorClass="error" /><br />
            Last Name: <sf:input path="lastName" /><br />
            Email: <sf:input path="email" type="email" /><br />
            Username: <sf:input path="username" /><br />
            Password: <sf:password path="password" /><br />

            <input type="submit" value="Register" />
        </sf:form>
    </body>
</html>
