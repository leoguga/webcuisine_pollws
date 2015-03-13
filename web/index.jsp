<%-- 
    Document   : index
    Created on : 10-mar-2015, 2:21:52
    Author     : Leopoldo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!!!</h1>
        <c:forEach var="poll" items="${poll}">
            ${poll.id}
        </c:forEach>
    </body>
</html>
