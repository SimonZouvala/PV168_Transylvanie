<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head title="Guest">

</head>
<body style="background: lightpink;text-align: center;">
<h2>Hosté v hotelu</h2>
<table style="border: solid 2px black;border-radius: 2px; padding: 20px; margin: auto;">
    <thead>
    <tr>
        <th>Jméno</th>
        <th>Tel. číslo</th>
    </tr>
    </thead>
    <c:forEach items="${guests}" var="guest">
        <tr>
            <td><c:out value="${guest.name}"/></td>
            <td><c:out value="${guest.phone}"/></td>
            <td><form method="post" action="${pageContext.request.contextPath}/guests/delete?id=${guest.id}"
                      style="margin-bottom: 0;"><input type="submit" value="Smazat"></form></td>
        </tr>
    </c:forEach>
</table>

<h2>Zadejte hosta</h2>
<c:if test="${not empty chyba}">
    <div style="border: solid 1px lightgoldenrodyellow; background-color: red; padding: 10px; width: 400px; margin: auto; font-weight: bolder">
        <c:out value="${chyba}"/>
    </div>
</c:if>
<form action="${pageContext.request.contextPath}/guests/add" method="post">
    <table style="margin: auto; text-align: center">
        <tr>
            <th>Jméno hosta:</th>
            <td><input type="text" name="name" value="<c:out value='${param.name}'/>"/></td>
        </tr>
        <tr>
            <th>Tel. číslo:</th>
            <td><input type="text" name="phone" value="<c:out value='${param.phone}'/>"/></td>
        </tr>
    </table>
    <input type="Submit" value="Zadat" />
</form>
<br>
<a href="/hotel-web/index.jsp">Zpět na rozcesník</a>
</body>
</html>