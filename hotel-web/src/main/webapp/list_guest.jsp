<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>

<table border="1">
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
    <div style="border: solid 1px lightgoldenrodyellow; background-color: red; padding: 10px">
        <c:out value="${chyba}"/>
    </div>
</c:if>
<form action="${pageContext.request.contextPath}/guests/add" method="post">
    <table>
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

</body>
</html>